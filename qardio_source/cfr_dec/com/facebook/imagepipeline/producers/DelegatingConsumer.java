/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.producers.BaseConsumer;
import com.facebook.imagepipeline.producers.Consumer;

public abstract class DelegatingConsumer<I, O>
extends BaseConsumer<I> {
    private final Consumer<O> mConsumer;

    public DelegatingConsumer(Consumer<O> consumer) {
        this.mConsumer = consumer;
    }

    public Consumer<O> getConsumer() {
        return this.mConsumer;
    }

    @Override
    protected void onCancellationImpl() {
        this.mConsumer.onCancellation();
    }

    @Override
    protected void onFailureImpl(Throwable throwable) {
        this.mConsumer.onFailure(throwable);
    }

    @Override
    protected void onProgressUpdateImpl(float f) {
        this.mConsumer.onProgressUpdate(f);
    }
}

