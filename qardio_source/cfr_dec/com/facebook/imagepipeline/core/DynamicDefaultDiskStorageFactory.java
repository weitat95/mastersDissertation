/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.core;

import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DynamicDefaultDiskStorage;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.core.DiskStorageFactory;
import java.io.File;

public class DynamicDefaultDiskStorageFactory
implements DiskStorageFactory {
    @Override
    public DiskStorage get(DiskCacheConfig diskCacheConfig) {
        return new DynamicDefaultDiskStorage(diskCacheConfig.getVersion(), diskCacheConfig.getBaseDirectoryPathSupplier(), diskCacheConfig.getBaseDirectoryName(), diskCacheConfig.getCacheErrorLogger());
    }
}

