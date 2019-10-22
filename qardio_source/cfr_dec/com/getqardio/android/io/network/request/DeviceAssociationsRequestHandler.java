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
import android.support.v4.content.LocalBroadcastManager;
import com.getqardio.android.datamodel.DeviceAssociation;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class DeviceAssociationsRequestHandler
extends RequestHandler {
    private static boolean associationAlreadyExistError(List<BaseError> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (!"hsynch.association.alredy.exist".equals(list.get((int)i).messageKey)) continue;
            return true;
        }
        return false;
    }

    private static boolean associationDeviceIdInUseError(List<BaseError> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (!"hsynch.association.device.id.in.use".equals(list.get((int)i).messageKey)) continue;
            return true;
        }
        return false;
    }

    private static boolean associationDoesnotExistError(List<BaseError> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (!"hsynch.association.user.not.associated".equals(list.get((int)i).messageKey)) continue;
            return true;
        }
        return false;
    }

    public static Intent createRemoveDeviceAssociationsIntent(Context context, long l, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 22, l);
        context.putExtra("com.getqardio.android.extra.ASSOCIATION_ACTION", 1);
        context.putExtra("com.getqardio.android.extra.DEVICE_ID", string2);
        return context;
    }

    public static Intent createSyncDeviceAssociationsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 22, l);
        context.putExtra("com.getqardio.android.extra.ASSOCIATION_ACTION", 0);
        return context;
    }

    public static Intent createUpdateDeviceAssociationsIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 22, l);
        context.putExtra("com.getqardio.android.extra.ASSOCIATION_ACTION", 2);
        return context;
    }

    private RequestHandler.ProcessResult getDeviceAssociations(Context context, long l, String object) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        BaseResponse<List<DeviceAssociation>, List<BaseError>> baseResponse = DeviceAssociationsRequestHandler.requestGetUserActiveDeviceAssociations(object);
        if (baseResponse.isSuccessful()) {
            object = processResult;
            if (!DataHelper.DeviceAssociationsHelper.updateDeviceAssociationsAfterGet(context, l, baseResponse.getData())) {
                BaseError.setUnknownErrorResult(baseResponse);
                object = this.getErrorCode(baseResponse.getError());
            }
            return object;
        }
        return this.getErrorCode(baseResponse.getError());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult removeDeviceAssociations(Context object, Intent object2, String iterator) {
        void var1_3;
        void var2_7;
        Object object3;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object3 = DeviceAssociationsRequestHandler.requestRemoveDeviceAssociation((String)object3, var2_7.getStringExtra("com.getqardio.android.extra.DEVICE_ID")))).isSuccessful() || DeviceAssociationsRequestHandler.associationDoesnotExistError(((BaseResponse)object3).getError())) {
            Intent intent = new Intent("com.getqardio.android.action.RESET_ASSOCIATION");
            intent.putExtra("com.getqardio.android.extra.RESET_SUCCESS", true);
            LocalBroadcastManager.getInstance(object).sendBroadcast(intent);
            RequestHandler.ProcessResult processResult2 = processResult;
            return var1_3;
        } else {
            Intent intent = new Intent("com.getqardio.android.action.RESET_ASSOCIATION");
            intent.putExtra("com.getqardio.android.extra.RESET_SUCCESS", false);
            LocalBroadcastManager.getInstance(object).sendBroadcast(intent);
            Timber.e("Error deleting device associations: ", new Object[0]);
            RequestHandler.ProcessResult processResult3 = this.getErrorCode((List<BaseError>)((BaseResponse)object3).getError());
            object3 = ((BaseResponse)object3).getError().iterator();
            do {
                RequestHandler.ProcessResult processResult4 = processResult3;
                if (!object3.hasNext()) return var1_3;
                BaseError baseError = (BaseError)object3.next();
                Timber.e("%s : %s", baseError.messageKey, baseError.defaultMessageText);
            } while (true);
        }
    }

    public static BaseResponse<List<DeviceAssociation>, List<BaseError>> requestGetUserActiveDeviceAssociations(String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(1);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkContract.UserActiveAssociations.METHOD, NetworkContract.UserActiveAssociations.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseUserActiveDeviceAssociations(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<List<DeviceAssociation>, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestRemoveDeviceAssociation(String baseResponse, String string2) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(2);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("deviceId", string2));
        baseResponse = NetworkRequestHelper.request(NetworkContract.RemoveDeviceAssociation.METHOD, NetworkContract.RemoveDeviceAssociation.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseRemoveDeviceAssociation(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestUploadDeviceAssociation(String baseResponse, DeviceAssociation deviceAssociation) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(3);
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        arrayList.add(new BasicNameValuePair("deviceId", deviceAssociation.deviceId));
        arrayList.add(new BasicNameValuePair("serialNumber", deviceAssociation.serialNumber));
        baseResponse = NetworkRequestHelper.request(NetworkContract.AddDeviceAssociation.METHOD, NetworkContract.AddDeviceAssociation.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseUploadDeviceAssociation(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<String, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RequestHandler.ProcessResult syncDeviceAssociations(Context context, Intent object, long l, String object2) {
        BaseResponse<String, List<BaseError>> baseResponse;
        Object object3;
        object = RequestHandler.ProcessResult.SUCCESS;
        Cursor cursor = DataHelper.DeviceAssociationsHelper.getDeviceAssociationsForUpload(context, l, null);
        try {
            block8 : while (cursor.moveToNext()) {
                int n;
                object3 = DataHelper.DeviceAssociationsHelper.parseDeviceAssociation(cursor);
                baseResponse = DeviceAssociationsRequestHandler.requestUploadDeviceAssociation(object2, object3);
                if (baseResponse.isSuccessful() || DeviceAssociationsRequestHandler.associationAlreadyExistError(baseResponse.getError())) {
                    n = object3.syncStatus;
                    DataHelper.DeviceAssociationsHelper.changeSyncStatus(context, l, object3._id, n & 0xFFFFFFFE);
                    continue;
                }
                if (DeviceAssociationsRequestHandler.associationDeviceIdInUseError(baseResponse.getError())) {
                    n = object3.syncStatus;
                    DataHelper.DeviceAssociationsHelper.changeSyncStatus(context, l, object3._id, n & 0xFFFFFFFE);
                    continue;
                }
                Timber.e("Error updating device association: ", new Object[0]);
                object3 = this.getErrorCode(baseResponse.getError());
                baseResponse = baseResponse.getError().iterator();
                do {
                    object = object3;
                    if (!baseResponse.hasNext()) continue block8;
                    object = (BaseError)baseResponse.next();
                    Timber.e("%s : %s", object.messageKey, object.defaultMessageText);
                } while (true);
            }
        }
        finally {
            cursor.close();
        }
        cursor = DataHelper.DeviceAssociationsHelper.getDeviceAssociationsForDelete(context, l, DataHelper.DeviceAssociationsHelper.DELETING_PROJECTION);
        try {
            block10 : while (cursor.moveToNext()) {
                object3 = DataHelper.DeviceAssociationsHelper.parseDeviceAssociation(cursor);
                baseResponse = DeviceAssociationsRequestHandler.requestRemoveDeviceAssociation(object2, object3.deviceId);
                if (baseResponse.isSuccessful() || DeviceAssociationsRequestHandler.associationDoesnotExistError(baseResponse.getError())) {
                    if (l == -1L) continue;
                    DataHelper.DeviceAssociationsHelper.deleteDeviceAssociationFromLocalCache(context, l, object3._id);
                    continue;
                }
                Timber.e("Error deleting device associations: ", new Object[0]);
                object3 = this.getErrorCode(baseResponse.getError());
                baseResponse = ((List)baseResponse.getError()).iterator();
                do {
                    object = object3;
                    if (!baseResponse.hasNext()) continue block10;
                    object = (BaseError)baseResponse.next();
                    Timber.e("%s : %s", object.messageKey, object.defaultMessageText);
                } while (true);
            }
        }
        finally {
            cursor.close();
        }
        object2 = object;
        if (object != RequestHandler.ProcessResult.SUCCESS) return object2;
        if (DataHelper.DeviceAssociationsHelper.getDeviceAssociationsCountForSync(context, l) > 0) return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        return RequestHandler.ProcessResult.SUCCESS;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ASSOCIATION_ACTION", 0)) {
            default: {
                return this.syncDeviceAssociations(context, intent, l, string2);
            }
            case 0: {
                return this.syncDeviceAssociations(context, intent, l, string2);
            }
            case 1: {
                return this.removeDeviceAssociations(context, intent, string2);
            }
            case 2: 
        }
        return this.getDeviceAssociations(context, l, string2);
    }
}

