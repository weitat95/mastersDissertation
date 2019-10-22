/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheEvent;

public interface CacheEventListener {
    public void onEviction(CacheEvent var1);

    public void onHit(CacheEvent var1);

    public void onMiss(CacheEvent var1);

    public void onReadException(CacheEvent var1);

    public void onWriteAttempt(CacheEvent var1);

    public void onWriteException(CacheEvent var1);

    public void onWriteSuccess(CacheEvent var1);

    public static enum EvictionReason {
        CACHE_FULL,
        CONTENT_STALE,
        USER_FORCED,
        CACHE_MANAGER_TRIMMED;

    }

}

