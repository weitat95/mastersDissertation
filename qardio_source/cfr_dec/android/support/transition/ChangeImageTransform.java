/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.transition.ImageViewUtils;
import android.support.transition.MatrixUtils;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform
extends Transition {
    private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY;
    private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR;
    private static final String[] sTransitionProperties;

    static {
        sTransitionProperties = new String[]{"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};
        NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>(){

            public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
                return null;
            }
        };
        ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform"){

            public Matrix get(ImageView imageView) {
                return null;
            }

            public void set(ImageView imageView, Matrix matrix) {
                ImageViewUtils.animateTransform(imageView, matrix);
            }
        };
    }

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void captureValues(TransitionValues map) {
        ImageView imageView;
        View view = ((TransitionValues)map).view;
        if (!(view instanceof ImageView) || view.getVisibility() != 0 || (imageView = (ImageView)view).getDrawable() == null) {
            return;
        }
        map = ((TransitionValues)map).values;
        map.put("android:changeImageTransform:bounds", (Object)new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        map.put("android:changeImageTransform:matrix", (Object)ChangeImageTransform.copyImageMatrix(imageView));
    }

    private static Matrix centerCropMatrix(ImageView imageView) {
        Drawable drawable2 = imageView.getDrawable();
        int n = drawable2.getIntrinsicWidth();
        int n2 = imageView.getWidth();
        float f = (float)n2 / (float)n;
        int n3 = drawable2.getIntrinsicHeight();
        int n4 = imageView.getHeight();
        f = Math.max(f, (float)n4 / (float)n3);
        float f2 = n;
        float f3 = n3;
        n = Math.round(((float)n2 - f2 * f) / 2.0f);
        n4 = Math.round(((float)n4 - f3 * f) / 2.0f);
        imageView = new Matrix();
        imageView.postScale(f, f);
        imageView.postTranslate((float)n, (float)n4);
        return imageView;
    }

    private static Matrix copyImageMatrix(ImageView imageView) {
        switch (3.$SwitchMap$android$widget$ImageView$ScaleType[imageView.getScaleType().ordinal()]) {
            default: {
                return new Matrix(imageView.getImageMatrix());
            }
            case 1: {
                return ChangeImageTransform.fitXYMatrix(imageView);
            }
            case 2: 
        }
        return ChangeImageTransform.centerCropMatrix(imageView);
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix matrix, Matrix matrix2) {
        return ObjectAnimator.ofObject((Object)imageView, ANIMATED_TRANSFORM_PROPERTY, (TypeEvaluator)new TransitionUtils.MatrixEvaluator(), (Object[])new Matrix[]{matrix, matrix2});
    }

    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject((Object)imageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, (Object[])new Matrix[]{null, null});
    }

    private static Matrix fitXYMatrix(ImageView imageView) {
        Drawable drawable2 = imageView.getDrawable();
        Matrix matrix = new Matrix();
        matrix.postScale((float)imageView.getWidth() / (float)drawable2.getIntrinsicWidth(), (float)imageView.getHeight() / (float)drawable2.getIntrinsicHeight());
        return matrix;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ImageView imageView;
        void var1_5;
        Matrix matrix;
        Matrix matrix2;
        int n;
        block8: {
            block7: {
                if (matrix2 == null || imageView == null) break block7;
                Rect rect = (Rect)matrix2.values.get("android:changeImageTransform:bounds");
                Rect rect2 = (Rect)imageView.values.get("android:changeImageTransform:bounds");
                if (rect == null || rect2 == null) break block7;
                matrix2 = (Matrix)matrix2.values.get("android:changeImageTransform:matrix");
                matrix = (Matrix)imageView.values.get("android:changeImageTransform:matrix");
                n = matrix2 == null && matrix == null || matrix2 != null && matrix2.equals((Object)matrix) ? 1 : 0;
                if (!rect.equals((Object)rect2) || n == 0) break block8;
            }
            return null;
        }
        imageView = (ImageView)imageView.view;
        Drawable drawable2 = imageView.getDrawable();
        n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        ImageViewUtils.startAnimateTransform(imageView);
        if (n == 0 || n2 == 0) {
            ObjectAnimator objectAnimator = this.createNullAnimator(imageView);
        } else {
            void var1_8;
            Matrix matrix3 = matrix2;
            if (matrix2 == null) {
                Matrix matrix4 = MatrixUtils.IDENTITY_MATRIX;
            }
            matrix2 = matrix;
            if (matrix == null) {
                matrix2 = MatrixUtils.IDENTITY_MATRIX;
            }
            ANIMATED_TRANSFORM_PROPERTY.set((Object)imageView, (Object)var1_8);
            ObjectAnimator objectAnimator = this.createMatrixAnimator(imageView, (Matrix)var1_8, matrix2);
        }
        ImageViewUtils.reserveEndAnimateTransform(imageView, (Animator)var1_5);
        return var1_5;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}

