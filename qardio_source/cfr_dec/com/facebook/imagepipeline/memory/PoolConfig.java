/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.Immutable
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.DefaultBitmapPoolParams;
import com.facebook.imagepipeline.memory.DefaultByteArrayPoolParams;
import com.facebook.imagepipeline.memory.DefaultFlexByteArrayPoolParams;
import com.facebook.imagepipeline.memory.DefaultNativeMemoryChunkPoolParams;
import com.facebook.imagepipeline.memory.NoOpPoolStatsTracker;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.Immutable;

@Immutable
public class PoolConfig {
    private final PoolParams mBitmapPoolParams;
    private final PoolStatsTracker mBitmapPoolStatsTracker;
    private final PoolParams mFlexByteArrayPoolParams;
    private final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    private final PoolParams mNativeMemoryChunkPoolParams;
    private final PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
    private final PoolParams mSmallByteArrayPoolParams;
    private final PoolStatsTracker mSmallByteArrayPoolStatsTracker;

    /*
     * Enabled aggressive block sorting
     */
    private PoolConfig(Builder object) {
        Object object2 = ((Builder)object).mBitmapPoolParams == null ? DefaultBitmapPoolParams.get() : ((Builder)object).mBitmapPoolParams;
        this.mBitmapPoolParams = object2;
        object2 = ((Builder)object).mBitmapPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : ((Builder)object).mBitmapPoolStatsTracker;
        this.mBitmapPoolStatsTracker = object2;
        object2 = ((Builder)object).mFlexByteArrayPoolParams == null ? DefaultFlexByteArrayPoolParams.get() : ((Builder)object).mFlexByteArrayPoolParams;
        this.mFlexByteArrayPoolParams = object2;
        object2 = ((Builder)object).mMemoryTrimmableRegistry == null ? NoOpMemoryTrimmableRegistry.getInstance() : ((Builder)object).mMemoryTrimmableRegistry;
        this.mMemoryTrimmableRegistry = object2;
        object2 = ((Builder)object).mNativeMemoryChunkPoolParams == null ? DefaultNativeMemoryChunkPoolParams.get() : ((Builder)object).mNativeMemoryChunkPoolParams;
        this.mNativeMemoryChunkPoolParams = object2;
        object2 = ((Builder)object).mNativeMemoryChunkPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : ((Builder)object).mNativeMemoryChunkPoolStatsTracker;
        this.mNativeMemoryChunkPoolStatsTracker = object2;
        object2 = ((Builder)object).mSmallByteArrayPoolParams == null ? DefaultByteArrayPoolParams.get() : ((Builder)object).mSmallByteArrayPoolParams;
        this.mSmallByteArrayPoolParams = object2;
        object = ((Builder)object).mSmallByteArrayPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : ((Builder)object).mSmallByteArrayPoolStatsTracker;
        this.mSmallByteArrayPoolStatsTracker = object;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public PoolParams getBitmapPoolParams() {
        return this.mBitmapPoolParams;
    }

    public PoolStatsTracker getBitmapPoolStatsTracker() {
        return this.mBitmapPoolStatsTracker;
    }

    public PoolParams getFlexByteArrayPoolParams() {
        return this.mFlexByteArrayPoolParams;
    }

    public MemoryTrimmableRegistry getMemoryTrimmableRegistry() {
        return this.mMemoryTrimmableRegistry;
    }

    public PoolParams getNativeMemoryChunkPoolParams() {
        return this.mNativeMemoryChunkPoolParams;
    }

    public PoolStatsTracker getNativeMemoryChunkPoolStatsTracker() {
        return this.mNativeMemoryChunkPoolStatsTracker;
    }

    public PoolParams getSmallByteArrayPoolParams() {
        return this.mSmallByteArrayPoolParams;
    }

    public PoolStatsTracker getSmallByteArrayPoolStatsTracker() {
        return this.mSmallByteArrayPoolStatsTracker;
    }

    public static class Builder {
        private PoolParams mBitmapPoolParams;
        private PoolStatsTracker mBitmapPoolStatsTracker;
        private PoolParams mFlexByteArrayPoolParams;
        private MemoryTrimmableRegistry mMemoryTrimmableRegistry;
        private PoolParams mNativeMemoryChunkPoolParams;
        private PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
        private PoolParams mSmallByteArrayPoolParams;
        private PoolStatsTracker mSmallByteArrayPoolStatsTracker;

        private Builder() {
        }

        public PoolConfig build() {
            return new PoolConfig(this);
        }
    }

}

