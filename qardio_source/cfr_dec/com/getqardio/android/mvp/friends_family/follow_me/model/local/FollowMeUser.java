/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import io.realm.FollowMeUserRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class FollowMeUser
extends RealmObject
implements FollowMeUserRealmProxyInterface {
    private String accessStatus;
    private String firstName;
    private String lastName;
    private boolean notificationsEnabled;
    private int syncStatus;
    private String userEmail;
    private long userId;
    private String watcherEmail;

    public FollowMeUser() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (FollowMeUser)object;
                if (this.realmGet$syncStatus() != ((FollowMeUser)object).realmGet$syncStatus()) {
                    return false;
                }
                if (this.realmGet$watcherEmail() != null) {
                    return this.realmGet$watcherEmail().equals(((FollowMeUser)object).realmGet$watcherEmail());
                }
                if (((FollowMeUser)object).realmGet$watcherEmail() != null) break block7;
            }
            return true;
        }
        return false;
    }

    public FFTypes.Status getAccessStatus() {
        return FFTypes.Status.valueOf(this.realmGet$accessStatus());
    }

    public String getFirstName() {
        return this.realmGet$firstName();
    }

    public String getLastName() {
        return this.realmGet$lastName();
    }

    public SyncStatus getSyncStatus() {
        return SyncStatus.values()[this.realmGet$syncStatus()];
    }

    public String getWatcherEmail() {
        return this.realmGet$watcherEmail();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        if (this.realmGet$watcherEmail() != null) {
            n = this.realmGet$watcherEmail().hashCode();
            do {
                return n * 31 + this.realmGet$syncStatus();
                break;
            } while (true);
        }
        n = 0;
        return n * 31 + this.realmGet$syncStatus();
    }

    @Override
    public String realmGet$accessStatus() {
        return this.accessStatus;
    }

    @Override
    public String realmGet$firstName() {
        return this.firstName;
    }

    @Override
    public String realmGet$lastName() {
        return this.lastName;
    }

    @Override
    public boolean realmGet$notificationsEnabled() {
        return this.notificationsEnabled;
    }

    @Override
    public int realmGet$syncStatus() {
        return this.syncStatus;
    }

    @Override
    public String realmGet$userEmail() {
        return this.userEmail;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public String realmGet$watcherEmail() {
        return this.watcherEmail;
    }

    @Override
    public void realmSet$accessStatus(String string2) {
        this.accessStatus = string2;
    }

    @Override
    public void realmSet$firstName(String string2) {
        this.firstName = string2;
    }

    @Override
    public void realmSet$lastName(String string2) {
        this.lastName = string2;
    }

    @Override
    public void realmSet$notificationsEnabled(boolean bl) {
        this.notificationsEnabled = bl;
    }

    @Override
    public void realmSet$syncStatus(int n) {
        this.syncStatus = n;
    }

    @Override
    public void realmSet$userEmail(String string2) {
        this.userEmail = string2;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }

    @Override
    public void realmSet$watcherEmail(String string2) {
        this.watcherEmail = string2;
    }

    public void setAccessStatus(FFTypes.Status status) {
        this.realmSet$accessStatus(status.name());
    }

    public void setFirstName(String string2) {
        this.realmSet$firstName(string2);
    }

    public void setLastName(String string2) {
        this.realmSet$lastName(string2);
    }

    public void setNotificationsEnabled(boolean bl) {
        this.realmSet$notificationsEnabled(bl);
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.realmSet$syncStatus(syncStatus.ordinal());
    }

    public void setUserEmail(String string2) {
        this.realmSet$userEmail(string2);
    }

    public void setUserId(long l) {
        this.realmSet$userId(l);
    }

    public void setWatcherEmail(String string2) {
        this.realmSet$watcherEmail(string2);
    }

    public String toString() {
        return "FollowMeUser{watcherEmail='" + this.realmGet$watcherEmail() + '\'' + ", userEmail='" + this.realmGet$userEmail() + '\'' + ", accessStatus='" + this.realmGet$accessStatus() + '\'' + ", notificationsEnabled=" + this.realmGet$notificationsEnabled() + ", firstName='" + this.realmGet$firstName() + '\'' + ", lastName='" + this.realmGet$lastName() + '\'' + ", syncStatus=" + this.realmGet$syncStatus() + '}';
    }
}

