/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  javax.annotation.Nullable
 */
package com.facebook.cache.disk;

import android.content.Context;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.NoOpCacheErrorLogger;
import com.facebook.cache.common.NoOpCacheEventListener;
import com.facebook.cache.disk.DefaultEntryEvictionComparatorSupplier;
import com.facebook.cache.disk.EntryEvictionComparatorSupplier;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.Suppliers;
import java.io.File;
import javax.annotation.Nullable;

public class DiskCacheConfig {
    private final String mBaseDirectoryName;
    private final Supplier<File> mBaseDirectoryPathSupplier;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private final Context mContext;
    private final long mDefaultSizeLimit;
    private final DiskTrimmableRegistry mDiskTrimmableRegistry;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final boolean mIndexPopulateAtStartupEnabled;
    private final long mLowDiskSpaceSizeLimit;
    private final long mMinimumSizeLimit;
    private final int mVersion;

    /*
     * Enabled aggressive block sorting
     */
    private DiskCacheConfig(Builder builder) {
        this.mVersion = builder.mVersion;
        this.mBaseDirectoryName = Preconditions.checkNotNull(builder.mBaseDirectoryName);
        this.mBaseDirectoryPathSupplier = Preconditions.checkNotNull(builder.mBaseDirectoryPathSupplier);
        this.mDefaultSizeLimit = builder.mMaxCacheSize;
        this.mLowDiskSpaceSizeLimit = builder.mMaxCacheSizeOnLowDiskSpace;
        this.mMinimumSizeLimit = builder.mMaxCacheSizeOnVeryLowDiskSpace;
        this.mEntryEvictionComparatorSupplier = Preconditions.checkNotNull(builder.mEntryEvictionComparatorSupplier);
        Object object = builder.mCacheErrorLogger == null ? NoOpCacheErrorLogger.getInstance() : builder.mCacheErrorLogger;
        this.mCacheErrorLogger = object;
        object = builder.mCacheEventListener == null ? NoOpCacheEventListener.getInstance() : builder.mCacheEventListener;
        this.mCacheEventListener = object;
        object = builder.mDiskTrimmableRegistry == null ? NoOpDiskTrimmableRegistry.getInstance() : builder.mDiskTrimmableRegistry;
        this.mDiskTrimmableRegistry = object;
        this.mContext = builder.mContext;
        this.mIndexPopulateAtStartupEnabled = builder.mIndexPopulateAtStartupEnabled;
    }

    public static Builder newBuilder(@Nullable Context context) {
        return new Builder(context);
    }

    public String getBaseDirectoryName() {
        return this.mBaseDirectoryName;
    }

    public Supplier<File> getBaseDirectoryPathSupplier() {
        return this.mBaseDirectoryPathSupplier;
    }

    public CacheErrorLogger getCacheErrorLogger() {
        return this.mCacheErrorLogger;
    }

    public CacheEventListener getCacheEventListener() {
        return this.mCacheEventListener;
    }

    public Context getContext() {
        return this.mContext;
    }

    public long getDefaultSizeLimit() {
        return this.mDefaultSizeLimit;
    }

    public DiskTrimmableRegistry getDiskTrimmableRegistry() {
        return this.mDiskTrimmableRegistry;
    }

    public EntryEvictionComparatorSupplier getEntryEvictionComparatorSupplier() {
        return this.mEntryEvictionComparatorSupplier;
    }

    public boolean getIndexPopulateAtStartupEnabled() {
        return this.mIndexPopulateAtStartupEnabled;
    }

    public long getLowDiskSpaceSizeLimit() {
        return this.mLowDiskSpaceSizeLimit;
    }

    public long getMinimumSizeLimit() {
        return this.mMinimumSizeLimit;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public static class Builder {
        private String mBaseDirectoryName = "image_cache";
        private Supplier<File> mBaseDirectoryPathSupplier;
        private CacheErrorLogger mCacheErrorLogger;
        private CacheEventListener mCacheEventListener;
        @Nullable
        private final Context mContext;
        private DiskTrimmableRegistry mDiskTrimmableRegistry;
        private EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier = new DefaultEntryEvictionComparatorSupplier();
        private boolean mIndexPopulateAtStartupEnabled;
        private long mMaxCacheSize = 41943040L;
        private long mMaxCacheSizeOnLowDiskSpace = 0xA00000L;
        private long mMaxCacheSizeOnVeryLowDiskSpace = 0x200000L;
        private int mVersion = 1;

        private Builder(@Nullable Context context) {
            this.mContext = context;
        }

        /*
         * Enabled aggressive block sorting
         */
        public DiskCacheConfig build() {
            boolean bl = this.mBaseDirectoryPathSupplier != null || this.mContext != null;
            Preconditions.checkState(bl, "Either a non-null context or a base directory path or supplier must be provided.");
            if (this.mBaseDirectoryPathSupplier == null && this.mContext != null) {
                this.mBaseDirectoryPathSupplier = new Supplier<File>(){

                    @Override
                    public File get() {
                        return Builder.this.mContext.getApplicationContext().getCacheDir();
                    }
                };
            }
            return new DiskCacheConfig(this);
        }

        public Builder setBaseDirectoryName(String string2) {
            this.mBaseDirectoryName = string2;
            return this;
        }

        public Builder setBaseDirectoryPath(File file) {
            this.mBaseDirectoryPathSupplier = Suppliers.of(file);
            return this;
        }

        public Builder setMaxCacheSize(long l) {
            this.mMaxCacheSize = l;
            return this;
        }

        public Builder setVersion(int n) {
            this.mVersion = n;
            return this;
        }

    }

}

