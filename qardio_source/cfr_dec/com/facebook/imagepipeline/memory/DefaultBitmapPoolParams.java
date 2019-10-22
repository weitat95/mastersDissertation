/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 */
package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.imagepipeline.memory.PoolParams;

public class DefaultBitmapPoolParams {
    private static final SparseIntArray DEFAULT_BUCKETS = new SparseIntArray(0);

    public static PoolParams get() {
        return new PoolParams(0, DefaultBitmapPoolParams.getMaxSizeHardCap(), DEFAULT_BUCKETS);
    }

    private static int getMaxSizeHardCap() {
        int n = (int)Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);
        if (n > 16777216) {
            return n / 4 * 3;
        }
        return n / 2;
    }
}

