/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.google.gson.annotations.SerializedName;

public class EnablePushNotificationsRequest {
    @SerializedName(value="email")
    public String email;
    @SerializedName(value="enabled")
    public String enabled;

    public EnablePushNotificationsRequest(String string2, Boolean bl) {
        this.email = string2;
        this.enabled = String.valueOf(bl);
    }
}

