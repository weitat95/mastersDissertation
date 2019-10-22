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
import com.google.android.gms.wearable.internal.zzm;

public final class zzl
extends zzbfm {
    public static final Parcelable.Creator<zzl> CREATOR = new zzm();
    private final String mAppId;
    private int mId;
    private final String mPackageName;
    private final String zzegt;
    private final String zzemt;
    private final String zzepx;
    private final String zzlid;
    private final String zzlie;
    private final byte zzlif;
    private final byte zzlig;
    private final byte zzlih;
    private final byte zzlii;

    public zzl(int n, String string2, String string3, String string4, String string5, String string6, String string7, byte by, byte by2, byte by3, byte by4, String string8) {
        this.mId = n;
        this.mAppId = string2;
        this.zzlid = string3;
        this.zzepx = string4;
        this.zzemt = string5;
        this.zzlie = string6;
        this.zzegt = string7;
        this.zzlif = by;
        this.zzlig = by2;
        this.zzlih = by3;
        this.zzlii = by4;
        this.mPackageName = string8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block17: {
            block16: {
                if (this == object) break block16;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (zzl)object;
                if (this.mId != ((zzl)object).mId) {
                    return false;
                }
                if (this.zzlif != ((zzl)object).zzlif) {
                    return false;
                }
                if (this.zzlig != ((zzl)object).zzlig) {
                    return false;
                }
                if (this.zzlih != ((zzl)object).zzlih) {
                    return false;
                }
                if (this.zzlii != ((zzl)object).zzlii) {
                    return false;
                }
                if (!this.mAppId.equals(((zzl)object).mAppId)) {
                    return false;
                }
                if (this.zzlid != null ? !this.zzlid.equals(((zzl)object).zzlid) : ((zzl)object).zzlid != null) {
                    return false;
                }
                if (!this.zzepx.equals(((zzl)object).zzepx)) {
                    return false;
                }
                if (!this.zzemt.equals(((zzl)object).zzemt)) {
                    return false;
                }
                if (!this.zzlie.equals(((zzl)object).zzlie)) {
                    return false;
                }
                if (this.zzegt != null ? !this.zzegt.equals(((zzl)object).zzegt) : ((zzl)object).zzegt != null) {
                    return false;
                }
                if (this.mPackageName != null) {
                    return this.mPackageName.equals(((zzl)object).mPackageName);
                }
                if (((zzl)object).mPackageName != null) break block17;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int hashCode() {
        int n = 0;
        int n2 = this.mId;
        int n3 = this.mAppId.hashCode();
        int n4 = this.zzlid != null ? this.zzlid.hashCode() : 0;
        int n5 = this.zzepx.hashCode();
        int n6 = this.zzemt.hashCode();
        int n7 = this.zzlie.hashCode();
        int n8 = this.zzegt != null ? this.zzegt.hashCode() : 0;
        byte by = this.zzlif;
        byte by2 = this.zzlig;
        byte by3 = this.zzlih;
        byte by4 = this.zzlii;
        if (this.mPackageName != null) {
            n = this.mPackageName.hashCode();
        }
        return (((((n8 + ((((n4 + ((n2 + 31) * 31 + n3) * 31) * 31 + n5) * 31 + n6) * 31 + n7) * 31) * 31 + by) * 31 + by2) * 31 + by3) * 31 + by4) * 31 + n;
    }

    public final String toString() {
        int n = this.mId;
        String string2 = this.mAppId;
        String string3 = this.zzlid;
        String string4 = this.zzepx;
        String string5 = this.zzemt;
        String string6 = this.zzlie;
        String string7 = this.zzegt;
        byte by = this.zzlif;
        byte by2 = this.zzlig;
        byte by3 = this.zzlih;
        byte by4 = this.zzlii;
        String string8 = this.mPackageName;
        return new StringBuilder(String.valueOf(string2).length() + 211 + String.valueOf(string3).length() + String.valueOf(string4).length() + String.valueOf(string5).length() + String.valueOf(string6).length() + String.valueOf(string7).length() + String.valueOf(string8).length()).append("AncsNotificationParcelable{, id=").append(n).append(", appId='").append(string2).append("', dateTime='").append(string3).append("', notificationText='").append(string4).append("', title='").append(string5).append("', subtitle='").append(string6).append("', displayName='").append(string7).append("', eventId=").append(by).append(", eventFlags=").append(by2).append(", categoryId=").append(by3).append(", categoryCount=").append(by4).append(", packageName='").append(string8).append("'}").toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.mId);
        zzbfp.zza(parcel, 3, this.mAppId, false);
        zzbfp.zza(parcel, 4, this.zzlid, false);
        zzbfp.zza(parcel, 5, this.zzepx, false);
        zzbfp.zza(parcel, 6, this.zzemt, false);
        zzbfp.zza(parcel, 7, this.zzlie, false);
        String string2 = this.zzegt == null ? this.mAppId : this.zzegt;
        zzbfp.zza(parcel, 8, string2, false);
        zzbfp.zza(parcel, 9, this.zzlif);
        zzbfp.zza(parcel, 10, this.zzlig);
        zzbfp.zza(parcel, 11, this.zzlih);
        zzbfp.zza(parcel, 12, this.zzlii);
        zzbfp.zza(parcel, 13, this.mPackageName, false);
        zzbfp.zzai(parcel, n);
    }
}

