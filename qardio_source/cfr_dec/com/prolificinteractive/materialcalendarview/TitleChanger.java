/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.TimeInterpolator
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.TextView
 */
package com.prolificinteractive.materialcalendarview;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.AnimatorListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

class TitleChanger {
    private final int animDelay;
    private final int animDuration;
    private final Interpolator interpolator = new DecelerateInterpolator(2.0f);
    private long lastAnimTime = 0L;
    private int orientation = 0;
    private CalendarDay previousMonth = null;
    private final TextView title;
    private TitleFormatter titleFormatter;
    private final int translate;

    public TitleChanger(TextView textView) {
        this.title = textView;
        textView = textView.getResources();
        this.animDelay = 400;
        this.animDuration = textView.getInteger(17694720) / 2;
        this.translate = (int)TypedValue.applyDimension((int)1, (float)20.0f, (DisplayMetrics)textView.getDisplayMetrics());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doChange(long l, CalendarDay calendarDay, boolean bl) {
        this.title.animate().cancel();
        this.doTranslation(this.title, 0);
        this.title.setAlpha(1.0f);
        this.lastAnimTime = l;
        final CharSequence charSequence = this.titleFormatter.format(calendarDay);
        if (!bl) {
            this.title.setText(charSequence);
        } else {
            int n = this.translate;
            final int n2 = this.previousMonth.isBefore(calendarDay) ? 1 : -1;
            n2 = n * n2;
            ViewPropertyAnimator viewPropertyAnimator = this.title.animate();
            if (this.orientation == 1) {
                viewPropertyAnimator.translationX((float)(n2 * -1));
            } else {
                viewPropertyAnimator.translationY((float)(n2 * -1));
            }
            viewPropertyAnimator.alpha(0.0f).setDuration((long)this.animDuration).setInterpolator((TimeInterpolator)this.interpolator).setListener((Animator.AnimatorListener)new AnimatorListener(){

                @Override
                public void onAnimationCancel(Animator animator2) {
                    TitleChanger.this.doTranslation(TitleChanger.this.title, 0);
                    TitleChanger.this.title.setAlpha(1.0f);
                }

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void onAnimationEnd(Animator animator2) {
                    TitleChanger.this.title.setText(charSequence);
                    TitleChanger.this.doTranslation(TitleChanger.this.title, n2);
                    animator2 = TitleChanger.this.title.animate();
                    if (TitleChanger.this.orientation == 1) {
                        animator2.translationX(0.0f);
                    } else {
                        animator2.translationY(0.0f);
                    }
                    animator2.alpha(1.0f).setDuration((long)TitleChanger.this.animDuration).setInterpolator((TimeInterpolator)TitleChanger.this.interpolator).setListener((Animator.AnimatorListener)new AnimatorListener()).start();
                }
            }).start();
        }
        this.previousMonth = calendarDay;
    }

    private void doTranslation(TextView textView, int n) {
        if (this.orientation == 1) {
            textView.setTranslationX((float)n);
            return;
        }
        textView.setTranslationY((float)n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void change(CalendarDay calendarDay) {
        long l;
        block5: {
            block4: {
                l = System.currentTimeMillis();
                if (calendarDay == null) break block4;
                if (TextUtils.isEmpty((CharSequence)this.title.getText()) || l - this.lastAnimTime < (long)this.animDelay) {
                    this.doChange(l, calendarDay, false);
                }
                if (!calendarDay.equals(this.previousMonth) && (calendarDay.getMonth() != this.previousMonth.getMonth() || calendarDay.getYear() != this.previousMonth.getYear())) break block5;
            }
            return;
        }
        this.doChange(l, calendarDay, true);
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int n) {
        this.orientation = n;
    }

    public void setPreviousMonth(CalendarDay calendarDay) {
        this.previousMonth = calendarDay;
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

}

