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
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.MeasurementsHistory;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class SendHistoryRequestHandler
extends RequestHandler {
    private List<BasicNameValuePair> buildParams(MeasurementsHistory measurementsHistory, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        String string3 = CustomApplication.getApplication().getCurrentUserEmail();
        arrayList.add(new BasicNameValuePair("authToken", string2));
        arrayList.add(new BasicNameValuePair("email", Utils.encodeString(measurementsHistory.targetEmail)));
        arrayList.add(new BasicNameValuePair("name", measurementsHistory.targetName));
        arrayList.add(new BasicNameValuePair("note", Utils.encodeString(measurementsHistory.note)));
        arrayList.add(new BasicNameValuePair("userId", Utils.encodeString(string3)));
        arrayList.add(new BasicNameValuePair("userProfile", "mn"));
        return arrayList;
    }

    public static Intent createSendHistoryIntent(Context context, long l, long l2) {
        context = AsyncReceiverHandler.createIntent(context, 10, l);
        context.putExtra("com.qardio.android.extra.MEASUREMENT_HISTORY_ID", l2);
        context.putExtra("com.qardio.android.extra.EXTRA_ACTION_TYPE", 1);
        return context;
    }

    public static Intent createSendOldHistoryIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 10, l);
        context.putExtra("com.qardio.android.extra.EXTRA_ACTION_TYPE", 2);
        return context;
    }

    private void notifySendingFailed(Context context, List<BaseError> list) {
        Intent intent = NotificationHelper.SendHistoryNotification.createErrorsResult();
        ErrorHelper.putErrorsToIntent(intent, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void notifySuccessfulSending(Context context) {
        Intent intent = NotificationHelper.SendHistoryNotification.createSuccessResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendHistory(Context context, long l, String object) {
        block3: {
            block2: {
                MeasurementsHistory measurementsHistory = MeasurementHelper.History.getMeasurementHistory(context, l);
                if (measurementsHistory == null) break block2;
                if (!((BaseResponse)(object = this.sendRequest(this.buildParams(measurementsHistory, (String)object)))).isSuccessful()) break block3;
                MeasurementHelper.History.deleteMeasurementHistory(context, l);
                this.notifySuccessfulSending(context);
            }
            return;
        }
        this.notifySendingFailed(context, (List)((BaseResponse)object).getError());
    }

    private void sendOldHistory(Context context, String string2) {
        List<Object> list = MeasurementHelper.History.getMeasurementsHistoryList(context);
        List<Object> list2 = new ArrayList();
        for (MeasurementsHistory measurementsHistory : list) {
            list2.clear();
            list = this.buildParams(measurementsHistory, string2);
            list2 = list;
            if (!this.sendRequest(list).isSuccessful()) continue;
            MeasurementHelper.History.deleteMeasurementHistory(context, measurementsHistory._id);
            this.notifySuccessfulSending(context);
            list2 = list;
        }
    }

    private BaseResponse<List<Object>, List<BaseError>> sendRequest(List<BasicNameValuePair> object) {
        object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.SendHistory.URI, object);
        BaseResponse<List<Object>, List<BaseError>> baseResponse = new BaseResponse<List<Object>, List<BaseError>>();
        JSONParser.parseStatusAndErrors(((NetworkRequestHelper.HttpResponse)object).getResponseBody(), baseResponse);
        return baseResponse;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.qardio.android.extra.EXTRA_ACTION_TYPE", 0)) {
            case 1: {
                this.sendHistory(context, intent.getLongExtra("com.qardio.android.extra.MEASUREMENT_HISTORY_ID", 0L), string2);
            }
            default: {
                return RequestHandler.ProcessResult.SUCCESS;
            }
            case 2: 
        }
        this.sendOldHistory(context, string2);
        return RequestHandler.ProcessResult.SUCCESS;
    }
}

