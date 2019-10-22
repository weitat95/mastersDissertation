/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ConsumerSingleObserver<T>
extends AtomicReference<Disposable>
implements SingleObserver<T>,
Disposable {
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onSuccess;

    public ConsumerSingleObserver(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        this.onSuccess = consumer;
        this.onError = consumer2;
    }

    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override
    public boolean isDisposed() {
        return this.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public void onError(Throwable throwable) {
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

    @Override
    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this, disposable);
    }

    @Override
    public void onSuccess(T t) {
        this.lazySet(DisposableHelper.DISPOSED);
        try {
            this.onSuccess.accept(t);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            RxJavaPlugins.onError(throwable);
            return;
        }
    }
}

