/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$10
implements Storefront.QueryRootQueryDefinition {
    private final String arg$1;
    private final Storefront.CheckoutQueryDefinition arg$2;

    private CheckoutRepository$$Lambda$10(String string2, Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = checkoutQueryDefinition;
    }

    public static Storefront.QueryRootQueryDefinition lambdaFactory$(String string2, Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        return new CheckoutRepository$$Lambda$10(string2, checkoutQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.QueryRootQuery queryRootQuery) {
        CheckoutRepository.lambda$shippingRates$8(this.arg$1, this.arg$2, queryRootQuery);
    }
}

