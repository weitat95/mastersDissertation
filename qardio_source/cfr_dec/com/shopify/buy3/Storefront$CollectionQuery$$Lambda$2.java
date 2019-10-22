/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$CollectionQuery$$Lambda$2
implements Storefront.CollectionQuery.ImageArgumentsDefinition {
    private static final Storefront$CollectionQuery$$Lambda$2 instance = new Storefront$CollectionQuery$$Lambda$2();

    private Storefront$CollectionQuery$$Lambda$2() {
    }

    public static Storefront.CollectionQuery.ImageArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.CollectionQuery.ImageArguments imageArguments) {
        Storefront.CollectionQuery.lambda$image$1(imageArguments);
    }
}

