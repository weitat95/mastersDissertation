/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableJust<T>
extends Flowable<T>
implements ScalarCallable<T> {
    private final T value;

    public FlowableJust(T t) {
        this.value = t;
    }

    @Override
    public T call() {
        return this.value;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new ScalarSubscription<T>((Subscriber<? super T>)subscriber, (T)this.value));
    }
}

