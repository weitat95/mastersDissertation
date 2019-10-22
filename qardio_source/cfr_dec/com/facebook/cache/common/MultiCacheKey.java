/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheKey;
import java.util.List;

public class MultiCacheKey
implements CacheKey {
    final List<CacheKey> mCacheKeys;

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof MultiCacheKey) {
            object = (MultiCacheKey)object;
            return this.mCacheKeys.equals(((MultiCacheKey)object).mCacheKeys);
        }
        return false;
    }

    public List<CacheKey> getCacheKeys() {
        return this.mCacheKeys;
    }

    public int hashCode() {
        return this.mCacheKeys.hashCode();
    }

    @Override
    public String toString() {
        return "MultiCacheKey:" + this.mCacheKeys.toString();
    }
}

