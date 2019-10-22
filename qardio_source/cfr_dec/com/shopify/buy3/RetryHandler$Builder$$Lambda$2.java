/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Condition;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.RetryHandler;

final class RetryHandler$Builder$$Lambda$2
implements Condition {
    private static final RetryHandler$Builder$$Lambda$2 instance = new RetryHandler$Builder$$Lambda$2();

    private RetryHandler$Builder$$Lambda$2() {
    }

    public static Condition lambdaFactory$() {
        return instance;
    }

    public boolean check(Object object) {
        return RetryHandler.Builder.lambda$new$1((GraphError)object);
    }
}

