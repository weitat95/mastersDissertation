/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class zzm {
    private static final Pattern zzgeo = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern zzgep = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern zzgeq = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String decode(String string2, String string3) {
        if (string3 == null) {
            string3 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(string2, string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException(unsupportedEncodingException);
        }
    }

    public static Map<String, String> zza(URI object, String string2) {
        Map<String, String> map = Collections.emptyMap();
        Object object2 = ((URI)object).getRawQuery();
        object = map;
        if (object2 != null) {
            object = map;
            if (((String)object2).length() > 0) {
                map = new HashMap<String, String>();
                object2 = new Scanner((String)object2);
                ((Scanner)object2).useDelimiter("&");
                while (((Scanner)object2).hasNext()) {
                    String[] arrstring = ((Scanner)object2).next().split("=");
                    if (arrstring.length == 0 || arrstring.length > 2) {
                        throw new IllegalArgumentException("bad parameter");
                    }
                    String string3 = zzm.decode(arrstring[0], string2);
                    object = null;
                    if (arrstring.length == 2) {
                        object = zzm.decode(arrstring[1], string2);
                    }
                    map.put(string3, (String)object);
                }
                object = map;
            }
        }
        return object;
    }
}

