/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRateFragment;
import com.shopify.buy3.Storefront;

final class CheckoutShippingRatesFragment
implements Storefront.AvailableShippingRatesQueryDefinition {
    CheckoutShippingRatesFragment() {
    }

    @Override
    public void define(Storefront.AvailableShippingRatesQuery availableShippingRatesQuery) {
        availableShippingRatesQuery.ready().shippingRates(new CheckoutShippingRateFragment());
    }
}

