/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarn;
import com.google.android.gms.internal.zzarp;

public final class zzasm
extends zzaqa {
    private boolean zzdox;
    private String zzdqz;
    private String zzdra;
    private int zzdvo;
    protected int zzdxr;
    protected boolean zzdzk;
    private boolean zzdzl;

    public zzasm(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    public final boolean zzaai() {
        this.zzxf();
        return false;
    }

    public final boolean zzaaj() {
        this.zzxf();
        return this.zzdzl;
    }

    public final boolean zzaak() {
        this.zzxf();
        return this.zzdox;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void zzvf() {
        void var3_3;
        boolean bl;
        Context context = this.getContext();
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            this.zzd("PackageManager doesn't know about the app package", (Object)nameNotFoundException);
            Object var3_5 = null;
        }
        if (var3_3 == null) {
            this.zzdx("Couldn't get ApplicationInfo to load global config");
            return;
        } else {
            zzarp zzarp2;
            int n;
            Bundle bundle = var3_3.metaData;
            if (bundle == null || (n = bundle.getInt("com.google.android.gms.analytics.globalConfigResource")) <= 0 || (zzarp2 = (zzarp)new zzarn(this.zzwr()).zzav(n)) == null) return;
            {
                String string2;
                this.zzdu("Loading global XML config values");
                n = zzarp2.zzdqz != null ? 1 : 0;
                if (n != 0) {
                    this.zzdqz = string2 = zzarp2.zzdqz;
                    this.zzb("XML config - app name", string2);
                }
                if ((n = zzarp2.zzdra != null ? 1 : 0) != 0) {
                    this.zzdra = string2 = zzarp2.zzdra;
                    this.zzb("XML config - app version", string2);
                }
                if ((n = zzarp2.zzdxq != null ? 1 : 0) != 0 && (n = "verbose".equals(string2 = zzarp2.zzdxq.toLowerCase()) ? 0 : ("info".equals(string2) ? 1 : ("warning".equals(string2) ? 2 : ("error".equals(string2) ? 3 : -1)))) >= 0) {
                    this.zzdvo = n;
                    this.zza("XML config - log level", n);
                }
                if ((n = zzarp2.zzdxr >= 0 ? 1 : 0) != 0) {
                    this.zzdxr = n = zzarp2.zzdxr;
                    this.zzdzk = true;
                    this.zzb("XML config - dispatch period (sec)", n);
                }
                if (zzarp2.zzdxs == -1) return;
                {
                    bl = zzarp2.zzdxs == 1;
                }
            }
        }
        this.zzdox = bl;
        this.zzdzl = true;
        this.zzb("XML config - dry run", bl);
    }

    public final String zzvi() {
        this.zzxf();
        return this.zzdqz;
    }

    public final String zzvj() {
        this.zzxf();
        return this.zzdra;
    }
}

