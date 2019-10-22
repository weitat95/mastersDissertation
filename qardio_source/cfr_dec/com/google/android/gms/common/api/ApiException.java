/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Status;

public class ApiException
extends Exception {
    protected final Status mStatus;

    /*
     * Enabled aggressive block sorting
     */
    public ApiException(Status status) {
        int n = status.getStatusCode();
        String string2 = status.getStatusMessage() != null ? status.getStatusMessage() : "";
        super(new StringBuilder(String.valueOf(string2).length() + 13).append(n).append(": ").append(string2).toString());
        this.mStatus = status;
    }
}

