/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.Observable;

public interface FuseToObservable<T> {
    public Observable<T> fuseToObservable();
}

