/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.datasource;

import com.facebook.common.internal.Preconditions;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.BaseConsumer;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class AbstractProducerToDataSourceAdapter<T>
extends AbstractDataSource<T> {
    private final RequestListener mRequestListener;
    private final SettableProducerContext mSettableProducerContext;

    protected AbstractProducerToDataSourceAdapter(Producer<T> producer, SettableProducerContext settableProducerContext, RequestListener requestListener) {
        this.mSettableProducerContext = settableProducerContext;
        this.mRequestListener = requestListener;
        this.mRequestListener.onRequestStart(settableProducerContext.getImageRequest(), this.mSettableProducerContext.getCallerContext(), this.mSettableProducerContext.getId(), this.mSettableProducerContext.isPrefetch());
        producer.produceResults(this.createConsumer(), settableProducerContext);
    }

    private Consumer<T> createConsumer() {
        return new BaseConsumer<T>(){

            @Override
            protected void onCancellationImpl() {
                AbstractProducerToDataSourceAdapter.this.onCancellationImpl();
            }

            @Override
            protected void onFailureImpl(Throwable throwable) {
                AbstractProducerToDataSourceAdapter.this.onFailureImpl(throwable);
            }

            @Override
            protected void onNewResultImpl(@Nullable T t, boolean bl) {
                AbstractProducerToDataSourceAdapter.this.onNewResultImpl(t, bl);
            }

            @Override
            protected void onProgressUpdateImpl(float f) {
                AbstractProducerToDataSourceAdapter.this.setProgress(f);
            }
        };
    }

    private void onCancellationImpl() {
        synchronized (this) {
            Preconditions.checkState(this.isClosed());
            return;
        }
    }

    private void onFailureImpl(Throwable throwable) {
        if (super.setFailure(throwable)) {
            this.mRequestListener.onRequestFailure(this.mSettableProducerContext.getImageRequest(), this.mSettableProducerContext.getId(), throwable, this.mSettableProducerContext.isPrefetch());
        }
    }

    @Override
    public boolean close() {
        if (!super.close()) {
            return false;
        }
        if (!super.isFinished()) {
            this.mRequestListener.onRequestCancellation(this.mSettableProducerContext.getId());
            this.mSettableProducerContext.cancel();
        }
        return true;
    }

    protected void onNewResultImpl(@Nullable T t, boolean bl) {
        if (super.setResult(t, bl) && bl) {
            this.mRequestListener.onRequestSuccess(this.mSettableProducerContext.getImageRequest(), this.mSettableProducerContext.getId(), this.mSettableProducerContext.isPrefetch());
        }
    }

}

