/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzcxf;

public final class zzcxe
implements Api.ApiOptions.Optional {
    public static final zzcxe zzkbs;
    private final boolean zzefl;
    private final String zzefm;
    private final boolean zzehn;
    private final String zzeho;
    private final boolean zzkbt;
    private final boolean zzkbu;
    private final Long zzkbv = null;
    private final Long zzkbw = null;

    static {
        new zzcxf();
        zzkbs = new zzcxe(false, false, null, false, null, false, null, null);
    }

    private zzcxe(boolean bl, boolean bl2, String string2, boolean bl3, String string3, boolean bl4, Long l, Long l2) {
        this.zzkbt = false;
        this.zzefl = false;
        this.zzefm = null;
        this.zzehn = false;
        this.zzkbu = false;
        this.zzeho = null;
    }

    public final String getServerClientId() {
        return this.zzefm;
    }

    public final boolean isIdTokenRequested() {
        return this.zzefl;
    }

    public final boolean zzbdc() {
        return this.zzkbt;
    }

    public final boolean zzbdd() {
        return this.zzehn;
    }

    public final String zzbde() {
        return this.zzeho;
    }

    public final boolean zzbdf() {
        return this.zzkbu;
    }

    public final Long zzbdg() {
        return this.zzkbv;
    }

    public final Long zzbdh() {
        return this.zzkbw;
    }
}

