/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.ValidationEnforcer;

public final class RetryStrategy {
    public static final RetryStrategy DEFAULT_EXPONENTIAL = new RetryStrategy(1, 30, 3600);
    public static final RetryStrategy DEFAULT_LINEAR = new RetryStrategy(2, 30, 3600);
    private final int initialBackoff;
    private final int maximumBackoff;
    private final int policy;

    RetryStrategy(int n, int n2, int n3) {
        this.policy = n;
        this.initialBackoff = n2;
        this.maximumBackoff = n3;
    }

    public int getInitialBackoff() {
        return this.initialBackoff;
    }

    public int getMaximumBackoff() {
        return this.maximumBackoff;
    }

    public int getPolicy() {
        return this.policy;
    }

    static final class Builder {
        private final ValidationEnforcer validator;

        Builder(ValidationEnforcer validationEnforcer) {
            this.validator = validationEnforcer;
        }
    }

}

