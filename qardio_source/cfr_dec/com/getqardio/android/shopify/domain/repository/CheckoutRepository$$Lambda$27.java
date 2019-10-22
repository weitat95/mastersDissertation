/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$27
implements Storefront.QueryRootQueryDefinition {
    private final String arg$1;
    private final Storefront.NodeQueryDefinition arg$2;

    private CheckoutRepository$$Lambda$27(String string2, Storefront.NodeQueryDefinition nodeQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = nodeQueryDefinition;
    }

    public static Storefront.QueryRootQueryDefinition lambdaFactory$(String string2, Storefront.NodeQueryDefinition nodeQueryDefinition) {
        return new CheckoutRepository$$Lambda$27(string2, nodeQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.QueryRootQuery queryRootQuery) {
        CheckoutRepository.lambda$paymentById$22(this.arg$1, this.arg$2, queryRootQuery);
    }
}

