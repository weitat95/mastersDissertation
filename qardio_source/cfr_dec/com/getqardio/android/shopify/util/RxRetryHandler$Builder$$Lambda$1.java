/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.RxRetryHandler;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class RxRetryHandler$Builder$$Lambda$1
implements Predicate {
    private static final RxRetryHandler$Builder$$Lambda$1 instance = new RxRetryHandler$Builder$$Lambda$1();

    private RxRetryHandler$Builder$$Lambda$1() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return RxRetryHandler.Builder.lambda$new$0((Throwable)object);
    }
}

