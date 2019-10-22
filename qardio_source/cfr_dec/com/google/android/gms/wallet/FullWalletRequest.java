/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.zzl;
import com.google.android.gms.wallet.zzm;

public final class FullWalletRequest
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<FullWalletRequest> CREATOR = new zzm();
    String zzlaa;
    String zzlab;
    Cart zzlal;

    FullWalletRequest() {
    }

    FullWalletRequest(String string2, String string3, Cart cart) {
        this.zzlaa = string2;
        this.zzlab = string3;
        this.zzlal = cart;
    }

    public static Builder newBuilder() {
        return new Builder(new FullWalletRequest(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlaa, false);
        zzbfp.zza(parcel, 3, this.zzlab, false);
        zzbfp.zza(parcel, 4, this.zzlal, n, false);
        zzbfp.zzai(parcel, n2);
    }

    public final class Builder {
        private /* synthetic */ FullWalletRequest zzlam;

        private Builder(FullWalletRequest fullWalletRequest) {
            this.zzlam = fullWalletRequest;
        }

        /* synthetic */ Builder(FullWalletRequest fullWalletRequest, zzl zzl2) {
            this(fullWalletRequest);
        }

        public final FullWalletRequest build() {
            return this.zzlam;
        }

        public final Builder setCart(Cart cart) {
            this.zzlam.zzlal = cart;
            return this;
        }

        public final Builder setGoogleTransactionId(String string2) {
            this.zzlam.zzlaa = string2;
            return this;
        }
    }

}

