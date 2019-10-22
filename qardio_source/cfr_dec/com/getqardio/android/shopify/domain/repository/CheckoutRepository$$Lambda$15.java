/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$15
implements Storefront.MutationQueryDefinition {
    private final String arg$1;
    private final String arg$2;
    private final Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition arg$3;

    private CheckoutRepository$$Lambda$15(String string2, String string3, Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = string3;
        this.arg$3 = checkoutShippingLineUpdatePayloadQueryDefinition;
    }

    public static Storefront.MutationQueryDefinition lambdaFactory$(String string2, String string3, Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$15(string2, string3, checkoutShippingLineUpdatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.MutationQuery mutationQuery) {
        CheckoutRepository.lambda$updateShippingLine$12(this.arg$1, this.arg$2, this.arg$3, mutationQuery);
    }
}

