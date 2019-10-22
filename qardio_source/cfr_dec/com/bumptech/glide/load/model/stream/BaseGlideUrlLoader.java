/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.bumptech.glide.load.model.stream;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import java.io.InputStream;

public abstract class BaseGlideUrlLoader<T>
implements StreamModelLoader<T> {
    private final ModelLoader<GlideUrl, InputStream> concreteLoader;
    private final ModelCache<T, GlideUrl> modelCache;

    public BaseGlideUrlLoader(Context context) {
        this(context, null);
    }

    public BaseGlideUrlLoader(Context context, ModelCache<T, GlideUrl> modelCache) {
        this(Glide.buildModelLoader(GlideUrl.class, InputStream.class, context), modelCache);
    }

    public BaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> modelLoader, ModelCache<T, GlideUrl> modelCache) {
        this.concreteLoader = modelLoader;
        this.modelCache = modelCache;
    }
}

