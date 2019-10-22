/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.internal.zzbfn;

public final class zzbu
implements Parcelable.Creator<zzbt> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ConnectionResult connectionResult = null;
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        boolean bl2 = false;
        IBinder iBinder = null;
        int n2 = 0;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block7;
                }
                case 2: {
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block7;
                }
                case 3: {
                    connectionResult = zzbfn.zza(parcel, n3, ConnectionResult.CREATOR);
                    continue block7;
                }
                case 4: {
                    bl2 = zzbfn.zzc(parcel, n3);
                    continue block7;
                }
                case 5: 
            }
            bl = zzbfn.zzc(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzbt(n2, iBinder, connectionResult, bl2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbt[n];
    }
}

