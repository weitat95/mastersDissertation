/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSubscribeOn<T>
extends AbstractMaybeWithUpstream<T, T> {
    final Scheduler scheduler;

    public MaybeSubscribeOn(MaybeSource<T> maybeSource, Scheduler scheduler) {
        super(maybeSource);
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        SubscribeOnMaybeObserver<? super T> subscribeOnMaybeObserver = new SubscribeOnMaybeObserver<T>(maybeObserver);
        maybeObserver.onSubscribe(subscribeOnMaybeObserver);
        subscribeOnMaybeObserver.task.replace(this.scheduler.scheduleDirect(new SubscribeTask<T>(subscribeOnMaybeObserver, this.source)));
    }

    static final class SubscribeOnMaybeObserver<T>
    extends AtomicReference<Disposable>
    implements MaybeObserver<T>,
    Disposable {
        final MaybeObserver<? super T> actual;
        final SequentialDisposable task;

        SubscribeOnMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
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
        public void onComplete() {
            this.actual.onComplete();
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
    }

    static final class SubscribeTask<T>
    implements Runnable {
        final MaybeObserver<? super T> observer;
        final MaybeSource<T> source;

        SubscribeTask(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.observer = maybeObserver;
            this.source = maybeSource;
        }

        @Override
        public void run() {
            this.source.subscribe(this.observer);
        }
    }

}

