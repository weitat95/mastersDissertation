/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;
import com.google.android.gms.internal.zzdkw;
import com.google.android.gms.internal.zzdkz;
import com.google.android.gms.internal.zzdld;
import com.google.android.gms.internal.zzdle;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzdlv
extends zzp<zzdld> {
    private static zzdlv zzlfp;

    protected zzdlv() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static zzdkw zza(Activity object, zzk zzk2, WalletFragmentOptions walletFragmentOptions, zzdkz zzdkz2) throws GooglePlayServicesNotAvailableException {
        int n = GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)object);
        if (n != 0) {
            throw new GooglePlayServicesNotAvailableException(n);
        }
        try {
            if (zzlfp == null) {
                zzlfp = new zzdlv();
            }
            object = ((zzdld)zzlfp.zzde((Context)object)).zza(zzn.zzz(object), zzk2, walletFragmentOptions, zzdkz2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        catch (zzq zzq2) {
            throw new RuntimeException(zzq2);
        }
    }

    @Override
    protected final /* synthetic */ Object zze(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
        if (iInterface instanceof zzdld) {
            return (zzdld)iInterface;
        }
        return new zzdle(iBinder);
    }
}

