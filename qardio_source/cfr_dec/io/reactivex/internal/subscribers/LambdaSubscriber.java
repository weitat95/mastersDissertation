/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class LambdaSubscriber<T>
extends AtomicReference<Subscription>
implements FlowableSubscriber<T>,
Disposable,
Subscription {
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final Consumer<? super Subscription> onSubscribe;

    public LambdaSubscriber(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Consumer<? super Subscription> consumer3) {
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onSubscribe = consumer3;
    }

    @Override
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    @Override
    public void dispose() {
        this.cancel();
    }

    @Override
    public boolean isDisposed() {
        return this.get() == SubscriptionHelper.CANCELLED;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onComplete() {
        if (this.get() == SubscriptionHelper.CANCELLED) return;
        this.lazySet(SubscriptionHelper.CANCELLED);
        try {
            this.onComplete.run();
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            RxJavaPlugins.onError(throwable);
            return;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            try {
                this.onError.accept(throwable);
                return;
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                RxJavaPlugins.onError(new CompositeException(throwable, throwable2));
                return;
            }
        }
        RxJavaPlugins.onError(throwable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onNext(T t) {
        if (this.isDisposed()) return;
        try {
            this.onNext.accept(t);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            ((Subscription)this.get()).cancel();
            this.onError(throwable);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        if (!SubscriptionHelper.setOnce(this, subscription)) return;
        try {
            this.onSubscribe.accept(this);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            subscription.cancel();
            this.onError(throwable);
            return;
        }
    }

    @Override
    public void request(long l) {
        ((Subscription)this.get()).request(l);
    }
}

