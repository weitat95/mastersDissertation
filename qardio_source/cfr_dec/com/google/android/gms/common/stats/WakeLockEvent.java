/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.stats.StatsEvent;
import com.google.android.gms.common.stats.zzd;
import com.google.android.gms.internal.zzbfp;
import java.util.List;

public final class WakeLockEvent
extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzd();
    private final long mTimeout;
    private int zzeck;
    private final long zzgdl;
    private int zzgdm;
    private final String zzgdn;
    private final String zzgdo;
    private final String zzgdp;
    private final int zzgdq;
    private final List<String> zzgdr;
    private final String zzgds;
    private final long zzgdt;
    private int zzgdu;
    private final String zzgdv;
    private final float zzgdw;
    private long zzgdx;

    WakeLockEvent(int n, long l, int n2, String string2, int n3, List<String> list, String string3, long l2, int n4, String string4, String string5, float f, long l3, String string6) {
        this.zzeck = n;
        this.zzgdl = l;
        this.zzgdm = n2;
        this.zzgdn = string2;
        this.zzgdo = string4;
        this.zzgdp = string6;
        this.zzgdq = n3;
        this.zzgdx = -1L;
        this.zzgdr = list;
        this.zzgds = string3;
        this.zzgdt = l2;
        this.zzgdu = n4;
        this.zzgdv = string5;
        this.zzgdw = f;
        this.mTimeout = l3;
    }

    public WakeLockEvent(long l, int n, String string2, int n2, List<String> list, String string3, long l2, int n3, String string4, String string5, float f, long l3, String string6) {
        this(2, l, n, string2, n2, list, string3, l2, n3, string4, string5, f, l3, string6);
    }

    @Override
    public final int getEventType() {
        return this.zzgdm;
    }

    @Override
    public final long getTimeMillis() {
        return this.zzgdl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, ((StatsEvent)this).getTimeMillis());
        zzbfp.zza(parcel, 4, this.zzgdn, false);
        zzbfp.zzc(parcel, 5, this.zzgdq);
        zzbfp.zzb(parcel, 6, this.zzgdr, false);
        zzbfp.zza(parcel, 8, this.zzgdt);
        zzbfp.zza(parcel, 10, this.zzgdo, false);
        zzbfp.zzc(parcel, 11, ((StatsEvent)this).getEventType());
        zzbfp.zza(parcel, 12, this.zzgds, false);
        zzbfp.zza(parcel, 13, this.zzgdv, false);
        zzbfp.zzc(parcel, 14, this.zzgdu);
        zzbfp.zza(parcel, 15, this.zzgdw);
        zzbfp.zza(parcel, 16, this.mTimeout);
        zzbfp.zza(parcel, 17, this.zzgdp, false);
        zzbfp.zzai(parcel, n);
    }

    @Override
    public final long zzamd() {
        return this.zzgdx;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final String zzame() {
        String string2;
        String string3 = this.zzgdn;
        int n = this.zzgdq;
        String string4 = this.zzgdr == null ? "" : TextUtils.join((CharSequence)",", this.zzgdr);
        int n2 = this.zzgdu;
        String string5 = this.zzgdo == null ? "" : this.zzgdo;
        String string6 = this.zzgdv == null ? "" : this.zzgdv;
        float f = this.zzgdw;
        if (this.zzgdp == null) {
            string2 = "";
            return new StringBuilder(String.valueOf("\t").length() + 37 + String.valueOf(string3).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string4).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string5).length() + String.valueOf("\t").length() + String.valueOf(string6).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string2).length()).append("\t").append(string3).append("\t").append(n).append("\t").append(string4).append("\t").append(n2).append("\t").append(string5).append("\t").append(string6).append("\t").append(f).append("\t").append(string2).toString();
        }
        string2 = this.zzgdp;
        return new StringBuilder(String.valueOf("\t").length() + 37 + String.valueOf(string3).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string4).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string5).length() + String.valueOf("\t").length() + String.valueOf(string6).length() + String.valueOf("\t").length() + String.valueOf("\t").length() + String.valueOf(string2).length()).append("\t").append(string3).append("\t").append(n).append("\t").append(string4).append("\t").append(n2).append("\t").append(string5).append("\t").append(string6).append("\t").append(f).append("\t").append(string2).toString();
    }
}

