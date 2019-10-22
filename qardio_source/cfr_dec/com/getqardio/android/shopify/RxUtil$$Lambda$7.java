/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.shopify.buy3.GraphCall;
import io.reactivex.functions.Cancellable;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$7
implements Cancellable {
    private final GraphCall arg$1;

    private RxUtil$$Lambda$7(GraphCall graphCall) {
        this.arg$1 = graphCall;
    }

    public static Cancellable lambdaFactory$(GraphCall graphCall) {
        return new RxUtil$$Lambda$7(graphCall);
    }

    @LambdaForm.Hidden
    @Override
    public void cancel() {
        this.arg$1.cancel();
    }
}

