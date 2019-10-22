/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import java.io.File;

public class DiskLruCacheFactory
implements DiskCache.Factory {
    private final CacheDirectoryGetter cacheDirectoryGetter;
    private final int diskCacheSize;

    public DiskLruCacheFactory(CacheDirectoryGetter cacheDirectoryGetter, int n) {
        this.diskCacheSize = n;
        this.cacheDirectoryGetter = cacheDirectoryGetter;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DiskCache build() {
        File file = this.cacheDirectoryGetter.getCacheDirectory();
        if (!(file != null && (file.mkdirs() || file.exists() && file.isDirectory()))) {
            return null;
        }
        return DiskLruCacheWrapper.get(file, this.diskCacheSize);
    }

    public static interface CacheDirectoryGetter {
        public File getCacheDirectory();
    }

}

