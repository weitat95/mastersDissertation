/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

public class NetworkFetchProducer
implements Producer<EncodedImage> {
    private final ByteArrayPool mByteArrayPool;
    private final NetworkFetcher mNetworkFetcher;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public NetworkFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, NetworkFetcher networkFetcher) {
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mNetworkFetcher = networkFetcher;
    }

    private static float calculateProgress(int n, int n2) {
        if (n2 > 0) {
            return (float)n / (float)n2;
        }
        return 1.0f - (float)Math.exp((double)(-n) / 50000.0);
    }

    @Nullable
    private Map<String, String> getExtraMap(FetchState fetchState, int n) {
        if (!fetchState.getListener().requiresExtraMap(fetchState.getId())) {
            return null;
        }
        return this.mNetworkFetcher.getExtraMap(fetchState, n);
    }

    private void handleFinalResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        Map<String, String> map = this.getExtraMap(fetchState, pooledByteBufferOutputStream.size());
        fetchState.getListener().onProducerFinishWithSuccess(fetchState.getId(), "NetworkFetchProducer", map);
        this.notifyConsumer(pooledByteBufferOutputStream, true, fetchState.getConsumer());
    }

    private void maybeHandleIntermediateResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        long l = SystemClock.uptimeMillis();
        if (this.shouldPropagateIntermediateResults(fetchState) && l - fetchState.getLastIntermediateResultTimeMs() >= 100L) {
            fetchState.setLastIntermediateResultTimeMs(l);
            fetchState.getListener().onProducerEvent(fetchState.getId(), "NetworkFetchProducer", "intermediate_result");
            this.notifyConsumer(pooledByteBufferOutputStream, false, fetchState.getConsumer());
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyConsumer(PooledByteBufferOutputStream closeable, boolean bl, Consumer<EncodedImage> consumer) {
        EncodedImage encodedImage;
        CloseableReference<PooledByteBuffer> closeableReference = CloseableReference.of(((PooledByteBufferOutputStream)closeable).toByteBuffer());
        Object var1_2 = null;
        try {
            encodedImage = new EncodedImage(closeableReference);
        }
        catch (Throwable throwable) {}
        try {
            void var3_6;
            void var2_5;
            encodedImage.parseMetaData();
            var3_6.onNewResult(encodedImage, (boolean)var2_5);
        }
        catch (Throwable throwable) {
            EncodedImage encodedImage2 = encodedImage;
        }
        EncodedImage.closeSafely(encodedImage);
        CloseableReference.closeSafely(closeableReference);
        return;
        {
            void var1_3;
            void var3_8;
            EncodedImage.closeSafely((EncodedImage)var1_3);
            CloseableReference.closeSafely(closeableReference);
            throw var3_8;
        }
    }

    private void onCancellation(FetchState fetchState) {
        fetchState.getListener().onProducerFinishWithCancellation(fetchState.getId(), "NetworkFetchProducer", null);
        fetchState.getConsumer().onCancellation();
    }

    private void onFailure(FetchState fetchState, Throwable throwable) {
        fetchState.getListener().onProducerFinishWithFailure(fetchState.getId(), "NetworkFetchProducer", throwable, null);
        fetchState.getConsumer().onFailure(throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onResponse(FetchState fetchState, InputStream inputStream, int n) throws IOException {
        PooledByteBufferOutputStream pooledByteBufferOutputStream = n > 0 ? this.mPooledByteBufferFactory.newOutputStream(n) : this.mPooledByteBufferFactory.newOutputStream();
        byte[] arrby = (byte[])this.mByteArrayPool.get(16384);
        try {
            int n2;
            while ((n2 = inputStream.read(arrby)) >= 0) {
                if (n2 <= 0) continue;
                pooledByteBufferOutputStream.write(arrby, 0, n2);
                this.maybeHandleIntermediateResult(pooledByteBufferOutputStream, fetchState);
                float f = NetworkFetchProducer.calculateProgress(pooledByteBufferOutputStream.size(), n);
                fetchState.getConsumer().onProgressUpdate(f);
            }
            this.mNetworkFetcher.onFetchCompletion(fetchState, pooledByteBufferOutputStream.size());
            this.handleFinalResult(pooledByteBufferOutputStream, fetchState);
            return;
        }
        finally {
            this.mByteArrayPool.release(arrby);
            pooledByteBufferOutputStream.close();
        }
    }

    private boolean shouldPropagateIntermediateResults(FetchState fetchState) {
        if (!fetchState.getContext().getImageRequest().getProgressiveRenderingEnabled()) {
            return false;
        }
        return this.mNetworkFetcher.shouldPropagate(fetchState);
    }

    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        producerContext.getListener().onProducerStart(producerContext.getId(), "NetworkFetchProducer");
        consumer = this.mNetworkFetcher.createFetchState(consumer, producerContext);
        this.mNetworkFetcher.fetch(consumer, new NetworkFetcher.Callback((FetchState)((Object)consumer)){
            final /* synthetic */ FetchState val$fetchState;
            {
                this.val$fetchState = fetchState;
            }

            @Override
            public void onCancellation() {
                NetworkFetchProducer.this.onCancellation(this.val$fetchState);
            }

            @Override
            public void onFailure(Throwable throwable) {
                NetworkFetchProducer.this.onFailure(this.val$fetchState, throwable);
            }

            @Override
            public void onResponse(InputStream inputStream, int n) throws IOException {
                NetworkFetchProducer.this.onResponse(this.val$fetchState, inputStream, n);
            }
        });
    }

}

