/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqb;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarz;
import com.google.android.gms.internal.zzasb;
import java.util.Map;
import java.util.Set;

public class zzarv
extends zzaqa {
    private static zzarv zzdyf;

    public zzarv(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzo(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            object = (long)((Integer)object).intValue();
        }
        if (object instanceof Long) {
            if (Math.abs((Long)object) < 100L) {
                return String.valueOf(object);
            }
            String string2 = String.valueOf(object).charAt(0) == '-' ? "-" : "";
            object = String.valueOf(Math.abs((Long)object));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(Math.round(Math.pow(10.0, ((String)object).length() - 1)));
            stringBuilder.append("...");
            stringBuilder.append(string2);
            stringBuilder.append(Math.round(Math.pow(10.0, ((String)object).length()) - 1.0));
            return stringBuilder.toString();
        }
        if (object instanceof Boolean) {
            return String.valueOf(object);
        }
        if (object instanceof Throwable) {
            return object.getClass().getCanonicalName();
        }
        return "-";
    }

    public static zzarv zzzo() {
        return zzdyf;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(zzarq object, String string2) {
        object = object != null ? ((zzarq)object).toString() : "no hit data";
        string2 = (string2 = String.valueOf(string2)).length() != 0 ? "Discarding hit. ".concat(string2) : new String("Discarding hit. ");
        this.zzd(string2, object);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzb(int n, String object, Object object2, Object object3, Object object4) {
        int n2 = 0;
        synchronized (this) {
            void var4_4;
            Object object5;
            void var5_5;
            zzbq.checkNotNull(object);
            if (n < 0) {
                n = n2;
            }
            if (n >= 9) {
                n = 8;
            }
            char c = this.zzwu().zzyp() ? (char)'C' : 'c';
            char c2 = "01VDIWEA?".charAt(n);
            String string2 = zzaqb.VERSION;
            object = zzarv.zzc((String)object, zzarv.zzo(object5), zzarv.zzo(var4_4), zzarv.zzo(var5_5));
            object = object5 = new StringBuilder(String.valueOf("3").length() + 3 + String.valueOf(string2).length() + String.valueOf(object).length()).append("3").append(c2).append(c).append(string2).append(":").append((String)object).toString();
            if (((String)object5).length() > 1024) {
                object = ((String)object5).substring(0, 1024);
            }
            if ((object5 = this.zzwr().zzxj()) != null) {
                ((zzarz)object5).zzaab().zzeg((String)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzf(Map<String, String> object, String string2) {
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry entry : object.entrySet()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append((String)entry.getKey());
                stringBuilder.append('=');
                stringBuilder.append((String)entry.getValue());
            }
            object = stringBuilder.toString();
        } else {
            object = "no hit data";
        }
        string2 = (string2 = String.valueOf(string2)).length() != 0 ? "Discarding hit. ".concat(string2) : new String("Discarding hit. ");
        this.zzd(string2, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void zzvf() {
        synchronized (zzarv.class) {
            zzdyf = this;
            return;
        }
    }
}

