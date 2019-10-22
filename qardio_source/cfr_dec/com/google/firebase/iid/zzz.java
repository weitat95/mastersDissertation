/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

final class zzz {
    private static final long zznzu = TimeUnit.DAYS.toMillis(7L);
    private long timestamp;
    private String zzifm;
    final String zzldj;

    private zzz(String string2, String string3, long l) {
        this.zzldj = string2;
        this.zzifm = string3;
        this.timestamp = l;
    }

    static String zzc(String string2, String string3, long l) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("token", (Object)string2);
            jSONObject.put("appVersion", (Object)string3);
            jSONObject.put("timestamp", l);
            string2 = jSONObject.toString();
            return string2;
        }
        catch (JSONException jSONException) {
            String string4 = String.valueOf((Object)jSONException);
            Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(string4).length() + 24).append("Failed to encode token: ").append(string4).toString());
            return null;
        }
    }

    static zzz zzrn(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if (((String)object).startsWith("{")) {
            try {
                object = new JSONObject((String)object);
                object = new zzz(object.getString("token"), object.getString("appVersion"), object.getLong("timestamp"));
                return object;
            }
            catch (JSONException jSONException) {
                String string2 = String.valueOf((Object)jSONException);
                Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(string2).length() + 23).append("Failed to parse token: ").append(string2).toString());
                return null;
            }
        }
        return new zzz((String)object, null, 0L);
    }

    final boolean zzro(String string2) {
        return System.currentTimeMillis() > this.timestamp + zznzu || !string2.equals(this.zzifm);
    }
}

