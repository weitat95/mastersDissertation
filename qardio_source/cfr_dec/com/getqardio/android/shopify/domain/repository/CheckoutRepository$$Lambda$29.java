/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.RxUtil;
import com.shopify.buy3.GraphCall;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$29
implements Function {
    private static final CheckoutRepository$$Lambda$29 instance = new CheckoutRepository$$Lambda$29();

    private CheckoutRepository$$Lambda$29() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxUtil.rxGraphQueryCall((GraphCall)object);
    }
}

