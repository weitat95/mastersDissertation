/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.operators.flowable.FlowableSingle;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

public final class FlowableSingleSingle<T>
extends Single<T>
implements FuseToFlowable<T> {
    final T defaultValue;
    final Flowable<T> source;

    public FlowableSingleSingle(Flowable<T> flowable, T t) {
        this.source = flowable;
        this.defaultValue = t;
    }

    @Override
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableSingle<T>(this.source, this.defaultValue));
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe((FlowableSubscriber<? super T>)new SingleElementSubscriber<T>(singleObserver, (T)this.defaultValue));
    }

    static final class SingleElementSubscriber<T>
    implements FlowableSubscriber<T>,
    Disposable {
        final SingleObserver<? super T> actual;
        final T defaultValue;
        boolean done;
        Subscription s;
        T value;

        SingleElementSubscriber(SingleObserver<? super T> singleObserver, T t) {
            this.actual = singleObserver;
            this.defaultValue = t;
        }

        @Override
        public void dispose() {
            this.s.cancel();
            this.s = SubscriptionHelper.CANCELLED;
        }

        @Override
        public boolean isDisposed() {
            return this.s == SubscriptionHelper.CANCELLED;
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.s = SubscriptionHelper.CANCELLED;
            T t = this.value;
            this.value = null;
            T t2 = t;
            if (t == null) {
                t2 = this.defaultValue;
            }
            if (t2 != null) {
                this.actual.onSuccess(t2);
                return;
            }
            this.actual.onError(new NoSuchElementException());
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            this.s = SubscriptionHelper.CANCELLED;
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
                this.s = SubscriptionHelper.CANCELLED;
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

