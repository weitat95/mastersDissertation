/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheErrorLogger;
import javax.annotation.Nullable;

public class NoOpCacheErrorLogger
implements CacheErrorLogger {
    private static NoOpCacheErrorLogger sInstance = null;

    private NoOpCacheErrorLogger() {
    }

    public static NoOpCacheErrorLogger getInstance() {
        synchronized (NoOpCacheErrorLogger.class) {
            if (sInstance == null) {
                sInstance = new NoOpCacheErrorLogger();
            }
            NoOpCacheErrorLogger noOpCacheErrorLogger = sInstance;
            return noOpCacheErrorLogger;
        }
    }

    @Override
    public void logError(CacheErrorLogger.CacheErrorCategory cacheErrorCategory, Class<?> class_, String string2, @Nullable Throwable throwable) {
    }
}

