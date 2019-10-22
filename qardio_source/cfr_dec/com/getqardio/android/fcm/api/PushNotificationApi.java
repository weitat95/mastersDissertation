/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm.api;

import com.getqardio.android.fcm.api.PushRegistrationResponse;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PushNotificationApi {
    @POST(value="device/register_for_remote_notification")
    public Single<PushRegistrationResponse> sendFireBaseInstanceIdToken(@Header(value="Authorization") String var1, @Body RequestBody var2);
}

