/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.tagmanager.zzfu;
import java.util.HashMap;
import java.util.Map;

public class zzcx {
    private static String zzkge;
    static Map<String, String> zzkgf;

    static {
        zzkgf = new HashMap<String, String>();
    }

    static void zzai(Context context, String string2) {
        zzfu.zze(context, "gtm_install_referrer", "referrer", string2);
        zzcx.zzak(context, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zzaj(Context context, String string2) {
        if (zzkge == null) {
            synchronized (zzcx.class) {
                if (zzkge == null) {
                    zzkge = (context = context.getSharedPreferences("gtm_install_referrer", 0)) != null ? context.getString("referrer", "") : "";
                }
            }
        }
        return zzcx.zzax(zzkge, string2);
    }

    public static void zzak(Context context, String string2) {
        String string3 = zzcx.zzax(string2, "conv");
        if (string3 != null && string3.length() > 0) {
            zzkgf.put(string3, string2);
            zzfu.zze(context, "gtm_click_referrers", string3, string2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String zzax(String string2, String string3) {
        if (string3 == null) {
            if (string2.length() <= 0) return null;
            return string2;
        }
        if ((string2 = String.valueOf(string2)).length() != 0) {
            string2 = "http://hostname/?".concat(string2);
            do {
                return Uri.parse((String)string2).getQueryParameter(string3);
                break;
            } while (true);
        }
        string2 = new String("http://hostname/?");
        return Uri.parse((String)string2).getQueryParameter(string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzlr(String string2) {
        synchronized (zzcx.class) {
            zzkge = string2;
            return;
        }
    }
}

