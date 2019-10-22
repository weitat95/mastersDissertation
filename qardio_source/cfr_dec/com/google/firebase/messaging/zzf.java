/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.firebase.messaging;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.firebase.messaging.RemoteMessage;

public final class zzf
implements Parcelable.Creator<RemoteMessage> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        Bundle bundle = null;
        block3 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block3;
                }
                case 2: 
            }
            bundle = zzbfn.zzs(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new RemoteMessage(bundle);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new RemoteMessage[n];
    }
}

