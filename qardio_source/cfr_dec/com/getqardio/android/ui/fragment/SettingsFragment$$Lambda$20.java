/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.getqardio.android.ui.fragment;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$20
implements ValueAnimator.AnimatorUpdateListener {
    private final ViewGroup.LayoutParams arg$1;
    private final int arg$2;
    private final ViewGroup arg$3;

    private SettingsFragment$$Lambda$20(ViewGroup.LayoutParams layoutParams, int n, ViewGroup viewGroup) {
        this.arg$1 = layoutParams;
        this.arg$2 = n;
        this.arg$3 = viewGroup;
    }

    public static ValueAnimator.AnimatorUpdateListener lambdaFactory$(ViewGroup.LayoutParams layoutParams, int n, ViewGroup viewGroup) {
        return new SettingsFragment$$Lambda$20(layoutParams, n, viewGroup);
    }

    @LambdaForm.Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        SettingsFragment.lambda$hideLayout$18(this.arg$1, this.arg$2, this.arg$3, valueAnimator);
    }
}

