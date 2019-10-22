/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.HttpCallbackWithRetry;

final class HttpCallbackWithRetry$$Lambda$1
implements Runnable {
    private final HttpCallbackWithRetry arg$1;
    private final GraphResponse arg$2;

    private HttpCallbackWithRetry$$Lambda$1(HttpCallbackWithRetry httpCallbackWithRetry, GraphResponse graphResponse) {
        this.arg$1 = httpCallbackWithRetry;
        this.arg$2 = graphResponse;
    }

    public static Runnable lambdaFactory$(HttpCallbackWithRetry httpCallbackWithRetry, GraphResponse graphResponse) {
        return new HttpCallbackWithRetry$$Lambda$1(httpCallbackWithRetry, graphResponse);
    }

    @Override
    public void run() {
        HttpCallbackWithRetry.lambda$notifyResponse$0(this.arg$1, this.arg$2);
    }
}

