/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcgy;
import com.google.android.gms.internal.zzcgz;
import java.util.Iterator;

public final class zzcgx
extends zzbfm
implements Iterable<String> {
    public static final Parcelable.Creator<zzcgx> CREATOR = new zzcgz();
    private final Bundle zzebe;

    zzcgx(Bundle bundle) {
        this.zzebe = bundle;
    }

    static /* synthetic */ Bundle zza(zzcgx zzcgx2) {
        return zzcgx2.zzebe;
    }

    final Object get(String string2) {
        return this.zzebe.get(string2);
    }

    final Double getDouble(String string2) {
        return this.zzebe.getDouble(string2);
    }

    final Long getLong(String string2) {
        return this.zzebe.getLong(string2);
    }

    final String getString(String string2) {
        return this.zzebe.getString(string2);
    }

    @Override
    public final Iterator<String> iterator() {
        return new zzcgy(this);
    }

    public final int size() {
        return this.zzebe.size();
    }

    public final String toString() {
        return this.zzebe.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzayx(), false);
        zzbfp.zzai(parcel, n);
    }

    public final Bundle zzayx() {
        return new Bundle(this.zzebe);
    }
}

