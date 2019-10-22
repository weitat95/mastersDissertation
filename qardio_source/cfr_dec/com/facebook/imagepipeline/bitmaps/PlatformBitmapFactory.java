/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.bitmaps;

public abstract class PlatformBitmapFactory {
    private static BitmapCreationObserver sBitmapCreationObserver;

    public void setCreationListener(BitmapCreationObserver bitmapCreationObserver) {
        if (sBitmapCreationObserver == null) {
            sBitmapCreationObserver = bitmapCreationObserver;
        }
    }

    public static interface BitmapCreationObserver {
    }

}

