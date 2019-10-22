/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbv;
import com.google.android.gms.internal.zzbfn;

public final class zzbw
implements Parcelable.Creator<zzbv> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        Scope[] arrscope = null;
        int n3 = 0;
        int n4 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    n = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            arrscope = zzbfn.zzb(parcel, n5, Scope.CREATOR);
        }
        zzbfn.zzaf(parcel, n2);
        return new zzbv(n4, n3, n, arrscope);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbv[n];
    }
}

