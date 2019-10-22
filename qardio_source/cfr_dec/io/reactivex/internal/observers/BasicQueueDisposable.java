/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.QueueDisposable;

public abstract class BasicQueueDisposable<T>
implements QueueDisposable<T> {
    @Override
    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called");
    }
}

