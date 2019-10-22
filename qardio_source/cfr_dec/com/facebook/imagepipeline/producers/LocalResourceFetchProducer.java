/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetFileDescriptor
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.net.Uri
 */
package com.facebook.imagepipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class LocalResourceFetchProducer
extends LocalFetchProducer {
    private final Resources mResources;

    public LocalResourceFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Resources resources, boolean bl) {
        super(executor, pooledByteBufferFactory, bl);
        this.mResources = resources;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int getLength(ImageRequest imageRequest) {
        int n;
        ImageRequest imageRequest2 = null;
        ImageRequest imageRequest3 = null;
        imageRequest3 = imageRequest = this.mResources.openRawResourceFd(LocalResourceFetchProducer.getResourceId(imageRequest));
        imageRequest2 = imageRequest;
        long l = imageRequest.getLength();
        int n2 = n = (int)l;
        if (imageRequest == null) return n2;
        imageRequest.close();
        return n;
        {
            catch (IOException iOException) {
                return n;
            }
        }
        catch (Resources.NotFoundException notFoundException) {
            n2 = -1;
            if (imageRequest3 == null) return n2;
            try {
                imageRequest3.close();
                return -1;
            }
            catch (IOException iOException) {
                return -1;
            }
        }
        catch (Throwable throwable) {
            if (imageRequest2 == null) throw throwable;
            try {
                imageRequest2.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
    }

    private static int getResourceId(ImageRequest imageRequest) {
        return Integer.parseInt(imageRequest.getSourceUri().getPath().substring(1));
    }

    @Override
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return this.getEncodedImage(this.mResources.openRawResource(LocalResourceFetchProducer.getResourceId(imageRequest)), this.getLength(imageRequest));
    }

    @Override
    protected String getProducerName() {
        return "LocalResourceFetchProducer";
    }
}

