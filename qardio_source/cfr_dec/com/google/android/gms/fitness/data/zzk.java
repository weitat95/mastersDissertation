/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.data.zzb;
import com.google.android.gms.internal.zzbfn;

public final class zzk
implements Parcelable.Creator<DataSource> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int[] arrn = null;
        int n2 = zzbfn.zzd(parcel);
        String string2 = null;
        zzb zzb2 = null;
        Device device = null;
        String string3 = null;
        DataType dataType = null;
        int n3 = 0;
        block10 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block10;
                }
                case 1: {
                    dataType = zzbfn.zza(parcel, n4, DataType.CREATOR);
                    continue block10;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n4);
                    continue block10;
                }
                case 3: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block10;
                }
                case 4: {
                    device = zzbfn.zza(parcel, n4, Device.CREATOR);
                    continue block10;
                }
                case 5: {
                    zzb2 = zzbfn.zza(parcel, n4, zzb.CREATOR);
                    continue block10;
                }
                case 6: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block10;
                }
                case 1000: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block10;
                }
                case 8: 
            }
            arrn = zzbfn.zzw(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new DataSource(n3, dataType, string3, n, device, zzb2, string2, arrn);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataSource[n];
    }
}

