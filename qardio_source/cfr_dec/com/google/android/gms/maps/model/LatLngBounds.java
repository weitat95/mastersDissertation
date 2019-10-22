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
package com.google.android.gms.maps.model;

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
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.zze;
import java.util.Arrays;

public final class LatLngBounds
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<LatLngBounds> CREATOR = new zze();
    public final LatLng northeast;
    public final LatLng southwest;

    /*
     * Enabled aggressive block sorting
     */
    public LatLngBounds(LatLng latLng, LatLng latLng2) {
        zzbq.checkNotNull(latLng, "null southwest");
        zzbq.checkNotNull(latLng2, "null northeast");
        boolean bl = latLng2.latitude >= latLng.latitude;
        zzbq.zzb(bl, "southern latitude exceeds northern latitude (%s > %s)", latLng.latitude, latLng2.latitude);
        this.southwest = latLng;
        this.northeast = latLng2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static LatLngBounds createFromAttributes(Context object, AttributeSet object2) {
        Float f;
        Object object3;
        block3: {
            block2: {
                if (object == null || object2 == null) break block2;
                object3 = object.getResources().obtainAttributes((AttributeSet)object2, R.styleable.MapAttrs);
                object = object3.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude) ? Float.valueOf(object3.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLatitude, 0.0f)) : null;
                object2 = object3.hasValue(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude) ? Float.valueOf(object3.getFloat(R.styleable.MapAttrs_latLngBoundsSouthWestLongitude, 0.0f)) : null;
                f = object3.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude) ? Float.valueOf(object3.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLatitude, 0.0f)) : null;
                object3 = object3.hasValue(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude) ? Float.valueOf(object3.getFloat(R.styleable.MapAttrs_latLngBoundsNorthEastLongitude, 0.0f)) : null;
                if (object != null && object2 != null && f != null && object3 != null) break block3;
            }
            return null;
        }
        return new LatLngBounds(new LatLng(((Float)object).floatValue(), ((Float)object2).floatValue()), new LatLng(f.floatValue(), ((Float)object3).floatValue()));
    }

    private static double zza(double d, double d2) {
        return (d - d2 + 360.0) % 360.0;
    }

    private static double zzb(double d, double d2) {
        return (d2 - d + 360.0) % 360.0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof LatLngBounds)) {
                    return false;
                }
                object = (LatLngBounds)object;
                if (!this.southwest.equals(((LatLngBounds)object).southwest) || !this.northeast.equals(((LatLngBounds)object).northeast)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.southwest, this.northeast});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("southwest", this.southwest).zzg("northeast", this.northeast).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.southwest, n, false);
        zzbfp.zza(parcel, 3, this.northeast, n, false);
        zzbfp.zzai(parcel, n2);
    }

    public static final class Builder {
        private double zziva = Double.POSITIVE_INFINITY;
        private double zzivb = Double.NEGATIVE_INFINITY;
        private double zzivc = Double.NaN;
        private double zzivd = Double.NaN;

        /*
         * Enabled aggressive block sorting
         */
        public final LatLngBounds build() {
            boolean bl = !Double.isNaN(this.zzivc);
            zzbq.zza(bl, "no included points");
            return new LatLngBounds(new LatLng(this.zziva, this.zzivc), new LatLng(this.zzivb, this.zzivd));
        }

        /*
         * Enabled aggressive block sorting
         */
        public final Builder include(LatLng latLng) {
            boolean bl = true;
            this.zziva = Math.min(this.zziva, latLng.latitude);
            this.zzivb = Math.max(this.zzivb, latLng.latitude);
            double d = latLng.longitude;
            if (Double.isNaN(this.zzivc)) {
                this.zzivc = d;
            } else {
                boolean bl2;
                if (this.zzivc <= this.zzivd) {
                    bl2 = this.zzivc <= d && d <= this.zzivd ? bl : false;
                } else {
                    bl2 = bl;
                    if (!(this.zzivc <= d)) {
                        bl2 = bl;
                        if (!(d <= this.zzivd)) {
                            bl2 = false;
                        }
                    }
                }
                if (bl2) return this;
                {
                    if (LatLngBounds.zza(this.zzivc, d) < LatLngBounds.zzb(this.zzivd, d)) {
                        this.zzivc = d;
                        return this;
                    }
                }
            }
            this.zzivd = d;
            return this;
        }
    }

}

