/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.util.Log
 *  android.util.Pair
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.Pair;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.LoginResponse;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class LoginRequestHandler
extends RequestHandler {
    public static Intent createLoginIntent(Context context, String string2, String string3) {
        context = AsyncReceiverHandler.createIntent(context, 1, -1L);
        context.putExtra("com.getqardio.android.extra.LOGIN", string2);
        context.putExtra("com.getqardio.android.extra.PASSWORD", string3);
        return context;
    }

    private boolean hasTracking(User user) {
        return user.trackingId != null;
    }

    private void identifyUser(Context context, String string2) {
        IdentifyHelper.identify(context, string2, new CustomTraits());
    }

    private boolean isTokenValid(User user) {
        return user.tokenExpired > System.currentTimeMillis();
    }

    private void notifyFailedLogin(Context context, List<BaseError> list) {
        CustomApplication.getApplication().setCurrentUserToken(null);
        Intent intent = NotificationHelper.LoginNotification.createErrorsResult();
        ErrorHelper.putErrorsToIntent(intent, list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void notifyNetworkError(Context context) {
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        arrayList.add(ErrorHelper.makeNetworkError());
        this.notifyFailedLogin(context, arrayList);
    }

    private void notifySuccessfulLogin(Context context, String string2) {
        CustomApplication.getApplication().setCurrentUserToken(string2);
        string2 = NotificationHelper.LoginNotification.createSuccessResult();
        LocalBroadcastManager.getInstance(context).sendBroadcast((Intent)string2);
    }

    private void offlineLogin(Context context, User user, String string2) {
        if (user != null) {
            if (string2.equals(user.password)) {
                if (this.isTokenValid(user) && this.hasTracking(user)) {
                    this.notifySuccessfulLogin(context, user.token);
                    return;
                }
                this.updateToken(context, user);
                return;
            }
            this.notifyNetworkError(context);
            return;
        }
        this.notifyNetworkError(context);
    }

    public static boolean relogin(Context context, User user) {
        BaseResponse<LoginResponse, List<BaseError>> baseResponse = LoginRequestHandler.requestLogin(user.email, user.password);
        if (baseResponse.isSuccessful()) {
            AuthHelper.updateTokenAndTracking(context, user.email, baseResponse.getData().getAccessToken(), baseResponse.getData().getExpiresDate(), baseResponse.getData().getUserId());
            CustomApplication.getApplication().setCurrentUserToken(baseResponse.getData().getAccessToken());
            CustomApplication.getApplication().setCurrentUserTrackingId(baseResponse.getData().getUserId());
            return true;
        }
        return baseResponse.getStatus() == BaseResponse.Status.NETWORK_ERROR;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static BaseResponse<LoginResponse, List<BaseError>> requestLogin(String object, String baseResponse) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(4);
        try {
            arrayList.add(new BasicNameValuePair("username", URLEncoder.encode((String)object, "utf-8")));
            arrayList.add(new BasicNameValuePair("password", URLEncoder.encode(baseResponse, "utf-8")));
            arrayList.add(new BasicNameValuePair("scope", URLEncoder.encode("read+write", "utf-8")));
            arrayList.add(new BasicNameValuePair("grant_type", URLEncoder.encode("password", "utf-8")));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Timber.e(unsupportedEncodingException);
        }
        object = new ArrayList<Pair<String, String>>(2);
        object.add(Pair.create((Object)"Authorization", (Object)NetworkContract.OAuthData.generateAuthorization()));
        object.add(Pair.create((Object)"Content-Type", (Object)"application/x-www-form-urlencoded; charset=utf-8"));
        object = NetworkRequestHelper.request(NetworkRequestHelper.Method.POST, NetworkContract.Login.URI, arrayList, object);
        if (((NetworkRequestHelper.HttpResponse)object).isSuccessful()) {
            return JSONParser.parseLogin(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        if (((NetworkRequestHelper.HttpResponse)object).getResponseCode() == 400) {
            object = JSONParser.parseLoginFailed(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
            baseResponse = new BaseResponse();
            baseResponse.setStatus(BaseResponse.Status.FAILURE);
            baseResponse.setError((List<BaseError>)object);
            baseResponse.setData(null);
            return baseResponse;
        }
        object = new BaseResponse();
        BaseError.setNetworkErrorResult(object);
        return object;
    }

    private void updateToken(Context context, User user) {
        BaseResponse<LoginResponse, List<BaseError>> baseResponse = LoginRequestHandler.requestLogin(user.email, user.password);
        if (baseResponse.isSuccessful()) {
            AuthHelper.updateTokenAndTracking(context, user.email, baseResponse.getData().getAccessToken(), baseResponse.getData().getExpiresDate(), user.trackingId);
            this.notifySuccessfulLogin(context, baseResponse.getData().getAccessToken());
            return;
        }
        if (baseResponse.getStatus() == BaseResponse.Status.NETWORK_ERROR) {
            this.notifySuccessfulLogin(context, user.token);
            return;
        }
        this.notifyFailedLogin(context, baseResponse.getError());
    }

    @Override
    public boolean checkUserId(Intent intent, Long l, long l2) {
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent object, long l, String baseResponse) {
        String string2 = object.getStringExtra("com.getqardio.android.extra.LOGIN");
        String string3 = object.getStringExtra("com.getqardio.android.extra.PASSWORD");
        User user = AuthHelper.getUserByEmail(context, string2);
        if (!Utils.isNetworkAvaliable(context)) {
            this.offlineLogin(context, user, string3);
            return RequestHandler.ProcessResult.SUCCESS;
        }
        BaseResponse<LoginResponse, List<BaseError>> baseResponse2 = LoginRequestHandler.requestLogin(string2, string3);
        if (baseResponse2.isSuccessful()) {
            if (AuthHelper.updatePassword(context, string2, string3)) {
                void var2_5;
                user.trackingId = baseResponse2.getData().getUserId();
                if (AuthHelper.updateTokenAndTracking(context, string2, baseResponse2.getData().getAccessToken(), baseResponse2.getData().getExpiresDate(), user.trackingId)) {
                    String string4 = "Token for user has been updated";
                } else {
                    String string5 = "Token couldn't be updated";
                }
                Log.v((String)"LoginReqHandler", (String)var2_5);
            } else {
                user = new User();
                user.email = string2;
                user.password = string3;
                user.token = baseResponse2.getData().getAccessToken();
                user.tokenExpired = baseResponse2.getData().getExpiresDate();
                user.trackingId = baseResponse2.getData().getUserId();
                AuthHelper.setUser(context, user);
            }
            String string6 = baseResponse2.getData().getUserId();
            CustomApplication.getApplication().setCurrentUserTrackingId(string6);
            this.identifyUser(context, string6);
            this.notifySuccessfulLogin(context, baseResponse2.getData().getAccessToken());
            return RequestHandler.ProcessResult.SUCCESS;
        }
        if (baseResponse2.getStatus() == BaseResponse.Status.NETWORK_ERROR) {
            this.offlineLogin(context, user, string3);
            return RequestHandler.ProcessResult.SUCCESS;
        }
        this.notifyFailedLogin(context, baseResponse2.getError());
        return RequestHandler.ProcessResult.SUCCESS;
    }
}

