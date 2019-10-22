/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.ProducerContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

public interface NetworkFetcher<FETCH_STATE extends FetchState> {
    public FETCH_STATE createFetchState(Consumer<EncodedImage> var1, ProducerContext var2);

    public void fetch(FETCH_STATE var1, Callback var2);

    @Nullable
    public Map<String, String> getExtraMap(FETCH_STATE var1, int var2);

    public void onFetchCompletion(FETCH_STATE var1, int var2);

    public boolean shouldPropagate(FETCH_STATE var1);

    public static interface Callback {
        public void onCancellation();

        public void onFailure(Throwable var1);

        public void onResponse(InputStream var1, int var2) throws IOException;
    }

}

