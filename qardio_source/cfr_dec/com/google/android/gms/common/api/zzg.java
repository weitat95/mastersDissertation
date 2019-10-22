/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfn;

public final class zzg
implements Parcelable.Creator<Status> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int n = zzbfn.zzd(parcel);
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
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block6;
                }
                case 3: {
                    pendingIntent = (PendingIntent)zzbfn.zza(parcel, n4, PendingIntent.CREATOR);
                    continue block6;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new Status(n3, n2, string2, pendingIntent);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Status[n];
    }
}

