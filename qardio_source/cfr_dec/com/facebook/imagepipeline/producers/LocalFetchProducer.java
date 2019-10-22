/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.imagepipeline.producers;

import android.os.Build;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public abstract class LocalFetchProducer
implements Producer<EncodedImage> {
    private final boolean mDecodeFileDescriptorEnabledForKitKat;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    /*
     * Enabled aggressive block sorting
     */
    protected LocalFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean bl) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        bl = bl && Build.VERSION.SDK_INT == 19;
        this.mDecodeFileDescriptorEnabledForKitKat = bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected EncodedImage getByteBufferBackedEncodedImage(InputStream var1_1, int var2_2) throws IOException {
        var4_3 = null;
        if (var2_2 > 0) ** GOTO lbl6
        try {
            block3: {
                var3_4 = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(var1_1));
                break block3;
lbl6:
                // 1 sources
                var3_4 = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(var1_1, var2_2));
            }
            var4_3 = var3_4;
            var5_6 = new EncodedImage(var3_4);
        }
        catch (Throwable var3_5) {
            Closeables.closeQuietly(var1_1);
            CloseableReference.closeSafely(var4_3);
            throw var3_5;
        }
        Closeables.closeQuietly(var1_1);
        CloseableReference.closeSafely(var3_4);
        return var5_6;
    }

    protected abstract EncodedImage getEncodedImage(ImageRequest var1) throws IOException;

    protected EncodedImage getEncodedImage(InputStream inputStream, int n) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        long l = runtime.maxMemory();
        long l2 = Math.min(l - (runtime.totalMemory() - runtime.freeMemory()), 0x800000L);
        if (this.mDecodeFileDescriptorEnabledForKitKat && inputStream instanceof FileInputStream && l >= 64L * l2) {
            return this.getInputStreamBackedEncodedImage(new File(inputStream.toString()), n);
        }
        return this.getByteBufferBackedEncodedImage(inputStream, n);
    }

    protected EncodedImage getInputStreamBackedEncodedImage(final File file, int n) throws IOException {
        return new EncodedImage(new Supplier<FileInputStream>(){

            @Override
            public FileInputStream get() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    return fileInputStream;
                }
                catch (IOException iOException) {
                    throw new RuntimeException(iOException);
                }
            }
        }, n);
    }

    protected abstract String getProducerName();

    @Override
    public void produceResults(Consumer<EncodedImage> object, ProducerContext producerContext) {
        ProducerListener producerListener = producerContext.getListener();
        String string2 = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        object = new StatefulProducerRunnable<EncodedImage>(object, producerListener, this.getProducerName(), string2){

            @Override
            protected void disposeResult(EncodedImage encodedImage) {
                EncodedImage.closeSafely(encodedImage);
            }

            @Override
            protected EncodedImage getResult() throws Exception {
                EncodedImage encodedImage = LocalFetchProducer.this.getEncodedImage(imageRequest);
                if (encodedImage == null) {
                    return null;
                }
                encodedImage.parseMetaData();
                return encodedImage;
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks((StatefulProducerRunnable)object){
            final /* synthetic */ StatefulProducerRunnable val$cancellableProducerRunnable;
            {
                this.val$cancellableProducerRunnable = statefulProducerRunnable;
            }

            @Override
            public void onCancellationRequested() {
                this.val$cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute((Runnable)object);
    }

}

