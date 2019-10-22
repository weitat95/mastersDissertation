/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzaf;
import java.util.Arrays;

public final class zzae
extends zzbfm {
    public static final Parcelable.Creator<zzae> CREATOR = new zzaf();
    private int zzikl;
    private int zzikm;
    private long zzikn;
    private long zziko;

    zzae(int n, int n2, long l, long l2) {
        this.zzikl = n;
        this.zzikm = n2;
        this.zzikn = l;
        this.zziko = l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (zzae)object;
                if (this.zzikl != ((zzae)object).zzikl || this.zzikm != ((zzae)object).zzikm || this.zzikn != ((zzae)object).zzikn || this.zziko != ((zzae)object).zziko) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzikm, this.zzikl, this.zziko, this.zzikn});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkLocationStatus:");
        stringBuilder.append(" Wifi status: ").append(this.zzikl).append(" Cell status: ").append(this.zzikm).append(" elapsed time NS: ").append(this.zziko).append(" system time ms: ").append(this.zzikn);
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzikl);
        zzbfp.zzc(parcel, 2, this.zzikm);
        zzbfp.zza(parcel, 3, this.zzikn);
        zzbfp.zza(parcel, 4, this.zziko);
        zzbfp.zzai(parcel, n);
    }
}

