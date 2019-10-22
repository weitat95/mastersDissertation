/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.RetryState;

class RetryManager {
    long lastRetry;
    private RetryState retryState;

    public RetryManager(RetryState retryState) {
        if (retryState == null) {
            throw new NullPointerException("retryState must not be null");
        }
        this.retryState = retryState;
    }

    public boolean canRetry(long l) {
        long l2 = this.retryState.getRetryDelay();
        return l - this.lastRetry >= 1000000L * l2;
    }

    public void recordRetry(long l) {
        this.lastRetry = l;
        this.retryState = this.retryState.nextRetryState();
    }

    public void reset() {
        this.lastRetry = 0L;
        this.retryState = this.retryState.initialRetryState();
    }
}

