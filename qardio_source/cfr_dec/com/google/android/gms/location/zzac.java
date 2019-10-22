/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;

public final class zzac
implements Parcelable.Creator<LocationSettingsResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        LocationSettingsStates locationSettingsStates = null;
        Status status = null;
        block4 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block4;
                }
                case 1: {
                    status = zzbfn.zza(parcel, n2, Status.CREATOR);
                    continue block4;
                }
                case 2: 
            }
            locationSettingsStates = zzbfn.zza(parcel, n2, LocationSettingsStates.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LocationSettingsResult(status, locationSettingsStates);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationSettingsResult[n];
    }
}

