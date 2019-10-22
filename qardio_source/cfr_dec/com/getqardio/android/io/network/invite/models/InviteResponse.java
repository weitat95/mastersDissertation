/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.invite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteResponse {
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

