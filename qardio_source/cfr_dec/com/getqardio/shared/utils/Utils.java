/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.shared.utils;

public class Utils {
    public static boolean isDiaValid(int n) {
        return n > 40 && n < 250;
    }

    public static boolean isPulseValid(int n) {
        return n > 40 && n < 200;
    }

    public static boolean isSysValid(int n) {
        return n > 40 && n < 250;
    }
}

