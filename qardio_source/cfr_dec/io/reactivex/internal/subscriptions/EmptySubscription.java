/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public enum EmptySubscription implements QueueSubscription<Object>
{
    INSTANCE;


    public static void complete(Subscriber<?> subscriber) {
        subscriber.onSubscribe(INSTANCE);
        subscriber.onComplete();
    }

    public static void error(Throwable throwable, Subscriber<?> subscriber) {
        subscriber.onSubscribe(INSTANCE);
        subscriber.onError(throwable);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean offer(Object object) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public void request(long l) {
        SubscriptionHelper.validate(l);
    }

    @Override
    public int requestFusion(int n) {
        return n & 2;
    }

    public String toString() {
        return "EmptySubscription";
    }
}

