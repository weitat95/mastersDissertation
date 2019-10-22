/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzdlz;
import com.google.android.gms.internal.zzdma;
import com.google.android.gms.internal.zzdmb;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.wearable.Asset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DataMap {
    private final HashMap<String, Object> zzaln = new HashMap();

    public static DataMap fromByteArray(byte[] object) {
        try {
            object = zzdlz.zza(new zzdma(zzdmb.zzaa(object), new ArrayList<Asset>()));
            return object;
        }
        catch (zzfjr zzfjr2) {
            throw new IllegalArgumentException("Unable to convert data", zzfjr2);
        }
    }

    private static void zza(String string2, Object object, String string3, ClassCastException classCastException) {
        DataMap.zza(string2, object, string3, "<null>", classCastException);
    }

    private static void zza(String string2, Object object, String string3, Object object2, ClassCastException classCastException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Key ");
        stringBuilder.append(string2);
        stringBuilder.append(" expected ");
        stringBuilder.append(string3);
        stringBuilder.append(" but value was a ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append(".  The default value ");
        stringBuilder.append(object2);
        stringBuilder.append(" was returned.");
        Log.w((String)"DataMap", (String)stringBuilder.toString());
        Log.w((String)"DataMap", (String)"Attempt to cast generated internal exception:", (Throwable)classCastException);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object var1_1) {
        if (!(var1_1 instanceof DataMap)) {
            return false;
        }
        var1_1 = (DataMap)var1_1;
        if (this.size() != var1_1.size()) {
            return false;
        }
        var3_2 = this.keySet().iterator();
        do lbl-1000:
        // 7 sources
        {
            block14: {
                block13: {
                    block12: {
                        block11: {
                            block10: {
                                if (var3_2.hasNext() == false) return true;
                                var5_6 = var3_2.next();
                                var4_4 = this.get(var5_6);
                                var5_7 = var1_1.get(var5_6);
                                if (!(var4_4 instanceof Asset)) break block10;
                                if (!(var5_7 instanceof Asset)) {
                                    return false;
                                }
                                var4_4 = (Asset)var4_4;
                                var5_8 = (Asset)var5_7;
                                if (var4_4 == null || var5_8 == null) {
                                    if (var4_4 != var5_8) return false;
                                    var2_3 = true;
                                } else {
                                    var2_3 = TextUtils.isEmpty((CharSequence)var4_4.getDigest()) == false ? var4_4.getDigest().equals(var5_8.getDigest()) : Arrays.equals(var4_4.getData(), var5_8.getData());
                                }
                                if (var2_3) ** GOTO lbl-1000
                                return false;
                            }
                            if (!(var4_4 instanceof String[])) break block11;
                            if (!(var5_7 instanceof String[])) {
                                return false;
                            }
                            if (Arrays.equals((String[])var4_4, (String[])var5_7)) ** GOTO lbl-1000
                            return false;
                        }
                        if (!(var4_4 instanceof long[])) break block12;
                        if (!(var5_7 instanceof long[])) {
                            return false;
                        }
                        if (Arrays.equals((long[])var4_4, (long[])var5_7)) ** GOTO lbl-1000
                        return false;
                    }
                    if (!(var4_4 instanceof float[])) break block13;
                    if (!(var5_7 instanceof float[])) {
                        return false;
                    }
                    if (Arrays.equals((float[])var4_4, (float[])var5_7)) ** GOTO lbl-1000
                    return false;
                }
                if (!(var4_4 instanceof byte[])) break block14;
                if (!(var5_7 instanceof byte[])) {
                    return false;
                }
                if (Arrays.equals((byte[])var4_4, (byte[])var5_7)) ** GOTO lbl-1000
                return false;
            }
            if (var4_4 != null && var5_7 != null) continue;
            if (var4_4 == var5_7) return true;
            return false;
        } while (var4_4.equals(var5_7));
        return false;
    }

    public <T> T get(String string2) {
        return (T)this.zzaln.get(string2);
    }

    public byte[] getByteArray(String string2) {
        Object object = this.zzaln.get(string2);
        if (object == null) {
            return null;
        }
        try {
            byte[] arrby = (byte[])object;
            return arrby;
        }
        catch (ClassCastException classCastException) {
            DataMap.zza(string2, object, "byte[]", classCastException);
            return null;
        }
    }

    public String getString(String string2) {
        Object object = this.zzaln.get(string2);
        if (object == null) {
            return null;
        }
        try {
            String string3 = (String)object;
            return string3;
        }
        catch (ClassCastException classCastException) {
            DataMap.zza(string2, object, "String", classCastException);
            return null;
        }
    }

    public int hashCode() {
        return this.zzaln.hashCode() * 29;
    }

    public Set<String> keySet() {
        return this.zzaln.keySet();
    }

    public void putAsset(String string2, Asset asset) {
        this.zzaln.put(string2, asset);
    }

    public void putBoolean(String string2, boolean bl) {
        this.zzaln.put(string2, bl);
    }

    public void putByte(String string2, byte by) {
        this.zzaln.put(string2, by);
    }

    public void putByteArray(String string2, byte[] arrby) {
        this.zzaln.put(string2, arrby);
    }

    public void putDataMap(String string2, DataMap dataMap) {
        this.zzaln.put(string2, dataMap);
    }

    public void putDataMapArrayList(String string2, ArrayList<DataMap> arrayList) {
        this.zzaln.put(string2, arrayList);
    }

    public void putDouble(String string2, double d) {
        this.zzaln.put(string2, d);
    }

    public void putFloat(String string2, float f) {
        this.zzaln.put(string2, Float.valueOf(f));
    }

    public void putFloatArray(String string2, float[] arrf) {
        this.zzaln.put(string2, arrf);
    }

    public void putInt(String string2, int n) {
        this.zzaln.put(string2, n);
    }

    public void putIntegerArrayList(String string2, ArrayList<Integer> arrayList) {
        this.zzaln.put(string2, arrayList);
    }

    public void putLong(String string2, long l) {
        this.zzaln.put(string2, l);
    }

    public void putLongArray(String string2, long[] arrl) {
        this.zzaln.put(string2, arrl);
    }

    public void putString(String string2, String string3) {
        this.zzaln.put(string2, string3);
    }

    public void putStringArray(String string2, String[] arrstring) {
        this.zzaln.put(string2, arrstring);
    }

    public void putStringArrayList(String string2, ArrayList<String> arrayList) {
        this.zzaln.put(string2, arrayList);
    }

    public int size() {
        return this.zzaln.size();
    }

    public String toString() {
        return this.zzaln.toString();
    }
}

