/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorPauseListener
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.transition.AnimatorUtilsImpl;

class AnimatorUtilsApi19
implements AnimatorUtilsImpl {
    AnimatorUtilsApi19() {
    }

    @Override
    public void addPauseListener(Animator animator2, AnimatorListenerAdapter animatorListenerAdapter) {
        animator2.addPauseListener((Animator.AnimatorPauseListener)animatorListenerAdapter);
    }

    @Override
    public void pause(Animator animator2) {
        animator2.pause();
    }

    @Override
    public void resume(Animator animator2) {
        animator2.resume();
    }
}

