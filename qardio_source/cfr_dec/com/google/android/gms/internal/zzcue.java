/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzcuf;
import com.google.android.gms.internal.zzcug;
import com.google.android.gms.internal.zzcus;
import com.google.android.gms.internal.zzcut;

public final class zzcue {
    @Deprecated
    private static Api<Object> API;
    private static final Api.zzf<zzcut> zzebf;
    private static final Api.zza<zzcut, Object> zzebg;
    @Deprecated
    private static zzcug zzjwx;

    static {
        zzebf = new Api.zzf();
        zzebg = new zzcuf();
        API = new Api<Object>("Phenotype.API", zzebg, zzebf);
        zzjwx = new zzcus();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Uri zzks(String string2) {
        if ((string2 = String.valueOf(Uri.encode((String)string2))).length() != 0) {
            string2 = "content://com.google.android.gms.phenotype/".concat(string2);
            do {
                return Uri.parse((String)string2);
                break;
            } while (true);
        }
        string2 = new String("content://com.google.android.gms.phenotype/");
        return Uri.parse((String)string2);
    }
}

