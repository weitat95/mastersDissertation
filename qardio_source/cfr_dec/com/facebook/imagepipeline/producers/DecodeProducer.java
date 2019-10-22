/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.DownsampleUtil;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class DecodeProducer
implements Producer<CloseableReference<CloseableImage>> {
    private final ByteArrayPool mByteArrayPool;
    private final boolean mDownsampleEnabled;
    private final boolean mDownsampleEnabledForNetwork;
    private final Executor mExecutor;
    private final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    public DecodeProducer(ByteArrayPool byteArrayPool, Executor executor, ImageDecoder imageDecoder, ProgressiveJpegConfig progressiveJpegConfig, boolean bl, boolean bl2, Producer<EncodedImage> producer) {
        this.mByteArrayPool = Preconditions.checkNotNull(byteArrayPool);
        this.mExecutor = Preconditions.checkNotNull(executor);
        this.mImageDecoder = Preconditions.checkNotNull(imageDecoder);
        this.mProgressiveJpegConfig = Preconditions.checkNotNull(progressiveJpegConfig);
        this.mDownsampleEnabled = bl;
        this.mDownsampleEnabledForNetwork = bl2;
        this.mInputProducer = Preconditions.checkNotNull(producer);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void produceResults(Consumer<CloseableReference<CloseableImage>> progressiveDecoder, ProducerContext producerContext) {
        void var1_3;
        void var2_5;
        if (!UriUtil.isNetworkUri(var2_5.getImageRequest().getSourceUri())) {
            LocalImagesProgressiveDecoder localImagesProgressiveDecoder = new LocalImagesProgressiveDecoder(progressiveDecoder, (ProducerContext)var2_5);
        } else {
            NetworkImagesProgressiveDecoder networkImagesProgressiveDecoder = new NetworkImagesProgressiveDecoder(progressiveDecoder, (ProducerContext)var2_5, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig);
        }
        this.mInputProducer.produceResults((Consumer<EncodedImage>)var1_3, (ProducerContext)var2_5);
    }

    private class LocalImagesProgressiveDecoder
    extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }

        @Override
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        @Override
        protected QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.of(0, false, false);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected boolean updateDecodeJob(EncodedImage encodedImage, boolean bl) {
            synchronized (this) {
                if (!bl) {
                    return false;
                }
                bl = super.updateDecodeJob(encodedImage, bl);
                return bl;
            }
        }
    }

    private class NetworkImagesProgressiveDecoder
    extends ProgressiveDecoder {
        private int mLastScheduledScanNumber;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig) {
            super(consumer, producerContext);
            this.mProgressiveJpegParser = Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = Preconditions.checkNotNull(progressiveJpegConfig);
            this.mLastScheduledScanNumber = 0;
        }

        @Override
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        @Override
        protected QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected boolean updateDecodeJob(EncodedImage encodedImage, boolean bl) {
            synchronized (this) {
                boolean bl2;
                block5: {
                    boolean bl3 = bl2 = super.updateDecodeJob(encodedImage, bl);
                    if (bl) return bl3;
                    bl3 = bl2;
                    if (!EncodedImage.isValid(encodedImage)) return bl3;
                    bl = this.mProgressiveJpegParser.parseMoreData(encodedImage);
                    if (bl) break block5;
                    return false;
                }
                int n = this.mProgressiveJpegParser.getBestScanNumber();
                if (n <= this.mLastScheduledScanNumber) return false;
                if (n < this.mProgressiveJpegConfig.getNextScanNumberToDecode(this.mLastScheduledScanNumber)) return false;
                this.mLastScheduledScanNumber = n;
                return bl2;
            }
        }
    }

    private abstract class ProgressiveDecoder
    extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private final ImageDecodeOptions mImageDecodeOptions;
        @GuardedBy(value="this")
        private boolean mIsFinished;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;
        private final ProducerListener mProducerListener;

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> object, final ProducerContext producerContext) {
            super(object);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getListener();
            this.mImageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mIsFinished = false;
            object = new JobScheduler.JobRunnable(){

                @Override
                public void run(EncodedImage encodedImage, boolean bl) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled) {
                            ImageRequest imageRequest = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(imageRequest.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(imageRequest, encodedImage));
                            }
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, bl);
                    }
                }
            };
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, (JobScheduler.JobRunnable)object, this.mImageDecodeOptions.minDecodeIntervalMs);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(){

                @Override
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void doDecode(EncodedImage encodedImage, boolean bl) {
            if (this.isFinished()) return;
            if (!EncodedImage.isValid(encodedImage)) {
                return;
            }
            try {
                long l = this.mJobScheduler.getQueuedTime();
                int n = bl ? encodedImage.getSize() : this.getIntermediateImageEndOffset(encodedImage);
                Object object = bl ? ImmutableQualityInfo.FULL_QUALITY : this.getQualityInfo();
                this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                try {
                    CloseableImage closeableImage = DecodeProducer.this.mImageDecoder.decodeImage(encodedImage, n, (QualityInfo)object, this.mImageDecodeOptions);
                    object = this.getExtraMap(closeableImage, l, (QualityInfo)object, bl);
                    this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", (Map<String, String>)object);
                    this.handleResult(closeableImage, bl);
                    return;
                }
                catch (Exception exception) {
                    object = this.getExtraMap(null, l, (QualityInfo)object, bl);
                    this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), "DecodeProducer", exception, (Map<String, String>)object);
                    this.handleError(exception);
                    return;
                }
            }
            finally {
                EncodedImage.closeSafely(encodedImage);
            }
        }

        private Map<String, String> getExtraMap(@Nullable CloseableImage closeableImage, long l, QualityInfo object, boolean bl) {
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String string2 = String.valueOf(l);
            object = String.valueOf(object.isOfGoodEnoughQuality());
            String string3 = String.valueOf(bl);
            String string4 = String.valueOf((Object)this.mProducerContext.getImageRequest().getCacheChoice());
            if (closeableImage instanceof CloseableStaticBitmap) {
                closeableImage = ((CloseableStaticBitmap)closeableImage).getUnderlyingBitmap();
                return ImmutableMap.of("bitmapSize", closeableImage.getWidth() + "x" + closeableImage.getHeight(), "queueTime", string2, "hasGoodQuality", object, "isFinal", string3, "imageType", string4);
            }
            return ImmutableMap.of("queueTime", string2, "hasGoodQuality", object, "isFinal", string3, "imageType", string4);
        }

        private void handleCancellation() {
            this.maybeFinish(true);
            this.getConsumer().onCancellation();
        }

        private void handleError(Throwable throwable) {
            this.maybeFinish(true);
            this.getConsumer().onFailure(throwable);
        }

        private void handleResult(CloseableImage closeable, boolean bl) {
            closeable = CloseableReference.of(closeable);
            try {
                this.maybeFinish(bl);
                this.getConsumer().onNewResult(closeable, bl);
                return;
            }
            finally {
                CloseableReference.closeSafely(closeable);
            }
        }

        private boolean isFinished() {
            synchronized (this) {
                boolean bl = this.mIsFinished;
                return bl;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void maybeFinish(boolean bl) {
            synchronized (this) {
                if (bl && !this.mIsFinished) {
                    this.getConsumer().onProgressUpdate(1.0f);
                    this.mIsFinished = true;
                    // MONITOREXIT [2, 3] lbl5 : MonitorExitStatement: MONITOREXIT : this
                    this.mJobScheduler.clearJob();
                    return;
                }
                return;
            }
        }

        protected abstract int getIntermediateImageEndOffset(EncodedImage var1);

        protected abstract QualityInfo getQualityInfo();

        @Override
        public void onCancellationImpl() {
            this.handleCancellation();
        }

        @Override
        public void onFailureImpl(Throwable throwable) {
            this.handleError(throwable);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
            if (bl && !EncodedImage.isValid(encodedImage)) {
                this.handleError(new NullPointerException("Encoded image is not valid."));
                return;
            } else {
                if (!this.updateDecodeJob(encodedImage, bl) || !bl && !this.mProducerContext.isIntermediateResultExpected()) return;
                {
                    this.mJobScheduler.scheduleJob();
                    return;
                }
            }
        }

        @Override
        protected void onProgressUpdateImpl(float f) {
            super.onProgressUpdateImpl(0.99f * f);
        }

        protected boolean updateDecodeJob(EncodedImage encodedImage, boolean bl) {
            return this.mJobScheduler.updateJob(encodedImage, bl);
        }

    }

}

