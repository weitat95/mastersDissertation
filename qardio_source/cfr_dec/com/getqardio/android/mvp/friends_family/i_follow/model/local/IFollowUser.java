/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import io.realm.IFollowUserRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class IFollowUser
extends RealmObject
implements IFollowUserRealmProxyInterface {
    private String accessStatus;
    private BpLastMeasurement bpLastMeasurement;
    private String firstName;
    private String lastName;
    private boolean notificationsEnabled;
    private int syncStatus;
    private String userEmail;
    private long userId;
    private String watchingEmail;
    private WeightLastMeasurement weightLastMeasurement;

    public IFollowUser() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public String buildFullName() {
        if (this.realmGet$firstName() == null && this.realmGet$lastName() == null) {
            return "";
        }
        if (this.realmGet$firstName() == null) {
            return this.realmGet$lastName();
        }
        return this.realmGet$firstName() + " " + this.realmGet$lastName();
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
                object = (IFollowUser)object;
                if (this.realmGet$syncStatus() != ((IFollowUser)object).realmGet$syncStatus()) {
                    return false;
                }
                if (this.realmGet$watchingEmail() != null) {
                    return this.realmGet$watchingEmail().equals(((IFollowUser)object).realmGet$watchingEmail());
                }
                if (((IFollowUser)object).realmGet$watchingEmail() != null) break block7;
            }
            return true;
        }
        return false;
    }

    public FFTypes.Status getAccessStatus() {
        return FFTypes.Status.valueOf(this.realmGet$accessStatus());
    }

    public BpLastMeasurement getBpLastMeasurement() {
        return this.realmGet$bpLastMeasurement();
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

    public String getWatchingEmail() {
        return this.realmGet$watchingEmail();
    }

    public WeightLastMeasurement getWeightLastMeasurement() {
        return this.realmGet$weightLastMeasurement();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        if (this.realmGet$watchingEmail() != null) {
            n = this.realmGet$watchingEmail().hashCode();
            do {
                return n * 31 + this.realmGet$syncStatus();
                break;
            } while (true);
        }
        n = 0;
        return n * 31 + this.realmGet$syncStatus();
    }

    public boolean isNotificationsEnabled() {
        return this.realmGet$notificationsEnabled();
    }

    @Override
    public String realmGet$accessStatus() {
        return this.accessStatus;
    }

    @Override
    public BpLastMeasurement realmGet$bpLastMeasurement() {
        return this.bpLastMeasurement;
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
    public String realmGet$watchingEmail() {
        return this.watchingEmail;
    }

    @Override
    public WeightLastMeasurement realmGet$weightLastMeasurement() {
        return this.weightLastMeasurement;
    }

    @Override
    public void realmSet$accessStatus(String string2) {
        this.accessStatus = string2;
    }

    @Override
    public void realmSet$bpLastMeasurement(BpLastMeasurement bpLastMeasurement) {
        this.bpLastMeasurement = bpLastMeasurement;
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
    public void realmSet$watchingEmail(String string2) {
        this.watchingEmail = string2;
    }

    @Override
    public void realmSet$weightLastMeasurement(WeightLastMeasurement weightLastMeasurement) {
        this.weightLastMeasurement = weightLastMeasurement;
    }

    public void setAccessStatus(FFTypes.Status status) {
        this.realmSet$accessStatus(status.name());
    }

    public void setBpLastMeasurement(BpLastMeasurement bpLastMeasurement) {
        this.realmSet$bpLastMeasurement(bpLastMeasurement);
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

    public void setWatchingEmail(String string2) {
        this.realmSet$watchingEmail(string2);
    }

    public void setWeightLastMeasurement(WeightLastMeasurement weightLastMeasurement) {
        this.realmSet$weightLastMeasurement(weightLastMeasurement);
    }
}

