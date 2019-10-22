/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

public interface ProducerContextCallbacks {
    public void onCancellationRequested();

    public void onIsIntermediateResultExpectedChanged();

    public void onIsPrefetchChanged();

    public void onPriorityChanged();
}

