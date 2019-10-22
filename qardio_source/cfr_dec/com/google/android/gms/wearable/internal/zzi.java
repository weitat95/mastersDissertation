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
import com.google.android.gms.wearable.internal.zzj;

public final class zzi
extends zzbfm {
    public static final Parcelable.Creator<zzi> CREATOR = new zzj();
    private final String mValue;
    private byte zzlib;
    private final byte zzlic;

    public zzi(byte by, byte by2, String string2) {
        this.zzlib = by;
        this.zzlic = by2;
        this.mValue = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (zzi)object;
                if (this.zzlib != ((zzi)object).zzlib) {
                    return false;
                }
                if (this.zzlic != ((zzi)object).zzlic) {
                    return false;
                }
                if (!this.mValue.equals(((zzi)object).mValue)) break block7;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zzlib + 31) * 31 + this.zzlic) * 31 + this.mValue.hashCode();
    }

    public final String toString() {
        byte by = this.zzlib;
        byte by2 = this.zzlic;
        String string2 = this.mValue;
        return new StringBuilder(String.valueOf(string2).length() + 73).append("AmsEntityUpdateParcelable{, mEntityId=").append(by).append(", mAttributeId=").append(by2).append(", mValue='").append(string2).append("'}").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlib);
        zzbfp.zza(parcel, 3, this.zzlic);
        zzbfp.zza(parcel, 4, this.mValue, false);
        zzbfp.zzai(parcel, n);
    }
}

