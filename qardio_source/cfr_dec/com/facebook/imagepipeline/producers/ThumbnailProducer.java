/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.producers.Producer;

public interface ThumbnailProducer<T>
extends Producer<T> {
    public boolean canProvideImageForSize(ResizeOptions var1);
}

