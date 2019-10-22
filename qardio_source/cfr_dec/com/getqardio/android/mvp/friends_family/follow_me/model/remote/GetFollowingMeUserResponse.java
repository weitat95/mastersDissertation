/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.common.gson_annotation.ConvertData;
import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.User;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

@ConvertData
public class GetFollowingMeUserResponse
extends BaseResponse {
    @SerializedName(value="users")
    public List<User> users = new ArrayList<User>();

    public GetFollowingMeUserResponse() {
        super("success");
    }

    public List<User> getUsers() {
        return this.users;
    }
}

