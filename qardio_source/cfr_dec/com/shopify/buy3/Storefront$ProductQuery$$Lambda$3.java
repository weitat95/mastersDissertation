/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$ProductQuery$$Lambda$3
implements Storefront.ProductQuery.ImagesArgumentsDefinition {
    private static final Storefront$ProductQuery$$Lambda$3 instance = new Storefront$ProductQuery$$Lambda$3();

    private Storefront$ProductQuery$$Lambda$3() {
    }

    public static Storefront.ProductQuery.ImagesArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
        Storefront.ProductQuery.lambda$images$2(imagesArguments);
    }
}

