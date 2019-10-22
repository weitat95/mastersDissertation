/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$14
implements Function {
    private static final CheckoutRepository$$Lambda$14 instance = new CheckoutRepository$$Lambda$14();

    private CheckoutRepository$$Lambda$14() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.Checkout)object).getAvailableShippingRates();
    }
}

