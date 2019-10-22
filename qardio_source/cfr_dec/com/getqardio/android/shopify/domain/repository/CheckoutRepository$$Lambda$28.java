/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.GraphCall;
import java.lang.invoke.LambdaForm;
import java.util.concurrent.Callable;

final class CheckoutRepository$$Lambda$28
implements Callable {
    private final GraphCall arg$1;

    private CheckoutRepository$$Lambda$28(GraphCall graphCall) {
        this.arg$1 = graphCall;
    }

    public static Callable lambdaFactory$(GraphCall graphCall) {
        return new CheckoutRepository$$Lambda$28(graphCall);
    }

    @LambdaForm.Hidden
    public Object call() {
        return this.arg$1.clone();
    }
}

