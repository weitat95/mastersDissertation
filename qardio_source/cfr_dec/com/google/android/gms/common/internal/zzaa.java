/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzbfn;

public final class zzaa
implements Parcelable.Creator<zzz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        zzc[] arrzzc = null;
        int n2 = zzbfn.zzd(parcel);
        Account account = null;
        Bundle bundle = null;
        Scope[] arrscope = null;
        IBinder iBinder = null;
        String string2 = null;
        int n3 = 0;
        int n4 = 0;
        block11 : while (parcel.dataPosition() < n2) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block11;
                }
                case 1: {
                    n4 = zzbfn.zzg(parcel, n5);
                    continue block11;
                }
                case 2: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block11;
                }
                case 3: {
                    n = zzbfn.zzg(parcel, n5);
                    continue block11;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n5);
                    continue block11;
                }
                case 5: {
                    iBinder = zzbfn.zzr(parcel, n5);
                    continue block11;
                }
                case 6: {
                    arrscope = zzbfn.zzb(parcel, n5, Scope.CREATOR);
                    continue block11;
                }
                case 7: {
                    bundle = zzbfn.zzs(parcel, n5);
                    continue block11;
                }
                case 8: {
                    account = (Account)zzbfn.zza(parcel, n5, Account.CREATOR);
                    continue block11;
                }
                case 10: 
            }
            arrzzc = zzbfn.zzb(parcel, n5, zzc.CREATOR);
        }
        zzbfn.zzaf(parcel, n2);
        return new zzz(n4, n3, n, string2, iBinder, arrscope, bundle, account, arrzzc);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzz[n];
    }
}

