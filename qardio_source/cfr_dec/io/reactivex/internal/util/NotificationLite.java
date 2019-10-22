/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public enum NotificationLite {
    COMPLETE;


    public static <T> boolean acceptFull(Object object, Subscriber<? super T> subscriber) {
        if (object == COMPLETE) {
            subscriber.onComplete();
            return true;
        }
        if (object instanceof ErrorNotification) {
            subscriber.onError(((ErrorNotification)object).e);
            return true;
        }
        if (object instanceof SubscriptionNotification) {
            subscriber.onSubscribe(((SubscriptionNotification)object).s);
            return false;
        }
        subscriber.onNext(object);
        return false;
    }

    public static Object complete() {
        return COMPLETE;
    }

    public static Object error(Throwable throwable) {
        return new ErrorNotification(throwable);
    }

    public static <T> Object next(T t) {
        return t;
    }

    public static Object subscription(Subscription subscription) {
        return new SubscriptionNotification(subscription);
    }

    public String toString() {
        return "NotificationLite.Complete";
    }

    static final class ErrorNotification
    implements Serializable {
        final Throwable e;

        ErrorNotification(Throwable throwable) {
            this.e = throwable;
        }

        public boolean equals(Object object) {
            if (object instanceof ErrorNotification) {
                object = (ErrorNotification)object;
                return ObjectHelper.equals(this.e, ((ErrorNotification)object).e);
            }
            return false;
        }

        public int hashCode() {
            return this.e.hashCode();
        }

        public String toString() {
            return "NotificationLite.Error[" + this.e + "]";
        }
    }

    static final class SubscriptionNotification
    implements Serializable {
        final Subscription s;

        SubscriptionNotification(Subscription subscription) {
            this.s = subscription;
        }

        public String toString() {
            return "NotificationLite.Subscription[" + this.s + "]";
        }
    }

}

