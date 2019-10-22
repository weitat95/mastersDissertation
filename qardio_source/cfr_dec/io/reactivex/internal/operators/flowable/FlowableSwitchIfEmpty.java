/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchIfEmpty<T>
extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends T> other;

    public FlowableSwitchIfEmpty(Flowable<T> flowable, Publisher<? extends T> publisher) {
        super(flowable);
        this.other = publisher;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        SwitchIfEmptySubscriber<? extends T> switchIfEmptySubscriber = new SwitchIfEmptySubscriber<T>(subscriber, this.other);
        subscriber.onSubscribe(switchIfEmptySubscriber.arbiter);
        this.source.subscribe(switchIfEmptySubscriber);
    }

    static final class SwitchIfEmptySubscriber<T>
    implements FlowableSubscriber<T> {
        final Subscriber<? super T> actual;
        final SubscriptionArbiter arbiter;
        boolean empty;
        final Publisher<? extends T> other;

        SwitchIfEmptySubscriber(Subscriber<? super T> subscriber, Publisher<? extends T> publisher) {
            this.actual = subscriber;
            this.other = publisher;
            this.empty = true;
            this.arbiter = new SubscriptionArbiter();
        }

        @Override
        public void onComplete() {
            if (this.empty) {
                this.empty = false;
                this.other.subscribe(this);
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
            if (this.empty) {
                this.empty = false;
            }
            this.actual.onNext(t);
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            this.arbiter.setSubscription(subscription);
        }
    }

}

