/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.local.model;

import io.realm.RealmObject;
import io.realm.UserRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

public class User
extends RealmObject
implements UserRealmProxyInterface {
    private String email;
    private String password;
    private String token;
    private Long tokenExpired;
    private String trackingId;
    private long userId;

    public User() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public String getEmail() {
        return this.realmGet$email();
    }

    public String getToken() {
        return this.realmGet$token();
    }

    @Override
    public String realmGet$email() {
        return this.email;
    }

    @Override
    public String realmGet$password() {
        return this.password;
    }

    @Override
    public String realmGet$token() {
        return this.token;
    }

    @Override
    public Long realmGet$tokenExpired() {
        return this.tokenExpired;
    }

    @Override
    public String realmGet$trackingId() {
        return this.trackingId;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$email(String string2) {
        this.email = string2;
    }

    @Override
    public void realmSet$password(String string2) {
        this.password = string2;
    }

    @Override
    public void realmSet$token(String string2) {
        this.token = string2;
    }

    @Override
    public void realmSet$tokenExpired(Long l) {
        this.tokenExpired = l;
    }

    @Override
    public void realmSet$trackingId(String string2) {
        this.trackingId = string2;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }

    public void setEmail(String string2) {
        this.realmSet$email(string2);
    }

    public void setPassword(String string2) {
        this.realmSet$password(string2);
    }

    public void setToken(String string2) {
        this.realmSet$token(string2);
    }

    public void setTokenExpired(Long l) {
        this.realmSet$tokenExpired(l);
    }

    public void setTrackingId(String string2) {
        this.realmSet$trackingId(string2);
    }

    public void setUserId(long l) {
        this.realmSet$userId(l);
    }
}

