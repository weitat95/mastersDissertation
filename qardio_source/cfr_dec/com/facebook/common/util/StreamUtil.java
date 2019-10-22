/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.util;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
    /*
     * Enabled aggressive block sorting
     */
    public static long skip(InputStream inputStream, long l) throws IOException {
        Preconditions.checkNotNull(inputStream);
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl);
        long l2 = l;
        do {
            long l3 = l;
            if (l2 <= 0L) return l3;
            l3 = inputStream.skip(l2);
            if (l3 > 0L) {
                l2 -= l3;
                continue;
            }
            if (inputStream.read() == -1) return l - l2;
            --l2;
        } while (true);
    }
}

