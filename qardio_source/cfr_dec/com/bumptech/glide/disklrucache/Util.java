/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.disklrucache;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

final class Util {
    static final Charset US_ASCII = Charset.forName("US-ASCII");
    static final Charset UTF_8 = Charset.forName("UTF-8");

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    static void deleteContents(File file2) throws IOException {
        File[] arrfile = file2.listFiles();
        if (arrfile == null) {
            throw new IOException("not a readable directory: " + file2);
        }
        for (File file2 : arrfile) {
            if (file2.isDirectory()) {
                Util.deleteContents(file2);
            }
            if (file2.delete()) continue;
            throw new IOException("failed to delete file: " + file2);
        }
    }
}

