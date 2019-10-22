/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingAddressUpdateInteractor$$Lambda$1
implements Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition {
    private static final RealCheckoutShippingAddressUpdateInteractor$$Lambda$1 instance = new RealCheckoutShippingAddressUpdateInteractor$$Lambda$1();

    private RealCheckoutShippingAddressUpdateInteractor$$Lambda$1() {
    }

    public static Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        RealCheckoutShippingAddressUpdateInteractor.lambda$execute$0(checkoutShippingAddressUpdatePayloadQuery);
    }
}

