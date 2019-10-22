/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.functions;

import io.reactivex.functions.BiPredicate;

public final class ObjectHelper {
    static final BiPredicate<Object, Object> EQUALS = new BiObjectPredicate();

    public static int compare(int n, int n2) {
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        return 0;
    }

    public static int compare(long l, long l2) {
        if (l < l2) {
            return -1;
        }
        if (l > l2) {
            return 1;
        }
        return 0;
    }

    public static boolean equals(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static <T> T requireNonNull(T t, String string2) {
        if (t == null) {
            throw new NullPointerException(string2);
        }
        return t;
    }

    public static int verifyPositive(int n, String string2) {
        if (n <= 0) {
            throw new IllegalArgumentException(string2 + " > 0 required but it was " + n);
        }
        return n;
    }

    static final class BiObjectPredicate
    implements BiPredicate<Object, Object> {
        BiObjectPredicate() {
        }
    }

}

