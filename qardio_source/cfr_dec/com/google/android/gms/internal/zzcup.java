/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.internal;

import android.net.Uri;

public final class zzcup {
    private final String zzjxi;
    private final Uri zzjxj;
    private final String zzjxk;
    private final String zzjxl;
    private final boolean zzjxm;
    private final boolean zzjxn;

    public zzcup(Uri uri) {
        this(null, uri, "", "", false, false);
    }

    private zzcup(String string2, Uri uri, String string3, String string4, boolean bl, boolean bl2) {
        this.zzjxi = string2;
        this.zzjxj = uri;
        this.zzjxk = string3;
        this.zzjxl = string4;
        this.zzjxm = bl;
        this.zzjxn = bl2;
    }

    public final zzcup zzku(String string2) {
        if (this.zzjxm) {
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }
        return new zzcup(this.zzjxi, this.zzjxj, string2, this.zzjxl, this.zzjxm, this.zzjxn);
    }

    public final zzcup zzkv(String string2) {
        return new zzcup(this.zzjxi, this.zzjxj, this.zzjxk, string2, this.zzjxm, this.zzjxn);
    }
}

