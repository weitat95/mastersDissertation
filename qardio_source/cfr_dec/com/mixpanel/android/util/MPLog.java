/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.mixpanel.android.util;

import android.util.Log;

public class MPLog {
    private static int sMinLevel = 5;

    public static void d(String string2, String string3) {
        if (MPLog.shouldLog(3)) {
            Log.d((String)string2, (String)string3);
        }
    }

    public static void d(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(3)) {
            Log.d((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public static void e(String string2, String string3) {
        if (MPLog.shouldLog(6)) {
            Log.e((String)string2, (String)string3);
        }
    }

    public static void e(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(6)) {
            Log.e((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public static void i(String string2, String string3) {
        if (MPLog.shouldLog(4)) {
            Log.i((String)string2, (String)string3);
        }
    }

    public static void i(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(4)) {
            Log.i((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public static void setLevel(int n) {
        sMinLevel = n;
    }

    private static boolean shouldLog(int n) {
        return sMinLevel <= n;
    }

    public static void v(String string2, String string3) {
        if (MPLog.shouldLog(2)) {
            Log.v((String)string2, (String)string3);
        }
    }

    public static void v(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(2)) {
            Log.v((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public static void w(String string2, String string3) {
        if (MPLog.shouldLog(5)) {
            Log.w((String)string2, (String)string3);
        }
    }

    public static void w(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(5)) {
            Log.w((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public static void wtf(String string2, String string3) {
        if (MPLog.shouldLog(6)) {
            Log.wtf((String)string2, (String)string3);
        }
    }

    public static void wtf(String string2, String string3, Throwable throwable) {
        if (MPLog.shouldLog(6)) {
            Log.wtf((String)string2, (String)string3, (Throwable)throwable);
        }
    }
}

