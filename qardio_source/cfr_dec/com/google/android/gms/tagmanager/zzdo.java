/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tagmanager.zzfn;

class zzdo
extends BroadcastReceiver {
    private static String zzdyg = zzdo.class.getName();
    private final zzfn zzkgy;

    zzdo(zzfn zzfn2) {
        this.zzkgy = zzfn2;
    }

    public static void zzej(Context context) {
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzdyg, true);
        context.sendBroadcast(intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context object, Intent object2) {
        object = object2.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            Bundle bundle = object2.getExtras();
            object = Boolean.FALSE;
            if (bundle != null) {
                object = object2.getExtras().getBoolean("noConnectivity");
            }
            object2 = this.zzkgy;
            boolean bl = !((Boolean)object).booleanValue();
            ((zzfn)object2).zzbv(bl);
            return;
        } else {
            if (!"com.google.analytics.RADIO_POWERED".equals(object) || object2.hasExtra(zzdyg)) return;
            {
                this.zzkgy.zzbgf();
                return;
            }
        }
    }
}

