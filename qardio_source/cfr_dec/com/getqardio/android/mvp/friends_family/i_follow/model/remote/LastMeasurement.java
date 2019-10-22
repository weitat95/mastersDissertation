/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.google.gson.annotations.SerializedName;

public class LastMeasurement {
    @SerializedName(value="data1")
    public String data1;
    @SerializedName(value="data2")
    public String data2;
    @SerializedName(value="data3")
    public String data3;
    @SerializedName(value="date")
    public Long date;

    public String getData1() {
        return this.data1;
    }

    public String getData2() {
        return this.data2;
    }

    public String getData3() {
        return this.data3;
    }

    public Long getDate() {
        return this.date;
    }
}

