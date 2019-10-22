/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.TranslateAnimation
 */
package io.branch.referral;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

public class AnimatedDialog
extends Dialog {
    private boolean isClosing_;
    private final boolean isFullWidthStyle_;

    private void slideClose() {
        if (!this.isClosing_) {
            this.isClosing_ = true;
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
            translateAnimation.setDuration(500L);
            translateAnimation.setInterpolator((Interpolator)new DecelerateInterpolator());
            ((ViewGroup)this.getWindow().getDecorView()).getChildAt(0).startAnimation((Animation)translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    AnimatedDialog.this.dismiss();
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
        }
    }

    private void slideOpen() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(500L);
        translateAnimation.setInterpolator((Interpolator)new AccelerateInterpolator());
        ((ViewGroup)this.getWindow().getDecorView()).getChildAt(0).startAnimation((Animation)translateAnimation);
        super.show();
    }

    public void cancel() {
        this.slideClose();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.isFullWidthStyle_) {
            bundle = this.getWindow().getAttributes();
            bundle.width = -1;
            this.getWindow().setAttributes((WindowManager.LayoutParams)bundle);
        }
    }

    public void setContentView(int n) {
        this.setDialogWindowAttributes();
        super.setContentView(n);
    }

    public void setDialogWindowAttributes() {
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.getWindow().addFlags(2);
        this.getWindow().addFlags(1024);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.gravity = 80;
        layoutParams.dimAmount = 0.8f;
        this.getWindow().setAttributes(layoutParams);
        this.getWindow().setWindowAnimations(17432578);
        this.setCanceledOnTouchOutside(true);
    }

    public void show() {
        this.slideOpen();
    }

}

