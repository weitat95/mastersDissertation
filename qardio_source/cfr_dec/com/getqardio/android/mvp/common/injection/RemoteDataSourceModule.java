/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.fcm.api.PushNotificationApi;
import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.remote.GoalForActivityTypeRemoteDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.remote.HistoryActivityRemoteDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.remote.TodayActivityRemoteDataSource;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.CommonHeadersInterceptor;
import com.getqardio.android.mvp.common.remote.ConverterFactory;
import com.getqardio.android.mvp.common.remote.RxErrorHandlingCallAdapterFactory;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import com.getqardio.android.mvp.common.remote.UnauthorisedInterceptor;
import com.getqardio.android.mvp.common.util.RxEventBus;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceModule {
    public static CommonHeadersInterceptor getCommonHeadersInterceptor() {
        return new CommonHeadersInterceptor();
    }

    public static Gson getGson() {
        return new GsonBuilder().create();
    }

    public static OkHttpClient getOkHttpClient(UnauthorisedInterceptor unauthorisedInterceptor, CommonHeadersInterceptor commonHeadersInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.certificatePinner(new CertificatePinner.Builder().add("api.getqardio.com", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=").build());
        builder.addInterceptor(unauthorisedInterceptor);
        builder.addInterceptor(commonHeadersInterceptor);
        return builder.build();
    }

    public static Retrofit getRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl("https://api.getqardio.com").addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create()).addConverterFactory(ConverterFactory.create(gson)).client(okHttpClient).build();
    }

    public static ServerInterface getServerInterface(Retrofit retrofit) {
        return retrofit.create(ServerInterface.class);
    }

    public static Retrofit getSimplifiedRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.certificatePinner(new CertificatePinner.Builder().add("api.getqardio.com", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=").build());
        builder.addInterceptor(RemoteDataSourceModule.getCommonHeadersInterceptor());
        return new Retrofit.Builder().baseUrl("https://api.getqardio.com").addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    }

    public static UnauthorisedInterceptor getUnauthorisedInterceptor(RxEventBus rxEventBus) {
        return new UnauthorisedInterceptor(rxEventBus);
    }

    HistoryActivityDataSource provideActivityTrackerHistoryRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        return new HistoryActivityRemoteDataSource(serverInterface, accountContextHelper);
    }

    TodayActivityDataSource provideActivityTrackerRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        return new TodayActivityRemoteDataSource(serverInterface, accountContextHelper);
    }

    CommonHeadersInterceptor provideCommonHeadersInterceptor() {
        return RemoteDataSourceModule.getCommonHeadersInterceptor();
    }

    PushNotificationApi provideFCMTokenRegisterApi(Retrofit retrofit) {
        return retrofit.create(PushNotificationApi.class);
    }

    FollowMeUserDataSource provideFollowingMeUserRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        return new FollowMeUserRemoteDataSource(serverInterface, accountContextHelper);
    }

    GoalForActivityTypeDataSource provideGoalForActivityTypeRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        return new GoalForActivityTypeRemoteDataSource(serverInterface, accountContextHelper);
    }

    Gson provideGson() {
        return RemoteDataSourceModule.getGson();
    }

    IFollowUserDataSource provideIFollowUserRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        return new IFollowUserRemoteDataSource(serverInterface, accountContextHelper);
    }

    InviteApi provideInviteApi(Retrofit retrofit) {
        return retrofit.create(InviteApi.class);
    }

    OkHttpClient provideOkHttpClient(UnauthorisedInterceptor unauthorisedInterceptor, CommonHeadersInterceptor commonHeadersInterceptor) {
        return RemoteDataSourceModule.getOkHttpClient(unauthorisedInterceptor, commonHeadersInterceptor);
    }

    ServerInterface provideServerInterface(Gson gson, OkHttpClient okHttpClient) {
        return RemoteDataSourceModule.getServerInterface(RemoteDataSourceModule.getRetrofit(gson, okHttpClient));
    }

    UnauthorisedInterceptor provideUnathorizedInterceptor(RxEventBus rxEventBus) {
        return RemoteDataSourceModule.getUnauthorisedInterceptor(rxEventBus);
    }
}

