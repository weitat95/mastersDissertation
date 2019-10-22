/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$ProductQuery$$Lambda$5
implements Storefront.ProductQuery.VariantsArgumentsDefinition {
    private static final Storefront$ProductQuery$$Lambda$5 instance = new Storefront$ProductQuery$$Lambda$5();

    private Storefront$ProductQuery$$Lambda$5() {
    }

    public static Storefront.ProductQuery.VariantsArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
        Storefront.ProductQuery.lambda$variants$4(variantsArguments);
    }
}

