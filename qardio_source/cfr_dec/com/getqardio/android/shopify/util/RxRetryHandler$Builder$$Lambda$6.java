/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import android.support.v4.util.Pair;
import com.getqardio.android.shopify.util.RxRetryHandler;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import org.reactivestreams.Publisher;

final class RxRetryHandler$Builder$$Lambda$6
implements Function {
    private final RxRetryHandler.Builder arg$1;

    private RxRetryHandler$Builder$$Lambda$6(RxRetryHandler.Builder builder) {
        this.arg$1 = builder;
    }

    public static Function lambdaFactory$(RxRetryHandler.Builder builder) {
        return new RxRetryHandler$Builder$$Lambda$6(builder);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$3((Pair)object);
    }
}

