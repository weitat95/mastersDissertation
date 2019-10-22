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
import com.google.android.gms.wallet.ProxyCard;

public final class zzak
implements Parcelable.Creator<ProxyCard> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n4);
                    continue block6;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block6;
                }
                case 4: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 5: 
            }
            n = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new ProxyCard(string3, string2, n3, n);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ProxyCard[n];
    }
}

