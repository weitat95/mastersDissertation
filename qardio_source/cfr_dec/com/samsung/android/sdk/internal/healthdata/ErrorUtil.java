/*
 * Decompiled with CFR 0.147.
 */
package com.samsung.android.sdk.internal.healthdata;

public final class ErrorUtil {
    public static String getNullArgumentMessage() {
        return "Argument is null";
    }

    public static String getRemoteExceptionMessage(Exception exception) {
        return "A remote-invocation error occurs on the connection: " + exception.toString();
    }
}

