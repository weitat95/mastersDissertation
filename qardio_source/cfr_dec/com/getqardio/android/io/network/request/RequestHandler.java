/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.utils.ErrorHelper;
import java.util.Iterator;
import java.util.List;

public abstract class RequestHandler {
    public boolean checkUserId(Intent intent, Long l, long l2) {
        return l != null && l2 == l;
    }

    protected ProcessResult getErrorCode(List<BaseError> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (ErrorHelper.getErrorId(((BaseError)object.next()).messageKey) != 6) continue;
            return ProcessResult.INVALID_TOKEN;
        }
        return ProcessResult.UNKNOWN_ERROR;
    }

    public abstract ProcessResult processIntent(Context var1, Intent var2, long var3, String var5);

    public static enum ProcessResult {
        SUCCESS,
        INVALID_TOKEN,
        UNKNOWN_REQUEST,
        UNKNOWN_ERROR;

    }

}

