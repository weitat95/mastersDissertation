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
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.CardInfo;

public final class zzc
implements Parcelable.Creator<CardInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        UserAddress userAddress = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block7;
                }
                case 5: 
            }
            userAddress = zzbfn.zza(parcel, n3, UserAddress.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new CardInfo(string4, string3, string2, n2, userAddress);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new CardInfo[n];
    }
}

