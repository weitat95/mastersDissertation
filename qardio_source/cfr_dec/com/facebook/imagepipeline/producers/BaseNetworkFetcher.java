/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class BaseNetworkFetcher<FETCH_STATE extends FetchState>
implements NetworkFetcher<FETCH_STATE> {
    @Nullable
    @Override
    public Map<String, String> getExtraMap(FETCH_STATE FETCH_STATE, int n) {
        return null;
    }

    @Override
    public void onFetchCompletion(FETCH_STATE FETCH_STATE, int n) {
    }

    @Override
    public boolean shouldPropagate(FETCH_STATE FETCH_STATE) {
        return true;
    }
}

