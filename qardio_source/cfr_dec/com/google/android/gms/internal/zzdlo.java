/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzdlb;
import com.google.android.gms.internal.zzdlc;
import com.google.android.gms.internal.zzdlf;
import com.google.android.gms.internal.zzdlp;
import com.google.android.gms.internal.zzdlq;
import com.google.android.gms.internal.zzdlt;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class zzdlo
extends zzab<zzdlb> {
    private final Context mContext;
    private final int mTheme;
    private final String zzebv;
    private final int zzlea;
    private final boolean zzleb;

    public zzdlo(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, int n, int n2, boolean bl) {
        super(context, looper, 4, zzr2, connectionCallbacks, onConnectionFailedListener);
        this.mContext = context;
        this.zzlea = n;
        this.zzebv = zzr2.getAccountName();
        this.mTheme = n2;
        this.zzleb = bl;
    }

    private final Bundle zzbka() {
        int n = this.zzlea;
        String string2 = this.mContext.getPackageName();
        String string3 = this.zzebv;
        int n2 = this.mTheme;
        boolean bl = this.zzleb;
        Bundle bundle = new Bundle();
        bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", n);
        bundle.putBoolean("com.google.android.gms.wallet.EXTRA_USING_ANDROID_PAY_BRAND", bl);
        bundle.putString("androidPackageName", string2);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", (Parcelable)new Account(string3, "com.google"));
        }
        bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", n2);
        return bundle;
    }

    public final void zza(FullWalletRequest fullWalletRequest, int n) {
        zzdlp zzdlp2 = new zzdlp((Activity)this.mContext, n);
        Bundle bundle = this.zzbka();
        try {
            ((zzdlb)this.zzakn()).zza(fullWalletRequest, bundle, (zzdlf)zzdlp2);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException getting full wallet", (Throwable)remoteException);
            ((zzdlq)zzdlp2).zza(8, (FullWallet)null, Bundle.EMPTY);
            return;
        }
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, zzn<BooleanResult> object) {
        object = new zzdlt((zzn<BooleanResult>)object);
        Bundle bundle = this.zzbka();
        try {
            ((zzdlb)this.zzakn()).zza(isReadyToPayRequest, bundle, (zzdlf)object);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException during isReadyToPay", (Throwable)remoteException);
            ((zzdlq)object).zza(Status.zzfnk, false, Bundle.EMPTY);
            return;
        }
    }

    public final void zza(MaskedWalletRequest maskedWalletRequest, int n) {
        Object object = (Activity)this.mContext;
        Bundle bundle = this.zzbka();
        object = new zzdlp((Activity)object, n);
        try {
            ((zzdlb)this.zzakn()).zza(maskedWalletRequest, bundle, (zzdlf)object);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException getting masked wallet", (Throwable)remoteException);
            ((zzdlq)object).zza(8, (MaskedWallet)null, Bundle.EMPTY);
            return;
        }
    }

    @Override
    public final boolean zzako() {
        return true;
    }

    public final void zzc(String string2, String string3, int n) {
        Object object = (Activity)this.mContext;
        Bundle bundle = this.zzbka();
        object = new zzdlp((Activity)object, n);
        try {
            ((zzdlb)this.zzakn()).zza(string2, string3, bundle, (zzdlf)object);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException changing masked wallet", (Throwable)remoteException);
            ((zzdlq)object).zza(8, (MaskedWallet)null, Bundle.EMPTY);
            return;
        }
    }

    @Override
    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IOwService");
        if (iInterface instanceof zzdlb) {
            return (zzdlb)iInterface;
        }
        return new zzdlc(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.gms.wallet.service.BIND";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.wallet.internal.IOwService";
    }
}

