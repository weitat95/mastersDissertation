/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeat<T>
extends AbstractFlowableWithUpstream<T, T> {
    final long count;

    public FlowableRepeat(Flowable<T> flowable, long l) {
        super(flowable);
        this.count = l;
    }

    @Override
    public void subscribeActual(Subscriber<? super T> subscriber) {
        long l = Long.MAX_VALUE;
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter();
        subscriber.onSubscribe(subscriptionArbiter);
        if (this.count != Long.MAX_VALUE) {
            l = this.count - 1L;
        }
        new RepeatSubscriber<T>(subscriber, l, subscriptionArbiter, this.source).subscribeNext();
    }

    static final class RepeatSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T> {
        final Subscriber<? super T> actual;
        long remaining;
        final SubscriptionArbiter sa;
        final Publisher<? extends T> source;

        RepeatSubscriber(Subscriber<? super T> subscriber, long l, SubscriptionArbiter subscriptionArbiter, Publisher<? extends T> publisher) {
            this.actual = subscriber;
            this.sa = subscriptionArbiter;
            this.source = publisher;
            this.remaining = l;
        }

        @Override
        public void onComplete() {
            long l = this.remaining;
            if (l != Long.MAX_VALUE) {
                this.remaining = l - 1L;
            }
            if (l != 0L) {
                this.subscribeNext();
                return;
            }
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            this.actual.onNext(t);
            this.sa.produced(1L);
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            this.sa.setSubscription(subscription);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void subscribeNext() {
            int n;
            if (this.getAndIncrement() != 0) return;
            int n2 = 1;
            do {
                if (this.sa.isCancelled()) {
                    return;
                }
                this.source.subscribe(this);
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }
    }

}

