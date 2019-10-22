/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import javax.annotation.Nullable;

public class PoolParams {
    public final SparseIntArray bucketSizes;
    public final int maxBucketSize;
    public final int maxNumThreads;
    public final int maxSizeHardCap;
    public final int maxSizeSoftCap;
    public final int minBucketSize;

    public PoolParams(int n, int n2, @Nullable SparseIntArray sparseIntArray) {
        this(n, n2, sparseIntArray, 0, Integer.MAX_VALUE, -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PoolParams(int n, int n2, @Nullable SparseIntArray sparseIntArray, int n3, int n4, int n5) {
        boolean bl = n >= 0 && n2 >= n;
        Preconditions.checkState(bl);
        this.maxSizeSoftCap = n;
        this.maxSizeHardCap = n2;
        this.bucketSizes = sparseIntArray;
        this.minBucketSize = n3;
        this.maxBucketSize = n4;
        this.maxNumThreads = n5;
    }
}

