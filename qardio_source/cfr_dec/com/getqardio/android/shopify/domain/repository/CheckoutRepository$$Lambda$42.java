/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$42
implements Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition {
    private final Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition arg$1;

    private CheckoutRepository$$Lambda$42(Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        this.arg$1 = checkoutShippingAddressUpdatePayloadQueryDefinition;
    }

    public static Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition lambdaFactory$(Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$42(checkoutShippingAddressUpdatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        CheckoutRepository.lambda$null$2(this.arg$1, checkoutShippingAddressUpdatePayloadQuery);
    }
}

