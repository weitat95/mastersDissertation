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
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcln;

public final class zzcgm
implements Parcelable.Creator<zzcgl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string2 = null;
        String string3 = null;
        zzcln zzcln2 = null;
        long l = 0L;
        boolean bl = false;
        String string4 = null;
        zzcha zzcha2 = null;
        long l2 = 0L;
        zzcha zzcha3 = null;
        long l3 = 0L;
        zzcha zzcha4 = null;
        block14 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block14;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block14;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 4: {
                    zzcln2 = zzbfn.zza(parcel, n3, zzcln.CREATOR);
                    continue block14;
                }
                case 5: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block14;
                }
                case 6: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block14;
                }
                case 7: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 8: {
                    zzcha2 = zzbfn.zza(parcel, n3, zzcha.CREATOR);
                    continue block14;
                }
                case 9: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block14;
                }
                case 10: {
                    zzcha3 = zzbfn.zza(parcel, n3, zzcha.CREATOR);
                    continue block14;
                }
                case 11: {
                    l3 = zzbfn.zzi(parcel, n3);
                    continue block14;
                }
                case 12: 
            }
            zzcha4 = zzbfn.zza(parcel, n3, zzcha.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcgl(n2, string2, string3, zzcln2, l, bl, string4, zzcha2, l2, zzcha3, l3, zzcha4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcgl[n];
    }
}

