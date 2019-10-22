/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.internal.zzdd;

public final class zzde
implements Parcelable.Creator<zzdd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        byte[] arrby = null;
        Bundle bundle = null;
        Uri uri = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    uri = (Uri)zzbfn.zza(parcel, n2, Uri.CREATOR);
                    continue block5;
                }
                case 4: {
                    bundle = zzbfn.zzs(parcel, n2);
                    continue block5;
                }
                case 5: 
            }
            arrby = zzbfn.zzt(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzdd(uri, bundle, arrby);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdd[n];
    }
}

