/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.shopify.buy3.Storefront;

final class CheckoutShippingRateFragment
implements Storefront.ShippingRateQueryDefinition {
    CheckoutShippingRateFragment() {
    }

    @Override
    public void define(Storefront.ShippingRateQuery shippingRateQuery) {
        shippingRateQuery.title().handle().price();
    }
}

