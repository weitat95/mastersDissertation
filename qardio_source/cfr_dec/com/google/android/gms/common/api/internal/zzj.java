/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzj {
    private final ArrayMap<zzh<?>, ConnectionResult> zzflw;
    private final ArrayMap<zzh<?>, String> zzfoc = new ArrayMap();
    private final TaskCompletionSource<Map<zzh<?>, String>> zzfod = new TaskCompletionSource();
    private int zzfoe;
    private boolean zzfof = false;

    public zzj(Iterable<? extends GoogleApi<?>> object) {
        this.zzflw = new ArrayMap();
        object = object.iterator();
        while (object.hasNext()) {
            GoogleApi googleApi = (GoogleApi)object.next();
            this.zzflw.put(googleApi.zzagn(), null);
        }
        this.zzfoe = this.zzflw.keySet().size();
    }

    public final Task<Map<zzh<?>, String>> getTask() {
        return this.zzfod.getTask();
    }

    public final void zza(zzh<?> object, ConnectionResult connectionResult, String string2) {
        block5: {
            block4: {
                this.zzflw.put((zzh<?>)object, connectionResult);
                this.zzfoc.put((zzh<?>)object, string2);
                --this.zzfoe;
                if (!connectionResult.isSuccess()) {
                    this.zzfof = true;
                }
                if (this.zzfoe != 0) break block4;
                if (!this.zzfof) break block5;
                object = new AvailabilityException(this.zzflw);
                this.zzfod.setException((Exception)object);
            }
            return;
        }
        this.zzfod.setResult(this.zzfoc);
    }

    public final Set<zzh<?>> zzaha() {
        return this.zzflw.keySet();
    }
}

