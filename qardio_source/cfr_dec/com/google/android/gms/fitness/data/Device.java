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
import com.google.android.gms.fitness.data.zzo;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcaj;
import java.util.Arrays;

public final class Device
extends zzbfm {
    public static final Parcelable.Creator<Device> CREATOR = new zzo();
    private final int type;
    private final String version;
    private final int versionCode;
    private final String zzhbt;
    private final String zzhbu;
    private final String zzhbv;
    private final int zzhbw;

    Device(int n, String string2, String string3, String string4, String string5, int n2, int n3) {
        this.versionCode = n;
        this.zzhbt = zzbq.checkNotNull(string2);
        this.zzhbu = zzbq.checkNotNull(string3);
        this.version = "";
        if (string5 != null) {
            this.zzhbv = string5;
            this.type = n2;
            this.zzhbw = n3;
            return;
        }
        throw new IllegalStateException("Device UID is null.");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof Device)) return bl2;
        object = (Device)object;
        boolean bl3 = zzbg.equal(this.zzhbt, ((Device)object).zzhbt) && zzbg.equal(this.zzhbu, ((Device)object).zzhbu) && zzbg.equal(this.version, ((Device)object).version) && zzbg.equal(this.zzhbv, ((Device)object).zzhbv) && this.type == ((Device)object).type && this.zzhbw == ((Device)object).zzhbw;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final String getManufacturer() {
        return this.zzhbt;
    }

    public final String getModel() {
        return this.zzhbu;
    }

    final String getStreamIdentifier() {
        return String.format("%s:%s:%s", this.zzhbt, this.zzhbu, this.zzhbv);
    }

    public final int getType() {
        return this.type;
    }

    public final String getUid() {
        return this.zzhbv;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbt, this.zzhbu, this.version, this.zzhbv, this.type});
    }

    public final String toString() {
        return String.format("Device{%s:%s:%s:%s}", this.getStreamIdentifier(), this.version, this.type, this.zzhbw);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getManufacturer(), false);
        zzbfp.zza(parcel, 2, this.getModel(), false);
        zzbfp.zza(parcel, 3, this.version, false);
        String string2 = this.zzhbw == 1 ? this.zzhbv : zzcaj.zzho(this.zzhbv);
        zzbfp.zza(parcel, 4, string2, false);
        zzbfp.zzc(parcel, 5, this.getType());
        zzbfp.zzc(parcel, 6, this.zzhbw);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n);
    }
}

