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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.zzw;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public class MapValue
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<MapValue> CREATOR = new zzw();
    private final int zzeck;
    private final int zzhdi;
    private final float zzhdj;

    MapValue(int n, int n2, float f) {
        this.zzeck = n;
        this.zzhdi = n2;
        this.zzhdj = f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float asFloat() {
        boolean bl = this.zzhdi == 2;
        zzbq.zza(bl, "Value is not in float format");
        return this.zzhdj;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        block4: {
            block3: {
                bl = false;
                if (this == object) return true;
                bl3 = bl;
                if (!(object instanceof MapValue)) return bl3;
                object = (MapValue)object;
                if (this.zzhdi != ((MapValue)object).zzhdi) break block3;
                switch (this.zzhdi) {
                    default: {
                        if (this.zzhdj != ((MapValue)object).zzhdj) break;
                        bl2 = true;
                        break block4;
                    }
                    case 2: {
                        bl2 = this.asFloat() == ((MapValue)object).asFloat();
                        break block4;
                    }
                }
                bl2 = false;
                break block4;
            }
            bl2 = false;
        }
        bl3 = bl;
        if (!bl2) return bl3;
        return true;
    }

    public int hashCode() {
        return (int)this.zzhdj;
    }

    public String toString() {
        switch (this.zzhdi) {
            default: {
                return "unknown";
            }
            case 2: 
        }
        return Float.toString(this.asFloat());
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzhdi);
        zzbfp.zza(parcel, 2, this.zzhdj);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n);
    }
}

