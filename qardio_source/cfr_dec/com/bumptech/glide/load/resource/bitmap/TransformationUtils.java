/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

public final class TransformationUtils {
    /*
     * Enabled aggressive block sorting
     */
    public static Bitmap centerCrop(Bitmap bitmap, Bitmap bitmap2, int n, int n2) {
        float f;
        Bitmap bitmap3;
        if (bitmap2 == null) {
            return null;
        }
        if (bitmap2.getWidth() == n) {
            bitmap3 = bitmap2;
            if (bitmap2.getHeight() == n2) return bitmap3;
        }
        float f2 = 0.0f;
        float f3 = 0.0f;
        bitmap3 = new Matrix();
        if (bitmap2.getWidth() * n2 > bitmap2.getHeight() * n) {
            f = (float)n2 / (float)bitmap2.getHeight();
            f2 = ((float)n - (float)bitmap2.getWidth() * f) * 0.5f;
        } else {
            f = (float)n / (float)bitmap2.getWidth();
            f3 = ((float)n2 - (float)bitmap2.getHeight() * f) * 0.5f;
        }
        bitmap3.setScale(f, f);
        bitmap3.postTranslate((float)((int)(f2 + 0.5f)), (float)((int)(f3 + 0.5f)));
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)TransformationUtils.getSafeConfig(bitmap2));
        }
        TransformationUtils.setAlpha(bitmap2, bitmap);
        new Canvas(bitmap).drawBitmap(bitmap2, (Matrix)bitmap3, new Paint(6));
        return bitmap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Bitmap fitCenter(Bitmap bitmap, BitmapPool bitmapPool, int n, int n2) {
        if (bitmap.getWidth() == n && bitmap.getHeight() == n2) {
            if (!Log.isLoggable((String)"TransformationUtils", (int)2)) return bitmap;
            {
                Log.v((String)"TransformationUtils", (String)"requested target size matches input, returning input");
            }
            return bitmap;
        }
        float f = Math.min((float)n / (float)bitmap.getWidth(), (float)n2 / (float)bitmap.getHeight());
        int n3 = (int)((float)bitmap.getWidth() * f);
        int n4 = (int)((float)bitmap.getHeight() * f);
        if (bitmap.getWidth() == n3 && bitmap.getHeight() == n4) {
            if (!Log.isLoggable((String)"TransformationUtils", (int)2)) return bitmap;
            {
                Log.v((String)"TransformationUtils", (String)"adjusted target size matches input, returning input");
                return bitmap;
            }
        }
        Bitmap.Config config = TransformationUtils.getSafeConfig(bitmap);
        Bitmap bitmap2 = bitmapPool.get(n3, n4, config);
        bitmapPool = bitmap2;
        if (bitmap2 == null) {
            bitmapPool = Bitmap.createBitmap((int)n3, (int)n4, (Bitmap.Config)config);
        }
        TransformationUtils.setAlpha(bitmap, (Bitmap)bitmapPool);
        if (Log.isLoggable((String)"TransformationUtils", (int)2)) {
            Log.v((String)"TransformationUtils", (String)("request: " + n + "x" + n2));
            Log.v((String)"TransformationUtils", (String)("toFit:   " + bitmap.getWidth() + "x" + bitmap.getHeight()));
            Log.v((String)"TransformationUtils", (String)("toReuse: " + bitmapPool.getWidth() + "x" + bitmapPool.getHeight()));
            Log.v((String)"TransformationUtils", (String)("minPct:   " + f));
        }
        bitmap2 = new Canvas((Bitmap)bitmapPool);
        config = new Matrix();
        config.setScale(f, f);
        bitmap2.drawBitmap(bitmap, (Matrix)config, new Paint(6));
        return bitmapPool;
    }

    public static int getExifOrientationDegrees(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 5: 
            case 6: {
                return 90;
            }
            case 3: 
            case 4: {
                return 180;
            }
            case 7: 
            case 8: 
        }
        return 270;
    }

    private static Bitmap.Config getSafeConfig(Bitmap bitmap) {
        if (bitmap.getConfig() != null) {
            return bitmap.getConfig();
        }
        return Bitmap.Config.ARGB_8888;
    }

    static void initializeMatrixForRotation(int n, Matrix matrix) {
        switch (n) {
            default: {
                return;
            }
            case 2: {
                matrix.setScale(-1.0f, 1.0f);
                return;
            }
            case 3: {
                matrix.setRotate(180.0f);
                return;
            }
            case 4: {
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            }
            case 5: {
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            }
            case 6: {
                matrix.setRotate(90.0f);
                return;
            }
            case 7: {
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            }
            case 8: 
        }
        matrix.setRotate(-90.0f);
    }

    public static Bitmap rotateImageExif(Bitmap bitmap, BitmapPool bitmapPool, int n) {
        Matrix matrix = new Matrix();
        TransformationUtils.initializeMatrixForRotation(n, matrix);
        if (matrix.isIdentity()) {
            return bitmap;
        }
        RectF rectF = new RectF(0.0f, 0.0f, (float)bitmap.getWidth(), (float)bitmap.getHeight());
        matrix.mapRect(rectF);
        n = Math.round(rectF.width());
        int n2 = Math.round(rectF.height());
        Bitmap.Config config = TransformationUtils.getSafeConfig(bitmap);
        Bitmap bitmap2 = bitmapPool.get(n, n2, config);
        bitmapPool = bitmap2;
        if (bitmap2 == null) {
            bitmapPool = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)config);
        }
        matrix.postTranslate(-rectF.left, -rectF.top);
        new Canvas((Bitmap)bitmapPool).drawBitmap(bitmap, matrix, new Paint(6));
        return bitmapPool;
    }

    @TargetApi(value=12)
    public static void setAlpha(Bitmap bitmap, Bitmap bitmap2) {
        if (Build.VERSION.SDK_INT >= 12 && bitmap2 != null) {
            bitmap2.setHasAlpha(bitmap.hasAlpha());
        }
    }
}

