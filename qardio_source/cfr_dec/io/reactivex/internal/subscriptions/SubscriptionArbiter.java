/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public class SubscriptionArbiter
extends AtomicInteger
implements Subscription {
    Subscription actual;
    volatile boolean cancelled;
    final AtomicLong missedProduced;
    final AtomicLong missedRequested;
    final AtomicReference<Subscription> missedSubscription = new AtomicReference();
    long requested;
    protected boolean unbounded;

    public SubscriptionArbiter() {
        this.missedRequested = new AtomicLong();
        this.missedProduced = new AtomicLong();
    }

    @Override
    public void cancel() {
        if (!this.cancelled) {
            this.cancelled = true;
            this.drain();
        }
    }

    final void drain() {
        if (this.getAndIncrement() != 0) {
            return;
        }
        this.drainLoop();
    }

    /*
     * Enabled aggressive block sorting
     */
    final void drainLoop() {
        int n;
        long l;
        Subscription subscription;
        int n2 = 1;
        long l2 = 0L;
        Subscription subscription2 = null;
        do {
            long l3;
            Subscription subscription3 = subscription = this.missedSubscription.get();
            if (subscription != null) {
                subscription3 = this.missedSubscription.getAndSet(null);
            }
            long l4 = l = this.missedRequested.get();
            if (l != 0L) {
                l4 = this.missedRequested.getAndSet(0L);
            }
            l = l3 = this.missedProduced.get();
            if (l3 != 0L) {
                l = this.missedProduced.getAndSet(0L);
            }
            Subscription subscription4 = this.actual;
            if (this.cancelled) {
                if (subscription4 != null) {
                    subscription4.cancel();
                    this.actual = null;
                }
                subscription = subscription2;
                l = l2;
                if (subscription3 != null) {
                    subscription3.cancel();
                    l = l2;
                    subscription = subscription2;
                }
            } else {
                long l5;
                l3 = l5 = this.requested;
                if (l5 != Long.MAX_VALUE) {
                    l3 = BackpressureHelper.addCap(l5, l4);
                    if (l3 != Long.MAX_VALUE) {
                        l3 -= l;
                        l = l3;
                        if (l3 < 0L) {
                            SubscriptionHelper.reportMoreProduced(l3);
                            l = 0L;
                        }
                    } else {
                        l = l3;
                    }
                    this.requested = l;
                    l3 = l;
                }
                if (subscription3 != null) {
                    if (subscription4 != null) {
                        subscription4.cancel();
                    }
                    this.actual = subscription3;
                    subscription = subscription2;
                    l = l2;
                    if (l3 != 0L) {
                        l = BackpressureHelper.addCap(l2, l3);
                        subscription = subscription3;
                    }
                } else {
                    subscription = subscription2;
                    l = l2;
                    if (subscription4 != null) {
                        subscription = subscription2;
                        l = l2;
                        if (l4 != 0L) {
                            l = BackpressureHelper.addCap(l2, l4);
                            subscription = subscription4;
                        }
                    }
                }
            }
            n2 = n = this.addAndGet(-n2);
            subscription2 = subscription;
            l2 = l;
        } while (n != 0);
        if (l != 0L) {
            subscription.request(l);
        }
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void produced(long l) {
        block6: {
            block7: {
                block5: {
                    if (this.unbounded) break block5;
                    if (this.get() != 0 || !this.compareAndSet(0, 1)) break block6;
                    long l2 = this.requested;
                    if (l2 != Long.MAX_VALUE) {
                        l2 -= l;
                        l = l2;
                        if (l2 < 0L) {
                            SubscriptionHelper.reportMoreProduced(l2);
                            l = 0L;
                        }
                        this.requested = l;
                    }
                    if (this.decrementAndGet() != 0) break block7;
                }
                return;
            }
            this.drainLoop();
            return;
        }
        BackpressureHelper.add(this.missedProduced, l);
        this.drain();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void request(long l) {
        block7: {
            Subscription subscription;
            block8: {
                block6: {
                    if (!SubscriptionHelper.validate(l) || this.unbounded) break block6;
                    if (this.get() != 0 || !this.compareAndSet(0, 1)) break block7;
                    long l2 = this.requested;
                    if (l2 != Long.MAX_VALUE) {
                        this.requested = l2 = BackpressureHelper.addCap(l2, l);
                        if (l2 == Long.MAX_VALUE) {
                            this.unbounded = true;
                        }
                    }
                    subscription = this.actual;
                    if (this.decrementAndGet() != 0) {
                        this.drainLoop();
                    }
                    if (subscription != null) break block8;
                }
                return;
            }
            subscription.request(l);
            return;
        }
        BackpressureHelper.add(this.missedRequested, l);
        this.drain();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setSubscription(Subscription subscription) {
        if (this.cancelled) {
            subscription.cancel();
            return;
        }
        ObjectHelper.requireNonNull(subscription, "s is null");
        if (this.get() == 0 && this.compareAndSet(0, 1)) {
            Subscription subscription2 = this.actual;
            if (subscription2 != null) {
                subscription2.cancel();
            }
            this.actual = subscription;
            long l = this.requested;
            if (this.decrementAndGet() != 0) {
                this.drainLoop();
            }
            if (l == 0L) return;
            {
                subscription.request(l);
                return;
            }
        }
        if ((subscription = this.missedSubscription.getAndSet(subscription)) != null) {
            subscription.cancel();
        }
        this.drain();
    }
}

