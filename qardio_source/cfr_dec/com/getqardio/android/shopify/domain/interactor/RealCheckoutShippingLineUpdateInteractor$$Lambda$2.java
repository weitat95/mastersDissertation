/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingLineUpdateInteractor$$Lambda$2
implements Function {
    private static final RealCheckoutShippingLineUpdateInteractor$$Lambda$2 instance = new RealCheckoutShippingLineUpdateInteractor$$Lambda$2();

    private RealCheckoutShippingLineUpdateInteractor$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.convertToCheckout((Storefront.Checkout)object);
    }
}

