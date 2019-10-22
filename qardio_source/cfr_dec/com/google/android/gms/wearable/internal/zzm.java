/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.internal.zzl;

public final class zzm
implements Parcelable.Creator<zzl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        byte by = 0;
        byte by2 = 0;
        byte by3 = 0;
        byte by4 = 0;
        String string8 = null;
        block14 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block14;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block14;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 4: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 5: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 6: {
                    string5 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 7: {
                    string6 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 8: {
                    string7 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 9: {
                    by = zzbfn.zze(parcel, n3);
                    continue block14;
                }
                case 10: {
                    by2 = zzbfn.zze(parcel, n3);
                    continue block14;
                }
                case 11: {
                    by3 = zzbfn.zze(parcel, n3);
                    continue block14;
                }
                case 12: {
                    by4 = zzbfn.zze(parcel, n3);
                    continue block14;
                }
                case 13: 
            }
            string8 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzl(n2, string2, string3, string4, string5, string6, string7, by, by2, by3, by4, string8);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzl[n];
    }
}

