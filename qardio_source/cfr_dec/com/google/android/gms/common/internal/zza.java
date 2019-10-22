/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzao;

public final class zza
extends zzao {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Account zza(zzan zzan2) {
        Account account = null;
        if (zzan2 == null) return account;
        long l = Binder.clearCallingIdentity();
        try {
            account = zzan2.getAccount();
        }
        catch (RemoteException remoteException) {
            Log.w((String)"AccountAccessor", (String)"Remote account accessor probably died");
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
        return account;
    }

    public final boolean equals(Object object) {
        throw new NoSuchMethodError();
    }

    @Override
    public final Account getAccount() {
        throw new NoSuchMethodError();
    }
}

