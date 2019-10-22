/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqx;
import com.google.android.gms.internal.zzasl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzarq {
    private final Map<String, String> zzbsr;
    private final List<zzaqx> zzdxt;
    private final long zzdxu;
    private final long zzdxv;
    private final int zzdxw;
    private final boolean zzdxx;
    private final String zzdxy;

    public zzarq(zzapz zzapz2, Map<String, String> map, long l, boolean bl) {
        this(zzapz2, map, l, bl, 0L, 0, null);
    }

    public zzarq(zzapz zzapz2, Map<String, String> map, long l, boolean bl, long l2, int n) {
        this(zzapz2, map, l, bl, l2, n, null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public zzarq(zzapz zzapz2, Map<String, String> iterator, long l, boolean bl, long l2, int n, List<zzaqx> hashMap) {
        void var10_9;
        zzbq.checkNotNull(zzapz2);
        zzbq.checkNotNull(iterator);
        this.zzdxv = l;
        this.zzdxx = bl;
        this.zzdxu = l2;
        this.zzdxw = n;
        if (hashMap != null) {
            HashMap<String, String> hashMap2 = hashMap;
        } else {
            List list = Collections.emptyList();
        }
        this.zzdxt = var10_9;
        this.zzdxy = zzarq.zzt(hashMap);
        hashMap = new HashMap();
        for (Map.Entry entry : iterator.entrySet()) {
            String string2;
            if (!zzarq.zzn(entry.getKey()) || (string2 = zzarq.zza(zzapz2, entry.getKey())) == null) continue;
            hashMap.put(string2, zzarq.zzb(zzapz2, entry.getValue()));
        }
        for (Map.Entry entry : iterator.entrySet()) {
            String string3;
            if (zzarq.zzn(entry.getKey()) || (string3 = zzarq.zza(zzapz2, entry.getKey())) == null) continue;
            hashMap.put(string3, zzarq.zzb(zzapz2, entry.getValue()));
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdxy)) {
            zzasl.zzb(hashMap, "_v", this.zzdxy);
            if (this.zzdxy.equals("ma4.0.0") || this.zzdxy.equals("ma4.0.1")) {
                hashMap.remove("adid");
            }
        }
        this.zzbsr = Collections.unmodifiableMap(hashMap);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String zza(zzapz object, Object object2) {
        Object object3;
        if (object2 == null) {
            return null;
        }
        object2 = object3 = object2.toString();
        if (((String)object3).startsWith("&")) {
            object2 = ((String)object3).substring(1);
        }
        int n = ((String)object2).length();
        object3 = object2;
        if (n > 256) {
            object3 = ((String)object2).substring(0, 256);
            ((zzapz)object).zzc("Hit param name is too long and will be trimmed", n, object3);
        }
        object = object3;
        if (!TextUtils.isEmpty((CharSequence)object3)) return object;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzb(zzapz zzapz2, Object object) {
        object = object == null ? "" : object.toString();
        int n = ((String)object).length();
        Object object2 = object;
        if (n > 8192) {
            object2 = ((String)object).substring(0, 8192);
            zzapz2.zzc("Hit param value is too long and will be trimmed", n, object2);
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final String zzk(String string2, String string3) {
        zzbq.zzgm(string2);
        boolean bl = !string2.startsWith("&");
        zzbq.checkArgument(bl, "Short param name required");
        string2 = this.zzbsr.get(string2);
        if (string2 != null) {
            return string2;
        }
        return string3;
    }

    private static boolean zzn(Object object) {
        if (object == null) {
            return false;
        }
        return object.toString().startsWith("&");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzt(List<zzaqx> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                zzaqx zzaqx2 = (zzaqx)object.next();
                if (!"appendVersion".equals(zzaqx2.getId())) continue;
                object = zzaqx2.getValue();
                break;
            }
        } else {
            object = null;
        }
        if (TextUtils.isEmpty(object)) {
            return null;
        }
        return object;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.zzdxv);
        if (this.zzdxu != 0L) {
            stringBuffer.append(", dbId=").append(this.zzdxu);
        }
        if (this.zzdxw != 0) {
            stringBuffer.append(", appUID=").append(this.zzdxw);
        }
        ArrayList<String> arrayList = new ArrayList<String>(this.zzbsr.keySet());
        Collections.sort(arrayList);
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            String string2 = arrayList.get(i);
            stringBuffer.append(", ");
            stringBuffer.append(string2);
            stringBuffer.append("=");
            stringBuffer.append(this.zzbsr.get(string2));
        }
        return stringBuffer.toString();
    }

    public final Map<String, String> zzjh() {
        return this.zzbsr;
    }

    public final int zzzg() {
        return this.zzdxw;
    }

    public final long zzzh() {
        return this.zzdxu;
    }

    public final long zzzi() {
        return this.zzdxv;
    }

    public final List<zzaqx> zzzj() {
        return this.zzdxt;
    }

    public final boolean zzzk() {
        return this.zzdxx;
    }

    public final long zzzl() {
        return zzasl.zzei(this.zzk("_s", "0"));
    }

    public final String zzzm() {
        return this.zzk("_m", "");
    }
}

