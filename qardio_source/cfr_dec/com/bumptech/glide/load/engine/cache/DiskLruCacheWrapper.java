/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.engine.cache;

import android.util.Log;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheWriteLocker;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import java.io.File;
import java.io.IOException;

public class DiskLruCacheWrapper
implements DiskCache {
    private static DiskLruCacheWrapper wrapper = null;
    private final File directory;
    private DiskLruCache diskLruCache;
    private final int maxSize;
    private final SafeKeyGenerator safeKeyGenerator;
    private final DiskCacheWriteLocker writeLocker = new DiskCacheWriteLocker();

    protected DiskLruCacheWrapper(File file, int n) {
        this.directory = file;
        this.maxSize = n;
        this.safeKeyGenerator = new SafeKeyGenerator();
    }

    public static DiskCache get(File object, int n) {
        synchronized (DiskLruCacheWrapper.class) {
            if (wrapper == null) {
                wrapper = new DiskLruCacheWrapper((File)object, n);
            }
            object = wrapper;
            return object;
        }
    }

    private DiskLruCache getDiskCache() throws IOException {
        synchronized (this) {
            if (this.diskLruCache == null) {
                this.diskLruCache = DiskLruCache.open(this.directory, 1, 1, this.maxSize);
            }
            DiskLruCache diskLruCache = this.diskLruCache;
            return diskLruCache;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void delete(Key object) {
        object = this.safeKeyGenerator.getSafeKey((Key)object);
        try {
            this.getDiskCache().remove((String)object);
            return;
        }
        catch (IOException iOException) {
            if (!Log.isLoggable((String)"DiskLruCacheWrapper", (int)5)) return;
            Log.w((String)"DiskLruCacheWrapper", (String)"Unable to delete from disk cache", (Throwable)iOException);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public File get(Key object) {
        DiskLruCache.Value value;
        object = this.safeKeyGenerator.getSafeKey((Key)object);
        Object var2_2 = null;
        try {
            value = this.getDiskCache().get((String)object);
            object = var2_2;
            if (value == null) return object;
        }
        catch (IOException iOException) {
            object = var2_2;
            if (!Log.isLoggable((String)"DiskLruCacheWrapper", (int)5)) return object;
            Log.w((String)"DiskLruCacheWrapper", (String)"Unable to get from disk cache", (Throwable)iOException);
            return null;
        }
        return value.getFile(0);
    }

    @Override
    public void put(Key key, DiskCache.Writer writer) {
        block10: {
            Object object;
            block11: {
                object = this.safeKeyGenerator.getSafeKey(key);
                this.writeLocker.acquire(key);
                object = this.getDiskCache().edit((String)object);
                if (object == null) break block10;
                try {
                    if (!writer.write(((DiskLruCache.Editor)object).getFile(0))) break block11;
                    ((DiskLruCache.Editor)object).commit();
                }
                catch (Throwable throwable) {
                    try {
                        ((DiskLruCache.Editor)object).abortUnlessCommitted();
                        throw throwable;
                    }
                    catch (IOException iOException) {
                        if (Log.isLoggable((String)"DiskLruCacheWrapper", (int)5)) {
                            Log.w((String)"DiskLruCacheWrapper", (String)"Unable to put to disk cache", (Throwable)iOException);
                        }
                        return;
                    }
                }
            }
            ((DiskLruCache.Editor)object).abortUnlessCommitted();
        }
        this.writeLocker.release(key);
        return;
        finally {
            this.writeLocker.release(key);
        }
    }
}

