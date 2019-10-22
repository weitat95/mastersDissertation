/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMap<T, U>
extends AbstractFlowableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean bl, int n, int n2) {
        super(flowable);
        this.mapper = function;
        this.delayErrors = bl;
        this.maxConcurrency = n;
        this.bufferSize = n2;
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean bl, int n, int n2) {
        return new MergeSubscriber<T, U>(subscriber, function, bl, n, n2);
    }

    @Override
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe(FlowableFlatMap.subscribe(subscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }

    static final class InnerSubscriber<T, U>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<U>,
    Disposable {
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final MergeSubscriber<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        InnerSubscriber(MergeSubscriber<T, U> mergeSubscriber, long l) {
            this.id = l;
            this.parent = mergeSubscriber;
            this.bufferSize = mergeSubscriber.bufferSize;
            this.limit = this.bufferSize >> 2;
        }

        @Override
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override
        public boolean isDisposed() {
            return this.get() == SubscriptionHelper.CANCELLED;
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, throwable);
        }

        @Override
        public void onNext(U u) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(u, this);
                return;
            }
            this.parent.drain();
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            block5: {
                QueueSubscription queueSubscription;
                int n;
                block6: {
                    block4: {
                        if (!SubscriptionHelper.setOnce(this, subscription)) break block4;
                        if (!(subscription instanceof QueueSubscription)) break block5;
                        queueSubscription = (QueueSubscription)subscription;
                        n = queueSubscription.requestFusion(7);
                        if (n != 1) break block6;
                        this.fusionMode = n;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                    }
                    return;
                }
                if (n == 2) {
                    this.fusionMode = n;
                    this.queue = queueSubscription;
                }
            }
            subscription.request(this.bufferSize);
        }

        void requestMore(long l) {
            block3: {
                block2: {
                    if (this.fusionMode == 1) break block2;
                    if ((l = this.produced + l) < (long)this.limit) break block3;
                    this.produced = 0L;
                    ((Subscription)this.get()).request(l);
                }
                return;
            }
            this.produced = l;
        }
    }

    static final class MergeSubscriber<T, U>
    extends AtomicInteger
    implements FlowableSubscriber<T>,
    Subscription {
        static final InnerSubscriber<?, ?>[] CANCELLED;
        static final InnerSubscriber<?, ?>[] EMPTY;
        final Subscriber<? super U> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final AtomicThrowable errs = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        final AtomicLong requested;
        Subscription s;
        int scalarEmitted;
        final int scalarLimit;
        final AtomicReference<InnerSubscriber<?, ?>[]> subscribers = new AtomicReference();
        long uniqueId;

        static {
            EMPTY = new InnerSubscriber[0];
            CANCELLED = new InnerSubscriber[0];
        }

        MergeSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean bl, int n, int n2) {
            this.requested = new AtomicLong();
            this.actual = subscriber;
            this.mapper = function;
            this.delayErrors = bl;
            this.maxConcurrency = n;
            this.bufferSize = n2;
            this.scalarLimit = Math.max(1, n >> 1);
            this.subscribers.lazySet(EMPTY);
        }

        boolean addInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber[] arrinnerSubscriber;
            InnerSubscriber<?, ?>[] arrinnerSubscriber2;
            do {
                if ((arrinnerSubscriber2 = this.subscribers.get()) == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int n = arrinnerSubscriber2.length;
                arrinnerSubscriber = new InnerSubscriber[n + 1];
                System.arraycopy(arrinnerSubscriber2, 0, arrinnerSubscriber, 0, n);
                arrinnerSubscriber[n] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(arrinnerSubscriber2, arrinnerSubscriber));
            return true;
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                SimplePlainQueue<U> simplePlainQueue;
                this.cancelled = true;
                this.s.cancel();
                this.disposeAll();
                if (this.getAndIncrement() == 0 && (simplePlainQueue = this.queue) != null) {
                    simplePlainQueue.clear();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean checkTerminate() {
            if (this.cancelled) {
                this.clearScalarQueue();
                return true;
            }
            if (this.delayErrors || this.errs.get() == null) return false;
            {
                this.clearScalarQueue();
                Throwable throwable = this.errs.terminate();
                if (throwable == ExceptionHelper.TERMINATED) return true;
                {
                    this.actual.onError(throwable);
                    return true;
                }
            }
        }

        void clearScalarQueue() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        void disposeAll() {
            Object object;
            if (this.subscribers.get() != CANCELLED && (object = this.subscribers.getAndSet(CANCELLED)) != CANCELLED) {
                int n = ((InnerSubscriber<?, ?>[])object).length;
                for (int i = 0; i < n; ++i) {
                    object[i].dispose();
                }
                object = this.errs.terminate();
                if (object != null && object != ExceptionHelper.TERMINATED) {
                    RxJavaPlugins.onError(object);
                }
            }
        }

        void drain() {
            if (this.getAndIncrement() == 0) {
                this.drainLoop();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void drainLoop() {
            var23_1 = this.actual;
            var5_2 = 1;
            block2 : while (!this.checkTerminate()) {
                block42: {
                    block41: {
                        block37: {
                            var22_19 /* !! */  = this.queue;
                            var10_11 = this.requested.get();
                            var6_7 = var10_11 == Long.MAX_VALUE;
                            var14_13 = 0L;
                            var16_14 = var10_11;
                            var12_12 = var14_13;
                            if (var22_19 /* !! */  == null) ** GOTO lbl34
                            var16_14 = var14_13;
                            var12_12 = var10_11;
                            block3: do {
                                var18_15 = 0L;
                                var21_17 = null;
                                var14_13 = var16_14;
                                var10_11 = var12_12;
                                do {
                                    block40: {
                                        block39: {
                                            if (var10_11 == 0L) break block39;
                                            var21_17 = var22_19 /* !! */ .poll();
                                            if (this.checkTerminate()) break block2;
                                            if (var21_17 != null) break block40;
                                        }
                                        if (var18_15 != 0L) {
                                            var10_11 = var6_7 != false ? Long.MAX_VALUE : this.requested.addAndGet(-var18_15);
                                        }
                                        var16_14 = var10_11;
                                        var12_12 = var14_13;
                                        if (var10_11 != 0L) {
                                            var12_12 = var10_11;
                                            var16_14 = var14_13;
                                            if (var21_17 != null) continue block3;
                                            var12_12 = var14_13;
                                            var16_14 = var10_11;
                                        }
lbl34:
                                        // 4 sources
                                        var20_16 = this.done;
                                        var21_17 = this.queue;
                                        var24_20 = this.subscribers.get();
                                        var9_10 = var24_20.length;
                                        if (var20_16 && (var21_17 == null || var21_17.isEmpty()) && var9_10 == 0) {
                                            var21_17 = this.errs.terminate();
                                            if (var21_17 == ExceptionHelper.TERMINATED) break block2;
                                            if (var21_17 != null) break block3;
                                            var23_1.onComplete();
                                            return;
                                        }
                                        break block37;
                                    }
                                    var23_1.onNext(var21_17);
                                    ++var14_13;
                                    ++var18_15;
                                    --var10_11;
                                } while (true);
                                break;
                            } while (true);
                            var23_1.onError((Throwable)var21_17);
                            return;
                        }
                        var1_3 = 0;
                        var3_5 = 0;
                        var10_11 = var12_12;
                        if (var9_10 == 0) ** GOTO lbl117
                        var10_11 = this.lastId;
                        var2_4 = this.lastIndex;
                        if (var9_10 <= var2_4) break block41;
                        var1_3 = var2_4;
                        if (var24_20[var2_4].id == var10_11) break block42;
                    }
                    var1_3 = var2_4;
                    if (var9_10 <= var2_4) {
                        var1_3 = 0;
                    }
                    var2_4 = 0;
                    do {
                        if (var2_4 >= var9_10 || var24_20[var1_3].id == var10_11) {
                            var2_4 = var1_3;
                            this.lastIndex = var1_3;
                            this.lastId = var24_20[var1_3].id;
                            var1_3 = var2_4;
                            break;
                        }
                        var1_3 = var4_6 = var1_3 + 1;
                        if (var4_6 == var9_10) {
                            var1_3 = 0;
                        }
                        ++var2_4;
                    } while (true);
                }
                var7_8 = 0;
                var10_11 = var12_12;
                var14_13 = var16_14;
                var4_6 = var1_3;
                var2_4 = var3_5;
                var3_5 = var7_8;
                block6: do {
                    var1_3 = var2_4;
                    var12_12 = var10_11;
                    if (var3_5 >= var9_10) ** GOTO lbl114
                    if (this.checkTerminate()) break block2;
                    var25_21 = var24_20[var4_6];
                    var21_17 = null;
                    block7 : while (!this.checkTerminate()) {
                        block43: {
                            var26_22 = var25_21.queue;
                            if (var26_22 != null) break block43;
lbl97:
                            // 3 sources
                            do {
                                block46: {
                                    block44: {
                                        block45: {
                                            var20_16 = var25_21.done;
                                            var21_17 = var25_21.queue;
                                            var1_3 = var2_4;
                                            var12_12 = var10_11;
                                            if (!var20_16) break block44;
                                            if (var21_17 == null) break block45;
                                            var1_3 = var2_4;
                                            var12_12 = var10_11;
                                            if (!var21_17.isEmpty()) break block44;
                                        }
                                        this.removeInner(var25_21);
                                        if (this.checkTerminate()) break block2;
                                        var12_12 = var10_11 + 1L;
                                        var1_3 = 1;
                                    }
                                    if (var14_13 != 0L) break block46;
lbl114:
                                    // 2 sources
                                    this.lastIndex = var4_6;
                                    this.lastId = var24_20[var4_6].id;
                                    var10_11 = var12_12;
lbl117:
                                    // 2 sources
                                    if (var10_11 != 0L && !this.cancelled) {
                                        this.s.request(var10_11);
                                    }
                                    if (var1_3 != 0) continue block2;
                                    var5_2 = var1_3 = this.addAndGet(-var5_2);
                                    if (var1_3 != 0) continue block2;
                                    return;
                                }
                                var8_9 = var4_6 + 1;
                                var7_8 = var3_5;
                                var2_4 = var1_3;
                                var4_6 = var8_9;
                                var10_11 = var14_13;
                                var16_14 = var12_12;
                                if (var8_9 == var9_10) {
                                    var4_6 = 0;
                                    var7_8 = var3_5;
                                    var2_4 = var1_3;
                                    var10_11 = var14_13;
                                    var16_14 = var12_12;
                                }
                                ** GOTO lbl169
                                break;
                            } while (true);
                        }
                        var16_14 = 0L;
                        var12_12 = var14_13;
                        var14_13 = var16_14;
                        do {
                            block38: {
                                var22_19 /* !! */  = var21_17;
                                if (var12_12 != 0L) {
                                    var21_17 = var26_22.poll();
                                    if (var21_17 != null) break block38;
                                    var22_19 /* !! */  = var21_17;
                                }
                                if (var14_13 != 0L) {
                                    var12_12 = var6_7 == false ? this.requested.addAndGet(-var14_13) : Long.MAX_VALUE;
                                    var25_21.requestMore(var14_13);
                                }
                                var14_13 = var12_12;
                                if (var12_12 == 0L) ** GOTO lbl97
                                var21_17 = var22_19 /* !! */ ;
                                var14_13 = var12_12;
                                if (var22_19 /* !! */  != null) continue block7;
                                var14_13 = var12_12;
                                ** continue;
                                catch (Throwable var21_18) {
                                    Exceptions.throwIfFatal(var21_18);
                                    var25_21.dispose();
                                    this.errs.addThrowable(var21_18);
                                    if (this.checkTerminate()) break block2;
                                    this.removeInner(var25_21);
                                    var2_4 = 1;
                                    var7_8 = var3_5 + 1;
                                    var16_14 = var10_11;
                                    var10_11 = var12_12;
lbl169:
                                    // 2 sources
                                    var3_5 = var7_8 + 1;
                                    var14_13 = var10_11;
                                    var10_11 = var16_14;
                                    continue block6;
                                }
                            }
                            var23_1.onNext(var21_17);
                            if (this.checkTerminate()) break block2;
                            --var12_12;
                            ++var14_13;
                        } while (true);
                    }
                    break block2;
                    break;
                } while (true);
            }
        }

        SimpleQueue<U> getInnerQueue(InnerSubscriber<T, U> innerSubscriber) {
            SimpleQueue simpleQueue;
            SimpleQueue simpleQueue2 = simpleQueue = innerSubscriber.queue;
            if (simpleQueue == null) {
                innerSubscriber.queue = simpleQueue2 = new SpscArrayQueue(this.bufferSize);
            }
            return simpleQueue2;
        }

        /*
         * Enabled aggressive block sorting
         */
        SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> simplePlainQueue;
            SimplePlainQueue<U> simplePlainQueue2 = simplePlainQueue = this.queue;
            if (simplePlainQueue == null) {
                simplePlainQueue2 = this.maxConcurrency == Integer.MAX_VALUE ? new SpscLinkedArrayQueue<U>(this.bufferSize) : new SpscArrayQueue<U>(this.maxConcurrency);
                this.queue = simplePlainQueue2;
            }
            return simplePlainQueue2;
        }

        void innerError(InnerSubscriber<T, U> arrinnerSubscriber, Throwable throwable) {
            if (this.errs.addThrowable(throwable)) {
                arrinnerSubscriber.done = true;
                if (!this.delayErrors) {
                    this.s.cancel();
                    arrinnerSubscriber = this.subscribers.getAndSet(CANCELLED);
                    int n = arrinnerSubscriber.length;
                    for (int i = 0; i < n; ++i) {
                        arrinnerSubscriber[i].dispose();
                    }
                }
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            if (this.errs.addThrowable(throwable)) {
                this.done = true;
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNext(T object) {
            block12: {
                if (this.done) return;
                try {
                    object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper returned a null Publisher");
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    this.s.cancel();
                    this.onError(throwable);
                    return;
                }
                if (!(object instanceof Callable)) break block12;
                try {
                    object = ((Callable)object).call();
                    if (object != null) {
                        this.tryEmitScalar(object);
                        return;
                    }
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    this.errs.addThrowable(throwable);
                    this.drain();
                    return;
                }
                if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) return;
                {
                    int n;
                    this.scalarEmitted = n = this.scalarEmitted + 1;
                    if (n != this.scalarLimit) return;
                    {
                        this.scalarEmitted = 0;
                        this.s.request(this.scalarLimit);
                        return;
                    }
                }
            }
            long l = this.uniqueId;
            this.uniqueId = 1L + l;
            InnerSubscriber innerSubscriber = new InnerSubscriber(this, l);
            if (!this.addInner(innerSubscriber)) {
                return;
            }
            object.subscribe(innerSubscriber);
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            block3: {
                block2: {
                    if (!SubscriptionHelper.validate(this.s, subscription)) break block2;
                    this.s = subscription;
                    this.actual.onSubscribe(this);
                    if (this.cancelled) break block2;
                    if (this.maxConcurrency != Integer.MAX_VALUE) break block3;
                    subscription.request(Long.MAX_VALUE);
                }
                return;
            }
            subscription.request(this.maxConcurrency);
        }

        /*
         * Enabled aggressive block sorting
         */
        void removeInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] arrinnerSubscriber;
            block0 : while ((arrinnerSubscriber = this.subscribers.get()) != CANCELLED && arrinnerSubscriber != EMPTY) {
                int n = arrinnerSubscriber.length;
                int n2 = -1;
                int n3 = 0;
                do {
                    block8: {
                        int n4;
                        InnerSubscriber<?, ?>[] arrinnerSubscriber2;
                        block7: {
                            n4 = n2;
                            if (n3 >= n) break block7;
                            if (arrinnerSubscriber[n3] != innerSubscriber) break block8;
                            n4 = n3;
                        }
                        if (n4 < 0) break block0;
                        if (n == 1) {
                            arrinnerSubscriber2 = EMPTY;
                        } else {
                            arrinnerSubscriber2 = new InnerSubscriber[n - 1];
                            System.arraycopy(arrinnerSubscriber, 0, arrinnerSubscriber2, 0, n4);
                            System.arraycopy(arrinnerSubscriber, n4 + 1, arrinnerSubscriber2, n4, n - n4 - 1);
                        }
                        if (!this.subscribers.compareAndSet(arrinnerSubscriber, arrinnerSubscriber2)) continue block0;
                        return;
                    }
                    ++n3;
                } while (true);
            }
        }

        @Override
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                BackpressureHelper.add(this.requested, l);
                this.drain();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void tryEmit(U u, InnerSubscriber<T, U> innerSubscriber) {
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
                long l = this.requested.get();
                SimpleQueue simpleQueue = innerSubscriber.queue;
                if (l != 0L && (simpleQueue == null || simpleQueue.isEmpty())) {
                    this.actual.onNext(u);
                    if (l != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    innerSubscriber.requestMore(1L);
                } else {
                    SimpleQueue simpleQueue2 = simpleQueue;
                    if (simpleQueue == null) {
                        simpleQueue2 = this.getInnerQueue(innerSubscriber);
                    }
                    if (!simpleQueue2.offer(u)) {
                        this.onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                }
                if (this.decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue simpleQueue;
                SimpleQueue simpleQueue3 = simpleQueue = innerSubscriber.queue;
                if (simpleQueue == null) {
                    simpleQueue3 = new SpscArrayQueue(this.bufferSize);
                    innerSubscriber.queue = simpleQueue3;
                }
                if (!simpleQueue3.offer(u)) {
                    this.onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                }
                if (this.getAndIncrement() != 0) {
                    return;
                }
            }
            this.drainLoop();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        void tryEmitScalar(U u) {
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
                long l = this.requested.get();
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                if (l != 0L && (simplePlainQueue == null || simplePlainQueue.isEmpty())) {
                    this.actual.onNext(u);
                    if (l != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int n;
                        this.scalarEmitted = n = this.scalarEmitted + 1;
                        if (n == this.scalarLimit) {
                            this.scalarEmitted = 0;
                            this.s.request(this.scalarLimit);
                        }
                    }
                } else {
                    void var5_7;
                    SimplePlainQueue<U> simplePlainQueue2 = simplePlainQueue;
                    if (simplePlainQueue == null) {
                        SimpleQueue<U> simpleQueue = this.getMainQueue();
                    }
                    if (!var5_7.offer(u)) {
                        this.onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                }
                if (this.decrementAndGet() == 0) {
                    return;
                }
            } else {
                if (!this.getMainQueue().offer(u)) {
                    this.onError(new IllegalStateException("Scalar queue full?!"));
                    return;
                }
                if (this.getAndIncrement() != 0) {
                    return;
                }
            }
            this.drainLoop();
        }
    }

}

