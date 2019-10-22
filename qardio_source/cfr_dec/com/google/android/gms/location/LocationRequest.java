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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzw;
import java.util.Arrays;

public final class LocationRequest
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationRequest> CREATOR = new zzw();
    private int mPriority;
    private boolean zzhhr;
    private int zziiv;
    private long zziiz;
    private long zzijq;
    private long zzijr;
    private float zzijs;
    private long zzijt;

    public LocationRequest() {
        this.mPriority = 102;
        this.zzijq = 3600000L;
        this.zzijr = 600000L;
        this.zzhhr = false;
        this.zziiz = Long.MAX_VALUE;
        this.zziiv = Integer.MAX_VALUE;
        this.zzijs = 0.0f;
        this.zzijt = 0L;
    }

    LocationRequest(int n, long l, long l2, boolean bl, long l3, int n2, float f, long l4) {
        this.mPriority = n;
        this.zzijq = l;
        this.zzijr = l2;
        this.zzhhr = bl;
        this.zziiz = l3;
        this.zziiv = n2;
        this.zzijs = f;
        this.zzijt = l4;
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void zzai(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException(new StringBuilder(38).append("invalid interval: ").append(l).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof LocationRequest)) {
                    return false;
                }
                object = (LocationRequest)object;
                if (this.mPriority != ((LocationRequest)object).mPriority || this.zzijq != ((LocationRequest)object).zzijq || this.zzijr != ((LocationRequest)object).zzijr || this.zzhhr != ((LocationRequest)object).zzhhr || this.zziiz != ((LocationRequest)object).zziiz || this.zziiv != ((LocationRequest)object).zziiv || this.zzijs != ((LocationRequest)object).zzijs || this.getMaxWaitTime() != ((LocationRequest)object).getMaxWaitTime()) break block5;
            }
            return true;
        }
        return false;
    }

    public final long getMaxWaitTime() {
        long l;
        long l2 = l = this.zzijt;
        if (l < this.zzijq) {
            l2 = this.zzijq;
        }
        return l2;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mPriority, this.zzijq, Float.valueOf(this.zzijs), this.zzijt});
    }

    public final LocationRequest setFastestInterval(long l) {
        LocationRequest.zzai(l);
        this.zzhhr = true;
        this.zzijr = l;
        return this;
    }

    public final LocationRequest setInterval(long l) {
        LocationRequest.zzai(l);
        this.zzijq = l;
        if (!this.zzhhr) {
            this.zzijr = (long)((double)this.zzijq / 6.0);
        }
        return this;
    }

    public final LocationRequest setPriority(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException(new StringBuilder(28).append("invalid quality: ").append(n).toString());
            }
            case 100: 
            case 102: 
            case 104: 
            case 105: 
        }
        this.mPriority = n;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = stringBuilder.append("Request[");
        switch (this.mPriority) {
            default: {
                string2 = "???";
                break;
            }
            case 100: {
                string2 = "PRIORITY_HIGH_ACCURACY";
                break;
            }
            case 102: {
                string2 = "PRIORITY_BALANCED_POWER_ACCURACY";
                break;
            }
            case 104: {
                string2 = "PRIORITY_LOW_POWER";
                break;
            }
            case 105: {
                string2 = "PRIORITY_NO_POWER";
            }
        }
        stringBuilder2.append(string2);
        if (this.mPriority != 105) {
            stringBuilder.append(" requested=");
            stringBuilder.append(this.zzijq).append("ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append(this.zzijr).append("ms");
        if (this.zzijt > this.zzijq) {
            stringBuilder.append(" maxWait=");
            stringBuilder.append(this.zzijt).append("ms");
        }
        if (this.zzijs > 0.0f) {
            stringBuilder.append(" smallestDisplacement=");
            stringBuilder.append(this.zzijs).append("m");
        }
        if (this.zziiz != Long.MAX_VALUE) {
            long l = this.zziiz;
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
        zzbfp.zzc(parcel, 1, this.mPriority);
        zzbfp.zza(parcel, 2, this.zzijq);
        zzbfp.zza(parcel, 3, this.zzijr);
        zzbfp.zza(parcel, 4, this.zzhhr);
        zzbfp.zza(parcel, 5, this.zziiz);
        zzbfp.zzc(parcel, 6, this.zziiv);
        zzbfp.zza(parcel, 7, this.zzijs);
        zzbfp.zza(parcel, 8, this.zzijt);
        zzbfp.zzai(parcel, n);
    }
}

