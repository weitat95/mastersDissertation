/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.content.res;

import java.lang.reflect.Array;

final class GrowingArrayUtils {
    static final /* synthetic */ boolean $assertionsDisabled;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !GrowingArrayUtils.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private GrowingArrayUtils() {
    }

    public static int[] append(int[] arrn, int n, int n2) {
        if (!$assertionsDisabled && n > arrn.length) {
            throw new AssertionError();
        }
        int[] arrn2 = arrn;
        if (n + 1 > arrn.length) {
            arrn2 = new int[GrowingArrayUtils.growSize(n)];
            System.arraycopy(arrn, 0, arrn2, 0, n);
        }
        arrn2[n] = n2;
        return arrn2;
    }

    public static <T> T[] append(T[] arrT, int n, T t) {
        if (!$assertionsDisabled && n > arrT.length) {
            throw new AssertionError();
        }
        Object[] arrobject = arrT;
        if (n + 1 > arrT.length) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), GrowingArrayUtils.growSize(n));
            System.arraycopy(arrT, 0, arrobject, 0, n);
        }
        arrobject[n] = t;
        return arrobject;
    }

    public static int growSize(int n) {
        if (n <= 4) {
            return 8;
        }
        return n * 2;
    }
}

