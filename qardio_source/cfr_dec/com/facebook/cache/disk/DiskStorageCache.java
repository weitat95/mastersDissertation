/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.cache.disk;

import android.content.Context;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.CacheKeyUtil;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.EntryEvictionComparator;
import com.facebook.cache.disk.EntryEvictionComparatorSupplier;
import com.facebook.cache.disk.FileCache;
import com.facebook.cache.disk.SettableCacheEvent;
import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.logging.FLog;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class DiskStorageCache
implements FileCache,
DiskTrimmable {
    private static final long FILECACHE_SIZE_UPDATE_PERIOD_MS;
    private static final long FUTURE_TIMESTAMP_THRESHOLD_MS;
    private static final Class<?> TAG;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    @GuardedBy(value="mLock")
    private long mCacheSizeLastUpdateTime;
    private long mCacheSizeLimit;
    private final long mCacheSizeLimitMinimum;
    private final CacheStats mCacheStats;
    private final Clock mClock;
    private final CountDownLatch mCountDownLatch;
    private final long mDefaultCacheSizeLimit;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final boolean mIndexPopulateAtStartupEnabled;
    private boolean mIndexReady;
    private final Object mLock = new Object();
    private final long mLowDiskSpaceCacheSizeLimit;
    @GuardedBy(value="mLock")
    final Set<String> mResourceIndex;
    private final StatFsHelper mStatFsHelper;
    private final DiskStorage mStorage;

    static {
        TAG = DiskStorageCache.class;
        FUTURE_TIMESTAMP_THRESHOLD_MS = TimeUnit.HOURS.toMillis(2L);
        FILECACHE_SIZE_UPDATE_PERIOD_MS = TimeUnit.MINUTES.toMillis(30L);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DiskStorageCache(DiskStorage diskStorage, EntryEvictionComparatorSupplier entryEvictionComparatorSupplier, Params params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, @Nullable DiskTrimmableRegistry diskTrimmableRegistry, final Context context, Executor executor, boolean bl) {
        this.mLowDiskSpaceCacheSizeLimit = params.mLowDiskSpaceCacheSizeLimit;
        this.mDefaultCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mStatFsHelper = StatFsHelper.getInstance();
        this.mStorage = diskStorage;
        this.mEntryEvictionComparatorSupplier = entryEvictionComparatorSupplier;
        this.mCacheSizeLastUpdateTime = -1L;
        this.mCacheEventListener = cacheEventListener;
        this.mCacheSizeLimitMinimum = params.mCacheSizeLimitMinimum;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mCacheStats = new CacheStats();
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.registerDiskTrimmable(this);
        }
        this.mClock = SystemClock.get();
        this.mIndexPopulateAtStartupEnabled = bl;
        this.mResourceIndex = new HashSet<String>();
        if (this.mIndexPopulateAtStartupEnabled) {
            this.mCountDownLatch = new CountDownLatch(1);
            executor.execute(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    Object object = DiskStorageCache.this.mLock;
                    synchronized (object) {
                        DiskStorageCache.this.maybeUpdateFileCacheSize();
                    }
                    DiskStorageCache.this.mCountDownLatch.countDown();
                }
            });
        } else {
            this.mCountDownLatch = new CountDownLatch(0);
        }
        executor.execute(new Runnable(){

            @Override
            public void run() {
                DiskStorageCache.maybeDeleteSharedPreferencesFile(context, DiskStorageCache.this.mStorage.getStorageName());
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private BinaryResource endInsert(DiskStorage.Inserter object, CacheKey cacheKey, String string2) throws IOException {
        Object object2 = this.mLock;
        synchronized (object2) {
            object = object.commit(cacheKey);
            this.mResourceIndex.add(string2);
            this.mCacheStats.increment(object.size(), 1L);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @GuardedBy(value="mLock")
    private void evictAboveSize(long l, CacheEventListener.EvictionReason evictionReason) throws IOException {
        long l2;
        int n;
        long l3;
        Object object;
        try {
            object = this.getSortedEntries(this.mStorage.getEntries());
            l3 = this.mCacheStats.getSize();
            n = 0;
            l2 = 0L;
            object = object.iterator();
        }
        catch (IOException iOException) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "evictAboveSize: " + iOException.getMessage(), iOException);
            throw iOException;
        }
        do {
            Object object2;
            block6: {
                block5: {
                    if (!object.hasNext()) break block5;
                    object2 = (DiskStorage.Entry)object.next();
                    if (l2 <= l3 - l) break block6;
                }
                this.mCacheStats.increment(-l2, -n);
                this.mStorage.purgeUnexpectedResources();
                return;
            }
            long l4 = this.mStorage.remove((DiskStorage.Entry)object2);
            this.mResourceIndex.remove(object2.getId());
            if (l4 <= 0L) continue;
            ++n;
            object2 = SettableCacheEvent.obtain().setResourceId(object2.getId()).setEvictionReason(evictionReason).setItemSize(l4).setCacheSize(l3 - (l2 += l4)).setCacheLimit(l);
            this.mCacheEventListener.onEviction((CacheEvent)object2);
            ((SettableCacheEvent)object2).recycle();
        } while (true);
    }

    private Collection<DiskStorage.Entry> getSortedEntries(Collection<DiskStorage.Entry> object) {
        long l = this.mClock.now();
        long l2 = FUTURE_TIMESTAMP_THRESHOLD_MS;
        ArrayList<DiskStorage.Entry> arrayList = new ArrayList<DiskStorage.Entry>(object.size());
        ArrayList<DiskStorage.Entry> arrayList2 = new ArrayList<DiskStorage.Entry>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            DiskStorage.Entry entry = (DiskStorage.Entry)object.next();
            if (entry.getTimestamp() > l + l2) {
                arrayList.add(entry);
                continue;
            }
            arrayList2.add(entry);
        }
        Collections.sort(arrayList2, this.mEntryEvictionComparatorSupplier.get());
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    private static void maybeDeleteSharedPreferencesFile(Context object, String string2) {
        object = object.getApplicationContext();
        object = object.getFilesDir().getParent() + File.separator + "shared_prefs" + File.separator + "disk_entries_list" + string2;
        object = new File((String)object + ".xml");
        try {
            if (((File)object).exists()) {
                ((File)object).delete();
            }
            return;
        }
        catch (Exception exception) {
            FLog.e(TAG, "Fail to delete SharedPreference from file system. ");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void maybeEvictFilesInCacheDir() throws IOException {
        Object object = this.mLock;
        synchronized (object) {
            boolean bl = this.maybeUpdateFileCacheSize();
            this.updateFileCacheSizeLimit();
            long l = this.mCacheStats.getSize();
            if (l > this.mCacheSizeLimit && !bl) {
                this.mCacheStats.reset();
                this.maybeUpdateFileCacheSize();
            }
            if (l > this.mCacheSizeLimit) {
                this.evictAboveSize(this.mCacheSizeLimit * 9L / 10L, CacheEventListener.EvictionReason.CACHE_FULL);
            }
            return;
        }
    }

    @GuardedBy(value="mLock")
    private boolean maybeUpdateFileCacheSize() {
        long l = this.mClock.now();
        if (!this.mCacheStats.isInitialized() || this.mCacheSizeLastUpdateTime == -1L || l - this.mCacheSizeLastUpdateTime > FILECACHE_SIZE_UPDATE_PERIOD_MS) {
            return this.maybeUpdateFileCacheSizeAndIndex();
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @GuardedBy(value="mLock")
    private boolean maybeUpdateFileCacheSizeAndIndex() {
        block8: {
            block9: {
                var6_1 = 0L;
                var3_2 = 0;
                var4_3 = false;
                var2_4 = 0;
                var1_5 = 0;
                var8_6 = -1L;
                var12_7 = this.mClock.now();
                var14_8 = DiskStorageCache.FUTURE_TIMESTAMP_THRESHOLD_MS;
                var16_9 = this.mIndexPopulateAtStartupEnabled != false && this.mResourceIndex.isEmpty() != false ? this.mResourceIndex : (this.mIndexPopulateAtStartupEnabled != false ? new HashSet<String>() : null);
                try {
                    for (DiskStorage.Entry var18_14 : this.mStorage.getEntries()) {
                        var5_12 = var3_2 + 1;
                        var10_13 = var6_1 + var18_14.getSize();
                        if (var18_14.getTimestamp() > var12_7 + var14_8) {
                            var4_3 = true;
                            ++var2_4;
                            var1_5 = (int)((long)var1_5 + var18_14.getSize());
                            var8_6 = Math.max(var18_14.getTimestamp() - var12_7, var8_6);
                            var3_2 = var5_12;
                            var6_1 = var10_13;
                            continue;
                        }
                        var3_2 = var5_12;
                        var6_1 = var10_13;
                        if (!this.mIndexPopulateAtStartupEnabled) continue;
                        var16_9.add(var18_14.getId());
                        var3_2 = var5_12;
                        var6_1 = var10_13;
                    }
                }
                catch (IOException var16_10) {
                    this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, DiskStorageCache.TAG, "calcFileCacheSize: " + var16_10.getMessage(), var16_10);
                    return false;
                }
                if (!var4_3) ** GOTO lbl36
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.READ_INVALID_ENTRY, DiskStorageCache.TAG, "Future timestamp found in " + var2_4 + " files , with a total size of " + var1_5 + " bytes, and a maximum time delta of " + var8_6 + "ms", null);
lbl36:
                // 2 sources
                if (this.mCacheStats.getCount() == (long)var3_2 && this.mCacheStats.getSize() == var6_1) break block8;
                if (!this.mIndexPopulateAtStartupEnabled || this.mResourceIndex == var16_9) break block9;
                this.mIndexReady = true;
            }
            if (this.mIndexPopulateAtStartupEnabled) {
                this.mResourceIndex.clear();
                this.mResourceIndex.addAll(var16_9);
            }
            this.mCacheStats.set(var6_1, var3_2);
        }
        this.mCacheSizeLastUpdateTime = var12_7;
        return true;
    }

    private DiskStorage.Inserter startInsert(String string2, CacheKey cacheKey) throws IOException {
        this.maybeEvictFilesInCacheDir();
        return this.mStorage.insert(string2, cacheKey);
    }

    /*
     * Enabled aggressive block sorting
     */
    @GuardedBy(value="mLock")
    private void updateFileCacheSizeLimit() {
        StatFsHelper.StorageType storageType = this.mStorage.isExternal() ? StatFsHelper.StorageType.EXTERNAL : StatFsHelper.StorageType.INTERNAL;
        if (this.mStatFsHelper.testLowDiskSpace(storageType, this.mDefaultCacheSizeLimit - this.mCacheStats.getSize())) {
            this.mCacheSizeLimit = this.mLowDiskSpaceCacheSizeLimit;
            return;
        }
        this.mCacheSizeLimit = this.mDefaultCacheSizeLimit;
    }

    /*
     * Exception decompiling
     */
    @Override
    public BinaryResource getResource(CacheKey var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasKeySync(CacheKey object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            object = CacheKeyUtil.getResourceIds((CacheKey)object);
            int n = 0;
            while (n < object.size()) {
                String string2 = (String)object.get(n);
                if (this.mResourceIndex.contains(string2)) {
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public BinaryResource insert(CacheKey object, WriterCallback writerCallback) throws IOException {
        String string2;
        SettableCacheEvent settableCacheEvent = SettableCacheEvent.obtain().setCacheKey((CacheKey)object);
        this.mCacheEventListener.onWriteAttempt(settableCacheEvent);
        Object object2 = this.mLock;
        synchronized (object2) {
            string2 = CacheKeyUtil.getFirstResourceId((CacheKey)object);
        }
        settableCacheEvent.setResourceId(string2);
        object2 = this.startInsert(string2, (CacheKey)object);
        try {
            object2.writeData(writerCallback, object);
            object = this.endInsert((DiskStorage.Inserter)object2, (CacheKey)object, string2);
            settableCacheEvent.setItemSize(object.size()).setCacheSize(this.mCacheStats.getSize());
            this.mCacheEventListener.onWriteSuccess(settableCacheEvent);
        }
        catch (Throwable throwable) {
            try {
                if (object2.cleanUp()) throw throwable;
                FLog.e(TAG, "Failed to delete temp file");
                throw throwable;
            }
            catch (IOException iOException) {
                settableCacheEvent.setException(iOException);
                this.mCacheEventListener.onWriteException(settableCacheEvent);
                FLog.e(TAG, "Failed inserting a file into the cache", (Throwable)iOException);
                throw iOException;
            }
        }
        try {
            if (object2.cleanUp()) return object;
            FLog.e(TAG, "Failed to delete temp file");
            return object;
        }
        finally {
            settableCacheEvent.recycle();
        }
    }

    static class CacheStats {
        private long mCount = -1L;
        private boolean mInitialized = false;
        private long mSize = -1L;

        CacheStats() {
        }

        public long getCount() {
            synchronized (this) {
                long l = this.mCount;
                return l;
            }
        }

        public long getSize() {
            synchronized (this) {
                long l = this.mSize;
                return l;
            }
        }

        public void increment(long l, long l2) {
            synchronized (this) {
                if (this.mInitialized) {
                    this.mSize += l;
                    this.mCount += l2;
                }
                return;
            }
        }

        public boolean isInitialized() {
            synchronized (this) {
                boolean bl = this.mInitialized;
                return bl;
            }
        }

        public void reset() {
            synchronized (this) {
                this.mInitialized = false;
                this.mCount = -1L;
                this.mSize = -1L;
                return;
            }
        }

        public void set(long l, long l2) {
            synchronized (this) {
                this.mCount = l2;
                this.mSize = l;
                this.mInitialized = true;
                return;
            }
        }
    }

    public static class Params {
        public final long mCacheSizeLimitMinimum;
        public final long mDefaultCacheSizeLimit;
        public final long mLowDiskSpaceCacheSizeLimit;

        public Params(long l, long l2, long l3) {
            this.mCacheSizeLimitMinimum = l;
            this.mLowDiskSpaceCacheSizeLimit = l2;
            this.mDefaultCacheSizeLimit = l3;
        }
    }

}

