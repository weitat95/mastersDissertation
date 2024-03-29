/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableSubscribeOn
extends Completable {
    final Scheduler scheduler;
    final CompletableSource source;

    public CompletableSubscribeOn(CompletableSource completableSource, Scheduler scheduler) {
        this.source = completableSource;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(CompletableObserver object) {
        SubscribeOnObserver subscribeOnObserver = new SubscribeOnObserver((CompletableObserver)object, this.source);
        object.onSubscribe(subscribeOnObserver);
        object = this.scheduler.scheduleDirect(subscribeOnObserver);
        subscribeOnObserver.task.replace((Disposable)object);
    }

    static final class SubscribeOnObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver,
    Disposable,
    Runnable {
        final CompletableObserver actual;
        final CompletableSource source;
        final SequentialDisposable task;

        SubscribeOnObserver(CompletableObserver completableObserver, CompletableSource completableSource) {
            this.actual = completableObserver;
            this.source = completableSource;
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
        public void run() {
            this.source.subscribe(this);
        }
    }

}

