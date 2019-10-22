/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.widget.RemoteViews
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzdku;

public final class zzdkv
implements Parcelable.Creator<zzdku> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        byte[] arrby = null;
        RemoteViews remoteViews = null;
        int[] arrn = null;
        String[] arrstring = null;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 1: {
                    arrstring = zzbfn.zzaa(parcel, n2);
                    continue block6;
                }
                case 2: {
                    arrn = zzbfn.zzw(parcel, n2);
                    continue block6;
                }
                case 3: {
                    remoteViews = (RemoteViews)zzbfn.zza(parcel, n2, RemoteViews.CREATOR);
                    continue block6;
                }
                case 4: 
            }
            arrby = zzbfn.zzt(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzdku(arrstring, arrn, remoteViews, arrby);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdku[n];
    }
}

