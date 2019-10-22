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
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.CreateNewUserResponse;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateNewUserRequestHandler
extends RequestHandler {
    /*
     * Enabled aggressive block sorting
     */
    private RequestHandler.ProcessResult createNewUser(Context context, String string2, String object, String string3, boolean bl) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        BaseResponse<CreateNewUserResponse, List<BaseError>> baseResponse = CreateNewUserRequestHandler.requestCreateNewUser(string2, (String)object, string3, Locale.getDefault().getLanguage(), bl);
        if (!baseResponse.isSuccessful()) {
            this.notifyFailedCreateNewUser(context, baseResponse.getError());
            return RequestHandler.ProcessResult.UNKNOWN_ERROR;
        }
        User user = AuthHelper.getUserByEmail(context, string2);
        boolean bl2 = user != null;
        User user2 = user;
        if (user == null) {
            user2 = new User();
        }
        user2.email = string2;
        user2.password = object;
        user2.token = baseResponse.getData().authToken;
        user2.tokenExpired = baseResponse.getData().expired;
        user2.trackingId = baseResponse.getData().trackingId;
        if (bl2) {
            AuthHelper.updateUser(context, user2._id, user2);
        } else {
            AuthHelper.setUser(context, user2);
        }
        CustomApplication.getApplication().setCurrentUserToken(user2.token);
        CustomApplication.getApplication().setCurrentUserTrackingId(user2.trackingId);
        object = new Profile();
        ((Profile)object).userId = CustomApplication.getApplication().getCurrentUserId();
        ((Profile)object).firstName = string3;
        ((Profile)object).setEmail(string2);
        DataHelper.ProfileHelper.saveProfile(context, (Profile)object, true);
        this.identifyUser(context, user2.trackingId, string3);
        this.notifySuccessfulCreateNewUser(context);
        return processResult;
    }

    public static Intent createNewUserIntent(Context context, String string2, String string3, String string4, boolean bl) {
        if (string2 == null || string3 == null || string4 == null) {
            throw new NullPointerException("login, password, name params can't be null");
        }
        context = AsyncReceiverHandler.createIntent(context, 3, -1L);
        context.putExtra("com.getqardio.android.extra.LOGIN", string2);
        context.putExtra("com.getqardio.android.extra.PASSWORD", string3);
        context.putExtra("com.getqardio.android.extra.NAME", string4);
        context.putExtra("com.getqardio.android.extra.enableMarketing", bl);
        return context;
    }

    private void identifyUser(Context context, String string2, String object) {
        object = new CustomTraits();
        ((CustomTraits)object).putEnabledGoogleFit(false).putEnabledPlaces(true).putEnabledPhotoSlideshow(false).putEnabledImportantNotifications(true).putStepsGoal(5000).putActivityGoal(30).putEnabledBodyComposition(false).putEnteredDoctorInfo(false).putQaMeasurementCount(1).putQaMeasurementPause(15);
        IdentifyHelper.identify(context, string2, (CustomTraits)object);
    }

    private void notifyFailedCreateNewUser(Context context, List<BaseError> list) {
        Intent intent = NotificationHelper.CreateNewUserNotification.createErrorsResult();
        ErrorHelper.putErrorsToIntent(intent, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void notifySuccessfulCreateNewUser(Context context) {
        Intent intent = NotificationHelper.CreateNewUserNotification.createSuccessResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private static BaseResponse<CreateNewUserResponse, List<BaseError>> requestCreateNewUser(String baseResponse, String string2, String string3, String string4, boolean bl) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("email", Utils.encodeString((String)((Object)baseResponse))));
        arrayList.add(new BasicNameValuePair("password", string2));
        arrayList.add(new BasicNameValuePair("firstName", Utils.encodeString(string3)));
        arrayList.add(new BasicNameValuePair("accountType", "CLIENT"));
        arrayList.add(new BasicNameValuePair("lastName", ""));
        arrayList.add(new BasicNameValuePair("locale", string4));
        arrayList.add(new BasicNameValuePair("key1", "kidJdCIJX39GnXpDlZBvM5ynS"));
        arrayList.add(new BasicNameValuePair("key2", "kYvLN3E5X9jRjnDF5OCcypNZf"));
        arrayList.add(new BasicNameValuePair("consent_marketing", Boolean.toString(bl)));
        baseResponse = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.CreateNewAccount.URI, arrayList);
        if (((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).isSuccessful()) {
            return JSONParser.parseCreateNewUser(((NetworkRequestHelper.HttpResponse)((Object)baseResponse)).getResponseBody());
        }
        baseResponse = new BaseResponse<CreateNewUserResponse, List<BaseError>>();
        BaseError.setNetworkErrorResult(baseResponse);
        return baseResponse;
    }

    @Override
    public boolean checkUserId(Intent intent, Long l, long l2) {
        return true;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        return this.createNewUser(context, intent.getStringExtra("com.getqardio.android.extra.LOGIN"), intent.getStringExtra("com.getqardio.android.extra.PASSWORD"), intent.getStringExtra("com.getqardio.android.extra.NAME"), intent.getBooleanExtra("com.getqardio.android.extra.enableMarketing", true));
    }
}

