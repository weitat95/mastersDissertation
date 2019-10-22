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
import com.getqardio.android.datamodel.Statistic;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.StatisticResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class StatisticRequestHandler
extends RequestHandler {
    private static Intent createDefaultIntent(Context context, int n, long l, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 16, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", n);
        context.putExtra("com.getqardio.android.extra.DEVICE_ID", string2);
        return context;
    }

    public static Intent createGetStatisticIntent(Context context, long l, String string2) {
        return StatisticRequestHandler.createDefaultIntent(context, 1, l, string2);
    }

    public static Intent createUpdateStatisticIntent(Context context, long l, String string2) {
        return StatisticRequestHandler.createDefaultIntent(context, 2, l, string2);
    }

    private RequestHandler.ProcessResult getStatistic(Context context, String object, long l, String string2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object = this.requestGetStatistic((String)object, string2))).isSuccessful()) {
            object = ((StatisticResponse)object.getData()).statistic;
            ((Statistic)object).userId = l;
            DataHelper.StatisticHelper.insertStatistic(context, (Statistic)object);
            return processResult;
        }
        return this.getErrorCode((List)((BaseResponse)object).getError());
    }

    private RequestHandler.ProcessResult processAction(Context context, int n, String string2, long l, String string3) {
        switch (n) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 1: {
                return this.getStatistic(context, string2, l, string3);
            }
            case 2: 
        }
        return this.updateStatistic(context, string2, l, string3);
    }

    private BaseResponse<StatisticResponse, List<BaseError>> requestGetStatistic(String baseResponse, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("deviceId", string2));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetStatistic.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseStatistic(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<StatisticResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    private BaseResponse<String, List<BaseError>> requestUpdateStatistic(Statistic baseResponse, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", string2));
        arrayList.add(new BasicNameValuePair("deviceId", ((Statistic)baseResponse).deviceId));
        arrayList.add(new BasicNameValuePair("measurementsCount", String.valueOf(((Statistic)baseResponse).measurementsCount)));
        arrayList.add(new BasicNameValuePair("badMeasurementsCount", String.valueOf(((Statistic)baseResponse).badMeasurementsCount)));
        arrayList.add(new BasicNameValuePair("batteryStatus", String.valueOf(((Statistic)baseResponse).batteryStatus)));
        arrayList.add(new BasicNameValuePair("changedBatteriesCount", String.valueOf(((Statistic)baseResponse).changedBatteriesCount)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.UpdateStatistic.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseUpdateStatistic(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    private RequestHandler.ProcessResult updateStatistic(Context object, String object2, long l, String string2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if ((object = DataHelper.StatisticHelper.getStatistic(object, l, string2)) != null) {
            object2 = this.requestUpdateStatistic((Statistic)object, (String)object2);
            object = processResult;
            if (!((BaseResponse)object2).isSuccessful()) {
                object = this.getErrorCode((List)((BaseResponse)object2).getError());
            }
            return object;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent object, long l, String string2) {
        int n = object.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        if (!TextUtils.isEmpty((CharSequence)(object = object.getStringExtra("com.getqardio.android.extra.DEVICE_ID")))) {
            return this.processAction(context, n, string2, l, (String)object);
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }
}

