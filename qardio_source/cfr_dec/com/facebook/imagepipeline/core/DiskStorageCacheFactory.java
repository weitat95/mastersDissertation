/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.imagepipeline.core;

import android.content.Context;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.cache.disk.EntryEvictionComparatorSupplier;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.imagepipeline.core.DiskStorageFactory;
import com.facebook.imagepipeline.core.FileCacheFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskStorageCacheFactory
implements FileCacheFactory {
    private DiskStorageFactory mDiskStorageFactory;

    public DiskStorageCacheFactory(DiskStorageFactory diskStorageFactory) {
        this.mDiskStorageFactory = diskStorageFactory;
    }

    public static DiskStorageCache buildDiskStorageCache(DiskCacheConfig diskCacheConfig, DiskStorage diskStorage) {
        return DiskStorageCacheFactory.buildDiskStorageCache(diskCacheConfig, diskStorage, Executors.newSingleThreadExecutor());
    }

    public static DiskStorageCache buildDiskStorageCache(DiskCacheConfig diskCacheConfig, DiskStorage diskStorage, Executor executor) {
        DiskStorageCache.Params params = new DiskStorageCache.Params(diskCacheConfig.getMinimumSizeLimit(), diskCacheConfig.getLowDiskSpaceSizeLimit(), diskCacheConfig.getDefaultSizeLimit());
        return new DiskStorageCache(diskStorage, diskCacheConfig.getEntryEvictionComparatorSupplier(), params, diskCacheConfig.getCacheEventListener(), diskCacheConfig.getCacheErrorLogger(), diskCacheConfig.getDiskTrimmableRegistry(), diskCacheConfig.getContext(), executor, diskCacheConfig.getIndexPopulateAtStartupEnabled());
    }

    @Override
    public FileCache get(DiskCacheConfig diskCacheConfig) {
        return DiskStorageCacheFactory.buildDiskStorageCache(diskCacheConfig, this.mDiskStorageFactory.get(diskCacheConfig));
    }
}

