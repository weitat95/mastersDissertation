/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$1
implements Storefront.QueryRootQueryDefinition {
    private final String arg$1;
    private final int arg$2;
    private final String arg$3;
    private final Storefront.ProductConnectionQueryDefinition arg$4;

    private ProductRepository$$Lambda$1(String string2, int n, String string3, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = n;
        this.arg$3 = string3;
        this.arg$4 = productConnectionQueryDefinition;
    }

    public static Storefront.QueryRootQueryDefinition lambdaFactory$(String string2, int n, String string3, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        return new ProductRepository$$Lambda$1(string2, n, string3, productConnectionQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.QueryRootQuery queryRootQuery) {
        ProductRepository.lambda$nextPage$3(this.arg$1, this.arg$2, this.arg$3, this.arg$4, queryRootQuery);
    }
}

