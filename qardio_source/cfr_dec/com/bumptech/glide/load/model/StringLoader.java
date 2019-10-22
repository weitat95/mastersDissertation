/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.bumptech.glide.load.model;

import android.net.Uri;
import android.text.TextUtils;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.File;

public class StringLoader<T>
implements ModelLoader<String, T> {
    private final ModelLoader<Uri, T> uriLoader;

    public StringLoader(ModelLoader<Uri, T> modelLoader) {
        this.uriLoader = modelLoader;
    }

    private static Uri toFileUri(String string2) {
        return Uri.fromFile((File)new File(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DataFetcher<T> getResourceFetcher(String string2, int n, int n2) {
        Uri uri;
        Uri uri2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        if (string2.startsWith("/")) {
            uri = StringLoader.toFileUri(string2);
            return this.uriLoader.getResourceFetcher(uri, n, n2);
        }
        uri = uri2 = Uri.parse((String)string2);
        if (uri2.getScheme() != null) return this.uriLoader.getResourceFetcher(uri, n, n2);
        uri = StringLoader.toFileUri(string2);
        return this.uriLoader.getResourceFetcher(uri, n, n2);
    }
}

