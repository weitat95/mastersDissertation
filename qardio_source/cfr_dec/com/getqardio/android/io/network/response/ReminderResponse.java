/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.io.network.JSONParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReminderResponse {
    public Integer days;
    public Long id;
    public Boolean repeat;
    public Long time;
    public String type;

    public static List<Reminder> convertToReminderList(List<ReminderResponse> object) {
        ArrayList<Reminder> arrayList = new ArrayList<Reminder>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            ReminderResponse reminderResponse = (ReminderResponse)object.next();
            Reminder reminder = new Reminder();
            reminder.reminderId = reminderResponse.id;
            reminder.remindTime = reminderResponse.time;
            reminder.days = reminderResponse.days;
            reminder.repeat = reminderResponse.repeat;
            reminder.type = reminderResponse.type;
            arrayList.add(reminder);
        }
        return arrayList;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static ReminderResponse parse(String var0) {
        var5_4 = null;
        var0 = new JSONObject(var0);
        var4_6 = new ReminderResponse();
        var4_6.id = var0.optLong("id", -1L);
        var4_6.time = var0.optLong("time", 0L);
        var5_4 = var0.getJSONArray("days");
        var3_7 = var5_4.length();
        var2_8 = 0;
        for (var1_9 = 0; var1_9 < var3_7; var2_8 |= 1 << var5_4.getInt(var1_9), ++var1_9) {
        }
        var4_6.days = var2_8;
        ** GOTO lbl20
        {
            catch (JSONException var5_5) {}
        }
        catch (JSONException var0_1) {
            block8: {
                var4_6 = var5_4;
                break block8;
lbl20:
                // 2 sources
                try {
                    var4_6.repeat = var0.optBoolean("repeat", false);
                    var4_6.type = var0.optString("type", "");
                    return var4_6;
                }
                catch (JSONException var0_3) {}
            }
            var0_2.printStackTrace();
            return var4_6;
        }
    }

    public static Long parseId(String string2) {
        block2: {
            long l;
            try {
                l = JSONParser.getResponseData(string2).optLong("id", -1L);
                if (l == -1L) break block2;
            }
            catch (JSONException jSONException) {
                return null;
            }
            return l;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<ReminderResponse> parseList(String string2) {
        ArrayList<ReminderResponse> arrayList;
        int i;
        int n;
        try {
            string2 = JSONParser.getResponseData(string2).getJSONArray("reminders");
            n = string2.length();
            arrayList = new ArrayList<ReminderResponse>(n);
            i = 0;
        }
        catch (JSONException jSONException) {
            return null;
        }
        while (i < n) {
            try {
                arrayList.add(ReminderResponse.parse(string2.getString(i)));
                ++i;
            }
            catch (JSONException jSONException) {
                return null;
            }
        }
        return arrayList;
    }
}

