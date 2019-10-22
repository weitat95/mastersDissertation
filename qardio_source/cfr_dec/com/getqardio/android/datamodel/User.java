/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class User
extends BaseEntity {
    public String email;
    public String emailHash;
    public String password;
    public String token;
    public Long tokenExpired;
    public String trackingId;
}

