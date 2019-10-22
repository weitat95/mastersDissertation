/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzcgv;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.internal.zzcls;
import com.google.android.gms.internal.zzclt;
import com.google.android.gms.internal.zzclu;
import com.google.android.gms.internal.zzclv;
import com.google.android.gms.internal.zzclw;
import com.google.android.gms.internal.zzcma;
import com.google.android.gms.internal.zzcmb;
import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzcmd;
import com.google.android.gms.internal.zzcme;
import com.google.android.gms.internal.zzcmf;
import com.google.android.gms.internal.zzcmg;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.Iterator;
import java.util.Set;

public final class zzchk
extends zzcjl {
    private static String[] zzjbq = new String[AppMeasurement.Event.zziwg.length];
    private static String[] zzjbr = new String[AppMeasurement.Param.zziwi.length];
    private static String[] zzjbs = new String[AppMeasurement.UserProperty.zziwn.length];

    zzchk(zzcim zzcim2) {
        super(zzcim2);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String zza(String charSequence, String[] arrstring, String[] arrstring2, String[] arrstring3) {
        boolean bl = true;
        int n = 0;
        zzbq.checkNotNull(arrstring);
        zzbq.checkNotNull(arrstring2);
        zzbq.checkNotNull(arrstring3);
        boolean bl2 = arrstring.length == arrstring2.length;
        zzbq.checkArgument(bl2);
        bl2 = arrstring.length == arrstring3.length ? bl : false;
        zzbq.checkArgument(bl2);
        while (n < arrstring.length) {
            if (zzclq.zzas((String)charSequence, arrstring[n])) {
                synchronized (arrstring3) {
                    if (arrstring3[n] != null) return arrstring3[n];
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(arrstring2[n]);
                    ((StringBuilder)charSequence).append("(");
                    ((StringBuilder)charSequence).append(arrstring[n]);
                    ((StringBuilder)charSequence).append(")");
                    arrstring3[n] = ((StringBuilder)charSequence).toString();
                    return arrstring3[n];
                }
            }
            ++n;
        }
        return charSequence;
    }

    private static void zza(StringBuilder stringBuilder, int n) {
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("  ");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(StringBuilder stringBuilder, int n, zzclt zzclt2) {
        if (zzclt2 == null) {
            return;
        }
        zzchk.zza(stringBuilder, n);
        stringBuilder.append("filter {\n");
        zzchk.zza(stringBuilder, n, "complement", zzclt2.zzjke);
        zzchk.zza(stringBuilder, n, "param_name", this.zzji(zzclt2.zzjkf));
        int n2 = n + 1;
        zzclw zzclw2 = zzclt2.zzjkc;
        if (zzclw2 != null) {
            zzchk.zza(stringBuilder, n2);
            stringBuilder.append("string_filter");
            stringBuilder.append(" {\n");
            if (zzclw2.zzjko != null) {
                String[] arrstring = "UNKNOWN_MATCH_TYPE";
                switch (zzclw2.zzjko) {
                    case 1: {
                        arrstring = "REGEXP";
                        break;
                    }
                    case 2: {
                        arrstring = "BEGINS_WITH";
                        break;
                    }
                    case 3: {
                        arrstring = "ENDS_WITH";
                        break;
                    }
                    case 4: {
                        arrstring = "PARTIAL";
                        break;
                    }
                    case 5: {
                        arrstring = "EXACT";
                        break;
                    }
                    case 6: {
                        arrstring = "IN_LIST";
                    }
                }
                zzchk.zza(stringBuilder, n2, "match_type", (Object)arrstring);
            }
            zzchk.zza(stringBuilder, n2, "expression", zzclw2.zzjkp);
            zzchk.zza(stringBuilder, n2, "case_sensitive", zzclw2.zzjkq);
            if (zzclw2.zzjkr.length > 0) {
                zzchk.zza(stringBuilder, n2 + 1);
                stringBuilder.append("expression_list {\n");
                for (String string2 : zzclw2.zzjkr) {
                    zzchk.zza(stringBuilder, n2 + 2);
                    stringBuilder.append(string2);
                    stringBuilder.append("\n");
                }
                stringBuilder.append("}\n");
            }
            zzchk.zza(stringBuilder, n2);
            stringBuilder.append("}\n");
        }
        this.zza(stringBuilder, n + 1, "number_filter", zzclt2.zzjkd);
        zzchk.zza(stringBuilder, n);
        stringBuilder.append("}\n");
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(StringBuilder stringBuilder, int n, String string2, zzclu zzclu2) {
        if (zzclu2 == null) {
            return;
        }
        zzchk.zza(stringBuilder, n);
        stringBuilder.append(string2);
        stringBuilder.append(" {\n");
        if (zzclu2.zzjkg != null) {
            string2 = "UNKNOWN_COMPARISON_TYPE";
            switch (zzclu2.zzjkg) {
                case 1: {
                    string2 = "LESS_THAN";
                    break;
                }
                case 2: {
                    string2 = "GREATER_THAN";
                    break;
                }
                case 3: {
                    string2 = "EQUAL";
                    break;
                }
                case 4: {
                    string2 = "BETWEEN";
                    break;
                }
            }
            zzchk.zza(stringBuilder, n, "comparison_type", string2);
        }
        zzchk.zza(stringBuilder, n, "match_as_float", zzclu2.zzjkh);
        zzchk.zza(stringBuilder, n, "comparison_value", zzclu2.zzjki);
        zzchk.zza(stringBuilder, n, "min_comparison_value", zzclu2.zzjkj);
        zzchk.zza(stringBuilder, n, "max_comparison_value", zzclu2.zzjkk);
        zzchk.zza(stringBuilder, n);
        stringBuilder.append("}\n");
    }

    private static void zza(StringBuilder stringBuilder, int n, String arrl, zzcmf zzcmf2) {
        int n2;
        long l;
        int n3;
        int n4 = 0;
        if (zzcmf2 == null) {
            return;
        }
        int n5 = n + 1;
        zzchk.zza(stringBuilder, n5);
        stringBuilder.append((String)arrl);
        stringBuilder.append(" {\n");
        if (zzcmf2.zzjmq != null) {
            zzchk.zza(stringBuilder, n5 + 1);
            stringBuilder.append("results: ");
            arrl = zzcmf2.zzjmq;
            n3 = arrl.length;
            n2 = 0;
            n = 0;
            while (n2 < n3) {
                l = arrl[n2];
                if (n != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append((Object)l);
                ++n2;
                ++n;
            }
            stringBuilder.append('\n');
        }
        if (zzcmf2.zzjmp != null) {
            zzchk.zza(stringBuilder, n5 + 1);
            stringBuilder.append("status: ");
            arrl = zzcmf2.zzjmp;
            n3 = arrl.length;
            n = 0;
            n2 = n4;
            while (n2 < n3) {
                l = arrl[n2];
                if (n != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append((Object)l);
                ++n2;
                ++n;
            }
            stringBuilder.append('\n');
        }
        zzchk.zza(stringBuilder, n5);
        stringBuilder.append("}\n");
    }

    private static void zza(StringBuilder stringBuilder, int n, String string2, Object object) {
        if (object == null) {
            return;
        }
        zzchk.zza(stringBuilder, n + 1);
        stringBuilder.append(string2);
        stringBuilder.append(": ");
        stringBuilder.append(object);
        stringBuilder.append('\n');
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(StringBuilder stringBuilder, int n, zzcma[] arrzzcma) {
        if (arrzzcma != null) {
            for (zzcma zzcma2 : arrzzcma) {
                if (zzcma2 == null) continue;
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("audience_membership {\n");
                zzchk.zza(stringBuilder, 2, "audience_id", zzcma2.zzjjs);
                zzchk.zza(stringBuilder, 2, "new_audience", zzcma2.zzjlf);
                zzchk.zza(stringBuilder, 2, "current_data", zzcma2.zzjld);
                zzchk.zza(stringBuilder, 2, "previous_data", zzcma2.zzjle);
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("}\n");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(StringBuilder stringBuilder, int n, zzcmb[] arrzzcmb) {
        if (arrzzcmb != null) {
            for (zzcmc[] arrzzcmc : arrzzcmb) {
                if (arrzzcmc == null) continue;
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("event {\n");
                zzchk.zza(stringBuilder, 2, "name", this.zzjh(arrzzcmc.name));
                zzchk.zza(stringBuilder, 2, "timestamp_millis", arrzzcmc.zzjli);
                zzchk.zza(stringBuilder, 2, "previous_timestamp_millis", arrzzcmc.zzjlj);
                zzchk.zza(stringBuilder, 2, "count", arrzzcmc.count);
                arrzzcmc = arrzzcmc.zzjlh;
                if (arrzzcmc != null) {
                    int n2 = arrzzcmc.length;
                    for (int i = 0; i < n2; ++i) {
                        zzcmc zzcmc2 = arrzzcmc[i];
                        if (zzcmc2 == null) continue;
                        zzchk.zza(stringBuilder, 3);
                        stringBuilder.append("param {\n");
                        zzchk.zza(stringBuilder, 3, "name", this.zzji(zzcmc2.name));
                        zzchk.zza(stringBuilder, 3, "string_value", zzcmc2.zzgcc);
                        zzchk.zza(stringBuilder, 3, "int_value", zzcmc2.zzjll);
                        zzchk.zza(stringBuilder, 3, "double_value", zzcmc2.zzjjl);
                        zzchk.zza(stringBuilder, 3);
                        stringBuilder.append("}\n");
                    }
                }
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("}\n");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(StringBuilder stringBuilder, int n, zzcmg[] arrzzcmg) {
        if (arrzzcmg != null) {
            for (zzcmg zzcmg2 : arrzzcmg) {
                if (zzcmg2 == null) continue;
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("user_property {\n");
                zzchk.zza(stringBuilder, 2, "set_timestamp_millis", zzcmg2.zzjms);
                zzchk.zza(stringBuilder, 2, "name", this.zzjj(zzcmg2.name));
                zzchk.zza(stringBuilder, 2, "string_value", zzcmg2.zzgcc);
                zzchk.zza(stringBuilder, 2, "int_value", zzcmg2.zzjll);
                zzchk.zza(stringBuilder, 2, "double_value", zzcmg2.zzjjl);
                zzchk.zza(stringBuilder, 2);
                stringBuilder.append("}\n");
            }
        }
    }

    private final boolean zzazc() {
        return this.zziwf.zzawy().zzae(3);
    }

    private final String zzb(zzcgx zzcgx2) {
        if (zzcgx2 == null) {
            return null;
        }
        if (!this.zzazc()) {
            return zzcgx2.toString();
        }
        return this.zzx(zzcgx2.zzayx());
    }

    protected final String zza(zzcgv zzcgv2) {
        if (zzcgv2 == null) {
            return null;
        }
        if (!this.zzazc()) {
            return zzcgv2.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Event{appId='");
        stringBuilder.append(zzcgv2.mAppId);
        stringBuilder.append("', name='");
        stringBuilder.append(this.zzjh(zzcgv2.mName));
        stringBuilder.append("', params=");
        stringBuilder.append(this.zzb(zzcgv2.zzizj));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    protected final String zza(zzcls arrzzclt) {
        if (arrzzclt == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nevent_filter {\n");
        zzchk.zza(stringBuilder, 0, "filter_id", arrzzclt.zzjjw);
        zzchk.zza(stringBuilder, 0, "event_name", this.zzjh(arrzzclt.zzjjx));
        this.zza(stringBuilder, 1, "event_count_filter", arrzzclt.zzjka);
        stringBuilder.append("  filters {\n");
        arrzzclt = arrzzclt.zzjjy;
        int n = arrzzclt.length;
        for (int i = 0; i < n; ++i) {
            this.zza(stringBuilder, 2, arrzzclt[i]);
        }
        zzchk.zza(stringBuilder, 1);
        stringBuilder.append("}\n}\n");
        return stringBuilder.toString();
    }

    protected final String zza(zzclv zzclv2) {
        if (zzclv2 == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nproperty_filter {\n");
        zzchk.zza(stringBuilder, 0, "filter_id", zzclv2.zzjjw);
        zzchk.zza(stringBuilder, 0, "property_name", this.zzjj(zzclv2.zzjkm));
        this.zza(stringBuilder, 1, zzclv2.zzjkn);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    protected final String zza(zzcmd arrzzcme) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nbatch {\n");
        if (arrzzcme.zzjlm != null) {
            for (zzcme zzcme2 : arrzzcme.zzjlm) {
                if (zzcme2 == null || zzcme2 == null) continue;
                zzchk.zza(stringBuilder, 1);
                stringBuilder.append("bundle {\n");
                zzchk.zza(stringBuilder, 1, "protocol_version", zzcme2.zzjlo);
                zzchk.zza(stringBuilder, 1, "platform", zzcme2.zzjlw);
                zzchk.zza(stringBuilder, 1, "gmp_version", zzcme2.zzjma);
                zzchk.zza(stringBuilder, 1, "uploading_gmp_version", zzcme2.zzjmb);
                zzchk.zza(stringBuilder, 1, "config_version", zzcme2.zzjmn);
                zzchk.zza(stringBuilder, 1, "gmp_app_id", zzcme2.zzixs);
                zzchk.zza(stringBuilder, 1, "app_id", zzcme2.zzcn);
                zzchk.zza(stringBuilder, 1, "app_version", zzcme2.zzifm);
                zzchk.zza(stringBuilder, 1, "app_version_major", zzcme2.zzjmj);
                zzchk.zza(stringBuilder, 1, "firebase_instance_id", zzcme2.zziya);
                zzchk.zza(stringBuilder, 1, "dev_cert_hash", zzcme2.zzjmf);
                zzchk.zza(stringBuilder, 1, "app_store", zzcme2.zzixt);
                zzchk.zza(stringBuilder, 1, "upload_timestamp_millis", zzcme2.zzjlr);
                zzchk.zza(stringBuilder, 1, "start_timestamp_millis", zzcme2.zzjls);
                zzchk.zza(stringBuilder, 1, "end_timestamp_millis", zzcme2.zzjlt);
                zzchk.zza(stringBuilder, 1, "previous_bundle_start_timestamp_millis", zzcme2.zzjlu);
                zzchk.zza(stringBuilder, 1, "previous_bundle_end_timestamp_millis", zzcme2.zzjlv);
                zzchk.zza(stringBuilder, 1, "app_instance_id", zzcme2.zzjme);
                zzchk.zza(stringBuilder, 1, "resettable_device_id", zzcme2.zzjmc);
                zzchk.zza(stringBuilder, 1, "device_id", zzcme2.zzjmm);
                zzchk.zza(stringBuilder, 1, "limited_ad_tracking", zzcme2.zzjmd);
                zzchk.zza(stringBuilder, 1, "os_version", zzcme2.zzdb);
                zzchk.zza(stringBuilder, 1, "device_model", zzcme2.zzjlx);
                zzchk.zza(stringBuilder, 1, "user_default_language", zzcme2.zzjly);
                zzchk.zza(stringBuilder, 1, "time_zone_offset_minutes", zzcme2.zzjlz);
                zzchk.zza(stringBuilder, 1, "bundle_sequential_index", zzcme2.zzjmg);
                zzchk.zza(stringBuilder, 1, "service_upload", zzcme2.zzjmh);
                zzchk.zza(stringBuilder, 1, "health_monitor", zzcme2.zzixw);
                if (zzcme2.zzfkk != 0L) {
                    zzchk.zza(stringBuilder, 1, "android_id", zzcme2.zzfkk);
                }
                this.zza(stringBuilder, 1, zzcme2.zzjlq);
                this.zza(stringBuilder, 1, zzcme2.zzjmi);
                this.zza(stringBuilder, 1, zzcme2.zzjlp);
                zzchk.zza(stringBuilder, 1);
                stringBuilder.append("}\n");
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    protected final String zzb(zzcha zzcha2) {
        if (zzcha2 == null) {
            return null;
        }
        if (!this.zzazc()) {
            return zzcha2.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin=");
        stringBuilder.append(zzcha2.zziyf);
        stringBuilder.append(",name=");
        stringBuilder.append(this.zzjh(zzcha2.name));
        stringBuilder.append(",params=");
        stringBuilder.append(this.zzb(zzcha2.zzizt));
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String zzjh(String string2) {
        if (string2 == null) {
            return null;
        }
        String string3 = string2;
        if (!this.zzazc()) return string3;
        return zzchk.zza(string2, AppMeasurement.Event.zziwh, AppMeasurement.Event.zziwg, zzjbq);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String zzji(String string2) {
        if (string2 == null) {
            return null;
        }
        String string3 = string2;
        if (!this.zzazc()) return string3;
        return zzchk.zza(string2, AppMeasurement.Param.zziwj, AppMeasurement.Param.zziwi, zzjbr);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String zzjj(String string2) {
        if (string2 == null) {
            return null;
        }
        CharSequence charSequence = string2;
        if (!this.zzazc()) return charSequence;
        if (!string2.startsWith("_exp_")) return zzchk.zza(string2, AppMeasurement.UserProperty.zziwo, AppMeasurement.UserProperty.zziwn, zzjbs);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("experiment_id");
        ((StringBuilder)charSequence).append("(");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(")");
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final String zzx(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!this.zzazc()) {
            return bundle.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator = bundle.keySet().iterator();
        do {
            if (!iterator.hasNext()) {
                stringBuilder.append("}]");
                return stringBuilder.toString();
            }
            String string2 = (String)iterator.next();
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append("Bundle[{");
            }
            stringBuilder.append(this.zzji(string2));
            stringBuilder.append("=");
            stringBuilder.append(bundle.get(string2));
        } while (true);
    }
}

