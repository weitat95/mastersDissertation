/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.datasource;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.AbstractProducerToDataSourceAdapter;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableProducerToDataSourceAdapter<T>
extends AbstractProducerToDataSourceAdapter<CloseableReference<T>> {
    private CloseableProducerToDataSourceAdapter(Producer<CloseableReference<T>> producer, SettableProducerContext settableProducerContext, RequestListener requestListener) {
        super(producer, settableProducerContext, requestListener);
    }

    public static <T> DataSource<CloseableReference<T>> create(Producer<CloseableReference<T>> producer, SettableProducerContext settableProducerContext, RequestListener requestListener) {
        return new CloseableProducerToDataSourceAdapter<T>(producer, settableProducerContext, requestListener);
    }

    @Override
    protected void closeResult(CloseableReference<T> closeableReference) {
        CloseableReference.closeSafely(closeableReference);
    }

    @Nullable
    @Override
    public CloseableReference<T> getResult() {
        return CloseableReference.cloneOrNull((CloseableReference)super.getResult());
    }

    @Override
    protected void onNewResultImpl(CloseableReference<T> closeableReference, boolean bl) {
        super.onNewResultImpl(CloseableReference.cloneOrNull(closeableReference), bl);
    }
}

