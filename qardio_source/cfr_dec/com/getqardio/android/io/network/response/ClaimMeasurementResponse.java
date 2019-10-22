/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.ClaimMeasurement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClaimMeasurementResponse {
    public List<ClaimMeasurement> data;
    public List<String> type;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        int n;
        JSONArray jSONArray = jSONObject.optJSONArray("type");
        jSONObject = jSONObject.optJSONArray("data");
        this.type = new ArrayList<String>(jSONArray.length());
        this.data = new ArrayList<ClaimMeasurement>(jSONObject.length());
        for (n = 0; n < jSONArray.length(); ++n) {
            this.type.add(jSONArray.getString(n));
        }
        for (n = 0; n < jSONObject.length(); ++n) {
            jSONArray = (JSONObject)jSONObject.get(n);
            ClaimMeasurement claimMeasurement = new ClaimMeasurement();
            JSONObject jSONObject2 = new JSONObject(jSONArray.optString("data3", "{}"));
            claimMeasurement.id = Integer.valueOf(jSONArray.optString("id", "-1"));
            claimMeasurement.measurementId = jSONObject2.optString("id", null);
            claimMeasurement.userId = CustomApplication.getApplication().getCurrentUserId();
            claimMeasurement.fullname = jSONArray.optString("fullName", "");
            claimMeasurement.deviceId = jSONArray.optString("deviceId", "");
            claimMeasurement.data1 = jSONArray.optString("data1", "");
            claimMeasurement.data2 = jSONArray.optString("data2", "");
            claimMeasurement.data3 = jSONArray.optString("data3", "");
            claimMeasurement.note = jSONArray.optString("note", "");
            claimMeasurement.memberId = jSONArray.optLong("memberId", -1L);
            claimMeasurement.irregularHeartBeat = jSONArray.optBoolean("irregularHeartBeat", false);
            claimMeasurement.longitude = jSONArray.optDouble("longitude");
            claimMeasurement.longitude = jSONArray.optDouble("latitude");
            claimMeasurement.source = jSONArray.optInt("source");
            claimMeasurement.timezone = jSONArray.optString("timezone");
            claimMeasurement.tag = jSONArray.optInt("tag");
            claimMeasurement.measureDate = new Date(jSONArray.optLong("time"));
            claimMeasurement.syncStatus = 0;
            this.data.add(claimMeasurement);
        }
    }
}

