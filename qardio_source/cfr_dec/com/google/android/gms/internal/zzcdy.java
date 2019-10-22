/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcdv;
import com.google.android.gms.internal.zzcdx;
import com.google.android.gms.location.zze;
import java.util.List;

public final class zzcdy
implements Parcelable.Creator<zzcdx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        zze zze2 = zzcdx.zzikw;
        List<zzcdv> list = zzcdx.zzikv;
        String string2 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 1: {
                    zze2 = zzbfn.zza(parcel, n2, zze.CREATOR);
                    continue block5;
                }
                case 2: {
                    list = zzbfn.zzc(parcel, n2, zzcdv.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcdx(zze2, list, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcdx[n];
    }
}

