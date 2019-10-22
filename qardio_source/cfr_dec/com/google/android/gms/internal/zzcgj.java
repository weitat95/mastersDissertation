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
import com.google.android.gms.internal.zzcgi;

public final class zzcgj
implements Parcelable.Creator<zzcgi> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        long l = 0L;
        long l2 = 0L;
        String string6 = null;
        boolean bl = true;
        boolean bl2 = false;
        long l3 = Integer.MIN_VALUE;
        String string7 = null;
        long l4 = 0L;
        long l5 = 0L;
        int n2 = 0;
        boolean bl3 = true;
        block17 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block17;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 4: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 5: {
                    string5 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 6: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block17;
                }
                case 7: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block17;
                }
                case 8: {
                    string6 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 9: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block17;
                }
                case 10: {
                    bl2 = zzbfn.zzc(parcel, n3);
                    continue block17;
                }
                case 11: {
                    l3 = zzbfn.zzi(parcel, n3);
                    continue block17;
                }
                case 12: {
                    string7 = zzbfn.zzq(parcel, n3);
                    continue block17;
                }
                case 13: {
                    l4 = zzbfn.zzi(parcel, n3);
                    continue block17;
                }
                case 14: {
                    l5 = zzbfn.zzi(parcel, n3);
                    continue block17;
                }
                case 15: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block17;
                }
                case 16: 
            }
            bl3 = zzbfn.zzc(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcgi(string2, string3, string4, string5, l, l2, string6, bl, bl2, l3, string7, l4, l5, n2, bl3);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcgi[n];
    }
}

