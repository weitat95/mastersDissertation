/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

public final class Pow2 {
    public static int roundToPowerOfTwo(int n) {
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }
}

