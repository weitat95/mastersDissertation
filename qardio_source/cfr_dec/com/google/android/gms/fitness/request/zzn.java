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
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzn
implements Parcelable.Creator<DataReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        ArrayList<DataType> arrayList = null;
        ArrayList<DataSource> arrayList2 = null;
        long l = 0L;
        long l2 = 0L;
        ArrayList<DataType> arrayList3 = null;
        ArrayList<DataSource> arrayList4 = null;
        int n3 = 0;
        long l3 = 0L;
        DataSource dataSource = null;
        int n4 = 0;
        boolean bl = false;
        boolean bl2 = false;
        IBinder iBinder = null;
        ArrayList<Device> arrayList5 = null;
        ArrayList<Integer> arrayList6 = null;
        block18 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block18;
                }
                case 1: {
                    arrayList = zzbfn.zzc(parcel, n5, DataType.CREATOR);
                    continue block18;
                }
                case 2: {
                    arrayList2 = zzbfn.zzc(parcel, n5, DataSource.CREATOR);
                    continue block18;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n5);
                    continue block18;
                }
                case 4: {
                    l2 = zzbfn.zzi(parcel, n5);
                    continue block18;
                }
                case 5: {
                    arrayList3 = zzbfn.zzc(parcel, n5, DataType.CREATOR);
                    continue block18;
                }
                case 6: {
                    arrayList4 = zzbfn.zzc(parcel, n5, DataSource.CREATOR);
                    continue block18;
                }
                case 7: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block18;
                }
                case 1000: {
                    n2 = zzbfn.zzg(parcel, n5);
                    continue block18;
                }
                case 8: {
                    l3 = zzbfn.zzi(parcel, n5);
                    continue block18;
                }
                case 9: {
                    dataSource = zzbfn.zza(parcel, n5, DataSource.CREATOR);
                    continue block18;
                }
                case 10: {
                    n4 = zzbfn.zzg(parcel, n5);
                    continue block18;
                }
                case 12: {
                    bl = zzbfn.zzc(parcel, n5);
                    continue block18;
                }
                case 13: {
                    bl2 = zzbfn.zzc(parcel, n5);
                    continue block18;
                }
                case 14: {
                    iBinder = zzbfn.zzr(parcel, n5);
                    continue block18;
                }
                case 16: {
                    arrayList5 = zzbfn.zzc(parcel, n5, Device.CREATOR);
                    continue block18;
                }
                case 17: 
            }
            arrayList6 = zzbfn.zzab(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new DataReadRequest(n2, arrayList, arrayList2, l, l2, arrayList3, arrayList4, n3, l3, dataSource, n4, bl, bl2, iBinder, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataReadRequest[n];
    }
}

