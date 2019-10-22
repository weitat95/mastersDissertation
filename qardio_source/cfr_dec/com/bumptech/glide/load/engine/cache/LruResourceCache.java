/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.bumptech.glide.load.engine.cache;

import android.annotation.SuppressLint;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.util.LruCache;

public class LruResourceCache
extends LruCache<Key, Resource<?>>
implements MemoryCache {
    private MemoryCache.ResourceRemovedListener listener;

    public LruResourceCache(int n) {
        super(n);
    }

    @Override
    protected int getSize(Resource<?> resource) {
        return resource.getSize();
    }

    @Override
    protected void onItemEvicted(Key key, Resource<?> resource) {
        if (this.listener != null) {
            this.listener.onResourceRemoved(resource);
        }
    }

    @Override
    public void setResourceRemovedListener(MemoryCache.ResourceRemovedListener resourceRemovedListener) {
        this.listener = resourceRemovedListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"InlinedApi"})
    @Override
    public void trimMemory(int n) {
        if (n >= 60) {
            this.clearMemory();
            return;
        } else {
            if (n < 40) return;
            {
                this.trimToSize(this.getCurrentSize() / 2);
                return;
            }
        }
    }
}

