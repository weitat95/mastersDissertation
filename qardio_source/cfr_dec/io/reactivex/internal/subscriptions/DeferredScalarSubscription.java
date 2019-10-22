/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscriptions;

import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;

public class DeferredScalarSubscription<T>
extends BasicIntQueueSubscription<T> {
    protected final Subscriber<? super T> actual;
    protected T value;

    public DeferredScalarSubscription(Subscriber<? super T> subscriber) {
        this.actual = subscriber;
    }

    @Override
    public void cancel() {
        this.set(4);
        this.value = null;
    }

    @Override
    public final void clear() {
        this.lazySet(32);
        this.value = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void complete(T t) {
        int n;
        int n2 = this.get();
        do {
            if (n2 == 8) {
                this.value = t;
                this.lazySet(16);
                Subscriber<T> subscriber = this.actual;
                subscriber.onNext(t);
                if (this.get() == 4) return;
                {
                    subscriber.onComplete();
                    return;
                }
            }
            if ((n2 & 0xFFFFFFFD) != 0) return;
            if (n2 == 2) {
                this.lazySet(3);
                Subscriber<T> subscriber = this.actual;
                subscriber.onNext(t);
                if (this.get() == 4) return;
                {
                    subscriber.onComplete();
                    return;
                }
            }
            this.value = t;
            if (this.compareAndSet(0, 1)) return;
            n2 = n = this.get();
        } while (n != 4);
        this.value = null;
    }

    @Override
    public final boolean isEmpty() {
        return this.get() != 16;
    }

    @Override
    public final T poll() {
        if (this.get() == 16) {
            this.lazySet(32);
            T t = this.value;
            this.value = null;
            return t;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public final void request(long l) {
        if (!SubscriptionHelper.validate(l)) return;
        do {
            int n;
            if (((n = this.get()) & 0xFFFFFFFE) != 0) {
                return;
            }
            if (n != 1) continue;
            if (!this.compareAndSet(1, 3)) return;
            T t = this.value;
            if (t == null) return;
            this.value = null;
            Subscriber<T> subscriber = this.actual;
            subscriber.onNext(t);
            if (this.get() == 4) return;
            subscriber.onComplete();
            return;
        } while (!this.compareAndSet(0, 2));
    }

    @Override
    public final int requestFusion(int n) {
        if ((n & 2) != 0) {
            this.lazySet(8);
            return 2;
        }
        return 0;
    }
}

