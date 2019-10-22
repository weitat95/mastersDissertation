/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.wobs.LabelValue;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import java.util.ArrayList;

public final class zze
implements Parcelable.Creator<LabelValueRow> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        ArrayList<LabelValue> arrayList = new ArrayList<LabelValue>();
        String string3 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 4: 
            }
            arrayList = zzbfn.zzc(parcel, n2, LabelValue.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LabelValueRow(string3, string2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LabelValueRow[n];
    }
}

