/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.model;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import java.net.URL;

public class UrlLoader<T>
implements ModelLoader<URL, T> {
    private final ModelLoader<GlideUrl, T> glideUrlLoader;

    public UrlLoader(ModelLoader<GlideUrl, T> modelLoader) {
        this.glideUrlLoader = modelLoader;
    }

    @Override
    public DataFetcher<T> getResourceFetcher(URL uRL, int n, int n2) {
        return this.glideUrlLoader.getResourceFetcher(new GlideUrl(uRL), n, n2);
    }
}

