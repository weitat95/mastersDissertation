/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$38
implements Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition {
    private final Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition arg$1;

    private CheckoutRepository$$Lambda$38(Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        this.arg$1 = checkoutShippingLineUpdatePayloadQueryDefinition;
    }

    public static Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition lambdaFactory$(Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$38(checkoutShippingLineUpdatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        CheckoutRepository.lambda$null$11(this.arg$1, checkoutShippingLineUpdatePayloadQuery);
    }
}

