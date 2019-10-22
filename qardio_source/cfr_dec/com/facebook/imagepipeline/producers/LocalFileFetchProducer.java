/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class LocalFileFetchProducer
extends LocalFetchProducer {
    public LocalFileFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean bl) {
        super(executor, pooledByteBufferFactory, bl);
    }

    @Override
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return this.getEncodedImage(new FileInputStream(imageRequest.getSourceFile().toString()), (int)imageRequest.getSourceFile().length());
    }

    @Override
    protected String getProducerName() {
        return "LocalFileFetchProducer";
    }
}

