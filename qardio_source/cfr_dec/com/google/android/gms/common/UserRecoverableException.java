/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common;

import android.content.Intent;

public class UserRecoverableException
extends Exception {
    private final Intent mIntent;

    public UserRecoverableException(String string2, Intent intent) {
        super(string2);
        this.mIntent = intent;
    }
}

