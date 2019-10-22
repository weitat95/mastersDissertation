/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTake<T>
extends AbstractFlowableWithUpstream<T, T> {
    final long limit;

    public FlowableTake(Flowable<T> flowable, long l) {
        super(flowable);
        this.limit = l;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new TakeSubscriber<T>(subscriber, this.limit));
    }

    static final class TakeSubscriber<T>
    extends AtomicBoolean
    implements FlowableSubscriber<T>,
    Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        final long limit;
        long remaining;
        Subscription subscription;

        TakeSubscriber(Subscriber<? super T> subscriber, long l) {
            this.actual = subscriber;
            this.limit = l;
            this.remaining = l;
        }

        @Override
        public void cancel() {
            this.subscription.cancel();
        }

        @Override
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!this.done) {
                this.done = true;
                this.subscription.cancel();
                this.actual.onError(throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNext(T t) {
            if (!this.done) {
                long l = this.remaining;
                this.remaining = l - 1L;
                if (l > 0L) {
                    boolean bl = this.remaining == 0L;
                    this.actual.onNext(t);
                    if (bl) {
                        this.subscription.cancel();
                        this.onComplete();
                    }
                }
            }
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            block3: {
                block2: {
                    if (!SubscriptionHelper.validate(this.subscription, subscription)) break block2;
                    this.subscription = subscription;
                    if (this.limit != 0L) break block3;
                    subscription.cancel();
                    this.done = true;
                    EmptySubscription.complete(this.actual);
                }
                return;
            }
            this.actual.onSubscribe(this);
        }

        @Override
        public void request(long l) {
            if (!SubscriptionHelper.validate(l)) {
                return;
            }
            if (!this.get() && this.compareAndSet(false, true) && l >= this.limit) {
                this.subscription.request(Long.MAX_VALUE);
                return;
            }
            this.subscription.request(l);
        }
    }

}

