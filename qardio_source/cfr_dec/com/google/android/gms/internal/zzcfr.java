/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcfo;
import com.google.android.gms.internal.zzcfq;

public final class zzcfr
implements Parcelable.Creator<zzcfq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 1;
        IBinder iBinder2 = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder3 = null;
        zzcfo zzcfo2 = null;
        block8 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block8;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block8;
                }
                case 2: {
                    zzcfo2 = zzbfn.zza(parcel, n3, zzcfo.CREATOR);
                    continue block8;
                }
                case 3: {
                    iBinder3 = zzbfn.zzr(parcel, n3);
                    continue block8;
                }
                case 4: {
                    pendingIntent = (PendingIntent)zzbfn.zza(parcel, n3, PendingIntent.CREATOR);
                    continue block8;
                }
                case 5: {
                    iBinder2 = zzbfn.zzr(parcel, n3);
                    continue block8;
                }
                case 6: 
            }
            iBinder = zzbfn.zzr(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcfq(n2, zzcfo2, iBinder3, pendingIntent, iBinder2, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcfq[n];
    }
}

