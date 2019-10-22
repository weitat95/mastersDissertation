/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzbj;
import com.google.android.gms.internal.zzbfn;

public final class zzbk
implements Parcelable.Creator<zzbj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        boolean bl = false;
        Subscription subscription = null;
        int n2 = 0;
        IBinder iBinder = null;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 1: {
                    subscription = zzbfn.zza(parcel, n3, Subscription.CREATOR);
                    continue block6;
                }
                case 2: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block6;
                }
                case 3: {
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block6;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzbj(n2, subscription, bl, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbj[n];
    }
}

