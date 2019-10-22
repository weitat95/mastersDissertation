/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.support.transition.RectEvaluator;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.support.transition.ViewUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeClipBounds
extends Transition {
    private static final String[] sTransitionProperties = new String[]{"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void captureValues(TransitionValues transitionValues) {
        View view;
        block3: {
            block2: {
                view = transitionValues.view;
                if (view.getVisibility() == 8) break block2;
                Rect rect = ViewCompat.getClipBounds(view);
                transitionValues.values.put("android:clipBounds:clip", (Object)rect);
                if (rect == null) break block3;
            }
            return;
        }
        view = new Rect(0, 0, view.getWidth(), view.getHeight());
        transitionValues.values.put("android:clipBounds:bounds", (Object)view);
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
        void var1_6;
        Rect rect;
        Object var6_14;
        Rect rect2;
        void var3_13;
        Object var1_2 = var6_14 = null;
        if (rect == null) return var1_6;
        Object var1_3 = var6_14;
        if (var3_13 == null) return var1_6;
        Object var1_4 = var6_14;
        if (!rect.values.containsKey("android:clipBounds:clip")) return var1_6;
        if (!var3_13.values.containsKey("android:clipBounds:clip")) {
            Object var1_5 = var6_14;
            return var1_6;
        }
        Rect rect3 = (Rect)rect.values.get("android:clipBounds:clip");
        Rect rect4 = (Rect)var3_13.values.get("android:clipBounds:clip");
        boolean bl = rect4 == null;
        if (rect3 == null) {
            Object var1_7 = var6_14;
            if (rect4 == null) return var1_6;
        }
        if (rect3 == null) {
            rect2 = (Rect)rect.values.get("android:clipBounds:bounds");
            rect = rect4;
        } else {
            rect = rect4;
            rect2 = rect3;
            if (rect4 == null) {
                rect = (Rect)var3_13.values.get("android:clipBounds:bounds");
                rect2 = rect3;
            }
        }
        Object var1_9 = var6_14;
        if (rect2.equals((Object)rect)) return var1_6;
        {
            ViewCompat.setClipBounds(var3_13.view, rect2);
            RectEvaluator rectEvaluator = new RectEvaluator(new Rect());
            Rect rect5 = rect = ObjectAnimator.ofObject((Object)var3_13.view, ViewUtils.CLIP_BOUNDS, (TypeEvaluator)rectEvaluator, (Object[])new Rect[]{rect2, rect});
            if (!bl) return var1_6;
            {
                rect.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(var3_13.view){
                    final /* synthetic */ View val$endView;
                    {
                        this.val$endView = view;
                    }

                    public void onAnimationEnd(Animator animator2) {
                        ViewCompat.setClipBounds(this.val$endView, null);
                    }
                });
                return rect;
            }
        }
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}

