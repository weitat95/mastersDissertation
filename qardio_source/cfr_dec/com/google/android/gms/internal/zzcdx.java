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
import com.google.android.gms.internal.zzcdy;
import com.google.android.gms.location.zze;
import java.util.Collections;
import java.util.List;

public final class zzcdx
extends zzbfm {
    public static final Parcelable.Creator<zzcdx> CREATOR;
    static final List<zzcdv> zzikv;
    static final zze zzikw;
    private String mTag;
    private zze zzikx;
    private List<zzcdv> zziky;

    static {
        zzikv = Collections.emptyList();
        zzikw = new zze();
        CREATOR = new zzcdy();
    }

    zzcdx(zze zze2, List<zzcdv> list, String string2) {
        this.zzikx = zze2;
        this.zziky = list;
        this.mTag = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof zzcdx)) break block2;
                object = (zzcdx)object;
                if (zzbg.equal(this.zzikx, ((zzcdx)object).zzikx) && zzbg.equal(this.zziky, ((zzcdx)object).zziky) && zzbg.equal(this.mTag, ((zzcdx)object).mTag)) break block3;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.zzikx.hashCode();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzikx, n, false);
        zzbfp.zzc(parcel, 2, this.zziky, false);
        zzbfp.zza(parcel, 3, this.mTag, false);
        zzbfp.zzai(parcel, n2);
    }
}

