/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.disk;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import java.io.IOException;

public class SettableCacheEvent
implements CacheEvent {
    private static final Object RECYCLER_LOCK = new Object();
    private static SettableCacheEvent sFirstRecycledEvent;
    private static int sRecycledCount;
    private CacheKey mCacheKey;
    private long mCacheLimit;
    private long mCacheSize;
    private CacheEventListener.EvictionReason mEvictionReason;
    private IOException mException;
    private long mItemSize;
    private SettableCacheEvent mNextRecycledEvent;
    private String mResourceId;

    private SettableCacheEvent() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SettableCacheEvent obtain() {
        Object object = RECYCLER_LOCK;
        synchronized (object) {
            if (sFirstRecycledEvent != null) {
                SettableCacheEvent settableCacheEvent = sFirstRecycledEvent;
                sFirstRecycledEvent = settableCacheEvent.mNextRecycledEvent;
                settableCacheEvent.mNextRecycledEvent = null;
                --sRecycledCount;
                return settableCacheEvent;
            }
            return new SettableCacheEvent();
        }
    }

    private void reset() {
        this.mCacheKey = null;
        this.mResourceId = null;
        this.mItemSize = 0L;
        this.mCacheLimit = 0L;
        this.mCacheSize = 0L;
        this.mException = null;
        this.mEvictionReason = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recycle() {
        Object object = RECYCLER_LOCK;
        synchronized (object) {
            if (sRecycledCount < 5) {
                this.reset();
                ++sRecycledCount;
                if (sFirstRecycledEvent != null) {
                    this.mNextRecycledEvent = sFirstRecycledEvent;
                }
                sFirstRecycledEvent = this;
            }
            return;
        }
    }

    public SettableCacheEvent setCacheKey(CacheKey cacheKey) {
        this.mCacheKey = cacheKey;
        return this;
    }

    public SettableCacheEvent setCacheLimit(long l) {
        this.mCacheLimit = l;
        return this;
    }

    public SettableCacheEvent setCacheSize(long l) {
        this.mCacheSize = l;
        return this;
    }

    public SettableCacheEvent setEvictionReason(CacheEventListener.EvictionReason evictionReason) {
        this.mEvictionReason = evictionReason;
        return this;
    }

    public SettableCacheEvent setException(IOException iOException) {
        this.mException = iOException;
        return this;
    }

    public SettableCacheEvent setItemSize(long l) {
        this.mItemSize = l;
        return this;
    }

    public SettableCacheEvent setResourceId(String string2) {
        this.mResourceId = string2;
        return this;
    }
}

