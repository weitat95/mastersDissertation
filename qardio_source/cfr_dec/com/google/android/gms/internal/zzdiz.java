/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzdjj;
import java.util.HashMap;
import java.util.Map;

public final class zzdiz {
    private final Context mContext;
    private final zzd zzata;
    private String zzkeh = null;
    private Map<String, Object> zzkrd = new HashMap<String, Object>();
    private final Map<String, Object> zzkre;
    private final zzdjj zzktp;

    public zzdiz(Context context) {
        this(context, new HashMap<String, Object>(), new zzdjj(context), zzh.zzamg());
    }

    private zzdiz(Context context, Map<String, Object> map, zzdjj zzdjj2, zzd zzd2) {
        this.mContext = context;
        this.zzata = zzd2;
        this.zzktp = zzdjj2;
        this.zzkre = map;
    }
}

