/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.core;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;

public interface DiskStorageFactory {
    public DiskStorage get(DiskCacheConfig var1);
}

