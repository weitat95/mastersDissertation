/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zze;

public final class zzn
extends zze {
    private /* synthetic */ zzd zzfza;
    private IBinder zzfze;

    public zzn(zzd zzd2, int n, IBinder iBinder, Bundle bundle) {
        this.zzfza = zzd2;
        super(zzd2, n, bundle);
        this.zzfze = iBinder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final boolean zzakr() {
        block4: {
            String string2;
            String string3;
            try {
                string2 = this.zzfze.getInterfaceDescriptor();
                if (this.zzfza.zzhj().equals(string2)) break block4;
                string3 = this.zzfza.zzhj();
            }
            catch (RemoteException remoteException) {
                Log.w((String)"GmsClient", (String)"service probably died");
                return false;
            }
            Log.e((String)"GmsClient", (String)new StringBuilder(String.valueOf(string3).length() + 34 + String.valueOf(string2).length()).append("service descriptor mismatch: ").append(string3).append(" vs. ").append(string2).toString());
            return false;
        }
        Object t = this.zzfza.zzd(this.zzfze);
        if (t == null || !zzd.zza(this.zzfza, 2, 4, t) && !zzd.zza(this.zzfza, 3, 4, t)) return false;
        {
            zzd.zza(this.zzfza, null);
            Bundle bundle = this.zzfza.zzafi();
            if (zzd.zze(this.zzfza) == null) return true;
            {
                zzd.zze(this.zzfza).onConnected(bundle);
            }
            return true;
        }
    }

    @Override
    protected final void zzj(ConnectionResult connectionResult) {
        if (zzd.zzg(this.zzfza) != null) {
            zzd.zzg(this.zzfza).onConnectionFailed(connectionResult);
        }
        this.zzfza.onConnectionFailed(connectionResult);
    }
}

