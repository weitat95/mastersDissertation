/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

public class DefaultEncodedMemoryCacheParamsSupplier
implements Supplier<MemoryCacheParams> {
    private int getMaxCacheSize() {
        int n = (int)Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);
        if (n < 16777216) {
            return 1048576;
        }
        if (n < 33554432) {
            return 2097152;
        }
        return 4194304;
    }

    @Override
    public MemoryCacheParams get() {
        int n = this.getMaxCacheSize();
        return new MemoryCacheParams(n, Integer.MAX_VALUE, n, Integer.MAX_VALUE, n / 8);
    }
}

