/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingRatesInteractor$$Lambda$1
implements Storefront.CheckoutQueryDefinition {
    private static final RealCheckoutShippingRatesInteractor$$Lambda$1 instance = new RealCheckoutShippingRatesInteractor$$Lambda$1();

    private RealCheckoutShippingRatesInteractor$$Lambda$1() {
    }

    public static Storefront.CheckoutQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutQuery checkoutQuery) {
        RealCheckoutShippingRatesInteractor.lambda$execute$0(checkoutQuery);
    }
}

