/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

public class ThrottlingProducer<T>
implements Producer<T> {
    private final Executor mExecutor;
    private final Producer<T> mInputProducer;
    private final int mMaxSimultaneousRequests;
    @GuardedBy(value="this")
    private int mNumCurrentRequests;
    @GuardedBy(value="this")
    private final ConcurrentLinkedQueue<Pair<Consumer<T>, ProducerContext>> mPendingRequests;

    public ThrottlingProducer(int n, Executor executor, Producer<T> producer) {
        this.mMaxSimultaneousRequests = n;
        this.mExecutor = Preconditions.checkNotNull(executor);
        this.mInputProducer = Preconditions.checkNotNull(producer);
        this.mPendingRequests = new ConcurrentLinkedQueue();
        this.mNumCurrentRequests = 0;
    }

    static /* synthetic */ int access$210(ThrottlingProducer throttlingProducer) {
        int n = throttlingProducer.mNumCurrentRequests;
        throttlingProducer.mNumCurrentRequests = n - 1;
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void produceResults(Consumer<T> consumer, ProducerContext producerContext) {
        boolean bl;
        producerContext.getListener().onProducerStart(producerContext.getId(), "ThrottlingProducer");
        synchronized (this) {
            if (this.mNumCurrentRequests >= this.mMaxSimultaneousRequests) {
                this.mPendingRequests.add(Pair.create(consumer, (Object)producerContext));
                return;
            }
            ++this.mNumCurrentRequests;
            bl = false;
        }
        if (bl) return;
        this.produceResultsInternal(consumer, producerContext);
    }

    void produceResultsInternal(Consumer<T> consumer, ProducerContext producerContext) {
        producerContext.getListener().onProducerFinishWithSuccess(producerContext.getId(), "ThrottlingProducer", null);
        this.mInputProducer.produceResults(new ThrottlerConsumer(consumer), producerContext);
    }

    private class ThrottlerConsumer
    extends DelegatingConsumer<T, T> {
        private ThrottlerConsumer(Consumer<T> consumer) {
            super(consumer);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void onRequestFinished() {
            ThrottlingProducer throttlingProducer = ThrottlingProducer.this;
            // MONITORENTER : throttlingProducer
            final Pair pair = (Pair)ThrottlingProducer.this.mPendingRequests.poll();
            if (pair == null) {
                ThrottlingProducer.access$210(ThrottlingProducer.this);
            }
            // MONITOREXIT : throttlingProducer
            if (pair == null) return;
            ThrottlingProducer.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    ThrottlingProducer.this.produceResultsInternal((Consumer)pair.first, (ProducerContext)pair.second);
                }
            });
        }

        @Override
        protected void onCancellationImpl() {
            this.getConsumer().onCancellation();
            this.onRequestFinished();
        }

        @Override
        protected void onFailureImpl(Throwable throwable) {
            this.getConsumer().onFailure(throwable);
            this.onRequestFinished();
        }

        @Override
        protected void onNewResultImpl(T t, boolean bl) {
            this.getConsumer().onNewResult(t, bl);
            if (bl) {
                this.onRequestFinished();
            }
        }

    }

}

