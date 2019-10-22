/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzd;
import java.util.ArrayList;

public abstract class zzi<TListener> {
    private TListener zzfuk;
    private /* synthetic */ zzd zzfza;
    private boolean zzfzb;

    public zzi(TListener TListener) {
        this.zzfza = var1_1;
        this.zzfuk = TListener;
        this.zzfzb = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeListener() {
        synchronized (this) {
            this.zzfuk = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void unregister() {
        this.removeListener();
        ArrayList arrayList = zzd.zzf(this.zzfza);
        synchronized (arrayList) {
            zzd.zzf(this.zzfza).remove(this);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void zzaks() {
        // MONITORENTER : this
        TListener TListener = this.zzfuk;
        if (this.zzfzb) {
            String string2 = String.valueOf(this);
            Log.w((String)"GmsClient", (String)new StringBuilder(String.valueOf(string2).length() + 47).append("Callback proxy ").append(string2).append(" being reused. This is not safe.").toString());
        }
        // MONITOREXIT : this
        if (TListener != null) {
            this.zzw(TListener);
        }
        // MONITORENTER : this
        this.zzfzb = true;
        // MONITOREXIT : this
        this.unregister();
        return;
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
    }

    protected abstract void zzw(TListener var1);
}

