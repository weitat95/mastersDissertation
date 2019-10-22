/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.QueueDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BasicIntQueueDisposable<T>
extends AtomicInteger
implements QueueDisposable<T> {
    @Override
    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called");
    }
}

