/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.Statistic;
import org.json.JSONObject;

public class StatisticResponse {
    public Statistic statistic;

    public static Statistic parse(JSONObject jSONObject) {
        Statistic statistic = new Statistic();
        statistic.deviceId = jSONObject.optString("deviceId");
        statistic.measurementsCount = jSONObject.optInt("measurementsCount");
        statistic.badMeasurementsCount = jSONObject.optInt("badMeasurementsCount");
        statistic.changedBatteriesCount = jSONObject.optInt("changedBatteriesCount");
        statistic.batteryStatus = jSONObject.optInt("batteryStatus");
        statistic.isActive = jSONObject.optBoolean("isActive");
        statistic.firmware = jSONObject.optString("firmware");
        return statistic;
    }
}

