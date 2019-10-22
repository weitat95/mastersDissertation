/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$7
implements Storefront.NodeQueryDefinition {
    private final Storefront.ProductQueryDefinition arg$1;

    private ProductRepository$$Lambda$7(Storefront.ProductQueryDefinition productQueryDefinition) {
        this.arg$1 = productQueryDefinition;
    }

    public static Storefront.NodeQueryDefinition lambdaFactory$(Storefront.ProductQueryDefinition productQueryDefinition) {
        return new ProductRepository$$Lambda$7(productQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.NodeQuery nodeQuery) {
        ProductRepository.lambda$null$5(this.arg$1, nodeQuery);
    }
}

