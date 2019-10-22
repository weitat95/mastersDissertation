/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$40
implements Storefront.NodeQueryDefinition {
    private final Storefront.CheckoutQueryDefinition arg$1;

    private CheckoutRepository$$Lambda$40(Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        this.arg$1 = checkoutQueryDefinition;
    }

    public static Storefront.NodeQueryDefinition lambdaFactory$(Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        return new CheckoutRepository$$Lambda$40(checkoutQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.NodeQuery nodeQuery) {
        CheckoutRepository.lambda$null$7(this.arg$1, nodeQuery);
    }
}

