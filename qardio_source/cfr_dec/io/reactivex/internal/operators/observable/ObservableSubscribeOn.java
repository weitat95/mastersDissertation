/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSubscribeOn<T>
extends AbstractObservableWithUpstream<T, T> {
    final Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource<T> observableSource, Scheduler scheduler) {
        super(observableSource);
        this.scheduler = scheduler;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        SubscribeOnObserver<T> subscribeOnObserver = new SubscribeOnObserver<T>(observer);
        observer.onSubscribe(subscribeOnObserver);
        subscribeOnObserver.setDisposable(this.scheduler.scheduleDirect(new SubscribeTask(subscribeOnObserver)));
    }

    static final class SubscribeOnObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>,
    Disposable {
        final Observer<? super T> actual;
        final AtomicReference<Disposable> s;

        SubscribeOnObserver(Observer<? super T> observer) {
            this.actual = observer;
            this.s = new AtomicReference();
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this.s);
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
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
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.s, disposable);
        }

        void setDisposable(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    final class SubscribeTask
    implements Runnable {
        private final SubscribeOnObserver<T> parent;

        SubscribeTask(SubscribeOnObserver<T> subscribeOnObserver) {
            this.parent = subscribeOnObserver;
        }

        @Override
        public void run() {
            ObservableSubscribeOn.this.source.subscribe(this.parent);
        }
    }

}

