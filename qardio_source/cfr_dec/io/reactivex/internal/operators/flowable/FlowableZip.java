/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZip<T, R>
extends Flowable<R> {
    final int bufferSize;
    final boolean delayError;
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    public FlowableZip(Publisher<? extends T>[] arrpublisher, Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int n, boolean bl) {
        this.sources = arrpublisher;
        this.sourcesIterable = iterable;
        this.zipper = function;
        this.bufferSize = n;
        this.delayError = bl;
    }

    @Override
    public void subscribeActual(Subscriber<? super R> subscriber) {
        Object object;
        int n;
        Publisher<? extends T>[] arrpublisher = this.sources;
        int n2 = 0;
        if (arrpublisher == null) {
            object = new Publisher[8];
            Iterator<Publisher<T>> iterator = this.sourcesIterable.iterator();
            do {
                n = ++n2;
                arrpublisher = object;
                if (iterator.hasNext()) {
                    Publisher<? extends T> publisher = iterator.next();
                    arrpublisher = object;
                    if (n2 == ((Publisher[])object).length) {
                        arrpublisher = new Publisher[(n2 >> 2) + n2];
                        System.arraycopy(object, 0, arrpublisher, 0, n2);
                    }
                    arrpublisher[n2] = publisher;
                    object = arrpublisher;
                    continue;
                }
                break;
            } while (true);
        } else {
            n = arrpublisher.length;
        }
        if (n == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        object = new ZipCoordinator(subscriber, this.zipper, n, this.bufferSize, this.delayError);
        subscriber.onSubscribe((Subscription)object);
        ((ZipCoordinator)object).subscribe(arrpublisher, n);
    }

    static final class ZipCoordinator<T, R>
    extends AtomicInteger
    implements Subscription {
        final Subscriber<? super R> actual;
        volatile boolean cancelled;
        final Object[] current;
        final boolean delayErrors;
        final AtomicThrowable errors;
        final AtomicLong requested;
        final ZipSubscriber<T, R>[] subscribers;
        final Function<? super Object[], ? extends R> zipper;

        ZipCoordinator(Subscriber<? super R> arrzipSubscriber, Function<? super Object[], ? extends R> function, int n, int n2, boolean bl) {
            this.actual = arrzipSubscriber;
            this.zipper = function;
            this.delayErrors = bl;
            arrzipSubscriber = new ZipSubscriber[n];
            for (int i = 0; i < n; ++i) {
                arrzipSubscriber[i] = new ZipSubscriber(this, n2);
            }
            this.current = new Object[n];
            this.subscribers = arrzipSubscriber;
            this.requested = new AtomicLong();
            this.errors = new AtomicThrowable();
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.cancelAll();
            }
        }

        void cancelAll() {
            ZipSubscriber<T, R>[] arrzipSubscriber = this.subscribers;
            int n = arrzipSubscriber.length;
            for (int i = 0; i < n; ++i) {
                arrzipSubscriber[i].cancel();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void drain() {
            if (this.getAndIncrement() != 0) {
                return;
            }
            var12_1 = this.actual;
            var13_2 = this.subscribers;
            var5_3 = var13_2.length;
            var14_4 = this.current;
            var2_5 = 1;
            do {
                var8_10 = this.requested.get();
                block9: for (var6_9 = 0L; var8_10 != var6_9; ++var6_9) {
                    if (this.cancelled != false) return;
                    if (!this.delayErrors && this.errors.get() != null) {
                        this.cancelAll();
                        var12_1.onError(this.errors.terminate());
                        return;
                    }
                    var4_8 = 0;
                    var3_7 = 0;
                    do {
                        block27: {
                            block26: {
                                if (var3_7 >= var5_3) ** GOTO lbl28
                                var11_12 /* !! */  = var13_2[var3_7];
                                var1_6 = var4_8;
                                if (var14_4[var3_7] != null) break block27;
                                try {
                                    block28: {
                                        var10_11 = var11_12 /* !! */ .done;
                                        var11_12 /* !! */  = var11_12 /* !! */ .queue;
                                        var11_12 /* !! */  = var11_12 /* !! */  != null ? var11_12 /* !! */ .poll() : null;
                                        break block28;
lbl28:
                                        // 1 sources
                                        if (var4_8 != 0) break block9;
                                        try {
                                            var11_12 /* !! */  = ObjectHelper.requireNonNull(this.zipper.apply((Object[])var14_4.clone()), "The zipper returned a null value");
                                            var12_1.onNext(var11_12 /* !! */ );
                                        }
                                        catch (Throwable var11_14) {
                                            Exceptions.throwIfFatal(var11_14);
                                            this.cancelAll();
                                            this.errors.addThrowable(var11_14);
                                            var12_1.onError(this.errors.terminate());
                                            return;
                                        }
                                        Arrays.fill(var14_4, null);
                                        continue block9;
                                    }
                                    var1_6 = var11_12 /* !! */  == null ? 1 : 0;
                                    if (var10_11 && var1_6 != 0) {
                                        this.cancelAll();
                                        if ((Throwable)this.errors.get() != null) {
                                            var12_1.onError(this.errors.terminate());
                                            return;
                                        }
                                        var12_1.onComplete();
                                        return;
                                    }
                                }
                                catch (Throwable var11_13) {
                                    Exceptions.throwIfFatal(var11_13);
                                    this.errors.addThrowable(var11_13);
                                    if (!this.delayErrors) {
                                        this.cancelAll();
                                        var12_1.onError(this.errors.terminate());
                                        return;
                                    }
                                    break block26;
                                }
                                if (var1_6 == 0) {
                                    var14_4[var3_7] = var11_12 /* !! */ ;
                                    var1_6 = var4_8;
                                } else {
                                    var1_6 = 1;
                                }
                                break block27;
                            }
                            var1_6 = 1;
                        }
                        ++var3_7;
                        var4_8 = var1_6;
                    } while (true);
                }
                if (var8_10 == var6_9) {
                    if (this.cancelled != false) return;
                    if (!this.delayErrors && this.errors.get() != null) {
                        this.cancelAll();
                        var12_1.onError(this.errors.terminate());
                        return;
                    }
                    for (var1_6 = 0; var1_6 < var5_3; ++var1_6) {
                        var11_12 /* !! */  = var13_2[var1_6];
                        if (var14_4[var1_6] != null) continue;
                        try {
                            var10_11 = var11_12 /* !! */ .done;
                            var11_12 /* !! */  = var11_12 /* !! */ .queue;
                            var11_12 /* !! */  = var11_12 /* !! */  != null ? var11_12 /* !! */ .poll() : null;
                            var3_7 = var11_12 /* !! */  == null ? 1 : 0;
                            if (var10_11 && var3_7 != 0) {
                                this.cancelAll();
                                if ((Throwable)this.errors.get() != null) {
                                    var12_1.onError(this.errors.terminate());
                                    return;
                                }
                                var12_1.onComplete();
                                return;
                            }
                        }
                        catch (Throwable var11_15) {
                            Exceptions.throwIfFatal(var11_15);
                            this.errors.addThrowable(var11_15);
                            if (this.delayErrors) continue;
                            this.cancelAll();
                            var12_1.onError(this.errors.terminate());
                            return;
                        }
                        if (var3_7 != 0) continue;
                        var14_4[var1_6] = var11_12 /* !! */ ;
                    }
                }
                if (var6_9 != 0L) {
                    var3_7 = var13_2.length;
                    for (var1_6 = 0; var1_6 < var3_7; ++var1_6) {
                        var13_2[var1_6].request(var6_9);
                    }
                    if (var8_10 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-var6_9);
                    }
                }
                var2_5 = var1_6 = this.addAndGet(-var2_5);
            } while (var1_6 != 0);
        }

        void error(ZipSubscriber<T, R> zipSubscriber, Throwable throwable) {
            if (this.errors.addThrowable(throwable)) {
                zipSubscriber.done = true;
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        @Override
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                BackpressureHelper.add(this.requested, l);
                this.drain();
            }
        }

        void subscribe(Publisher<? extends T>[] arrpublisher, int n) {
            ZipSubscriber<T, R>[] arrzipSubscriber = this.subscribers;
            int n2 = 0;
            while (n2 < n && !this.cancelled && (this.delayErrors || this.errors.get() == null)) {
                arrpublisher[n2].subscribe(arrzipSubscriber[n2]);
                ++n2;
            }
            return;
        }
    }

    static final class ZipSubscriber<T, R>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>,
    Subscription {
        volatile boolean done;
        final int limit;
        final ZipCoordinator<T, R> parent;
        final int prefetch;
        long produced;
        SimpleQueue<T> queue;
        int sourceMode;

        ZipSubscriber(ZipCoordinator<T, R> zipCoordinator, int n) {
            this.parent = zipCoordinator;
            this.prefetch = n;
            this.limit = n - (n >> 2);
        }

        @Override
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.parent.error(this, throwable);
        }

        @Override
        public void onNext(T t) {
            if (this.sourceMode != 2) {
                this.queue.offer(t);
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
                        this.sourceMode = n;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                    }
                    return;
                }
                if (n == 2) {
                    this.sourceMode = n;
                    this.queue = queueSubscription;
                    subscription.request(this.prefetch);
                    return;
                }
            }
            this.queue = new SpscArrayQueue<T>(this.prefetch);
            subscription.request(this.prefetch);
        }

        @Override
        public void request(long l) {
            block3: {
                block2: {
                    if (this.sourceMode == 1) break block2;
                    if ((l = this.produced + l) < (long)this.limit) break block3;
                    this.produced = 0L;
                    ((Subscription)this.get()).request(l);
                }
                return;
            }
            this.produced = l;
        }
    }

}

