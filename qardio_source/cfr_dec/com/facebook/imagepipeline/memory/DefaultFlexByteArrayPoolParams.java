/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 */
package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.imagepipeline.memory.PoolParams;

public class DefaultFlexByteArrayPoolParams {
    public static final int DEFAULT_MAX_NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static SparseIntArray generateBuckets(int n, int n2, int n3) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        while (n <= n2) {
            sparseIntArray.put(n, n3);
            n *= 2;
        }
        return sparseIntArray;
    }

    public static PoolParams get() {
        return new PoolParams(4194304, DEFAULT_MAX_NUM_THREADS * 4194304, DefaultFlexByteArrayPoolParams.generateBuckets(131072, 4194304, DEFAULT_MAX_NUM_THREADS), 131072, 4194304, DEFAULT_MAX_NUM_THREADS);
    }
}

