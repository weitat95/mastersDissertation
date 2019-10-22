/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package com.github.mikephil.charting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;

public abstract class Utils {
    public static final double DOUBLE_EPSILON;
    public static final float FLOAT_EPSILON;
    private static final int[] POW_10;
    private static Rect mCalcTextHeightRect;
    private static Rect mCalcTextSizeRect;
    private static IValueFormatter mDefaultValueFormatter;
    private static Rect mDrawTextRectBuffer;
    private static Paint.FontMetrics mFontMetrics;
    private static Paint.FontMetrics mFontMetricsBuffer;
    private static int mMaximumFlingVelocity;
    private static DisplayMetrics mMetrics;
    private static int mMinimumFlingVelocity;

    static {
        mMinimumFlingVelocity = 50;
        mMaximumFlingVelocity = 8000;
        DOUBLE_EPSILON = Double.longBitsToDouble(1L);
        FLOAT_EPSILON = Float.intBitsToFloat(1);
        mCalcTextHeightRect = new Rect();
        mFontMetrics = new Paint.FontMetrics();
        mCalcTextSizeRect = new Rect();
        POW_10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        mDefaultValueFormatter = Utils.generateDefaultValueFormatter();
        mDrawTextRectBuffer = new Rect();
        mFontMetricsBuffer = new Paint.FontMetrics();
    }

    public static int calcTextHeight(Paint paint, String string2) {
        Rect rect = mCalcTextHeightRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(string2, 0, string2.length(), rect);
        return rect.height();
    }

    public static FSize calcTextSize(Paint paint, String string2) {
        FSize fSize = FSize.getInstance(0.0f, 0.0f);
        Utils.calcTextSize(paint, string2, fSize);
        return fSize;
    }

    public static void calcTextSize(Paint paint, String string2, FSize fSize) {
        Rect rect = mCalcTextSizeRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(string2, 0, string2.length(), rect);
        fSize.width = rect.width();
        fSize.height = rect.height();
    }

    public static int calcTextWidth(Paint paint, String string2) {
        return (int)paint.measureText(string2);
    }

    public static float convertDpToPixel(float f) {
        if (mMetrics == null) {
            Log.e((String)"MPChartLib-Utils", (String)"Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
            return f;
        }
        return f * ((float)Utils.mMetrics.densityDpi / 160.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void drawXAxisValue(Canvas canvas, String string2, float f, float f2, Paint paint, MPPointF mPPointF, float f3) {
        Paint.Align align;
        block7: {
            float f4;
            block9: {
                float f5;
                float f6;
                float f7;
                block8: {
                    block4: {
                        float f8;
                        float f9;
                        float f10;
                        block6: {
                            block5: {
                                f7 = paint.getFontMetrics(mFontMetricsBuffer);
                                paint.getTextBounds(string2, 0, string2.length(), mDrawTextRectBuffer);
                                f6 = 0.0f - (float)Utils.mDrawTextRectBuffer.left;
                                f5 = 0.0f + -Utils.mFontMetricsBuffer.ascent;
                                align = paint.getTextAlign();
                                paint.setTextAlign(Paint.Align.LEFT);
                                if (f3 == 0.0f) break block4;
                                f9 = mDrawTextRectBuffer.width();
                                if (mPPointF.x != 0.5f) break block5;
                                f10 = f;
                                f8 = f2;
                                if (mPPointF.y == 0.5f) break block6;
                            }
                            FSize fSize = Utils.getSizeOfRotatedRectangleByDegrees(mDrawTextRectBuffer.width(), f7, f3);
                            f10 = f - fSize.width * (mPPointF.x - 0.5f);
                            f8 = f2 - fSize.height * (mPPointF.y - 0.5f);
                            FSize.recycleInstance(fSize);
                        }
                        canvas.save();
                        canvas.translate(f10, f8);
                        canvas.rotate(f3);
                        canvas.drawText(string2, f6 - f9 * 0.5f, f5 - 0.5f * f7, paint);
                        canvas.restore();
                        break block7;
                    }
                    if (mPPointF.x != 0.0f) break block8;
                    f4 = f6;
                    f3 = f5;
                    if (mPPointF.y == 0.0f) break block9;
                }
                f4 = f6 - (float)mDrawTextRectBuffer.width() * mPPointF.x;
                f3 = f5 - mPPointF.y * f7;
            }
            canvas.drawText(string2, f4 + f, f3 + f2, paint);
        }
        paint.setTextAlign(align);
    }

    private static IValueFormatter generateDefaultValueFormatter() {
        return new DefaultValueFormatter(1);
    }

    public static int getDecimals(float f) {
        if (Float.isInfinite(f = Utils.roundToNextSignificant(f))) {
            return 0;
        }
        return (int)Math.ceil(-Math.log10(f)) + 2;
    }

    public static IValueFormatter getDefaultValueFormatter() {
        return mDefaultValueFormatter;
    }

    public static float getLineHeight(Paint paint) {
        return Utils.getLineHeight(paint, mFontMetrics);
    }

    public static float getLineHeight(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getLineSpacing(Paint paint) {
        return Utils.getLineSpacing(paint, mFontMetrics);
    }

    public static float getLineSpacing(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.ascent - fontMetrics.top + fontMetrics.bottom;
    }

    public static int getMaximumFlingVelocity() {
        return mMaximumFlingVelocity;
    }

    public static int getMinimumFlingVelocity() {
        return mMinimumFlingVelocity;
    }

    public static float getNormalizedAngle(float f) {
        while (f < 0.0f) {
            f += 360.0f;
        }
        return f % 360.0f;
    }

    public static void getPosition(MPPointF mPPointF, float f, float f2, MPPointF mPPointF2) {
        mPPointF2.x = (float)((double)mPPointF.x + (double)f * Math.cos(Math.toRadians(f2)));
        mPPointF2.y = (float)((double)mPPointF.y + (double)f * Math.sin(Math.toRadians(f2)));
    }

    public static int getSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(float f, float f2, float f3) {
        return Utils.getSizeOfRotatedRectangleByRadians(f, f2, f3 * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(float f, float f2, float f3) {
        return FSize.getInstance(Math.abs((float)Math.cos(f3) * f) + Math.abs((float)Math.sin(f3) * f2), Math.abs((float)Math.sin(f3) * f) + Math.abs((float)Math.cos(f3) * f2));
    }

    public static void init(Context context) {
        if (context == null) {
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e((String)"MPChartLib-Utils", (String)"Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMetrics = context.getResources().getDisplayMetrics();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static double nextUp(double d) {
        long l;
        if (d == Double.POSITIVE_INFINITY) {
            return d;
        }
        long l2 = Double.doubleToRawLongBits(d += 0.0);
        if (d >= 0.0) {
            l = 1L;
            do {
                return Double.longBitsToDouble(l + l2);
                break;
            } while (true);
        }
        l = -1L;
        return Double.longBitsToDouble(l + l2);
    }

    @SuppressLint(value={"NewApi"})
    public static void postInvalidateOnAnimation(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
            return;
        }
        view.postInvalidateDelayed(10L);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static float roundToNextSignificant(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d) || d == 0.0) {
            return 0.0f;
        }
        double d2 = d < 0.0 ? -d : d;
        float f = (float)Math.pow(10.0, 1 - (int)Math.ceil((float)Math.log10(d2)));
        return (float)Math.round((double)f * d) / f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent motionEvent, VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(1000, (float)mMaximumFlingVelocity);
        int n = motionEvent.getActionIndex();
        int n2 = motionEvent.getPointerId(n);
        float f = velocityTracker.getXVelocity(n2);
        float f2 = velocityTracker.getYVelocity(n2);
        int n3 = motionEvent.getPointerCount();
        for (n2 = 0; n2 < n3; ++n2) {
            int n4;
            if (n2 == n || !(f * velocityTracker.getXVelocity(n4 = motionEvent.getPointerId(n2)) + f2 * velocityTracker.getYVelocity(n4) < 0.0f)) continue;
            velocityTracker.clear();
            return;
        }
    }
}

