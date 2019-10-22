/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.shopify.view.cart;

import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.ViewModel;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayCart;
import java.util.UUID;

public interface CartDetailsViewModel
extends ViewModel {
    public static final int REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT;
    public static final int REQUEST_ID_CREATE_WEB_CHECKOUT;
    public static final int REQUEST_ID_PREPARE_ANDROID_PAY;
    public static final int REQUEST_ID_UPDATE_CART;

    static {
        REQUEST_ID_UPDATE_CART = UUID.randomUUID().hashCode();
        REQUEST_ID_CREATE_WEB_CHECKOUT = UUID.randomUUID().hashCode();
        REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT = UUID.randomUUID().hashCode();
        REQUEST_ID_PREPARE_ANDROID_PAY = UUID.randomUUID().hashCode();
    }

    public LifeCycleBoundCallback<AndroidPayCheckout> androidPayCheckoutCallback();

    public LifeCycleBoundCallback<PayCart> androidPayStartCheckoutCallback();

    public void handleMaskedWalletResponse(int var1, int var2, Intent var3);

    public void onGoogleApiClientConnectionChanged(boolean var1);

    public void restoreState(Bundle var1);

    public Bundle saveState();

    public LifeCycleBoundCallback<Checkout> webCheckoutCallback();

    public static final class AndroidPayCheckout {
        public final String checkoutId;
        public final MaskedWallet maskedWallet;
        public final PayCart payCart;

        AndroidPayCheckout(String string2, PayCart payCart, MaskedWallet maskedWallet) {
            this.checkoutId = string2;
            this.payCart = payCart;
            this.maskedWallet = maskedWallet;
        }
    }

}

