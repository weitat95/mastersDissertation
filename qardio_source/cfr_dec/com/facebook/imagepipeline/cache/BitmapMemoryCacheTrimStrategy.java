/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.imagepipeline.cache;

import android.os.Build;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.imagepipeline.cache.CountingMemoryCache;

public class BitmapMemoryCacheTrimStrategy
implements CountingMemoryCache.CacheTrimStrategy {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public double getTrimRatio(MemoryTrimType memoryTrimType) {
        switch (1.$SwitchMap$com$facebook$common$memory$MemoryTrimType[memoryTrimType.ordinal()]) {
            default: {
                FLog.wtf("BitmapMemoryCacheTrimStrategy", "unknown trim type: %s", new Object[]{memoryTrimType});
                return 0.0;
            }
            case 1: {
                if (Build.VERSION.SDK_INT < 21) return 0.0;
                return MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio();
            }
            case 2: 
            case 3: 
            case 4: 
        }
        return 1.0;
    }

}

