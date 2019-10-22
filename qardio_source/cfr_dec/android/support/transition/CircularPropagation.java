/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.graphics.Rect;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.support.transition.VisibilityPropagation;
import android.view.ViewGroup;

public class CircularPropagation
extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;

    private static float distance(float f, float f2, float f3, float f4) {
        f = f3 - f;
        f2 = f4 - f2;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues rect, TransitionValues transitionValues) {
        int n;
        long l;
        int n2;
        if (rect == null && transitionValues == null) {
            return 0L;
        }
        int n3 = 1;
        if (transitionValues == null || this.getViewVisibility((TransitionValues)rect) == 0) {
            n3 = -1;
        } else {
            rect = transitionValues;
        }
        int n4 = this.getViewX((TransitionValues)rect);
        int n5 = this.getViewY((TransitionValues)rect);
        rect = transition.getEpicenter();
        if (rect != null) {
            n = rect.centerX();
            n2 = rect.centerY();
        } else {
            rect = new int[2];
            viewGroup.getLocationOnScreen((int[])rect);
            n = Math.round((float)(rect[0] + viewGroup.getWidth() / 2) + viewGroup.getTranslationX());
            n2 = Math.round((float)(rect[1] + viewGroup.getHeight() / 2) + viewGroup.getTranslationY());
        }
        float f = CircularPropagation.distance(n4, n5, n, n2) / CircularPropagation.distance(0.0f, 0.0f, viewGroup.getWidth(), viewGroup.getHeight());
        long l2 = l = transition.getDuration();
        if (l < 0L) {
            l2 = 300L;
        }
        return Math.round((float)((long)n3 * l2) / this.mPropagationSpeed * f);
    }
}

