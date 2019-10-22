/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageutils;

import java.io.IOException;
import java.io.InputStream;

class StreamProcessor {
    /*
     * Enabled aggressive block sorting
     */
    public static int readPackedInt(InputStream inputStream, int n, boolean bl) throws IOException {
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            int n4 = inputStream.read();
            if (n4 == -1) {
                throw new IOException("no more bytes");
            }
            n2 = bl ? (n2 |= (n4 & 0xFF) << n3 * 8) : n2 << 8 | n4 & 0xFF;
            ++n3;
        }
        return n2;
    }
}

