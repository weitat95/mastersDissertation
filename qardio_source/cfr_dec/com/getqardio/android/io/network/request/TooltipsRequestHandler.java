/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.getqardio.android.datamodel.Tooltip;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.TooltipsResponse;
import com.getqardio.android.provider.TooltipHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class TooltipsRequestHandler
extends RequestHandler {
    public static Intent createTooltipsIntent(Context context, long l) {
        return AsyncReceiverHandler.createIntent(context, 11, l);
    }

    private RequestHandler.ProcessResult getTooltips(Context context, String baseResponse) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetTolltips.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful() && !TextUtils.isEmpty((CharSequence)((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody())) {
            if ((baseResponse = JSONParser.parseTooltipsList(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody())).isSuccessful()) {
                TooltipHelper.saveTooltips(context, baseResponse.getData().tooltips);
                return processResult;
            }
            return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        return this.getTooltips(context, string2);
    }
}

