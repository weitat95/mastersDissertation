/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package net.danlew.android.joda;

import android.util.Log;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResUtils {
    private static Map<Class<?>, Map<String, Integer>> sIdentifierCache = new ConcurrentHashMap();

    private static String convertPathToResource(String object) {
        File file;
        object = new File((String)object);
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            arrayList.add(((File)object).getName());
            file = ((File)object).getParentFile();
            object = file;
        } while (file != null);
        object = new StringBuffer();
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            if (((StringBuffer)object).length() > 0) {
                ((StringBuffer)object).append("_");
            }
            ((StringBuffer)object).append((String)arrayList.get(i));
        }
        return ((StringBuffer)object).toString().replace('-', '_').replace("+", "plus").toLowerCase(Locale.US);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getIdentifier(Class<?> class_, String string2) {
        Map<Object, Object> map;
        if (!sIdentifierCache.containsKey(class_)) {
            map = new ConcurrentHashMap();
            sIdentifierCache.put(class_, map);
        } else {
            map = sIdentifierCache.get(class_);
        }
        if (map.containsKey(string2)) {
            return (Integer)map.get(string2);
        }
        try {
            int n;
            int n2 = n = class_.getField(string2).getInt(null);
            if (n == 0) return n2;
            map.put(string2, n);
            return n;
        }
        catch (Exception exception) {
            Log.e((String)"JodaTimeAndroid", (String)("Failed to retrieve identifier: type=" + class_ + " name=" + string2), (Throwable)exception);
            return 0;
        }
    }

    public static String getTzResource(String string2) {
        return "joda_" + ResUtils.convertPathToResource(string2);
    }
}

