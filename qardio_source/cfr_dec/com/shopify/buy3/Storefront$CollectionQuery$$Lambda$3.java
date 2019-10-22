/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$CollectionQuery$$Lambda$3
implements Storefront.CollectionQuery.ProductsArgumentsDefinition {
    private static final Storefront$CollectionQuery$$Lambda$3 instance = new Storefront$CollectionQuery$$Lambda$3();

    private Storefront$CollectionQuery$$Lambda$3() {
    }

    public static Storefront.CollectionQuery.ProductsArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.CollectionQuery.ProductsArguments productsArguments) {
        Storefront.CollectionQuery.lambda$products$2(productsArguments);
    }
}

