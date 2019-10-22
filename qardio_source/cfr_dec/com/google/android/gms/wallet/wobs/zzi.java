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
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.LoyaltyPointsBalance;
import com.google.android.gms.wallet.wobs.TimeInterval;

public final class zzi
implements Parcelable.Creator<LoyaltyPoints> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        TimeInterval timeInterval = null;
        LoyaltyPointsBalance loyaltyPointsBalance = null;
        String string2 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 3: {
                    loyaltyPointsBalance = zzbfn.zza(parcel, n2, LoyaltyPointsBalance.CREATOR);
                    continue block5;
                }
                case 5: 
            }
            timeInterval = zzbfn.zza(parcel, n2, TimeInterval.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LoyaltyPoints(string2, loyaltyPointsBalance, timeInterval);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LoyaltyPoints[n];
    }
}

