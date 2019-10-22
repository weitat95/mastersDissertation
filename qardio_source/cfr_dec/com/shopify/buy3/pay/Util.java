/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.pay;

final class Util {
    static String checkNotEmpty(String string2, Object object) {
        if (string2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        if (string2.trim().length() == 0) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }
}

