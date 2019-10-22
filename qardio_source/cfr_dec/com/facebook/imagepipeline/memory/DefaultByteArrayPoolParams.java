/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 */
package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.imagepipeline.memory.PoolParams;

public class DefaultByteArrayPoolParams {
    public static PoolParams get() {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(16384, 5);
        return new PoolParams(81920, 1048576, sparseIntArray);
    }
}

