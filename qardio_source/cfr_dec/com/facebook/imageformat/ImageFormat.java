/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imageformat;

import javax.annotation.Nullable;

public class ImageFormat {
    public static final ImageFormat UNKNOWN = new ImageFormat("UNKNOWN", null);
    private final String mFileExtension;
    private final String mName;

    public ImageFormat(String string2, @Nullable String string3) {
        this.mName = string2;
        this.mFileExtension = string3;
    }

    public String getName() {
        return this.mName;
    }

    public String toString() {
        return this.getName();
    }

    public static interface FormatChecker {
        @Nullable
        public ImageFormat determineFormat(byte[] var1, int var2);
    }

}

