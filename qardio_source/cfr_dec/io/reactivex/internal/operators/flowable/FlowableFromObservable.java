/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T>
extends Flowable<T> {
    private final Observable<T> upstream;

    public FlowableFromObservable(Observable<T> observable) {
        this.upstream = observable;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.upstream.subscribe(new SubscriberObserver<T>(subscriber));
    }

    static class SubscriberObserver<T>
    implements Observer<T>,
    Subscription {
        private Disposable d;
        private final Subscriber<? super T> s;

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.s = subscriber;
        }

        @Override
        public void cancel() {
            this.d.dispose();
        }

        @Override
        public void onComplete() {
            this.s.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.s.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            this.s.onNext(t);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.d = disposable;
            this.s.onSubscribe(this);
        }

        @Override
        public void request(long l) {
        }
    }

}

