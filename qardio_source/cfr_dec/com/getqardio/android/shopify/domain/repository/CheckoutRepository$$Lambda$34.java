/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.util.Function;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$34
implements Function {
    private static final CheckoutRepository$$Lambda$34 instance = new CheckoutRepository$$Lambda$34();

    private CheckoutRepository$$Lambda$34() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.UserError)object).getMessage();
    }
}

