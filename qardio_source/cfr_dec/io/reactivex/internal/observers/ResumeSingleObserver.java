/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ResumeSingleObserver<T>
implements SingleObserver<T> {
    final SingleObserver<? super T> actual;
    final AtomicReference<Disposable> parent;

    public ResumeSingleObserver(AtomicReference<Disposable> atomicReference, SingleObserver<? super T> singleObserver) {
        this.parent = atomicReference;
        this.actual = singleObserver;
    }

    @Override
    public void onError(Throwable throwable) {
        this.actual.onError(throwable);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        DisposableHelper.replace(this.parent, disposable);
    }

    @Override
    public void onSuccess(T t) {
        this.actual.onSuccess(t);
    }
}

