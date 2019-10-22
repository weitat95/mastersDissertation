/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.LiveData;
import java.math.BigDecimal;

public interface CartHeaderViewModel {
    public void androidPayCheckout();

    public LiveData<BigDecimal> cartTotalLiveData();

    public LiveData<Boolean> googleApiClientConnectionData();

    public void webCheckout();
}

