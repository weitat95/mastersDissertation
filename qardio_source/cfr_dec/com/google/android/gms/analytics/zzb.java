/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzd;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzape;
import com.google.android.gms.internal.zzapf;
import com.google.android.gms.internal.zzapg;
import com.google.android.gms.internal.zzaph;
import com.google.android.gms.internal.zzapi;
import com.google.android.gms.internal.zzapj;
import com.google.android.gms.internal.zzapk;
import com.google.android.gms.internal.zzapl;
import com.google.android.gms.internal.zzapm;
import com.google.android.gms.internal.zzapn;
import com.google.android.gms.internal.zzapo;
import com.google.android.gms.internal.zzapp;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqb;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqf;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasl;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzb
extends zzapz
implements zzm {
    private static DecimalFormat zzdol;
    private final zzaqc zzdoh;
    private final String zzdom;
    private final Uri zzdon;

    public zzb(zzaqc zzaqc2, String string2) {
        this(zzaqc2, string2, true, false);
    }

    private zzb(zzaqc zzaqc2, String string2, boolean bl, boolean bl2) {
        super(zzaqc2);
        zzbq.zzgm(string2);
        this.zzdoh = zzaqc2;
        this.zzdom = string2;
        this.zzdon = zzb.zzdf(this.zzdom);
    }

    private static void zza(Map<String, String> map, String string2, double d) {
        if (d != 0.0) {
            map.put(string2, zzb.zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String string2, int n, int n2) {
        if (n > 0 && n2 > 0) {
            map.put(string2, new StringBuilder(23).append(n).append("x").append(n2).toString());
        }
    }

    private static void zza(Map<String, String> map, String string2, String string3) {
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            map.put(string2, string3);
        }
    }

    private static void zza(Map<String, String> map, String string2, boolean bl) {
        if (bl) {
            map.put(string2, "1");
        }
    }

    private static String zzb(double d) {
        if (zzdol == null) {
            zzdol = new DecimalFormat("0.######");
        }
        return zzdol.format(d);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Map<String, String> zzc(zzg object) {
        Object object2;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Object object3 = ((zzg)object).zza(zzaph.class);
        if (object3 != null) {
            for (Map.Entry<String, Object> entry : ((zzaph)object3).zzvt().entrySet()) {
                object3 = entry.getValue();
                if (object3 == null) {
                    object3 = null;
                } else if (object3 instanceof String) {
                    object2 = (String)object3;
                    object3 = object2;
                    if (TextUtils.isEmpty((CharSequence)object2)) {
                        object3 = null;
                    }
                } else {
                    object3 = object3 instanceof Double ? ((Double)(object3 = (Double)object3) != 0.0 ? zzb.zzb((Double)object3) : null) : (object3 instanceof Boolean ? (object3 != Boolean.FALSE ? "1" : null) : String.valueOf(object3));
                }
                if (object3 == null) continue;
                hashMap.put(entry.getKey(), (String)object3);
            }
        }
        if ((object3 = ((zzg)object).zza(zzapm.class)) != null) {
            zzb.zza(hashMap, "t", ((zzapm)object3).zzvy());
            zzb.zza(hashMap, "cid", ((zzapm)object3).zzvz());
            zzb.zza(hashMap, "uid", ((zzapm)object3).getUserId());
            zzb.zza(hashMap, "sc", ((zzapm)object3).zzwc());
            zzb.zza(hashMap, "sf", ((zzapm)object3).zzwe());
            zzb.zza(hashMap, "ni", ((zzapm)object3).zzwd());
            zzb.zza(hashMap, "adid", ((zzapm)object3).zzwa());
            zzb.zza(hashMap, "ate", ((zzapm)object3).zzwb());
        }
        if ((object3 = ((zzg)object).zza(zzapn.class)) != null) {
            zzb.zza(hashMap, "cd", ((zzapn)object3).zzwf());
            zzb.zza(hashMap, "a", ((zzapn)object3).zzwg());
            zzb.zza(hashMap, "dr", ((zzapn)object3).zzwh());
        }
        if ((object3 = ((zzg)object).zza(zzapk.class)) != null) {
            zzb.zza(hashMap, "ec", ((zzapk)object3).getCategory());
            zzb.zza(hashMap, "ea", ((zzapk)object3).getAction());
            zzb.zza(hashMap, "el", ((zzapk)object3).getLabel());
            zzb.zza(hashMap, "ev", ((zzapk)object3).getValue());
        }
        if ((object3 = ((zzg)object).zza(zzape.class)) != null) {
            zzb.zza(hashMap, "cn", ((zzape)object3).getName());
            zzb.zza(hashMap, "cs", ((zzape)object3).getSource());
            zzb.zza(hashMap, "cm", ((zzape)object3).zzvl());
            zzb.zza(hashMap, "ck", ((zzape)object3).zzvm());
            zzb.zza(hashMap, "cc", ((zzape)object3).getContent());
            zzb.zza(hashMap, "ci", ((zzape)object3).getId());
            zzb.zza(hashMap, "anid", ((zzape)object3).zzvn());
            zzb.zza(hashMap, "gclid", ((zzape)object3).zzvo());
            zzb.zza(hashMap, "dclid", ((zzape)object3).zzvp());
            zzb.zza(hashMap, "aclid", ((zzape)object3).zzvq());
        }
        if ((object3 = ((zzg)object).zza(zzapl.class)) != null) {
            zzb.zza(hashMap, "exd", ((zzapl)object3).zzdrs);
            zzb.zza(hashMap, "exf", ((zzapl)object3).zzdrt);
        }
        if ((object3 = ((zzg)object).zza(zzapo.class)) != null) {
            zzb.zza(hashMap, "sn", ((zzapo)object3).zzdsh);
            zzb.zza(hashMap, "sa", ((zzapo)object3).zzdrp);
            zzb.zza(hashMap, "st", ((zzapo)object3).zzdsi);
        }
        if ((object3 = ((zzg)object).zza(zzapp.class)) != null) {
            zzb.zza(hashMap, "utv", ((zzapp)object3).zzdsj);
            zzb.zza(hashMap, "utt", ((zzapp)object3).zzdsk);
            zzb.zza(hashMap, "utc", ((zzapp)object3).mCategory);
            zzb.zza(hashMap, "utl", ((zzapp)object3).zzdrq);
        }
        if ((object3 = ((zzg)object).zza(zzapf.class)) != null) {
            object3 = ((zzapf)object3).zzvr().entrySet().iterator();
            while (object3.hasNext()) {
                object2 = (Map.Entry)object3.next();
                String string2 = zzd.zzaj((Integer)object2.getKey());
                if (TextUtils.isEmpty((CharSequence)string2)) continue;
                hashMap.put(string2, (String)object2.getValue());
            }
        }
        if ((object3 = ((zzg)object).zza(zzapg.class)) != null) {
            object3 = ((zzapg)object3).zzvs().entrySet().iterator();
            while (object3.hasNext()) {
                object2 = (Map.Entry)object3.next();
                String string3 = zzd.zzal((Integer)object2.getKey());
                if (TextUtils.isEmpty((CharSequence)string3)) continue;
                hashMap.put(string3, zzb.zzb((Double)object2.getValue()));
            }
        }
        if ((object3 = ((zzg)object).zza(zzapj.class)) != null) {
            object2 = ((zzapj)object3).zzvu();
            if (object2 != null) {
                for (Map.Entry entry : ((ProductAction)object2).build().entrySet()) {
                    if (((String)entry.getKey()).startsWith("&")) {
                        hashMap.put(((String)entry.getKey()).substring(1), (String)entry.getValue());
                        continue;
                    }
                    hashMap.put((String)entry.getKey(), (String)entry.getValue());
                }
            }
            object2 = ((zzapj)object3).zzvx().iterator();
            int n = 1;
            while (object2.hasNext()) {
                hashMap.putAll(((Promotion)object2.next()).zzdr(zzd.zzap(n)));
                ++n;
            }
            object2 = ((zzapj)object3).zzvv().iterator();
            n = 1;
            while (object2.hasNext()) {
                hashMap.putAll(((Product)object2.next()).zzdr(zzd.zzan(n)));
                ++n;
            }
            object2 = ((zzapj)object3).zzvw().entrySet().iterator();
            n = 1;
            while (object2.hasNext()) {
                Map.Entry entry = (Map.Entry)object2.next();
                object3 = (List)entry.getValue();
                String string4 = zzd.zzas(n);
                Iterator iterator = object3.iterator();
                int n2 = 1;
                while (iterator.hasNext()) {
                    Product product = (Product)iterator.next();
                    object3 = String.valueOf(string4);
                    String string5 = String.valueOf(zzd.zzaq(n2));
                    object3 = string5.length() != 0 ? ((String)object3).concat(string5) : new String((String)object3);
                    hashMap.putAll(product.zzdr((String)object3));
                    ++n2;
                }
                if (!TextUtils.isEmpty((CharSequence)((CharSequence)entry.getKey()))) {
                    object3 = String.valueOf(string4);
                    String string6 = String.valueOf("nm");
                    object3 = string6.length() != 0 ? ((String)object3).concat(string6) : new String((String)object3);
                    hashMap.put((String)object3, (String)entry.getKey());
                }
                ++n;
            }
        }
        if ((object3 = ((zzg)object).zza(zzapi.class)) != null) {
            zzb.zza(hashMap, "ul", ((zzapi)object3).getLanguage());
            zzb.zza(hashMap, "sd", ((zzapi)object3).zzdrm);
            zzb.zza(hashMap, "sr", ((zzapi)object3).zzchl, ((zzapi)object3).zzchm);
            zzb.zza(hashMap, "vp", ((zzapi)object3).zzdrn, ((zzapi)object3).zzdro);
        }
        if ((object = ((zzg)object).zza(zzapd.class)) != null) {
            zzb.zza(hashMap, "an", ((zzapd)object).zzvi());
            zzb.zza(hashMap, "aid", ((zzapd)object).getAppId());
            zzb.zza(hashMap, "aiid", ((zzapd)object).zzvk());
            zzb.zza(hashMap, "av", ((zzapd)object).zzvj());
        }
        return hashMap;
    }

    static Uri zzdf(String string2) {
        zzbq.zzgm(string2);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(string2);
        return builder.build();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzb(zzg object) {
        zzbq.checkNotNull(object);
        zzbq.checkArgument(((zzg)object).zzuw(), "Can't deliver not submitted measurement");
        zzbq.zzgn("deliver should be called on worker thread");
        Iterator<Map.Entry<String, String>> iterator = ((zzg)object).zzus();
        zzapm zzapm2 = ((zzg)((Object)iterator)).zzb(zzapm.class);
        if (TextUtils.isEmpty((CharSequence)zzapm2.zzvy())) {
            this.zzwt().zzf(zzb.zzc((zzg)((Object)iterator)), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty((CharSequence)zzapm2.zzvz())) {
            this.zzwt().zzf(zzb.zzc((zzg)((Object)iterator)), "Ignoring measurement without client id");
            return;
        }
        if (this.zzdoh.zzxi().getAppOptOut()) return;
        {
            double d = zzapm2.zzwe();
            if (zzasl.zza(d, zzapm2.zzvz())) {
                this.zzb("Sampling enabled. Hit sampled out. sampling rate", d);
                return;
            }
        }
        iterator = zzb.zzc((zzg)((Object)iterator));
        iterator.put("v", "1");
        iterator.put("_v", zzaqb.zzdtc);
        iterator.put("tid", this.zzdom);
        if (this.zzdoh.zzxi().isDryRunEnabled()) {
            object = new StringBuilder();
            iterator = iterator.entrySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    this.zzc("Dry run is enabled. GoogleAnalytics would have sent", ((StringBuilder)object).toString());
                    return;
                }
                Map.Entry entry = iterator.next();
                if (((StringBuilder)object).length() != 0) {
                    ((StringBuilder)object).append(", ");
                }
                ((StringBuilder)object).append((String)entry.getKey());
                ((StringBuilder)object).append("=");
                ((StringBuilder)object).append((String)entry.getValue());
            } while (true);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        zzasl.zzb(hashMap, "uid", zzapm2.getUserId());
        Object object2 = ((zzg)object).zza(zzapd.class);
        if (object2 != null) {
            zzasl.zzb(hashMap, "an", ((zzapd)object2).zzvi());
            zzasl.zzb(hashMap, "aid", ((zzapd)object2).getAppId());
            zzasl.zzb(hashMap, "av", ((zzapd)object2).zzvj());
            zzasl.zzb(hashMap, "aiid", ((zzapd)object2).zzvk());
        }
        object2 = zzapm2.zzvz();
        String string2 = this.zzdom;
        boolean bl = !TextUtils.isEmpty((CharSequence)zzapm2.zzwa());
        zzaqf zzaqf2 = new zzaqf(0L, (String)object2, string2, bl, 0L, hashMap);
        iterator.put("_s", String.valueOf(this.zzwx().zza(zzaqf2)));
        object = new zzarq(this.zzwt(), (Map<String, String>)((Object)iterator), ((zzg)object).zzuu(), true);
        this.zzwx().zza((zzarq)object);
    }

    @Override
    public final Uri zzup() {
        return this.zzdon;
    }
}

