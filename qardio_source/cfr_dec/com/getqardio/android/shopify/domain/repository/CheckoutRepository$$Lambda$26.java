/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$26
implements Function {
    private static final CheckoutRepository$$Lambda$26 instance = new CheckoutRepository$$Lambda$26();

    private CheckoutRepository$$Lambda$26() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.CheckoutCompleteWithTokenizedPaymentPayload)object).getPayment();
    }
}

