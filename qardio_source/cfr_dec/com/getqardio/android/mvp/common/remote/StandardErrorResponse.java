/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class StandardErrorResponse
extends BaseResponse {
    private Map<String, String> errorData;
    @SerializedName(value="data")
    private List<Map<String, String>> errorDatas;
    @SerializedName(value="error")
    private String errorText;

    public StandardErrorResponse(String string2) {
        super(string2);
    }

    public void setErrorData(Map<String, String> map) {
        this.errorData = map;
    }

    public void setErrorDatas(List<Map<String, String>> list) {
        this.errorDatas = list;
    }

    public void setErrorText(String string2) {
        this.errorText = string2;
    }
}

