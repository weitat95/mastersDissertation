/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.getqardio.android.fcm;

import android.content.SharedPreferences;
import com.getqardio.android.fcm.FCMManager$$Lambda$1;
import com.getqardio.android.fcm.FCMManager$$Lambda$2;
import com.getqardio.android.fcm.api.PushNotificationApi;
import com.getqardio.android.fcm.api.PushRegistrationResponse;
import com.getqardio.android.mvp.common.util.RxUtil;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FCMManager {
    private PushNotificationApi api;
    private SharedPreferences prefs;

    public FCMManager(PushNotificationApi pushNotificationApi, SharedPreferences sharedPreferences) {
        this.api = pushNotificationApi;
        this.prefs = sharedPreferences;
    }

    private String getRequestBodyText(String string2, String string3) {
        return "authToken=" + string2 + "&platform=Android&deviceToken=" + string3;
    }

    public boolean isRegisteredForFCM() {
        return this.prefs.getBoolean("is_fcm_registered", false);
    }

    /* synthetic */ PushRegistrationResponse lambda$registerFCMToken$0(PushRegistrationResponse pushRegistrationResponse) throws Exception {
        SharedPreferences.Editor editor = this.prefs.edit();
        if ("success".equalsIgnoreCase(pushRegistrationResponse.getStatus())) {
            editor.putBoolean("is_fcm_registered", true).apply();
            return pushRegistrationResponse;
        }
        editor.putBoolean("is_fcm_registered", false).apply();
        return pushRegistrationResponse;
    }

    /* synthetic */ void lambda$registerFCMToken$1(Throwable throwable) throws Exception {
        this.prefs.edit().putBoolean("is_fcm_registered", false).apply();
    }

    public Completable registerFCMToken(String string2, String object) {
        if (string2 == null || string2.isEmpty() || object == null || ((String)object).isEmpty()) {
            return Completable.error(new IllegalArgumentException("auth or push token cannot be empty"));
        }
        object = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), this.getRequestBodyText(string2, (String)object));
        return this.api.sendFireBaseInstanceIdToken(string2, (RequestBody)object).onErrorReturnItem(new PushRegistrationResponse()).compose(RxUtil.applySingleSchedulers()).map(FCMManager$$Lambda$1.lambdaFactory$(this)).toCompletable().doOnError(FCMManager$$Lambda$2.lambdaFactory$(this));
    }

    public void setRegistrationPending() {
        this.prefs.edit().putBoolean("is_fcm_registered", false).apply();
    }
}

