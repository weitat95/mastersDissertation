/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeMergeArray<T>
extends Flowable<T> {
    final MaybeSource<? extends T>[] sources;

    public MaybeMergeArray(MaybeSource<? extends T>[] arrmaybeSource) {
        this.sources = arrmaybeSource;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void subscribeActual(Subscriber<? super T> object) {
        MaybeSource<? extends T>[] arrmaybeSource = this.sources;
        int n = arrmaybeSource.length;
        SimpleQueue simpleQueue = n <= MaybeMergeArray.bufferSize() ? new MpscFillOnceSimpleQueue(n) : new ClqSimpleQueue();
        simpleQueue = new MergeMaybeObserver(object, n, (SimpleQueueWithConsumerIndex<Object>)simpleQueue);
        object.onSubscribe((Subscription)((Object)simpleQueue));
        object = ((MergeMaybeObserver)simpleQueue).error;
        int n2 = arrmaybeSource.length;
        n = 0;
        do {
            MaybeSource maybeSource;
            block4: {
                block3: {
                    if (n >= n2) break block3;
                    maybeSource = arrmaybeSource[n];
                    if (!((MergeMaybeObserver)simpleQueue).isCancelled() && ((AtomicReference)object).get() == null) break block4;
                }
                return;
            }
            maybeSource.subscribe(simpleQueue);
            ++n;
        } while (true);
    }

    static final class ClqSimpleQueue<T>
    extends ConcurrentLinkedQueue<T>
    implements SimpleQueueWithConsumerIndex<T> {
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        ClqSimpleQueue() {
        }

        @Override
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override
        public void drop() {
            this.poll();
        }

        @Override
        public boolean offer(T t) {
            this.producerIndex.getAndIncrement();
            return super.offer(t);
        }

        @Override
        public T poll() {
            Object e = super.poll();
            if (e != null) {
                ++this.consumerIndex;
            }
            return (T)e;
        }

        @Override
        public int producerIndex() {
            return this.producerIndex.get();
        }
    }

    static final class MergeMaybeObserver<T>
    extends BasicIntQueueSubscription<T>
    implements MaybeObserver<T> {
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        long consumed;
        final AtomicThrowable error;
        boolean outputFused;
        final SimpleQueueWithConsumerIndex<Object> queue;
        final AtomicLong requested;
        final CompositeDisposable set;
        final int sourceCount;

        MergeMaybeObserver(Subscriber<? super T> subscriber, int n, SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex) {
            this.actual = subscriber;
            this.sourceCount = n;
            this.set = new CompositeDisposable();
            this.requested = new AtomicLong();
            this.error = new AtomicThrowable();
            this.queue = simpleQueueWithConsumerIndex;
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.set.dispose();
                if (this.getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override
        public void clear() {
            this.queue.clear();
        }

        void drain() {
            if (this.getAndIncrement() != 0) {
                return;
            }
            if (this.outputFused) {
                this.drainFused();
                return;
            }
            this.drainNormal();
        }

        /*
         * Enabled aggressive block sorting
         */
        void drainFused() {
            int n;
            int n2 = 1;
            Subscriber<T> subscriber = this.actual;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.queue;
            do {
                if (this.cancelled) {
                    simpleQueueWithConsumerIndex.clear();
                    return;
                }
                Throwable throwable = (Throwable)this.error.get();
                if (throwable != null) {
                    simpleQueueWithConsumerIndex.clear();
                    subscriber.onError(throwable);
                    return;
                }
                n = simpleQueueWithConsumerIndex.producerIndex() == this.sourceCount ? 1 : 0;
                if (!simpleQueueWithConsumerIndex.isEmpty()) {
                    subscriber.onNext(null);
                }
                if (n != 0) {
                    subscriber.onComplete();
                    return;
                }
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }

        void drainNormal() {
            int n;
            int n2 = 1;
            Subscriber<Object> subscriber = this.actual;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.queue;
            long l = this.consumed;
            do {
                block9: {
                    long l2 = this.requested.get();
                    do {
                        Object object;
                        block11: {
                            block10: {
                                if (l == l2) break block10;
                                if (this.cancelled) {
                                    simpleQueueWithConsumerIndex.clear();
                                    return;
                                }
                                if ((Throwable)this.error.get() != null) {
                                    simpleQueueWithConsumerIndex.clear();
                                    subscriber.onError(this.error.terminate());
                                    return;
                                }
                                if (simpleQueueWithConsumerIndex.consumerIndex() == this.sourceCount) {
                                    subscriber.onComplete();
                                    return;
                                }
                                object = simpleQueueWithConsumerIndex.poll();
                                if (object != null) break block11;
                            }
                            if (l == l2) {
                                if ((Throwable)this.error.get() == null) break;
                                simpleQueueWithConsumerIndex.clear();
                                subscriber.onError(this.error.terminate());
                                return;
                            }
                            break block9;
                        }
                        if (object == NotificationLite.COMPLETE) continue;
                        subscriber.onNext(object);
                        ++l;
                    } while (true);
                    while (simpleQueueWithConsumerIndex.peek() == NotificationLite.COMPLETE) {
                        simpleQueueWithConsumerIndex.drop();
                    }
                    if (simpleQueueWithConsumerIndex.consumerIndex() == this.sourceCount) {
                        subscriber.onComplete();
                        return;
                    }
                }
                this.consumed = l;
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }

        boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        public void onComplete() {
            this.queue.offer((Object)NotificationLite.COMPLETE);
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.error.addThrowable(throwable)) {
                this.set.dispose();
                this.queue.offer((Object)NotificationLite.COMPLETE);
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        @Override
        public void onSuccess(T t) {
            this.queue.offer(t);
            this.drain();
        }

        @Override
        public T poll() throws Exception {
            Object object;
            while ((object = this.queue.poll()) == NotificationLite.COMPLETE) {
            }
            return (T)object;
        }

        @Override
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                BackpressureHelper.add(this.requested, l);
                this.drain();
            }
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 2) != 0) {
                this.outputFused = true;
                return 2;
            }
            return 0;
        }
    }

    static final class MpscFillOnceSimpleQueue<T>
    extends AtomicReferenceArray<T>
    implements SimpleQueueWithConsumerIndex<T> {
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        MpscFillOnceSimpleQueue(int n) {
            super(n);
        }

        @Override
        public void clear() {
            while (this.poll() != null && !this.isEmpty()) {
            }
        }

        @Override
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override
        public void drop() {
            int n = this.consumerIndex;
            this.lazySet(n, null);
            this.consumerIndex = n + 1;
        }

        @Override
        public boolean isEmpty() {
            return this.consumerIndex == this.producerIndex();
        }

        @Override
        public boolean offer(T t) {
            ObjectHelper.requireNonNull(t, "value is null");
            int n = this.producerIndex.getAndIncrement();
            if (n < this.length()) {
                this.lazySet(n, t);
                return true;
            }
            return false;
        }

        @Override
        public T peek() {
            int n = this.consumerIndex;
            if (n == this.length()) {
                return null;
            }
            return (T)this.get(n);
        }

        @Override
        public T poll() {
            int n = this.consumerIndex;
            if (n == this.length()) {
                return null;
            }
            AtomicInteger atomicInteger = this.producerIndex;
            do {
                Object e;
                if ((e = this.get(n)) == null) continue;
                this.consumerIndex = n + 1;
                this.lazySet(n, null);
                return (T)e;
            } while (atomicInteger.get() != n);
            return null;
        }

        @Override
        public int producerIndex() {
            return this.producerIndex.get();
        }
    }

    static interface SimpleQueueWithConsumerIndex<T>
    extends SimpleQueue<T> {
        public int consumerIndex();

        public void drop();

        public T peek();

        @Override
        public T poll();

        public int producerIndex();
    }

}

