/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.joda.time.DateTimeUtils;
import org.joda.time.tz.NameProvider;

public class DefaultNameProvider
implements NameProvider {
    private HashMap<Locale, Map<String, Map<String, Object>>> iByLocaleCache = this.createCache();
    private HashMap<Locale, Map<String, Map<Boolean, Object>>> iByLocaleCache2 = this.createCache();

    private HashMap createCache() {
        return new HashMap(7);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String[] getNameSet(Locale object, String string2, String string3) {
        Object var8_4 = null;
        synchronized (this) {
            void var3_3;
            void var2_2;
            HashMap<Locale, Map<String, Map<String, Object>>> hashMap;
            if (object == null) return null;
            if (var2_2 == null) return null;
            if (var3_3 == null) {
                return null;
            }
            HashMap<Locale, Map<String, Map<String, Object>>> hashMap2 = this.iByLocaleCache.get(object);
            if (hashMap2 == null) {
                hashMap = this.iByLocaleCache;
                hashMap2 = this.createCache();
                hashMap.put((Locale)object, (Map<String, Map<String, Object>>)hashMap2);
            }
            HashMap hashMap3 = hashMap2.get(var2_2);
            hashMap = hashMap3;
            if (hashMap3 != null) return (String[])hashMap.get(var3_3);
            hashMap3 = this.createCache();
            hashMap2.put((Locale)var2_2, hashMap3);
            hashMap = DateTimeUtils.getDateFormatSymbols(Locale.ENGLISH).getZoneStrings();
            int n = ((HashMap<Locale, Map<String, Map<String, Object>>>)hashMap).length;
            int n2 = 0;
            do {
                if (n2 < n) {
                    hashMap2 = hashMap[n2];
                    if (hashMap2 != null && ((HashMap<Locale, Map<String, Map<String, Object>>>)hashMap2).length >= 5 && var2_2.equals(hashMap2[0])) {
                        break;
                    }
                } else {
                    hashMap2 = null;
                    break;
                }
                ++n2;
            } while (true);
            hashMap = DateTimeUtils.getDateFormatSymbols((Locale)object).getZoneStrings();
            n = ((HashMap<Locale, Map<String, Map<String, Object>>>)hashMap).length;
            n2 = 0;
            do {
                object = var8_4;
                if (n2 >= n || (object = hashMap[n2]) != null && ((String[])object).length >= 5 && var2_2.equals(object[0])) {
                    hashMap = hashMap3;
                    if (hashMap2 == null) return (String[])hashMap.get(var3_3);
                    hashMap = hashMap3;
                    if (object == null) return (String[])hashMap.get(var3_3);
                    hashMap3.put(hashMap2[2], new String[]{object[2], object[1]});
                    if (((String)((Object)hashMap2[2])).equals(hashMap2[4])) {
                        hashMap3.put(hashMap2[4] + "-Summer", new String[]{object[4], object[3]});
                        hashMap = hashMap3;
                        return (String[])hashMap.get(var3_3);
                    } else {
                        hashMap3.put(hashMap2[4], new String[]{object[4], object[3]});
                        hashMap = hashMap3;
                    }
                    return (String[])hashMap.get(var3_3);
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String[] getNameSet(Locale object, String map, String object2, boolean bl) {
        Object var8_5 = null;
        synchronized (this) {
            String string2;
            HashMap<Locale, Map<String, Map<Boolean, Object>>> hashMap4;
            void var4_4;
            HashMap<Locale, Map<String, Map<Boolean, Object>>> hashMap2;
            HashMap hashMap3;
            block9: {
                if (object == null) return null;
                if (hashMap4 == null) return null;
                if (string2 == null) {
                    return null;
                }
                string2 = hashMap4;
                if (((String)((Object)hashMap4)).startsWith("Etc/")) {
                    string2 = ((String)((Object)hashMap4)).substring(4);
                }
                if ((hashMap4 = this.iByLocaleCache2.get(object)) == null) {
                    hashMap2 = this.iByLocaleCache2;
                    hashMap4 = this.createCache();
                    hashMap2.put((Locale)object, (Map<String, Map<Boolean, Object>>)hashMap4);
                }
                hashMap3 = hashMap4.get(string2);
                hashMap2 = hashMap3;
                if (hashMap3 != null) return (String[])hashMap2.get((boolean)var4_4);
                hashMap3 = this.createCache();
                hashMap4.put((Locale)((Object)string2), hashMap3);
                for (HashMap<Locale, Map<String, Map<Boolean, Object>>> hashMap4 : DateTimeUtils.getDateFormatSymbols(Locale.ENGLISH).getZoneStrings()) {
                    if (hashMap4 == null || ((HashMap<Locale, Map<String, Map<Boolean, Object>>>)hashMap4).length < 5 || !string2.equals(hashMap4[0])) {
                        continue;
                    }
                    break block9;
                }
                hashMap4 = null;
            }
            hashMap2 = DateTimeUtils.getDateFormatSymbols((Locale)object).getZoneStrings();
            int n = ((HashMap<Locale, Map<String, Map<Boolean, Object>>>)hashMap2).length;
            int n2 = 0;
            do {
                object = var8_5;
                if (n2 >= n || (object = hashMap2[n2]) != null && ((String[])object).length >= 5 && string2.equals(object[0])) {
                    hashMap2 = hashMap3;
                    if (hashMap4 == null) return (String[])hashMap2.get((boolean)var4_4);
                    hashMap2 = hashMap3;
                    if (object == null) return (String[])hashMap2.get((boolean)var4_4);
                    hashMap3.put(Boolean.TRUE, new String[]{object[2], object[1]});
                    hashMap3.put(Boolean.FALSE, new String[]{object[4], object[3]});
                    hashMap2 = hashMap3;
                    return (String[])hashMap2.get((boolean)var4_4);
                }
                ++n2;
            } while (true);
        }
    }

    @Override
    public String getName(Locale arrstring, String string2, String string3) {
        if ((arrstring = this.getNameSet((Locale)arrstring, string2, string3)) == null) {
            return null;
        }
        return arrstring[1];
    }

    public String getName(Locale arrstring, String string2, String string3, boolean bl) {
        if ((arrstring = this.getNameSet((Locale)arrstring, string2, string3, bl)) == null) {
            return null;
        }
        return arrstring[1];
    }

    @Override
    public String getShortName(Locale arrstring, String string2, String string3) {
        if ((arrstring = this.getNameSet((Locale)arrstring, string2, string3)) == null) {
            return null;
        }
        return arrstring[0];
    }

    public String getShortName(Locale arrstring, String string2, String string3, boolean bl) {
        if ((arrstring = this.getNameSet((Locale)arrstring, string2, string3, bl)) == null) {
            return null;
        }
        return arrstring[0];
    }
}

