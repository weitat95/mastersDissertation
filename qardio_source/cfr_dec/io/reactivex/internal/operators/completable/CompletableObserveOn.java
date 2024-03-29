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
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableObserveOn
extends Completable {
    final Scheduler scheduler;
    final CompletableSource source;

    public CompletableObserveOn(CompletableSource completableSource, Scheduler scheduler) {
        this.source = completableSource;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new ObserveOnCompletableObserver(completableObserver, this.scheduler));
    }

    static final class ObserveOnCompletableObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver,
    Disposable,
    Runnable {
        final CompletableObserver actual;
        Throwable error;
        final Scheduler scheduler;

        ObserveOnCompletableObserver(CompletableObserver completableObserver, Scheduler scheduler) {
            this.actual = completableObserver;
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
        public void onComplete() {
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
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
        public void run() {
            Throwable throwable = this.error;
            if (throwable != null) {
                this.error = null;
                this.actual.onError(throwable);
                return;
            }
            this.actual.onComplete();
        }
    }

}

