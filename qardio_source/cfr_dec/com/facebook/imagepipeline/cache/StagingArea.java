/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class StagingArea {
    private static final Class<?> TAG = StagingArea.class;
    @GuardedBy(value="this")
    private Map<CacheKey, EncodedImage> mMap = new HashMap<CacheKey, EncodedImage>();

    private StagingArea() {
    }

    public static StagingArea getInstance() {
        return new StagingArea();
    }

    private void logStats() {
        synchronized (this) {
            FLog.v(TAG, "Count = %d", (Object)this.mMap.size());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean containsKey(CacheKey cacheKey) {
        boolean bl = false;
        synchronized (this) {
            Preconditions.checkNotNull(cacheKey);
            boolean bl2 = this.mMap.containsKey(cacheKey);
            if (!bl2) return bl;
            EncodedImage encodedImage = this.mMap.get(cacheKey);
            synchronized (encodedImage) {
                if (EncodedImage.isValid(encodedImage)) return true;
                this.mMap.remove(cacheKey);
                FLog.w(TAG, "Found closed reference %d for key %s (%d)", System.identityHashCode(encodedImage), cacheKey.toString(), System.identityHashCode(cacheKey));
                return bl;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public EncodedImage get(CacheKey object) {
        synchronized (this) {
            EncodedImage encodedImage;
            Preconditions.checkNotNull(object);
            EncodedImage encodedImage2 = encodedImage = this.mMap.get(object);
            if (encodedImage == null) return encodedImage2;
            synchronized (encodedImage) {
                try {
                    if (!EncodedImage.isValid(encodedImage)) {
                        this.mMap.remove(object);
                        FLog.w(TAG, "Found closed reference %d for key %s (%d)", System.identityHashCode(encodedImage), object.toString(), System.identityHashCode(object));
                        return null;
                    }
                    encodedImage2 = EncodedImage.cloneOrNull(encodedImage);
                    return encodedImage2;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    public void put(CacheKey cacheKey, EncodedImage encodedImage) {
        synchronized (this) {
            Preconditions.checkNotNull(cacheKey);
            Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
            EncodedImage.closeSafely(this.mMap.put(cacheKey, EncodedImage.cloneOrNull(encodedImage)));
            this.logStats();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean remove(CacheKey cacheKey, EncodedImage closeable) {
        CloseableReference<PooledByteBuffer> closeableReference;
        EncodedImage encodedImage;
        boolean bl;
        block11: {
            bl = false;
            // MONITORENTER : this
            Preconditions.checkNotNull(cacheKey);
            Preconditions.checkNotNull(closeable);
            Preconditions.checkArgument(EncodedImage.isValid((EncodedImage)closeable));
            encodedImage = this.mMap.get(cacheKey);
            if (encodedImage == null) {
                // MONITOREXIT : this
                return bl;
            }
            closeableReference = encodedImage.getByteBufferRef();
            closeable = ((EncodedImage)closeable).getByteBufferRef();
            if (closeableReference != null && closeable != null) {
                PooledByteBuffer pooledByteBuffer = closeableReference.get();
                Object t = ((CloseableReference)closeable).get();
                if (pooledByteBuffer == t) break block11;
            }
            CloseableReference.closeSafely(closeable);
            CloseableReference.closeSafely(closeableReference);
            EncodedImage.closeSafely(encodedImage);
            return bl;
        }
        try {
            this.mMap.remove(cacheKey);
            this.logStats();
            bl = true;
            return bl;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            CloseableReference.closeSafely(closeable);
            CloseableReference.closeSafely(closeableReference);
            EncodedImage.closeSafely(encodedImage);
        }
    }
}

