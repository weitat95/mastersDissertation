/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.util;

import com.getqardio.android.data.BaseUser;
import com.getqardio.android.exceptions.CommandException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseUserUtil {
    public static JSONObject createAddCommand(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("add", (Object)jSONObject);
            return jSONObject2;
        }
        catch (JSONException jSONException) {
            return jSONObject2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static JSONObject createBaseUserJson(BaseUser baseUser, Integer n) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", (Object)String.valueOf(baseUser.id));
        jSONObject.put("name", (Object)baseUser.name);
        jSONObject.put("male", (Object)String.valueOf(baseUser.male));
        jSONObject.put("height", (Object)String.valueOf(baseUser.height));
        jSONObject.put("born", (Object)String.valueOf(baseUser.born));
        jSONObject.put("units", (Object)String.valueOf(baseUser.units));
        jSONObject.put("goal", (Object)String.valueOf(baseUser.goal));
        jSONObject.put("rate", (Object)String.valueOf(baseUser.rate));
        jSONObject.put("token", (Object)baseUser.authToken);
        if (baseUser.display != null && baseUser.display != 0) {
            jSONObject.put("ui", (Object)baseUser.display.toString());
            return jSONObject;
        } else {
            if (n == null) return jSONObject;
            {
                jSONObject.put("ui", (Object)String.valueOf(n));
                return jSONObject;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JSONObject createGoalCommand(float f, float f2, int n, int n2) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        JSONObject jSONObject2 = new JSONObject();
        int n3 = (int)(10.0f * f);
        int n4 = (int)(10.0f * f2);
        try {
            jSONObject = new JSONObject();
            jSONObject.put("goal", (Object)Integer.toString(n3));
            jSONObject.put("rate", (Object)Integer.toString(n4));
            jSONArray = new JSONArray();
            jSONObject2.put("users", (Object)jSONArray);
            n3 = 0;
        }
        catch (JSONException jSONException) {
            throw new CommandException(String.format("Command could not be created due to %s", jSONException.getMessage()));
        }
        do {
            if (n3 < n2) {
                if (n3 == n) {
                    jSONArray.put((Object)jSONObject);
                } else {
                    jSONArray.put((Object)new JSONObject());
                }
            } else {
                return jSONObject2;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JSONObject createImpedanceCommand(boolean bl, boolean bl2) {
        JSONObject jSONObject = new JSONObject();
        String string2 = bl ? "1" : "0";
        String string3 = bl2 ? "1" : "0";
        try {
            jSONObject.put("z", (Object)string2);
            jSONObject.put("haptic", (Object)string3);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new CommandException(String.format("Command could not be created due to %s", jSONException.getMessage()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JSONObject createModeUpdateCommand(int n, int n2, int n3) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject = new JSONObject();
            jSONObject.put("ui", (Object)Integer.toString(n));
            jSONArray = new JSONArray();
            jSONObject2.put("users", (Object)jSONArray);
            n = 0;
        }
        catch (JSONException jSONException) {
            throw new CommandException(String.format("Command could not be created due to %s", jSONException.getMessage()));
        }
        do {
            if (n < n3) {
                if (n == n2) {
                    jSONArray.put((Object)jSONObject);
                } else {
                    jSONArray.put((Object)new JSONObject());
                }
            } else {
                return jSONObject2;
            }
            ++n;
        } while (true);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static JSONObject createOnboardCommand(BaseUser baseUser, int n, boolean bl, boolean bl2, int n2, int n3) {
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (n2 == -1) {
            try {
                JSONObject jSONObject3;
                jSONObject2 = jSONObject3 = BaseUserUtil.createAddCommand(BaseUserUtil.createBaseUserJson(baseUser, n));
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
        } else {
            try {
                JSONObject jSONObject4;
                jSONObject2 = jSONObject4 = BaseUserUtil.createBaseUserJson(baseUser, n);
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
        }
        if (bl) {
            String string2 = "1";
        } else {
            String string3 = "0";
        }
        String string4 = bl2 ? "1" : "0";
        try {
            void var7_12;
            jSONObject2.put("ui", (Object)Integer.toString(n));
            jSONArray = new JSONArray();
            jSONObject.put("z", (Object)var7_12);
            jSONObject.put("haptic", (Object)string4);
            if (n2 == -1) {
                jSONObject.put("add", (Object)BaseUserUtil.createBaseUserJson(baseUser, n));
                return jSONObject;
            }
            jSONObject.put("users", (Object)jSONArray);
            n = 0;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return jSONObject;
        }
        while (n < n3) {
            if (n == n2) {
                jSONArray.put((Object)jSONObject2);
            } else {
                jSONArray.put((Object)new JSONObject());
            }
            ++n;
        }
        return jSONObject;
    }
}

