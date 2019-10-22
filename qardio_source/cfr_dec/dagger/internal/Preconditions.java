/*
 * Decompiled with CFR 0.147.
 */
package dagger.internal;

public final class Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public static <T> T checkNotNull(T t, String string2) {
        if (t == null) {
            throw new NullPointerException(string2);
        }
        return t;
    }
}

