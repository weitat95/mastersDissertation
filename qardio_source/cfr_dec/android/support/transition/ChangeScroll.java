/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeScroll
extends Transition {
    private static final String[] PROPERTIES = new String[]{"android:changeScroll:x", "android:changeScroll:y"};

    public ChangeScroll() {
    }

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put("android:changeScroll:x", transitionValues.view.getScrollX());
        transitionValues.values.put("android:changeScroll:y", transitionValues.view.getScrollY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        View view = transitionValues2.view;
        int n = (Integer)transitionValues.values.get("android:changeScroll:x");
        int n2 = (Integer)transitionValues2.values.get("android:changeScroll:x");
        int n3 = (Integer)transitionValues.values.get("android:changeScroll:y");
        int n4 = (Integer)transitionValues2.values.get("android:changeScroll:y");
        viewGroup = null;
        transitionValues = null;
        if (n != n2) {
            view.setScrollX(n);
            viewGroup = ObjectAnimator.ofInt((Object)view, (String)"scrollX", (int[])new int[]{n, n2});
        }
        if (n3 != n4) {
            view.setScrollY(n3);
            transitionValues = ObjectAnimator.ofInt((Object)view, (String)"scrollY", (int[])new int[]{n3, n4});
        }
        return TransitionUtils.mergeAnimators((Animator)viewGroup, (Animator)transitionValues);
    }

    @Override
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }
}

