/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;

public class DefaultRetryPolicy
implements RetryPolicy {
    private final int maxRetries;

    public DefaultRetryPolicy() {
        this(1);
    }

    public DefaultRetryPolicy(int n) {
        this.maxRetries = n;
    }
}

