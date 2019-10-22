/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.nativecode.WebpTranscoder;
import com.facebook.imagepipeline.nativecode.WebpTranscoderFactory;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class WebpTranscodeProducer
implements Producer<EncodedImage> {
    private final int mEnhancedTranscodingType;
    private final Executor mExecutor;
    private final Producer<EncodedImage> mInputProducer;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public WebpTranscodeProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Producer<EncodedImage> producer, int n) {
        this.mExecutor = Preconditions.checkNotNull(executor);
        this.mPooledByteBufferFactory = Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mInputProducer = Preconditions.checkNotNull(producer);
        this.mEnhancedTranscodingType = n;
    }

    private static void doTranscode(EncodedImage closeable, PooledByteBufferOutputStream pooledByteBufferOutputStream, int n) throws Exception {
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException((InputStream)(closeable = closeable.getInputStream()));
        if (imageFormat == DefaultImageFormats.WEBP_SIMPLE || imageFormat == DefaultImageFormats.WEBP_EXTENDED) {
            if (n == 0) {
                WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToJpeg((InputStream)closeable, pooledByteBufferOutputStream, 80);
            }
            return;
        }
        if (imageFormat == DefaultImageFormats.WEBP_LOSSLESS || imageFormat == DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA) {
            WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToPng((InputStream)closeable, pooledByteBufferOutputStream);
            return;
        }
        throw new IllegalArgumentException("Wrong image format");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static TriState shouldTranscode(EncodedImage object) {
        Preconditions.checkNotNull(object);
        object = ImageFormatChecker.getImageFormat_WrapIOException(((EncodedImage)object).getInputStream());
        if (DefaultImageFormats.isStaticWebpFormat((ImageFormat)object)) {
            boolean bl;
            WebpTranscoder webpTranscoder = WebpTranscoderFactory.getWebpTranscoder();
            if (webpTranscoder == null) {
                return TriState.NO;
            }
            if (!webpTranscoder.isWebpNativelySupported((ImageFormat)object)) {
                bl = true;
                do {
                    return TriState.valueOf(bl);
                    break;
                } while (true);
            }
            bl = false;
            return TriState.valueOf(bl);
        }
        if (object != ImageFormat.UNKNOWN) return TriState.NO;
        return TriState.UNSET;
    }

    private void transcodeLastResult(EncodedImage object, Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        Preconditions.checkNotNull(object);
        object = EncodedImage.cloneOrNull((EncodedImage)object);
        object = new StatefulProducerRunnable<EncodedImage>(consumer, producerContext.getListener(), "WebpTranscodeProducer", producerContext.getId(), (EncodedImage)object){
            final /* synthetic */ EncodedImage val$encodedImageCopy;
            {
                this.val$encodedImageCopy = encodedImage;
                super(consumer, producerListener, string2, string3);
            }

            @Override
            protected void disposeResult(EncodedImage encodedImage) {
                EncodedImage.closeSafely(encodedImage);
            }

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            protected EncodedImage getResult() throws Exception {
                EncodedImage encodedImage;
                PooledByteBufferOutputStream pooledByteBufferOutputStream = WebpTranscodeProducer.this.mPooledByteBufferFactory.newOutputStream();
                WebpTranscodeProducer.doTranscode(this.val$encodedImageCopy, pooledByteBufferOutputStream, WebpTranscodeProducer.this.mEnhancedTranscodingType);
                CloseableReference<PooledByteBuffer> closeableReference = CloseableReference.of(pooledByteBufferOutputStream.toByteBuffer());
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
                try {
                    encodedImage = new EncodedImage(closeableReference);
                    encodedImage.copyMetaDataFrom(this.val$encodedImageCopy);
                }
                catch (Throwable throwable) {
                    CloseableReference.closeSafely(closeableReference);
                    throw throwable;
                }
                try {
                    CloseableReference.closeSafely(closeableReference);
                    return encodedImage;
                }
                finally {
                    pooledByteBufferOutputStream.close();
                }
            }

            @Override
            protected void onCancellation() {
                EncodedImage.closeSafely(this.val$encodedImageCopy);
                super.onCancellation();
            }

            @Override
            protected void onFailure(Exception exception) {
                EncodedImage.closeSafely(this.val$encodedImageCopy);
                super.onFailure(exception);
            }

            @Override
            protected void onSuccess(EncodedImage encodedImage) {
                EncodedImage.closeSafely(this.val$encodedImageCopy);
                super.onSuccess(encodedImage);
            }
        };
        this.mExecutor.execute((Runnable)object);
    }

    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        this.mInputProducer.produceResults(new WebpTranscodeConsumer(consumer, producerContext), producerContext);
    }

    private class WebpTranscodeConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ProducerContext mContext;
        private TriState mShouldTranscodeWhenFinished;

        public WebpTranscodeConsumer(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer);
            this.mContext = producerContext;
            this.mShouldTranscodeWhenFinished = TriState.UNSET;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void onNewResultImpl(@Nullable EncodedImage encodedImage, boolean bl) {
            if (this.mShouldTranscodeWhenFinished == TriState.UNSET && encodedImage != null) {
                this.mShouldTranscodeWhenFinished = WebpTranscodeProducer.shouldTranscode(encodedImage);
            }
            if (this.mShouldTranscodeWhenFinished == TriState.NO) {
                this.getConsumer().onNewResult(encodedImage, bl);
                return;
            }
            if (!bl) return;
            {
                if (this.mShouldTranscodeWhenFinished == TriState.YES && encodedImage != null) {
                    WebpTranscodeProducer.this.transcodeLastResult(encodedImage, this.getConsumer(), this.mContext);
                    return;
                }
            }
            this.getConsumer().onNewResult(encodedImage, bl);
        }
    }

}

