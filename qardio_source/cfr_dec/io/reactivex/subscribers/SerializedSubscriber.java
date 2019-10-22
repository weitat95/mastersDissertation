/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SerializedSubscriber<T>
implements FlowableSubscriber<T>,
Subscription {
    final Subscriber<? super T> actual;
    final boolean delayError;
    volatile boolean done;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;
    Subscription subscription;

    public SerializedSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, false);
    }

    public SerializedSubscriber(Subscriber<? super T> subscriber, boolean bl) {
        this.actual = subscriber;
        this.delayError = bl;
    }

    @Override
    public void cancel() {
        this.subscription.cancel();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void emitLoop() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        do {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
        } while (!appendOnlyLinkedArrayList.accept(this.actual));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onComplete() {
        if (this.done) {
            return;
        }
        synchronized (this) {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.done = true;
                this.emitting = true;
                // MONITOREXIT [2, 4] lbl10 : MonitorExitStatement: MONITOREXIT : this
                this.actual.onComplete();
                return;
            }
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList = this.queue;
            if (appendOnlyLinkedArrayList == null) {
                this.queue = appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
            }
            appendOnlyLinkedArrayList2.add(NotificationLite.complete());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onError(Throwable object) {
        boolean bl;
        if (this.done) {
            RxJavaPlugins.onError((Throwable)object);
            return;
        }
        // MONITORENTER : this
        if (this.done) {
            bl = true;
        } else {
            if (this.emitting) {
                this.done = true;
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
                    this.queue = appendOnlyLinkedArrayList2;
                }
                object = NotificationLite.error((Throwable)object);
                if (this.delayError) {
                    appendOnlyLinkedArrayList2.add(object);
                    // MONITOREXIT : this
                    return;
                }
                appendOnlyLinkedArrayList2.setFirst(object);
                return;
            }
            this.done = true;
            this.emitting = true;
            bl = false;
        }
        // MONITOREXIT : this
        if (bl) {
            RxJavaPlugins.onError((Throwable)object);
            return;
        }
        this.actual.onError((Throwable)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onNext(T t) {
        if (this.done) {
            return;
        }
        if (t == null) {
            this.subscription.cancel();
            this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return;
        }
        synchronized (this) {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                // MONITOREXIT [2, 4] lbl13 : MonitorExitStatement: MONITOREXIT : this
                this.actual.onNext(t);
                this.emitLoop();
                return;
            }
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList = this.queue;
            if (appendOnlyLinkedArrayList == null) {
                this.queue = appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
            }
            appendOnlyLinkedArrayList2.add(NotificationLite.next(t));
            return;
        }
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.subscription, subscription)) {
            this.subscription = subscription;
            this.actual.onSubscribe(this);
        }
    }

    @Override
    public void request(long l) {
        this.subscription.request(l);
    }
}

