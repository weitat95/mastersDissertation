/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;

public class BranchOnSeparateImagesProducer
implements Producer<EncodedImage> {
    private final Producer<EncodedImage> mInputProducer1;
    private final Producer<EncodedImage> mInputProducer2;

    public BranchOnSeparateImagesProducer(Producer<EncodedImage> producer, Producer<EncodedImage> producer2) {
        this.mInputProducer1 = producer;
        this.mInputProducer2 = producer2;
    }

    @Override
    public void produceResults(Consumer<EncodedImage> onFirstImageConsumer, ProducerContext producerContext) {
        onFirstImageConsumer = new OnFirstImageConsumer(onFirstImageConsumer, producerContext);
        this.mInputProducer1.produceResults(onFirstImageConsumer, producerContext);
    }

    private class OnFirstImageConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private ProducerContext mProducerContext;

        private OnFirstImageConsumer(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer);
            this.mProducerContext = producerContext;
        }

        @Override
        protected void onFailureImpl(Throwable throwable) {
            BranchOnSeparateImagesProducer.this.mInputProducer2.produceResults(this.getConsumer(), this.mProducerContext);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
            Object object = this.mProducerContext.getImageRequest();
            boolean bl2 = ThumbnailSizeChecker.isImageBigEnough(encodedImage, ((ImageRequest)object).getResizeOptions());
            if (encodedImage != null && (bl2 || ((ImageRequest)object).getLocalThumbnailPreviewsEnabled())) {
                object = this.getConsumer();
                boolean bl3 = bl && bl2;
                object.onNewResult(encodedImage, bl3);
            }
            if (bl && !bl2) {
                EncodedImage.closeSafely(encodedImage);
                BranchOnSeparateImagesProducer.this.mInputProducer2.produceResults(this.getConsumer(), this.mProducerContext);
            }
        }
    }

}

