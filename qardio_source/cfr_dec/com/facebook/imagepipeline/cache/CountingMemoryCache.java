/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.SystemClock
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.cache;

import android.graphics.Bitmap;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CountingLruMap;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.ValueDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CountingMemoryCache<K, V>
implements MemoryTrimmable,
MemoryCache<K, V> {
    static final long PARAMS_INTERCHECK_INTERVAL_MS = TimeUnit.MINUTES.toMillis(5L);
    private final CacheTrimStrategy mCacheTrimStrategy;
    @GuardedBy(value="this")
    final CountingLruMap<K, Entry<K, V>> mCachedEntries;
    @GuardedBy(value="this")
    final CountingLruMap<K, Entry<K, V>> mExclusiveEntries;
    @GuardedBy(value="this")
    private long mLastCacheParamsCheck;
    @GuardedBy(value="this")
    protected MemoryCacheParams mMemoryCacheParams;
    private final Supplier<MemoryCacheParams> mMemoryCacheParamsSupplier;
    @GuardedBy(value="this")
    final Map<Bitmap, Object> mOtherEntries = new WeakHashMap<Bitmap, Object>();
    private final ValueDescriptor<V> mValueDescriptor;

    public CountingMemoryCache(ValueDescriptor<V> valueDescriptor, CacheTrimStrategy cacheTrimStrategy, Supplier<MemoryCacheParams> supplier, PlatformBitmapFactory platformBitmapFactory, boolean bl) {
        this.mValueDescriptor = valueDescriptor;
        this.mExclusiveEntries = new CountingLruMap<K, Entry<K, Entry<K, V>>>(this.wrapValueDescriptor(valueDescriptor));
        this.mCachedEntries = new CountingLruMap<K, Entry<K, Entry<K, V>>>(this.wrapValueDescriptor(valueDescriptor));
        this.mCacheTrimStrategy = cacheTrimStrategy;
        this.mMemoryCacheParamsSupplier = supplier;
        this.mMemoryCacheParams = this.mMemoryCacheParamsSupplier.get();
        this.mLastCacheParamsCheck = SystemClock.uptimeMillis();
        if (bl) {
            platformBitmapFactory.setCreationListener(new PlatformBitmapFactory.BitmapCreationObserver(){});
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean canCacheNewValue(V v) {
        synchronized (this) {
            int n;
            int n2 = this.mValueDescriptor.getSizeInBytes(v);
            if (n2 > this.mMemoryCacheParams.maxCacheEntrySize) return false;
            if (this.getInUseCount() > this.mMemoryCacheParams.maxCacheEntries - 1) return false;
            int n3 = this.getInUseSizeInBytes();
            if (n3 > (n = this.mMemoryCacheParams.maxCacheSize) - n2) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void decreaseClientCount(Entry<K, V> entry) {
        synchronized (this) {
            Preconditions.checkNotNull(entry);
            boolean bl = entry.clientCount > 0;
            Preconditions.checkState(bl);
            --entry.clientCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void increaseClientCount(Entry<K, V> entry) {
        synchronized (this) {
            Preconditions.checkNotNull(entry);
            boolean bl = !entry.isOrphan;
            Preconditions.checkState(bl);
            ++entry.clientCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void makeOrphan(Entry<K, V> entry) {
        boolean bl = true;
        synchronized (this) {
            Preconditions.checkNotNull(entry);
            if (entry.isOrphan) {
                bl = false;
            }
            Preconditions.checkState(bl);
            entry.isOrphan = true;
            return;
        }
    }

    private void makeOrphans(@Nullable ArrayList<Entry<K, V>> object) {
        synchronized (this) {
            if (object != null) {
                object = ((ArrayList)object).iterator();
                while (object.hasNext()) {
                    this.makeOrphan((Entry)object.next());
                }
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean maybeAddToExclusives(Entry<K, V> entry) {
        synchronized (this) {
            if (entry.isOrphan) return false;
            if (entry.clientCount != 0) return false;
            this.mExclusiveEntries.put(entry.key, entry);
            return true;
        }
    }

    private void maybeClose(@Nullable ArrayList<Entry<K, V>> object) {
        if (object != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                CloseableReference.closeSafely(this.referenceToClose((Entry)object.next()));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void maybeEvictEntries() {
        ArrayList<Entry<K, V>> arrayList;
        synchronized (this) {
            arrayList = this.trimExclusivelyOwnedEntries(Math.min(this.mMemoryCacheParams.maxEvictionQueueEntries, this.mMemoryCacheParams.maxCacheEntries - this.getInUseCount()), Math.min(this.mMemoryCacheParams.maxEvictionQueueSize, this.mMemoryCacheParams.maxCacheSize - this.getInUseSizeInBytes()));
            this.makeOrphans(arrayList);
        }
        this.maybeClose(arrayList);
        this.maybeNotifyExclusiveEntryRemoval(arrayList);
    }

    private static <K, V> void maybeNotifyExclusiveEntryInsertion(@Nullable Entry<K, V> entry) {
        if (entry != null && entry.observer != null) {
            entry.observer.onExclusivityChanged(entry.key, true);
        }
    }

    private static <K, V> void maybeNotifyExclusiveEntryRemoval(@Nullable Entry<K, V> entry) {
        if (entry != null && entry.observer != null) {
            entry.observer.onExclusivityChanged(entry.key, false);
        }
    }

    private void maybeNotifyExclusiveEntryRemoval(@Nullable ArrayList<Entry<K, V>> object) {
        if (object != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                CountingMemoryCache.maybeNotifyExclusiveEntryRemoval((Entry)object.next());
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void maybeUpdateCacheParams() {
        synchronized (this) {
            block6: {
                long l = this.mLastCacheParamsCheck;
                long l2 = PARAMS_INTERCHECK_INTERVAL_MS;
                long l3 = SystemClock.uptimeMillis();
                if (l + l2 <= l3) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            this.mLastCacheParamsCheck = SystemClock.uptimeMillis();
            this.mMemoryCacheParams = this.mMemoryCacheParamsSupplier.get();
            return;
        }
    }

    private CloseableReference<V> newClientReference(Entry<K, V> object) {
        synchronized (this) {
            this.increaseClientCount((Entry<K, V>)object);
            object = CloseableReference.of(((Entry)object).valueRef.get(), new ResourceReleaser<V>((Entry)object){
                final /* synthetic */ Entry val$entry;
                {
                    this.val$entry = entry;
                }

                @Override
                public void release(V v) {
                    CountingMemoryCache.this.releaseClientReference(this.val$entry);
                }
            });
            return object;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    private CloseableReference<V> referenceToClose(Entry<K, V> closeableReference) {
        synchronized (this) {
            Preconditions.checkNotNull(closeableReference);
            if (!((Entry)closeableReference).isOrphan) return null;
            if (((Entry)closeableReference).clientCount != 0) return null;
            return ((Entry)closeableReference).valueRef;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseClientReference(Entry<K, V> entry) {
        boolean bl;
        CloseableReference<V> closeableReference;
        Preconditions.checkNotNull(entry);
        synchronized (this) {
            this.decreaseClientCount(entry);
            bl = this.maybeAddToExclusives(entry);
            closeableReference = this.referenceToClose(entry);
        }
        CloseableReference.closeSafely(closeableReference);
        if (!bl) {
            entry = null;
        }
        CountingMemoryCache.maybeNotifyExclusiveEntryInsertion(entry);
        this.maybeUpdateCacheParams();
        this.maybeEvictEntries();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private ArrayList<Entry<K, V>> trimExclusivelyOwnedEntries(int n, int n2) {
        synchronized (this) {
            int n3;
            void var4_5;
            n = Math.max(n, 0);
            n2 = Math.max(n2, 0);
            if (this.mExclusiveEntries.getCount() <= n && (n3 = this.mExclusiveEntries.getSizeInBytes()) <= n2) {
                Object var4_4 = null;
            } else {
                ArrayList<Entry<K, V>> arrayList = new ArrayList<Entry<K, V>>();
                do {
                    if (this.mExclusiveEntries.getCount() <= n) {
                        ArrayList<Entry<K, V>> arrayList2 = arrayList;
                        if (this.mExclusiveEntries.getSizeInBytes() <= n2) break;
                    }
                    K k = this.mExclusiveEntries.getFirstKey();
                    this.mExclusiveEntries.remove(k);
                    arrayList.add(this.mCachedEntries.remove(k));
                } while (true);
            }
            return var4_5;
        }
    }

    private ValueDescriptor<Entry<K, V>> wrapValueDescriptor(final ValueDescriptor<V> valueDescriptor) {
        return new ValueDescriptor<Entry<K, V>>(){

            @Override
            public int getSizeInBytes(Entry<K, V> entry) {
                return valueDescriptor.getSizeInBytes(entry.valueRef.get());
            }
        };
    }

    @Override
    public CloseableReference<V> cache(K k, CloseableReference<V> closeableReference) {
        return this.cache(k, closeableReference, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CloseableReference<V> cache(K k, CloseableReference<V> object, EntryStateObserver<K> entryStateObserver) {
        Entry<K, V> entry;
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(object);
        this.maybeUpdateCacheParams();
        CloseableReference<V> closeableReference = null;
        CloseableReference<V> closeableReference2 = null;
        synchronized (this) {
            entry = this.mExclusiveEntries.remove(k);
            Entry<K, V> entry2 = this.mCachedEntries.remove(k);
            if (entry2 != null) {
                this.makeOrphan(entry2);
                closeableReference = this.referenceToClose(entry2);
            }
            if (this.canCacheNewValue(((CloseableReference)object).get())) {
                object = Entry.of(k, object, entryStateObserver);
                this.mCachedEntries.put(k, (Entry<K, Object>)object);
                closeableReference2 = this.newClientReference((Entry<K, V>)object);
            }
        }
        CloseableReference.closeSafely(closeableReference);
        CountingMemoryCache.maybeNotifyExclusiveEntryRemoval(entry);
        this.maybeEvictEntries();
        return closeableReference2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    @Override
    public CloseableReference<V> get(K object) {
        Entry<K, V> entry;
        Preconditions.checkNotNull(object);
        Object var2_2 = null;
        synchronized (this) {
            entry = this.mExclusiveEntries.remove(object);
            Entry<K, V> entry2 = this.mCachedEntries.get(object);
            object = var2_2;
            if (entry2 != null) {
                object = this.newClientReference(entry2);
            }
        }
        CountingMemoryCache.maybeNotifyExclusiveEntryRemoval(entry);
        this.maybeUpdateCacheParams();
        this.maybeEvictEntries();
        return object;
    }

    public int getInUseCount() {
        synchronized (this) {
            int n = this.mCachedEntries.getCount();
            int n2 = this.mExclusiveEntries.getCount();
            return n - n2;
        }
    }

    public int getInUseSizeInBytes() {
        synchronized (this) {
            int n = this.mCachedEntries.getSizeInBytes();
            int n2 = this.mExclusiveEntries.getSizeInBytes();
            return n - n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void trim(MemoryTrimType object) {
        double d = this.mCacheTrimStrategy.getTrimRatio((MemoryTrimType)((Object)object));
        synchronized (this) {
            object = this.trimExclusivelyOwnedEntries(Integer.MAX_VALUE, Math.max(0, (int)((double)this.mCachedEntries.getSizeInBytes() * (1.0 - d)) - this.getInUseSizeInBytes()));
            this.makeOrphans((ArrayList<Entry<K, V>>)object);
        }
        this.maybeClose((ArrayList<Entry<K, V>>)object);
        this.maybeNotifyExclusiveEntryRemoval((ArrayList<Entry<K, V>>)object);
        this.maybeUpdateCacheParams();
        this.maybeEvictEntries();
    }

    public static interface CacheTrimStrategy {
        public double getTrimRatio(MemoryTrimType var1);
    }

    static class Entry<K, V> {
        public int clientCount;
        public boolean isOrphan;
        public final K key;
        @Nullable
        public final EntryStateObserver<K> observer;
        public final CloseableReference<V> valueRef;

        private Entry(K k, CloseableReference<V> closeableReference, @Nullable EntryStateObserver<K> entryStateObserver) {
            this.key = Preconditions.checkNotNull(k);
            this.valueRef = Preconditions.checkNotNull(CloseableReference.cloneOrNull(closeableReference));
            this.clientCount = 0;
            this.isOrphan = false;
            this.observer = entryStateObserver;
        }

        static <K, V> Entry<K, V> of(K k, CloseableReference<V> closeableReference, @Nullable EntryStateObserver<K> entryStateObserver) {
            return new Entry<K, V>(k, closeableReference, entryStateObserver);
        }
    }

    public static interface EntryStateObserver<K> {
        public void onExclusivityChanged(K var1, boolean var2);
    }

}

