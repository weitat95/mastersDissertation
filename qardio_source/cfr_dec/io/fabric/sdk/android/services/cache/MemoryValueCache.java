/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.cache;

import android.content.Context;
import io.fabric.sdk.android.services.cache.AbstractValueCache;
import io.fabric.sdk.android.services.cache.ValueCache;

public class MemoryValueCache<T>
extends AbstractValueCache<T> {
    private T value;

    public MemoryValueCache() {
        this(null);
    }

    public MemoryValueCache(ValueCache<T> valueCache) {
        super(valueCache);
    }

    @Override
    protected void cacheValue(Context context, T t) {
        this.value = t;
    }

    @Override
    protected T getCached(Context context) {
        return this.value;
    }
}

