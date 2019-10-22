/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;

public class FetchState {
    private final Consumer<EncodedImage> mConsumer;
    private final ProducerContext mContext;
    private long mLastIntermediateResultTimeMs;

    public FetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        this.mConsumer = consumer;
        this.mContext = producerContext;
        this.mLastIntermediateResultTimeMs = 0L;
    }

    public Consumer<EncodedImage> getConsumer() {
        return this.mConsumer;
    }

    public ProducerContext getContext() {
        return this.mContext;
    }

    public String getId() {
        return this.mContext.getId();
    }

    public long getLastIntermediateResultTimeMs() {
        return this.mLastIntermediateResultTimeMs;
    }

    public ProducerListener getListener() {
        return this.mContext.getListener();
    }

    public Uri getUri() {
        return this.mContext.getImageRequest().getSourceUri();
    }

    public void setLastIntermediateResultTimeMs(long l) {
        this.mLastIntermediateResultTimeMs = l;
    }
}

