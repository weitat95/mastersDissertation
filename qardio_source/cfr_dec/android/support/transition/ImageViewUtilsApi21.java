/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.widget.ImageView
 */
package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.support.transition.ImageViewUtilsImpl;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ImageViewUtilsApi21
implements ImageViewUtilsImpl {
    private static Method sAnimateTransformMethod;
    private static boolean sAnimateTransformMethodFetched;

    ImageViewUtilsApi21() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void fetchAnimateTransformMethod() {
        if (!sAnimateTransformMethodFetched) {
            try {
                sAnimateTransformMethod = ImageView.class.getDeclaredMethod("animateTransform", Matrix.class);
                sAnimateTransformMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ImageViewUtilsApi21", (String)"Failed to retrieve animateTransform method", (Throwable)noSuchMethodException);
            }
            sAnimateTransformMethodFetched = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void animateTransform(ImageView imageView, Matrix matrix) {
        this.fetchAnimateTransformMethod();
        if (sAnimateTransformMethod == null) return;
        try {
            sAnimateTransformMethod.invoke((Object)imageView, new Object[]{matrix});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    @Override
    public void reserveEndAnimateTransform(ImageView imageView, Animator animator2) {
    }

    @Override
    public void startAnimateTransform(ImageView imageView) {
    }
}

