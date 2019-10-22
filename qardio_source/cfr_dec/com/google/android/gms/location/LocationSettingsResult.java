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
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.zzac;

public final class LocationSettingsResult
extends zzbfm
implements Result {
    public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzac();
    private final Status mStatus;
    private final LocationSettingsStates zzike;

    public LocationSettingsResult(Status status) {
        this(status, null);
    }

    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.mStatus = status;
        this.zzike = locationSettingsStates;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzike;
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getStatus(), n, false);
        zzbfp.zza(parcel, 2, this.getLocationSettingsStates(), n, false);
        zzbfp.zzai(parcel, n2);
    }
}

