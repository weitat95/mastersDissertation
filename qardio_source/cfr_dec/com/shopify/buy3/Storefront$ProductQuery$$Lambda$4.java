/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$ProductQuery$$Lambda$4
implements Storefront.ProductQuery.OptionsArgumentsDefinition {
    private static final Storefront$ProductQuery$$Lambda$4 instance = new Storefront$ProductQuery$$Lambda$4();

    private Storefront$ProductQuery$$Lambda$4() {
    }

    public static Storefront.ProductQuery.OptionsArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.ProductQuery.OptionsArguments optionsArguments) {
        Storefront.ProductQuery.lambda$options$3(optionsArguments);
    }
}

