/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.io.IOException;

public class FormatUtils {
    private static final double LOG_10 = Math.log(10.0);

    /*
     * Enabled aggressive block sorting
     */
    public static void appendPaddedInteger(Appendable appendable, int n, int n2) throws IOException {
        int n3 = n;
        if (n < 0) {
            appendable.append('-');
            n3 = n2;
            if (n == Integer.MIN_VALUE) {
                do {
                    if (n3 <= 10) {
                        appendable.append("2147483648");
                        return;
                    }
                    appendable.append('0');
                    --n3;
                } while (true);
            }
            n3 = -n;
        }
        if (n3 < 10) {
            do {
                if (n2 <= 1) {
                    appendable.append((char)(n3 + 48));
                    return;
                }
                appendable.append('0');
                --n2;
            } while (true);
        }
        if (n3 < 100) {
            do {
                if (n2 <= 2) {
                    n = (n3 + 1) * 13421772 >> 27;
                    appendable.append((char)(n + 48));
                    appendable.append((char)(n3 - (n << 3) - (n << 1) + 48));
                    return;
                }
                appendable.append('0');
                --n2;
            } while (true);
        }
        n = n3 < 1000 ? 3 : (n3 < 10000 ? 4 : (int)(Math.log(n3) / LOG_10) + 1);
        do {
            if (n2 <= n) {
                appendable.append(Integer.toString(n3));
                return;
            }
            appendable.append('0');
            --n2;
        } while (true);
    }

    public static void appendPaddedInteger(StringBuffer stringBuffer, int n, int n2) {
        try {
            FormatUtils.appendPaddedInteger((Appendable)stringBuffer, n, n2);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void appendUnpaddedInteger(Appendable appendable, int n) throws IOException {
        int n2 = n;
        if (n < 0) {
            appendable.append('-');
            if (n == Integer.MIN_VALUE) {
                appendable.append("2147483648");
                return;
            }
            n2 = -n;
        }
        if (n2 < 10) {
            appendable.append((char)(n2 + 48));
            return;
        }
        if (n2 < 100) {
            n = (n2 + 1) * 13421772 >> 27;
            appendable.append((char)(n + 48));
            appendable.append((char)(n2 - (n << 3) - (n << 1) + 48));
            return;
        }
        appendable.append(Integer.toString(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    static String createErrorMessage(String string2, int n) {
        int n2 = n + 32;
        String string3 = string2.length() <= n2 + 3 ? string2 : string2.substring(0, n2).concat("...");
        if (n <= 0) {
            return "Invalid format: \"" + string3 + '\"';
        }
        if (n >= string2.length()) {
            return "Invalid format: \"" + string3 + "\" is too short";
        }
        return "Invalid format: \"" + string3 + "\" is malformed at \"" + string3.substring(n) + '\"';
    }

    static int parseTwoDigits(CharSequence charSequence, int n) {
        int n2 = charSequence.charAt(n) - 48;
        return (n2 << 1) + (n2 << 3) + charSequence.charAt(n + 1) - 48;
    }
}

