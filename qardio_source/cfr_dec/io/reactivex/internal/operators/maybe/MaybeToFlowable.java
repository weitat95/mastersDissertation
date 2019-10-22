/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeToFlowable<T>
extends Flowable<T> {
    final MaybeSource<T> source;

    public MaybeToFlowable(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new MaybeToFlowableSubscriber<T>(subscriber));
    }

    static final class MaybeToFlowableSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements MaybeObserver<T> {
        Disposable d;

        MaybeToFlowableSubscriber(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override
        public void cancel() {
            super.cancel();
            this.d.dispose();
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T t) {
            this.complete(t);
        }
    }

}

