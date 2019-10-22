/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.listener;

import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;

public interface RequestListener
extends ProducerListener {
    public void onRequestCancellation(String var1);

    public void onRequestFailure(ImageRequest var1, String var2, Throwable var3, boolean var4);

    public void onRequestStart(ImageRequest var1, Object var2, String var3, boolean var4);

    public void onRequestSuccess(ImageRequest var1, String var2, boolean var3);
}

