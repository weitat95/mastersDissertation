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
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.zzad;
import com.google.android.gms.fitness.data.zzb;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Session
extends zzbfm {
    public static final Parcelable.Creator<Session> CREATOR = new zzad();
    private final String mName;
    private final String zzdrs;
    private final long zzdvq;
    private final int zzeck;
    private final long zzgzz;
    private final int zzhaa;
    private final String zzhdu;
    private final zzb zzhdv;
    private final Long zzhdw;

    Session(int n, long l, long l2, String string2, String string3, String string4, int n2, zzb zzb2, Long l3) {
        this.zzeck = n;
        this.zzdvq = l;
        this.zzgzz = l2;
        this.mName = string2;
        this.zzhdu = string3;
        this.zzdrs = string4;
        this.zzhaa = n2;
        this.zzhdv = zzb2;
        this.zzhdw = l3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == this) return true;
        boolean bl2 = bl;
        if (!(object instanceof Session)) return bl2;
        object = (Session)object;
        boolean bl3 = this.zzdvq == ((Session)object).zzdvq && this.zzgzz == ((Session)object).zzgzz && zzbg.equal(this.mName, ((Session)object).mName) && zzbg.equal(this.zzhdu, ((Session)object).zzhdu) && zzbg.equal(this.zzdrs, ((Session)object).zzdrs) && zzbg.equal(this.zzhdv, ((Session)object).zzhdv) && this.zzhaa == ((Session)object).zzhaa;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public String getDescription() {
        return this.zzdrs;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public String getIdentifier() {
        return this.zzhdu;
    }

    public String getName() {
        return this.mName;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdvq, this.zzgzz, this.zzhdu});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTime", this.zzdvq).zzg("endTime", this.zzgzz).zzg("name", this.mName).zzg("identifier", this.zzhdu).zzg("description", this.zzdrs).zzg("activity", this.zzhaa).zzg("application", this.zzhdv).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, this.getName(), false);
        zzbfp.zza(parcel, 4, this.getIdentifier(), false);
        zzbfp.zza(parcel, 5, this.getDescription(), false);
        zzbfp.zzc(parcel, 7, this.zzhaa);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, this.zzhdv, n, false);
        zzbfp.zza(parcel, 9, this.zzhdw, false);
        zzbfp.zzai(parcel, n2);
    }
}

