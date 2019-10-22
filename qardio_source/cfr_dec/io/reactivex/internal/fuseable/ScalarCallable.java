/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import java.util.concurrent.Callable;

public interface ScalarCallable<T>
extends Callable<T> {
    @Override
    public T call();
}

