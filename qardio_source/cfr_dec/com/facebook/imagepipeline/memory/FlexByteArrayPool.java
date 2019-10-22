/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.memory.Bucket;
import com.facebook.imagepipeline.memory.GenericByteArrayPool;
import com.facebook.imagepipeline.memory.NoOpPoolStatsTracker;
import com.facebook.imagepipeline.memory.OOMSoftReferenceBucket;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class FlexByteArrayPool {
    final SoftRefByteArrayPool mDelegatePool;
    private final ResourceReleaser<byte[]> mResourceReleaser;

    /*
     * Enabled aggressive block sorting
     */
    public FlexByteArrayPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams) {
        boolean bl = poolParams.maxNumThreads > 0;
        Preconditions.checkArgument(bl);
        this.mDelegatePool = new SoftRefByteArrayPool(memoryTrimmableRegistry, poolParams, NoOpPoolStatsTracker.getInstance());
        this.mResourceReleaser = new ResourceReleaser<byte[]>(){

            @Override
            public void release(byte[] arrby) {
                FlexByteArrayPool.this.release(arrby);
            }
        };
    }

    public CloseableReference<byte[]> get(int n) {
        return CloseableReference.of(this.mDelegatePool.get(n), this.mResourceReleaser);
    }

    public void release(byte[] arrby) {
        this.mDelegatePool.release(arrby);
    }

    static class SoftRefByteArrayPool
    extends GenericByteArrayPool {
        public SoftRefByteArrayPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
            super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        }

        @Override
        Bucket<byte[]> newBucket(int n) {
            return new OOMSoftReferenceBucket<byte[]>(this.getSizeInBytes(n), this.mPoolParams.maxNumThreads, 0);
        }
    }

}

