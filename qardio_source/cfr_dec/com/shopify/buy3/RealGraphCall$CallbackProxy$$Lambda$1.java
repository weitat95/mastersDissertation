/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphCall;
import com.shopify.buy3.RealGraphCall;

final class RealGraphCall$CallbackProxy$$Lambda$1
implements Runnable {
    private final GraphCall.Callback arg$1;

    private RealGraphCall$CallbackProxy$$Lambda$1(GraphCall.Callback callback) {
        this.arg$1 = callback;
    }

    public static Runnable lambdaFactory$(GraphCall.Callback callback) {
        return new RealGraphCall$CallbackProxy$$Lambda$1(callback);
    }

    @Override
    public void run() {
        RealGraphCall.CallbackProxy.lambda$cancel$0(this.arg$1);
    }
}

