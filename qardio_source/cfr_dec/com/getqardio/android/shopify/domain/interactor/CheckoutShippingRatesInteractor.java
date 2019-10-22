/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Checkout;
import io.reactivex.Single;

public interface CheckoutShippingRatesInteractor {
    public Single<Checkout.ShippingRates> execute(String var1);
}

