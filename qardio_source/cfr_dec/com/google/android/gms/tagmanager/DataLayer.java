/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzao;
import com.google.android.gms.tagmanager.zzap;
import com.google.android.gms.tagmanager.zzaq;
import com.google.android.gms.tagmanager.zzdj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static String[] zzkek = "gtm.lifetime".toString().split("\\.");
    private static final Pattern zzkel = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzkem;
    private final Map<String, Object> zzken;
    private final ReentrantLock zzkeo;
    private final LinkedList<Map<String, Object>> zzkep;
    private final zzc zzkeq;
    private final CountDownLatch zzker;

    DataLayer() {
        this(new zzao());
    }

    DataLayer(zzc zzc2) {
        this.zzkeq = zzc2;
        this.zzkem = new ConcurrentHashMap();
        this.zzken = new HashMap<String, Object>();
        this.zzkeo = new ReentrantLock();
        this.zzkep = new LinkedList();
        this.zzker = new CountDownLatch(1);
        this.zzkeq.zza(new zzap(this));
    }

    static /* synthetic */ CountDownLatch zza(DataLayer dataLayer) {
        return dataLayer.zzker;
    }

    static /* synthetic */ void zza(DataLayer dataLayer, Map map) {
        dataLayer.zzy(map);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(Map<String, Object> object, String string2, Collection<zza> collection) {
        Iterator iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            object = string2.length() == 0 ? "" : ".";
            String string3 = (String)entry.getKey();
            object = new StringBuilder(String.valueOf(string2).length() + String.valueOf(object).length() + String.valueOf(string3).length()).append(string2).append((String)object).append(string3).toString();
            if (entry.getValue() instanceof Map) {
                this.zza((Map)entry.getValue(), (String)object, collection);
                continue;
            }
            if (((String)object).equals("gtm.lifetime")) continue;
            collection.add(new zza((String)object, entry.getValue()));
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        int n = 0;
        while (n < list.size()) {
            Object object = list.get(n);
            if (object instanceof List) {
                if (!(list2.get(n) instanceof List)) {
                    list2.set(n, new ArrayList());
                }
                this.zzb((List)object, (List)list2.get(n));
            } else if (object instanceof Map) {
                if (!(list2.get(n) instanceof Map)) {
                    list2.set(n, new HashMap());
                }
                this.zzd((Map)object, (Map)list2.get(n));
            } else if (object != OBJECT_NOT_PRESENT) {
                list2.set(n, object);
            }
            ++n;
        }
        return;
    }

    private final void zzd(Map<String, Object> map, Map<String, Object> map2) {
        for (String string2 : map.keySet()) {
            Object object = map.get(string2);
            if (object instanceof List) {
                if (!(map2.get(string2) instanceof List)) {
                    map2.put(string2, new ArrayList());
                }
                this.zzb((List)object, (List)map2.get(string2));
                continue;
            }
            if (object instanceof Map) {
                if (!(map2.get(string2) instanceof Map)) {
                    map2.put(string2, new HashMap());
                }
                this.zzd((Map)object, (Map)map2.get(string2));
                continue;
            }
            map2.put(string2, object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Long zzlh(String string2) {
        long l;
        String string3;
        Matcher matcher = zzkel.matcher(string2);
        if (!matcher.matches()) {
            string2 = (string2 = String.valueOf(string2)).length() != 0 ? "unknown _lifetime: ".concat(string2) : new String("unknown _lifetime: ");
            zzdj.zzct(string2);
            return null;
        }
        try {
            l = Long.parseLong(matcher.group(1));
        }
        catch (NumberFormatException numberFormatException) {
            string3 = String.valueOf(string2);
            string3 = string3.length() != 0 ? "illegal number in _lifetime value: ".concat(string3) : new String("illegal number in _lifetime value: ");
            zzdj.zzcu(string3);
            l = 0L;
        }
        if (l <= 0L) {
            string2 = (string2 = String.valueOf(string2)).length() != 0 ? "non-positive _lifetime: ".concat(string2) : new String("non-positive _lifetime: ");
            zzdj.zzct(string2);
            return null;
        }
        string3 = matcher.group(2);
        if (string3.length() == 0) {
            return l;
        }
        switch (string3.charAt(0)) {
            default: {
                string2 = String.valueOf(string2);
                string2 = string2.length() != 0 ? "unknown units in _lifetime: ".concat(string2) : new String("unknown units in _lifetime: ");
            }
            case 's': {
                return l * 1000L;
            }
            case 'm': {
                return l * 1000L * 60L;
            }
            case 'h': {
                return l * 1000L * 60L * 60L;
            }
            case 'd': {
                return l * 1000L * 60L * 60L * 24L;
            }
        }
        zzdj.zzcu(string2);
        return null;
    }

    static Map<String, Object> zzn(String hashMap, Object object) {
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        String[] arrstring = ((String)((Object)hashMap)).toString().split("\\.");
        hashMap = hashMap2;
        for (int i = 0; i < arrstring.length - 1; ++i) {
            HashMap<String, HashMap<String, Object>> hashMap3 = new HashMap<String, HashMap<String, Object>>();
            hashMap.put(arrstring[i], hashMap3);
            hashMap = hashMap3;
        }
        hashMap.put(arrstring[arrstring.length - 1], (HashMap<String, Object>)object);
        return hashMap2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzy(Map<String, Object> var1_1) {
        this.zzkeo.lock();
        try {
            this.zzkep.offer(var1_1);
            if (this.zzkeo.getHoldCount() == 1) {
                var2_3 = 0;
lbl7:
                // 2 sources
                while ((var3_4 = this.zzkep.poll()) != null) {
                    var4_5 = this.zzken;
                    synchronized (var4_5) {
                        var5_6 = var3_4.keySet().iterator();
                        ** break block14
                    }
                }
            }
            ** GOTO lbl29
        }
        catch (Throwable var1_2) {
            this.zzkeo.unlock();
            throw var1_2;
        }
lbl-1000:
        // 1 sources
        {
            while (var5_6.hasNext()) {
                var6_7 = (String)var5_6.next();
                this.zzd(DataLayer.zzn(var6_7, var3_4.get(var6_7)), this.zzken);
            }
        }
        {
            var4_5 = this.zzkem.keySet().iterator();
            while (var4_5.hasNext()) {
                ((zzb)var4_5.next()).zzw(var3_4);
            }
            if (++var2_3 <= 500) ** GOTO lbl7
            this.zzkep.clear();
            throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
lbl29:
            // 1 sources
            var3_4 = DataLayer.zzz(var1_1);
            if (var3_4 != null) ** GOTO lbl-1000
            var3_4 = null;
lbl32:
            // 2 sources
            do {
                if (var3_4 != null) {
                    var4_5 = new ArrayList<E>();
                    this.zza(var1_1, "", (Collection<zza>)var4_5);
                    this.zzkeq.zza((List<zza>)var4_5, var3_4.longValue());
                }
                this.zzkeo.unlock();
                return;
                break;
            } while (true);
        }
lbl-1000:
        // 1 sources
        {
            var3_4 = DataLayer.zzlh(var3_4.toString());
            ** continue;
        }
    }

    private static Object zzz(Map<String, Object> object) {
        String[] arrstring = zzkek;
        int n = arrstring.length;
        int n2 = 0;
        do {
            Object object2;
            block4: {
                block3: {
                    object2 = object;
                    if (n2 >= n) break block3;
                    object2 = arrstring[n2];
                    if (object instanceof Map) break block4;
                    object2 = null;
                }
                return object2;
            }
            object = object.get(object2);
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void push(Map<String, Object> map) {
        try {
            this.zzker.await();
        }
        catch (InterruptedException interruptedException) {
            zzdj.zzcu("DataLayer.push: unexpected InterruptedException");
        }
        this.zzy(map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        Map<String, Object> map = this.zzken;
        synchronized (map) {
            CharSequence charSequence = new StringBuilder();
            Iterator<Map.Entry<String, Object>> iterator = this.zzken.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                ((StringBuilder)charSequence).append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            return ((StringBuilder)charSequence).toString();
        }
    }

    final void zza(zzb zzb2) {
        this.zzkem.put(zzb2, 0);
    }

    static final class zza {
        public final Object mValue;
        public final String zzbhb;

        zza(String string2, Object object) {
            this.zzbhb = string2;
            this.mValue = object;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final boolean equals(Object object) {
            block3: {
                block2: {
                    if (!(object instanceof zza)) break block2;
                    object = (zza)object;
                    if (this.zzbhb.equals(((zza)object).zzbhb) && this.mValue.equals(((zza)object).mValue)) break block3;
                }
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return Arrays.hashCode((Object[])new Integer[]{this.zzbhb.hashCode(), this.mValue.hashCode()});
        }

        public final String toString() {
            String string2 = this.zzbhb;
            String string3 = this.mValue.toString();
            return new StringBuilder(String.valueOf(string2).length() + 13 + String.valueOf(string3).length()).append("Key: ").append(string2).append(" value: ").append(string3).toString();
        }
    }

    static interface zzb {
        public void zzw(Map<String, Object> var1);
    }

    static interface zzc {
        public void zza(zzaq var1);

        public void zza(List<zza> var1, long var2);
    }

}

