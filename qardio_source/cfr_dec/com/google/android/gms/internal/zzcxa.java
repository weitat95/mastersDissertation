/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzcxb;
import com.google.android.gms.internal.zzcxc;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxn;

public final class zzcxa {
    public static final Api<zzcxe> API;
    private static Api.zzf<zzcxn> zzebf;
    public static final Api.zza<zzcxn, zzcxe> zzebg;
    private static Scope zzehi;
    private static Scope zzehj;
    private static Api<Object> zzgjb;
    private static Api.zzf<zzcxn> zzkbq;
    private static Api.zza<zzcxn, Object> zzkbr;

    static {
        zzebf = new Api.zzf();
        zzkbq = new Api.zzf();
        zzebg = new zzcxb();
        zzkbr = new zzcxc();
        zzehi = new Scope("profile");
        zzehj = new Scope("email");
        API = new Api<zzcxe>("SignIn.API", zzebg, zzebf);
        zzgjb = new Api<Object>("SignIn.INTERNAL_API", zzkbr, zzkbq);
    }
}

