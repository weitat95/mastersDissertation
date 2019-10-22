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
import com.google.android.gms.internal.zzcfo;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public final class zzcfp
implements Parcelable.Creator<zzcfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        List<zzcdv> list = zzcfo.zzikv;
        boolean bl2 = false;
        boolean bl3 = false;
        String string3 = null;
        LocationRequest locationRequest = null;
        block9 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block9;
                }
                case 1: {
                    locationRequest = zzbfn.zza(parcel, n2, LocationRequest.CREATOR);
                    continue block9;
                }
                case 5: {
                    list = zzbfn.zzc(parcel, n2, zzcdv.CREATOR);
                    continue block9;
                }
                case 6: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block9;
                }
                case 7: {
                    bl3 = zzbfn.zzc(parcel, n2);
                    continue block9;
                }
                case 8: {
                    bl2 = zzbfn.zzc(parcel, n2);
                    continue block9;
                }
                case 9: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block9;
                }
                case 10: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcfo(locationRequest, list, string3, bl3, bl2, bl, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcfo[n];
    }
}

