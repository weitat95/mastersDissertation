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
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzcha;

public final class zzchb
implements Parcelable.Creator<zzcha> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        long l = 0L;
        zzcgx zzcgx2 = null;
        String string3 = null;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block6;
                }
                case 3: {
                    zzcgx2 = zzbfn.zza(parcel, n2, zzcgx.CREATOR);
                    continue block6;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block6;
                }
                case 5: 
            }
            l = zzbfn.zzi(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcha(string3, zzcgx2, string2, l);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcha[n];
    }
}

