/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Base64
 */
package com.facebook.imagepipeline.producers;

import android.net.Uri;
import android.util.Base64;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class DataFetchProducer
extends LocalFetchProducer {
    public DataFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, boolean bl) {
        super(CallerThreadExecutor.getInstance(), pooledByteBufferFactory, bl);
    }

    static byte[] getData(String string2) {
        Preconditions.checkArgument(string2.substring(0, 5).equals("data:"));
        int n = string2.indexOf(44);
        String string3 = string2.substring(n + 1, string2.length());
        if (DataFetchProducer.isBase64(string2.substring(0, n))) {
            return Base64.decode((String)string3, (int)0);
        }
        return Uri.decode((String)string3).getBytes();
    }

    static boolean isBase64(String arrstring) {
        if (!arrstring.contains(";")) {
            return false;
        }
        arrstring = arrstring.split(";");
        return arrstring[arrstring.length - 1].equals("base64");
    }

    @Override
    protected EncodedImage getEncodedImage(ImageRequest arrby) throws IOException {
        arrby = DataFetchProducer.getData(arrby.getSourceUri().toString());
        return this.getByteBufferBackedEncodedImage(new ByteArrayInputStream(arrby), arrby.length);
    }

    @Override
    protected String getProducerName() {
        return "DataFetchProducer";
    }
}

