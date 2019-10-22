/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.zzbs;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzbr
extends zzbfm {
    public static final Parcelable.Creator<zzbr> CREATOR = new zzbs();
    private final Account zzebz;
    private int zzeck;
    private final int zzgbl;
    private final GoogleSignInAccount zzgbm;

    zzbr(int n, Account account, int n2, GoogleSignInAccount googleSignInAccount) {
        this.zzeck = n;
        this.zzebz = account;
        this.zzgbl = n2;
        this.zzgbm = googleSignInAccount;
    }

    public zzbr(Account account, int n, GoogleSignInAccount googleSignInAccount) {
        this(2, account, n, googleSignInAccount);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, (Parcelable)this.zzebz, n, false);
        zzbfp.zzc(parcel, 3, this.zzgbl);
        zzbfp.zza(parcel, 4, this.zzgbm, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

