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
import com.google.android.gms.internal.zzcdw;

public final class zzcdv
extends zzbfm {
    public static final Parcelable.Creator<zzcdv> CREATOR = new zzcdw();
    private String packageName;
    private int uid;

    public zzcdv(int n, String string2) {
        this.uid = n;
        this.packageName = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (object == null || !(object instanceof zzcdv)) {
                    return false;
                }
                object = (zzcdv)object;
                if (((zzcdv)object).uid != this.uid || !zzbg.equal(((zzcdv)object).packageName, this.packageName)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.uid;
    }

    public final String toString() {
        return String.format("%d:%s", this.uid, this.packageName);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.uid);
        zzbfp.zza(parcel, 2, this.packageName, false);
        zzbfp.zzai(parcel, n);
    }
}

