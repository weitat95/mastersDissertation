/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.iid.MessengerCompat;

final class zzk
implements Parcelable.Creator<MessengerCompat> {
    zzk() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        if ((parcel = parcel.readStrongBinder()) != null) {
            return new MessengerCompat((IBinder)parcel);
        }
        return null;
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new MessengerCompat[n];
    }
}

