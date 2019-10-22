/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.friends_family.common.InviteDeleteAcceptToFollowMeRequest;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.GetFollowingMeUserResponse;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.EnablePushNotificationsRequest;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.GetIFollowUserResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerInterface {
    @POST(value="/user/uninvite_friends_and_family")
    public Single<BaseResponse> deleteFollowingMeUser(@Query(value="authToken") String var1, @Body InviteDeleteAcceptToFollowMeRequest var2);

    @POST(value="/user/unfollow_friend")
    public Single<BaseResponse> deleteIFollowUser(@Query(value="authToken") String var1, @Body InviteDeleteAcceptToFollowMeRequest var2);

    @POST(value="/user/enable_push_notifications")
    public Single<BaseResponse> enablePushNotifications(@Query(value="authToken") String var1, @Body EnablePushNotificationsRequest var2);

    @GET(value="/user/view_friends_and_family")
    public Single<GetFollowingMeUserResponse> getFollowingMeUsers(@Query(value="authToken") String var1);

    @GET(value="/user/view_friends_and_family_data")
    public Single<GetIFollowUserResponse> getIFollowUsers(@Query(value="authToken") String var1);

    @POST(value="/user/invite_friends_and_family")
    public Single<BaseResponse> inviteUserToFollowMe(@Query(value="authToken") String var1, @Body InviteDeleteAcceptToFollowMeRequest var2);
}

