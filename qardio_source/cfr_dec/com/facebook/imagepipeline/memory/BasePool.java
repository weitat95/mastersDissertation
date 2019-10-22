/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.Throwables;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.Bucket;
import com.facebook.imagepipeline.memory.Pool;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

public abstract class BasePool<V>
implements Pool<V> {
    private final Class<?> TAG = this.getClass();
    private boolean mAllowNewBuckets;
    final SparseArray<Bucket<V>> mBuckets;
    @GuardedBy(value="this")
    final Counter mFree;
    final Set<V> mInUseValues;
    final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    final PoolParams mPoolParams;
    private final PoolStatsTracker mPoolStatsTracker;
    @GuardedBy(value="this")
    final Counter mUsed;

    public BasePool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        this.mMemoryTrimmableRegistry = Preconditions.checkNotNull(memoryTrimmableRegistry);
        this.mPoolParams = Preconditions.checkNotNull(poolParams);
        this.mPoolStatsTracker = Preconditions.checkNotNull(poolStatsTracker);
        this.mBuckets = new SparseArray();
        this.initBuckets(new SparseIntArray(0));
        this.mInUseValues = Sets.newIdentityHashSet();
        this.mFree = new Counter();
        this.mUsed = new Counter();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ensurePoolSizeInvariant() {
        synchronized (this) {
            boolean bl = !this.isMaxSizeSoftCapExceeded() || this.mFree.mNumBytes == 0;
            Preconditions.checkState(bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initBuckets(SparseIntArray sparseIntArray) {
        synchronized (this) {
            Preconditions.checkNotNull(sparseIntArray);
            this.mBuckets.clear();
            SparseIntArray sparseIntArray2 = this.mPoolParams.bucketSizes;
            if (sparseIntArray2 != null) {
                for (int i = 0; i < sparseIntArray2.size(); ++i) {
                    int n = sparseIntArray2.keyAt(i);
                    int n2 = sparseIntArray2.valueAt(i);
                    int n3 = sparseIntArray.get(n, 0);
                    this.mBuckets.put(n, new Bucket(this.getSizeInBytes(n), n2, n3));
                }
                this.mAllowNewBuckets = false;
            } else {
                this.mAllowNewBuckets = true;
            }
            return;
        }
    }

    @SuppressLint(value={"InvalidAccessToGuardedField"})
    private void logStats() {
        if (FLog.isLoggable(2)) {
            FLog.v(this.TAG, "Used = (%d, %d); Free = (%d, %d)", (Object)this.mUsed.mCount, (Object)this.mUsed.mNumBytes, (Object)this.mFree.mCount, (Object)this.mFree.mNumBytes);
        }
    }

    protected abstract V alloc(int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean canAllocate(int n) {
        boolean bl = false;
        synchronized (this) {
            int n2 = this.mPoolParams.maxSizeHardCap;
            if (n > n2 - this.mUsed.mNumBytes) {
                this.mPoolStatsTracker.onHardCapReached();
            } else {
                int n3 = this.mPoolParams.maxSizeSoftCap;
                if (n > n3 - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
                    this.trimToSize(n3 - n);
                }
                if (n <= n2 - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) return true;
                this.mPoolStatsTracker.onHardCapReached();
            }
            return bl;
        }
    }

    protected abstract void free(V var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V get(int n) {
        V v;
        Bucket<V> bucket;
        int n2;
        this.ensurePoolSizeInvariant();
        n = this.getBucketedSize(n);
        synchronized (this) {
            bucket = this.getBucket(n);
            if (bucket != null && (v = bucket.get()) != null) {
                Preconditions.checkState(this.mInUseValues.add(v));
                n = this.getBucketedSizeForValue(v);
                int n3 = this.getSizeInBytes(n);
                this.mUsed.increment(n3);
                this.mFree.decrement(n3);
                this.mPoolStatsTracker.onValueReuse(n3);
                this.logStats();
                if (FLog.isLoggable(2)) {
                    FLog.v(this.TAG, "get (reuse) (object, size) = (%x, %s)", (Object)System.identityHashCode(v), (Object)n);
                }
                return v;
            }
            n2 = this.getSizeInBytes(n);
            if (!this.canAllocate(n2)) {
                throw new PoolSizeViolationException(this.mPoolParams.maxSizeHardCap, this.mUsed.mNumBytes, this.mFree.mNumBytes, n2);
            }
            this.mUsed.increment(n2);
            if (bucket != null) {
                bucket.incrementInUseCount();
            }
        }
        bucket = null;
        try {
            v = this.alloc(n);
            bucket = v;
        }
        catch (Throwable throwable) {
            synchronized (this) {
                this.mUsed.decrement(n2);
                Bucket<V> bucket2 = this.getBucket(n);
                if (bucket2 != null) {
                    bucket2.decrementInUseCount();
                }
            }
            Throwables.propagateIfPossible(throwable);
        }
        synchronized (this) {
            Preconditions.checkState(this.mInUseValues.add(bucket));
            this.trimToSoftCap();
            this.mPoolStatsTracker.onAlloc(n2);
            this.logStats();
            if (FLog.isLoggable(2)) {
                FLog.v(this.TAG, "get (alloc) (object, size) = (%x, %s)", (Object)System.identityHashCode(bucket), (Object)n);
            }
            return (V)bucket;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Bucket<V> getBucket(int n) {
        synchronized (this) {
            Bucket<V> bucket;
            block9: {
                block8: {
                    bucket = (Bucket<V>)this.mBuckets.get(n);
                    if (bucket != null) break block8;
                    boolean bl = this.mAllowNewBuckets;
                    if (bl) break block9;
                }
                do {
                    return bucket;
                    break;
                } while (true);
            }
            if (FLog.isLoggable(2)) {
                FLog.v(this.TAG, "creating new bucket %s", (Object)n);
            }
            bucket = this.newBucket(n);
            this.mBuckets.put(n, bucket);
            return bucket;
        }
    }

    protected abstract int getBucketedSize(int var1);

    protected abstract int getBucketedSizeForValue(V var1);

    protected abstract int getSizeInBytes(int var1);

    protected void initialize() {
        this.mMemoryTrimmableRegistry.registerMemoryTrimmable(this);
        this.mPoolStatsTracker.setBasePool(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isMaxSizeSoftCapExceeded() {
        synchronized (this) {
            if (this.mUsed.mNumBytes + this.mFree.mNumBytes <= this.mPoolParams.maxSizeSoftCap) return false;
            boolean bl = true;
            if (!bl) return bl;
            this.mPoolStatsTracker.onSoftCapReached();
            return bl;
        }
    }

    protected boolean isReusable(V v) {
        Preconditions.checkNotNull(v);
        return true;
    }

    Bucket<V> newBucket(int n) {
        return new Bucket<V>(this.getSizeInBytes(n), Integer.MAX_VALUE, 0);
    }

    protected void onParamsChanged() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void release(V v) {
        Preconditions.checkNotNull(v);
        int n = this.getBucketedSizeForValue(v);
        int n2 = this.getSizeInBytes(n);
        synchronized (this) {
            Bucket<V> bucket = this.getBucket(n);
            if (!this.mInUseValues.remove(v)) {
                FLog.e(this.TAG, "release (free, value unrecognized) (object, size) = (%x, %s)", System.identityHashCode(v), n);
                this.free(v);
                this.mPoolStatsTracker.onFree(n2);
            } else if (bucket == null || bucket.isMaxLengthExceeded() || this.isMaxSizeSoftCapExceeded() || !this.isReusable(v)) {
                if (bucket != null) {
                    bucket.decrementInUseCount();
                }
                if (FLog.isLoggable(2)) {
                    FLog.v(this.TAG, "release (free) (object, size) = (%x, %s)", (Object)System.identityHashCode(v), (Object)n);
                }
                this.free(v);
                this.mUsed.decrement(n2);
                this.mPoolStatsTracker.onFree(n2);
            } else {
                bucket.release(v);
                this.mFree.increment(n2);
                this.mUsed.decrement(n2);
                this.mPoolStatsTracker.onValueRelease(n2);
                if (FLog.isLoggable(2)) {
                    FLog.v(this.TAG, "release (reuse) (object, size) = (%x, %s)", (Object)System.identityHashCode(v), (Object)n);
                }
            }
            this.logStats();
            return;
        }
    }

    @Override
    public void trim(MemoryTrimType memoryTrimType) {
        this.trimToNothing();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void trimToNothing() {
        Bucket<V> bucket;
        int n;
        ArrayList<Bucket> arrayList = new ArrayList<Bucket>(this.mBuckets.size());
        Object object = new SparseIntArray();
        synchronized (this) {
            for (n = 0; n < this.mBuckets.size(); ++n) {
                bucket = (Bucket)this.mBuckets.valueAt(n);
                if (bucket.getFreeListSize() > 0) {
                    arrayList.add(bucket);
                }
                object.put(this.mBuckets.keyAt(n), bucket.getInUseCount());
            }
            this.initBuckets((SparseIntArray)object);
            this.mFree.reset();
            this.logStats();
        }
        this.onParamsChanged();
        n = 0;
        block4 : while (n < arrayList.size()) {
            object = (Bucket)arrayList.get(n);
            do {
                if ((bucket = ((Bucket)object).pop()) == null) {
                    ++n;
                    continue block4;
                }
                this.free(bucket);
            } while (true);
            break;
        }
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void trimToSize(int var1_1) {
        // MONITORENTER : this
        var3_2 = Math.min(this.mUsed.mNumBytes + this.mFree.mNumBytes - var1_1, this.mFree.mNumBytes);
        if (var3_2 <= 0) ** GOTO lbl12
        if (FLog.isLoggable(2)) {
            FLog.v(this.TAG, "trimToSize: TargetSize = %d; Initial Size = %d; Bytes to free = %d", (Object)var1_1, (Object)(this.mUsed.mNumBytes + this.mFree.mNumBytes), (Object)var3_2);
        }
        this.logStats();
        var2_3 = 0;
        do {
            block10: {
                block11: {
                    if (var2_3 < this.mBuckets.size() && var3_2 > 0) break block10;
                    this.logStats();
                    if (FLog.isLoggable(2)) break block11;
lbl12:
                    // 2 sources
                    // MONITOREXIT : this
                    return;
                }
                FLog.v(this.TAG, "trimToSize: TargetSize = %d; Final Size = %d", (Object)var1_1, (Object)(this.mUsed.mNumBytes + this.mFree.mNumBytes));
                return;
            }
            var4_4 = (Bucket)this.mBuckets.valueAt(var2_3);
            while (var3_2 > 0 && (var5_5 = var4_4.pop()) != null) {
                this.free(var5_5);
                var3_2 -= var4_4.mItemSize;
                this.mFree.decrement(var4_4.mItemSize);
            }
            ++var2_3;
        } while (true);
    }

    void trimToSoftCap() {
        synchronized (this) {
            if (this.isMaxSizeSoftCapExceeded()) {
                this.trimToSize(this.mPoolParams.maxSizeSoftCap);
            }
            return;
        }
    }

    @NotThreadSafe
    static class Counter {
        int mCount;
        int mNumBytes;

        Counter() {
        }

        public void decrement(int n) {
            if (this.mNumBytes >= n && this.mCount > 0) {
                --this.mCount;
                this.mNumBytes -= n;
                return;
            }
            FLog.wtf("com.facebook.imagepipeline.memory.BasePool.Counter", "Unexpected decrement of %d. Current numBytes = %d, count = %d", n, this.mNumBytes, this.mCount);
        }

        public void increment(int n) {
            ++this.mCount;
            this.mNumBytes += n;
        }

        public void reset() {
            this.mCount = 0;
            this.mNumBytes = 0;
        }
    }

    public static class InvalidSizeException
    extends RuntimeException {
        public InvalidSizeException(Object object) {
            super("Invalid size: " + object.toString());
        }
    }

    public static class PoolSizeViolationException
    extends RuntimeException {
        public PoolSizeViolationException(int n, int n2, int n3, int n4) {
            super("Pool hard cap violation? Hard cap = " + n + " Used size = " + n2 + " Free size = " + n3 + " Request size = " + n4);
        }
    }

}

