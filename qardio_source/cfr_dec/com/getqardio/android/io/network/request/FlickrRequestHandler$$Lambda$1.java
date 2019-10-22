/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import com.getqardio.android.io.network.request.FlickrRequestHandler;
import java.lang.invoke.LambdaForm;

final class FlickrRequestHandler$$Lambda$1
implements Runnable {
    private final Context arg$1;
    private final FlickrPhotoMetadata arg$2;

    private FlickrRequestHandler$$Lambda$1(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
        this.arg$1 = context;
        this.arg$2 = flickrPhotoMetadata;
    }

    public static Runnable lambdaFactory$(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
        return new FlickrRequestHandler$$Lambda$1(context, flickrPhotoMetadata);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        FlickrRequestHandler.lambda$loadFlickrPhoto$0(this.arg$1, this.arg$2);
    }
}

