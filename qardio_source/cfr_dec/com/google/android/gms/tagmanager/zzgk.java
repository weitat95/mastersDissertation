/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzgj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzgk {
    private static final Object zzkkl = null;
    private static Long zzkkm = new Long(0L);
    private static Double zzkkn = new Double(0.0);
    private static zzgj zzkko = zzgj.zzbi(0L);
    private static String zzkkp = new String("");
    private static Boolean zzkkq = new Boolean(false);
    private static List<Object> zzkkr = new ArrayList<Object>(0);
    private static Map<Object, Object> zzkks = new HashMap<Object, Object>();
    private static zzbs zzkkt = zzgk.zzam(zzkkp);

    private static String zzal(Object object) {
        if (object == null) {
            return zzkkp;
        }
        return object.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static zzbs zzam(Object var0) {
        block17: {
            block15: {
                block13: {
                    block16: {
                        block14: {
                            block12: {
                                var1_1 = false;
                                var2_2 = new zzbs();
                                if (var0 instanceof zzbs) {
                                    return (zzbs)var0;
                                }
                                if (!(var0 instanceof String)) break block12;
                                var2_2.type = 1;
                                var2_2.string = (String)var0;
                                break block13;
                            }
                            if (!(var0 instanceof List)) break block14;
                            var2_2.type = 2;
                            var3_3 = (List)var0;
                            var0 = new ArrayList<E>(var3_3.size());
                            var3_3 = var3_3.iterator();
                            var1_1 = false;
                            break block15;
                        }
                        if (!(var0 instanceof Map)) break block16;
                        var2_2.type = 3;
                        var4_6 = ((Map)var0).entrySet();
                        var0 = new ArrayList<E>(var4_6.size());
                        var3_4 = new ArrayList<Object>(var4_6.size());
                        var4_6 = var4_6.iterator();
                        var1_1 = false;
                        break block17;
                    }
                    if (zzgk.zzan(var0)) {
                        var2_2.type = 1;
                        var2_2.string = var0.toString();
                    } else if (zzgk.zzao(var0)) {
                        var2_2.type = 6;
                        var2_2.zzyq = zzgk.zzap(var0);
                    } else if (var0 instanceof Boolean) {
                        var2_2.type = 8;
                        var2_2.zzyr = (Boolean)var0;
                    } else {
                        var0 = var0 == null ? "null" : var0.getClass().toString();
                        var0 = (var0 = String.valueOf(var0)).length() != 0 ? "Converting to Value from unknown object type: ".concat((String)var0) : new String("Converting to Value from unknown object type: ");
                        zzdj.e((String)var0);
                        return zzgk.zzkkt;
                    }
                }
lbl44:
                // 3 sources
                do {
                    var2_2.zzyu = var1_1;
                    return var2_2;
                    break;
                } while (true);
            }
            while (var3_3.hasNext()) {
                var4_5 = zzgk.zzam(var3_3.next());
                if (var4_5 == zzgk.zzkkt) {
                    return zzgk.zzkkt;
                }
                var1_1 = var1_1 != false || var4_5.zzyu != false;
                var0.add(var4_5);
            }
            var2_2.zzyl = var0.toArray(new zzbs[0]);
            ** GOTO lbl44
        }
        while (var4_6.hasNext()) {
            var6_8 = (Map.Entry)var4_6.next();
            var5_7 = zzgk.zzam(var6_8.getKey());
            var6_8 = zzgk.zzam(var6_8.getValue());
            if (var5_7 == zzgk.zzkkt) return zzgk.zzkkt;
            if (var6_8 == zzgk.zzkkt) {
                return zzgk.zzkkt;
            }
            var1_1 = var1_1 != false || var5_7.zzyu != false || var6_8.zzyu != false;
            var0.add(var5_7);
            var3_4.add(var6_8);
        }
        var2_2.zzym = var0.toArray(new zzbs[0]);
        var2_2.zzyn = var3_4.toArray(new zzbs[0]);
        ** while (true)
    }

    private static boolean zzan(Object object) {
        return object instanceof Double || object instanceof Float || object instanceof zzgj && ((zzgj)object).zzbgk();
    }

    private static boolean zzao(Object object) {
        return object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof zzgj && ((zzgj)object).zzbgl();
    }

    private static long zzap(Object object) {
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        zzdj.e("getInt64 received non-Number");
        return 0L;
    }

    public static String zzb(zzbs zzbs2) {
        return zzgk.zzal(zzgk.zzg(zzbs2));
    }

    public static zzbs zzbgs() {
        return zzkkt;
    }

    public static Boolean zzf(zzbs object) {
        if ((object = zzgk.zzg((zzbs)object)) instanceof Boolean) {
            return (Boolean)object;
        }
        if ("true".equalsIgnoreCase((String)(object = zzgk.zzal(object)))) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase((String)object)) {
            return Boolean.FALSE;
        }
        return zzkkq;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Object zzg(zzbs object) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (object == null) {
            return null;
        }
        switch (((zzbs)object).type) {
            default: {
                n3 = ((zzbs)object).type;
                zzdj.e(new StringBuilder(46).append("Failed to convert a value of type: ").append(n3).toString());
                return null;
            }
            case 1: {
                return ((zzbs)object).string;
            }
            case 2: {
                ArrayList<Object> arrayList = new ArrayList<Object>(((zzbs)object).zzyl.length);
                object = ((zzbs)object).zzyl;
                n2 = ((Object)object).length;
                do {
                    if (n3 >= n2) {
                        return arrayList;
                    }
                    Object object2 = zzgk.zzg((zzbs)object[n3]);
                    if (object2 == null) {
                        return null;
                    }
                    arrayList.add(object2);
                    ++n3;
                } while (true);
            }
            case 3: {
                if (((zzbs)object).zzym.length != ((zzbs)object).zzyn.length) {
                    object = ((String)(object = String.valueOf(((zzfjs)object).toString()))).length() != 0 ? "Converting an invalid value to object: ".concat((String)object) : new String("Converting an invalid value to object: ");
                    zzdj.e((String)object);
                    return null;
                }
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>(((zzbs)object).zzyn.length);
                n3 = n;
                do {
                    if (n3 >= ((zzbs)object).zzym.length) {
                        return hashMap;
                    }
                    Object object3 = zzgk.zzg(((zzbs)object).zzym[n3]);
                    Object object4 = zzgk.zzg(((zzbs)object).zzyn[n3]);
                    if (object3 == null || object4 == null) {
                        return null;
                    }
                    hashMap.put(object3, object4);
                    ++n3;
                } while (true);
            }
            case 4: {
                zzdj.e("Trying to convert a macro reference to object");
                return null;
            }
            case 5: {
                zzdj.e("Trying to convert a function id to object");
                return null;
            }
            case 6: {
                return ((zzbs)object).zzyq;
            }
            case 7: {
                StringBuffer stringBuffer = new StringBuffer();
                object = ((zzbs)object).zzys;
                n = ((Object)object).length;
                n3 = n2;
                do {
                    if (n3 >= n) {
                        return stringBuffer.toString();
                    }
                    String string2 = zzgk.zzb((zzbs)object[n3]);
                    if (string2 == zzkkp) {
                        return null;
                    }
                    stringBuffer.append(string2);
                    ++n3;
                } while (true);
            }
            case 8: 
        }
        return ((zzbs)object).zzyr;
    }
}

