/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.disposables;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.QueueDisposable;

public enum EmptyDisposable implements QueueDisposable<Object>
{
    INSTANCE,
    NEVER;


    public static void complete(MaybeObserver<?> maybeObserver) {
        maybeObserver.onSubscribe(INSTANCE);
        maybeObserver.onComplete();
    }

    public static void complete(Observer<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable throwable, CompletableObserver completableObserver) {
        completableObserver.onSubscribe(INSTANCE);
        completableObserver.onError(throwable);
    }

    public static void error(Throwable throwable, Observer<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(throwable);
    }

    public static void error(Throwable throwable, SingleObserver<?> singleObserver) {
        singleObserver.onSubscribe(INSTANCE);
        singleObserver.onError(throwable);
    }

    @Override
    public void clear() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isDisposed() {
        return this == INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean offer(Object object) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public Object poll() throws Exception {
        return null;
    }

    @Override
    public int requestFusion(int n) {
        return n & 2;
    }
}

