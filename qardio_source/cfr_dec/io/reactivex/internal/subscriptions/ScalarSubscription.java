/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class ScalarSubscription<T>
extends AtomicInteger
implements QueueSubscription<T> {
    final Subscriber<? super T> subscriber;
    final T value;

    public ScalarSubscription(Subscriber<? super T> subscriber, T t) {
        this.subscriber = subscriber;
        this.value = t;
    }

    @Override
    public void cancel() {
        this.lazySet(2);
    }

    @Override
    public void clear() {
        this.lazySet(1);
    }

    @Override
    public boolean isEmpty() {
        return this.get() != 0;
    }

    @Override
    public boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public T poll() {
        if (this.get() == 0) {
            this.lazySet(1);
            return this.value;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void request(long l) {
        Subscriber<T> subscriber;
        block3: {
            block2: {
                if (!SubscriptionHelper.validate(l) || !this.compareAndSet(0, 1)) break block2;
                subscriber = this.subscriber;
                subscriber.onNext(this.value);
                if (this.get() != 2) break block3;
            }
            return;
        }
        subscriber.onComplete();
    }

    @Override
    public int requestFusion(int n) {
        return n & 1;
    }
}

