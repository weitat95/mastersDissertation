/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;

public final class zzah
implements Parcelable.Creator<PaymentMethodTokenizationParameters> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        Bundle bundle = null;
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
            bundle = zzbfn.zzs(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new PaymentMethodTokenizationParameters(n2, bundle);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new PaymentMethodTokenizationParameters[n];
    }
}

