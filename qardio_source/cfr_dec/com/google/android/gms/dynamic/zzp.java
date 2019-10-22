/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzq;

public abstract class zzp<T> {
    private final String zzgwn;
    private T zzgwo;

    protected zzp(String string2) {
        this.zzgwn = string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final T zzde(Context object) throws zzq {
        if (this.zzgwo != null) return this.zzgwo;
        zzbq.checkNotNull(object);
        object = com.google.android.gms.common.zzp.getRemoteContext((Context)object);
        if (object == null) {
            throw new zzq("Could not get remote context.");
        }
        object = object.getClassLoader();
        try {
            this.zzgwo = this.zze((IBinder)((ClassLoader)object).loadClass(this.zzgwn).newInstance());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new zzq("Could not load creator class.", classNotFoundException);
        }
        catch (InstantiationException instantiationException) {
            throw new zzq("Could not instantiate creator.", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new zzq("Could not access creator.", illegalAccessException);
        }
        return this.zzgwo;
    }

    protected abstract T zze(IBinder var1);
}

