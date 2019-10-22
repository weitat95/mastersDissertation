/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.response;

import java.util.concurrent.TimeUnit;

public class LoginResponse {
    private String accessToken = "";
    private long expiresDate = 0L;
    private String refreshToken = "";
    private String scope = "";
    private String tokenType = "";
    private String userId;

    public String getAccessToken() {
        return this.accessToken;
    }

    public long getExpiresDate() {
        return this.expiresDate;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAccessToken(String string2) {
        this.accessToken = string2;
    }

    public void setExpiresIn(long l) {
        this.expiresDate = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(l);
    }

    public void setRefreshToken(String string2) {
        this.refreshToken = string2;
    }

    public void setScope(String string2) {
        this.scope = string2;
    }

    public void setTokenType(String string2) {
        this.tokenType = string2;
    }

    public void setUserId(String string2) {
        this.userId = string2;
    }
}

