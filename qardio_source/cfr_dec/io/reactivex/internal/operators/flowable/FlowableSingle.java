/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSingle<T>
extends AbstractFlowableWithUpstream<T, T> {
    final T defaultValue;

    public FlowableSingle(Flowable<T> flowable, T t) {
        super(flowable);
        this.defaultValue = t;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new SingleElementSubscriber<T>(subscriber, (T)this.defaultValue));
    }

    static final class SingleElementSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements FlowableSubscriber<T> {
        final T defaultValue;
        boolean done;
        Subscription s;

        SingleElementSubscriber(Subscriber<? super T> subscriber, T t) {
            super(subscriber);
            this.defaultValue = t;
        }

        @Override
        public void cancel() {
            super.cancel();
            this.s.cancel();
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Object object = this.value;
            this.value = null;
            Object object2 = object;
            if (object == null) {
                object2 = this.defaultValue;
            }
            if (object2 == null) {
                this.actual.onComplete();
                return;
            }
            this.complete(object2);
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.value != null) {
                this.done = true;
                this.s.cancel();
                this.actual.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                return;
            }
            this.value = t;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }
    }

}

