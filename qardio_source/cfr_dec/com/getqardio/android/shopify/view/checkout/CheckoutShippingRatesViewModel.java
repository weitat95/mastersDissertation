/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.LiveData;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.ViewModel;
import java.util.UUID;

public interface CheckoutShippingRatesViewModel
extends ViewModel {
    public static final int REQUEST_ID_FETCH_SHIPPING_RATES = UUID.randomUUID().hashCode();

    public void fetchShippingRates();

    public void selectShippingRate(Checkout.ShippingRate var1);

    public LiveData<Checkout.ShippingRate> selectedShippingRateLiveData();

    public LiveData<Checkout.ShippingRates> shippingRatesLiveData();
}

