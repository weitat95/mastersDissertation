/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$com.facebook.imagepipeline.producers.MultiplexProducer
 *  com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer$com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.producers.BaseConsumer;
import com.facebook.imagepipeline.producers.BaseProducerContext;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.MultiplexProducer.com.facebook.imagepipeline.producers.MultiplexProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class MultiplexProducer<K, T extends Closeable>
implements Producer<T> {
    private final Producer<T> mInputProducer;
    @GuardedBy(value="this")
    final Map<K, com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer> mMultiplexers;

    protected MultiplexProducer(Producer<T> producer) {
        this.mInputProducer = producer;
        this.mMultiplexers = new HashMap<K, com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer>();
    }

    private com.facebook.imagepipeline.producers.MultiplexProducer$com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer createAndPutNewMultiplexer(K k) {
        synchronized (this) {
            Multiplexer multiplexer = new Multiplexer(k);
            this.mMultiplexers.put(k, (com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer)multiplexer);
            return multiplexer;
        }
    }

    private com.facebook.imagepipeline.producers.MultiplexProducer$com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer getExistingMultiplexer(K object) {
        synchronized (this) {
            object = (Multiplexer)this.mMultiplexers.get(object);
            return object;
        }
    }

    private void removeMultiplexer(K k, com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer multiplexer) {
        synchronized (this) {
            if (this.mMultiplexers.get(k) == multiplexer) {
                this.mMultiplexers.remove(k);
            }
            return;
        }
    }

    protected abstract T cloneOrNull(T var1);

    protected abstract K getKey(ProducerContext var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void produceResults(Consumer<T> consumer, ProducerContext producerContext) {
        Multiplexer multiplexer;
        boolean bl;
        K k = this.getKey(producerContext);
        do {
            bl = false;
            synchronized (this) {
                Multiplexer multiplexer2;
                multiplexer = multiplexer2 = this.getExistingMultiplexer(k);
                if (multiplexer2 == null) {
                    multiplexer = this.createAndPutNewMultiplexer(k);
                    bl = true;
                }
            }
        } while (!multiplexer.addNewConsumer(consumer, producerContext));
        if (bl) {
            multiplexer.startInputProducerIfHasAttachedConsumers();
        }
    }

    class Multiplexer {
        private final CopyOnWriteArraySet<Pair<Consumer<T>, ProducerContext>> mConsumerContextPairs = Sets.newCopyOnWriteArraySet();
        @Nullable
        @GuardedBy(value="Multiplexer.this")
        private com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer mForwardingConsumer;
        private final K mKey;
        @Nullable
        @GuardedBy(value="Multiplexer.this")
        private T mLastIntermediateResult;
        @GuardedBy(value="Multiplexer.this")
        private float mLastProgress;
        @Nullable
        @GuardedBy(value="Multiplexer.this")
        private BaseProducerContext mMultiplexProducerContext;

        public Multiplexer(K k) {
            this.mKey = k;
        }

        private void addCallbacks(final Pair<Consumer<T>, ProducerContext> pair, ProducerContext producerContext) {
            producerContext.addCallbacks(new BaseProducerContextCallbacks(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onCancellationRequested() {
                    List list;
                    BaseProducerContext baseProducerContext;
                    boolean bl;
                    List list2;
                    List list3;
                    BaseProducerContext baseProducerContext2 = null;
                    List list4 = null;
                    List list5 = null;
                    List list6 = null;
                    Multiplexer multiplexer = Multiplexer.this;
                    synchronized (multiplexer) {
                        bl = Multiplexer.this.mConsumerContextPairs.remove((Object)pair);
                        baseProducerContext = baseProducerContext2;
                        list = list6;
                        list2 = list4;
                        list3 = list5;
                        if (bl) {
                            if (Multiplexer.this.mConsumerContextPairs.isEmpty()) {
                                baseProducerContext = Multiplexer.this.mMultiplexProducerContext;
                                list3 = list5;
                                list2 = list4;
                                list = list6;
                            } else {
                                list2 = Multiplexer.this.updateIsPrefetch();
                                list3 = Multiplexer.this.updatePriority();
                                list = Multiplexer.this.updateIsIntermediateResultExpected();
                                baseProducerContext = baseProducerContext2;
                            }
                        }
                    }
                    BaseProducerContext.callOnIsPrefetchChanged(list2);
                    BaseProducerContext.callOnPriorityChanged(list3);
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(list);
                    if (baseProducerContext != null) {
                        baseProducerContext.cancel();
                    }
                    if (bl) {
                        ((Consumer)pair.first).onCancellation();
                    }
                }

                @Override
                public void onIsIntermediateResultExpectedChanged() {
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(Multiplexer.this.updateIsIntermediateResultExpected());
                }

                @Override
                public void onIsPrefetchChanged() {
                    BaseProducerContext.callOnIsPrefetchChanged(Multiplexer.this.updateIsPrefetch());
                }

                @Override
                public void onPriorityChanged() {
                    BaseProducerContext.callOnPriorityChanged(Multiplexer.this.updatePriority());
                }
            });
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void closeSafely(Closeable closeable) {
            if (closeable == null) return;
            try {
                closeable.close();
                return;
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean computeIsIntermediateResultExpected() {
            synchronized (this) {
                boolean bl;
                Iterator<Pair<Consumer<T>, ProducerContext>> iterator = this.mConsumerContextPairs.iterator();
                do {
                    if (!iterator.hasNext()) return false;
                } while (!(bl = ((ProducerContext)iterator.next().second).isIntermediateResultExpected()));
                return true;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean computeIsPrefetch() {
            synchronized (this) {
                boolean bl;
                Iterator<Pair<Consumer<T>, ProducerContext>> iterator = this.mConsumerContextPairs.iterator();
                do {
                    if (!iterator.hasNext()) return true;
                } while (bl = ((ProducerContext)iterator.next().second).isPrefetch());
                return false;
            }
        }

        private Priority computePriority() {
            synchronized (this) {
                Priority priority = Priority.LOW;
                Iterator<Pair<Consumer<T>, ProducerContext>> iterator = this.mConsumerContextPairs.iterator();
                while (iterator.hasNext()) {
                    priority = Priority.getHigherPriority(priority, ((ProducerContext)iterator.next().second).getPriority());
                }
                return priority;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void startInputProducerIfHasAttachedConsumers() {
            ProducerContext producerContext;
            com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer forwardingConsumer;
            boolean bl = true;
            synchronized (this) {
                boolean bl2 = this.mMultiplexProducerContext == null;
                Preconditions.checkArgument(bl2);
                bl2 = this.mForwardingConsumer == null ? bl : false;
                Preconditions.checkArgument(bl2);
                if (this.mConsumerContextPairs.isEmpty()) {
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    return;
                }
                producerContext = (ProducerContext)this.mConsumerContextPairs.iterator().next().second;
                this.mMultiplexProducerContext = new BaseProducerContext(producerContext.getImageRequest(), producerContext.getId(), producerContext.getListener(), producerContext.getCallerContext(), producerContext.getLowestPermittedRequestLevel(), this.computeIsPrefetch(), this.computeIsIntermediateResultExpected(), this.computePriority());
                this.mForwardingConsumer = new ForwardingConsumer();
                producerContext = this.mMultiplexProducerContext;
                forwardingConsumer = this.mForwardingConsumer;
            }
            MultiplexProducer.this.mInputProducer.produceResults(forwardingConsumer, producerContext);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private List<ProducerContextCallbacks> updateIsIntermediateResultExpected() {
            synchronized (this) {
                Object object;
                block6: {
                    object = this.mMultiplexProducerContext;
                    if (object != null) break block6;
                    return null;
                }
                object = this.mMultiplexProducerContext.setIsIntermediateResultExpectedNoCallbacks(this.computeIsIntermediateResultExpected());
                return object;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private List<ProducerContextCallbacks> updateIsPrefetch() {
            synchronized (this) {
                Object object;
                block6: {
                    object = this.mMultiplexProducerContext;
                    if (object != null) break block6;
                    return null;
                }
                object = this.mMultiplexProducerContext.setIsPrefetchNoCallbacks(this.computeIsPrefetch());
                return object;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private List<ProducerContextCallbacks> updatePriority() {
            synchronized (this) {
                Object object;
                block6: {
                    object = this.mMultiplexProducerContext;
                    if (object != null) break block6;
                    return null;
                }
                object = this.mMultiplexProducerContext.setPriorityNoCallbacks(this.computePriority());
                return object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public boolean addNewConsumer(Consumer<T> consumer, ProducerContext producerContext) {
            Pair pair = Pair.create(consumer, (Object)producerContext);
            // MONITORENTER : this
            if (MultiplexProducer.this.getExistingMultiplexer(this.mKey) != this) {
                // MONITOREXIT : this
                return false;
            }
            this.mConsumerContextPairs.add(pair);
            List<ProducerContextCallbacks> list = this.updateIsPrefetch();
            List<ProducerContextCallbacks> list2 = this.updatePriority();
            List<ProducerContextCallbacks> list3 = this.updateIsIntermediateResultExpected();
            T t = this.mLastIntermediateResult;
            float f = this.mLastProgress;
            // MONITOREXIT : this
            BaseProducerContext.callOnIsPrefetchChanged(list);
            BaseProducerContext.callOnPriorityChanged(list2);
            BaseProducerContext.callOnIsIntermediateResultExpectedChanged(list3);
            // MONITORENTER : pair
            // MONITORENTER : this
            if (t != this.mLastIntermediateResult) {
                list = null;
            } else {
                list = t;
                if (t != null) {
                    list = MultiplexProducer.this.cloneOrNull(t);
                }
            }
            // MONITOREXIT : this
            if (list != null) {
                if (f > 0.0f) {
                    consumer.onProgressUpdate(f);
                }
                consumer.onNewResult(list, false);
                this.closeSafely((Closeable)((Object)list));
            }
            // MONITOREXIT : pair
            this.addCallbacks(pair, producerContext);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onCancelled(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer forwardingConsumer) {
            synchronized (this) {
                if (this.mForwardingConsumer != forwardingConsumer) {
                    return;
                }
                this.mForwardingConsumer = null;
                this.mMultiplexProducerContext = null;
                this.closeSafely((Closeable)this.mLastIntermediateResult);
                this.mLastIntermediateResult = null;
            }
            this.startInputProducerIfHasAttachedConsumers();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onFailure(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer pair, Throwable throwable) {
            Iterator<Pair<Consumer<T>, ProducerContext>> iterator;
            synchronized (this) {
                if (this.mForwardingConsumer != pair) {
                    return;
                }
                iterator = this.mConsumerContextPairs.iterator();
                this.mConsumerContextPairs.clear();
                MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                this.closeSafely((Closeable)this.mLastIntermediateResult);
                this.mLastIntermediateResult = null;
            }
            while (iterator.hasNext()) {
                pair = iterator.next();
                synchronized (pair) {
                    ((Consumer)pair.first).onFailure(throwable);
                }
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer pair, T t, boolean bl) {
            Iterator<Pair<Consumer<T>, ProducerContext>> iterator;
            synchronized (this) {
                if (this.mForwardingConsumer != pair) {
                    return;
                }
                this.closeSafely((Closeable)this.mLastIntermediateResult);
                this.mLastIntermediateResult = null;
                iterator = this.mConsumerContextPairs.iterator();
                if (!bl) {
                    this.mLastIntermediateResult = MultiplexProducer.this.cloneOrNull(t);
                } else {
                    this.mConsumerContextPairs.clear();
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                }
            }
            while (iterator.hasNext()) {
                pair = iterator.next();
                synchronized (pair) {
                    ((Consumer)pair.first).onNewResult(t, bl);
                }
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer.com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer pair, float f) {
            Iterator<Pair<Consumer<T>, ProducerContext>> iterator;
            synchronized (this) {
                if (this.mForwardingConsumer != pair) {
                    return;
                }
                this.mLastProgress = f;
                iterator = this.mConsumerContextPairs.iterator();
            }
            while (iterator.hasNext()) {
                pair = iterator.next();
                synchronized (pair) {
                    ((Consumer)pair.first).onProgressUpdate(f);
                }
            }
            return;
        }

        private class ForwardingConsumer
        extends BaseConsumer<T> {
            private ForwardingConsumer() {
            }

            @Override
            protected void onCancellationImpl() {
                Multiplexer.this.onCancelled(this);
            }

            @Override
            protected void onFailureImpl(Throwable throwable) {
                Multiplexer.this.onFailure(this, throwable);
            }

            @Override
            protected void onNewResultImpl(T t, boolean bl) {
                Multiplexer.this.onNextResult(this, t, bl);
            }

            @Override
            protected void onProgressUpdateImpl(float f) {
                Multiplexer.this.onProgressUpdate(this, f);
            }
        }

    }

}

