/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.imagepipeline.cache.CountingMemoryCache;

public class NativeMemoryCacheTrimStrategy
implements CountingMemoryCache.CacheTrimStrategy {
    @Override
    public double getTrimRatio(MemoryTrimType memoryTrimType) {
        switch (1.$SwitchMap$com$facebook$common$memory$MemoryTrimType[memoryTrimType.ordinal()]) {
            default: {
                FLog.wtf("NativeMemoryCacheTrimStrategy", "unknown trim type: %s", new Object[]{memoryTrimType});
            }
            case 1: {
                return 0.0;
            }
            case 2: 
            case 3: 
            case 4: 
        }
        return 1.0;
    }

}

