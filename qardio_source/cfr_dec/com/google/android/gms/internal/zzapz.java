/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarh;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzarz;
import com.google.android.gms.internal.zzasm;

public class zzapz {
    private final zzaqc zzdta;

    protected zzapz(zzaqc zzaqc2) {
        zzbq.checkNotNull(zzaqc2);
        this.zzdta = zzaqc2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(int n, String string2, Object object, Object object2, Object object3) {
        Object object4 = null;
        if (this.zzdta != null) {
            object4 = this.zzdta.zzxh();
        }
        if (object4 != null) {
            String string3 = zzarl.zzdvy.get();
            if (Log.isLoggable((String)string3, (int)n)) {
                Log.println((int)n, (String)string3, (String)zzarv.zzc(string2, object, object2, object3));
            }
            if (n < 5) return;
            {
                ((zzarv)object4).zzb(n, string2, object, object2, object3);
                return;
            }
        } else {
            object4 = zzarl.zzdvy.get();
            if (!Log.isLoggable((String)object4, (int)n)) return;
            {
                Log.println((int)n, (String)object4, (String)zzapz.zzc(string2, object, object2, object3));
                return;
            }
        }
    }

    protected static String zzc(String object, Object object2, Object object3, Object object4) {
        String string2 = object;
        if (object == null) {
            string2 = "";
        }
        String string3 = zzapz.zzm(object2);
        object3 = zzapz.zzm(object3);
        object4 = zzapz.zzm(object4);
        StringBuilder stringBuilder = new StringBuilder();
        object = "";
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            stringBuilder.append(string2);
            object = ": ";
        }
        object2 = object;
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            stringBuilder.append((String)object);
            stringBuilder.append(string3);
            object2 = ", ";
        }
        object = object2;
        if (!TextUtils.isEmpty((CharSequence)object3)) {
            stringBuilder.append((String)object2);
            stringBuilder.append((String)object3);
            object = ", ";
        }
        if (!TextUtils.isEmpty((CharSequence)object4)) {
            stringBuilder.append((String)object);
            stringBuilder.append((String)object4);
        }
        return stringBuilder.toString();
    }

    private static String zzm(Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof Boolean) {
            if (object == Boolean.TRUE) {
                return "true";
            }
            return "false";
        }
        if (object instanceof Throwable) {
            return ((Throwable)object).toString();
        }
        return object.toString();
    }

    public static boolean zzpz() {
        return Log.isLoggable((String)zzarl.zzdvy.get(), (int)2);
    }

    protected final Context getContext() {
        return this.zzdta.getContext();
    }

    public final void zza(String string2, Object object) {
        this.zza(2, string2, object, null, null);
    }

    public final void zza(String string2, Object object, Object object2) {
        this.zza(2, string2, object, object2, null);
    }

    public final void zza(String string2, Object object, Object object2, Object object3) {
        this.zza(3, string2, object, object2, object3);
    }

    public final void zzb(String string2, Object object) {
        this.zza(3, string2, object, null, null);
    }

    public final void zzb(String string2, Object object, Object object2) {
        this.zza(3, string2, object, object2, null);
    }

    public final void zzb(String string2, Object object, Object object2, Object object3) {
        this.zza(5, string2, object, object2, object3);
    }

    public final void zzc(String string2, Object object) {
        this.zza(4, string2, object, null, null);
    }

    public final void zzc(String string2, Object object, Object object2) {
        this.zza(5, string2, object, object2, null);
    }

    public final void zzd(String string2, Object object) {
        this.zza(5, string2, object, null, null);
    }

    public final void zzd(String string2, Object object, Object object2) {
        this.zza(6, string2, object, object2, null);
    }

    public final void zzdu(String string2) {
        this.zza(2, string2, null, null, null);
    }

    public final void zzdv(String string2) {
        this.zza(3, string2, null, null, null);
    }

    public final void zzdw(String string2) {
        this.zza(4, string2, null, null, null);
    }

    public final void zzdx(String string2) {
        this.zza(5, string2, null, null, null);
    }

    public final void zzdy(String string2) {
        this.zza(6, string2, null, null, null);
    }

    public final void zze(String string2, Object object) {
        this.zza(6, string2, object, null, null);
    }

    public final zzaqc zzwr() {
        return this.zzdta;
    }

    protected final zzd zzws() {
        return this.zzdta.zzws();
    }

    protected final zzarv zzwt() {
        return this.zzdta.zzwt();
    }

    protected final zzard zzwu() {
        return this.zzdta.zzwu();
    }

    protected final zzj zzwv() {
        return this.zzdta.zzwv();
    }

    public final GoogleAnalytics zzww() {
        return this.zzdta.zzxi();
    }

    protected final zzapr zzwx() {
        return this.zzdta.zzwx();
    }

    protected final zzari zzwy() {
        return this.zzdta.zzwy();
    }

    protected final zzasm zzwz() {
        return this.zzdta.zzwz();
    }

    protected final zzarz zzxa() {
        return this.zzdta.zzxa();
    }

    protected final zzaqu zzxb() {
        return this.zzdta.zzxl();
    }

    protected final zzapq zzxc() {
        return this.zzdta.zzxk();
    }

    protected final zzaqn zzxd() {
        return this.zzdta.zzxd();
    }

    protected final zzarh zzxe() {
        return this.zzdta.zzxe();
    }
}

