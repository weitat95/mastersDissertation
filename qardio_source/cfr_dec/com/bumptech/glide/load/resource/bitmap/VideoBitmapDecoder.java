/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.media.MediaMetadataRetriever
 *  android.os.ParcelFileDescriptor
 */
package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapDecoder;
import java.io.FileDescriptor;
import java.io.IOException;

public class VideoBitmapDecoder
implements BitmapDecoder<ParcelFileDescriptor> {
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();
    private MediaMetadataRetrieverFactory factory;
    private int frame;

    public VideoBitmapDecoder() {
        this(DEFAULT_FACTORY, -1);
    }

    VideoBitmapDecoder(MediaMetadataRetrieverFactory mediaMetadataRetrieverFactory, int n) {
        this.factory = mediaMetadataRetrieverFactory;
        this.frame = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Bitmap decode(ParcelFileDescriptor parcelFileDescriptor, BitmapPool bitmapPool, int n, int n2, DecodeFormat decodeFormat) throws IOException {
        decodeFormat = this.factory.build();
        decodeFormat.setDataSource(parcelFileDescriptor.getFileDescriptor());
        bitmapPool = this.frame >= 0 ? decodeFormat.getFrameAtTime((long)this.frame) : decodeFormat.getFrameAtTime();
        decodeFormat.release();
        parcelFileDescriptor.close();
        return bitmapPool;
    }

    @Override
    public String getId() {
        return "VideoBitmapDecoder.com.bumptech.glide.load.resource.bitmap";
    }

    static class MediaMetadataRetrieverFactory {
        MediaMetadataRetrieverFactory() {
        }

        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }

}

