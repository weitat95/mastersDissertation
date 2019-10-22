/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.util.Pair
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import com.getqardio.android.datamodel.LogoutResponse;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LogoutRequestHandler
extends RequestHandler {
    public static Intent createLogoutIntent(Context context, String string2, String string3) {
        context = AsyncReceiverHandler.createIntent(context, 14, -1L);
        context.putExtra("com.getqardio.android.extra.AUTH_TOKEN", string2);
        context.putExtra("com.getqardio.android.extra.DEVICE_TOKEN", string3);
        return context;
    }

    public static BaseResponse<LogoutResponse, List<BaseError>> requestLogout(String baseResponse, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(2);
        arrayList.add(new BasicNameValuePair("authToken", URLEncoder.encode((String)((Object)baseResponse))));
        arrayList.add(new BasicNameValuePair("deviceToken", URLEncoder.encode(string2)));
        baseResponse = new ArrayList(1);
        baseResponse.add(new Pair((Object)"Content-Type", (Object)"application/x-www-form-urlencoded; charset=utf-8"));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.Logout.URI, arrayList, (Collection<Pair<String, String>>)((Object)baseResponse));
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseLogout(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<LogoutResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    @Override
    public boolean checkUserId(Intent intent, Long l, long l2) {
        return true;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        LogoutRequestHandler.requestLogout(intent.getStringExtra("com.getqardio.android.extra.AUTH_TOKEN"), intent.getStringExtra("com.getqardio.android.extra.DEVICE_TOKEN"));
        return RequestHandler.ProcessResult.SUCCESS;
    }
}

