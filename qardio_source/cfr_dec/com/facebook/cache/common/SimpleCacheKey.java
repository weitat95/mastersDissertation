/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;

public class SimpleCacheKey
implements CacheKey {
    final String mKey;

    public SimpleCacheKey(String string2) {
        this.mKey = Preconditions.checkNotNull(string2);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof SimpleCacheKey) {
            object = (SimpleCacheKey)object;
            return this.mKey.equals(((SimpleCacheKey)object).mKey);
        }
        return false;
    }

    public int hashCode() {
        return this.mKey.hashCode();
    }

    @Override
    public String toString() {
        return this.mKey;
    }
}

