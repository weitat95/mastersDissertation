/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.processors;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.FlowableProcessor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class UnicastProcessor<T>
extends FlowableProcessor<T> {
    final AtomicReference<Subscriber<? super T>> actual;
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    boolean enableOperatorFusion;
    Throwable error;
    final AtomicReference<Runnable> onTerminate;
    final AtomicBoolean once;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicLong requested;
    final BasicIntQueueSubscription<T> wip;

    UnicastProcessor(int n) {
        this(n, null, true);
    }

    UnicastProcessor(int n, Runnable runnable, boolean bl) {
        this.queue = new SpscLinkedArrayQueue(ObjectHelper.verifyPositive(n, "capacityHint"));
        this.onTerminate = new AtomicReference<Runnable>(runnable);
        this.delayError = bl;
        this.actual = new AtomicReference();
        this.once = new AtomicBoolean();
        this.wip = new UnicastQueueSubscription();
        this.requested = new AtomicLong();
    }

    public static <T> UnicastProcessor<T> create(int n) {
        return new UnicastProcessor<T>(n);
    }

    boolean checkTerminated(boolean bl, boolean bl2, boolean bl3, Subscriber<? super T> subscriber, SpscLinkedArrayQueue<T> object) {
        if (this.cancelled) {
            ((SpscLinkedArrayQueue)object).clear();
            this.actual.lazySet(null);
            return true;
        }
        if (bl2) {
            if (bl && this.error != null) {
                ((SpscLinkedArrayQueue)object).clear();
                this.actual.lazySet(null);
                subscriber.onError(this.error);
                return true;
            }
            if (bl3) {
                object = this.error;
                this.actual.lazySet(null);
                if (object != null) {
                    subscriber.onError((Throwable)object);
                    return true;
                }
                subscriber.onComplete();
                return true;
            }
        }
        return false;
    }

    void doTerminate() {
        Runnable runnable = this.onTerminate.get();
        if (runnable != null && this.onTerminate.compareAndSet(runnable, null)) {
            runnable.run();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void drain() {
        if (this.wip.getAndIncrement() != 0) return;
        {
            int n = 1;
            Subscriber<? super T> subscriber = this.actual.get();
            do {
                if (subscriber != null) {
                    if (!this.enableOperatorFusion) break;
                    this.drainFused(subscriber);
                    return;
                }
                if ((n = this.wip.addAndGet(-n)) == 0) return;
                {
                    subscriber = this.actual.get();
                    continue;
                }
                break;
            } while (true);
            this.drainRegular(subscriber);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void drainFused(Subscriber<? super T> subscriber) {
        int n;
        int n2 = 1;
        Object object = this.queue;
        boolean bl = !this.delayError;
        do {
            if (this.cancelled) {
                ((SpscLinkedArrayQueue)object).clear();
                this.actual.lazySet(null);
                return;
            }
            boolean bl2 = this.done;
            if (bl && bl2 && this.error != null) {
                ((SpscLinkedArrayQueue)object).clear();
                this.actual.lazySet(null);
                subscriber.onError(this.error);
                return;
            }
            subscriber.onNext(null);
            if (bl2) {
                this.actual.lazySet(null);
                object = this.error;
                if (object != null) {
                    subscriber.onError((Throwable)object);
                    return;
                }
                subscriber.onComplete();
                return;
            }
            n2 = n = this.wip.addAndGet(-n2);
        } while (n != 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void drainRegular(Subscriber<? super T> subscriber) {
        int n;
        int n2 = 1;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        boolean bl = !this.delayError;
        do {
            long l;
            long l2 = this.requested.get();
            for (l = 0L; l2 != l; ++l) {
                boolean bl2 = this.done;
                T t = spscLinkedArrayQueue.poll();
                boolean bl3 = t == null;
                if (this.checkTerminated(bl, bl2, bl3, subscriber, spscLinkedArrayQueue)) return;
                {
                    if (bl3) break;
                    subscriber.onNext(t);
                    continue;
                }
            }
            if (l2 == l && this.checkTerminated(bl, this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                return;
            }
            if (l != 0L && l2 != Long.MAX_VALUE) {
                this.requested.addAndGet(-l);
            }
            n2 = n = this.wip.addAndGet(-n2);
        } while (n != 0);
    }

    @Override
    public void onComplete() {
        if (this.done || this.cancelled) {
            return;
        }
        this.done = true;
        this.doTerminate();
        this.drain();
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.done || this.cancelled) {
            RxJavaPlugins.onError(throwable);
            return;
        }
        Throwable throwable2 = throwable;
        if (throwable == null) {
            throwable2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.error = throwable2;
        this.done = true;
        this.doTerminate();
        this.drain();
    }

    @Override
    public void onNext(T t) {
        if (this.done || this.cancelled) {
            return;
        }
        if (t == null) {
            this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return;
        }
        this.queue.offer(t);
        this.drain();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        if (this.done || this.cancelled) {
            subscription.cancel();
            return;
        }
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (!this.once.get() && this.once.compareAndSet(false, true)) {
            subscriber.onSubscribe(this.wip);
            this.actual.set(subscriber);
            if (this.cancelled) {
                this.actual.lazySet(null);
                return;
            }
            this.drain();
            return;
        }
        EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
    }

    final class UnicastQueueSubscription
    extends BasicIntQueueSubscription<T> {
        UnicastQueueSubscription() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void cancel() {
            block3: {
                block2: {
                    if (UnicastProcessor.this.cancelled) break block2;
                    UnicastProcessor.this.cancelled = true;
                    UnicastProcessor.this.doTerminate();
                    if (!UnicastProcessor.this.enableOperatorFusion && UnicastProcessor.this.wip.getAndIncrement() == 0) break block3;
                }
                return;
            }
            UnicastProcessor.this.queue.clear();
            UnicastProcessor.this.actual.lazySet(null);
        }

        @Override
        public void clear() {
            UnicastProcessor.this.queue.clear();
        }

        @Override
        public boolean isEmpty() {
            return UnicastProcessor.this.queue.isEmpty();
        }

        @Override
        public T poll() {
            return UnicastProcessor.this.queue.poll();
        }

        @Override
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                BackpressureHelper.add(UnicastProcessor.this.requested, l);
                UnicastProcessor.this.drain();
            }
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 2) != 0) {
                UnicastProcessor.this.enableOperatorFusion = true;
                return 2;
            }
            return 0;
        }
    }

}

