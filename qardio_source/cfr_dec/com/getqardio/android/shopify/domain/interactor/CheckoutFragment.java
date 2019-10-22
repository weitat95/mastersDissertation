/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutFragment$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRateFragment;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRatesFragment;
import com.shopify.buy3.Storefront;

final class CheckoutFragment
implements Storefront.CheckoutQueryDefinition {
    CheckoutFragment() {
    }

    static /* synthetic */ void lambda$define$3(Storefront.CheckoutLineItemConnectionQuery checkoutLineItemConnectionQuery) {
        checkoutLineItemConnectionQuery.edges(CheckoutFragment$$Lambda$2.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$0(Storefront.ProductVariantQuery productVariantQuery) {
        productVariantQuery.price();
    }

    static /* synthetic */ void lambda$null$1(Storefront.CheckoutLineItemQuery checkoutLineItemQuery) {
        checkoutLineItemQuery.variant(CheckoutFragment$$Lambda$4.lambdaFactory$()).quantity().title();
    }

    static /* synthetic */ void lambda$null$2(Storefront.CheckoutLineItemEdgeQuery checkoutLineItemEdgeQuery) {
        checkoutLineItemEdgeQuery.node(CheckoutFragment$$Lambda$3.lambdaFactory$());
    }

    @Override
    public void define(Storefront.CheckoutQuery checkoutQuery) {
        checkoutQuery.webUrl().requiresShipping().currencyCode().lineItems(250, CheckoutFragment$$Lambda$1.lambdaFactory$()).totalPrice().totalTax().subtotalPrice().availableShippingRates(new CheckoutShippingRatesFragment()).shippingLine(new CheckoutShippingRateFragment());
    }
}

