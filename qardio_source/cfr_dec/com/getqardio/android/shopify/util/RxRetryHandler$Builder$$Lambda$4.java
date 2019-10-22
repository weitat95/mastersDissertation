/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import android.support.v4.util.Pair;
import io.reactivex.functions.BiFunction;
import java.lang.invoke.LambdaForm;

final class RxRetryHandler$Builder$$Lambda$4
implements BiFunction {
    private static final RxRetryHandler$Builder$$Lambda$4 instance = new RxRetryHandler$Builder$$Lambda$4();

    private RxRetryHandler$Builder$$Lambda$4() {
    }

    public static BiFunction lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2) {
        return Pair.create((Throwable)object, (Long)object2);
    }
}

