/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.nostra13.universalimageloader.core;

import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import java.util.concurrent.Executor;

public final class ImageLoaderConfiguration {

    public static class Builder {
        public static final QueueProcessingType DEFAULT_TASK_PROCESSING_TYPE = QueueProcessingType.FIFO;
        private Context context;
        private boolean customExecutor = false;
        private boolean customExecutorForCachedImages = false;
        private DisplayImageOptions defaultDisplayImageOptions = null;
        private boolean denyCacheImageMultipleSizesInMemory = false;
        private DiskCache diskCache = null;
        private int diskCacheFileCount = 0;
        private FileNameGenerator diskCacheFileNameGenerator = null;
        private long diskCacheSize = 0L;
        private ImageDownloader downloader = null;
        private int maxImageHeightForDiskCache = 0;
        private int maxImageHeightForMemoryCache = 0;
        private int maxImageWidthForDiskCache = 0;
        private int maxImageWidthForMemoryCache = 0;
        private MemoryCache memoryCache = null;
        private int memoryCacheSize = 0;
        private BitmapProcessor processorForDiskCache = null;
        private Executor taskExecutor = null;
        private Executor taskExecutorForCachedImages = null;
        private QueueProcessingType tasksProcessingType = DEFAULT_TASK_PROCESSING_TYPE;
        private int threadPoolSize = 3;
        private int threadPriority = 3;
        private boolean writeLogs = false;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }
    }

}

