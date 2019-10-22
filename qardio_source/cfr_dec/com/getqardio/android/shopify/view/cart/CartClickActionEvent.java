/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.shopify.view.cart;

import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.shopify.view.ScreenActionEvent;

public final class CartClickActionEvent
extends ScreenActionEvent
implements Parcelable {
    public static final String ACTION = CartClickActionEvent.class.getSimpleName();
    public static final Parcelable.Creator<CartClickActionEvent> CREATOR = new Parcelable.Creator<CartClickActionEvent>(){

        public CartClickActionEvent createFromParcel(Parcel parcel) {
            return new CartClickActionEvent(parcel);
        }

        public CartClickActionEvent[] newArray(int n) {
            return new CartClickActionEvent[n];
        }
    };

    public CartClickActionEvent() {
        super(ACTION);
    }

    protected CartClickActionEvent(Parcel parcel) {
        super(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}

