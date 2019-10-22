/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;

public final class zzn
implements Parcelable.Creator<WalletObjectMessage> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        UriData uriData = null;
        int n = zzbfn.zzd(parcel);
        UriData uriData2 = null;
        TimeInterval timeInterval = null;
        String string2 = null;
        String string3 = null;
        block7 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block7;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block7;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block7;
                }
                case 4: {
                    timeInterval = zzbfn.zza(parcel, n2, TimeInterval.CREATOR);
                    continue block7;
                }
                case 5: {
                    uriData2 = zzbfn.zza(parcel, n2, UriData.CREATOR);
                    continue block7;
                }
                case 6: 
            }
            uriData = zzbfn.zza(parcel, n2, UriData.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new WalletObjectMessage(string3, string2, timeInterval, uriData2, uriData);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WalletObjectMessage[n];
    }
}

