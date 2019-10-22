/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;

public class NoOpCacheEventListener
implements CacheEventListener {
    private static NoOpCacheEventListener sInstance = null;

    private NoOpCacheEventListener() {
    }

    public static NoOpCacheEventListener getInstance() {
        synchronized (NoOpCacheEventListener.class) {
            if (sInstance == null) {
                sInstance = new NoOpCacheEventListener();
            }
            NoOpCacheEventListener noOpCacheEventListener = sInstance;
            return noOpCacheEventListener;
        }
    }

    @Override
    public void onEviction(CacheEvent cacheEvent) {
    }

    @Override
    public void onHit(CacheEvent cacheEvent) {
    }

    @Override
    public void onMiss(CacheEvent cacheEvent) {
    }

    @Override
    public void onReadException(CacheEvent cacheEvent) {
    }

    @Override
    public void onWriteAttempt(CacheEvent cacheEvent) {
    }

    @Override
    public void onWriteException(CacheEvent cacheEvent) {
    }

    @Override
    public void onWriteSuccess(CacheEvent cacheEvent) {
    }
}

