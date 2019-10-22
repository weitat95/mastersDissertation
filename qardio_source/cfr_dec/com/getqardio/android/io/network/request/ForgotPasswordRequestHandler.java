/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.ForgotPasswordResponse;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForgotPasswordRequestHandler
extends RequestHandler {
    public static Intent createForgotPasswordIntent(Context context, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 5, -1L);
        context.putExtra("com.getqardio.android.extra.TARGET_EMAIL", string2);
        return context;
    }

    private RequestHandler.ProcessResult forgotPassword(Context context, String object) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object = ForgotPasswordRequestHandler.requestForgotPassword((String)object))).isSuccessful() && (((BaseResponse)object).getError() == null || ((List)((BaseResponse)object).getError()).size() == 0)) {
            this.notifySuccessfulCreateNewUser(context);
            return processResult;
        }
        processResult = RequestHandler.ProcessResult.UNKNOWN_ERROR;
        this.notifyFailedCreateNewUser(context, (List)((BaseResponse)object).getError());
        return processResult;
    }

    private void notifyFailedCreateNewUser(Context context, List<BaseError> list) {
        Intent intent = NotificationHelper.ForgotPasswordNotification.createErrorsResult();
        ErrorHelper.putErrorsToIntent(intent, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void notifySuccessfulCreateNewUser(Context context) {
        Intent intent = NotificationHelper.ForgotPasswordNotification.createSuccessResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static BaseResponse<ForgotPasswordResponse, List<BaseError>> requestForgotPassword(String object) {
        Object object2 = new ArrayList<BasicNameValuePair>();
        object2.add(new BasicNameValuePair("userId", Utils.encodeString((String)object)));
        object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.ForgotPassword.URI, object2);
        if (((NetworkRequestHelper.HttpResponse)object).isSuccessful()) {
            return JSONParser.parseForgotPassword(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        object2 = new BaseResponse();
        if (((NetworkRequestHelper.HttpResponse)object).getResponseCode() == 400) {
            ((BaseResponse)object2).setStatus(BaseResponse.Status.FAILURE);
            ((BaseResponse)object2).setError(Arrays.asList(ErrorHelper.makeWrongCredentials()));
            return object2;
        }
        BaseError.setNetworkErrorResult(object2);
        return object2;
    }

    @Override
    public boolean checkUserId(Intent intent, Long l, long l2) {
        return true;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        return this.forgotPassword(context, intent.getStringExtra("com.getqardio.android.extra.TARGET_EMAIL"));
    }
}

