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
import com.google.android.gms.common.api.internal.zzcu;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

final class zzad
implements OnCompleteListener<Map<zzh<?>, String>> {
    private /* synthetic */ zzaa zzfqm;
    private zzcu zzfqn;

    final void cancel() {
        this.zzfqn.zzabi();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void onComplete(Task<Map<zzh<?>, String>> var1_1) {
        block17: {
            block14: {
                block15: {
                    block16: {
                        block13: {
                            zzaa.zza(this.zzfqm).lock();
                            if (!zzaa.zzb(this.zzfqm)) {
                                this.zzfqn.zzabi();
                                return;
                            }
                            if (!var1_1.isSuccessful()) break block13;
                            zzaa.zzb(this.zzfqm, new ArrayMap<K, V>(zzaa.zzm(this.zzfqm).size()));
                            for (zzz var2_3 : zzaa.zzm(this.zzfqm).values()) {
                                zzaa.zzg(this.zzfqm).put(var2_3.zzagn(), ConnectionResult.zzfkr);
                            }
                            break block14;
                        }
                        if (!(var1_1.getException() instanceof AvailabilityException)) break block15;
                        var1_1 = (AvailabilityException)var1_1.getException();
                        if (!zzaa.zze(this.zzfqm)) break block16;
                        zzaa.zzb(this.zzfqm, new ArrayMap<K, V>(zzaa.zzm(this.zzfqm).size()));
                        var2_4 = zzaa.zzm(this.zzfqm).values().iterator();
                        break block17;
                    }
                    zzaa.zzb(this.zzfqm, var1_1.zzagj());
                    break block14;
                }
                Log.e((String)"ConnectionlessGAC", (String)"Unexpected availability exception", (Throwable)var1_1.getException());
                zzaa.zzb(this.zzfqm, Collections.<K, V>emptyMap());
                break block14;
                finally {
                    zzaa.zza(this.zzfqm).unlock();
                }
            }
            do {
                if (this.zzfqm.isConnected()) {
                    zzaa.zzd(this.zzfqm).putAll(zzaa.zzg(this.zzfqm));
                    if (zzaa.zzf(this.zzfqm) == null) {
                        zzaa.zzi(this.zzfqm);
                        zzaa.zzj(this.zzfqm);
                        zzaa.zzl(this.zzfqm).signalAll();
                    }
                }
                this.zzfqn.zzabi();
                return;
                break;
            } while (true);
        }
        do lbl-1000:
        // 3 sources
        {
            if (!var2_4.hasNext()) ** continue;
            var3_5 = (zzz)var2_4.next();
            var4_6 = var3_5.zzagn();
            var5_7 = var1_1.getConnectionResult((GoogleApi<? extends Api.ApiOptions>)var3_5);
            if (zzaa.zza(this.zzfqm, var3_5, var5_7)) {
                zzaa.zzg(this.zzfqm).put(var4_6, new ConnectionResult(16));
                ** continue;
            }
            zzaa.zzg(this.zzfqm).put(var4_6, var5_7);
        } while (true);
    }
}

