/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.engine;

import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.EngineKey;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.util.LogTime;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class DecodeJob<A, T, Z> {
    private static final FileOpener DEFAULT_FILE_OPENER = new FileOpener();
    private final DiskCacheProvider diskCacheProvider;
    private final DiskCacheStrategy diskCacheStrategy;
    private final DataFetcher<A> fetcher;
    private final FileOpener fileOpener;
    private final int height;
    private volatile boolean isCancelled;
    private final DataLoadProvider<A, T> loadProvider;
    private final Priority priority;
    private final EngineKey resultKey;
    private final ResourceTranscoder<T, Z> transcoder;
    private final Transformation<T> transformation;
    private final int width;

    public DecodeJob(EngineKey engineKey, int n, int n2, DataFetcher<A> dataFetcher, DataLoadProvider<A, T> dataLoadProvider, Transformation<T> transformation, ResourceTranscoder<T, Z> resourceTranscoder, DiskCacheProvider diskCacheProvider, DiskCacheStrategy diskCacheStrategy, Priority priority) {
        this(engineKey, n, n2, dataFetcher, dataLoadProvider, transformation, resourceTranscoder, diskCacheProvider, diskCacheStrategy, priority, DEFAULT_FILE_OPENER);
    }

    DecodeJob(EngineKey engineKey, int n, int n2, DataFetcher<A> dataFetcher, DataLoadProvider<A, T> dataLoadProvider, Transformation<T> transformation, ResourceTranscoder<T, Z> resourceTranscoder, DiskCacheProvider diskCacheProvider, DiskCacheStrategy diskCacheStrategy, Priority priority, FileOpener fileOpener) {
        this.resultKey = engineKey;
        this.width = n;
        this.height = n2;
        this.fetcher = dataFetcher;
        this.loadProvider = dataLoadProvider;
        this.transformation = transformation;
        this.transcoder = resourceTranscoder;
        this.diskCacheProvider = diskCacheProvider;
        this.diskCacheStrategy = diskCacheStrategy;
        this.priority = priority;
        this.fileOpener = fileOpener;
    }

    private Resource<T> cacheAndDecodeSourceData(A object) throws IOException {
        long l = LogTime.getLogTime();
        object = new SourceWriter<A>(this.loadProvider.getSourceEncoder(), object);
        this.diskCacheProvider.getDiskCache().put(this.resultKey.getOriginalKey(), (DiskCache.Writer)object);
        if (Log.isLoggable((String)"DecodeJob", (int)2)) {
            this.logWithTimeAndKey("Wrote source to cache", l);
        }
        l = LogTime.getLogTime();
        object = this.loadFromCache(this.resultKey.getOriginalKey());
        if (Log.isLoggable((String)"DecodeJob", (int)2) && object != null) {
            this.logWithTimeAndKey("Decoded source from cache", l);
        }
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private Resource<T> decodeFromSourceData(A object) throws IOException {
        void var1_3;
        if (this.diskCacheStrategy.cacheSource()) {
            Resource<T> resource = this.cacheAndDecodeSourceData(object);
            return var1_3;
        } else {
            Resource<T> resource;
            long l = LogTime.getLogTime();
            Resource<T> resource2 = resource = this.loadProvider.getSourceDecoder().decode(object, this.width, this.height);
            if (!Log.isLoggable((String)"DecodeJob", (int)2)) return var1_3;
            {
                this.logWithTimeAndKey("Decoded from source", l);
                return resource;
            }
        }
    }

    private Resource<T> decodeSource() throws Exception {
        Object object;
        block5: {
            boolean bl;
            long l = LogTime.getLogTime();
            object = this.fetcher.loadData(this.priority);
            if (Log.isLoggable((String)"DecodeJob", (int)2)) {
                this.logWithTimeAndKey("Fetched data", l);
            }
            if (!(bl = this.isCancelled)) break block5;
            this.fetcher.cleanup();
            return null;
        }
        try {
            object = this.decodeFromSourceData(object);
            return object;
        }
        finally {
            this.fetcher.cleanup();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Resource<T> loadFromCache(Key key) throws IOException {
        Object object = this.diskCacheProvider.getDiskCache().get(key);
        if (object == null) {
            return null;
        }
        try {
            Resource<T> resource = this.loadProvider.getCacheDecoder().decode((File)object, this.width, this.height);
            object = resource;
            if (resource != null) return object;
            this.diskCacheProvider.getDiskCache().delete(key);
            return resource;
        }
        catch (Throwable throwable) {
            if (false) throw throwable;
            this.diskCacheProvider.getDiskCache().delete(key);
            throw throwable;
        }
    }

    private void logWithTimeAndKey(String string2, long l) {
        Log.v((String)"DecodeJob", (String)(string2 + " in " + LogTime.getElapsedMillis(l) + ", key: " + this.resultKey));
    }

    private Resource<Z> transcode(Resource<T> resource) {
        if (resource == null) {
            return null;
        }
        return this.transcoder.transcode(resource);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Resource<T> transform(Resource<T> resource) {
        Resource<T> resource2;
        if (resource == null) {
            return null;
        }
        Resource<T> resource3 = resource2 = this.transformation.transform(resource, this.width, this.height);
        if (resource.equals(resource2)) return resource3;
        resource.recycle();
        return resource2;
    }

    private Resource<Z> transformEncodeAndTranscode(Resource<T> resource) {
        long l = LogTime.getLogTime();
        resource = this.transform(resource);
        if (Log.isLoggable((String)"DecodeJob", (int)2)) {
            this.logWithTimeAndKey("Transformed resource from source", l);
        }
        this.writeTransformedToCache(resource);
        l = LogTime.getLogTime();
        resource = this.transcode(resource);
        if (Log.isLoggable((String)"DecodeJob", (int)2)) {
            this.logWithTimeAndKey("Transcoded transformed from source", l);
        }
        return resource;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeTransformedToCache(Resource<T> sourceWriter) {
        long l;
        block3: {
            block2: {
                if (sourceWriter == null || !this.diskCacheStrategy.cacheResult()) break block2;
                l = LogTime.getLogTime();
                sourceWriter = new SourceWriter<SourceWriter<Object>>(this.loadProvider.getEncoder(), sourceWriter);
                this.diskCacheProvider.getDiskCache().put(this.resultKey, sourceWriter);
                if (Log.isLoggable((String)"DecodeJob", (int)2)) break block3;
            }
            return;
        }
        this.logWithTimeAndKey("Wrote transformed from source to cache", l);
    }

    public void cancel() {
        this.isCancelled = true;
        this.fetcher.cancel();
    }

    public Resource<Z> decodeFromSource() throws Exception {
        return this.transformEncodeAndTranscode(this.decodeSource());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Resource<Z> decodeResultFromCache() throws Exception {
        Resource<Z> resource;
        void var3_2;
        if (!this.diskCacheStrategy.cacheResult()) {
            return var3_2;
        }
        long l = LogTime.getLogTime();
        Resource<T> resource2 = this.loadFromCache(this.resultKey);
        if (Log.isLoggable((String)"DecodeJob", (int)2)) {
            this.logWithTimeAndKey("Decoded transformed from cache", l);
        }
        l = LogTime.getLogTime();
        Resource<Z> resource3 = resource = this.transcode(resource2);
        if (!Log.isLoggable((String)"DecodeJob", (int)2)) {
            return var3_2;
        }
        this.logWithTimeAndKey("Transcoded transformed from cache", l);
        return resource;
    }

    public Resource<Z> decodeSourceFromCache() throws Exception {
        if (!this.diskCacheStrategy.cacheSource()) {
            return null;
        }
        long l = LogTime.getLogTime();
        Resource<T> resource = this.loadFromCache(this.resultKey.getOriginalKey());
        if (Log.isLoggable((String)"DecodeJob", (int)2)) {
            this.logWithTimeAndKey("Decoded source from cache", l);
        }
        return this.transformEncodeAndTranscode(resource);
    }

    static interface DiskCacheProvider {
        public DiskCache getDiskCache();
    }

    static class FileOpener {
        FileOpener() {
        }

        public OutputStream open(File file) throws FileNotFoundException {
            return new BufferedOutputStream(new FileOutputStream(file));
        }
    }

    class SourceWriter<DataType>
    implements DiskCache.Writer {
        private final DataType data;
        private final Encoder<DataType> encoder;

        public SourceWriter(Encoder<DataType> encoder, DataType DataType2) {
            this.encoder = encoder;
            this.data = DataType2;
        }

        /*
         * Loose catch block
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean write(File object) {
            boolean bl;
            boolean bl2 = false;
            Object object2 = null;
            Object object3 = null;
            object3 = object = DecodeJob.this.fileOpener.open((File)object);
            object2 = object;
            bl2 = bl = this.encoder.encode(this.data, (OutputStream)object);
            if (object == null) return bl2;
            try {
                ((OutputStream)object).close();
                return bl;
            }
            catch (IOException iOException) {
                return bl;
            }
            catch (FileNotFoundException fileNotFoundException) {
                block13: {
                    object2 = object3;
                    if (!Log.isLoggable((String)"DecodeJob", (int)3)) break block13;
                    object2 = object3;
                    Log.d((String)"DecodeJob", (String)"Failed to find file to write to disk cache", (Throwable)fileNotFoundException);
                }
                if (object3 == null) return bl2;
                try {
                    ((OutputStream)object3).close();
                    return false;
                }
                catch (IOException iOException) {
                    return false;
                }
                catch (Throwable throwable) {
                    if (object2 == null) throw throwable;
                    try {
                        ((OutputStream)object2).close();
                    }
                    catch (IOException iOException) {
                        throw throwable;
                    }
                    do {
                        throw throwable;
                        break;
                    } while (true);
                }
            }
        }
    }

}

