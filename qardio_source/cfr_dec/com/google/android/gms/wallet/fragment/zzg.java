/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public final class zzg
implements Parcelable.Creator<WalletFragmentStyle> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        Bundle bundle = null;
        int n2 = 0;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    bundle = zzbfn.zzs(parcel, n3);
                    continue block4;
                }
                case 3: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new WalletFragmentStyle(bundle, n2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WalletFragmentStyle[n];
    }
}

