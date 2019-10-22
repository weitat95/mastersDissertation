/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$8
implements Storefront.NodeQueryDefinition {
    private final int arg$1;
    private final String arg$2;
    private final Storefront.ProductConnectionQueryDefinition arg$3;

    private ProductRepository$$Lambda$8(int n, String string2, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        this.arg$1 = n;
        this.arg$2 = string2;
        this.arg$3 = productConnectionQueryDefinition;
    }

    public static Storefront.NodeQueryDefinition lambdaFactory$(int n, String string2, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        return new ProductRepository$$Lambda$8(n, string2, productConnectionQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.NodeQuery nodeQuery) {
        ProductRepository.lambda$null$2(this.arg$1, this.arg$2, this.arg$3, nodeQuery);
    }
}

