/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.imagepipeline.memory.BasePool;

public interface PoolStatsTracker {
    public void onAlloc(int var1);

    public void onFree(int var1);

    public void onHardCapReached();

    public void onSoftCapReached();

    public void onValueRelease(int var1);

    public void onValueReuse(int var1);

    public void setBasePool(BasePool var1);
}

