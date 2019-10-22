/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;

public class ThumbnailBranchProducer
implements Producer<EncodedImage> {
    private final ThumbnailProducer<EncodedImage>[] mThumbnailProducers;

    public ThumbnailBranchProducer(ThumbnailProducer<EncodedImage> ... arrthumbnailProducer) {
        this.mThumbnailProducers = Preconditions.checkNotNull(arrthumbnailProducer);
        Preconditions.checkElementIndex(0, this.mThumbnailProducers.length);
    }

    private int findFirstProducerForSize(int n, ResizeOptions resizeOptions) {
        while (n < this.mThumbnailProducers.length) {
            if (this.mThumbnailProducers[n].canProvideImageForSize(resizeOptions)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private boolean produceResultsFromThumbnailProducer(int n, Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        if ((n = this.findFirstProducerForSize(n, producerContext.getImageRequest().getResizeOptions())) == -1) {
            return false;
        }
        this.mThumbnailProducers[n].produceResults(new ThumbnailConsumer(consumer, producerContext, n), producerContext);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        if (producerContext.getImageRequest().getResizeOptions() == null) {
            consumer.onNewResult(null, true);
            return;
        } else {
            if (this.produceResultsFromThumbnailProducer(0, consumer, producerContext)) return;
            {
                consumer.onNewResult(null, true);
                return;
            }
        }
    }

    private class ThumbnailConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ProducerContext mProducerContext;
        private final int mProducerIndex;
        private final ResizeOptions mResizeOptions;

        public ThumbnailConsumer(Consumer<EncodedImage> consumer, ProducerContext producerContext, int n) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerIndex = n;
            this.mResizeOptions = this.mProducerContext.getImageRequest().getResizeOptions();
        }

        @Override
        protected void onFailureImpl(Throwable throwable) {
            if (!ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, this.getConsumer(), this.mProducerContext)) {
                this.getConsumer().onFailure(throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
            if (encodedImage != null && (!bl || ThumbnailSizeChecker.isImageBigEnough(encodedImage, this.mResizeOptions))) {
                this.getConsumer().onNewResult(encodedImage, bl);
                return;
            } else {
                if (!bl) return;
                {
                    EncodedImage.closeSafely(encodedImage);
                    if (ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, this.getConsumer(), this.mProducerContext)) return;
                    {
                        this.getConsumer().onNewResult(null, true);
                        return;
                    }
                }
            }
        }
    }

}

