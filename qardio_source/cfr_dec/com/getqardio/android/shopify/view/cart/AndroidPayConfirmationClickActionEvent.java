/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.shopify.view.cart;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ScreenActionEvent;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayCart;

public final class AndroidPayConfirmationClickActionEvent
extends ScreenActionEvent
implements Parcelable {
    public static final String ACTION;
    public static final Parcelable.Creator<AndroidPayConfirmationClickActionEvent> CREATOR;
    public static final String EXTRAS_CHECKOUT_ID = "checkout_id";
    public static final String EXTRAS_MASKED_WALLET = "masked_wallet";
    public static final String EXTRAS_PAY_CART = "pay_cart";

    static {
        CREATOR = new Parcelable.Creator<AndroidPayConfirmationClickActionEvent>(){

            public AndroidPayConfirmationClickActionEvent createFromParcel(Parcel parcel) {
                return new AndroidPayConfirmationClickActionEvent(parcel);
            }

            public AndroidPayConfirmationClickActionEvent[] newArray(int n) {
                return new AndroidPayConfirmationClickActionEvent[n];
            }
        };
        ACTION = AndroidPayConfirmationClickActionEvent.class.getSimpleName();
    }

    AndroidPayConfirmationClickActionEvent(Parcel parcel) {
        super(parcel);
    }

    public AndroidPayConfirmationClickActionEvent(String string2, PayCart payCart, MaskedWallet maskedWallet) {
        super(ACTION);
        this.payload.putString(EXTRAS_CHECKOUT_ID, Util.checkNotBlank(string2, "checkoutId can't be blank"));
        this.payload.putParcelable(EXTRAS_PAY_CART, (Parcelable)Util.checkNotNull(payCart, "payCart == null"));
        this.payload.putParcelable(EXTRAS_MASKED_WALLET, (Parcelable)Util.checkNotNull(maskedWallet, "maskedWallet == null"));
    }

    public String checkoutId() {
        return this.payload().getString(EXTRAS_CHECKOUT_ID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public MaskedWallet maskedWallet() {
        return (MaskedWallet)this.payload().getParcelable(EXTRAS_MASKED_WALLET);
    }

    public PayCart payCart() {
        return (PayCart)this.payload().getParcelable(EXTRAS_PAY_CART);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}

