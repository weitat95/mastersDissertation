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
import com.google.android.gms.wallet.PaymentMethodToken;

public final class zzaf
implements Parcelable.Creator<PaymentMethodToken> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string2 = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block4;
                }
                case 3: 
            }
            string2 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new PaymentMethodToken(n2, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new PaymentMethodToken[n];
    }
}

