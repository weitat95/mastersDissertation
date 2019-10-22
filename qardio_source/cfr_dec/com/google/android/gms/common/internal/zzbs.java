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
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbfn;

public final class zzbs
implements Parcelable.Creator<zzbr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        Account account = null;
        int n3 = 0;
        GoogleSignInAccount googleSignInAccount = null;
        block6 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    account = (Account)zzbfn.zza(parcel, n4, Account.CREATOR);
                    continue block6;
                }
                case 3: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            googleSignInAccount = zzbfn.zza(parcel, n4, GoogleSignInAccount.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzbr(n3, account, n2, googleSignInAccount);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzbr[n];
    }
}

