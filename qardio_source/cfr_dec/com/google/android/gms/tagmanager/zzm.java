/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzby;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzfu;
import com.google.android.gms.tagmanager.zzgi;
import com.google.android.gms.tagmanager.zzgk;
import com.google.android.gms.tagmanager.zzn;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzm
extends zzgi {
    private static final String ID = zzbg.zzkn.toString();
    private static final String URL = zzbh.zzvn.toString();
    private static final String zzkcx = zzbh.zzml.toString();
    private static final String zzkcy = zzbh.zzvm.toString();
    private static String zzkcz;
    private static final Set<String> zzkda;
    private final Context mContext;
    private final zza zzkdb;

    static {
        String string2 = ID;
        zzkcz = new StringBuilder(String.valueOf(string2).length() + 17).append("gtm_").append(string2).append("_unrepeatable").toString();
        zzkda = new HashSet<String>();
    }

    public zzm(Context context) {
        this(context, new zzn(context));
    }

    private zzm(Context context, zza zza2) {
        super(ID, URL);
        this.zzkdb = zza2;
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zzla(String string2) {
        boolean bl = true;
        synchronized (this) {
            boolean bl2 = zzkda.contains(string2);
            if (bl2) return bl;
            if (!this.mContext.getSharedPreferences(zzkcz, 0).contains(string2)) return false;
            zzkda.add(string2);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zzx(Map<String, zzbs> object) {
        String string2;
        block11: {
            block10: {
                string2 = object.get(zzkcy) != null ? zzgk.zzb(object.get(zzkcy)) : null;
                if (string2 != null && this.zzla(string2)) break block10;
                Uri.Builder builder = Uri.parse((String)zzgk.zzb(object.get(URL))).buildUpon();
                if ((object = object.get(zzkcx)) != null) {
                    if (!((object = zzgk.zzg((zzbs)object)) instanceof List)) {
                        object = String.valueOf(builder.build().toString());
                        object = ((String)object).length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat((String)object) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: ");
                        zzdj.e((String)object);
                        return;
                    }
                    object = ((List)object).iterator();
                    while (object.hasNext()) {
                        Object object2 = object.next();
                        if (!(object2 instanceof Map)) {
                            object = String.valueOf(builder.build().toString());
                            object = ((String)object).length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat((String)object) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: ");
                            zzdj.e((String)object);
                            return;
                        }
                        for (Map.Entry entry : ((Map)object2).entrySet()) {
                            builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        }
                    }
                }
                object = builder.build().toString();
                this.zzkdb.zzbdq().zzll((String)object);
                object = String.valueOf(object);
                object = ((String)object).length() != 0 ? "ArbitraryPixel: url = ".concat((String)object) : new String("ArbitraryPixel: url = ");
                zzdj.v((String)object);
                if (string2 != null) break block11;
            }
            return;
        }
        synchronized (zzm.class) {
            zzkda.add(string2);
            zzfu.zze(this.mContext, zzkcz, string2, "true");
            return;
        }
    }

    public static interface zza {
        public zzby zzbdq();
    }

}

