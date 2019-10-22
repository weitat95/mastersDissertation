/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.shopify.buy3.Storefront;

final class PaymentFragment
implements Storefront.PaymentQueryDefinition {
    PaymentFragment() {
    }

    @Override
    public void define(Storefront.PaymentQuery paymentQuery) {
        paymentQuery.ready().errorMessage();
    }
}

