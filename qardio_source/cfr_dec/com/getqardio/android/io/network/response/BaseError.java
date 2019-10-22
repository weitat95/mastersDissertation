/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.utils.ErrorHelper;
import java.util.ArrayList;
import java.util.List;

public class BaseError {
    public String defaultMessageText;
    public int id;
    public String messageKey;

    public BaseError() {
        this("", "");
    }

    public BaseError(String string2, String string3) {
        this.messageKey = string2;
        this.defaultMessageText = string3;
        this.id = ErrorHelper.getErrorId(string2);
    }

    public static final void setNetworkErrorResult(BaseResponse<?, List<BaseError>> baseResponse) {
        baseResponse.setStatus(BaseResponse.Status.NETWORK_ERROR);
        baseResponse.setData(null);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        arrayList.add(ErrorHelper.makeNetworkError());
        baseResponse.setError(arrayList);
    }

    public static final void setUnknownErrorResult(BaseResponse<?, List<BaseError>> baseResponse) {
        baseResponse.setStatus(BaseResponse.Status.UNKNOWN);
        baseResponse.setData(null);
        if (baseResponse.getError() == null) {
            baseResponse.setError(new ArrayList(1));
        }
        baseResponse.getError().add(ErrorHelper.makeParseJsonError());
    }
}

