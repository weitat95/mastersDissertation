/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Rect
 */
package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;

public class ScalingUtils {

    public static abstract class AbstractScaleType
    implements ScaleType {
        @Override
        public Matrix getTransform(Matrix matrix, Rect rect, int n, int n2, float f, float f2) {
            this.getTransformImpl(matrix, rect, n, n2, f, f2, (float)rect.width() / (float)n, (float)rect.height() / (float)n2);
            return matrix;
        }

        public abstract void getTransformImpl(Matrix var1, Rect var2, int var3, int var4, float var5, float var6, float var7, float var8);
    }

    public static interface ScaleType {
        public static final ScaleType CENTER;
        public static final ScaleType CENTER_CROP;
        public static final ScaleType CENTER_INSIDE;
        public static final ScaleType FIT_CENTER;
        public static final ScaleType FIT_END;
        public static final ScaleType FIT_START;
        public static final ScaleType FIT_XY;
        public static final ScaleType FOCUS_CROP;

        static {
            FIT_XY = ScaleTypeFitXY.INSTANCE;
            FIT_START = ScaleTypeFitStart.INSTANCE;
            FIT_CENTER = ScaleTypeFitCenter.INSTANCE;
            FIT_END = ScaleTypeFitEnd.INSTANCE;
            CENTER = ScaleTypeCenter.INSTANCE;
            CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE;
            CENTER_CROP = ScaleTypeCenterCrop.INSTANCE;
            FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE;
        }

        public Matrix getTransform(Matrix var1, Rect var2, int var3, int var4, float var5, float var6);
    }

    private static class ScaleTypeCenter
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenter();

        private ScaleTypeCenter() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = rect.left;
            f2 = rect.width() - n;
            f3 = rect.top;
            f4 = rect.height() - n2;
            matrix.setTranslate((float)((int)(f + f2 * 0.5f + 0.5f)), (float)((int)(f3 + f4 * 0.5f + 0.5f)));
        }
    }

    private static class ScaleTypeCenterCrop
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterCrop();

        private ScaleTypeCenterCrop() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            if (f4 > f3) {
                f3 = f4;
                f = (float)rect.left + ((float)rect.width() - (float)n * f3) * 0.5f;
                f2 = rect.top;
            } else {
                f = rect.left;
                f2 = (float)rect.top + ((float)rect.height() - (float)n2 * f3) * 0.5f;
            }
            matrix.setScale(f3, f3);
            matrix.postTranslate((float)((int)(f + 0.5f)), (float)((int)(f2 + 0.5f)));
        }
    }

    private static class ScaleTypeCenterInside
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterInside();

        private ScaleTypeCenterInside() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = Math.min(Math.min(f3, f4), 1.0f);
            f2 = rect.left;
            f3 = rect.width();
            f4 = n;
            float f5 = rect.top;
            float f6 = rect.height();
            float f7 = n2;
            matrix.setScale(f, f);
            matrix.postTranslate((float)((int)(f2 + (f3 - f4 * f) * 0.5f + 0.5f)), (float)((int)(f5 + (f6 - f7 * f) * 0.5f + 0.5f)));
        }
    }

    private static class ScaleTypeFitCenter
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitCenter();

        private ScaleTypeFitCenter() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = Math.min(f3, f4);
            f2 = rect.left;
            f3 = rect.width();
            f4 = n;
            float f5 = rect.top;
            float f6 = rect.height();
            float f7 = n2;
            matrix.setScale(f, f);
            matrix.postTranslate((float)((int)(f2 + (f3 - f4 * f) * 0.5f + 0.5f)), (float)((int)(f5 + (f6 - f7 * f) * 0.5f + 0.5f)));
        }
    }

    private static class ScaleTypeFitEnd
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitEnd();

        private ScaleTypeFitEnd() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = Math.min(f3, f4);
            f2 = rect.left;
            f3 = rect.width();
            f4 = n;
            float f5 = rect.top;
            float f6 = rect.height();
            float f7 = n2;
            matrix.setScale(f, f);
            matrix.postTranslate((float)((int)(f2 + (f3 - f4 * f) + 0.5f)), (float)((int)(f5 + (f6 - f7 * f) + 0.5f)));
        }
    }

    private static class ScaleTypeFitStart
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitStart();

        private ScaleTypeFitStart() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = Math.min(f3, f4);
            f2 = rect.left;
            f3 = rect.top;
            matrix.setScale(f, f);
            matrix.postTranslate((float)((int)(f2 + 0.5f)), (float)((int)(0.5f + f3)));
        }
    }

    private static class ScaleTypeFitXY
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitXY();

        private ScaleTypeFitXY() {
        }

        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            f = rect.left;
            f2 = rect.top;
            matrix.setScale(f3, f4);
            matrix.postTranslate((float)((int)(f + 0.5f)), (float)((int)(0.5f + f2)));
        }
    }

    private static class ScaleTypeFocusCrop
    extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFocusCrop();

        private ScaleTypeFocusCrop() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void getTransformImpl(Matrix matrix, Rect rect, int n, int n2, float f, float f2, float f3, float f4) {
            if (f4 > f3) {
                f2 = rect.width();
                f3 = n;
                f = (float)rect.left + Math.max(Math.min(f2 * 0.5f - f3 * f4 * f, 0.0f), (float)rect.width() - (float)n * f4);
                f2 = rect.top;
                f3 = f4;
            } else {
                f = rect.left;
                f4 = rect.height();
                float f5 = n2;
                f2 = (float)rect.top + Math.max(Math.min(f4 * 0.5f - f5 * f3 * f2, 0.0f), (float)rect.height() - (float)n2 * f3);
            }
            matrix.setScale(f3, f3);
            matrix.postTranslate((float)((int)(0.5f + f)), (float)((int)(0.5f + f2)));
        }
    }

    public static interface StatefulScaleType {
        public Object getState();
    }

}

