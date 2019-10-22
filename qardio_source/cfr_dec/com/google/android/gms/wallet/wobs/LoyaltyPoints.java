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
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.wobs.LoyaltyPointsBalance;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.zzi;

public final class LoyaltyPoints
extends zzbfm {
    public static final Parcelable.Creator<LoyaltyPoints> CREATOR = new zzi();
    String label;
    TimeInterval zzlbr;
    LoyaltyPointsBalance zzlfv;

    LoyaltyPoints() {
    }

    LoyaltyPoints(String string2, LoyaltyPointsBalance loyaltyPointsBalance, TimeInterval timeInterval) {
        this.label = string2;
        this.zzlfv = loyaltyPointsBalance;
        this.zzlbr = timeInterval;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.label, false);
        zzbfp.zza(parcel, 3, this.zzlfv, n, false);
        zzbfp.zza(parcel, 5, this.zzlbr, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

