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
import com.google.android.gms.wallet.wobs.LoyaltyPointsBalance;

public final class zzh
implements Parcelable.Creator<LoyaltyPointsBalance> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        double d = 0.0;
        long l = 0L;
        int n3 = -1;
        String string3 = null;
        block8 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block8;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block8;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n4);
                    continue block8;
                }
                case 4: {
                    d = zzbfn.zzn(parcel, n4);
                    continue block8;
                }
                case 5: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block8;
                }
                case 6: {
                    l = zzbfn.zzi(parcel, n4);
                    continue block8;
                }
                case 7: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new LoyaltyPointsBalance(n2, string3, d, string2, l, n3);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LoyaltyPointsBalance[n];
    }
}

