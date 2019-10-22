/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Map;

public class TextScale
extends Transition {
    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            TextView textView = (TextView)transitionValues.view;
            transitionValues.values.put("android:textscale:scale", Float.valueOf(textView.getScaleX()));
        }
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
     * Enabled aggressive block sorting
     */
    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues object, TransitionValues map) {
        float f;
        float f2;
        block3: {
            block2: {
                if (object == null || map == null || !(((TransitionValues)object).view instanceof TextView) || !(((TransitionValues)map).view instanceof TextView)) break block2;
                viewGroup = (TextView)((TransitionValues)map).view;
                object = ((TransitionValues)object).values;
                map = ((TransitionValues)map).values;
                f2 = object.get("android:textscale:scale") != null ? ((Float)object.get("android:textscale:scale")).floatValue() : 1.0f;
                if (f2 != (f = map.get("android:textscale:scale") != null ? ((Float)map.get("android:textscale:scale")).floatValue() : 1.0f)) break block3;
            }
            return null;
        }
        object = ValueAnimator.ofFloat((float[])new float[]{f2, f});
        object.addUpdateListener(new ValueAnimator.AnimatorUpdateListener((TextView)viewGroup){
            final /* synthetic */ TextView val$view;
            {
                this.val$view = textView;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                this.val$view.setScaleX(f);
                this.val$view.setScaleY(f);
            }
        });
        return object;
    }

}

