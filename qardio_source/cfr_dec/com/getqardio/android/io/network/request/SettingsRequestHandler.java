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
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class SettingsRequestHandler
extends RequestHandler {
    public static Intent createGetSettingsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 4, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        return context;
    }

    public static Intent createSyncSettingsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 4, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 1);
        return context;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult getSettingsInfo(Context object, Intent object2, long l, String iterator) {
        Object object3;
        void var2_6;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object3 = SettingsRequestHandler.requestGetSettings((String)object3))).isSuccessful()) {
            void var3_10;
            RequestHandler.ProcessResult processResult2 = processResult;
            if (var3_10 == -1L) return var2_6;
            {
                object3.getData().userId = (long)var3_10;
                DataHelper.SettingsHelper.saveSettings(object, ((BaseResponse)object3).getData(), false);
                RequestHandler.ProcessResult processResult3 = processResult;
                return var2_6;
            }
        } else {
            RequestHandler.ProcessResult processResult4 = this.getErrorCode((List<BaseError>)((BaseResponse)object3).getError());
            Timber.e("Error getting settings: ", new Object[0]);
            object3 = ((BaseResponse)object3).getError().iterator();
            do {
                RequestHandler.ProcessResult processResult5 = processResult4;
                if (!object3.hasNext()) return var2_6;
                BaseError baseError = (BaseError)object3.next();
                Timber.e("%s : %s", baseError.messageKey, baseError.defaultMessageText);
            } while (true);
        }
    }

    public static BaseResponse<Settings, List<BaseError>> requestGetSettings(String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetSettings.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseGetSettings(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<Settings, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestSetSettings(String baseResponse, Settings arrayList) {
        if ((arrayList = JSONParser.storeSettings((Settings)((Object)arrayList))) == null) {
            baseResponse = new BaseResponse();
            baseResponse.setStatus(BaseResponse.Status.FAILURE);
            baseResponse.setData(null);
            arrayList = new ArrayList<BaseError>(1);
            arrayList.add(ErrorHelper.makeCreateParamsError());
            baseResponse.setError(arrayList);
            return baseResponse;
        }
        ArrayList<BasicNameValuePair> arrayList2 = new ArrayList<BasicNameValuePair>();
        arrayList2.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList2.add(new BasicNameValuePair("value", (String)((Object)arrayList)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.SetSettings.URI, arrayList2);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseSetSettings(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private RequestHandler.ProcessResult syncSettingsInfo(Context object, Intent object2, long l, String object3) {
        Iterator iterator;
        void var3_4;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        Settings settings = DataHelper.SettingsHelper.getSettings(object, (long)var3_4);
        Object object4 = processResult;
        if (settings == null) return object4;
        object4 = processResult;
        if ((settings.syncStatus & 1) != 1) return object4;
        object4 = SettingsRequestHandler.requestSetSettings((String)((Object)iterator), settings);
        if (((BaseResponse)object4).isSuccessful()) {
            DataHelper.SettingsHelper.changeSyncStatus(object, (long)var3_4, settings.syncStatus & 0xFFFFFFFE);
            return processResult;
        }
        RequestHandler.ProcessResult processResult2 = this.getErrorCode((List)((BaseResponse)object4).getError());
        Timber.e("Error sync settings: ", new Object[0]);
        iterator = ((List)((BaseResponse)object4).getError()).iterator();
        do {
            object4 = processResult2;
            if (!iterator.hasNext()) return object4;
            object4 = (BaseError)iterator.next();
            Timber.e("%s : %s", ((BaseError)object4).messageKey, ((BaseError)object4).defaultMessageText);
        } while (true);
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: {
                if (this.syncSettingsInfo(context, intent, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
                    return this.getSettingsInfo(context, intent, l, string2);
                }
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 1: 
        }
        return this.syncSettingsInfo(context, intent, l, string2);
    }
}

