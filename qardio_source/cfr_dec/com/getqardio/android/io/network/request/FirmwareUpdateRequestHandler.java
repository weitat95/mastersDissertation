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
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import timber.log.Timber;

public class FirmwareUpdateRequestHandler
extends RequestHandler {
    public static Intent createGetQBLatestFirmwareIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 20, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        return context;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult getQBLatestFirmware(Context object, String object2, String object3) {
        void var2_8;
        Object object4;
        void var2_3;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object4 = FirmwareUpdateRequestHandler.requestLatestFirmware((String)var2_3, "QardioBaseWifi", (String)object4))).isSuccessful()) {
            FirmwareUpdateHelper.setServerQBFirmwareVersion(object, ((BaseResponse)object4).getData());
            FirmwareDescription firmwareDescription = FirmwareUpdateHelper.getCurrentQBFirmwareUpdate(object);
            RequestHandler.ProcessResult processResult2 = processResult;
            if (firmwareDescription == null) return var2_8;
            {
                RequestHandler.ProcessResult processResult3 = processResult;
                if (!((BaseResponse)object4).getData().isGreater(firmwareDescription)) return var2_8;
                {
                    FirmwareDescription firmwareDescription2 = (FirmwareDescription)((BaseResponse)object4).getData();
                    object4 = String.format("%d.%d.%d", firmwareDescription2.versionMajor, firmwareDescription2.versionMinor, firmwareDescription2.versionBugFix);
                    firmwareDescription = new Intent("com.getqardio.android.action.NEW_BASE_FIRMWARE");
                    firmwareDescription.putExtra("com.getqardio.android.extra.FIRMWARE_IP", firmwareDescription2.ipAddress);
                    firmwareDescription.putExtra("com.getqardio.android.extra.FIRMWARE_VERSION", (String)object4);
                    firmwareDescription.putExtra("com.getqardio.android.extra.FIRMWARE_DESCRIPTION", firmwareDescription2.description);
                    LocalBroadcastManager.getInstance(object).sendBroadcast((Intent)firmwareDescription);
                    RequestHandler.ProcessResult processResult4 = processResult;
                    return var2_8;
                }
            }
        } else {
            RequestHandler.ProcessResult processResult5 = this.getErrorCode((List<BaseError>)((BaseResponse)object4).getError());
            Timber.e("Error getting firmware information: ", new Object[0]);
            object4 = ((BaseResponse)object4).getError().iterator();
            do {
                RequestHandler.ProcessResult processResult6 = processResult5;
                if (!object4.hasNext()) return var2_8;
                BaseError baseError = (BaseError)object4.next();
                Timber.e("%s : %s", baseError.messageKey, baseError.defaultMessageText);
            } while (true);
        }
    }

    public static BaseResponse<FirmwareDescription, List<BaseError>> requestLatestFirmware(String baseResponse, String string2, String string3) {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        linkedList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        linkedList.add(new BasicNameValuePair("deviceType", string2));
        linkedList.add(new BasicNameValuePair("ignoreRestrictions", "true"));
        linkedList.add(new BasicNameValuePair("serialNumber", string3));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetLatestFirmware.URI, linkedList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseFirmwareDescription(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<FirmwareDescription, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: 
        }
        return this.getQBLatestFirmware(context, string2, DataHelper.DeviceSnHelper.getDeviceSn(context, l));
    }
}

