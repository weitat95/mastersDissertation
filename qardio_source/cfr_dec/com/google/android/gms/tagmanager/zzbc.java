/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 */
package com.google.android.gms.tagmanager;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzbc
extends zzbr {
    private static final String ID = zzbg.zzjb.toString();
    private final Context mContext;

    public zzbc(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    @Override
    public final zzbs zzv(Map<String, zzbs> object) {
        object = Settings.Secure.getString((ContentResolver)this.mContext.getContentResolver(), (String)"android_id");
        if (object == null) {
            return zzgk.zzbgs();
        }
        return zzgk.zzam(object);
    }
}

