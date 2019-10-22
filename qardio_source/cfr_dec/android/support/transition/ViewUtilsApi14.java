/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.os.IBinder
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.transition;

import android.graphics.Matrix;
import android.os.IBinder;
import android.support.transition.R;
import android.support.transition.ViewOverlayApi14;
import android.support.transition.ViewOverlayImpl;
import android.support.transition.ViewUtilsImpl;
import android.support.transition.WindowIdApi14;
import android.support.transition.WindowIdImpl;
import android.view.View;
import android.view.ViewParent;

class ViewUtilsApi14
implements ViewUtilsImpl {
    private float[] mMatrixValues;

    ViewUtilsApi14() {
    }

    @Override
    public void clearNonTransitionAlpha(View view) {
        if (view.getVisibility() == 0) {
            view.setTag(R.id.save_non_transition_alpha, null);
        }
    }

    @Override
    public ViewOverlayImpl getOverlay(View view) {
        return ViewOverlayApi14.createFrom(view);
    }

    @Override
    public float getTransitionAlpha(View view) {
        Float f = (Float)view.getTag(R.id.save_non_transition_alpha);
        if (f != null) {
            return view.getAlpha() / f.floatValue();
        }
        return view.getAlpha();
    }

    @Override
    public WindowIdImpl getWindowId(View view) {
        return new WindowIdApi14(view.getWindowToken());
    }

    @Override
    public void saveNonTransitionAlpha(View view) {
        if (view.getTag(R.id.save_non_transition_alpha) == null) {
            view.setTag(R.id.save_non_transition_alpha, (Object)Float.valueOf(view.getAlpha()));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setAnimationMatrix(View view, Matrix matrix) {
        float[] arrf;
        if (matrix == null || matrix.isIdentity()) {
            view.setPivotX((float)(view.getWidth() / 2));
            view.setPivotY((float)(view.getHeight() / 2));
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setRotation(0.0f);
            return;
        }
        float[] arrf2 = arrf = this.mMatrixValues;
        if (arrf == null) {
            this.mMatrixValues = arrf2 = new float[9];
        }
        matrix.getValues(arrf2);
        float f = arrf2[3];
        float f2 = (float)Math.sqrt(1.0f - f * f);
        int n = arrf2[0] < 0.0f ? -1 : 1;
        float f3 = f2 * (float)n;
        f = (float)Math.toDegrees(Math.atan2(f, f3));
        f2 = arrf2[0] / f3;
        f3 = arrf2[4] / f3;
        float f4 = arrf2[2];
        float f5 = arrf2[5];
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setTranslationX(f4);
        view.setTranslationY(f5);
        view.setRotation(f);
        view.setScaleX(f2);
        view.setScaleY(f3);
    }

    @Override
    public void setLeftTopRightBottom(View view, int n, int n2, int n3, int n4) {
        view.setLeft(n);
        view.setTop(n2);
        view.setRight(n3);
        view.setBottom(n4);
    }

    @Override
    public void setTransitionAlpha(View view, float f) {
        Float f2 = (Float)view.getTag(R.id.save_non_transition_alpha);
        if (f2 != null) {
            view.setAlpha(f2.floatValue() * f);
            return;
        }
        view.setAlpha(f);
    }

    @Override
    public void transformMatrixToGlobal(View view, Matrix matrix) {
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
            viewParent = (View)viewParent;
            this.transformMatrixToGlobal((View)viewParent, matrix);
            matrix.preTranslate((float)(-viewParent.getScrollX()), (float)(-viewParent.getScrollY()));
        }
        matrix.preTranslate((float)view.getLeft(), (float)view.getTop());
        view = view.getMatrix();
        if (!view.isIdentity()) {
            matrix.preConcat((Matrix)view);
        }
    }

    @Override
    public void transformMatrixToLocal(View view, Matrix matrix) {
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
            viewParent = (View)viewParent;
            this.transformMatrixToLocal((View)viewParent, matrix);
            matrix.postTranslate((float)viewParent.getScrollX(), (float)viewParent.getScrollY());
        }
        matrix.postTranslate((float)view.getLeft(), (float)view.getTop());
        view = view.getMatrix();
        if (!view.isIdentity() && view.invert((Matrix)(viewParent = new Matrix()))) {
            matrix.postConcat((Matrix)viewParent);
        }
    }
}

