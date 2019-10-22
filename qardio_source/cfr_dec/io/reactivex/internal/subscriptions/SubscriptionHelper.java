/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscriptions;

import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public enum SubscriptionHelper implements Subscription
{
    CANCELLED;


    public static boolean cancel(AtomicReference<Subscription> object) {
        if (((AtomicReference)object).get() != CANCELLED && (object = ((AtomicReference)object).getAndSet((Subscription)CANCELLED)) != CANCELLED) {
            if (object != null) {
                object.cancel();
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void deferredRequest(AtomicReference<Subscription> object, AtomicLong atomicLong, long l) {
        Subscription subscription = (Subscription)((AtomicReference)object).get();
        if (subscription != null) {
            subscription.request(l);
            return;
        } else {
            if (!SubscriptionHelper.validate(l)) return;
            {
                BackpressureHelper.add(atomicLong, l);
                if ((object = (Subscription)((AtomicReference)object).get()) == null || (l = atomicLong.getAndSet(0L)) == 0L) return;
                {
                    object.request(l);
                    return;
                }
            }
        }
    }

    public static boolean deferredSetOnce(AtomicReference<Subscription> atomicReference, AtomicLong atomicLong, Subscription subscription) {
        if (SubscriptionHelper.setOnce(atomicReference, subscription)) {
            long l = atomicLong.getAndSet(0L);
            if (l != 0L) {
                subscription.request(l);
            }
            return true;
        }
        return false;
    }

    public static boolean isCancelled(Subscription subscription) {
        return subscription == CANCELLED;
    }

    public static void reportMoreProduced(long l) {
        RxJavaPlugins.onError(new ProtocolViolationException("More produced than requested: " + l));
    }

    public static void reportSubscriptionSet() {
        RxJavaPlugins.onError(new ProtocolViolationException("Subscription already set!"));
    }

    public static boolean setOnce(AtomicReference<Subscription> atomicReference, Subscription subscription) {
        ObjectHelper.requireNonNull(subscription, "d is null");
        if (!atomicReference.compareAndSet(null, subscription)) {
            subscription.cancel();
            if (atomicReference.get() != CANCELLED) {
                SubscriptionHelper.reportSubscriptionSet();
            }
            return false;
        }
        return true;
    }

    public static boolean validate(long l) {
        if (l <= 0L) {
            RxJavaPlugins.onError(new IllegalArgumentException("n > 0 required but it was " + l));
            return false;
        }
        return true;
    }

    public static boolean validate(Subscription subscription, Subscription subscription2) {
        if (subscription2 == null) {
            RxJavaPlugins.onError(new NullPointerException("next is null"));
            return false;
        }
        if (subscription != null) {
            subscription2.cancel();
            SubscriptionHelper.reportSubscriptionSet();
            return false;
        }
        return true;
    }

    @Override
    public void cancel() {
    }

    @Override
    public void request(long l) {
    }
}

