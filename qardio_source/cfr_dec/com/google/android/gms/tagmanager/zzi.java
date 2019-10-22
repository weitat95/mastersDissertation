/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzi
extends zzbr {
    private static final String ID = zzbg.zzhv.toString();
    private final Context mContext;

    public zzi(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    @Override
    public final zzbs zzv(Map<String, zzbs> map) {
        return zzgk.zzam(this.mContext.getPackageName());
    }
}

