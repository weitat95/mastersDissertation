/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.common.util;

import javax.annotation.Nullable;

public class HashCodeUtil {
    public static int hashCode(int n, int n2) {
        return (n + 31) * 31 + n2;
    }

    public static int hashCode(int n, int n2, int n3, int n4, int n5, int n6) {
        return (((((n + 31) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int hashCode(@Nullable Object object, @Nullable Object object2) {
        int n = 0;
        int n2 = object == null ? 0 : object.hashCode();
        if (object2 == null) {
            return HashCodeUtil.hashCode(n2, n);
        }
        n = object2.hashCode();
        return HashCodeUtil.hashCode(n2, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int hashCode(@Nullable Object object, @Nullable Object object2, @Nullable Object object3, @Nullable Object object4, @Nullable Object object5, @Nullable Object object6) {
        int n = 0;
        int n2 = object == null ? 0 : object.hashCode();
        int n3 = object2 == null ? 0 : object2.hashCode();
        int n4 = object3 == null ? 0 : object3.hashCode();
        int n5 = object4 == null ? 0 : object4.hashCode();
        int n6 = object5 == null ? 0 : object5.hashCode();
        if (object6 == null) {
            return HashCodeUtil.hashCode(n2, n3, n4, n5, n6, n);
        }
        n = object6.hashCode();
        return HashCodeUtil.hashCode(n2, n3, n4, n5, n6, n);
    }
}

