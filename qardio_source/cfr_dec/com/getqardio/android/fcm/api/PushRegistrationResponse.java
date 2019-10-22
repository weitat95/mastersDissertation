/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushRegistrationResponse {
    @Expose
    @SerializedName(value="data")
    private String data = null;
    @Expose
    @SerializedName(value="status")
    private String status;

    public String getStatus() {
        return this.status;
    }
}

