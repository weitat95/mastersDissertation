/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatArray<T>
extends Flowable<T> {
    final boolean delayError;
    final Publisher<? extends T>[] sources;

    public FlowableConcatArray(Publisher<? extends T>[] arrpublisher, boolean bl) {
        this.sources = arrpublisher;
        this.delayError = bl;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        ConcatArraySubscriber<T> concatArraySubscriber = new ConcatArraySubscriber<T>(this.sources, this.delayError, subscriber);
        subscriber.onSubscribe(concatArraySubscriber);
        concatArraySubscriber.onComplete();
    }

    static final class ConcatArraySubscriber<T>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T> {
        final Subscriber<? super T> actual;
        final boolean delayError;
        List<Throwable> errors;
        int index;
        long produced;
        final Publisher<? extends T>[] sources;
        final AtomicInteger wip;

        ConcatArraySubscriber(Publisher<? extends T>[] arrpublisher, boolean bl, Subscriber<? super T> subscriber) {
            this.actual = subscriber;
            this.sources = arrpublisher;
            this.delayError = bl;
            this.wip = new AtomicInteger();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onComplete() {
            if (this.wip.getAndIncrement() != 0) return;
            Publisher<? extends T>[] arrpublisher = this.sources;
            int n = arrpublisher.length;
            int n2 = this.index;
            do {
                Object object;
                if (n2 == n) {
                    object = this.errors;
                    if (object != null) {
                        if (object.size() == 1) {
                            this.actual.onError((Throwable)object.get(0));
                            return;
                        }
                        this.actual.onError(new CompositeException((Iterable<? extends Throwable>)object));
                        return;
                    }
                    this.actual.onComplete();
                    return;
                }
                object = arrpublisher[n2];
                if (object == null) {
                    NullPointerException nullPointerException = new NullPointerException("A Publisher entry is null");
                    if (this.delayError) {
                        List<Throwable> list;
                        object = list = this.errors;
                        if (list == null) {
                            this.errors = object = new ArrayList<Throwable>(n - n2 + 1);
                        }
                        object.add((Throwable)nullPointerException);
                        ++n2;
                        continue;
                    }
                    this.actual.onError(nullPointerException);
                    return;
                }
                long l = this.produced;
                if (l != 0L) {
                    this.produced = 0L;
                    this.produced(l);
                }
                object.subscribe(this);
                this.index = ++n2;
                if (this.wip.decrementAndGet() == 0) return;
            } while (true);
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.delayError) {
                List<Throwable> list;
                List<Throwable> list2 = list = this.errors;
                if (list == null) {
                    this.errors = list2 = new ArrayList<Throwable>(this.sources.length - this.index + 1);
                }
                list2.add(throwable);
                this.onComplete();
                return;
            }
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            this.setSubscription(subscription);
        }
    }

}

