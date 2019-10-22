/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingLineUpdateInteractor$$Lambda$1
implements Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition {
    private static final RealCheckoutShippingLineUpdateInteractor$$Lambda$1 instance = new RealCheckoutShippingLineUpdateInteractor$$Lambda$1();

    private RealCheckoutShippingLineUpdateInteractor$$Lambda$1() {
    }

    public static Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        RealCheckoutShippingLineUpdateInteractor.lambda$execute$0(checkoutShippingLineUpdatePayloadQuery);
    }
}

