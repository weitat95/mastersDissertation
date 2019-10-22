/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;

public final class BlockingMultiObserver<T>
extends CountDownLatch
implements CompletableObserver,
MaybeObserver<T>,
SingleObserver<T> {
    volatile boolean cancelled;
    Disposable d;
    Throwable error;
    T value;

    public BlockingMultiObserver() {
        super(1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T blockingGet() {
        Throwable throwable;
        if (this.getCount() != 0L) {
            try {
                BlockingHelper.verifyNonBlocking();
                this.await();
            }
            catch (InterruptedException interruptedException) {
                this.dispose();
                throw ExceptionHelper.wrapOrThrow(interruptedException);
            }
        }
        if ((throwable = this.error) != null) {
            throw ExceptionHelper.wrapOrThrow(throwable);
        }
        return this.value;
    }

    void dispose() {
        this.cancelled = true;
        Disposable disposable = this.d;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {
        this.countDown();
    }

    @Override
    public void onError(Throwable throwable) {
        this.error = throwable;
        this.countDown();
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.d = disposable;
        if (this.cancelled) {
            disposable.dispose();
        }
    }

    @Override
    public void onSuccess(T t) {
        this.value = t;
        this.countDown();
    }
}

