/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzasa;
import com.google.android.gms.internal.zzasb;
import com.google.android.gms.internal.zzash;

public final class zzarz
extends zzaqa {
    private SharedPreferences zzdyn;
    private long zzdyo;
    private long zzdyp = -1L;
    private final zzasb zzdyq = new zzasb(this, "monitoring", zzarl.zzdxl.get(), null);

    protected zzarz(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    static /* synthetic */ SharedPreferences zza(zzarz zzarz2) {
        return zzarz2.zzdyn;
    }

    public final String zzaaa() {
        zzj.zzve();
        this.zzxf();
        String string2 = this.zzdyn.getString("installation_campaign", null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        return string2;
    }

    public final zzasb zzaab() {
        return this.zzdyq;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzef(String string2) {
        zzj.zzve();
        this.zzxf();
        SharedPreferences.Editor editor = this.zzdyn.edit();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            editor.remove("installation_campaign");
        } else {
            editor.putString("installation_campaign", string2);
        }
        if (!editor.commit()) {
            this.zzdx("Failed to commit campaign data");
        }
    }

    @Override
    protected final void zzvf() {
        this.zzdyn = this.getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final long zzzw() {
        long l;
        block6: {
            block5: {
                zzj.zzve();
                this.zzxf();
                if (this.zzdyo != 0L) break block5;
                l = this.zzdyn.getLong("first_run", 0L);
                if (l == 0L) break block6;
                this.zzdyo = l;
            }
            do {
                return this.zzdyo;
                break;
            } while (true);
        }
        l = this.zzws().currentTimeMillis();
        SharedPreferences.Editor editor = this.zzdyn.edit();
        editor.putLong("first_run", l);
        if (!editor.commit()) {
            this.zzdx("Failed to commit first run time");
        }
        this.zzdyo = l;
        return this.zzdyo;
    }

    public final zzash zzzx() {
        return new zzash(this.zzws(), this.zzzw());
    }

    public final long zzzy() {
        zzj.zzve();
        this.zzxf();
        if (this.zzdyp == -1L) {
            this.zzdyp = this.zzdyn.getLong("last_dispatch", 0L);
        }
        return this.zzdyp;
    }

    public final void zzzz() {
        zzj.zzve();
        this.zzxf();
        long l = this.zzws().currentTimeMillis();
        SharedPreferences.Editor editor = this.zzdyn.edit();
        editor.putLong("last_dispatch", l);
        editor.apply();
        this.zzdyp = l;
    }
}

