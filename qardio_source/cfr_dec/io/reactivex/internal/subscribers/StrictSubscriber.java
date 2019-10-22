/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class StrictSubscriber<T>
extends AtomicInteger
implements FlowableSubscriber<T>,
Subscription {
    final Subscriber<? super T> actual;
    volatile boolean done;
    final AtomicThrowable error;
    final AtomicBoolean once;
    final AtomicLong requested;
    final AtomicReference<Subscription> s;

    public StrictSubscriber(Subscriber<? super T> subscriber) {
        this.actual = subscriber;
        this.error = new AtomicThrowable();
        this.requested = new AtomicLong();
        this.s = new AtomicReference();
        this.once = new AtomicBoolean();
    }

    @Override
    public void cancel() {
        if (!this.done) {
            SubscriptionHelper.cancel(this.s);
        }
    }

    @Override
    public void onComplete() {
        this.done = true;
        HalfSerializer.onComplete(this.actual, this, this.error);
    }

    @Override
    public void onError(Throwable throwable) {
        this.done = true;
        HalfSerializer.onError(this.actual, throwable, this, this.error);
    }

    @Override
    public void onNext(T t) {
        HalfSerializer.onNext(this.actual, t, this, this.error);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        if (this.once.compareAndSet(false, true)) {
            this.actual.onSubscribe(this);
            SubscriptionHelper.deferredSetOnce(this.s, this.requested, subscription);
            return;
        }
        subscription.cancel();
        this.cancel();
        this.onError(new IllegalStateException("\u00a72.12 violated: onSubscribe must be called at most once"));
    }

    @Override
    public void request(long l) {
        if (l <= 0L) {
            this.cancel();
            this.onError(new IllegalArgumentException("\u00a73.9 violated: positive request amount required but it was " + l));
            return;
        }
        SubscriptionHelper.deferredRequest(this.s, this.requested, l);
    }
}

