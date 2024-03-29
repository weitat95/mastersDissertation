/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.bumptech.glide.load.model;

import android.net.Uri;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.File;

public class FileLoader<T>
implements ModelLoader<File, T> {
    private final ModelLoader<Uri, T> uriLoader;

    public FileLoader(ModelLoader<Uri, T> modelLoader) {
        this.uriLoader = modelLoader;
    }

    @Override
    public DataFetcher<T> getResourceFetcher(File file, int n, int n2) {
        return this.uriLoader.getResourceFetcher(Uri.fromFile((File)file), n, n2);
    }
}

