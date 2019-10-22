/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.executors;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class StatefulRunnable<T>
implements Runnable {
    protected final AtomicInteger mState = new AtomicInteger(0);

    public void cancel() {
        if (this.mState.compareAndSet(0, 2)) {
            this.onCancellation();
        }
    }

    protected void disposeResult(T t) {
    }

    protected abstract T getResult() throws Exception;

    protected void onCancellation() {
    }

    protected void onFailure(Exception exception) {
    }

    protected void onSuccess(T t) {
    }

    @Override
    public final void run() {
        T t;
        if (!this.mState.compareAndSet(0, 1)) {
            return;
        }
        try {
            t = this.getResult();
            this.mState.set(3);
        }
        catch (Exception exception) {
            this.mState.set(4);
            this.onFailure(exception);
            return;
        }
        try {
            this.onSuccess(t);
            return;
        }
        finally {
            this.disposeResult(t);
        }
    }
}

