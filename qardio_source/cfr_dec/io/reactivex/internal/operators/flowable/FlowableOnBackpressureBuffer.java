/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBuffer<T>
extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Action onOverflow;
    final boolean unbounded;

    public FlowableOnBackpressureBuffer(Flowable<T> flowable, int n, boolean bl, boolean bl2, Action action) {
        super(flowable);
        this.bufferSize = n;
        this.unbounded = bl;
        this.delayError = bl2;
        this.onOverflow = action;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new BackpressureBufferSubscriber<T>(subscriber, this.bufferSize, this.unbounded, this.delayError, this.onOverflow));
    }

    static final class BackpressureBufferSubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements FlowableSubscriber<T> {
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final Action onOverflow;
        boolean outputFused;
        final SimplePlainQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        Subscription s;

        /*
         * Enabled aggressive block sorting
         */
        BackpressureBufferSubscriber(Subscriber<? super T> simplePlainQueue, int n, boolean bl, boolean bl2, Action action) {
            this.actual = simplePlainQueue;
            this.onOverflow = action;
            this.delayError = bl2;
            simplePlainQueue = bl ? new SpscLinkedArrayQueue(n) : new SpscArrayQueue(n);
            this.queue = simplePlainQueue;
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (this.getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        boolean checkTerminated(boolean bl, boolean bl2, Subscriber<? super T> subscriber) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (bl) {
                if (this.delayError) {
                    if (bl2) {
                        Throwable throwable = this.error;
                        if (throwable != null) {
                            subscriber.onError(throwable);
                            return true;
                        }
                        subscriber.onComplete();
                        return true;
                    }
                } else {
                    Throwable throwable = this.error;
                    if (throwable != null) {
                        this.queue.clear();
                        subscriber.onError(throwable);
                        return true;
                    }
                    if (bl2) {
                        subscriber.onComplete();
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void clear() {
            this.queue.clear();
        }

        /*
         * Enabled aggressive block sorting
         */
        void drain() {
            int n;
            if (this.getAndIncrement() != 0) return;
            int n2 = 1;
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            Subscriber<T> subscriber = this.actual;
            do {
                long l;
                if (this.checkTerminated(this.done, simplePlainQueue.isEmpty(), subscriber)) return;
                long l2 = this.requested.get();
                for (l = 0L; l != l2; ++l) {
                    boolean bl = this.done;
                    T t = simplePlainQueue.poll();
                    boolean bl2 = t == null;
                    if (this.checkTerminated(bl, bl2, subscriber)) return;
                    {
                        if (bl2) break;
                        subscriber.onNext(t);
                        continue;
                    }
                }
                if (l == l2 && this.checkTerminated(this.done, simplePlainQueue.isEmpty(), subscriber)) {
                    return;
                }
                if (l != 0L && l2 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-l);
                }
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        public void onComplete() {
            this.done = true;
            if (this.outputFused) {
                this.actual.onComplete();
                return;
            }
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
            this.done = true;
            if (this.outputFused) {
                this.actual.onError(throwable);
                return;
            }
            this.drain();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNext(T object) {
            if (!this.queue.offer(object)) {
                this.s.cancel();
                object = new MissingBackpressureException("Buffer is full");
                try {
                    this.onOverflow.run();
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    ((Throwable)object).initCause(throwable);
                }
                this.onError((Throwable)object);
                return;
            }
            if (this.outputFused) {
                this.actual.onNext(null);
                return;
            }
            this.drain();
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public T poll() throws Exception {
            return this.queue.poll();
        }

        @Override
        public void request(long l) {
            if (!this.outputFused && SubscriptionHelper.validate(l)) {
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

}

