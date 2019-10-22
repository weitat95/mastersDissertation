/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

public class ResponseParser {
    /*
     * Enabled aggressive block sorting
     */
    public static int parse(int n) {
        block6: {
            block5: {
                if (n >= 200 && n <= 299) break block5;
                if (n >= 300 && n <= 399) {
                    return 1;
                }
                if (n < 400 || n > 499) break block6;
            }
            return 0;
        }
        if (n >= 500) {
            return 1;
        }
        return 1;
    }
}

