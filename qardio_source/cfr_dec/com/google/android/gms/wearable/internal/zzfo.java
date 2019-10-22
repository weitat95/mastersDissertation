/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.zzfp;

public final class zzfo
extends zzbfm
implements Node {
    public static final Parcelable.Creator<zzfo> CREATOR = new zzfp();
    private final String zzbuz;
    private final String zzegt;
    private final int zzlkz;
    private final boolean zzlla;

    public zzfo(String string2, String string3, int n, boolean bl) {
        this.zzbuz = string2;
        this.zzegt = string3;
        this.zzlkz = n;
        this.zzlla = bl;
    }

    public final boolean equals(Object object) {
        if (!(object instanceof zzfo)) {
            return false;
        }
        return ((zzfo)object).zzbuz.equals(this.zzbuz);
    }

    public final String getDisplayName() {
        return this.zzegt;
    }

    public final String getId() {
        return this.zzbuz;
    }

    public final int hashCode() {
        return this.zzbuz.hashCode();
    }

    public final boolean isNearby() {
        return this.zzlla;
    }

    public final String toString() {
        String string2 = this.zzegt;
        String string3 = this.zzbuz;
        int n = this.zzlkz;
        boolean bl = this.zzlla;
        return new StringBuilder(String.valueOf(string2).length() + 45 + String.valueOf(string3).length()).append("Node{").append(string2).append(", id=").append(string3).append(", hops=").append(n).append(", isNearby=").append(bl).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getId(), false);
        zzbfp.zza(parcel, 3, this.getDisplayName(), false);
        zzbfp.zzc(parcel, 4, this.zzlkz);
        zzbfp.zza(parcel, 5, this.isNearby());
        zzbfp.zzai(parcel, n);
    }
}

