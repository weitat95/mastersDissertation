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
import com.google.android.gms.wallet.LineItem;

public final class zzt
implements Parcelable.Creator<LineItem> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        block8 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block8;
                }
                case 2: {
                    string6 = zzbfn.zzq(parcel, n3);
                    continue block8;
                }
                case 3: {
                    string5 = zzbfn.zzq(parcel, n3);
                    continue block8;
                }
                case 4: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block8;
                }
                case 5: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block8;
                }
                case 6: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block8;
                }
                case 7: 
            }
            string2 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new LineItem(string6, string5, string4, string3, n2, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LineItem[n];
    }
}

