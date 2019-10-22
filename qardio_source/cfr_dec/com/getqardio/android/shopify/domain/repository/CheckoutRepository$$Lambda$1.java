/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$1
implements Storefront.MutationQueryDefinition {
    private final Storefront.CheckoutCreateInput arg$1;
    private final Storefront.CheckoutCreatePayloadQueryDefinition arg$2;

    private CheckoutRepository$$Lambda$1(Storefront.CheckoutCreateInput checkoutCreateInput, Storefront.CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition) {
        this.arg$1 = checkoutCreateInput;
        this.arg$2 = checkoutCreatePayloadQueryDefinition;
    }

    public static Storefront.MutationQueryDefinition lambdaFactory$(Storefront.CheckoutCreateInput checkoutCreateInput, Storefront.CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$1(checkoutCreateInput, checkoutCreatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.MutationQuery mutationQuery) {
        CheckoutRepository.lambda$create$0(this.arg$1, this.arg$2, mutationQuery);
    }
}

