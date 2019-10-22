/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.TimeInterpolator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package me.relex.circleindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import me.relex.circleindicator.R;

public class CircleIndicator
extends LinearLayout {
    private Animator mAnimatorIn;
    private Animator mAnimatorOut;
    private int mAnimatorResId = R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = 0;
    private Animator mImmediateAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private int mIndicatorBackgroundResId = R.drawable.white_radius;
    private int mIndicatorHeight = -1;
    private int mIndicatorMargin = -1;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
    private int mIndicatorWidth = -1;
    private DataSetObserver mInternalDataSetObserver;
    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
            View view;
            if (CircleIndicator.this.mViewpager.getAdapter() == null || CircleIndicator.this.mViewpager.getAdapter().getCount() <= 0) {
                return;
            }
            if (CircleIndicator.this.mAnimatorIn.isRunning()) {
                CircleIndicator.this.mAnimatorIn.end();
                CircleIndicator.this.mAnimatorIn.cancel();
            }
            if (CircleIndicator.this.mAnimatorOut.isRunning()) {
                CircleIndicator.this.mAnimatorOut.end();
                CircleIndicator.this.mAnimatorOut.cancel();
            }
            if (CircleIndicator.this.mLastPosition >= 0 && (view = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition)) != null) {
                view.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                CircleIndicator.this.mAnimatorIn.setTarget((Object)view);
                CircleIndicator.this.mAnimatorIn.start();
            }
            if ((view = CircleIndicator.this.getChildAt(n)) != null) {
                view.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                CircleIndicator.this.mAnimatorOut.setTarget((Object)view);
                CircleIndicator.this.mAnimatorOut.start();
            }
            CircleIndicator.this.mLastPosition = n;
        }
    };
    private int mLastPosition = -1;
    private ViewPager mViewpager;

    public CircleIndicator(Context context) {
        super(context);
        this.mInternalDataSetObserver = new DataSetObserver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onChanged() {
                int n;
                super.onChanged();
                if (CircleIndicator.this.mViewpager == null || (n = CircleIndicator.this.mViewpager.getAdapter().getCount()) == CircleIndicator.this.getChildCount()) {
                    return;
                }
                if (CircleIndicator.this.mLastPosition < n) {
                    CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                } else {
                    CircleIndicator.this.mLastPosition = -1;
                }
                CircleIndicator.this.createIndicators();
            }
        };
        this.init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInternalDataSetObserver = new /* invalid duplicate definition of identical inner class */;
        this.init(context, attributeSet);
    }

    public CircleIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mInternalDataSetObserver = new /* invalid duplicate definition of identical inner class */;
        this.init(context, attributeSet);
    }

    @TargetApi(value=21)
    public CircleIndicator(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mInternalDataSetObserver = new /* invalid duplicate definition of identical inner class */;
        this.init(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addIndicator(int n, int n2, Animator animator2) {
        if (animator2.isRunning()) {
            animator2.end();
            animator2.cancel();
        }
        View view = new View(this.getContext());
        view.setBackgroundResource(n2);
        this.addView(view, this.mIndicatorWidth, this.mIndicatorHeight);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
        if (n == 0) {
            layoutParams.leftMargin = this.mIndicatorMargin;
            layoutParams.rightMargin = this.mIndicatorMargin;
        } else {
            layoutParams.topMargin = this.mIndicatorMargin;
            layoutParams.bottomMargin = this.mIndicatorMargin;
        }
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        animator2.setTarget((Object)view);
        animator2.start();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkIndicatorConfig(Context context) {
        int n = this.mIndicatorWidth < 0 ? this.dip2px(5.0f) : this.mIndicatorWidth;
        this.mIndicatorWidth = n;
        n = this.mIndicatorHeight < 0 ? this.dip2px(5.0f) : this.mIndicatorHeight;
        this.mIndicatorHeight = n;
        n = this.mIndicatorMargin < 0 ? this.dip2px(5.0f) : this.mIndicatorMargin;
        this.mIndicatorMargin = n;
        n = this.mAnimatorResId == 0 ? R.animator.scale_with_alpha : this.mAnimatorResId;
        this.mAnimatorResId = n;
        this.mAnimatorOut = this.createAnimatorOut(context);
        this.mImmediateAnimatorOut = this.createAnimatorOut(context);
        this.mImmediateAnimatorOut.setDuration(0L);
        this.mAnimatorIn = this.createAnimatorIn(context);
        this.mImmediateAnimatorIn = this.createAnimatorIn(context);
        this.mImmediateAnimatorIn.setDuration(0L);
        n = this.mIndicatorBackgroundResId == 0 ? R.drawable.white_radius : this.mIndicatorBackgroundResId;
        this.mIndicatorBackgroundResId = n;
        n = this.mIndicatorUnselectedBackgroundResId == 0 ? this.mIndicatorBackgroundResId : this.mIndicatorUnselectedBackgroundResId;
        this.mIndicatorUnselectedBackgroundResId = n;
    }

    private Animator createAnimatorIn(Context context) {
        if (this.mAnimatorReverseResId == 0) {
            context = AnimatorInflater.loadAnimator((Context)context, (int)this.mAnimatorResId);
            context.setInterpolator((TimeInterpolator)new ReverseInterpolator());
            return context;
        }
        return AnimatorInflater.loadAnimator((Context)context, (int)this.mAnimatorReverseResId);
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator((Context)context, (int)this.mAnimatorResId);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void createIndicators() {
        this.removeAllViews();
        int n = this.mViewpager.getAdapter().getCount();
        if (n > 0) {
            int n2 = this.mViewpager.getCurrentItem();
            int n3 = this.getOrientation();
            for (int i = 0; i < n; ++i) {
                if (n2 == i) {
                    this.addIndicator(n3, this.mIndicatorBackgroundResId, this.mImmediateAnimatorOut);
                    continue;
                }
                this.addIndicator(n3, this.mIndicatorUnselectedBackgroundResId, this.mImmediateAnimatorIn);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleTypedArray(Context context, AttributeSet attributeSet) {
        int n = 1;
        if (attributeSet == null) {
            return;
        }
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CircleIndicator);
        this.mIndicatorWidth = context.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        this.mIndicatorHeight = context.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        this.mIndicatorMargin = context.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
        this.mAnimatorResId = context.getResourceId(R.styleable.CircleIndicator_ci_animator, R.animator.scale_with_alpha);
        this.mAnimatorReverseResId = context.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        this.mIndicatorBackgroundResId = context.getResourceId(R.styleable.CircleIndicator_ci_drawable, R.drawable.white_radius);
        this.mIndicatorUnselectedBackgroundResId = context.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, this.mIndicatorBackgroundResId);
        if (context.getInt(R.styleable.CircleIndicator_ci_orientation, -1) != 1) {
            n = 0;
        }
        this.setOrientation(n);
        n = context.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
        if (n < 0) {
            n = 17;
        }
        this.setGravity(n);
        context.recycle();
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.handleTypedArray(context, attributeSet);
        this.checkIndicatorConfig(context);
    }

    public int dip2px(float f) {
        return (int)(f * this.getResources().getDisplayMetrics().density + 0.5f);
    }

    public DataSetObserver getDataSetObserver() {
        return this.mInternalDataSetObserver;
    }

    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (this.mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        this.mViewpager.removeOnPageChangeListener(onPageChangeListener);
        this.mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewpager = viewPager;
        if (this.mViewpager != null && this.mViewpager.getAdapter() != null) {
            this.mLastPosition = -1;
            this.createIndicators();
            this.mViewpager.removeOnPageChangeListener(this.mInternalPageChangeListener);
            this.mViewpager.addOnPageChangeListener(this.mInternalPageChangeListener);
            this.mInternalPageChangeListener.onPageSelected(this.mViewpager.getCurrentItem());
        }
    }

    private class ReverseInterpolator
    implements Interpolator {
        private ReverseInterpolator() {
        }

        public float getInterpolation(float f) {
            return Math.abs(1.0f - f);
        }
    }

}

