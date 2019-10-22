/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$16
implements Function {
    private static final CheckoutRepository$$Lambda$16 instance = new CheckoutRepository$$Lambda$16();

    private CheckoutRepository$$Lambda$16() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.Mutation)object).getCheckoutShippingLineUpdate();
    }
}

