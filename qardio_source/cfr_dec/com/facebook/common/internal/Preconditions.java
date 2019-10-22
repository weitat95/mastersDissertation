/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.common.internal;

import javax.annotation.Nullable;

public final class Preconditions {
    private static String badElementIndex(int n, int n2, @Nullable String string2) {
        if (n < 0) {
            return Preconditions.format("%s (%s) must not be negative", string2, n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative size: " + n2);
        }
        return Preconditions.format("%s (%s) must be less than size (%s)", string2, n, n2);
    }

    public static void checkArgument(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean bl, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string2, @Nullable Object ... arrobject) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string2, arrobject));
        }
    }

    public static int checkElementIndex(int n, int n2) {
        return Preconditions.checkElementIndex(n, n2, "index");
    }

    public static int checkElementIndex(int n, int n2, @Nullable String string2) {
        if (n < 0 || n >= n2) {
            throw new IndexOutOfBoundsException(Preconditions.badElementIndex(n, n2, string2));
        }
        return n;
    }

    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public static <T> T checkNotNull(T t, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    public static void checkState(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean bl, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    static String format(@Nullable String string2, @Nullable Object ... arrobject) {
        StringBuilder stringBuilder;
        block3: {
            string2 = String.valueOf(string2);
            stringBuilder = new StringBuilder(string2.length() + arrobject.length * 16);
            int n = 0;
            int n2 = 0;
            do {
                int n3;
                if (n2 >= arrobject.length || (n3 = string2.indexOf("%s", n)) == -1) {
                    stringBuilder.append(string2.substring(n));
                    if (n2 >= arrobject.length) break block3;
                    stringBuilder.append(" [");
                    stringBuilder.append(arrobject[n2]);
                    ++n2;
                    while (n2 < arrobject.length) {
                        stringBuilder.append(", ");
                        stringBuilder.append(arrobject[n2]);
                        ++n2;
                    }
                    break;
                }
                stringBuilder.append(string2.substring(n, n3));
                stringBuilder.append(arrobject[n2]);
                n = n3 + 2;
                ++n2;
            } while (true);
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }
}

