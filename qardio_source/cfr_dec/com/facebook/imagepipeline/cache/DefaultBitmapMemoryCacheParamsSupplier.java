/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.imagepipeline.cache;

import android.app.ActivityManager;
import android.os.Build;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

public class DefaultBitmapMemoryCacheParamsSupplier
implements Supplier<MemoryCacheParams> {
    private final ActivityManager mActivityManager;

    public DefaultBitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    private int getMaxCacheSize() {
        int n = Math.min(this.mActivityManager.getMemoryClass() * 1048576, Integer.MAX_VALUE);
        if (n < 33554432) {
            return 4194304;
        }
        if (n < 67108864) {
            return 6291456;
        }
        if (Build.VERSION.SDK_INT < 11) {
            return 8388608;
        }
        return n / 4;
    }

    @Override
    public MemoryCacheParams get() {
        return new MemoryCacheParams(this.getMaxCacheSize(), 256, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}

