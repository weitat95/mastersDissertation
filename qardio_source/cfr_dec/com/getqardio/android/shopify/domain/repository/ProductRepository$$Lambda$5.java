/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$5
implements Storefront.QueryRootQueryDefinition {
    private final String arg$1;
    private final Storefront.ProductQueryDefinition arg$2;

    private ProductRepository$$Lambda$5(String string2, Storefront.ProductQueryDefinition productQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = productQueryDefinition;
    }

    public static Storefront.QueryRootQueryDefinition lambdaFactory$(String string2, Storefront.ProductQueryDefinition productQueryDefinition) {
        return new ProductRepository$$Lambda$5(string2, productQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.QueryRootQuery queryRootQuery) {
        ProductRepository.lambda$product$6(this.arg$1, this.arg$2, queryRootQuery);
    }
}

