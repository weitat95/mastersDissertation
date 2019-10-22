/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Bitmap
 *  android.graphics.Color
 *  android.view.View
 */
package com.mixpanel.android.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import com.mixpanel.android.util.MPLog;

public class ActivityImageUtils {
    public static int getHighlightColor(int n) {
        float[] arrf = new float[3];
        Color.colorToHSV((int)n, (float[])arrf);
        arrf[2] = 0.3f;
        return Color.HSVToColor((int)242, (float[])arrf);
    }

    public static int getHighlightColorFromBackground(Activity activity) {
        int n = -16777216;
        if ((activity = ActivityImageUtils.getScaledScreenshot(activity, 1, 1, false)) != null) {
            n = activity.getPixel(0, 0);
        }
        return ActivityImageUtils.getHighlightColor(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bitmap getScaledScreenshot(Activity activity, int n, int n2, boolean bl) {
        View view = activity.findViewById(16908290).getRootView();
        boolean bl2 = view.isDrawingCacheEnabled();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = view.getDrawingCache();
        Object var7_8 = null;
        activity = var7_8;
        if (bitmap != null) {
            activity = var7_8;
            if (bitmap.getWidth() > 0) {
                activity = var7_8;
                if (bitmap.getHeight() > 0) {
                    int n3 = n;
                    int n4 = n2;
                    if (bl) {
                        n3 = bitmap.getWidth() / n;
                        n4 = bitmap.getHeight() / n2;
                    }
                    activity = var7_8;
                    if (n3 > 0) {
                        activity = var7_8;
                        if (n4 > 0) {
                            try {
                                activity = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n3, (int)n4, (boolean)false);
                            }
                            catch (OutOfMemoryError outOfMemoryError) {
                                MPLog.i("MixpanelAPI.ActImgUtils", "Not enough memory to produce scaled image, returning a null screenshot");
                                activity = var7_8;
                            }
                        }
                    }
                }
            }
        }
        if (!bl2) {
            view.setDrawingCacheEnabled(false);
        }
        return activity;
    }
}

