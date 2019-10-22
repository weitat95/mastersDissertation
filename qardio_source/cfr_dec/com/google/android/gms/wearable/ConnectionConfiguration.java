/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.zzg;
import java.util.Arrays;

public class ConnectionConfiguration
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<ConnectionConfiguration> CREATOR = new zzg();
    private final String mName;
    private volatile boolean zzdyh;
    private final int zzeie;
    private final int zzgkq;
    private final String zzgzw;
    private final boolean zzlgo;
    private volatile String zzlgp;
    private boolean zzlgq;
    private String zzlgr;

    ConnectionConfiguration(String string2, String string3, int n, int n2, boolean bl, boolean bl2, String string4, boolean bl3, String string5) {
        this.mName = string2;
        this.zzgzw = string3;
        this.zzeie = n;
        this.zzgkq = n2;
        this.zzlgo = bl;
        this.zzdyh = bl2;
        this.zzlgp = string4;
        this.zzlgq = bl3;
        this.zzlgr = string5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof ConnectionConfiguration)) break block2;
                object = (ConnectionConfiguration)object;
                if (zzbg.equal(this.mName, ((ConnectionConfiguration)object).mName) && zzbg.equal(this.zzgzw, ((ConnectionConfiguration)object).zzgzw) && zzbg.equal(this.zzeie, ((ConnectionConfiguration)object).zzeie) && zzbg.equal(this.zzgkq, ((ConnectionConfiguration)object).zzgkq) && zzbg.equal(this.zzlgo, ((ConnectionConfiguration)object).zzlgo) && zzbg.equal(this.zzlgq, ((ConnectionConfiguration)object).zzlgq)) break block3;
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zzgzw, this.zzeie, this.zzgkq, this.zzlgo, this.zzlgq});
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ConnectionConfiguration[ ");
        String string2 = String.valueOf(this.mName);
        string2 = string2.length() != 0 ? "mName=".concat(string2) : new String("mName=");
        stringBuilder.append(string2);
        string2 = String.valueOf(this.zzgzw);
        string2 = string2.length() != 0 ? ", mAddress=".concat(string2) : new String(", mAddress=");
        stringBuilder.append(string2);
        int n = this.zzeie;
        stringBuilder.append(new StringBuilder(19).append(", mType=").append(n).toString());
        n = this.zzgkq;
        stringBuilder.append(new StringBuilder(19).append(", mRole=").append(n).toString());
        boolean bl = this.zzlgo;
        stringBuilder.append(new StringBuilder(16).append(", mEnabled=").append(bl).toString());
        bl = this.zzdyh;
        stringBuilder.append(new StringBuilder(20).append(", mIsConnected=").append(bl).toString());
        string2 = String.valueOf(this.zzlgp);
        string2 = string2.length() != 0 ? ", mPeerNodeId=".concat(string2) : new String(", mPeerNodeId=");
        stringBuilder.append(string2);
        bl = this.zzlgq;
        stringBuilder.append(new StringBuilder(21).append(", mBtlePriority=").append(bl).toString());
        string2 = String.valueOf(this.zzlgr);
        string2 = string2.length() != 0 ? ", mNodeId=".concat(string2) : new String(", mNodeId=");
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.mName, false);
        zzbfp.zza(parcel, 3, this.zzgzw, false);
        zzbfp.zzc(parcel, 4, this.zzeie);
        zzbfp.zzc(parcel, 5, this.zzgkq);
        zzbfp.zza(parcel, 6, this.zzlgo);
        zzbfp.zza(parcel, 7, this.zzdyh);
        zzbfp.zza(parcel, 8, this.zzlgp, false);
        zzbfp.zza(parcel, 9, this.zzlgq);
        zzbfp.zza(parcel, 10, this.zzlgr, false);
        zzbfp.zzai(parcel, n);
    }
}

