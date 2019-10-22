/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.common.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Closeables {
    static final Logger logger = Logger.getLogger(Closeables.class.getName());

    private Closeables() {
    }

    public static void close(@Nullable Closeable closeable, boolean bl) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            if (bl) {
                logger.log(Level.WARNING, "IOException thrown while closing Closeable.", iOException);
                return;
            }
            throw iOException;
        }
    }

    public static void closeQuietly(@Nullable InputStream inputStream) {
        try {
            Closeables.close(inputStream, true);
            return;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }
}

