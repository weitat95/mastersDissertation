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
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzab;
import com.google.android.gms.location.zzz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest
extends zzbfm {
    public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzab();
    private final List<LocationRequest> zzhhm;
    private final boolean zzika;
    private final boolean zzikb;
    private zzz zzikc;

    LocationSettingsRequest(List<LocationRequest> list, boolean bl, boolean bl2, zzz zzz2) {
        this.zzhhm = list;
        this.zzika = bl;
        this.zzikb = bl2;
        this.zzikc = zzz2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, Collections.unmodifiableList(this.zzhhm), false);
        zzbfp.zza(parcel, 2, this.zzika);
        zzbfp.zza(parcel, 3, this.zzikb);
        zzbfp.zza(parcel, 5, this.zzikc, n, false);
        zzbfp.zzai(parcel, n2);
    }

    public static final class Builder {
        private boolean zzika = false;
        private boolean zzikb = false;
        private zzz zzikc = null;
        private final ArrayList<LocationRequest> zzikd = new ArrayList();

        public final Builder addLocationRequest(LocationRequest locationRequest) {
            if (locationRequest != null) {
                this.zzikd.add(locationRequest);
            }
            return this;
        }

        public final LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzikd, this.zzika, this.zzikb, null);
        }

        public final Builder setAlwaysShow(boolean bl) {
            this.zzika = bl;
            return this;
        }

        public final Builder setNeedBle(boolean bl) {
            this.zzikb = bl;
            return this;
        }
    }

}

