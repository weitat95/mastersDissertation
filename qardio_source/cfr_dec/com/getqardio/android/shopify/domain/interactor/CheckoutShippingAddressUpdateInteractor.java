/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.shopify.buy3.pay.PayAddress;
import io.reactivex.Single;

public interface CheckoutShippingAddressUpdateInteractor {
    public Single<Checkout> execute(String var1, PayAddress var2);
}

