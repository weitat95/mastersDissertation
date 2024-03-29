/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class BasicFuseableSubscriber<T, R>
implements FlowableSubscriber<T>,
QueueSubscription<R> {
    protected final Subscriber<? super R> actual;
    protected boolean done;
    protected QueueSubscription<T> qs;
    protected Subscription s;
    protected int sourceMode;

    public BasicFuseableSubscriber(Subscriber<? super R> subscriber) {
        this.actual = subscriber;
    }

    protected void afterDownstream() {
    }

    protected boolean beforeDownstream() {
        return true;
    }

    @Override
    public void cancel() {
        this.s.cancel();
    }

    @Override
    public void clear() {
        this.qs.clear();
    }

    protected final void fail(Throwable throwable) {
        Exceptions.throwIfFatal(throwable);
        this.s.cancel();
        this.onError(throwable);
    }

    @Override
    public boolean isEmpty() {
        return this.qs.isEmpty();
    }

    @Override
    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        this.actual.onComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.done) {
            RxJavaPlugins.onError(throwable);
            return;
        }
        this.done = true;
        this.actual.onError(throwable);
    }

    @Override
    public final void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.s, subscription)) {
            this.s = subscription;
            if (subscription instanceof QueueSubscription) {
                this.qs = (QueueSubscription)subscription;
            }
            if (this.beforeDownstream()) {
                this.actual.onSubscribe(this);
                this.afterDownstream();
            }
        }
    }

    @Override
    public void request(long l) {
        this.s.request(l);
    }

    protected final int transitiveBoundaryFusion(int n) {
        QueueSubscription<T> queueSubscription = this.qs;
        if (queueSubscription != null && (n & 4) == 0) {
            if ((n = queueSubscription.requestFusion(n)) != 0) {
                this.sourceMode = n;
            }
            return n;
        }
        return 0;
    }
}

