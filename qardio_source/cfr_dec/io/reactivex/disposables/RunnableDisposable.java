/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.disposables;

import io.reactivex.disposables.ReferenceDisposable;

final class RunnableDisposable
extends ReferenceDisposable<Runnable> {
    RunnableDisposable(Runnable runnable) {
        super(runnable);
    }

    @Override
    protected void onDisposed(Runnable runnable) {
        runnable.run();
    }

    @Override
    public String toString() {
        return "RunnableDisposable(disposed=" + this.isDisposed() + ", " + this.get() + ")";
    }
}

