/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzct;
import com.google.android.gms.common.api.internal.zzdg;

final class zzdi
extends Handler {
    private /* synthetic */ zzdg zzfvf;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void handleMessage(Message object) {
        switch (((Message)object).what) {
            default: {
                int n = ((Message)object).what;
                Log.e((String)"TransformedResultImpl", (String)new StringBuilder(70).append("TransformationResultHandler received unknown message type: ").append(n).toString());
                return;
            }
            case 0: {
                PendingResult pendingResult = (PendingResult)((Message)object).obj;
                object = zzdg.zzf(this.zzfvf);
                synchronized (object) {
                    if (pendingResult == null) {
                        zzdg.zza(zzdg.zzg(this.zzfvf), new Status(13, "Transform returned null"));
                    } else if (pendingResult instanceof zzct) {
                        zzdg.zza(zzdg.zzg(this.zzfvf), ((zzct)pendingResult).getStatus());
                    } else {
                        zzdg.zzg(this.zzfvf).zza(pendingResult);
                    }
                    return;
                }
            }
            case 1: 
        }
        RuntimeException runtimeException = (RuntimeException)((Message)object).obj;
        object = String.valueOf(runtimeException.getMessage());
        object = ((String)object).length() != 0 ? "Runtime exception on the transformation worker thread: ".concat((String)object) : new String("Runtime exception on the transformation worker thread: ");
        Log.e((String)"TransformedResultImpl", (String)object);
        throw runtimeException;
    }
}

