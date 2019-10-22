/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Condition;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.RetryHandler$Builder$$Lambda$1;
import com.shopify.buy3.RetryHandler$Builder$$Lambda$2;
import com.shopify.buy3.Utils;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class RetryHandler {
    public static final RetryHandler NO_RETRY = RetryHandler.delay(0L, TimeUnit.MILLISECONDS).maxCount(0).build();
    private final float backoffMultiplier;
    private final long delayBetweenRetriesMs;
    private final Condition<GraphError> errorRetryCondition;
    private final int maxCount;
    private final Condition<GraphResponse> responseRetryCondition;
    private final AtomicInteger retryAttempt = new AtomicInteger();

    /*
     * Enabled aggressive block sorting
     */
    private RetryHandler(Builder builder) {
        int n = builder.maxCount == -1 ? Integer.MAX_VALUE : builder.maxCount;
        this.maxCount = n;
        this.delayBetweenRetriesMs = builder.delayBetweenRetriesMs;
        this.backoffMultiplier = builder.backoffMultiplier;
        this.responseRetryCondition = builder.responseRetryCondition;
        this.errorRetryCondition = builder.errorRetryCondition;
    }

    public static Builder delay(long l, TimeUnit timeUnit) {
        return new Builder().delay(l, timeUnit);
    }

    private boolean retry() {
        int n = this.retryAttempt.get();
        while (n < this.maxCount && !this.retryAttempt.compareAndSet(n, n + 1)) {
            n = this.retryAttempt.get();
        }
        return n < this.maxCount;
    }

    long nextRetryDelayMs() {
        return Math.max((long)((double)this.delayBetweenRetriesMs * Math.pow(this.backoffMultiplier, this.retryAttempt.get())), this.delayBetweenRetriesMs);
    }

    public boolean retry(GraphError graphError) {
        return this.errorRetryCondition.check(graphError) && this.retry();
    }

    public boolean retry(GraphResponse graphResponse) {
        return this.responseRetryCondition.check(graphResponse) && this.retry();
    }

    public static final class Builder {
        float backoffMultiplier = 1.0f;
        long delayBetweenRetriesMs;
        Condition<GraphError> errorRetryCondition;
        int maxCount = -1;
        Condition<GraphResponse> responseRetryCondition = RetryHandler$Builder$$Lambda$1.lambdaFactory$();

        private Builder() {
            this.errorRetryCondition = RetryHandler$Builder$$Lambda$2.lambdaFactory$();
        }

        static /* synthetic */ boolean lambda$new$0(GraphResponse graphResponse) {
            return false;
        }

        static /* synthetic */ boolean lambda$new$1(GraphError graphError) {
            return true;
        }

        public RetryHandler build() {
            return new RetryHandler(this);
        }

        Builder delay(long l, TimeUnit timeUnit) {
            this.delayBetweenRetriesMs = Utils.checkNotNull(timeUnit, "timeUnit == null").toMillis(l);
            return this;
        }

        public Builder maxCount(int n) {
            this.maxCount = n;
            return this;
        }
    }

}

