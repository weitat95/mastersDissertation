/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

public interface DisposableContainer {
    public boolean add(Disposable var1);

    public boolean delete(Disposable var1);

    public boolean remove(Disposable var1);
}

