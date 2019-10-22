/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.internal.zzd;

public final class zze
implements Parcelable.Creator<zzd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        IntentFilter[] arrintentFilter = null;
        IBinder iBinder = null;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 2: {
                    iBinder = zzbfn.zzr(parcel, n2);
                    continue block6;
                }
                case 3: {
                    arrintentFilter = (IntentFilter[])zzbfn.zzb(parcel, n2, IntentFilter.CREATOR);
                    continue block6;
                }
                case 4: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block6;
                }
                case 5: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzd(iBinder, arrintentFilter, string3, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzd[n];
    }
}

