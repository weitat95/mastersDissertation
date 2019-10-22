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
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.internal.zzff;

public final class zzfe
extends zzbfm
implements MessageEvent {
    public static final Parcelable.Creator<zzfe> CREATOR = new zzff();
    private final String mPath;
    private final String zzdrc;
    private final int zzgiq;
    private final byte[] zzhyw;

    public zzfe(int n, String string2, byte[] arrby, String string3) {
        this.zzgiq = n;
        this.mPath = string2;
        this.zzhyw = arrby;
        this.zzdrc = string3;
    }

    @Override
    public final byte[] getData() {
        return this.zzhyw;
    }

    @Override
    public final String getPath() {
        return this.mPath;
    }

    public final int getRequestId() {
        return this.zzgiq;
    }

    @Override
    public final String getSourceNodeId() {
        return this.zzdrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        int n = this.zzgiq;
        String string2 = this.mPath;
        Object object = this.zzhyw == null ? "null" : Integer.valueOf(this.zzhyw.length);
        object = String.valueOf(object);
        return new StringBuilder(String.valueOf(string2).length() + 43 + String.valueOf(object).length()).append("MessageEventParcelable[").append(n).append(",").append(string2).append(", size=").append((String)object).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.getRequestId());
        zzbfp.zza(parcel, 3, this.getPath(), false);
        zzbfp.zza(parcel, 4, this.getData(), false);
        zzbfp.zza(parcel, 5, this.getSourceNodeId(), false);
        zzbfp.zzai(parcel, n);
    }
}

