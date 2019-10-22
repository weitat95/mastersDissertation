/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SequentialDisposable
extends AtomicReference<Disposable>
implements Disposable {
    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override
    public boolean isDisposed() {
        return DisposableHelper.isDisposed((Disposable)this.get());
    }

    public boolean replace(Disposable disposable) {
        return DisposableHelper.replace(this, disposable);
    }

    public boolean update(Disposable disposable) {
        return DisposableHelper.set(this, disposable);
    }
}

