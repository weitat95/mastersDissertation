/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 */
package com.facebook.common.webp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.io.FileDescriptor;

public interface WebpBitmapFactory {
    public Bitmap decodeFileDescriptor(FileDescriptor var1, Rect var2, BitmapFactory.Options var3);

    public void setWebpErrorLogger(WebpErrorLogger var1);

    public static interface WebpErrorLogger {
    }

}

