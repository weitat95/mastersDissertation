/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

public interface SimpleQueue<T> {
    public void clear();

    public boolean isEmpty();

    public boolean offer(T var1);

    public T poll() throws Exception;
}

