/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Utils;
import java.util.concurrent.TimeUnit;

public final class HttpCachePolicy {
    public static final ExpirePolicy CACHE_FIRST;
    public static final ExpirePolicy CACHE_ONLY;
    public static final ExpirePolicy NETWORK_FIRST;
    public static final Policy NETWORK_ONLY;

    static {
        CACHE_ONLY = new ExpirePolicy(FetchStrategy.CACHE_ONLY);
        NETWORK_ONLY = new Policy(FetchStrategy.NETWORK_ONLY, 0L, null);
        CACHE_FIRST = new ExpirePolicy(FetchStrategy.CACHE_FIRST);
        NETWORK_FIRST = new ExpirePolicy(FetchStrategy.NETWORK_FIRST);
    }

    public static final class ExpirePolicy
    extends Policy {
        ExpirePolicy(FetchStrategy fetchStrategy) {
            super(fetchStrategy, 0L, null);
        }

        ExpirePolicy(FetchStrategy fetchStrategy, long l, TimeUnit timeUnit) {
            super(fetchStrategy, l, timeUnit);
        }

        public ExpirePolicy expireAfter(long l, TimeUnit timeUnit) {
            return new ExpirePolicy(this.fetchStrategy, l, Utils.checkNotNull(timeUnit, "expireTimeUnit == null"));
        }
    }

    public static enum FetchStrategy {
        CACHE_ONLY,
        NETWORK_ONLY,
        CACHE_FIRST,
        NETWORK_FIRST;

    }

    public static class Policy {
        final TimeUnit expireTimeUnit;
        final long expireTimeout;
        final FetchStrategy fetchStrategy;

        Policy(FetchStrategy fetchStrategy, long l, TimeUnit timeUnit) {
            this.fetchStrategy = fetchStrategy;
            this.expireTimeout = l;
            this.expireTimeUnit = timeUnit;
        }

        long expireTimeoutMs() {
            if (this.expireTimeUnit == null) {
                return 0L;
            }
            return this.expireTimeUnit.toMillis(this.expireTimeout);
        }
    }

}

