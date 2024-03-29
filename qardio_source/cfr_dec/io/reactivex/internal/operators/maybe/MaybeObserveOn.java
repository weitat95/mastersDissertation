/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeObserveOn<T>
extends AbstractMaybeWithUpstream<T, T> {
    final Scheduler scheduler;

    public MaybeObserveOn(MaybeSource<T> maybeSource, Scheduler scheduler) {
        super(maybeSource);
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new ObserveOnMaybeObserver<T>(maybeObserver, this.scheduler));
    }

    static final class ObserveOnMaybeObserver<T>
    extends AtomicReference<Disposable>
    implements MaybeObserver<T>,
    Disposable,
    Runnable {
        final MaybeObserver<? super T> actual;
        Throwable error;
        final Scheduler scheduler;
        T value;

        ObserveOnMaybeObserver(MaybeObserver<? super T> maybeObserver, Scheduler scheduler) {
            this.actual = maybeObserver;
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
        public void onSuccess(T t) {
            this.value = t;
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        @Override
        public void run() {
            Throwable throwable = this.error;
            if (throwable != null) {
                this.error = null;
                this.actual.onError(throwable);
                return;
            }
            throwable = this.value;
            if (throwable != null) {
                this.value = null;
                this.actual.onSuccess(throwable);
                return;
            }
            this.actual.onComplete();
        }
    }

}

