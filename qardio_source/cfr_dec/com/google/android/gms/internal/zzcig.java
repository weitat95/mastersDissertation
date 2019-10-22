/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.internal.zzclr;
import com.google.android.gms.internal.zzcls;
import com.google.android.gms.internal.zzclt;
import com.google.android.gms.internal.zzclv;
import com.google.android.gms.internal.zzclx;
import com.google.android.gms.internal.zzcly;
import com.google.android.gms.internal.zzclz;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.measurement.AppMeasurement;
import java.io.IOException;
import java.util.Map;

public final class zzcig
extends zzcjl {
    private static int zzjdx = 65535;
    private static int zzjdy = 2;
    private final Map<String, Map<String, String>> zzjdz = new ArrayMap<String, Map<String, String>>();
    private final Map<String, Map<String, Boolean>> zzjea = new ArrayMap<String, Map<String, Boolean>>();
    private final Map<String, Map<String, Boolean>> zzjeb = new ArrayMap<String, Map<String, Boolean>>();
    private final Map<String, zzcly> zzjec = new ArrayMap<String, zzcly>();
    private final Map<String, Map<String, Integer>> zzjed;
    private final Map<String, String> zzjee = new ArrayMap<String, String>();

    zzcig(zzcim zzcim2) {
        super(zzcim2);
        this.zzjed = new ArrayMap<String, Map<String, Integer>>();
    }

    private static Map<String, String> zza(zzcly arrzzclz) {
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
        if (arrzzclz != null && arrzzclz.zzjky != null) {
            for (zzclz zzclz2 : arrzzclz.zzjky) {
                if (zzclz2 == null) continue;
                arrayMap.put(zzclz2.key, zzclz2.value);
            }
        }
        return arrayMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(String string2, zzcly arrzzclx) {
        ArrayMap<String, Boolean> arrayMap = new ArrayMap<String, Boolean>();
        ArrayMap<String, Boolean> arrayMap2 = new ArrayMap<String, Boolean>();
        ArrayMap<String, Integer> arrayMap3 = new ArrayMap<String, Integer>();
        if (arrzzclx != null && arrzzclx.zzjkz != null) {
            for (zzclx zzclx2 : arrzzclx.zzjkz) {
                if (TextUtils.isEmpty((CharSequence)zzclx2.name)) {
                    ((zzcjk)this).zzawy().zzazf().log("EventConfig contained null event name");
                    continue;
                }
                String string3 = AppMeasurement.Event.zziq(zzclx2.name);
                if (!TextUtils.isEmpty((CharSequence)string3)) {
                    zzclx2.name = string3;
                }
                arrayMap.put(zzclx2.name, zzclx2.zzjkt);
                arrayMap2.put(zzclx2.name, zzclx2.zzjku);
                if (zzclx2.zzjkv == null) continue;
                if (zzclx2.zzjkv < zzjdy || zzclx2.zzjkv > zzjdx) {
                    ((zzcjk)this).zzawy().zzazf().zze("Invalid sampling rate. Event name, sample rate", zzclx2.name, zzclx2.zzjkv);
                    continue;
                }
                arrayMap3.put(zzclx2.name, zzclx2.zzjkv);
            }
        }
        this.zzjea.put(string2, arrayMap);
        this.zzjeb.put(string2, arrayMap2);
        this.zzjed.put(string2, arrayMap3);
    }

    private final zzcly zzc(String string2, byte[] object) {
        if (object == null) {
            return new zzcly();
        }
        object = zzfjj.zzn(object, 0, ((byte[])object).length);
        zzcly zzcly2 = new zzcly();
        try {
            ((zzfjs)zzcly2).zza((zzfjj)object);
            ((zzcjk)this).zzawy().zzazj().zze("Parsed config. version, gmp_app_id", zzcly2.zzjkw, zzcly2.zzixs);
            return zzcly2;
        }
        catch (IOException iOException) {
            ((zzcjk)this).zzawy().zzazf().zze("Unable to merge remote config. appId", zzchm.zzjk(string2), iOException);
            return new zzcly();
        }
    }

    private final void zzjr(String string2) {
        Object object;
        block3: {
            block2: {
                this.zzxf();
                ((zzcjk)this).zzve();
                zzbq.zzgm(string2);
                if (this.zzjec.get(string2) != null) break block2;
                object = ((zzcjk)this).zzaws().zzjd(string2);
                if (object != null) break block3;
                this.zzjdz.put(string2, null);
                this.zzjea.put(string2, null);
                this.zzjeb.put(string2, null);
                this.zzjec.put(string2, null);
                this.zzjee.put(string2, null);
                this.zzjed.put(string2, null);
            }
            return;
        }
        object = this.zzc(string2, (byte[])object);
        this.zzjdz.put(string2, zzcig.zza((zzcly)object));
        this.zza(string2, (zzcly)object);
        this.zzjec.put(string2, (zzcly)object);
        this.zzjee.put(string2, null);
    }

    final String zzam(String object, String string2) {
        ((zzcjk)this).zzve();
        this.zzjr((String)object);
        object = this.zzjdz.get(object);
        if (object != null) {
            return (String)object.get(string2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    final boolean zzan(String object, String string2) {
        ((zzcjk)this).zzve();
        this.zzjr((String)object);
        if (((zzcjk)this).zzawu().zzkl((String)object) && zzclq.zzki(string2) || ((zzcjk)this).zzawu().zzkm((String)object) && zzclq.zzjz(string2)) {
            return true;
        }
        if ((object = this.zzjea.get(object)) == null) {
            return false;
        }
        if ((object = object.get(string2)) == null) {
            return false;
        }
        return (Boolean)object;
    }

    final boolean zzao(String object, String string2) {
        ((zzcjk)this).zzve();
        this.zzjr((String)object);
        if ("ecommerce_purchase".equals(string2)) {
            return true;
        }
        if ((object = this.zzjeb.get(object)) != null) {
            if ((object = (Boolean)object.get(string2)) == null) {
                return false;
            }
            return (Boolean)object;
        }
        return false;
    }

    final int zzap(String object, String string2) {
        ((zzcjk)this).zzve();
        this.zzjr((String)object);
        object = this.zzjed.get(object);
        if (object != null) {
            if ((object = (Integer)object.get(string2)) == null) {
                return 1;
            }
            return (Integer)object;
        }
        return 1;
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final boolean zzb(String string2, byte[] object, String object2) {
        this.zzxf();
        ((zzcjk)this).zzve();
        zzbq.zzgm(string2);
        zzcly zzcly2 = this.zzc(string2, (byte[])object);
        if (zzcly2 == null) {
            return false;
        }
        this.zza(string2, zzcly2);
        this.zzjec.put(string2, zzcly2);
        this.zzjee.put(string2, (String)object2);
        this.zzjdz.put(string2, zzcig.zza(zzcly2));
        object2 = ((zzcjk)this).zzawl();
        zzclr[] arrzzclr = zzcly2.zzjla;
        zzbq.checkNotNull(arrzzclr);
        int n = arrzzclr.length;
        int n2 = 0;
        do {
            zzcls[] arrzzcls;
            zzclv[] arrzzclv;
            int n3;
            if (n2 < n) {
                arrzzclv = arrzzclr[n2];
                arrzzcls = arrzzclv.zzjju;
                n3 = arrzzcls.length;
            } else {
                ((zzcjk)object2).zzaws().zza(string2, arrzzclr);
                try {
                    zzcly2.zzjla = null;
                    object2 = new byte[zzcly2.zzho()];
                    ((zzfjs)zzcly2).zza(zzfjk.zzo((byte[])object2, 0, ((Object)object2).length));
                    object = object2;
                }
                catch (IOException iOException) {
                    ((zzcjk)this).zzawy().zzazf().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzchm.zzjk(string2), iOException);
                }
                object2 = ((zzcjk)this).zzaws();
                zzbq.zzgm(string2);
                ((zzcjk)object2).zzve();
                ((zzcjl)object2).zzxf();
                zzcly2 = new ContentValues();
                zzcly2.put("remote_config", object);
                try {
                    if ((long)((zzcgo)object2).getWritableDatabase().update("apps", (ContentValues)zzcly2, "app_id = ?", new String[]{string2}) != 0L) return true;
                    ((zzcjk)object2).zzawy().zzazd().zzj("Failed to update remote config (got 0). appId", zzchm.zzjk(string2));
                    return true;
                }
                catch (SQLiteException sQLiteException) {
                    ((zzcjk)object2).zzawy().zzazd().zze("Error storing remote config. appId", zzchm.zzjk(string2), (Object)sQLiteException);
                    return true;
                }
            }
            for (int i = 0; i < n3; ++i) {
                zzcls zzcls2 = arrzzcls[i];
                String string3 = AppMeasurement.Event.zziq(zzcls2.zzjjx);
                if (string3 != null) {
                    zzcls2.zzjjx = string3;
                }
                for (zzclt zzclt2 : zzcls2.zzjjy) {
                    String string4 = AppMeasurement.Param.zziq(zzclt2.zzjkf);
                    if (string4 == null) continue;
                    zzclt2.zzjkf = string4;
                }
            }
            for (zzclv zzclv2 : arrzzclv.zzjjt) {
                String string5 = AppMeasurement.UserProperty.zziq(zzclv2.zzjkm);
                if (string5 == null) continue;
                zzclv2.zzjkm = string5;
            }
            ++n2;
        } while (true);
    }

    protected final zzcly zzjs(String string2) {
        this.zzxf();
        ((zzcjk)this).zzve();
        zzbq.zzgm(string2);
        this.zzjr(string2);
        return this.zzjec.get(string2);
    }

    protected final String zzjt(String string2) {
        ((zzcjk)this).zzve();
        return this.zzjee.get(string2);
    }

    protected final void zzju(String string2) {
        ((zzcjk)this).zzve();
        this.zzjee.put(string2, null);
    }

    final void zzjv(String string2) {
        ((zzcjk)this).zzve();
        this.zzjec.remove(string2);
    }
}

