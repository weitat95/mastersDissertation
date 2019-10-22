/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.ViewModel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayCart;
import java.util.UUID;

public interface CheckoutViewModel
extends ViewModel {
    public static final int REQUEST_ID_APPLY_SHIPPING_RATE;
    public static final int REQUEST_ID_COMPLETE_CHECKOUT;
    public static final int REQUEST_ID_CONFIRM_CHECKOUT;
    public static final int REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS;

    static {
        REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS = UUID.randomUUID().hashCode();
        REQUEST_ID_APPLY_SHIPPING_RATE = UUID.randomUUID().hashCode();
        REQUEST_ID_CONFIRM_CHECKOUT = UUID.randomUUID().hashCode();
        REQUEST_ID_COMPLETE_CHECKOUT = UUID.randomUUID().hashCode();
    }

    public void confirmCheckout(GoogleApiClient var1);

    public void handleWalletResponse(int var1, int var2, Intent var3, GoogleApiClient var4);

    public LiveData<MaskedWallet> maskedWalletLiveData();

    public LiveData<PayCart> payCartLiveData();

    public LifeCycleBoundCallback<Payment> successPaymentLiveData();

    public static class ShippingRateMissingException
    extends RuntimeException {
    }

}

