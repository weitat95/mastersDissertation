/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzaqx;

final class zzaqy
implements Parcelable.Creator<zzaqx> {
    zzaqy() {
    }

    @Deprecated
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzaqx(parcel);
    }

    @Deprecated
    public final /* synthetic */ Object[] newArray(int n) {
        return new zzaqx[n];
    }
}

