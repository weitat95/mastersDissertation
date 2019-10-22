/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.zzf;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class Scope
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<Scope> CREATOR = new zzf();
    private int zzeck;
    private final String zzfnh;

    Scope(int n, String string2) {
        zzbq.zzh(string2, "scopeUri must not be null or empty");
        this.zzeck = n;
        this.zzfnh = string2;
    }

    public Scope(String string2) {
        this(1, string2);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scope)) {
            return false;
        }
        return this.zzfnh.equals(((Scope)object).zzfnh);
    }

    public final int hashCode() {
        return this.zzfnh.hashCode();
    }

    public final String toString() {
        return this.zzfnh;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, this.zzfnh, false);
        zzbfp.zzai(parcel, n);
    }
}

