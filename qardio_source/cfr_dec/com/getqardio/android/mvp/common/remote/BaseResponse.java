/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName(value="status")
    private String status;

    public BaseResponse(String string2) {
        this.status = string2;
    }

    public String getStatus() {
        return this.status;
    }
}

