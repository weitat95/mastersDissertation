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
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zze
implements Parcelable.Creator<Bucket> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = 0L;
        ArrayList<DataSet> arrayList = null;
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        int n3 = 0;
        Session session = null;
        long l2 = 0L;
        int n4 = 0;
        block10 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block10;
                }
                case 1: {
                    l2 = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 3: {
                    session = zzbfn.zza(parcel, n5, Session.CREATOR);
                    continue block10;
                }
                case 4: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block10;
                }
                case 5: {
                    arrayList = zzbfn.zzc(parcel, n5, DataSet.CREATOR);
                    continue block10;
                }
                case 6: {
                    n2 = zzbfn.zzg(parcel, n5);
                    continue block10;
                }
                case 7: {
                    bl = zzbfn.zzc(parcel, n5);
                    continue block10;
                }
                case 1000: 
            }
            n4 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new Bucket(n4, l2, l, session, n3, arrayList, n2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Bucket[n];
    }
}

