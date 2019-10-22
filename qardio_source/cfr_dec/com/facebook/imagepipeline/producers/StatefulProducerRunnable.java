/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.executors.StatefulRunnable;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.ProducerListener;
import java.util.Map;

public abstract class StatefulProducerRunnable<T>
extends StatefulRunnable<T> {
    private final Consumer<T> mConsumer;
    private final ProducerListener mProducerListener;
    private final String mProducerName;
    private final String mRequestId;

    public StatefulProducerRunnable(Consumer<T> consumer, ProducerListener producerListener, String string2, String string3) {
        this.mConsumer = consumer;
        this.mProducerListener = producerListener;
        this.mProducerName = string2;
        this.mRequestId = string3;
        this.mProducerListener.onProducerStart(this.mRequestId, this.mProducerName);
    }

    @Override
    protected abstract void disposeResult(T var1);

    protected Map<String, String> getExtraMapOnCancellation() {
        return null;
    }

    protected Map<String, String> getExtraMapOnFailure(Exception exception) {
        return null;
    }

    protected Map<String, String> getExtraMapOnSuccess(T t) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCancellation() {
        ProducerListener producerListener = this.mProducerListener;
        String string2 = this.mRequestId;
        String string3 = this.mProducerName;
        Map<String, String> map = this.mProducerListener.requiresExtraMap(this.mRequestId) ? this.getExtraMapOnCancellation() : null;
        producerListener.onProducerFinishWithCancellation(string2, string3, map);
        this.mConsumer.onCancellation();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onFailure(Exception exception) {
        ProducerListener producerListener = this.mProducerListener;
        String string2 = this.mRequestId;
        String string3 = this.mProducerName;
        Map<String, String> map = this.mProducerListener.requiresExtraMap(this.mRequestId) ? this.getExtraMapOnFailure(exception) : null;
        producerListener.onProducerFinishWithFailure(string2, string3, exception, map);
        this.mConsumer.onFailure(exception);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSuccess(T t) {
        ProducerListener producerListener = this.mProducerListener;
        String string2 = this.mRequestId;
        String string3 = this.mProducerName;
        Map<String, String> map = this.mProducerListener.requiresExtraMap(this.mRequestId) ? this.getExtraMapOnSuccess(t) : null;
        producerListener.onProducerFinishWithSuccess(string2, string3, map);
        this.mConsumer.onNewResult(t, true);
    }
}

