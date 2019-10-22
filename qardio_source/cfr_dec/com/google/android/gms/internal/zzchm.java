/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchn;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchp;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcib;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzchm
extends zzcjl {
    private final String zzgay = zzchc.zzjad.get();
    private final long zzixd;
    private final char zzjbt = ((zzcjk)this).zzaxa().zzyp() ? (char)67 : (char)99;
    private final zzcho zzjbu = new zzcho(this, 6, false, false);
    private final zzcho zzjbv = new zzcho(this, 6, true, false);
    private final zzcho zzjbw = new zzcho(this, 6, false, true);
    private final zzcho zzjbx = new zzcho(this, 5, false, false);
    private final zzcho zzjby = new zzcho(this, 5, true, false);
    private final zzcho zzjbz = new zzcho(this, 5, false, true);
    private final zzcho zzjca = new zzcho(this, 4, false, false);
    private final zzcho zzjcb = new zzcho(this, 3, false, false);
    private final zzcho zzjcc = new zzcho(this, 2, false, false);

    /*
     * Enabled aggressive block sorting
     */
    zzchm(zzcim zzcim2) {
        super(zzcim2);
        this.zzixd = 11910L;
    }

    private static String zza(boolean bl, String object, Object object2, Object object3, Object object4) {
        String string2 = object;
        if (object == null) {
            string2 = "";
        }
        String string3 = zzchm.zzb(bl, object2);
        object3 = zzchm.zzb(bl, object3);
        object4 = zzchm.zzb(bl, object4);
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

    /*
     * Enabled aggressive block sorting
     */
    private static String zzb(boolean bl, Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof Integer) {
            object = (long)((Integer)object).intValue();
        }
        if (object instanceof Long) {
            if (!bl) {
                return String.valueOf(object);
            }
            if (Math.abs((Long)object) < 100L) {
                return String.valueOf(object);
            }
            String string2 = String.valueOf(object).charAt(0) == '-' ? "-" : "";
            object = String.valueOf(Math.abs((Long)object));
            long l = Math.round(Math.pow(10.0, ((String)object).length() - 1));
            long l2 = Math.round(Math.pow(10.0, ((String)object).length()) - 1.0);
            return new StringBuilder(String.valueOf(string2).length() + 43 + String.valueOf(string2).length()).append(string2).append(l).append("...").append(string2).append(l2).toString();
        }
        if (object instanceof Boolean) {
            return String.valueOf(object);
        }
        if (!(object instanceof Throwable)) {
            if (object instanceof zzchp) {
                return zzchp.zza((zzchp)object);
            }
            if (bl) {
                return "-";
            }
            return String.valueOf(object);
        }
        StackTraceElement[] arrstackTraceElement = (StackTraceElement[])object;
        object = bl ? arrstackTraceElement.getClass().getName() : arrstackTraceElement.toString();
        object = new StringBuilder((String)object);
        String string3 = zzchm.zzjl(AppMeasurement.class.getCanonicalName());
        String string4 = zzchm.zzjl(zzcim.class.getCanonicalName());
        arrstackTraceElement = arrstackTraceElement.getStackTrace();
        int n = arrstackTraceElement.length;
        int n2 = 0;
        do {
            block14: {
                block13: {
                    String string5;
                    if (n2 >= n) break block13;
                    StackTraceElement stackTraceElement = arrstackTraceElement[n2];
                    if (stackTraceElement.isNativeMethod() || (string5 = stackTraceElement.getClassName()) == null || !(string5 = zzchm.zzjl(string5)).equals(string3) && !string5.equals(string4)) break block14;
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append(stackTraceElement);
                }
                return ((StringBuilder)object).toString();
            }
            ++n2;
        } while (true);
    }

    protected static Object zzjk(String string2) {
        if (string2 == null) {
            return null;
        }
        return new zzchp(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String zzjl(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return "";
        }
        int n = string2.lastIndexOf(46);
        String string3 = string2;
        if (n == -1) return string3;
        return string2.substring(0, n);
    }

    protected final void zza(int n, boolean bl, boolean bl2, String string2, Object object, Object object2, Object object3) {
        zzcih zzcih2;
        block9: {
            block8: {
                if (!bl && this.zzae(n)) {
                    this.zzk(n, zzchm.zza(false, string2, object, object2, object3));
                }
                if (bl2 || n < 5) break block8;
                zzbq.checkNotNull(string2);
                zzcih2 = this.zziwf.zzazy();
                if (zzcih2 != null) break block9;
                this.zzk(6, "Scheduler not set. Not logging error/warn");
            }
            return;
        }
        if (!zzcih2.isInitialized()) {
            this.zzk(6, "Scheduler not initialized. Not logging error/warn");
            return;
        }
        if (n < 0) {
            n = 0;
        }
        int n2 = n;
        if (n >= 9) {
            n2 = 8;
        }
        char c = "01VDIWEA?".charAt(n2);
        char c2 = this.zzjbt;
        long l = this.zzixd;
        object = zzchm.zza(true, string2, object, object2, object3);
        object = object2 = new StringBuilder(String.valueOf("2").length() + 23 + String.valueOf(object).length()).append("2").append(c).append(c2).append(l).append(":").append((String)object).toString();
        if (((String)object2).length() > 1024) {
            object = string2.substring(0, 1024);
        }
        zzcih2.zzg(new zzchn(this, (String)object));
    }

    protected final boolean zzae(int n) {
        return Log.isLoggable((String)this.zzgay, (int)n);
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final zzcho zzazd() {
        return this.zzjbu;
    }

    public final zzcho zzaze() {
        return this.zzjbv;
    }

    public final zzcho zzazf() {
        return this.zzjbx;
    }

    public final zzcho zzazg() {
        return this.zzjbz;
    }

    public final zzcho zzazh() {
        return this.zzjca;
    }

    public final zzcho zzazi() {
        return this.zzjcb;
    }

    public final zzcho zzazj() {
        return this.zzjcc;
    }

    public final String zzazk() {
        Object object = this.zzawz().zzjcq.zzaad();
        if (object == null || object == zzchx.zzjcp) {
            return null;
        }
        String string2 = String.valueOf(object.second);
        object = (String)object.first;
        return new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(object).length()).append(string2).append(":").append((String)object).toString();
    }

    protected final void zzk(int n, String string2) {
        Log.println((int)n, (String)this.zzgay, (String)string2);
    }
}

