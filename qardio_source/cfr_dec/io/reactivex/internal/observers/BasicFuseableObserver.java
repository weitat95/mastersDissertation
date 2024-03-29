/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class BasicFuseableObserver<T, R>
implements Observer<T>,
QueueDisposable<R> {
    protected final Observer<? super R> actual;
    protected boolean done;
    protected QueueDisposable<T> qs;
    protected Disposable s;
    protected int sourceMode;

    public BasicFuseableObserver(Observer<? super R> observer) {
        this.actual = observer;
    }

    protected void afterDownstream() {
    }

    protected boolean beforeDownstream() {
        return true;
    }

    @Override
    public void clear() {
        this.qs.clear();
    }

    @Override
    public void dispose() {
        this.s.dispose();
    }

    protected final void fail(Throwable throwable) {
        Exceptions.throwIfFatal(throwable);
        this.s.dispose();
        this.onError(throwable);
    }

    @Override
    public boolean isDisposed() {
        return this.s.isDisposed();
    }

    @Override
    public boolean isEmpty() {
        return this.qs.isEmpty();
    }

    @Override
    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        this.actual.onComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.done) {
            RxJavaPlugins.onError(throwable);
            return;
        }
        this.done = true;
        this.actual.onError(throwable);
    }

    @Override
    public final void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.s, disposable)) {
            this.s = disposable;
            if (disposable instanceof QueueDisposable) {
                this.qs = (QueueDisposable)disposable;
            }
            if (this.beforeDownstream()) {
                this.actual.onSubscribe(this);
                this.afterDownstream();
            }
        }
    }

    protected final int transitiveBoundaryFusion(int n) {
        QueueDisposable<T> queueDisposable = this.qs;
        if (queueDisposable != null && (n & 4) == 0) {
            if ((n = queueDisposable.requestFusion(n)) != 0) {
                this.sourceMode = n;
            }
            return n;
        }
        return 0;
    }
}

