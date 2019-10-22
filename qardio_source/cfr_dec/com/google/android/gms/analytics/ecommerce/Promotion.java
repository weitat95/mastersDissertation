/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzh;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Promotion {
    private Map<String, String> zzdsl = new HashMap<String, String>();

    public String toString() {
        return zzh.zzr(this.zzdsl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final Map<String, String> zzdr(String string2) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> iterator = this.zzdsl.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String string3 = String.valueOf(string2);
            String string4 = String.valueOf(entry.getKey());
            string3 = string4.length() != 0 ? string3.concat(string4) : new String(string3);
            hashMap.put(string3, entry.getValue());
        }
        return hashMap;
    }
}

