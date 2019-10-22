/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureLatest<T>
extends AbstractFlowableWithUpstream<T, T> {
    public FlowableOnBackpressureLatest(Flowable<T> flowable) {
        super(flowable);
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new BackpressureLatestSubscriber<T>(subscriber));
    }

    static final class BackpressureLatestSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>,
    Subscription {
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        final AtomicReference<T> current;
        volatile boolean done;
        Throwable error;
        final AtomicLong requested = new AtomicLong();
        Subscription s;

        BackpressureLatestSubscriber(Subscriber<? super T> subscriber) {
            this.current = new AtomicReference();
            this.actual = subscriber;
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (this.getAndIncrement() == 0) {
                    this.current.lazySet(null);
                }
            }
        }

        boolean checkTerminated(boolean bl, boolean bl2, Subscriber<?> subscriber, AtomicReference<T> atomicReference) {
            if (this.cancelled) {
                atomicReference.lazySet(null);
                return true;
            }
            if (bl) {
                Throwable throwable = this.error;
                if (throwable != null) {
                    atomicReference.lazySet(null);
                    subscriber.onError(throwable);
                    return true;
                }
                if (bl2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        void drain() {
            int n;
            if (this.getAndIncrement() != 0) return;
            Subscriber<Object> subscriber = this.actual;
            int n2 = 1;
            AtomicLong atomicLong = this.requested;
            AtomicReference<T> atomicReference = this.current;
            do {
                boolean bl;
                boolean bl2;
                long l;
                for (l = 0L; l != atomicLong.get(); ++l) {
                    bl = this.done;
                    Object t = atomicReference.getAndSet(null);
                    bl2 = t == null;
                    if (this.checkTerminated(bl, bl2, subscriber, atomicReference)) return;
                    {
                        if (bl2) break;
                        subscriber.onNext(t);
                        continue;
                    }
                }
                if (l == atomicLong.get() && this.checkTerminated(bl = this.done, bl2 = atomicReference.get() == null, subscriber, atomicReference)) {
                    return;
                }
                if (l != 0L) {
                    BackpressureHelper.produced(atomicLong, l);
                }
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
            this.done = true;
            this.drain();
        }

        @Override
        public void onNext(T t) {
            this.current.lazySet(t);
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
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                BackpressureHelper.add(this.requested, l);
                this.drain();
            }
        }
    }

}

