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
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzj
implements Parcelable.Creator<DataDeleteRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = 0L;
        boolean bl = false;
        IBinder iBinder = null;
        int n = zzbfn.zzd(parcel);
        boolean bl2 = false;
        ArrayList<Session> arrayList = null;
        ArrayList<DataType> arrayList2 = null;
        ArrayList<DataSource> arrayList3 = null;
        long l2 = 0L;
        int n2 = 0;
        block11 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block11;
                }
                case 1: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block11;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block11;
                }
                case 3: {
                    arrayList3 = zzbfn.zzc(parcel, n3, DataSource.CREATOR);
                    continue block11;
                }
                case 4: {
                    arrayList2 = zzbfn.zzc(parcel, n3, DataType.CREATOR);
                    continue block11;
                }
                case 5: {
                    arrayList = zzbfn.zzc(parcel, n3, Session.CREATOR);
                    continue block11;
                }
                case 6: {
                    bl2 = zzbfn.zzc(parcel, n3);
                    continue block11;
                }
                case 7: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block11;
                }
                case 1000: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block11;
                }
                case 8: 
            }
            iBinder = zzbfn.zzr(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new DataDeleteRequest(n2, l2, l, arrayList3, arrayList2, arrayList, bl2, bl, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataDeleteRequest[n];
    }
}

