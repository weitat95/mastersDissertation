/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.StatefulProducerRunnable;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import java.util.Map;

public class ThreadHandoffProducer<T>
implements Producer<T> {
    private final Producer<T> mInputProducer;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ThreadHandoffProducer(Producer<T> producer, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        this.mInputProducer = Preconditions.checkNotNull(producer);
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
    }

    @Override
    public void produceResults(Consumer<T> object, ProducerContext producerContext) {
        final ProducerListener producerListener = producerContext.getListener();
        final String string2 = producerContext.getId();
        object = new StatefulProducerRunnable<T>((Consumer)object, producerListener, "BackgroundThreadHandoffProducer", string2, (Consumer)object, producerContext){
            final /* synthetic */ Consumer val$consumer;
            final /* synthetic */ ProducerContext val$context;
            {
                this.val$consumer = consumer2;
                this.val$context = producerContext;
                super(consumer, producerListener3, string22, string3);
            }

            @Override
            protected void disposeResult(T t) {
            }

            @Override
            protected T getResult() throws Exception {
                return null;
            }

            @Override
            protected void onSuccess(T t) {
                producerListener.onProducerFinishWithSuccess(string2, "BackgroundThreadHandoffProducer", null);
                ThreadHandoffProducer.this.mInputProducer.produceResults(this.val$consumer, this.val$context);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks((StatefulProducerRunnable)object){
            final /* synthetic */ StatefulProducerRunnable val$statefulRunnable;
            {
                this.val$statefulRunnable = statefulProducerRunnable;
            }

            @Override
            public void onCancellationRequested() {
                this.val$statefulRunnable.cancel();
                ThreadHandoffProducer.this.mThreadHandoffProducerQueue.remove(this.val$statefulRunnable);
            }
        });
        this.mThreadHandoffProducerQueue.addToQueueOrExecute((Runnable)object);
    }

}

