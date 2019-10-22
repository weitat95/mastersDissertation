/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Looper
 */
package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Looper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public final class Util {
    private static final char[] HEX_CHAR_ARRAY = "0123456789abcdef".toCharArray();
    private static final char[] SHA_1_CHARS;
    private static final char[] SHA_256_CHARS;

    static {
        SHA_256_CHARS = new char[64];
        SHA_1_CHARS = new char[40];
    }

    public static void assertMainThread() {
        if (!Util.isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    private static String bytesToHex(byte[] arrby, char[] arrc) {
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 0xFF;
            arrc[i * 2] = HEX_CHAR_ARRAY[n >>> 4];
            arrc[i * 2 + 1] = HEX_CHAR_ARRAY[n & 0xF];
        }
        return new String(arrc);
    }

    public static <T> Queue<T> createQueue(int n) {
        return new ArrayDeque(n);
    }

    public static int getBitmapByteSize(int n, int n2, Bitmap.Config config) {
        return n * n2 * Util.getBytesPerPixel(config);
    }

    @TargetApi(value=19)
    public static int getBitmapByteSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                int n = bitmap.getAllocationByteCount();
                return n;
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
        return bitmap.getHeight() * bitmap.getRowBytes();
    }

    private static int getBytesPerPixel(Bitmap.Config config) {
        Bitmap.Config config2 = config;
        if (config == null) {
            config2 = Bitmap.Config.ARGB_8888;
        }
        switch (config2) {
            default: {
                return 4;
            }
            case ALPHA_8: {
                return 1;
            }
            case RGB_565: 
            case ARGB_4444: 
        }
        return 2;
    }

    public static <T> List<T> getSnapshot(Collection<T> object) {
        ArrayList arrayList = new ArrayList(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(object.next());
        }
        return arrayList;
    }

    public static boolean isOnBackgroundThread() {
        return !Util.isOnMainThread();
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static boolean isValidDimension(int n) {
        return n > 0 || n == Integer.MIN_VALUE;
    }

    public static boolean isValidDimensions(int n, int n2) {
        return Util.isValidDimension(n) && Util.isValidDimension(n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String sha256BytesToHex(byte[] object) {
        char[] arrc = SHA_256_CHARS;
        synchronized (arrc) {
            return Util.bytesToHex(object, SHA_256_CHARS);
        }
    }

}

