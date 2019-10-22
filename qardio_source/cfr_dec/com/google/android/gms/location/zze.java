/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzf;
import java.util.Arrays;

public final class zze
extends zzbfm {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    private boolean zziir;
    private long zziis;
    private float zziit;
    private long zziiu;
    private int zziiv;

    public zze() {
        this(true, 50L, 0.0f, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    zze(boolean bl, long l, float f, long l2, int n) {
        this.zziir = bl;
        this.zziis = l;
        this.zziit = f;
        this.zziiu = l2;
        this.zziiv = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zze)) {
                    return false;
                }
                object = (zze)object;
                if (this.zziir != ((zze)object).zziir || this.zziis != ((zze)object).zziis || Float.compare(this.zziit, ((zze)object).zziit) != 0 || this.zziiu != ((zze)object).zziiu || this.zziiv != ((zze)object).zziiv) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zziir, this.zziis, Float.valueOf(this.zziit), this.zziiu, this.zziiv});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceOrientationRequest[mShouldUseMag=").append(this.zziir);
        stringBuilder.append(" mMinimumSamplingPeriodMs=").append(this.zziis);
        stringBuilder.append(" mSmallestAngleChangeRadians=").append(this.zziit);
        if (this.zziiu != Long.MAX_VALUE) {
            long l = this.zziiu;
            long l2 = SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(l - l2).append("ms");
        }
        if (this.zziiv != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.zziiv);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zziir);
        zzbfp.zza(parcel, 2, this.zziis);
        zzbfp.zza(parcel, 3, this.zziit);
        zzbfp.zza(parcel, 4, this.zziiu);
        zzbfp.zzc(parcel, 5, this.zziiv);
        zzbfp.zzai(parcel, n);
    }
}

