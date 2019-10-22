/*
 * Decompiled with CFR 0.147.
 */
package android.support.v4.util;

public class Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }
}

