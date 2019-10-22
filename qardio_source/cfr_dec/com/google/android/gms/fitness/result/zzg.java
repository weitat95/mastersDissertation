/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzg
implements Parcelable.Creator<ListSubscriptionsResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Status status = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        ArrayList<Subscription> arrayList = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block5;
                }
                case 1: {
                    arrayList = zzbfn.zzc(parcel, n3, Subscription.CREATOR);
                    continue block5;
                }
                case 2: {
                    status = zzbfn.zza(parcel, n3, Status.CREATOR);
                    continue block5;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new ListSubscriptionsResult(n2, arrayList, status);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ListSubscriptionsResult[n];
    }
}

