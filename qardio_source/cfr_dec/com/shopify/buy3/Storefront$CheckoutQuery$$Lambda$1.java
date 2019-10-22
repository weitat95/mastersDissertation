/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.Storefront;

final class Storefront$CheckoutQuery$$Lambda$1
implements Storefront.CheckoutQuery.LineItemsArgumentsDefinition {
    private static final Storefront$CheckoutQuery$$Lambda$1 instance = new Storefront$CheckoutQuery$$Lambda$1();

    private Storefront$CheckoutQuery$$Lambda$1() {
    }

    public static Storefront.CheckoutQuery.LineItemsArgumentsDefinition lambdaFactory$() {
        return instance;
    }

    @Override
    public void define(Storefront.CheckoutQuery.LineItemsArguments lineItemsArguments) {
        Storefront.CheckoutQuery.lambda$lineItems$0(lineItemsArguments);
    }
}

