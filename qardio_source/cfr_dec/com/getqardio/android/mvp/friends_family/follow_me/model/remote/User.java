/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName(value="accessStatus")
    public String accessStatus;
    @SerializedName(value="firstname")
    public String firstName;
    @SerializedName(value="lastname")
    public String lastName;
    @SerializedName(value="notifications")
    public Boolean notifications;
    @SerializedName(value="watcherEmail")
    public String watcherEmail;

    public String getAccessStatus() {
        return this.accessStatus;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Boolean getNotifications() {
        return this.notifications;
    }

    public String getWatcherEmail() {
        return this.watcherEmail;
    }
}

