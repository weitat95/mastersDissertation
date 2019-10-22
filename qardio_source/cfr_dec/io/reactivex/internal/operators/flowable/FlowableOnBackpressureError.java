/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureError<T>
extends AbstractFlowableWithUpstream<T, T> {
    public FlowableOnBackpressureError(Flowable<T> flowable) {
        super(flowable);
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new BackpressureErrorSubscriber<T>(subscriber));
    }

    static final class BackpressureErrorSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>,
    Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        Subscription s;

        BackpressureErrorSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
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
            if (this.get() != 0L) {
                this.actual.onNext(t);
                BackpressureHelper.produced(this, 1L);
                return;
            }
            this.onError(new MissingBackpressureException("could not emit value due to lack of requests"));
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
                BackpressureHelper.add(this, l);
            }
        }
    }

}

