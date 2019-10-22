/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static String optionalStringKey(JSONObject jSONObject, String string2) throws JSONException {
        if (jSONObject.has(string2) && !jSONObject.isNull(string2)) {
            return jSONObject.getString(string2);
        }
        return null;
    }
}

