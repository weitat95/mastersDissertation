/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

public final class $Gson$Preconditions {
    public static void checkArgument(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }
}

