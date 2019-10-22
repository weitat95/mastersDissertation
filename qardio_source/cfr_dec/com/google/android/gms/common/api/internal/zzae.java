/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzaf;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzdj;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzae {
    private final Map<BasePendingResult<?>, Boolean> zzfqo = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zzfqp = Collections.synchronizedMap(new WeakHashMap());

    static /* synthetic */ Map zza(zzae zzae2) {
        return zzae2.zzfqo;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zza(boolean bl, Status status) {
        Object object;
        Object object2 = this.zzfqo;
        synchronized (object2) {
            object = new HashMap(this.zzfqo);
        }
        Object object32 = this.zzfqp;
        synchronized (object32) {
            object2 = new HashMap(this.zzfqp);
        }
        for (Object object32 : object.entrySet()) {
            if (!bl && !((Boolean)object32.getValue()).booleanValue()) continue;
            ((BasePendingResult)object32.getKey()).zzv(status);
        }
        object2 = object2.entrySet().iterator();
        while (object2.hasNext()) {
            object = (Map.Entry)object2.next();
            if (!bl && !((Boolean)object.getValue()).booleanValue()) continue;
            ((TaskCompletionSource)object.getKey()).trySetException(new ApiException(status));
        }
        return;
    }

    final void zza(BasePendingResult<? extends Result> basePendingResult, boolean bl) {
        this.zzfqo.put(basePendingResult, bl);
        ((PendingResult)basePendingResult).zza(new zzaf(this, basePendingResult));
    }

    final boolean zzahu() {
        return !this.zzfqo.isEmpty() || !this.zzfqp.isEmpty();
    }

    public final void zzahv() {
        this.zza(false, zzbm.zzfsy);
    }

    public final void zzahw() {
        this.zza(true, zzdj.zzfvg);
    }
}

