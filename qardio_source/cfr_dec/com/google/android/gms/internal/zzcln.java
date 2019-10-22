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
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzclo;
import com.google.android.gms.internal.zzclp;

public final class zzcln
extends zzbfm {
    public static final Parcelable.Creator<zzcln> CREATOR = new zzclo();
    public final String name;
    private int versionCode;
    private String zzgcc;
    public final String zziyf;
    public final long zzjji;
    private Long zzjjj;
    private Float zzjjk;
    private Double zzjjl;

    /*
     * Enabled aggressive block sorting
     */
    zzcln(int n, String object, long l, Long l2, Float f, String string2, String string3, Double d) {
        Object var10_9 = null;
        this.versionCode = n;
        this.name = object;
        this.zzjji = l;
        this.zzjjj = l2;
        this.zzjjk = null;
        if (n == 1) {
            object = var10_9;
            if (f != null) {
                object = f.doubleValue();
            }
            this.zzjjl = object;
        } else {
            this.zzjjl = d;
        }
        this.zzgcc = string2;
        this.zziyf = string3;
    }

    zzcln(zzclp zzclp2) {
        this(zzclp2.mName, zzclp2.zzjjm, zzclp2.mValue, zzclp2.mOrigin);
    }

    zzcln(String string2, long l, Object object, String string3) {
        zzbq.zzgm(string2);
        this.versionCode = 2;
        this.name = string2;
        this.zzjji = l;
        this.zziyf = string3;
        if (object == null) {
            this.zzjjj = null;
            this.zzjjk = null;
            this.zzjjl = null;
            this.zzgcc = null;
            return;
        }
        if (object instanceof Long) {
            this.zzjjj = (Long)object;
            this.zzjjk = null;
            this.zzjjl = null;
            this.zzgcc = null;
            return;
        }
        if (object instanceof String) {
            this.zzjjj = null;
            this.zzjjk = null;
            this.zzjjl = null;
            this.zzgcc = (String)object;
            return;
        }
        if (object instanceof Double) {
            this.zzjjj = null;
            this.zzjjk = null;
            this.zzjjl = (Double)object;
            this.zzgcc = null;
            return;
        }
        throw new IllegalArgumentException("User attribute given of un-supported type");
    }

    public final Object getValue() {
        if (this.zzjjj != null) {
            return this.zzjjj;
        }
        if (this.zzjjl != null) {
            return this.zzjjl;
        }
        if (this.zzgcc != null) {
            return this.zzgcc;
        }
        return null;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.versionCode);
        zzbfp.zza(parcel, 2, this.name, false);
        zzbfp.zza(parcel, 3, this.zzjji);
        zzbfp.zza(parcel, 4, this.zzjjj, false);
        zzbfp.zza(parcel, 5, null, false);
        zzbfp.zza(parcel, 6, this.zzgcc, false);
        zzbfp.zza(parcel, 7, this.zziyf, false);
        zzbfp.zza(parcel, 8, this.zzjjl, false);
        zzbfp.zzai(parcel, n);
    }
}

