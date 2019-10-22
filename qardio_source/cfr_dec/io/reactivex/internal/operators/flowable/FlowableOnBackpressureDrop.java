/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureDrop<T>
extends AbstractFlowableWithUpstream<T, T>
implements Consumer<T> {
    final Consumer<? super T> onDrop = this;

    public FlowableOnBackpressureDrop(Flowable<T> flowable) {
        super(flowable);
    }

    @Override
    public void accept(T t) {
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new BackpressureDropSubscriber<T>(subscriber, this.onDrop));
    }

    static final class BackpressureDropSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>,
    Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        final Consumer<? super T> onDrop;
        Subscription s;

        BackpressureDropSubscriber(Subscriber<? super T> subscriber, Consumer<? super T> consumer) {
            this.actual = subscriber;
            this.onDrop = consumer;
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
            try {
                this.onDrop.accept(t);
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.cancel();
                this.onError(throwable);
                return;
            }
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

