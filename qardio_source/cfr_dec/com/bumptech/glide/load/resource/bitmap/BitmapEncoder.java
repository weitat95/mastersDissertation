/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.util.Log
 */
package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import java.io.OutputStream;

public class BitmapEncoder
implements ResourceEncoder<Bitmap> {
    private Bitmap.CompressFormat compressFormat;
    private int quality;

    public BitmapEncoder() {
        this(null, 90);
    }

    public BitmapEncoder(Bitmap.CompressFormat compressFormat, int n) {
        this.compressFormat = compressFormat;
        this.quality = n;
    }

    private Bitmap.CompressFormat getFormat(Bitmap bitmap) {
        if (this.compressFormat != null) {
            return this.compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @Override
    public boolean encode(Resource<Bitmap> bitmap, OutputStream outputStream) {
        bitmap = bitmap.get();
        long l = LogTime.getLogTime();
        Bitmap.CompressFormat compressFormat = this.getFormat(bitmap);
        bitmap.compress(compressFormat, this.quality, outputStream);
        if (Log.isLoggable((String)"BitmapEncoder", (int)2)) {
            Log.v((String)"BitmapEncoder", (String)("Compressed with type: " + (Object)compressFormat + " of size " + Util.getBitmapByteSize(bitmap) + " in " + LogTime.getElapsedMillis(l)));
        }
        return true;
    }

    @Override
    public String getId() {
        return "BitmapEncoder.com.bumptech.glide.load.resource.bitmap";
    }
}

