/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcdv;
import com.google.android.gms.internal.zzcfp;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public final class zzcfo
extends zzbfm {
    public static final Parcelable.Creator<zzcfo> CREATOR;
    static final List<zzcdv> zzikv;
    private String mTag;
    private String zzelc;
    private LocationRequest zzhhp;
    private List<zzcdv> zziky;
    private boolean zzilw;
    private boolean zzilx;
    private boolean zzily;
    private boolean zzilz = true;

    static {
        zzikv = Collections.emptyList();
        CREATOR = new zzcfp();
    }

    zzcfo(LocationRequest locationRequest, List<zzcdv> list, String string2, boolean bl, boolean bl2, boolean bl3, String string3) {
        this.zzhhp = locationRequest;
        this.zziky = list;
        this.mTag = string2;
        this.zzilw = bl;
        this.zzilx = bl2;
        this.zzily = bl3;
        this.zzelc = string3;
    }

    @Deprecated
    public static zzcfo zza(LocationRequest locationRequest) {
        return new zzcfo(locationRequest, zzikv, null, false, false, false, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof zzcfo)) break block2;
                object = (zzcfo)object;
                if (zzbg.equal(this.zzhhp, ((zzcfo)object).zzhhp) && zzbg.equal(this.zziky, ((zzcfo)object).zziky) && zzbg.equal(this.mTag, ((zzcfo)object).mTag) && this.zzilw == ((zzcfo)object).zzilw && this.zzilx == ((zzcfo)object).zzilx && this.zzily == ((zzcfo)object).zzily && zzbg.equal(this.zzelc, ((zzcfo)object).zzelc)) break block3;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.zzhhp.hashCode();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzhhp.toString());
        if (this.mTag != null) {
            stringBuilder.append(" tag=").append(this.mTag);
        }
        if (this.zzelc != null) {
            stringBuilder.append(" moduleId=").append(this.zzelc);
        }
        stringBuilder.append(" hideAppOps=").append(this.zzilw);
        stringBuilder.append(" clients=").append(this.zziky);
        stringBuilder.append(" forceCoarseLocation=").append(this.zzilx);
        if (this.zzily) {
            stringBuilder.append(" exemptFromBackgroundThrottle");
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhhp, n, false);
        zzbfp.zzc(parcel, 5, this.zziky, false);
        zzbfp.zza(parcel, 6, this.mTag, false);
        zzbfp.zza(parcel, 7, this.zzilw);
        zzbfp.zza(parcel, 8, this.zzilx);
        zzbfp.zza(parcel, 9, this.zzily);
        zzbfp.zza(parcel, 10, this.zzelc, false);
        zzbfp.zzai(parcel, n2);
    }
}

