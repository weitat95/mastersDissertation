/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgw;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.internal.zzcls;
import com.google.android.gms.internal.zzclt;
import com.google.android.gms.internal.zzclu;
import com.google.android.gms.internal.zzclv;
import com.google.android.gms.internal.zzclw;
import com.google.android.gms.internal.zzcma;
import com.google.android.gms.internal.zzcmb;
import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzcmf;
import com.google.android.gms.internal.zzcmg;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzcgk
extends zzcjl {
    zzcgk(zzcim zzcim2) {
        super(zzcim2);
    }

    private final Boolean zza(double d, zzclu object) {
        try {
            object = zzcgk.zza(new BigDecimal(d), (zzclu)object, Math.ulp(d));
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private final Boolean zza(long l, zzclu object) {
        try {
            object = zzcgk.zza(new BigDecimal(l), (zzclu)object, 0.0);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final Boolean zza(zzcls object, zzcmb zzcmb2, long l) {
        void var2_10;
        Object object2;
        if (((zzcls)object).zzjka != null) {
            void var3_11;
            object2 = this.zza((long)var3_11, ((zzcls)object).zzjka);
            if (object2 == null) {
                return null;
            }
            if (!((Boolean)object2).booleanValue()) {
                return false;
            }
        }
        zzclt[] arrzzclt = new HashSet();
        for (zzclt zzclt2 : ((zzcls)object).zzjjy) {
            if (TextUtils.isEmpty((CharSequence)zzclt2.zzjkf)) {
                this.zzawy().zzazf().zzj("null or empty param name in filter. event", this.zzawt().zzjh(var2_10.name));
                return null;
            }
            arrzzclt.add(zzclt2.zzjkf);
        }
        object2 = new ArrayMap();
        for (zzcmc zzcmc2 : var2_10.zzjlh) {
            if (!arrzzclt.contains(zzcmc2.name)) continue;
            if (zzcmc2.zzjll != null) {
                object2.put(zzcmc2.name, zzcmc2.zzjll);
                continue;
            }
            if (zzcmc2.zzjjl != null) {
                object2.put(zzcmc2.name, zzcmc2.zzjjl);
                continue;
            }
            if (zzcmc2.zzgcc == null) {
                this.zzawy().zzazf().zze("Unknown value for param. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(zzcmc2.name));
                return null;
            }
            object2.put(zzcmc2.name, zzcmc2.zzgcc);
        }
        arrzzclt = ((zzcls)object).zzjjy;
        int n = arrzzclt.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            zzclt zzclt3 = arrzzclt[n2];
            int n4 = Boolean.TRUE.equals(zzclt3.zzjke);
            String string2 = zzclt3.zzjkf;
            if (TextUtils.isEmpty((CharSequence)string2)) {
                this.zzawy().zzazf().zzj("Event has empty param name. event", this.zzawt().zzjh(var2_10.name));
                return null;
            }
            Object v = object2.get(string2);
            if (v instanceof Long) {
                if (zzclt3.zzjkd == null) {
                    this.zzawy().zzazf().zze("No number filter for long param. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                    return null;
                }
                Boolean bl = this.zza((Long)v, zzclt3.zzjkd);
                if (bl == null) {
                    return null;
                }
                n3 = bl == false ? 1 : 0;
                if ((n3 ^ n4) != 0) {
                    return false;
                }
            } else if (v instanceof Double) {
                if (zzclt3.zzjkd == null) {
                    this.zzawy().zzazf().zze("No number filter for double param. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                    return null;
                }
                Boolean bl = this.zza((Double)v, zzclt3.zzjkd);
                if (bl == null) {
                    return null;
                }
                n3 = bl == false ? 1 : 0;
                if ((n3 ^ n4) != 0) {
                    return false;
                }
            } else if (v instanceof String) {
                void var1_7;
                if (zzclt3.zzjkc != null) {
                    Boolean bl = this.zza((String)v, zzclt3.zzjkc);
                } else {
                    if (zzclt3.zzjkd == null) {
                        this.zzawy().zzazf().zze("No filter for String param. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                        return null;
                    }
                    if (!zzclq.zzkk((String)v)) {
                        this.zzawy().zzazf().zze("Invalid param value for number filter. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                        return null;
                    }
                    Boolean bl = this.zza((String)v, zzclt3.zzjkd);
                }
                if (var1_7 == null) {
                    return null;
                }
                n3 = !var1_7.booleanValue() ? 1 : 0;
                if ((n3 ^ n4) != 0) {
                    return false;
                }
            } else {
                if (v == null) {
                    this.zzawy().zzazj().zze("Missing param for filter. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                    return false;
                }
                this.zzawy().zzazf().zze("Unknown param type. event, param", this.zzawt().zzjh(var2_10.name), this.zzawt().zzji(string2));
                return null;
            }
            ++n2;
        }
        return true;
    }

    private static Boolean zza(Boolean bl, boolean bl2) {
        if (bl == null) {
            return null;
        }
        return bl ^ bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Boolean zza(String string2, int n, boolean bl, String string3, List<String> list, String string4) {
        if (string2 == null) {
            return null;
        }
        if (n == 6 ? list == null || list.size() == 0 : string3 == null) {
            return null;
        }
        String string5 = string2;
        if (!bl) {
            string5 = n == 1 ? string2 : string2.toUpperCase(Locale.ENGLISH);
        }
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                n = bl ? 0 : 66;
                try {
                    bl = Pattern.compile(string4, n).matcher(string5).matches();
                }
                catch (PatternSyntaxException patternSyntaxException) {
                    this.zzawy().zzazf().zzj("Invalid regular expression in REGEXP audience filter. expression", string4);
                    return null;
                }
                return bl;
            }
            case 2: {
                return string5.startsWith(string3);
            }
            case 3: {
                return string5.endsWith(string3);
            }
            case 4: {
                return string5.contains(string3);
            }
            case 5: {
                return string5.equals(string3);
            }
            case 6: 
        }
        return list.contains(string5);
    }

    private final Boolean zza(String object, zzclu zzclu2) {
        if (!zzclq.zzkk((String)object)) {
            return null;
        }
        try {
            object = zzcgk.zza(new BigDecimal((String)object), zzclu2, 0.0);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Boolean zza(String string2, zzclw list) {
        int n;
        String string3;
        block11: {
            block10: {
                block9: {
                    n = 0;
                    string3 = null;
                    zzbq.checkNotNull(list);
                    if (string2 == null || ((zzclw)list).zzjko == null || ((zzclw)list).zzjko == 0) break block9;
                    if (((zzclw)list).zzjko != 6) break block10;
                    if (((zzclw)list).zzjkr != null && ((zzclw)list).zzjkr.length != 0) break block11;
                }
                return null;
            }
            if (((zzclw)list).zzjkp == null) {
                return null;
            }
        }
        int n2 = ((zzclw)list).zzjko;
        boolean bl = ((zzclw)list).zzjkq != null && ((zzclw)list).zzjkq != false;
        String string4 = bl || n2 == 1 || n2 == 6 ? ((zzclw)list).zzjkp : ((zzclw)list).zzjkp.toUpperCase(Locale.ENGLISH);
        if (((zzclw)list).zzjkr == null) {
            list = null;
        } else {
            String[] arrstring = ((zzclw)list).zzjkr;
            if (bl) {
                list = Arrays.asList(arrstring);
            } else {
                ArrayList<String> arrayList = new ArrayList<String>();
                int n3 = arrstring.length;
                do {
                    list = arrayList;
                    if (n >= n3) break;
                    arrayList.add(arrstring[n].toUpperCase(Locale.ENGLISH));
                    ++n;
                } while (true);
            }
        }
        if (n2 == 1) {
            string3 = string4;
        }
        return this.zza(string2, n2, bl, string4, list, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Boolean zza(BigDecimal bigDecimal, zzclu object, double d) {
        boolean bl;
        int n;
        boolean bl2;
        BigDecimal bigDecimal2;
        boolean bl3;
        BigDecimal bigDecimal3;
        boolean bl4;
        boolean bl5;
        block27: {
            block29: {
                block28: {
                    bl2 = true;
                    bl4 = true;
                    bl3 = true;
                    bl = true;
                    bl5 = true;
                    zzbq.checkNotNull(object);
                    if (((zzclu)object).zzjkg == null || ((zzclu)object).zzjkg == 0) {
                        return null;
                    }
                    if (((zzclu)object).zzjkg == 4 ? ((zzclu)object).zzjkj == null || ((zzclu)object).zzjkk == null : ((zzclu)object).zzjki == null) {
                        return null;
                    }
                    n = ((zzclu)object).zzjkg;
                    if (((zzclu)object).zzjkg != 4) break block28;
                    if (!zzclq.zzkk(((zzclu)object).zzjkj) || !zzclq.zzkk(((zzclu)object).zzjkk)) {
                        return null;
                    }
                    try {
                        bigDecimal3 = new BigDecimal(((zzclu)object).zzjkj);
                        object = new BigDecimal(((zzclu)object).zzjkk);
                        bigDecimal2 = null;
                    }
                    catch (NumberFormatException numberFormatException) {
                        return null;
                    }
                    while (n == 4) {
                        if (bigDecimal3 == null) {
                            return null;
                        }
                        break block27;
                    }
                    break block29;
                }
                if (!zzclq.zzkk(((zzclu)object).zzjki)) {
                    return null;
                }
                try {
                    bigDecimal2 = new BigDecimal(((zzclu)object).zzjki);
                    bigDecimal3 = null;
                    object = null;
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
            }
            if (bigDecimal2 == null) return null;
        }
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                if (bigDecimal.compareTo(bigDecimal2) == -1) {
                    do {
                        return bl5;
                        break;
                    } while (true);
                }
                bl5 = false;
                return bl5;
            }
            case 2: {
                if (bigDecimal.compareTo(bigDecimal2) == 1) {
                    bl5 = bl2;
                    do {
                        return bl5;
                        break;
                    } while (true);
                }
                bl5 = false;
                return bl5;
            }
            case 3: {
                if (d != 0.0) {
                    if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1) {
                        bl5 = bl4;
                        do {
                            return bl5;
                            break;
                        } while (true);
                    }
                    bl5 = false;
                    return bl5;
                }
                if (bigDecimal.compareTo(bigDecimal2) == 0) {
                    bl5 = bl3;
                    do {
                        return bl5;
                        break;
                    } while (true);
                }
                bl5 = false;
                return bl5;
            }
            case 4: 
        }
        if (bigDecimal.compareTo(bigDecimal3) != -1 && bigDecimal.compareTo((BigDecimal)object) != 1) {
            bl5 = bl;
            do {
                return bl5;
                break;
            } while (true);
        }
        bl5 = false;
        return bl5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final zzcma[] zza(String var1_1, zzcmb[] var2_2, zzcmg[] var3_5) {
        block42: {
            zzbq.zzgm(var1_1);
            var15_6 = new HashSet<Integer>();
            var16_7 = new ArrayMap<Integer, Object>();
            var17_8 = new ArrayMap<Integer, Object>();
            var18_9 = new ArrayMap<Integer, Object>();
            var13_10 = this.zzaws().zzje(var1_1);
            if (var13_10 != null) {
                var14_11 = var13_10.keySet().iterator();
                while (var14_11.hasNext()) {
                    var5_13 = var14_11.next();
                    var19_17 = var13_10.get(var5_13);
                    var12_16 = (BitSet)var17_8.get(var5_13);
                    var11_15 = (BitSet)var18_9.get(var5_13);
                    var10_14 = var12_16;
                    if (var12_16 == null) {
                        var10_14 = new BitSet();
                        var17_8.put(var5_13, var10_14);
                        var11_15 = new BitSet();
                        var18_9.put(var5_13, var11_15);
                    }
                    for (var4_12 = 0; var4_12 < var19_17.zzjmp.length << 6; ++var4_12) {
                        if (!zzclq.zza(var19_17.zzjmp, var4_12)) continue;
                        this.zzawy().zzazj().zze("Filter already evaluated. audience ID, filter ID", var5_13, var4_12);
                        var11_15.set(var4_12);
                        if (!zzclq.zza(var19_17.zzjmq, var4_12)) continue;
                        var10_14.set(var4_12);
                    }
                    var12_16 = new zzcma();
                    var16_7.put(var5_13, var12_16);
                    var12_16.zzjlf = false;
                    var12_16.zzjle = var19_17;
                    var12_16.zzjld = new zzcmf();
                    var12_16.zzjld.zzjmq = zzclq.zza(var10_14);
                    var12_16.zzjld.zzjmp = zzclq.zza((BitSet)var11_15);
                }
            }
            if (var2_2 != null) {
                var19_18 = new ArrayMap<String, ArrayMap<K, V>>();
                block6: for (Iterator<K> var20_24 : var2_2) {
                    var10_14 = this.zzaws().zzae(var1_1, var20_24.name);
                    if (var10_14 == null) {
                        this.zzawy().zzazf().zze("Event aggregate wasn't created during raw event logging. appId, event", zzchm.zzjk(var1_1), this.zzawt().zzjh(var20_24.name));
                        var10_14 = new zzcgw(var1_1, var20_24.name, 1L, 1L, var20_24.zzjli, 0L, null, null, null);
                    } else {
                        var10_14 = var10_14.zzayw();
                    }
                    this.zzaws().zza((zzcgw)var10_14);
                    var7_23 = var10_14.zzizk;
                    var10_14 = (Map)var19_18.get(var20_24.name);
                    if (var10_14 == null) {
                        var11_15 = this.zzaws().zzaj(var1_1, var20_24.name);
                        var10_14 = var11_15;
                        if (var11_15 == null) {
                            var10_14 = new ArrayMap<K, V>();
                        }
                        var19_18.put(var20_24.name, var10_14);
                    }
                    var21_25 = var10_14.keySet().iterator();
                    do {
                        if (!var21_25.hasNext()) continue block6;
                        var6_22 = (Integer)var21_25.next();
                        if (var15_6.contains(var6_22)) {
                            this.zzawy().zzazj().zzj("Skipping failed audience ID", var6_22);
                            continue;
                        }
                        var13_10 = (zzcma)var16_7.get(var6_22);
                        var11_15 = (BitSet)var17_8.get(var6_22);
                        var12_16 = (BitSet)var18_9.get(var6_22);
                        if (var13_10 == null) {
                            var11_15 = new zzcma();
                            var16_7.put(var6_22, var11_15);
                            var11_15.zzjlf = true;
                            var11_15 = new BitSet();
                            var17_8.put(var6_22, var11_15);
                            var12_16 = new BitSet();
                            var18_9.put(var6_22, var12_16);
                        }
                        var22_26 = ((List)var10_14.get(var6_22)).iterator();
                        do {
                            if (!var22_26.hasNext()) ** break;
                            var23_27 = (zzcls)var22_26.next();
                            if (this.zzawy().zzae(2)) {
                                this.zzawy().zzazj().zzd("Evaluating filter. audience, filter, event", var6_22, var23_27.zzjjw, this.zzawt().zzjh(var23_27.zzjjx));
                                this.zzawy().zzazj().zzj("Filter definition", this.zzawt().zza((zzcls)var23_27));
                            }
                            if (var23_27.zzjjw == null || var23_27.zzjjw > 256) {
                                this.zzawy().zzazf().zze("Invalid event filter ID. appId, id", zzchm.zzjk(var1_1), String.valueOf(var23_27.zzjjw));
                                continue;
                            }
                            if (var11_15.get(var23_27.zzjjw)) {
                                this.zzawy().zzazj().zze("Event filter already evaluated true. audience ID, filter ID", var6_22, var23_27.zzjjw);
                                continue;
                            }
                            var14_11 = this.zza((zzcls)var23_27, (zzcmb)var20_24, var7_23);
                            var24_28 = this.zzawy().zzazj();
                            var13_10 = var14_11 == null ? "null" : var14_11;
                            var24_28.zzj("Event filter result", var13_10);
                            if (var14_11 == null) {
                                var15_6.add(var6_22);
                                continue;
                            }
                            var12_16.set(var23_27.zzjjw);
                            if (!var14_11.booleanValue()) continue;
                            var11_15.set(var23_27.zzjjw);
                        } while (true);
                        break;
                    } while (true);
                }
            }
            if (var3_5 == null) break block42;
            var14_11 = new ArrayMap<K, V>();
            block9: for (zzfjm var19_21 : var3_5) {
                var10_14 = (Map)var14_11.get(var19_21.name);
                if (var10_14 == null) {
                    var10_14 = this.zzaws().zzak(var1_1, var19_21.name);
                    var2_2 = var10_14;
                    if (var10_14 == null) {
                        var2_2 = new ArrayMap<K, V>();
                    }
                    var14_11.put(var19_21.name, var2_2);
                    var10_14 = var2_2;
                }
                var20_24 = var10_14.keySet().iterator();
                block10: do {
                    if (!var20_24.hasNext()) continue block9;
                    var6_22 = (Integer)var20_24.next();
                    if (var15_6.contains(var6_22)) {
                        this.zzawy().zzazj().zzj("Skipping failed audience ID", var6_22);
                        continue;
                    }
                    var2_2 = (zzcma)var16_7.get(var6_22);
                    var11_15 = (BitSet)var17_8.get(var6_22);
                    var12_16 = (BitSet)var18_9.get(var6_22);
                    if (var2_2 == null) {
                        var2_2 = new zzcma();
                        var16_7.put(var6_22, var2_2);
                        var2_2.zzjlf = true;
                        var11_15 = new BitSet();
                        var17_8.put(var6_22, var11_15);
                        var12_16 = new BitSet();
                        var18_9.put(var6_22, var12_16);
                    }
                    var21_25 = ((List)var10_14.get(var6_22)).iterator();
                    do {
                        block44: {
                            block47: {
                                block48: {
                                    block49: {
                                        block46: {
                                            block45: {
                                                block43: {
                                                    if (!var21_25.hasNext()) continue block10;
                                                    var22_26 = (zzclv)var21_25.next();
                                                    if (this.zzawy().zzae(2)) {
                                                        this.zzawy().zzazj().zzd("Evaluating filter. audience, filter, property", var6_22, var22_26.zzjjw, this.zzawt().zzjj(var22_26.zzjkm));
                                                        this.zzawy().zzazj().zzj("Filter definition", this.zzawt().zza((zzclv)var22_26));
                                                    }
                                                    if (var22_26.zzjjw == null || var22_26.zzjjw > 256) {
                                                        this.zzawy().zzazf().zze("Invalid property filter ID. appId, id", zzchm.zzjk(var1_1), String.valueOf(var22_26.zzjjw));
                                                        var15_6.add(var6_22);
                                                        continue block10;
                                                    }
                                                    if (var11_15.get(var22_26.zzjjw)) {
                                                        this.zzawy().zzazj().zze("Property filter already evaluated true. audience ID, filter ID", var6_22, var22_26.zzjjw);
                                                        continue;
                                                    }
                                                    var2_2 = var22_26.zzjkn;
                                                    if (var2_2 != null) break block43;
                                                    this.zzawy().zzazf().zzj("Missing property filter. property", this.zzawt().zzjj(var19_21.name));
                                                    var2_2 = null;
                                                    break block44;
                                                }
                                                var9_29 = Boolean.TRUE.equals(var2_2.zzjke);
                                                if (var19_21.zzjll == null) break block45;
                                                if (var2_2.zzjkd == null) {
                                                    this.zzawy().zzazf().zzj("No number filter for long property. property", this.zzawt().zzjj(var19_21.name));
                                                    var2_2 = null;
                                                } else {
                                                    var2_2 = zzcgk.zza(this.zza(var19_21.zzjll, var2_2.zzjkd), var9_29);
                                                }
                                                break block44;
                                            }
                                            if (var19_21.zzjjl == null) break block46;
                                            if (var2_2.zzjkd == null) {
                                                this.zzawy().zzazf().zzj("No number filter for double property. property", this.zzawt().zzjj(var19_21.name));
                                                var2_2 = null;
                                            } else {
                                                var2_2 = zzcgk.zza(this.zza(var19_21.zzjjl, var2_2.zzjkd), var9_29);
                                            }
                                            break block44;
                                        }
                                        if (var19_21.zzgcc == null) break block47;
                                        if (var2_2.zzjkc != null) break block48;
                                        if (var2_2.zzjkd != null) break block49;
                                        this.zzawy().zzazf().zzj("No string or number filter defined. property", this.zzawt().zzjj(var19_21.name));
                                        ** GOTO lbl185
                                    }
                                    if (zzclq.zzkk(var19_21.zzgcc)) {
                                        var2_2 = zzcgk.zza(this.zza(var19_21.zzgcc, var2_2.zzjkd), var9_29);
                                    } else {
                                        this.zzawy().zzazf().zze("Invalid user property value for Numeric number filter. property, value", this.zzawt().zzjj(var19_21.name), var19_21.zzgcc);
lbl185:
                                        // 2 sources
                                        var2_2 = null;
                                    }
                                    break block44;
                                }
                                var2_2 = zzcgk.zza(this.zza(var19_21.zzgcc, var2_2.zzjkc), var9_29);
                                break block44;
                            }
                            this.zzawy().zzazf().zzj("User property has no value, property", this.zzawt().zzjj(var19_21.name));
                            var2_2 = null;
                        }
                        var23_27 = this.zzawy().zzazj();
                        var13_10 = var2_2 == null ? "null" : var2_2;
                        var23_27.zzj("Property filter result", var13_10);
                        if (var2_2 == null) {
                            var15_6.add(var6_22);
                            continue;
                        }
                        var12_16.set(var22_26.zzjjw);
                        if (!var2_2.booleanValue()) continue;
                        var11_15.set(var22_26.zzjjw);
                    } while (true);
                    break;
                } while (true);
            }
        }
        var3_5 = new zzcma[var17_8.size()];
        var10_14 = var17_8.keySet().iterator();
        var4_12 = 0;
        while (var10_14.hasNext() != false) {
            var6_22 = (Integer)var10_14.next();
            if (var15_6.contains(var6_22)) continue;
            var2_2 = (zzcma)var16_7.get(var6_22);
            if (var2_2 == null) {
                var2_2 = new zzcma();
            }
            var5_13 = var4_12 + 1;
            var3_5[var4_12] = var2_2;
            var2_2.zzjjs = var6_22;
            var2_2.zzjld = new zzcmf();
            var2_2.zzjld.zzjmq = zzclq.zza((BitSet)var17_8.get(var6_22));
            var2_2.zzjld.zzjmp = zzclq.zza((BitSet)var18_9.get(var6_22));
            var11_15 = this.zzaws();
            var12_16 = var2_2.zzjld;
            var11_15.zzxf();
            var11_15.zzve();
            zzbq.zzgm(var1_1);
            zzbq.checkNotNull(var12_16);
            try {
                var2_2 = new byte[var12_16.zzho()];
                var13_10 = zzfjk.zzo((byte[])var2_2, 0, ((Object)var2_2).length);
                var12_16.zza((zzfjk)var13_10);
                var13_10.zzcwt();
            }
            catch (IOException var2_3) {
                var11_15.zzawy().zzazd().zze("Configuration loss. Failed to serialize filter results. appId", zzchm.zzjk(var1_1), var2_3);
                var4_12 = var5_13;
                continue;
            }
            var12_16 = new ContentValues();
            var12_16.put("app_id", var1_1);
            var12_16.put("audience_id", Integer.valueOf(var6_22));
            var12_16.put("current_results", (byte[])var2_2);
            try {
                if (var11_15.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, (ContentValues)var12_16, 5) == -1L) {
                    var11_15.zzawy().zzazd().zzj("Failed to insert filter results (got -1). appId", zzchm.zzjk(var1_1));
                }
                var4_12 = var5_13;
            }
            catch (SQLiteException var2_4) {
                var11_15.zzawy().zzazd().zze("Error storing filter results. appId", zzchm.zzjk(var1_1), (Object)var2_4);
                var4_12 = var5_13;
            }
        }
        return (zzcma[])Arrays.copyOf(var3_5, var4_12);
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }
}

