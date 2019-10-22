/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.common.gson_annotation.ConvertData;
import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

@ConvertData
public class GetIFollowUserResponse
extends BaseResponse {
    @Expose
    @SerializedName(value="users")
    public List<User> users = new ArrayList<User>();

    public GetIFollowUserResponse() {
        super("success");
    }

    public List<User> getUsers() {
        return this.users;
    }
}

