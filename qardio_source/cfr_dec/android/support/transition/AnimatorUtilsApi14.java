/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.transition.AnimatorUtilsImpl;
import java.util.ArrayList;

class AnimatorUtilsApi14
implements AnimatorUtilsImpl {
    AnimatorUtilsApi14() {
    }

    @Override
    public void addPauseListener(Animator animator2, AnimatorListenerAdapter animatorListenerAdapter) {
    }

    @Override
    public void pause(Animator animator2) {
        ArrayList arrayList = animator2.getListeners();
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(i);
                if (!(animatorListener instanceof AnimatorPauseListenerCompat)) continue;
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationPause(animator2);
            }
        }
    }

    @Override
    public void resume(Animator animator2) {
        ArrayList arrayList = animator2.getListeners();
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Animator.AnimatorListener animatorListener = (Animator.AnimatorListener)arrayList.get(i);
                if (!(animatorListener instanceof AnimatorPauseListenerCompat)) continue;
                ((AnimatorPauseListenerCompat)animatorListener).onAnimationResume(animator2);
            }
        }
    }

    static interface AnimatorPauseListenerCompat {
        public void onAnimationPause(Animator var1);

        public void onAnimationResume(Animator var1);
    }

}

