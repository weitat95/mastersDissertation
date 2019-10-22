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
import com.google.android.gms.wearable.internal.zzfo;

public final class zzfp
implements Parcelable.Creator<zzfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        int n2 = 0;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block6;
                }
                case 5: 
            }
            bl = zzbfn.zzc(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzfo(string3, string2, n2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzfo[n];
    }
}

