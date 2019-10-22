/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import java.io.File;

public class DiskCacheAdapter
implements DiskCache {
    @Override
    public void delete(Key key) {
    }

    @Override
    public File get(Key key) {
        return null;
    }

    @Override
    public void put(Key key, DiskCache.Writer writer) {
    }
}

