/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import com.google.android.gms.internal.zzbfn;

public final class zza
implements Parcelable.Creator<CountrySpecification> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        block3 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block3;
                }
                case 2: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new CountrySpecification(string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new CountrySpecification[n];
    }
}

