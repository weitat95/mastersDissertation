/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.RxRetryHandler;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.concurrent.TimeUnit;

final class RxRetryHandler$Builder$$Lambda$2
implements Function {
    private final float arg$1;
    private final TimeUnit arg$2;
    private final long arg$3;

    private RxRetryHandler$Builder$$Lambda$2(float f, TimeUnit timeUnit, long l) {
        this.arg$1 = f;
        this.arg$2 = timeUnit;
        this.arg$3 = l;
    }

    public static Function lambdaFactory$(float f, TimeUnit timeUnit, long l) {
        return new RxRetryHandler$Builder$$Lambda$2(f, timeUnit, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxRetryHandler.Builder.lambda$exponentialBackoff$1(this.arg$1, this.arg$2, this.arg$3, (Integer)object);
    }
}

