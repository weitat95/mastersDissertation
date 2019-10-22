/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

public interface FollowMeUserRealmProxyInterface {
    public String realmGet$accessStatus();

    public String realmGet$firstName();

    public String realmGet$lastName();

    public boolean realmGet$notificationsEnabled();

    public int realmGet$syncStatus();

    public String realmGet$userEmail();

    public long realmGet$userId();

    public String realmGet$watcherEmail();

    public void realmSet$accessStatus(String var1);

    public void realmSet$firstName(String var1);

    public void realmSet$lastName(String var1);

    public void realmSet$notificationsEnabled(boolean var1);

    public void realmSet$syncStatus(int var1);

    public void realmSet$userEmail(String var1);

    public void realmSet$userId(long var1);

    public void realmSet$watcherEmail(String var1);
}

