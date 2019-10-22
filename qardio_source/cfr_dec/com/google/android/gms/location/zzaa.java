/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.zzz;

public final class zzaa
implements Parcelable.Creator<zzz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = "";
        String string3 = "";
        String string4 = "";
        int n2 = 0;
        boolean bl = true;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 2: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 3: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block7;
                }
                case 4: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block7;
                }
                case 5: 
            }
            string2 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzz(string2, string3, string4, n2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzz[n];
    }
}

