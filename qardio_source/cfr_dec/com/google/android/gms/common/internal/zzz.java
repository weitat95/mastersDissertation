/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzap;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzz
extends zzbfm {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();
    private int version;
    private int zzfzr;
    private int zzfzs;
    String zzfzt;
    IBinder zzfzu;
    Scope[] zzfzv;
    Bundle zzfzw;
    Account zzfzx;
    zzc[] zzfzy;

    public zzz(int n) {
        this.version = 3;
        this.zzfzs = zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzfzr = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    zzz(int n, int n2, int n3, String object, IBinder iBinder, Scope[] arrscope, Bundle bundle, Account account, zzc[] arrzzc) {
        Object var11_10 = null;
        Object var10_11 = null;
        this.version = n;
        this.zzfzr = n2;
        this.zzfzs = n3;
        this.zzfzt = "com.google.android.gms".equals(object) ? "com.google.android.gms" : object;
        if (n < 2) {
            object = var11_10;
            if (iBinder != null) {
                object = iBinder == null ? var10_11 : ((object = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor")) instanceof zzan ? (zzan)object : new zzap(iBinder));
                object = zza.zza((zzan)object);
            }
            this.zzfzx = object;
        } else {
            this.zzfzu = iBinder;
            this.zzfzx = account;
        }
        this.zzfzv = arrscope;
        this.zzfzw = bundle;
        this.zzfzy = arrzzc;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.version);
        zzbfp.zzc(parcel, 2, this.zzfzr);
        zzbfp.zzc(parcel, 3, this.zzfzs);
        zzbfp.zza(parcel, 4, this.zzfzt, false);
        zzbfp.zza(parcel, 5, this.zzfzu, false);
        zzbfp.zza((Parcel)parcel, (int)6, (Parcelable[])this.zzfzv, (int)n, (boolean)false);
        zzbfp.zza(parcel, 7, this.zzfzw, false);
        zzbfp.zza(parcel, 8, (Parcelable)this.zzfzx, n, false);
        zzbfp.zza((Parcel)parcel, (int)10, (Parcelable[])this.zzfzy, (int)n, (boolean)false);
        zzbfp.zzai(parcel, n2);
    }
}

