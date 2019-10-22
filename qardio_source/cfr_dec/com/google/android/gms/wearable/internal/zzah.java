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
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.zzai;
import com.google.android.gms.wearable.internal.zzfo;
import java.util.List;
import java.util.Set;

public final class zzah
extends zzbfm
implements CapabilityInfo {
    public static final Parcelable.Creator<zzah> CREATOR = new zzai();
    private final Object mLock = new Object();
    private final String mName;
    private Set<Node> zzlio;
    private final List<zzfo> zzliu;

    public zzah(String string2, List<zzfo> list) {
        this.mName = string2;
        this.zzliu = list;
        this.zzlio = null;
        zzbq.checkNotNull(this.mName);
        zzbq.checkNotNull(this.zzliu);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (zzah)object;
        if (this.mName != null) {
            if (!this.mName.equals(((zzah)object).mName)) {
                return false;
            }
        } else if (((zzah)object).mName != null) return false;
        if (this.zzliu != null) {
            if (this.zzliu.equals(((zzah)object).zzliu)) return true;
            return false;
        }
        if (((zzah)object).zzliu == null) return true;
        return false;
    }

    public final String getName() {
        return this.mName;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int hashCode() {
        int n = 0;
        int n2 = this.mName != null ? this.mName.hashCode() : 0;
        if (this.zzliu != null) {
            n = this.zzliu.hashCode();
        }
        return (n2 + 31) * 31 + n;
    }

    public final String toString() {
        String string2 = this.mName;
        String string3 = String.valueOf(this.zzliu);
        return new StringBuilder(String.valueOf(string2).length() + 18 + String.valueOf(string3).length()).append("CapabilityInfo{").append(string2).append(", ").append(string3).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getName(), false);
        zzbfp.zzc(parcel, 3, this.zzliu, false);
        zzbfp.zzai(parcel, n);
    }
}

