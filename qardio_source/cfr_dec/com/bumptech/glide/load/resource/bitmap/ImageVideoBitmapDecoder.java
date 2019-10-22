/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.ParcelFileDescriptor
 *  android.util.Log
 */
package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import java.io.IOException;
import java.io.InputStream;

public class ImageVideoBitmapDecoder
implements ResourceDecoder<ImageVideoWrapper, Bitmap> {
    private final ResourceDecoder<ParcelFileDescriptor, Bitmap> fileDescriptorDecoder;
    private final ResourceDecoder<InputStream, Bitmap> streamDecoder;

    public ImageVideoBitmapDecoder(ResourceDecoder<InputStream, Bitmap> resourceDecoder, ResourceDecoder<ParcelFileDescriptor, Bitmap> resourceDecoder2) {
        this.streamDecoder = resourceDecoder;
        this.fileDescriptorDecoder = resourceDecoder2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Resource<Bitmap> decode(ImageVideoWrapper imageVideoWrapper, int n, int n2) throws IOException {
        Resource<Bitmap> resource;
        Resource<Bitmap> resource2;
        block3: {
            resource = null;
            InputStream inputStream = imageVideoWrapper.getStream();
            resource2 = resource;
            if (inputStream != null) {
                try {
                    resource2 = this.streamDecoder.decode(inputStream, n, n2);
                }
                catch (IOException iOException) {
                    resource2 = resource;
                    if (!Log.isLoggable((String)"ImageVideoDecoder", (int)2)) break block3;
                    Log.v((String)"ImageVideoDecoder", (String)"Failed to load image from stream, trying FileDescriptor", (Throwable)iOException);
                    resource2 = resource;
                }
            }
        }
        resource = resource2;
        if (resource2 != null) return resource;
        imageVideoWrapper = imageVideoWrapper.getFileDescriptor();
        resource = resource2;
        if (imageVideoWrapper == null) return resource;
        return this.fileDescriptorDecoder.decode((ParcelFileDescriptor)imageVideoWrapper, n, n2);
    }

    @Override
    public String getId() {
        return "ImageVideoBitmapDecoder.com.bumptech.glide.load.resource.bitmap";
    }
}

