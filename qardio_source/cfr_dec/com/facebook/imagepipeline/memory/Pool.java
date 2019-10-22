/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.ResourceReleaser;

public interface Pool<V>
extends MemoryTrimmable,
ResourceReleaser<V> {
    public V get(int var1);

    @Override
    public void release(V var1);
}

