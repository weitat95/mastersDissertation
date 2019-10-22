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
import com.google.android.gms.maps.model.zza;
import java.util.Arrays;

public final class CameraPosition
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<CameraPosition> CREATOR = new zza();
    public final float bearing;
    public final LatLng target;
    public final float tilt;
    public final float zoom;

    /*
     * Enabled aggressive block sorting
     */
    public CameraPosition(LatLng latLng, float f, float f2, float f3) {
        zzbq.checkNotNull(latLng, "null camera target");
        boolean bl = 0.0f <= f2 && f2 <= 90.0f;
        zzbq.zzb(bl, "Tilt needs to be between 0 and 90 inclusive: %s", Float.valueOf(f2));
        this.target = latLng;
        this.zoom = f;
        this.tilt = f2 + 0.0f;
        f = f3;
        if ((double)f3 <= 0.0) {
            f = f3 % 360.0f + 360.0f;
        }
        this.bearing = f % 360.0f;
    }

    public static Builder builder() {
        return new Builder();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static CameraPosition createFromAttributes(Context context, AttributeSet object) {
        if (object == null) {
            return null;
        }
        float f = (context = context.getResources().obtainAttributes(object, R.styleable.MapAttrs)).hasValue(R.styleable.MapAttrs_cameraTargetLat) ? context.getFloat(R.styleable.MapAttrs_cameraTargetLat, 0.0f) : 0.0f;
        float f2 = context.hasValue(R.styleable.MapAttrs_cameraTargetLng) ? context.getFloat(R.styleable.MapAttrs_cameraTargetLng, 0.0f) : 0.0f;
        LatLng latLng = new LatLng(f, f2);
        Builder builder = CameraPosition.builder();
        builder.target(latLng);
        if (context.hasValue(R.styleable.MapAttrs_cameraZoom)) {
            builder.zoom(context.getFloat(R.styleable.MapAttrs_cameraZoom, 0.0f));
        }
        if (context.hasValue(R.styleable.MapAttrs_cameraBearing)) {
            builder.bearing(context.getFloat(R.styleable.MapAttrs_cameraBearing, 0.0f));
        }
        if (context.hasValue(R.styleable.MapAttrs_cameraTilt)) {
            builder.tilt(context.getFloat(R.styleable.MapAttrs_cameraTilt, 0.0f));
        }
        return builder.build();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof CameraPosition)) {
                    return false;
                }
                object = (CameraPosition)object;
                if (!this.target.equals(((CameraPosition)object).target) || Float.floatToIntBits(this.zoom) != Float.floatToIntBits(((CameraPosition)object).zoom) || Float.floatToIntBits(this.tilt) != Float.floatToIntBits(((CameraPosition)object).tilt) || Float.floatToIntBits(this.bearing) != Float.floatToIntBits(((CameraPosition)object).bearing)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing)});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("target", this.target).zzg("zoom", Float.valueOf(this.zoom)).zzg("tilt", Float.valueOf(this.tilt)).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.target, n, false);
        zzbfp.zza(parcel, 3, this.zoom);
        zzbfp.zza(parcel, 4, this.tilt);
        zzbfp.zza(parcel, 5, this.bearing);
        zzbfp.zzai(parcel, n2);
    }

    public static final class Builder {
        private LatLng zziue;
        private float zziuf;
        private float zziug;
        private float zziuh;

        public final Builder bearing(float f) {
            this.zziuh = f;
            return this;
        }

        public final CameraPosition build() {
            return new CameraPosition(this.zziue, this.zziuf, this.zziug, this.zziuh);
        }

        public final Builder target(LatLng latLng) {
            this.zziue = latLng;
            return this;
        }

        public final Builder tilt(float f) {
            this.zziug = f;
            return this;
        }

        public final Builder zoom(float f) {
            this.zziuf = f;
            return this;
        }
    }

}

