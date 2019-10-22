/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.internal.fuseable.SimpleQueue;

public interface SimplePlainQueue<T>
extends SimpleQueue<T> {
    @Override
    public T poll();
}

