/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphError;
import com.shopify.buy3.HttpCallbackWithRetry;

final class HttpCallbackWithRetry$$Lambda$2
implements Runnable {
    private final HttpCallbackWithRetry arg$1;
    private final GraphError arg$2;

    private HttpCallbackWithRetry$$Lambda$2(HttpCallbackWithRetry httpCallbackWithRetry, GraphError graphError) {
        this.arg$1 = httpCallbackWithRetry;
        this.arg$2 = graphError;
    }

    public static Runnable lambdaFactory$(HttpCallbackWithRetry httpCallbackWithRetry, GraphError graphError) {
        return new HttpCallbackWithRetry$$Lambda$2(httpCallbackWithRetry, graphError);
    }

    @Override
    public void run() {
        HttpCallbackWithRetry.lambda$notifyError$1(this.arg$1, this.arg$2);
    }
}

