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
import com.google.android.gms.wearable.internal.zzfe;

public final class zzff
implements Parcelable.Creator<zzfe> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        String string3 = null;
        int n2 = 0;
        byte[] arrby = null;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block6;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block6;
                }
                case 4: {
                    arrby = zzbfn.zzt(parcel, n3);
                    continue block6;
                }
                case 5: 
            }
            string2 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzfe(n2, string3, arrby, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzfe[n];
    }
}

