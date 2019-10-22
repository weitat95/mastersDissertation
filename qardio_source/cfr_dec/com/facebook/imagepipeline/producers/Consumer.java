/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

public interface Consumer<T> {
    public void onCancellation();

    public void onFailure(Throwable var1);

    public void onNewResult(T var1, boolean var2);

    public void onProgressUpdate(float var1);
}

