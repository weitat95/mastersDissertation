/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.support.transition.AnimatorUtilsApi14;
import android.support.transition.AnimatorUtilsApi19;
import android.support.transition.AnimatorUtilsImpl;

class AnimatorUtils {
    private static final AnimatorUtilsImpl IMPL = Build.VERSION.SDK_INT >= 19 ? new AnimatorUtilsApi19() : new AnimatorUtilsApi14();

    static void addPauseListener(Animator animator2, AnimatorListenerAdapter animatorListenerAdapter) {
        IMPL.addPauseListener(animator2, animatorListenerAdapter);
    }

    static void pause(Animator animator2) {
        IMPL.pause(animator2);
    }

    static void resume(Animator animator2) {
        IMPL.resume(animator2);
    }
}

