/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.io.network.JSONParser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BPMeasurementsResponse {
    public List<BPMeasurement> measurements;
    public int totalCount = 0;

    public void formJsonObject(JSONObject jSONObject) throws JSONException {
        this.totalCount = jSONObject.optInt("totalCount", 0);
        jSONObject = jSONObject.getJSONArray("records");
        this.measurements = new ArrayList<BPMeasurement>(jSONObject.length());
        for (int i = 0; i < jSONObject.length(); ++i) {
            BPMeasurement bPMeasurement = new BPMeasurement();
            JSONParser.parseBPMeasurement(jSONObject.getJSONObject(i), bPMeasurement);
            this.measurements.add(bPMeasurement);
        }
    }
}

