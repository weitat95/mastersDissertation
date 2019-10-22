/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Average;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.LastMeasurement;
import com.google.gson.annotations.SerializedName;

public class Metadata {
    @SerializedName(value="averagelastDay")
    public Average averageLastDay;
    @SerializedName(value="averagelastMonth")
    public Average averageLastMonth;
    @SerializedName(value="averagelastWeek")
    public Average averageLastWeek;
    @SerializedName(value="lastMeasurement")
    public LastMeasurement lastMeasurement;
    @SerializedName(value="type")
    public String type;

    public Average getAverageLastDay() {
        return this.averageLastDay;
    }

    public Average getAverageLastMonth() {
        return this.averageLastMonth;
    }

    public Average getAverageLastWeek() {
        return this.averageLastWeek;
    }

    public LastMeasurement getLastMeasurement() {
        return this.lastMeasurement;
    }

    public String getType() {
        return this.type;
    }
}

