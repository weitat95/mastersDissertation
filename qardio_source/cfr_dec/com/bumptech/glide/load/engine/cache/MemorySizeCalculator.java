/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.format.Formatter
 *  android.util.DisplayMetrics
 *  android.util.Log
 */
package com.bumptech.glide.load.engine.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

public class MemorySizeCalculator {
    private final int bitmapPoolSize;
    private final Context context;
    private final int memoryCacheSize;

    public MemorySizeCalculator(Context context) {
        this(context, (ActivityManager)context.getSystemService("activity"), new DisplayMetricsScreenDimensions(context.getResources().getDisplayMetrics()));
    }

    /*
     * Enabled aggressive block sorting
     */
    MemorySizeCalculator(Context object, ActivityManager activityManager, ScreenDimensions screenDimensions) {
        this.context = object;
        int n = MemorySizeCalculator.getMaxSize(activityManager);
        int n2 = screenDimensions.getWidthPixels() * screenDimensions.getHeightPixels() * 4;
        int n3 = n2 * 4;
        if ((n2 *= 2) + n3 <= n) {
            this.memoryCacheSize = n2;
            this.bitmapPoolSize = n3;
        } else {
            int n4 = Math.round((float)n / 6.0f);
            this.memoryCacheSize = n4 * 2;
            this.bitmapPoolSize = n4 * 4;
        }
        if (Log.isLoggable((String)"MemorySizeCalculator", (int)3)) {
            object = new StringBuilder().append("Calculated memory cache size: ").append(this.toMb(this.memoryCacheSize)).append(" pool size: ").append(this.toMb(this.bitmapPoolSize)).append(" memory class limited? ");
            boolean bl = n2 + n3 > n;
            Log.d((String)"MemorySizeCalculator", (String)((StringBuilder)object).append(bl).append(" max size: ").append(this.toMb(n)).append(" memoryClass: ").append(activityManager.getMemoryClass()).append(" isLowMemoryDevice: ").append(MemorySizeCalculator.isLowMemoryDevice(activityManager)).toString());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getMaxSize(ActivityManager activityManager) {
        float f;
        int n = activityManager.getMemoryClass();
        boolean bl = MemorySizeCalculator.isLowMemoryDevice(activityManager);
        float f2 = n * 1024 * 1024;
        if (bl) {
            f = 0.33f;
            do {
                return Math.round(f * f2);
                break;
            } while (true);
        }
        f = 0.4f;
        return Math.round(f * f2);
    }

    @TargetApi(value=19)
    private static boolean isLowMemoryDevice(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return Build.VERSION.SDK_INT < 11;
    }

    private String toMb(int n) {
        return Formatter.formatFileSize((Context)this.context, (long)n);
    }

    public int getBitmapPoolSize() {
        return this.bitmapPoolSize;
    }

    public int getMemoryCacheSize() {
        return this.memoryCacheSize;
    }

    private static class DisplayMetricsScreenDimensions
    implements ScreenDimensions {
        private final DisplayMetrics displayMetrics;

        public DisplayMetricsScreenDimensions(DisplayMetrics displayMetrics) {
            this.displayMetrics = displayMetrics;
        }

        @Override
        public int getHeightPixels() {
            return this.displayMetrics.heightPixels;
        }

        @Override
        public int getWidthPixels() {
            return this.displayMetrics.widthPixels;
        }
    }

    static interface ScreenDimensions {
        public int getHeightPixels();

        public int getWidthPixels();
    }

}

