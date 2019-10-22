/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;

public class AddImageTransformMetaDataProducer
implements Producer<EncodedImage> {
    private final Producer<EncodedImage> mInputProducer;

    public AddImageTransformMetaDataProducer(Producer<EncodedImage> producer) {
        this.mInputProducer = producer;
    }

    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        this.mInputProducer.produceResults(new AddImageTransformMetaDataConsumer(consumer), producerContext);
    }

    private static class AddImageTransformMetaDataConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private AddImageTransformMetaDataConsumer(Consumer<EncodedImage> consumer) {
            super(consumer);
        }

        @Override
        protected void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
            if (encodedImage == null) {
                this.getConsumer().onNewResult(null, bl);
                return;
            }
            if (!EncodedImage.isMetaDataAvailable(encodedImage)) {
                encodedImage.parseMetaData();
            }
            this.getConsumer().onNewResult(encodedImage, bl);
        }
    }

}

