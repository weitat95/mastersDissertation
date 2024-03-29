/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;

public interface MemoryCache {
    public void clearMemory();

    public Resource<?> put(Key var1, Resource<?> var2);

    public Resource<?> remove(Key var1);

    public void setResourceRemovedListener(ResourceRemovedListener var1);

    public void trimMemory(int var1);

    public static interface ResourceRemovedListener {
        public void onResourceRemoved(Resource<?> var1);
    }

}

