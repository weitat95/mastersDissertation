/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.getqardio.android.datamodel.PregnancyData;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PregnancyModeRequestHandler
extends RequestHandler {
    public static Intent createPregnancyModeGetHistoryIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 23, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 2);
        return context;
    }

    public static Intent createPregnancyModeUpdateIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 23, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        return context;
    }

    public static Intent createSyncPregnancyModeIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 23, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 1);
        return context;
    }

    private RequestHandler.ProcessResult getPregnancyModeHistory(Context context, long l, String arrpregnancyData) {
        if ((arrpregnancyData = this.requestGetPregnancyModeHistory((String)arrpregnancyData)).isSuccessful()) {
            if ((arrpregnancyData = (PregnancyData[])arrpregnancyData.getData()) != null) {
                int n = arrpregnancyData.length;
                for (int i = 0; i < n; ++i) {
                    DataHelper.PregnancyDataHelper.savePregnancyData(context, l, arrpregnancyData[i], false);
                }
            }
            return RequestHandler.ProcessResult.SUCCESS;
        }
        return this.getErrorCode((List)arrpregnancyData.getError());
    }

    private BaseResponse<PregnancyData[], List<BaseError>> requestGetPregnancyModeHistory(String object) {
        Object object2 = new ArrayList<BasicNameValuePair>();
        object2.add(new BasicNameValuePair("authToken", (String)object));
        object = NetworkRequestHelper.request(NetworkContract.GetPregnancyModeHistory.METHOD, NetworkContract.GetPregnancyModeHistory.URI, object2);
        object2 = new BaseResponse();
        if (((NetworkRequestHelper.HttpResponse)object).isSuccessful()) {
            return JSONParser.parsePregnancyMode(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        BaseError.setNetworkErrorResult(object2);
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RequestHandler.ProcessResult syncPregnancyMode(Context object, long l, String object2) {
        Object object3;
        Cursor cursor = DataHelper.PregnancyDataHelper.getPregnancyDataForSync(object, l);
        if (cursor == null) {
            return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        }
        try {
            block9: {
                boolean bl;
                int n = cursor.getColumnIndex("_id");
                int n2 = cursor.getColumnIndex("sync_status");
                int n3 = cursor.getColumnIndex("start_date");
                int n4 = cursor.getColumnIndex("due_date");
                int n5 = cursor.getColumnIndex("start_weight");
                int n6 = cursor.getColumnIndex("end_date");
                int n7 = cursor.getColumnIndex("cloud_id");
                if (!cursor.moveToFirst()) return RequestHandler.ProcessResult.SUCCESS;
                do {
                    long l2 = HelperUtils.getLong(cursor, n, null);
                    object3 = HelperUtils.getInt(cursor, n2, null);
                    Object object4 = HelperUtils.getDate(cursor, n3, null);
                    Date date = HelperUtils.getDate(cursor, n4, null);
                    Integer n8 = HelperUtils.getInt(cursor, n5, null);
                    Date date2 = HelperUtils.getDate(cursor, n6, null);
                    Long l3 = HelperUtils.getLong(cursor, n7, null);
                    if (((Integer)object3 & 3) <= 0) continue;
                    object3 = this.requestSetPregnancyMode((String)object2, l3, (Date)object4, date, n8.intValue(), date2);
                    if (((BaseResponse)object3).isSuccessful()) {
                        DataHelper.PregnancyDataHelper.updateCloudId(object, l, l2, (Long)((BaseResponse)object3).getData());
                        DataHelper.PregnancyDataHelper.changeSyncStatus(object, l, l2, 0);
                        continue;
                    }
                    if ((object3 = this.getErrorCode((List)((BaseResponse)object3).getError())) == (object4 = RequestHandler.ProcessResult.INVALID_TOKEN)) break block9;
                } while (bl = cursor.moveToNext());
                return RequestHandler.ProcessResult.SUCCESS;
            }
            object = object3;
            if (cursor.isClosed()) return object;
        }
        catch (Exception exception) {
            object2 = RequestHandler.ProcessResult.UNKNOWN_ERROR;
            object = object2;
            return object2;
        }
        finally {
            if (cursor.isClosed()) return object;
            cursor.close();
        }
        cursor.close();
        return object3;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: {
                if (this.syncPregnancyMode(context, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
                    return this.getPregnancyModeHistory(context, l, string2);
                }
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 1: {
                return this.syncPregnancyMode(context, l, string2);
            }
            case 2: 
        }
        return this.getPregnancyModeHistory(context, l, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public BaseResponse<Long, List<BaseError>> requestSetPregnancyMode(String object, Long baseResponse, Date date, Date date2, float f, Date date3) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        if (baseResponse != null) {
            arrayList.add(new BasicNameValuePair("id", String.valueOf(baseResponse)));
        }
        arrayList.add(new BasicNameValuePair("authToken", (String)object));
        arrayList.add(new BasicNameValuePair("startDate", String.valueOf(date.getTime())));
        arrayList.add(new BasicNameValuePair("dueDate", String.valueOf(date2.getTime())));
        arrayList.add(new BasicNameValuePair("startWeight", String.valueOf(f)));
        object = date3 != null ? String.valueOf(date3.getTime()) : "";
        arrayList.add(new BasicNameValuePair("endDate", (String)object));
        object = NetworkRequestHelper.request(NetworkContract.SetPregnancyMode.METHOD, NetworkContract.SetPregnancyMode.URI, arrayList);
        baseResponse = new BaseResponse();
        if (((NetworkRequestHelper.HttpResponse)object).isSuccessful()) {
            return JSONParser.parseSetPregnancyModeResponse(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }
}

