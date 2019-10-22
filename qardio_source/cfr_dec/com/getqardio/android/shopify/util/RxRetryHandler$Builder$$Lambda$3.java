/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.RxRetryHandler;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxRetryHandler$Builder$$Lambda$3
implements Function {
    private final RxRetryHandler.Builder arg$1;

    private RxRetryHandler$Builder$$Lambda$3(RxRetryHandler.Builder builder) {
        this.arg$1 = builder;
    }

    public static Function lambdaFactory$(RxRetryHandler.Builder builder) {
        return new RxRetryHandler$Builder$$Lambda$3(builder);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$build$4((Flowable)object);
    }
}

