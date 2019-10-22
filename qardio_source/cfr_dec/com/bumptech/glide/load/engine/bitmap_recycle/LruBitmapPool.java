/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.AttributeStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.SizeConfigStrategy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LruBitmapPool
implements BitmapPool {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private final Set<Bitmap.Config> allowedConfigs;
    private int currentSize;
    private int evictions;
    private int hits;
    private final int initialMaxSize;
    private int maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    public LruBitmapPool(int n) {
        this(n, LruBitmapPool.getDefaultStrategy(), LruBitmapPool.getDefaultAllowedConfigs());
    }

    LruBitmapPool(int n, LruPoolStrategy lruPoolStrategy, Set<Bitmap.Config> set) {
        this.initialMaxSize = n;
        this.maxSize = n;
        this.strategy = lruPoolStrategy;
        this.allowedConfigs = set;
        this.tracker = new NullBitmapTracker();
    }

    private void dump() {
        if (Log.isLoggable((String)"LruBitmapPool", (int)2)) {
            this.dumpUnchecked();
        }
    }

    private void dumpUnchecked() {
        Log.v((String)"LruBitmapPool", (String)("Hits=" + this.hits + ", misses=" + this.misses + ", puts=" + this.puts + ", evictions=" + this.evictions + ", currentSize=" + this.currentSize + ", maxSize=" + this.maxSize + "\nStrategy=" + this.strategy));
    }

    private void evict() {
        this.trimToSize(this.maxSize);
    }

    private static Set<Bitmap.Config> getDefaultAllowedConfigs() {
        HashSet<Bitmap.Config> hashSet = new HashSet<Bitmap.Config>();
        hashSet.addAll(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static LruPoolStrategy getDefaultStrategy() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new SizeConfigStrategy();
        }
        return new AttributeStrategy();
    }

    private void trimToSize(int n) {
        synchronized (this) {
            do {
                Bitmap bitmap;
                block12: {
                    block11: {
                        if (this.currentSize <= n) break block11;
                        bitmap = this.strategy.removeLast();
                        if (bitmap != null) break block12;
                        if (Log.isLoggable((String)"LruBitmapPool", (int)5)) {
                            Log.w((String)"LruBitmapPool", (String)"Size mismatch, resetting");
                            this.dumpUnchecked();
                        }
                        this.currentSize = 0;
                    }
                    return;
                }
                this.tracker.remove(bitmap);
                this.currentSize -= this.strategy.getSize(bitmap);
                bitmap.recycle();
                ++this.evictions;
                if (Log.isLoggable((String)"LruBitmapPool", (int)3)) {
                    Log.d((String)"LruBitmapPool", (String)("Evicting bitmap=" + this.strategy.logBitmap(bitmap)));
                }
                this.dump();
            } while (true);
        }
    }

    @Override
    public void clearMemory() {
        if (Log.isLoggable((String)"LruBitmapPool", (int)3)) {
            Log.d((String)"LruBitmapPool", (String)"clearMemory");
        }
        this.trimToSize(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bitmap get(int n, int n2, Bitmap.Config config) {
        synchronized (this) {
            config = this.getDirty(n, n2, config);
            if (config != null) {
                config.eraseColor(0);
            }
            return config;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=12)
    @Override
    public Bitmap getDirty(int n, int n2, Bitmap.Config config) {
        synchronized (this) {
            LruPoolStrategy lruPoolStrategy = this.strategy;
            Bitmap.Config config2 = config != null ? config : DEFAULT_CONFIG;
            if ((config2 = lruPoolStrategy.get(n, n2, config2)) == null) {
                if (Log.isLoggable((String)"LruBitmapPool", (int)3)) {
                    Log.d((String)"LruBitmapPool", (String)("Missing bitmap=" + this.strategy.logBitmap(n, n2, config)));
                }
                ++this.misses;
            } else {
                ++this.hits;
                this.currentSize -= this.strategy.getSize((Bitmap)config2);
                this.tracker.remove((Bitmap)config2);
                if (Build.VERSION.SDK_INT >= 12) {
                    config2.setHasAlpha(true);
                }
            }
            if (Log.isLoggable((String)"LruBitmapPool", (int)2)) {
                Log.v((String)"LruBitmapPool", (String)("Get bitmap=" + this.strategy.logBitmap(n, n2, config)));
            }
            this.dump();
            return config2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean put(Bitmap bitmap) {
        synchronized (this) {
            if (bitmap == null) {
                throw new NullPointerException("Bitmap must not be null");
            }
            if (!bitmap.isMutable() || this.strategy.getSize(bitmap) > this.maxSize || !this.allowedConfigs.contains((Object)bitmap.getConfig())) {
                if (!Log.isLoggable((String)"LruBitmapPool", (int)2)) return false;
                Log.v((String)"LruBitmapPool", (String)("Reject bitmap from pool, bitmap: " + this.strategy.logBitmap(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.allowedConfigs.contains((Object)bitmap.getConfig())));
                return false;
            }
            int n = this.strategy.getSize(bitmap);
            this.strategy.put(bitmap);
            this.tracker.add(bitmap);
            ++this.puts;
            this.currentSize += n;
            if (Log.isLoggable((String)"LruBitmapPool", (int)2)) {
                Log.v((String)"LruBitmapPool", (String)("Put bitmap in pool=" + this.strategy.logBitmap(bitmap)));
            }
            this.dump();
            this.evict();
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"InlinedApi"})
    @Override
    public void trimMemory(int n) {
        if (Log.isLoggable((String)"LruBitmapPool", (int)3)) {
            Log.d((String)"LruBitmapPool", (String)("trimMemory, level=" + n));
        }
        if (n >= 60) {
            this.clearMemory();
            return;
        } else {
            if (n < 40) return;
            {
                this.trimToSize(this.maxSize / 2);
                return;
            }
        }
    }

    private static interface BitmapTracker {
        public void add(Bitmap var1);

        public void remove(Bitmap var1);
    }

    private static class NullBitmapTracker
    implements BitmapTracker {
        private NullBitmapTracker() {
        }

        @Override
        public void add(Bitmap bitmap) {
        }

        @Override
        public void remove(Bitmap bitmap) {
        }
    }

}

