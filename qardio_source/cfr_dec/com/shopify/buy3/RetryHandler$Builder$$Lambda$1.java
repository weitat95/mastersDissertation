/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Condition;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.RetryHandler;

final class RetryHandler$Builder$$Lambda$1
implements Condition {
    private static final RetryHandler$Builder$$Lambda$1 instance = new RetryHandler$Builder$$Lambda$1();

    private RetryHandler$Builder$$Lambda$1() {
    }

    public static Condition lambdaFactory$() {
        return instance;
    }

    public boolean check(Object object) {
        return RetryHandler.Builder.lambda$new$0((GraphResponse)object);
    }
}

