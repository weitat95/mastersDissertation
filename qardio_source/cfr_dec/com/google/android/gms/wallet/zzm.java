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
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWalletRequest;

public final class zzm
implements Parcelable.Creator<FullWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Cart cart = null;
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 4: 
            }
            cart = zzbfn.zza(parcel, n2, Cart.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new FullWalletRequest(string3, string2, cart);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new FullWalletRequest[n];
    }
}

