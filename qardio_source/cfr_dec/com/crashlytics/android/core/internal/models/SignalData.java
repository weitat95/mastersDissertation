/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core.internal.models;

public class SignalData {
    public final String code;
    public final long faultAddress;
    public final String name;

    public SignalData(String string2, String string3, long l) {
        this.name = string2;
        this.code = string3;
        this.faultAddress = l;
    }
}

