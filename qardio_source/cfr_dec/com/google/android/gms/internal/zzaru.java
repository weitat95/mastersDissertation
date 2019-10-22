/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzare;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarv;

@Deprecated
public final class zzaru {
    private static volatile Logger zzdye = new zzare();

    /*
     * Enabled aggressive block sorting
     */
    public static void v(String string2) {
        Object object = zzarv.zzzo();
        if (object != null) {
            ((zzapz)object).zzdu(string2);
        } else if (zzaru.zzae(0)) {
            Log.v((String)zzarl.zzdvy.get(), (String)string2);
        }
        if ((object = zzdye) != null) {
            object.verbose(string2);
        }
    }

    private static boolean zzae(int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (zzdye != null) {
            bl2 = bl;
            if (zzdye.getLogLevel() <= n) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zzcu(String string2) {
        Object object = zzarv.zzzo();
        if (object != null) {
            ((zzapz)object).zzdx(string2);
        } else if (zzaru.zzae(2)) {
            Log.w((String)zzarl.zzdvy.get(), (String)string2);
        }
        if ((object = zzdye) != null) {
            object.warn(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zzf(String string2, Object object) {
        zzarv zzarv2 = zzarv.zzzo();
        if (zzarv2 != null) {
            zzarv2.zze(string2, object);
        } else if (zzaru.zzae(3)) {
            if (object != null) {
                object = String.valueOf(object);
                object = new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(object).length()).append(string2).append(":").append((String)object).toString();
            } else {
                object = string2;
            }
            Log.e((String)zzarl.zzdvy.get(), (String)object);
        }
        if ((object = zzdye) != null) {
            object.error(string2);
        }
    }
}

