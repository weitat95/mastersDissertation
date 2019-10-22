/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.internal.zzcue;
import com.google.android.gms.internal.zzcui;
import com.google.android.gms.internal.zzcup;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class zzbeu {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final zzcup zzfkh = new zzcup(zzcue.zzks("com.google.android.gms.clearcut.public")).zzku("gms:playlog:service:sampling_").zzkv("LogSampling__");
    private static Map<String, zzcui<String>> zzfki = null;
    private static Boolean zzfkj = null;
    private static Long zzfkk = null;
    private final Context zzair;

    public zzbeu(Context context) {
        this.zzair = context;
        if (zzfki == null) {
            zzfki = new HashMap<String, zzcui<String>>();
        }
        if (this.zzair != null) {
            zzcui.zzdz(this.zzair);
        }
    }
}

