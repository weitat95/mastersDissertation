/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.stats.WakeLockEvent;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzd
implements Parcelable.Creator<WakeLockEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        long l = 0L;
        int n3 = 0;
        String string2 = null;
        int n4 = 0;
        ArrayList<String> arrayList = null;
        String string3 = null;
        long l2 = 0L;
        int n5 = 0;
        String string4 = null;
        String string5 = null;
        float f = 0.0f;
        long l3 = 0L;
        String string6 = null;
        block16 : while (parcel.dataPosition() < n) {
            int n6 = parcel.readInt();
            switch (0xFFFF & n6) {
                default: {
                    zzbfn.zzb(parcel, n6);
                    continue block16;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n6);
                    continue block16;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n6);
                    continue block16;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n6);
                    continue block16;
                }
                case 5: {
                    n4 = zzbfn.zzg(parcel, n6);
                    continue block16;
                }
                case 6: {
                    arrayList = zzbfn.zzac(parcel, n6);
                    continue block16;
                }
                case 8: {
                    l2 = zzbfn.zzi(parcel, n6);
                    continue block16;
                }
                case 10: {
                    string4 = zzbfn.zzq(parcel, n6);
                    continue block16;
                }
                case 11: {
                    n3 = zzbfn.zzg(parcel, n6);
                    continue block16;
                }
                case 12: {
                    string3 = zzbfn.zzq(parcel, n6);
                    continue block16;
                }
                case 13: {
                    string5 = zzbfn.zzq(parcel, n6);
                    continue block16;
                }
                case 14: {
                    n5 = zzbfn.zzg(parcel, n6);
                    continue block16;
                }
                case 15: {
                    f = zzbfn.zzl(parcel, n6);
                    continue block16;
                }
                case 16: {
                    l3 = zzbfn.zzi(parcel, n6);
                    continue block16;
                }
                case 17: 
            }
            string6 = zzbfn.zzq(parcel, n6);
        }
        zzbfn.zzaf(parcel, n);
        return new WakeLockEvent(n2, l, n3, string2, n4, arrayList, string3, l2, n5, string4, string5, f, l3, string6);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WakeLockEvent[n];
    }
}

