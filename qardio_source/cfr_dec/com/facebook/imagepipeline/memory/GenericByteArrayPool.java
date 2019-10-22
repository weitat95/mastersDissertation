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
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class GenericByteArrayPool
extends BasePool<byte[]>
implements ByteArrayPool {
    private final int[] mBucketSizes;

    public GenericByteArrayPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        memoryTrimmableRegistry = poolParams.bucketSizes;
        this.mBucketSizes = new int[memoryTrimmableRegistry.size()];
        for (int i = 0; i < memoryTrimmableRegistry.size(); ++i) {
            this.mBucketSizes[i] = memoryTrimmableRegistry.keyAt(i);
        }
        this.initialize();
    }

    @Override
    protected byte[] alloc(int n) {
        return new byte[n];
    }

    @Override
    protected void free(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
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
    protected int getBucketedSizeForValue(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        return arrby.length;
    }

    @Override
    protected int getSizeInBytes(int n) {
        return n;
    }
}

