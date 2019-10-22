/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Metadata;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class User {
    @SerializedName(value="accessStatus")
    public String accessStatus;
    @SerializedName(value="firstname")
    public String firstname;
    @SerializedName(value="lastname")
    public String lastname;
    @SerializedName(value="metadata")
    public List<Metadata> metadata = new ArrayList<Metadata>();
    @SerializedName(value="notifications")
    public Boolean notifications;
    @SerializedName(value="ownerEmail")
    public String ownerEmail;
    @SerializedName(value="watcherEmail")
    public String watcherEmail;

    public String getAccessStatus() {
        return this.accessStatus;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public List<Metadata> getMetadata() {
        return this.metadata;
    }

    public Boolean getNotifications() {
        return this.notifications;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public String getWatcherEmail() {
        return this.watcherEmail;
    }
}

