/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.zzc;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class zzb
extends zzbfm {
    public static final Parcelable.Creator<zzb> CREATOR;
    public static final zzb zzgzu;
    private final String packageName;
    private final String version;
    private final int versionCode;
    private final String zzgzv;

    static {
        zzgzu = new zzb("com.google.android.gms", null, null);
        CREATOR = new zzc();
    }

    zzb(int n, String string2, String string3, String string4) {
        this.versionCode = n;
        this.packageName = zzbq.checkNotNull(string2);
        this.version = "";
        this.zzgzv = string4;
    }

    private zzb(String string2, String string3, String string4) {
        this(1, string2, "", null);
    }

    public static zzb zzhd(String string2) {
        if ("com.google.android.gms".equals(string2)) {
            return zzgzu;
        }
        return new zzb(string2, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof zzb)) return bl2;
        object = (zzb)object;
        boolean bl3 = this.packageName.equals(((zzb)object).packageName) && zzbg.equal(this.version, ((zzb)object).version) && zzbg.equal(this.zzgzv, ((zzb)object).zzgzv);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.packageName, this.version, this.zzgzv});
    }

    public final String toString() {
        return String.format("Application{%s:%s:%s}", this.packageName, this.version, this.zzgzv);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.packageName, false);
        zzbfp.zza(parcel, 2, this.version, false);
        zzbfp.zza(parcel, 3, this.zzgzv, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n);
    }
}

