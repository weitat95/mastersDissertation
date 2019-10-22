/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.model;

import android.util.Log;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.util.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamEncoder
implements Encoder<InputStream> {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean encode(InputStream inputStream, OutputStream outputStream) {
        byte[] arrby = ByteArrayPool.get().getBytes();
        try {
            try {
                int n;
                while ((n = inputStream.read(arrby)) != -1) {
                    outputStream.write(arrby, 0, n);
                }
                return true;
            }
            catch (IOException iOException) {
                if (Log.isLoggable((String)"StreamEncoder", (int)3)) {
                    Log.d((String)"StreamEncoder", (String)"Failed to encode data onto the OutputStream", (Throwable)iOException);
                }
                ByteArrayPool.get().releaseBytes(arrby);
                return false;
            }
        }
        finally {
            ByteArrayPool.get().releaseBytes(arrby);
        }
    }

    @Override
    public String getId() {
        return "";
    }
}

