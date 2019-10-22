/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzcx;
import com.google.android.gms.tagmanager.zzgk;
import java.util.Map;

final class zzh
extends zzbr {
    private static final String ID = zzbg.zzja.toString();
    private static final String zzkcv = zzbh.zzob.toString();
    private static final String zzkcw = zzbh.zzoe.toString();
    private final Context zzair;

    public zzh(Context context) {
        super(ID, zzkcw);
        this.zzair = context;
    }

    @Override
    public final boolean zzbdp() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final zzbs zzv(Map<String, zzbs> object) {
        Object object2 = (zzbs)object.get(zzkcw);
        if (object2 == null) {
            return zzgk.zzbgs();
        }
        String string2 = zzgk.zzb((zzbs)object2);
        object2 = (object = (zzbs)object.get(zzkcv)) != null ? zzgk.zzb((zzbs)object) : null;
        Context context = this.zzair;
        String string3 = zzcx.zzkgf.get(string2);
        object = string3;
        if (string3 == null) {
            object = context.getSharedPreferences("gtm_click_referrers", 0);
            object = object != null ? object.getString(string2, "") : "";
            zzcx.zzkgf.put(string2, (String)object);
        }
        if ((object = zzcx.zzax((String)object, (String)object2)) != null) {
            return zzgk.zzam(object);
        }
        return zzgk.zzbgs();
    }
}

