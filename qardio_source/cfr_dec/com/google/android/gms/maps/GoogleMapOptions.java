/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 */
package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.zzaa;

public final class GoogleMapOptions
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleMapOptions> CREATOR = new zzaa();
    private Boolean zzisb;
    private Boolean zzisc;
    private int zzisd = -1;
    private CameraPosition zzise;
    private Boolean zzisf;
    private Boolean zzisg;
    private Boolean zzish;
    private Boolean zzisi;
    private Boolean zzisj;
    private Boolean zzisk;
    private Boolean zzisl;
    private Boolean zzism;
    private Boolean zzisn;
    private Float zziso = null;
    private Float zzisp = null;
    private LatLngBounds zzisq = null;

    public GoogleMapOptions() {
    }

    GoogleMapOptions(byte by, byte by2, int n, CameraPosition cameraPosition, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, byte by9, byte by10, byte by11, Float f, Float f2, LatLngBounds latLngBounds) {
        this.zzisb = zza.zza(by);
        this.zzisc = zza.zza(by2);
        this.zzisd = n;
        this.zzise = cameraPosition;
        this.zzisf = zza.zza(by3);
        this.zzisg = zza.zza(by4);
        this.zzish = zza.zza(by5);
        this.zzisi = zza.zza(by6);
        this.zzisj = zza.zza(by7);
        this.zzisk = zza.zza(by8);
        this.zzisl = zza.zza(by9);
        this.zzism = zza.zza(by10);
        this.zzisn = zza.zza(by11);
        this.zziso = f;
        this.zzisp = f2;
        this.zzisq = latLngBounds;
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray typedArray = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (typedArray.hasValue(R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(typedArray.getInt(R.styleable.MapAttrs_mapType, -1));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(typedArray.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(typedArray.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiCompass, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(typedArray.getBoolean(R.styleable.MapAttrs_liteMode, false));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(typedArray.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.ambientEnabled(typedArray.getBoolean(R.styleable.MapAttrs_ambientEnabled, false));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.minZoomPreference(typedArray.getFloat(R.styleable.MapAttrs_cameraMinZoomPreference, Float.NEGATIVE_INFINITY));
        }
        if (typedArray.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.maxZoomPreference(typedArray.getFloat(R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
        }
        googleMapOptions.latLngBoundsForCameraTarget(LatLngBounds.createFromAttributes(context, attributeSet));
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        typedArray.recycle();
        return googleMapOptions;
    }

    public final GoogleMapOptions ambientEnabled(boolean bl) {
        this.zzisn = bl;
        return this;
    }

    public final GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.zzise = cameraPosition;
        return this;
    }

    public final GoogleMapOptions compassEnabled(boolean bl) {
        this.zzisg = bl;
        return this;
    }

    public final CameraPosition getCamera() {
        return this.zzise;
    }

    public final LatLngBounds getLatLngBoundsForCameraTarget() {
        return this.zzisq;
    }

    public final int getMapType() {
        return this.zzisd;
    }

    public final Float getMaxZoomPreference() {
        return this.zzisp;
    }

    public final Float getMinZoomPreference() {
        return this.zziso;
    }

    public final GoogleMapOptions latLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        this.zzisq = latLngBounds;
        return this;
    }

    public final GoogleMapOptions liteMode(boolean bl) {
        this.zzisl = bl;
        return this;
    }

    public final GoogleMapOptions mapToolbarEnabled(boolean bl) {
        this.zzism = bl;
        return this;
    }

    public final GoogleMapOptions mapType(int n) {
        this.zzisd = n;
        return this;
    }

    public final GoogleMapOptions maxZoomPreference(float f) {
        this.zzisp = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions minZoomPreference(float f) {
        this.zziso = Float.valueOf(f);
        return this;
    }

    public final GoogleMapOptions rotateGesturesEnabled(boolean bl) {
        this.zzisk = bl;
        return this;
    }

    public final GoogleMapOptions scrollGesturesEnabled(boolean bl) {
        this.zzish = bl;
        return this;
    }

    public final GoogleMapOptions tiltGesturesEnabled(boolean bl) {
        this.zzisj = bl;
        return this;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("MapType", this.zzisd).zzg("LiteMode", this.zzisl).zzg("Camera", this.zzise).zzg("CompassEnabled", this.zzisg).zzg("ZoomControlsEnabled", this.zzisf).zzg("ScrollGesturesEnabled", this.zzish).zzg("ZoomGesturesEnabled", this.zzisi).zzg("TiltGesturesEnabled", this.zzisj).zzg("RotateGesturesEnabled", this.zzisk).zzg("MapToolbarEnabled", this.zzism).zzg("AmbientEnabled", this.zzisn).zzg("MinZoomPreference", this.zziso).zzg("MaxZoomPreference", this.zzisp).zzg("LatLngBoundsForCameraTarget", this.zzisq).zzg("ZOrderOnTop", this.zzisb).zzg("UseViewLifecycleInFragment", this.zzisc).toString();
    }

    public final GoogleMapOptions useViewLifecycleInFragment(boolean bl) {
        this.zzisc = bl;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, zza.zzb(this.zzisb));
        zzbfp.zza(parcel, 3, zza.zzb(this.zzisc));
        zzbfp.zzc(parcel, 4, this.getMapType());
        zzbfp.zza(parcel, 5, this.getCamera(), n, false);
        zzbfp.zza(parcel, 6, zza.zzb(this.zzisf));
        zzbfp.zza(parcel, 7, zza.zzb(this.zzisg));
        zzbfp.zza(parcel, 8, zza.zzb(this.zzish));
        zzbfp.zza(parcel, 9, zza.zzb(this.zzisi));
        zzbfp.zza(parcel, 10, zza.zzb(this.zzisj));
        zzbfp.zza(parcel, 11, zza.zzb(this.zzisk));
        zzbfp.zza(parcel, 12, zza.zzb(this.zzisl));
        zzbfp.zza(parcel, 14, zza.zzb(this.zzism));
        zzbfp.zza(parcel, 15, zza.zzb(this.zzisn));
        zzbfp.zza(parcel, 16, this.getMinZoomPreference(), false);
        zzbfp.zza(parcel, 17, this.getMaxZoomPreference(), false);
        zzbfp.zza(parcel, 18, this.getLatLngBoundsForCameraTarget(), n, false);
        zzbfp.zzai(parcel, n2);
    }

    public final GoogleMapOptions zOrderOnTop(boolean bl) {
        this.zzisb = bl;
        return this;
    }

    public final GoogleMapOptions zoomControlsEnabled(boolean bl) {
        this.zzisf = bl;
        return this;
    }

    public final GoogleMapOptions zoomGesturesEnabled(boolean bl) {
        this.zzisi = bl;
        return this;
    }
}

