/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public final class zzf
implements Parcelable.Creator<WalletFragmentOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        int n3 = 1;
        WalletFragmentStyle walletFragmentStyle = null;
        int n4 = 1;
        block6 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    n2 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 4: {
                    walletFragmentStyle = zzbfn.zza(parcel, n5, WalletFragmentStyle.CREATOR);
                    continue block6;
                }
                case 5: 
            }
            n4 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new WalletFragmentOptions(n3, n2, walletFragmentStyle, n4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WalletFragmentOptions[n];
    }
}

