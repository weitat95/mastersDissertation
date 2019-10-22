/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;

public interface ProducerContext {
    public void addCallbacks(ProducerContextCallbacks var1);

    public Object getCallerContext();

    public String getId();

    public ImageRequest getImageRequest();

    public ProducerListener getListener();

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel();

    public Priority getPriority();

    public boolean isIntermediateResultExpected();

    public boolean isPrefetch();
}

