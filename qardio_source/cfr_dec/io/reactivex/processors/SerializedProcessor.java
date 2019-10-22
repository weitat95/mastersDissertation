/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.processors;

import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.FlowableProcessor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

final class SerializedProcessor<T>
extends FlowableProcessor<T> {
    final FlowableProcessor<T> actual;
    volatile boolean done;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;

    SerializedProcessor(FlowableProcessor<T> flowableProcessor) {
        this.actual = flowableProcessor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void emitLoop() {
        do {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
            appendOnlyLinkedArrayList.accept(this.actual);
        } while (true);
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
            this.done = true;
            if (!this.emitting) {
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
    public void onError(Throwable throwable) {
        boolean bl;
        if (this.done) {
            RxJavaPlugins.onError(throwable);
            return;
        }
        // MONITORENTER : this
        if (this.done) {
            bl = true;
        } else {
            this.done = true;
            if (this.emitting) {
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
                AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.queue = appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
                }
                appendOnlyLinkedArrayList2.setFirst(NotificationLite.error(throwable));
                // MONITOREXIT : this
                return;
            }
            bl = false;
            this.emitting = true;
        }
        // MONITOREXIT : this
        if (bl) {
            RxJavaPlugins.onError(throwable);
            return;
        }
        this.actual.onError(throwable);
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
        synchronized (this) {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.emitting = true;
                // MONITOREXIT [2, 4] lbl9 : MonitorExitStatement: MONITOREXIT : this
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        boolean bl;
        if (!this.done) {
            synchronized (this) {
                if (this.done) {
                    bl = true;
                } else {
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            this.queue = appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
                        }
                        appendOnlyLinkedArrayList2.add(NotificationLite.subscription(subscription));
                        return;
                    }
                    this.emitting = true;
                    bl = false;
                }
            }
        } else {
            bl = true;
        }
        if (bl) {
            subscription.cancel();
            return;
        }
        this.actual.onSubscribe(subscription);
        this.emitLoop();
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.actual.subscribe(subscriber);
    }
}

