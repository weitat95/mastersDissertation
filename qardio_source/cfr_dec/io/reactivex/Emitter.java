/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

public interface Emitter<T> {
    public void onComplete();

    public void onError(Throwable var1);

    public void onNext(T var1);
}

