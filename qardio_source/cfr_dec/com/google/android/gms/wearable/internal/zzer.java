/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.wearable.internal.zzd;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;
import com.google.android.gms.wearable.internal.zzeq;
import com.google.android.gms.wearable.internal.zzgz;
import com.google.android.gms.wearable.internal.zzhk;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class zzer<T> {
    private final Map<T, zzhk<T>> zzhhi = new HashMap<T, zzhk<T>>();

    zzer() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzbr(IBinder object) {
        Map<T, zzhk<T>> map = this.zzhhi;
        synchronized (map) {
            IInterface iInterface;
            object = object == null ? null : ((iInterface = object.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService")) instanceof zzep ? (zzep)iInterface : new zzeq((IBinder)object));
            iInterface = new zzgz();
            Iterator<Map.Entry<T, zzhk<T>>> iterator = this.zzhhi.entrySet().iterator();
            while (iterator.hasNext()) {
                Object object2 = iterator.next();
                Object object3 = object2.getValue();
                try {
                    object.zza((zzek)iInterface, new zzd((zzhk)object3));
                    if (!Log.isLoggable((String)"WearableClient", (int)3)) continue;
                    String string2 = String.valueOf(object2.getKey());
                    String string3 = String.valueOf(object3);
                    Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string2).length() + 27 + String.valueOf(string3).length()).append("onPostInitHandler: added: ").append(string2).append("/").append(string3).toString());
                }
                catch (RemoteException remoteException) {
                    object2 = String.valueOf(object2.getKey());
                    object3 = String.valueOf(object3);
                    Log.w((String)"WearableClient", (String)new StringBuilder(String.valueOf(object2).length() + 32 + String.valueOf(object3).length()).append("onPostInitHandler: Didn't add: ").append((String)object2).append("/").append((String)object3).toString());
                    continue;
                }
                break;
            }
            return;
        }
    }
}

