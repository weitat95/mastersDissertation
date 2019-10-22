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
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.internal.zzbi;

public final class zzay
extends zzbfm
implements Channel,
ChannelClient.Channel {
    public static final Parcelable.Creator<zzay> CREATOR = new zzbi();
    private final String mPath;
    private final String zzecl;
    private final String zzlgr;

    public zzay(String string2, String string3, String string4) {
        this.zzecl = zzbq.checkNotNull(string2);
        this.zzlgr = zzbq.checkNotNull(string3);
        this.mPath = zzbq.checkNotNull(string4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof zzay)) {
                    return false;
                }
                object = (zzay)object;
                if (!this.zzecl.equals(((zzay)object).zzecl) || !zzbg.equal(((zzay)object).zzlgr, this.zzlgr) || !zzbg.equal(((zzay)object).mPath, this.mPath)) break block5;
            }
            return true;
        }
        return false;
    }

    public final String getNodeId() {
        return this.zzlgr;
    }

    public final String getPath() {
        return this.mPath;
    }

    public final int hashCode() {
        return this.zzecl.hashCode();
    }

    public final String toString() {
        int n;
        Object object = this.zzecl.toCharArray();
        int n2 = ((char[])object).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            n3 += object[n];
        }
        String string2 = this.zzecl.trim();
        n = string2.length();
        object = string2;
        if (n > 25) {
            object = string2.substring(0, 10);
            string2 = string2.substring(n - 10, n);
            object = new StringBuilder(String.valueOf(object).length() + 16 + String.valueOf(string2).length()).append((String)object).append("...").append(string2).append("::").append(n3).toString();
        }
        string2 = this.zzlgr;
        String string3 = this.mPath;
        return new StringBuilder(String.valueOf(object).length() + 31 + String.valueOf(string2).length() + String.valueOf(string3).length()).append("Channel{token=").append((String)object).append(", nodeId=").append(string2).append(", path=").append(string3).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzecl, false);
        zzbfp.zza(parcel, 3, this.getNodeId(), false);
        zzbfp.zza(parcel, 4, this.getPath(), false);
        zzbfp.zzai(parcel, n);
    }
}

