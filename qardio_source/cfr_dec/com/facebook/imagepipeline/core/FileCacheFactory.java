/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.core;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.FileCache;

public interface FileCacheFactory {
    public FileCache get(DiskCacheConfig var1);
}

