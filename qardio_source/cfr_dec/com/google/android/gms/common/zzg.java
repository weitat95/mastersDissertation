/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzba;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzh;
import com.google.android.gms.common.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzg {
    private static zzba zzfky;
    private static final Object zzfkz;
    private static Context zzfla;

    static {
        zzfkz = new Object();
    }

    static boolean zza(String string2, zzh zzh2) {
        return zzg.zza(string2, zzh2, false);
    }

    private static boolean zza(String object, zzh zzh2, boolean bl) {
        if (!zzg.zzafz()) {
            return false;
        }
        zzbq.checkNotNull(zzfla);
        try {
            object = new zzn((String)object, zzh2, bl);
            bl = zzfky.zza((zzn)object, com.google.android.gms.dynamic.zzn.zzz(zzfla.getPackageManager()));
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static boolean zzafz() {
        if (zzfky != null) {
            return true;
        }
        zzbq.checkNotNull(zzfla);
        Object object = zzfkz;
        // MONITORENTER : object
        zzba zzba2 = zzfky;
        if (zzba2 != null) return true;
        try {
            zzfky = zzbb.zzan(DynamiteModule.zza(zzfla, DynamiteModule.zzgwz, "com.google.android.gms.googlecertificates").zzhb("com.google.android.gms.common.GoogleCertificatesImpl"));
            // MONITOREXIT : object
            return true;
        }
        catch (DynamiteModule.zzc zzc2) {
            Log.e((String)"GoogleCertificates", (String)"Failed to load com.google.android.gms.googlecertificates", (Throwable)zzc2);
            // MONITOREXIT : object
            return false;
        }
    }

    static boolean zzb(String string2, zzh zzh2) {
        return zzg.zza(string2, zzh2, true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void zzcg(Context context) {
        synchronized (zzg.class) {
            block7: {
                block8: {
                    if (zzfla != null) break block7;
                    if (context == null) break block8;
                    zzfla = context.getApplicationContext();
                }
                do {
                    return;
                    break;
                } while (true);
            }
            Log.w((String)"GoogleCertificates", (String)"GoogleCertificates has been initialized already");
            return;
        }
    }
}

