/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.zzby;
import com.google.android.gms.tagmanager.zzbz;
import com.google.android.gms.tagmanager.zzca;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzek;
import com.google.android.gms.tagmanager.zzfm;

public final class zzbb
implements zzby {
    private static final Object zzkcr = new Object();
    private static zzbb zzkff;
    private zzek zzkdt;
    private zzbz zzkfg;

    private zzbb(Context context) {
        this(zzca.zzei(context), new zzfm());
    }

    private zzbb(zzbz zzbz2, zzek zzek2) {
        this.zzkfg = zzbz2;
        this.zzkdt = zzek2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzby zzec(Context object) {
        Object object2 = zzkcr;
        synchronized (object2) {
            if (zzkff != null) return zzkff;
            zzkff = new zzbb((Context)object);
            return zzkff;
        }
    }

    @Override
    public final boolean zzll(String string2) {
        if (!this.zzkdt.zzzn()) {
            zzdj.zzcu("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        this.zzkfg.zzlq(string2);
        return true;
    }
}

