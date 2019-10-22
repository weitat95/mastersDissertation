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
import com.google.android.gms.wallet.IsReadyToPayRequest;
import java.util.ArrayList;

public final class zzr
implements Parcelable.Creator<IsReadyToPayRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ArrayList<Integer> arrayList = null;
        int n = zzbfn.zzd(parcel);
        boolean bl = false;
        String string2 = null;
        String string3 = null;
        ArrayList<Integer> arrayList2 = null;
        block7 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block7;
                }
                case 2: {
                    arrayList2 = zzbfn.zzab(parcel, n2);
                    continue block7;
                }
                case 4: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block7;
                }
                case 5: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block7;
                }
                case 6: {
                    arrayList = zzbfn.zzab(parcel, n2);
                    continue block7;
                }
                case 7: 
            }
            bl = zzbfn.zzc(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new IsReadyToPayRequest(arrayList2, string3, string2, arrayList, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new IsReadyToPayRequest[n];
    }
}

