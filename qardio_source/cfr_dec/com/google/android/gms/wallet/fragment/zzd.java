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
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;

public final class zzd
implements Parcelable.Creator<WalletFragmentInitParams> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = -1;
        MaskedWalletRequest maskedWalletRequest = null;
        String string2 = null;
        MaskedWallet maskedWallet = null;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 3: {
                    maskedWalletRequest = zzbfn.zza(parcel, n3, MaskedWalletRequest.CREATOR);
                    continue block6;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block6;
                }
                case 5: 
            }
            maskedWallet = zzbfn.zza(parcel, n3, MaskedWallet.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new WalletFragmentInitParams(string2, maskedWalletRequest, n2, maskedWallet);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WalletFragmentInitParams[n];
    }
}

