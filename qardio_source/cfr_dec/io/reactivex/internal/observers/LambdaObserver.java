/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class LambdaObserver<T>
extends AtomicReference<Disposable>
implements Observer<T>,
Disposable {
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final Consumer<? super Disposable> onSubscribe;

    public LambdaObserver(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Consumer<? super Disposable> consumer3) {
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onSubscribe = consumer3;
    }

    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override
    public boolean isDisposed() {
        return this.get() == DisposableHelper.DISPOSED;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onComplete() {
        if (this.isDisposed()) return;
        this.lazySet(DisposableHelper.DISPOSED);
        try {
            this.onComplete.run();
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            RxJavaPlugins.onError(throwable);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onError(Throwable throwable) {
        if (this.isDisposed()) return;
        this.lazySet(DisposableHelper.DISPOSED);
        try {
            this.onError.accept(throwable);
            return;
        }
        catch (Throwable throwable2) {
            Exceptions.throwIfFatal(throwable2);
            RxJavaPlugins.onError(new CompositeException(throwable, throwable2));
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onNext(T t) {
        if (this.isDisposed()) return;
        try {
            this.onNext.accept(t);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            ((Disposable)this.get()).dispose();
            this.onError(throwable);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onSubscribe(Disposable disposable) {
        if (!DisposableHelper.setOnce(this, disposable)) return;
        try {
            this.onSubscribe.accept(this);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            disposable.dispose();
            this.onError(throwable);
            return;
        }
    }
}

