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
import com.google.android.gms.wallet.OfferWalletObject;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public final class zzab
implements Parcelable.Creator<OfferWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        CommonWalletObject commonWalletObject = null;
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        int n2 = 0;
        String string3 = null;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block6;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 4: 
            }
            commonWalletObject = zzbfn.zza(parcel, n3, CommonWalletObject.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new OfferWalletObject(n2, string2, string3, commonWalletObject);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new OfferWalletObject[n];
    }
}

