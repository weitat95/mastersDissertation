/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.zzai;

public final class StreetViewPanoramaOptions
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<StreetViewPanoramaOptions> CREATOR = new zzai();
    private Boolean zzisc;
    private Boolean zzisi = true;
    private StreetViewPanoramaCamera zzitn;
    private String zzito;
    private LatLng zzitp;
    private Integer zzitq;
    private Boolean zzitr = true;
    private Boolean zzits = true;
    private Boolean zzitt = true;

    public StreetViewPanoramaOptions() {
    }

    StreetViewPanoramaOptions(StreetViewPanoramaCamera streetViewPanoramaCamera, String string2, LatLng latLng, Integer n, byte by, byte by2, byte by3, byte by4, byte by5) {
        this.zzitn = streetViewPanoramaCamera;
        this.zzitp = latLng;
        this.zzitq = n;
        this.zzito = string2;
        this.zzitr = zza.zza(by);
        this.zzisi = zza.zza(by2);
        this.zzits = zza.zza(by3);
        this.zzitt = zza.zza(by4);
        this.zzisc = zza.zza(by5);
    }

    public final String getPanoramaId() {
        return this.zzito;
    }

    public final LatLng getPosition() {
        return this.zzitp;
    }

    public final Integer getRadius() {
        return this.zzitq;
    }

    public final StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.zzitn;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("PanoramaId", this.zzito).zzg("Position", this.zzitp).zzg("Radius", this.zzitq).zzg("StreetViewPanoramaCamera", this.zzitn).zzg("UserNavigationEnabled", this.zzitr).zzg("ZoomGesturesEnabled", this.zzisi).zzg("PanningGesturesEnabled", this.zzits).zzg("StreetNamesEnabled", this.zzitt).zzg("UseViewLifecycleInFragment", this.zzisc).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getStreetViewPanoramaCamera(), n, false);
        zzbfp.zza(parcel, 3, this.getPanoramaId(), false);
        zzbfp.zza(parcel, 4, this.getPosition(), n, false);
        zzbfp.zza(parcel, 5, this.getRadius(), false);
        zzbfp.zza(parcel, 6, zza.zzb(this.zzitr));
        zzbfp.zza(parcel, 7, zza.zzb(this.zzisi));
        zzbfp.zza(parcel, 8, zza.zzb(this.zzits));
        zzbfp.zza(parcel, 9, zza.zzb(this.zzitt));
        zzbfp.zza(parcel, 10, zza.zzb(this.zzisc));
        zzbfp.zzai(parcel, n2);
    }
}

