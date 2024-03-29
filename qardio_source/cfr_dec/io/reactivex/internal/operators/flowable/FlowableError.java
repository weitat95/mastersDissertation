/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableError<T>
extends Flowable<T> {
    final Callable<? extends Throwable> errorSupplier;

    public FlowableError(Callable<? extends Throwable> callable) {
        this.errorSupplier = callable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Throwable throwable;
        try {
            throwable = ObjectHelper.requireNonNull(this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        }
        catch (Throwable throwable2) {
            Exceptions.throwIfFatal(throwable2);
        }
        EmptySubscription.error(throwable, subscriber);
    }
}

