/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.BasePool;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class NativeMemoryChunkPool
extends BasePool<NativeMemoryChunk> {
    private final int[] mBucketSizes;

    public NativeMemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        memoryTrimmableRegistry = poolParams.bucketSizes;
        this.mBucketSizes = new int[memoryTrimmableRegistry.size()];
        for (int i = 0; i < this.mBucketSizes.length; ++i) {
            this.mBucketSizes[i] = memoryTrimmableRegistry.keyAt(i);
        }
        this.initialize();
    }

    @Override
    protected NativeMemoryChunk alloc(int n) {
        return new NativeMemoryChunk(n);
    }

    @Override
    protected void free(NativeMemoryChunk nativeMemoryChunk) {
        Preconditions.checkNotNull(nativeMemoryChunk);
        nativeMemoryChunk.close();
    }

    @Override
    protected int getBucketedSize(int n) {
        if (n <= 0) {
            throw new BasePool.InvalidSizeException(n);
        }
        for (int n2 : this.mBucketSizes) {
            if (n2 < n) continue;
            return n2;
        }
        return n;
    }

    @Override
    protected int getBucketedSizeForValue(NativeMemoryChunk nativeMemoryChunk) {
        Preconditions.checkNotNull(nativeMemoryChunk);
        return nativeMemoryChunk.getSize();
    }

    public int getMinBufferSize() {
        return this.mBucketSizes[0];
    }

    @Override
    protected int getSizeInBytes(int n) {
        return n;
    }

    @Override
    protected boolean isReusable(NativeMemoryChunk nativeMemoryChunk) {
        Preconditions.checkNotNull(nativeMemoryChunk);
        return !nativeMemoryChunk.isClosed();
    }
}

