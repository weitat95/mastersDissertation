/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.zza;

public final class zzb
implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        boolean bl = false;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        String string10 = null;
        String string11 = null;
        block13 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block13;
                }
                case 2: {
                    string11 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 3: {
                    string10 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 4: {
                    string9 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 5: {
                    string8 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 6: {
                    string7 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 7: {
                    string6 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 8: {
                    string5 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 9: {
                    string4 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 10: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 11: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block13;
                }
                case 12: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zza(string11, string10, string9, string8, string7, string6, string5, string4, string3, bl, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zza[n];
    }
}

