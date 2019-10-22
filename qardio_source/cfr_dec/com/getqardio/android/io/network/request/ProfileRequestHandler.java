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
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ProfileRequestHandler
extends RequestHandler {
    private static void addProfileParams(Profile profile, String string2, List<BasicNameValuePair> list, boolean bl, String string3) {
        boolean bl2 = false;
        boolean bl3 = false;
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            string2 = string3;
            bl2 = true;
            bl3 = true;
        }
        boolean bl4 = bl3;
        boolean bl5 = bl2;
        if (bl) {
            bl4 = bl3;
            bl5 = bl2;
            if (profile.getEmail() != null) {
                list.add(new BasicNameValuePair("email", Utils.encodeString(profile.getEmail())));
                bl5 = true;
                bl4 = true;
            }
        }
        if (bl5) {
            list.add(new BasicNameValuePair("password", Utils.encodeString(string2)));
        }
        if (bl4) {
            list.add(new BasicNameValuePair("key1", "kidJdCIJX39GnXpDlZBvM5ynS"));
            list.add(new BasicNameValuePair("key2", "kYvLN3E5X9jRjnDF5OCcypNZf"));
        }
        if (profile.firstName != null) {
            list.add(new BasicNameValuePair("firstName", profile.firstName));
        }
        if (profile.lastName != null) {
            list.add(new BasicNameValuePair("lastName", profile.lastName));
        }
        if (profile.dob != null) {
            list.add(new BasicNameValuePair("dob", String.valueOf(profile.dob.getTime())));
        }
        if (profile.gender != null) {
            list.add(new BasicNameValuePair("sex", Constants.Gender.int2String(profile.gender)));
        }
        if (profile.address != null) {
            list.add(new BasicNameValuePair("address", profile.address));
        }
        if (profile.latitude != null) {
            list.add(new BasicNameValuePair("latitude", String.valueOf(profile.latitude)));
        }
        if (profile.longitude != null) {
            list.add(new BasicNameValuePair("longitude", String.valueOf(profile.longitude)));
        }
        if (profile.zip != null) {
            list.add(new BasicNameValuePair("zipCode", profile.zip));
        }
        if (profile.state != null) {
            list.add(new BasicNameValuePair("state", profile.state));
        }
        if (profile.country != null) {
            list.add(new BasicNameValuePair("country", profile.country));
        }
        if (profile.phone != null) {
            list.add(new BasicNameValuePair("phone", profile.phone));
        }
        if (profile.height != null) {
            list.add(new BasicNameValuePair("height", String.valueOf(profile.height)));
        }
        if (profile.heightUnit != null) {
            list.add(new BasicNameValuePair("heightUnit", Constants.HeightUnit.int2String(profile.heightUnit)));
        }
        if (profile.weight != null) {
            list.add(new BasicNameValuePair("weight", String.valueOf(profile.weight)));
        }
        if (profile.weightUnit != null) {
            list.add(new BasicNameValuePair("weightUnit", Constants.WeightUnit.int2String(profile.weightUnit)));
        }
        if (profile.locale != null) {
            list.add(new BasicNameValuePair("locale", profile.locale));
        }
        if (profile.doctorEmail != null) {
            list.add(new BasicNameValuePair("doctorEmail", profile.doctorEmail));
        }
        if (profile.doctorName != null) {
            list.add(new BasicNameValuePair("doctorName", profile.doctorName));
        }
    }

    public static Intent createGetProfileIntent(Context context, long l) {
        context = AsyncReceiverHandler.createIntent(context, 2, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        context.putExtra("com.getqardio.android.extra.NEED_UPDATE_EMAIL", false);
        return context;
    }

    public static Intent createSyncProfileIntent(Context context, long l, boolean bl, String string2) {
        context = AsyncReceiverHandler.createIntent(context, 2, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", 1);
        context.putExtra("com.getqardio.android.extra.NEED_UPDATE_EMAIL", bl);
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            context.putExtra("com.getqardio.android.extra.NEW_PASSWORD", string2);
        }
        return context;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult getProfileInfo(Context object, Intent object2, long l, String iterator) {
        Object object3;
        void var2_6;
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        if (((BaseResponse)(object3 = ProfileRequestHandler.requestGetProfileInfo((String)object3))).isSuccessful()) {
            object3.getData().userId = AuthHelper.getUserId(object, ((BaseResponse)object3).getData().getEmail());
            RequestHandler.ProcessResult processResult2 = processResult;
            if (((Profile)object3.getData()).userId == null) return var2_6;
            {
                DataHelper.ProfileHelper.saveProfile(object, (Profile)((BaseResponse)object3).getData(), false);
                this.trackProfileInfo((Context)object, (Profile)((BaseResponse)object3).getData());
                RequestHandler.ProcessResult processResult3 = processResult;
                return var2_6;
            }
        } else {
            RequestHandler.ProcessResult processResult4 = this.getErrorCode((List<BaseError>)((BaseResponse)object3).getError());
            object3 = ((BaseResponse)object3).getError().iterator();
            do {
                RequestHandler.ProcessResult processResult5 = processResult4;
                if (!object3.hasNext()) return var2_6;
                BaseError baseError = (BaseError)object3.next();
                Timber.e("%s : %s", baseError.messageKey, baseError.defaultMessageText);
            } while (true);
        }
    }

    private static void notifyProfileUpdated(Context context, boolean bl, List<BaseError> intent) {
        if (bl) {
            intent = NotificationHelper.UpdateProfileNotification.createSuccessResult();
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            return;
        }
        intent = NotificationHelper.UpdateProfileNotification.createErrorsResult((List<BaseError>)intent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static BaseResponse<Profile, List<BaseError>> requestGetProfileInfo(String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.GetProfileInfo.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseGetProfileInfo(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<Profile, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    public static BaseResponse<String, List<BaseError>> requestSetProfileInfo(String baseResponse, Profile profile, String string2, boolean bl, String string3) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("authToken", (String)((Object)baseResponse)));
        ProfileRequestHandler.addProfileParams(profile, string2, arrayList, bl, string3);
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.SetProfileInfo.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseSetProfileInfo(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
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
    private RequestHandler.ProcessResult syncProfileInfo(Context context, Intent object, long l, String object2) {
        synchronized (this) {
            BaseResponse<String, List<BaseError>> baseResponse;
            Object object3 = RequestHandler.ProcessResult.SUCCESS;
            boolean bl = object.getBooleanExtra("com.getqardio.android.extra.NEED_UPDATE_EMAIL", false);
            object = object.getStringExtra("com.getqardio.android.extra.NEW_PASSWORD");
            Profile profile = DataHelper.ProfileHelper.getProfileForUser(context, l);
            if (profile != null && (profile.syncStatus & 1) == 1) {
                User user = AuthHelper.getUserById(context, l);
                boolean bl2 = !TextUtils.equals((CharSequence)user.email, (CharSequence)profile.getEmail());
                baseResponse = ProfileRequestHandler.requestSetProfileInfo(object2, profile, user.password, bl |= bl2, (String)object);
                if (baseResponse.isSuccessful()) {
                    if (bl || !TextUtils.isEmpty((CharSequence)object)) {
                        if (bl) {
                            user.email = profile.getEmail();
                        }
                        if (!TextUtils.isEmpty((CharSequence)object)) {
                            user.password = object;
                        }
                        user.token = baseResponse.getData();
                        AuthHelper.updateUser(context, l, user);
                        CustomApplication.getApplication().setCurrentUserToken(baseResponse.getData());
                    }
                    DataHelper.ProfileHelper.changeSyncStatus(context, l, profile.syncStatus & 0xFFFFFFFE);
                    object = object3;
                } else {
                    if (bl) {
                        DataHelper.ProfileHelper.rollBackEmailChanges(context, l);
                    }
                    object2 = this.getErrorCode(baseResponse.getError());
                    Timber.e("Error sync profile info : ", new Object[0]);
                    object3 = baseResponse.getError().iterator();
                    do {
                        object = object2;
                        if (!object3.hasNext()) break;
                        object = (BaseError)object3.next();
                        Timber.e("%s : %s", object.messageKey, object.defaultMessageText);
                    } while (true);
                }
            } else {
                ProfileRequestHandler.notifyProfileUpdated(context, false, null);
                return object3;
            }
            ProfileRequestHandler.notifyProfileUpdated(context, baseResponse.isSuccessful(), baseResponse.getError());
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void trackProfileInfo(Context context, Profile profile) {
        String string2 = CustomApplication.getApplication().getCurrentUserTrackingId();
        if (string2 == null) {
            return;
        }
        CustomTraits customTraits = new CustomTraits();
        boolean bl = !TextUtils.isEmpty((CharSequence)profile.doctorEmail) || !TextUtils.isEmpty((CharSequence)profile.doctorName);
        customTraits.putEnteredDoctorInfo(bl);
        IdentifyHelper.identify(context, string2, customTraits);
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", -1)) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 0: {
                if (this.syncProfileInfo(context, intent, l, string2) == RequestHandler.ProcessResult.SUCCESS) {
                    return this.getProfileInfo(context, intent, l, string2);
                }
                return RequestHandler.ProcessResult.UNKNOWN_ERROR;
            }
            case 1: 
        }
        return this.syncProfileInfo(context, intent, l, string2);
    }
}

