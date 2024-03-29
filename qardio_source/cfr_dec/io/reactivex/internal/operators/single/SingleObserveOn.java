/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleObserveOn<T>
extends Single<T> {
    final Scheduler scheduler;
    final SingleSource<T> source;

    public SingleObserveOn(SingleSource<T> singleSource, Scheduler scheduler) {
        this.source = singleSource;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new ObserveOnSingleObserver<T>(singleObserver, this.scheduler));
    }

    static final class ObserveOnSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>,
    Disposable,
    Runnable {
        final SingleObserver<? super T> actual;
        Throwable error;
        final Scheduler scheduler;
        T value;

        ObserveOnSingleObserver(SingleObserver<? super T> singleObserver, Scheduler scheduler) {
            this.actual = singleObserver;
            this.scheduler = scheduler;
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T t) {
            this.value = t;
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        @Override
        public void run() {
            Throwable throwable = this.error;
            if (throwable != null) {
                this.actual.onError(throwable);
                return;
            }
            this.actual.onSuccess(this.value);
        }
    }

}

