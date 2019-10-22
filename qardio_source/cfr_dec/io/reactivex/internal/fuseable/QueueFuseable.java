/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.internal.fuseable.SimpleQueue;

public interface QueueFuseable<T>
extends SimpleQueue<T> {
    public int requestFusion(int var1);
}

