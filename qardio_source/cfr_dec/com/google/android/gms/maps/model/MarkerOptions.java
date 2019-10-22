/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.zzh;

public final class MarkerOptions
extends zzbfm {
    public static final Parcelable.Creator<MarkerOptions> CREATOR = new zzh();
    private float mAlpha = 1.0f;
    private float mRotation = 0.0f;
    private String zzemt;
    private LatLng zzitp;
    private float zzium;
    private boolean zziun = true;
    private float zziuw = 0.5f;
    private float zziux = 1.0f;
    private String zzivg;
    private BitmapDescriptor zzivh;
    private boolean zzivi;
    private boolean zzivj = false;
    private float zzivk = 0.5f;
    private float zzivl = 0.0f;

    public MarkerOptions() {
    }

    /*
     * Enabled aggressive block sorting
     */
    MarkerOptions(LatLng latLng, String string2, String string3, IBinder iBinder, float f, float f2, boolean bl, boolean bl2, boolean bl3, float f3, float f4, float f5, float f6, float f7) {
        this.zzitp = latLng;
        this.zzemt = string2;
        this.zzivg = string3;
        this.zzivh = iBinder == null ? null : new BitmapDescriptor(IObjectWrapper.zza.zzaq(iBinder));
        this.zziuw = f;
        this.zziux = f2;
        this.zzivi = bl;
        this.zziun = bl2;
        this.zzivj = bl3;
        this.mRotation = f3;
        this.zzivk = f4;
        this.zzivl = f5;
        this.mAlpha = f6;
        this.zzium = f7;
    }

    public final float getAlpha() {
        return this.mAlpha;
    }

    public final float getAnchorU() {
        return this.zziuw;
    }

    public final float getAnchorV() {
        return this.zziux;
    }

    public final float getInfoWindowAnchorU() {
        return this.zzivk;
    }

    public final float getInfoWindowAnchorV() {
        return this.zzivl;
    }

    public final LatLng getPosition() {
        return this.zzitp;
    }

    public final float getRotation() {
        return this.mRotation;
    }

    public final String getSnippet() {
        return this.zzivg;
    }

    public final String getTitle() {
        return this.zzemt;
    }

    public final float getZIndex() {
        return this.zzium;
    }

    public final MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        this.zzivh = bitmapDescriptor;
        return this;
    }

    public final boolean isDraggable() {
        return this.zzivi;
    }

    public final boolean isFlat() {
        return this.zzivj;
    }

    public final boolean isVisible() {
        return this.zziun;
    }

    public final MarkerOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
        this.zzitp = latLng;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getPosition(), n, false);
        zzbfp.zza(parcel, 3, this.getTitle(), false);
        zzbfp.zza(parcel, 4, this.getSnippet(), false);
        IBinder iBinder = this.zzivh == null ? null : this.zzivh.zzavz().asBinder();
        zzbfp.zza(parcel, 5, iBinder, false);
        zzbfp.zza(parcel, 6, this.getAnchorU());
        zzbfp.zza(parcel, 7, this.getAnchorV());
        zzbfp.zza(parcel, 8, this.isDraggable());
        zzbfp.zza(parcel, 9, this.isVisible());
        zzbfp.zza(parcel, 10, this.isFlat());
        zzbfp.zza(parcel, 11, this.getRotation());
        zzbfp.zza(parcel, 12, this.getInfoWindowAnchorU());
        zzbfp.zza(parcel, 13, this.getInfoWindowAnchorV());
        zzbfp.zza(parcel, 14, this.getAlpha());
        zzbfp.zza(parcel, 15, this.getZIndex());
        zzbfp.zzai(parcel, n2);
    }
}

