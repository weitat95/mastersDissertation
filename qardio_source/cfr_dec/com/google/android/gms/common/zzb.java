/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzbfn;

public final class zzb
implements Parcelable.Creator<ConnectionResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        PendingIntent pendingIntent = null;
        int n2 = 0;
        int n3 = 0;
        String string2 = null;
        block6 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 3: {
                    pendingIntent = (PendingIntent)zzbfn.zza(parcel, n4, PendingIntent.CREATOR);
                    continue block6;
                }
                case 4: 
            }
            string2 = zzbfn.zzq(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new ConnectionResult(n3, n2, pendingIntent, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ConnectionResult[n];
    }
}

