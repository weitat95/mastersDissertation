/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.graphics.Rect;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.support.transition.VisibilityPropagation;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

public class SidePropagation
extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    /*
     * Enabled aggressive block sorting
     */
    private int distance(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        n9 = this.mSide == 8388611 ? ((n9 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0) != 0 ? 5 : 3) : (this.mSide == 8388613 ? ((n9 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0) != 0 ? 3 : 5) : this.mSide);
        switch (n9) {
            default: {
                return 0;
            }
            case 3: {
                return n7 - n + Math.abs(n4 - n2);
            }
            case 48: {
                return n8 - n2 + Math.abs(n3 - n);
            }
            case 5: {
                return n - n5 + Math.abs(n4 - n2);
            }
            case 80: 
        }
        return n2 - n6 + Math.abs(n3 - n);
    }

    private int getMaxDistance(ViewGroup viewGroup) {
        switch (this.mSide) {
            default: {
                return viewGroup.getHeight();
            }
            case 3: 
            case 5: 
            case 8388611: 
            case 8388613: 
        }
        return viewGroup.getWidth();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues object, TransitionValues transitionValues) {
        int n;
        int n2;
        long l;
        if (object == null && transitionValues == null) {
            return 0L;
        }
        int n3 = 1;
        Rect rect = transition.getEpicenter();
        if (transitionValues == null || this.getViewVisibility((TransitionValues)object) == 0) {
            n3 = -1;
        } else {
            object = transitionValues;
        }
        int n4 = this.getViewX((TransitionValues)object);
        int n5 = this.getViewY((TransitionValues)object);
        object = new int[2];
        viewGroup.getLocationOnScreen(object);
        int n6 = object[0] + Math.round(viewGroup.getTranslationX());
        int n7 = object[1] + Math.round(viewGroup.getTranslationY());
        int n8 = n6 + viewGroup.getWidth();
        int n9 = n7 + viewGroup.getHeight();
        if (rect != null) {
            n = rect.centerX();
            n2 = rect.centerY();
        } else {
            n = (n6 + n8) / 2;
            n2 = (n7 + n9) / 2;
        }
        float f = (float)this.distance((View)viewGroup, n4, n5, n, n2, n6, n7, n8, n9) / (float)this.getMaxDistance(viewGroup);
        long l2 = l = transition.getDuration();
        if (l < 0L) {
            l2 = 300L;
        }
        return Math.round((float)((long)n3 * l2) / this.mPropagationSpeed * f);
    }

    public void setSide(int n) {
        this.mSide = n;
    }
}

