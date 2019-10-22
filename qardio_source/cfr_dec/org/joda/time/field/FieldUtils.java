/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.IllegalFieldValueException;

public class FieldUtils {
    public static boolean equals(Object object, Object object2) {
        if (object == object2) {
            return true;
        }
        if (object == null || object2 == null) {
            return false;
        }
        return object.equals(object2);
    }

    public static int safeAdd(int n, int n2) {
        int n3 = n + n2;
        if ((n ^ n3) < 0 && (n ^ n2) >= 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + n + " + " + n2);
        }
        return n3;
    }

    public static long safeAdd(long l, long l2) {
        long l3 = l + l2;
        if ((l ^ l3) < 0L && (l ^ l2) >= 0L) {
            throw new ArithmeticException("The calculation caused an overflow: " + l + " + " + l2);
        }
        return l3;
    }

    public static long safeMultiply(long l, int n) {
        long l2 = l;
        switch (n) {
            default: {
                l2 = (long)n * l;
                if (l2 / (long)n == l) break;
                throw new ArithmeticException("Multiplication overflows a long: " + l + " * " + n);
            }
            case -1: {
                if (l == Long.MIN_VALUE) {
                    throw new ArithmeticException("Multiplication overflows a long: " + l + " * " + n);
                }
                l2 = -l;
            }
            case 1: {
                return l2;
            }
            case 0: {
                return 0L;
            }
        }
        return l2;
    }

    public static long safeMultiply(long l, long l2) {
        if (l2 == 1L) {
            return l;
        }
        if (l == 1L) {
            return l2;
        }
        if (l == 0L || l2 == 0L) {
            return 0L;
        }
        long l3 = l * l2;
        if (l3 / l2 != l || l == Long.MIN_VALUE && l2 == -1L || l2 == Long.MIN_VALUE && l == -1L) {
            throw new ArithmeticException("Multiplication overflows a long: " + l + " * " + l2);
        }
        return l3;
    }

    public static int safeToInt(long l) {
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            return (int)l;
        }
        throw new ArithmeticException("Value cannot fit in an int: " + l);
    }

    public static void verifyValueBounds(DateTimeField dateTimeField, int n, int n2, int n3) {
        if (n < n2 || n > n3) {
            throw new IllegalFieldValueException(dateTimeField.getType(), n, n2, n3);
        }
    }
}

