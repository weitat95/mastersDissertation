/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzaa;
import com.google.android.gms.common.api.internal.zzab;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

final class zzac
implements OnCompleteListener<Map<zzh<?>, String>> {
    private /* synthetic */ zzaa zzfqm;

    private zzac(zzaa zzaa2) {
        this.zzfqm = zzaa2;
    }

    /* synthetic */ zzac(zzaa zzaa2, zzab zzab2) {
        this(zzaa2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onComplete(Task<Map<zzh<?>, String>> iterator) {
        block16: {
            block18: {
                Iterator iterator2;
                block17: {
                    zzaa.zza(this.zzfqm).lock();
                    boolean bl = zzaa.zzb(this.zzfqm);
                    if (!bl) {
                        zzaa.zza(this.zzfqm).unlock();
                        return;
                    }
                    if (((Task)((Object)iterator)).isSuccessful()) {
                        zzaa.zza(this.zzfqm, new ArrayMap(zzaa.zzc(this.zzfqm).size()));
                        for (zzz zzz2 : zzaa.zzc(this.zzfqm).values()) {
                            zzaa.zzd(this.zzfqm).put(zzz2.zzagn(), ConnectionResult.zzfkr);
                        }
                        break block16;
                    }
                    if (((Task)((Object)iterator)).getException() instanceof AvailabilityException) {
                        iterator = (AvailabilityException)((Task)((Object)iterator)).getException();
                        if (zzaa.zze(this.zzfqm)) {
                            zzaa.zza(this.zzfqm, new ArrayMap(zzaa.zzc(this.zzfqm).size()));
                            iterator2 = zzaa.zzc(this.zzfqm).values().iterator();
                            break block17;
                        }
                        zzaa.zza(this.zzfqm, ((AvailabilityException)((Object)iterator)).zzagj());
                        break block18;
                    }
                    Log.e((String)"ConnectionlessGAC", (String)"Unexpected availability exception", (Throwable)((Task)((Object)iterator)).getException());
                    zzaa.zza(this.zzfqm, Collections.emptyMap());
                    zzaa.zza(this.zzfqm, new ConnectionResult(8));
                    break block16;
                }
                while (iterator2.hasNext()) {
                    zzz zzz3 = (zzz)iterator2.next();
                    zzh zzh2 = zzz3.zzagn();
                    ConnectionResult connectionResult = ((AvailabilityException)((Object)iterator)).getConnectionResult(zzz3);
                    if (zzaa.zza(this.zzfqm, zzz3, connectionResult)) {
                        zzaa.zzd(this.zzfqm).put(zzh2, new ConnectionResult(16));
                        continue;
                    }
                    zzaa.zzd(this.zzfqm).put(zzh2, connectionResult);
                }
            }
            zzaa.zza(this.zzfqm, zzaa.zzf(this.zzfqm));
        }
        if (zzaa.zzg(this.zzfqm) != null) {
            zzaa.zzd(this.zzfqm).putAll(zzaa.zzg(this.zzfqm));
            zzaa.zza(this.zzfqm, zzaa.zzf(this.zzfqm));
        }
        if (zzaa.zzh(this.zzfqm) == null) {
            zzaa.zzi(this.zzfqm);
            zzaa.zzj(this.zzfqm);
        } else {
            zzaa.zza(this.zzfqm, false);
            zzaa.zzk(this.zzfqm).zzc(zzaa.zzh(this.zzfqm));
        }
        zzaa.zzl(this.zzfqm).signalAll();
    }
}

