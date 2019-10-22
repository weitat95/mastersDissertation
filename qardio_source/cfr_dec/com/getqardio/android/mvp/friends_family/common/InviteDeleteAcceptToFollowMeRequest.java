/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.common;

import com.google.gson.annotations.SerializedName;

public class InviteDeleteAcceptToFollowMeRequest {
    @SerializedName(value="recipientId")
    public String email;

    public InviteDeleteAcceptToFollowMeRequest(String string2) {
        this.email = string2;
    }
}

