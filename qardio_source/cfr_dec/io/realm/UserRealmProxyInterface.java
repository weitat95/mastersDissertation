/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

public interface UserRealmProxyInterface {
    public String realmGet$email();

    public String realmGet$password();

    public String realmGet$token();

    public Long realmGet$tokenExpired();

    public String realmGet$trackingId();

    public long realmGet$userId();

    public void realmSet$email(String var1);

    public void realmSet$password(String var1);

    public void realmSet$token(String var1);

    public void realmSet$tokenExpired(Long var1);

    public void realmSet$trackingId(String var1);

    public void realmSet$userId(long var1);
}

