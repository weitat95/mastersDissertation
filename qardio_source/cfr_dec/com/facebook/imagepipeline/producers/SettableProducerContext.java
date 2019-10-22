/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.producers.BaseProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class SettableProducerContext
extends BaseProducerContext {
    public SettableProducerContext(ImageRequest imageRequest, String string2, ProducerListener producerListener, Object object, ImageRequest.RequestLevel requestLevel, boolean bl, boolean bl2, Priority priority) {
        super(imageRequest, string2, producerListener, object, requestLevel, bl, bl2, priority);
    }
}

