/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewPropertyAnimator
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.ListView
 */
package com.getqardio.android.utils.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

public class BackPanelListViewHelper
implements View.OnTouchListener {
    private long mAnimationTime;
    private BackPanelCallbacks mCallbacks;
    private View mDeleteView;
    private View mDivider;
    private int mDownPosition;
    private View mDownView;
    private float mDownX;
    private float mDownY;
    private ListView mListView;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private boolean mPaused;
    private float mPreviousTranslationX;
    private int mSlop;
    private boolean mSwiping;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    private int mViewWidth = 1;
    private boolean skipNextUpEvent;

    public BackPanelListViewHelper(ListView listView) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)listView.getContext());
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mAnimationTime = listView.getContext().getResources().getInteger(17694720);
        this.mListView = listView;
    }

    public static void hideBackPanel(View view) {
        view.findViewById(2131821119).setEnabled(false);
        View view2 = view.findViewById(2131821120);
        view2.setTranslationX(0.0f);
        view2.setTag((Object)0);
        view.findViewById(2131821121).setVisibility(8);
    }

    public static void hideBackPanelWithAnimation(View view) {
        View view2 = view.findViewById(2131821119);
        if (view2 != null) {
            view2.setEnabled(false);
            view2 = view.findViewById(2131821120);
            if ((int)view2.getTranslationX() != 0) {
                final View view3 = view.findViewById(2131821121);
                int n = view.getContext().getResources().getInteger(17694720);
                view2.setTag((Object)0);
                view2.animate().translationX(0.0f).setDuration((long)n).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationEnd(Animator animator2) {
                        view3.setVisibility(8);
                    }
                });
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isLowerLayerMoved(View view) {
        return view != null && (view = view.findViewById(2131821120)) != null && (int)view.getTranslationX() != 0;
    }

    private AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener(){

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onScrollStateChanged(AbsListView object, int n) {
                boolean bl = true;
                object = BackPanelListViewHelper.this;
                if (n == 1) {
                    bl = false;
                }
                ((BackPanelListViewHelper)object).setEnabled(bl);
            }
        };
    }

    public static View wrapListViewItem(Context context, int n, int n2, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)context);
        context = layoutInflater.inflate(2130968736, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup)context.findViewById(2131821119);
        viewGroup2.setEnabled(false);
        viewGroup2.addView(layoutInflater.inflate(n2, viewGroup2, false));
        viewGroup2 = (ViewGroup)context.findViewById(2131821120);
        layoutInflater = layoutInflater.inflate(n, viewGroup2, false);
        layoutInflater.setBackground(viewGroup.getBackground().getConstantState().newDrawable());
        viewGroup2.addView((View)layoutInflater, 0);
        return context;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouch(View var1_1, MotionEvent var2_2) {
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mListView.getWidth();
        }
        switch (var2_5.getActionMasked()) {
            case 0: {
                if (this.mPaused) {
                    return false;
                }
                for (var7_6 = 0; var7_6 < this.mListView.getChildCount(); ++var7_6) {
                    BackPanelListViewHelper.hideBackPanelWithAnimation(this.mListView.getChildAt(var7_6));
                }
                var1_2 = new Rect();
                var8_8 = this.mListView.getChildCount();
                var13_10 = new int[2];
                this.mListView.getLocationOnScreen((int[])var13_10);
                var9_11 = (int)var2_5.getRawX();
                var10_12 = var13_10[0];
                var11_13 = (int)var2_5.getRawY();
                var12_14 = var13_10[1];
                var7_6 = 0;
                do {
                    if (var7_6 >= var8_8) ** GOTO lbl29
                    var13_10 = this.mListView.getChildAt(var7_6);
                    var13_10.getHitRect(var1_2);
                    if (!var1_2.contains(var9_11 - var10_12, var11_13 - var12_14)) ** GOTO lbl36
                    this.mDownView = var13_10.findViewById(2131821120);
                    this.mDeleteView = var13_10.findViewById(2131821119);
                    this.mDivider = var13_10.findViewById(2131821121);
                    if (this.mDivider != null) {
                        this.mDivider.setVisibility(0);
                    }
lbl29:
                    // 4 sources
                    if (this.mDownView == null) return false;
                    if (!this.isLowerLayerMoved(this.mDownView)) break;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mDownView.setTag((Object)0);
                    return true;
lbl36:
                    // 1 sources
                    ++var7_6;
                } while (true);
                var1_3 = this.mDownView.getTag();
                this.mPreviousTranslationX = var1_3 != null ? (float)((Integer)var1_3).intValue() : 0.0f;
                this.mDownX = var2_5.getRawX();
                this.mDownY = var2_5.getRawY();
                this.mDownPosition = this.mListView.getPositionForView(this.mDownView);
                if (this.mCallbacks.hasBackPanel(this.mDownPosition)) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                    this.mVelocityTracker.addMovement((MotionEvent)var2_5);
                    return false;
                }
                this.mDownView = null;
                return false;
            }
            case 3: {
                if (this.mVelocityTracker == null) return false;
                if (this.mDownView != null && this.mSwiping) {
                    this.mDownView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                        public void onAnimationEnd(Animator animator2) {
                            super.onAnimationEnd(animator2);
                            BackPanelListViewHelper.this.mDivider.setVisibility(8);
                        }
                    });
                    this.mDeleteView.setEnabled(false);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mDownView.setTag((Object)0);
                this.mDownView = null;
                this.mDownPosition = -1;
                this.mSwiping = false;
                ** break;
            }
            case 1: {
                if (this.isLowerLayerMoved(this.mDownView) && this.mVelocityTracker == null) {
                    return true;
                }
                if (this.skipNextUpEvent) {
                    this.skipNextUpEvent = false;
                    ** break;
                }
                if (this.mVelocityTracker == null) return false;
                var3_15 = var2_5.getRawX() - this.mDownX;
                this.mVelocityTracker.addMovement((MotionEvent)var2_5);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                var4_17 = this.mVelocityTracker.getXVelocity();
                var5_19 = Math.abs(var4_17);
                var6_21 = Math.abs(this.mVelocityTracker.getYVelocity());
                var8_9 = false;
                if (var3_15 < 0.0f && Math.abs(this.mPreviousTranslationX + var3_15) > (float)this.mDeleteView.getWidth() && this.mSwiping) {
                    var7_7 = true;
                } else {
                    var7_7 = var8_9;
                    if ((float)this.mMinFlingVelocity <= var5_19) {
                        var7_7 = var8_9;
                        if (var5_19 <= (float)this.mMaxFlingVelocity) {
                            var7_7 = var8_9;
                            if (var6_21 < var5_19) {
                                var7_7 = var8_9;
                                if (this.mSwiping) {
                                    var7_7 = var3_15 < 0.0f && (var7_7 = var4_17 < 0.0f) == (var8_9 = var3_15 < 0.0f);
                                }
                            }
                        }
                    }
                }
                if (var7_7 && this.mDownPosition != -1) {
                    this.mDownView.setTag((Object)(-this.mDeleteView.getWidth()));
                    this.mDownView.animate().translationX((float)(-this.mDeleteView.getWidth())).setDuration(this.mAnimationTime).setListener(null);
                    this.mDeleteView.setEnabled(true);
                } else {
                    this.mDownView.setTag((Object)0);
                    this.mDownView.animate().translationX(0.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                        public void onAnimationEnd(Animator animator2) {
                            super.onAnimationEnd(animator2);
                            BackPanelListViewHelper.this.mDivider.setVisibility(8);
                        }
                    });
                    this.mDeleteView.setEnabled(false);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mDownView = null;
                this.mDownPosition = -1;
                this.mSwiping = false;
            }
lbl107:
            // 4 sources
            default: {
                return false;
            }
            case 2: 
        }
        if (this.mVelocityTracker == null) return false;
        if (this.mPaused != false) return false;
        this.mVelocityTracker.addMovement((MotionEvent)var2_5);
        var3_16 = var2_5.getRawX() - this.mDownX;
        var4_18 = var2_5.getRawY();
        var5_20 = this.mDownY;
        if (Math.abs(var3_16) > (float)this.mSlop && Math.abs(var4_18 - var5_20) < Math.abs(var3_16) / 2.0f) {
            this.mSwiping = true;
            this.mListView.requestDisallowInterceptTouchEvent(true);
            var1_4 = MotionEvent.obtain((MotionEvent)var2_5);
            var1_4.setAction(var2_5.getActionIndex() << 8 | 3);
            this.mListView.onTouchEvent(var1_4);
            var1_4.recycle();
        }
        if (this.mSwiping == false) return false;
        this.mTranslationX = var3_16;
        if (this.mTranslationX + this.mPreviousTranslationX > 0.0f) {
            this.mDownView.setTranslationX(0.0f);
            return true;
        }
        this.mDownView.setTranslationX(this.mTranslationX + this.mPreviousTranslationX);
        return true;
    }

    public void setCallbacks(BackPanelCallbacks backPanelCallbacks) {
        this.mCallbacks = backPanelCallbacks;
        this.mListView.setOnTouchListener((View.OnTouchListener)this);
        this.mListView.setOnScrollListener(this.makeScrollListener());
        this.mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                BackPanelListViewHelper.this.skipNextUpEvent = true;
                if (BackPanelListViewHelper.this.isLowerLayerMoved(view)) {
                    BackPanelListViewHelper.hideBackPanelWithAnimation(view);
                    return true;
                }
                BackPanelListViewHelper.this.showBackPanelWithAnimation(view);
                return true;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setEnabled(boolean bl) {
        bl = !bl;
        this.mPaused = bl;
    }

    public void showBackPanelWithAnimation(View view) {
        View view2 = view.findViewById(2131821119);
        if (view2 != null) {
            view2.setEnabled(true);
            view = view.findViewById(2131821120);
            if ((int)view.getTranslationX() == 0) {
                int n = -this.mDeleteView.getWidth();
                view.setTag((Object)n);
                view.animate().translationX((float)n).setDuration(this.mAnimationTime);
            }
        }
    }

    public static interface BackPanelCallbacks {
        public boolean hasBackPanel(int var1);
    }

}

