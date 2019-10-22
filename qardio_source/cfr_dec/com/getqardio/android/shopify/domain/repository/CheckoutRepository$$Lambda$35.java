/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$35
implements Storefront.CheckoutEmailUpdatePayloadQueryDefinition {
    private final Storefront.CheckoutEmailUpdatePayloadQueryDefinition arg$1;

    private CheckoutRepository$$Lambda$35(Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition) {
        this.arg$1 = checkoutEmailUpdatePayloadQueryDefinition;
    }

    public static Storefront.CheckoutEmailUpdatePayloadQueryDefinition lambdaFactory$(Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$35(checkoutEmailUpdatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        CheckoutRepository.lambda$null$15(this.arg$1, checkoutEmailUpdatePayloadQuery);
    }
}

