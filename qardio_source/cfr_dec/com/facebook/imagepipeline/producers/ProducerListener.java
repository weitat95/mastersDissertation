/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import java.util.Map;
import javax.annotation.Nullable;

public interface ProducerListener {
    public void onProducerEvent(String var1, String var2, String var3);

    public void onProducerFinishWithCancellation(String var1, String var2, @Nullable Map<String, String> var3);

    public void onProducerFinishWithFailure(String var1, String var2, Throwable var3, @Nullable Map<String, String> var4);

    public void onProducerFinishWithSuccess(String var1, String var2, @Nullable Map<String, String> var3);

    public void onProducerStart(String var1, String var2);

    public boolean requiresExtraMap(String var1);
}

