/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.nostra13.universalimageloader.core.download;

import android.content.Context;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

public class BaseImageDownloader
implements ImageDownloader {
    protected final int connectTimeout;
    protected final Context context;
    protected final int readTimeout;

    public BaseImageDownloader(Context context) {
        this(context, 5000, 20000);
    }

    public BaseImageDownloader(Context context, int n, int n2) {
        this.context = context.getApplicationContext();
        this.connectTimeout = n;
        this.readTimeout = n2;
    }
}

