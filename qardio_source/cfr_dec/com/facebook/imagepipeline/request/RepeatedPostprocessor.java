/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.request;

import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;

public interface RepeatedPostprocessor
extends Postprocessor {
    public void setCallback(RepeatedPostprocessorRunner var1);
}

