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
import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$6
implements ValueAnimator.AnimatorUpdateListener {
    private final StartBPFragment arg$1;
    private final ViewGroup.LayoutParams arg$2;

    private StartBPFragment$$Lambda$6(StartBPFragment startBPFragment, ViewGroup.LayoutParams layoutParams) {
        this.arg$1 = startBPFragment;
        this.arg$2 = layoutParams;
    }

    public static ValueAnimator.AnimatorUpdateListener lambdaFactory$(StartBPFragment startBPFragment, ViewGroup.LayoutParams layoutParams) {
        return new StartBPFragment$$Lambda$6(startBPFragment, layoutParams);
    }

    @LambdaForm.Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$hideVisitorModeLabel$5(this.arg$2, valueAnimator);
    }
}

