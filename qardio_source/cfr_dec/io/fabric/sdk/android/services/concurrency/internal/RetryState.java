/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;

public class RetryState {
    private final Backoff backoff;
    private final int retryCount;
    private final RetryPolicy retryPolicy;

    public RetryState(int n, Backoff backoff, RetryPolicy retryPolicy) {
        this.retryCount = n;
        this.backoff = backoff;
        this.retryPolicy = retryPolicy;
    }

    public RetryState(Backoff backoff, RetryPolicy retryPolicy) {
        this(0, backoff, retryPolicy);
    }

    public long getRetryDelay() {
        return this.backoff.getDelayMillis(this.retryCount);
    }

    public RetryState initialRetryState() {
        return new RetryState(this.backoff, this.retryPolicy);
    }

    public RetryState nextRetryState() {
        return new RetryState(this.retryCount + 1, this.backoff, this.retryPolicy);
    }
}

