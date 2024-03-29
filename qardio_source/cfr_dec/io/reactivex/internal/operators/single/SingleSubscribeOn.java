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
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubscribeOn<T>
extends Single<T> {
    final Scheduler scheduler;
    final SingleSource<? extends T> source;

    public SingleSubscribeOn(SingleSource<? extends T> singleSource, Scheduler scheduler) {
        this.source = singleSource;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> object) {
        SubscribeOnObserver<? extends T> subscribeOnObserver = new SubscribeOnObserver<T>((SingleObserver<? extends T>)object, this.source);
        object.onSubscribe(subscribeOnObserver);
        object = this.scheduler.scheduleDirect(subscribeOnObserver);
        subscribeOnObserver.task.replace((Disposable)object);
    }

    static final class SubscribeOnObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>,
    Disposable,
    Runnable {
        final SingleObserver<? super T> actual;
        final SingleSource<? extends T> source;
        final SequentialDisposable task;

        SubscribeOnObserver(SingleObserver<? super T> singleObserver, SingleSource<? extends T> singleSource) {
            this.actual = singleObserver;
            this.source = singleSource;
            this.task = new SequentialDisposable();
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
            this.task.dispose();
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
        }

        @Override
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override
        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }

        @Override
        public void run() {
            this.source.subscribe(this);
        }
    }

}

