/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.StagingArea;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class BufferedDiskCache {
    private static final Class<?> TAG = BufferedDiskCache.class;
    private final FileCache mFileCache;
    private final ImageCacheStatsTracker mImageCacheStatsTracker;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    private final PooledByteStreams mPooledByteStreams;
    private final Executor mReadExecutor;
    private final StagingArea mStagingArea;
    private final Executor mWriteExecutor;

    public BufferedDiskCache(FileCache fileCache, PooledByteBufferFactory pooledByteBufferFactory, PooledByteStreams pooledByteStreams, Executor executor, Executor executor2, ImageCacheStatsTracker imageCacheStatsTracker) {
        this.mFileCache = fileCache;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mPooledByteStreams = pooledByteStreams;
        this.mReadExecutor = executor;
        this.mWriteExecutor = executor2;
        this.mImageCacheStatsTracker = imageCacheStatsTracker;
        this.mStagingArea = StagingArea.getInstance();
    }

    private Task<EncodedImage> foundPinnedImage(CacheKey cacheKey, EncodedImage encodedImage) {
        FLog.v(TAG, "Found image for %s in staging area", (Object)cacheKey.toString());
        this.mImageCacheStatsTracker.onStagingAreaHit(cacheKey);
        return Task.forResult(encodedImage);
    }

    private Task<EncodedImage> getAsync(CacheKey cacheKey, AtomicBoolean object) {
        try {
            object = Task.call(new Callable<EncodedImage>((AtomicBoolean)object, cacheKey){
                final /* synthetic */ AtomicBoolean val$isCancelled;
                final /* synthetic */ CacheKey val$key;
                {
                    this.val$isCancelled = atomicBoolean;
                    this.val$key = cacheKey;
                }

                /*
                 * Loose catch block
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                @Override
                public EncodedImage call() throws Exception {
                    CloseableReference<PooledByteBuffer> closeableReference;
                    if (this.val$isCancelled.get()) {
                        throw new CancellationException();
                    }
                    EncodedImage encodedImage = BufferedDiskCache.this.mStagingArea.get(this.val$key);
                    if (encodedImage != null) {
                        FLog.v(TAG, "Found image for %s in staging area", (Object)this.val$key.toString());
                        BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaHit(this.val$key);
                    } else {
                        FLog.v(TAG, "Did not find image for %s in staging area", (Object)this.val$key.toString());
                        BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaMiss();
                        closeableReference = CloseableReference.of(BufferedDiskCache.this.readFromDiskCache(this.val$key));
                        encodedImage = new EncodedImage(closeableReference);
                        CloseableReference.closeSafely(closeableReference);
                    }
                    if (!Thread.interrupted()) return encodedImage;
                    FLog.v(TAG, "Host thread was interrupted, decreasing reference count");
                    if (encodedImage == null) throw new InterruptedException();
                    encodedImage.close();
                    throw new InterruptedException();
                    catch (Throwable throwable) {
                        try {
                            CloseableReference.closeSafely(closeableReference);
                            throw throwable;
                        }
                        catch (Exception exception) {
                            return null;
                        }
                    }
                    catch (Exception exception) {
                        return null;
                    }
                }
            }, this.mReadExecutor);
            return object;
        }
        catch (Exception exception) {
            FLog.w(TAG, exception, "Failed to schedule disk-cache read for %s", cacheKey.toString());
            return Task.forError(exception);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private PooledByteBuffer readFromDiskCache(CacheKey var1_1) throws IOException {
        FLog.v(BufferedDiskCache.TAG, "Disk cache read for %s", (Object)var1_1.toString());
        var3_2 = this.mFileCache.getResource(var1_1);
        if (var3_2 == null) {
            FLog.v(BufferedDiskCache.TAG, "Disk cache miss for %s", (Object)var1_1.toString());
            this.mImageCacheStatsTracker.onDiskCacheMiss();
            return null;
        }
        FLog.v(BufferedDiskCache.TAG, "Found entry in disk cache for %s", (Object)var1_1.toString());
        this.mImageCacheStatsTracker.onDiskCacheHit();
        var2_4 = var3_2.openStream();
        {
            catch (IOException var2_5) {
                FLog.w(BufferedDiskCache.TAG, var2_5, "Exception reading from cache for %s", new Object[]{var1_1.toString()});
                this.mImageCacheStatsTracker.onDiskCacheGetFail();
                throw var2_5;
            }
        }
        try {
            var3_2 = this.mPooledByteBufferFactory.newByteBuffer(var2_4, (int)var3_2.size());
            {
                FLog.v(BufferedDiskCache.TAG, "Successful read from disk cache for %s", (Object)var1_1.toString());
            }
            return var3_2;
        }
        finally {
            ** try [egrp 2[TRYBLOCK] [4 : 102->120)] { 
lbl21:
            // 1 sources
            var2_4.close();
        }
    }

    private void writeToDiskCache(CacheKey cacheKey, final EncodedImage encodedImage) {
        FLog.v(TAG, "About to write to disk-cache for key %s", (Object)cacheKey.toString());
        try {
            this.mFileCache.insert(cacheKey, new WriterCallback(){

                @Override
                public void write(OutputStream outputStream) throws IOException {
                    BufferedDiskCache.this.mPooledByteStreams.copy(encodedImage.getInputStream(), outputStream);
                }
            });
            FLog.v(TAG, "Successful disk-cache write for key %s", (Object)cacheKey.toString());
            return;
        }
        catch (IOException iOException) {
            FLog.w(TAG, iOException, "Failed to write to disk-cache for key %s", cacheKey.toString());
            return;
        }
    }

    public boolean containsSync(CacheKey cacheKey) {
        return this.mStagingArea.containsKey(cacheKey) || this.mFileCache.hasKeySync(cacheKey);
    }

    public Task<EncodedImage> get(CacheKey cacheKey, AtomicBoolean atomicBoolean) {
        EncodedImage encodedImage = this.mStagingArea.get(cacheKey);
        if (encodedImage != null) {
            return this.foundPinnedImage(cacheKey, encodedImage);
        }
        return this.getAsync(cacheKey, atomicBoolean);
    }

    public void put(final CacheKey cacheKey, EncodedImage encodedImage) {
        Preconditions.checkNotNull(cacheKey);
        Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
        this.mStagingArea.put(cacheKey, encodedImage);
        final EncodedImage encodedImage2 = EncodedImage.cloneOrNull(encodedImage);
        try {
            this.mWriteExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        BufferedDiskCache.this.writeToDiskCache(cacheKey, encodedImage2);
                        return;
                    }
                    finally {
                        BufferedDiskCache.this.mStagingArea.remove(cacheKey, encodedImage2);
                        EncodedImage.closeSafely(encodedImage2);
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            FLog.w(TAG, exception, "Failed to schedule disk-cache write for %s", cacheKey.toString());
            this.mStagingArea.remove(cacheKey, encodedImage);
            EncodedImage.closeSafely(encodedImage2);
            return;
        }
    }

}

