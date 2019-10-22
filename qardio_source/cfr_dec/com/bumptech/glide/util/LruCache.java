/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LruCache<T, Y> {
    private final LinkedHashMap<T, Y> cache = new LinkedHashMap(100, 0.75f, true);
    private int currentSize = 0;
    private final int initialMaxSize;
    private int maxSize;

    public LruCache(int n) {
        this.initialMaxSize = n;
        this.maxSize = n;
    }

    private void evict() {
        this.trimToSize(this.maxSize);
    }

    public void clearMemory() {
        this.trimToSize(0);
    }

    public Y get(T t) {
        return this.cache.get(t);
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    protected int getSize(Y y) {
        return 1;
    }

    protected void onItemEvicted(T t, Y y) {
    }

    public Y put(T object, Y y) {
        if (this.getSize(y) >= this.maxSize) {
            this.onItemEvicted(object, y);
            return null;
        }
        object = this.cache.put(object, y);
        if (y != null) {
            this.currentSize += this.getSize(y);
        }
        if (object != null) {
            this.currentSize -= this.getSize(object);
        }
        this.evict();
        return (Y)object;
    }

    public Y remove(T object) {
        if ((object = this.cache.remove(object)) != null) {
            this.currentSize -= this.getSize(object);
        }
        return (Y)object;
    }

    protected void trimToSize(int n) {
        while (this.currentSize > n) {
            Map.Entry<Object, Object> entry = this.cache.entrySet().iterator().next();
            Y y = entry.getValue();
            this.currentSize -= this.getSize(y);
            entry = entry.getKey();
            this.cache.remove(entry);
            this.onItemEvicted(entry, y);
        }
    }
}

