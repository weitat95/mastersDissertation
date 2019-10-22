/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.imagepipeline.memory.BasePool;
import com.facebook.imagepipeline.memory.PoolStatsTracker;

public class NoOpPoolStatsTracker
implements PoolStatsTracker {
    private static NoOpPoolStatsTracker sInstance = null;

    private NoOpPoolStatsTracker() {
    }

    public static NoOpPoolStatsTracker getInstance() {
        synchronized (NoOpPoolStatsTracker.class) {
            if (sInstance == null) {
                sInstance = new NoOpPoolStatsTracker();
            }
            NoOpPoolStatsTracker noOpPoolStatsTracker = sInstance;
            return noOpPoolStatsTracker;
        }
    }

    @Override
    public void onAlloc(int n) {
    }

    @Override
    public void onFree(int n) {
    }

    @Override
    public void onHardCapReached() {
    }

    @Override
    public void onSoftCapReached() {
    }

    @Override
    public void onValueRelease(int n) {
    }

    @Override
    public void onValueReuse(int n) {
    }

    @Override
    public void setBasePool(BasePool basePool) {
    }
}

