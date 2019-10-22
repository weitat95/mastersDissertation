/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

interface AnimatorUtilsImpl {
    public void addPauseListener(Animator var1, AnimatorListenerAdapter var2);

    public void pause(Animator var1);

    public void resume(Animator var1);
}

