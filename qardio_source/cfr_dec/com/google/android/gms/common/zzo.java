/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.zzn;
import com.google.android.gms.internal.zzbfn;

public final class zzo
implements Parcelable.Creator<zzn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int n = zzbfn.zzd(parcel);
        boolean bl = false;
        String string2 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 1: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 2: {
                    iBinder = zzbfn.zzr(parcel, n2);
                    continue block5;
                }
                case 3: 
            }
            bl = zzbfn.zzc(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzn(string2, iBinder, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzn[n];
    }
}

