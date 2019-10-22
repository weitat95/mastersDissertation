/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.AnimatorSet$Builder
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.ContextThemeWrapper
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 *  android.widget.TextView
 */
package com.getbase.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.R;
import com.getbase.floatingactionbutton.TouchDelegateGroup;

public class FloatingActionsMenu
extends ViewGroup {
    private static Interpolator sAlphaExpandInterpolator;
    private static Interpolator sCollapseInterpolator;
    private static Interpolator sExpandInterpolator;
    private AddFloatingActionButton mAddButton;
    private int mAddButtonColorNormal;
    private int mAddButtonColorPressed;
    private int mAddButtonPlusColor;
    private int mAddButtonSize;
    private boolean mAddButtonStrokeVisible;
    private int mButtonSpacing;
    private int mButtonsCount;
    private AnimatorSet mCollapseAnimation;
    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(300L);
    private int mExpandDirection;
    private boolean mExpanded;
    private int mLabelsMargin;
    private int mLabelsPosition;
    private int mLabelsStyle;
    private int mLabelsVerticalOffset;
    private OnFloatingActionsMenuUpdateListener mListener;
    private int mMaxButtonHeight;
    private int mMaxButtonWidth;
    private RotatingDrawable mRotatingDrawable;
    private TouchDelegateGroup mTouchDelegateGroup;

    static {
        sExpandInterpolator = new OvershootInterpolator();
        sCollapseInterpolator = new DecelerateInterpolator(3.0f);
        sAlphaExpandInterpolator = new DecelerateInterpolator();
    }

    public FloatingActionsMenu(Context context) {
        this(context, null);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        this.init(context, attributeSet);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        this.init(context, attributeSet);
    }

    private int adjustForOvershoot(int n) {
        return n * 12 / 10;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void collapse(boolean bl) {
        if (this.mExpanded) {
            this.mExpanded = false;
            this.mTouchDelegateGroup.setEnabled(false);
            AnimatorSet animatorSet = this.mCollapseAnimation;
            long l = bl ? 0L : 300L;
            animatorSet.setDuration(l);
            this.mCollapseAnimation.start();
            this.mExpandAnimation.cancel();
            if (this.mListener != null) {
                this.mListener.onMenuCollapsed();
            }
        }
    }

    private void createAddButton(Context context) {
        this.mAddButton = new AddFloatingActionButton(context){

            @Override
            Drawable getIconDrawable() {
                RotatingDrawable rotatingDrawable = new RotatingDrawable(super.getIconDrawable());
                FloatingActionsMenu.this.mRotatingDrawable = rotatingDrawable;
                OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)((Object)rotatingDrawable), (String)"rotation", (float[])new float[]{135.0f, 0.0f});
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat((Object)((Object)rotatingDrawable), (String)"rotation", (float[])new float[]{0.0f, 135.0f});
                objectAnimator.setInterpolator((TimeInterpolator)overshootInterpolator);
                objectAnimator2.setInterpolator((TimeInterpolator)overshootInterpolator);
                FloatingActionsMenu.this.mExpandAnimation.play((Animator)objectAnimator2);
                FloatingActionsMenu.this.mCollapseAnimation.play((Animator)objectAnimator);
                return rotatingDrawable;
            }

            @Override
            void updateBackground() {
                this.mPlusColor = FloatingActionsMenu.this.mAddButtonPlusColor;
                this.mColorNormal = FloatingActionsMenu.this.mAddButtonColorNormal;
                this.mColorPressed = FloatingActionsMenu.this.mAddButtonColorPressed;
                this.mStrokeVisible = FloatingActionsMenu.this.mAddButtonStrokeVisible;
                super.updateBackground();
            }
        };
        this.mAddButton.setId(R.id.fab_expand_menu_button);
        this.mAddButton.setSize(this.mAddButtonSize);
        this.mAddButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FloatingActionsMenu.this.toggle();
            }
        });
        this.addView((View)this.mAddButton, super.generateDefaultLayoutParams());
        ++this.mButtonsCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void createLabels() {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.getContext(), this.mLabelsStyle);
        int n = 0;
        while (n < this.mButtonsCount) {
            FloatingActionButton floatingActionButton = (FloatingActionButton)this.getChildAt(n);
            String string2 = floatingActionButton.getTitle();
            if (floatingActionButton != this.mAddButton && string2 != null && floatingActionButton.getTag(R.id.fab_label) == null) {
                string2 = new TextView((Context)contextThemeWrapper);
                string2.setTextAppearance(this.getContext(), this.mLabelsStyle);
                string2.setText((CharSequence)floatingActionButton.getTitle());
                this.addView((View)string2);
                floatingActionButton.setTag(R.id.fab_label, (Object)string2);
            }
            ++n;
        }
        return;
    }

    private boolean expandsHorizontally() {
        return this.mExpandDirection == 2 || this.mExpandDirection == 3;
    }

    private int getColor(int n) {
        return this.getResources().getColor(n);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mButtonSpacing = (int)(this.getResources().getDimension(R.dimen.fab_actions_spacing) - this.getResources().getDimension(R.dimen.fab_shadow_radius) - this.getResources().getDimension(R.dimen.fab_shadow_offset));
        this.mLabelsMargin = this.getResources().getDimensionPixelSize(R.dimen.fab_labels_margin);
        this.mLabelsVerticalOffset = this.getResources().getDimensionPixelSize(R.dimen.fab_shadow_offset);
        this.mTouchDelegateGroup = new TouchDelegateGroup((View)this);
        this.setTouchDelegate((TouchDelegate)this.mTouchDelegateGroup);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionsMenu, 0, 0);
        this.mAddButtonPlusColor = attributeSet.getColor(R.styleable.FloatingActionsMenu_fab_addButtonPlusIconColor, this.getColor(17170443));
        this.mAddButtonColorNormal = attributeSet.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorNormal, this.getColor(17170451));
        this.mAddButtonColorPressed = attributeSet.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorPressed, this.getColor(17170450));
        this.mAddButtonSize = attributeSet.getInt(R.styleable.FloatingActionsMenu_fab_addButtonSize, 0);
        this.mAddButtonStrokeVisible = attributeSet.getBoolean(R.styleable.FloatingActionsMenu_fab_addButtonStrokeVisible, true);
        this.mExpandDirection = attributeSet.getInt(R.styleable.FloatingActionsMenu_fab_expandDirection, 0);
        this.mLabelsStyle = attributeSet.getResourceId(R.styleable.FloatingActionsMenu_fab_labelStyle, 0);
        this.mLabelsPosition = attributeSet.getInt(R.styleable.FloatingActionsMenu_fab_labelsPosition, 0);
        attributeSet.recycle();
        if (this.mLabelsStyle != 0 && this.expandsHorizontally()) {
            throw new IllegalStateException("Action labels in horizontal expand orientation is not supported.");
        }
        this.createAddButton(context);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams);
    }

    public void collapse() {
        this.collapse(false);
    }

    public void expand() {
        if (!this.mExpanded) {
            this.mExpanded = true;
            this.mTouchDelegateGroup.setEnabled(true);
            this.mCollapseAnimation.cancel();
            this.mExpandAnimation.start();
            if (this.mListener != null) {
                this.mListener.onMenuExpanded();
            }
        }
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(super.generateLayoutParams(attributeSet));
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(super.generateLayoutParams(layoutParams));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.bringChildToFront((View)this.mAddButton);
        this.mButtonsCount = this.getChildCount();
        if (this.mLabelsStyle != 0) {
            this.createLabels();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        switch (this.mExpandDirection) {
            case 0: 
            case 1: {
                boolean bl2 = this.mExpandDirection == 0;
                if (bl) {
                    this.mTouchDelegateGroup.clearTouchDelegates();
                }
                n4 = bl2 ? n4 - n2 - this.mAddButton.getMeasuredHeight() : 0;
                n3 = this.mLabelsPosition == 0 ? n3 - n - this.mMaxButtonWidth / 2 : this.mMaxButtonWidth / 2;
                n = n3 - this.mAddButton.getMeasuredWidth() / 2;
                this.mAddButton.layout(n, n4, this.mAddButton.getMeasuredWidth() + n, this.mAddButton.getMeasuredHeight() + n4);
                n = this.mMaxButtonWidth / 2 + this.mLabelsMargin;
                n = this.mLabelsPosition == 0 ? n3 - n : n3 + n;
                n2 = bl2 ? n4 - this.mButtonSpacing : this.mAddButton.getMeasuredHeight() + n4 + this.mButtonSpacing;
                for (int i = this.mButtonsCount - 1; i >= 0; --i) {
                    View view = this.getChildAt(i);
                    int n5 = n2;
                    if (view != this.mAddButton) {
                        if (view.getVisibility() == 8) {
                            n5 = n2;
                        } else {
                            int n6 = n3 - view.getMeasuredWidth() / 2;
                            n5 = bl2 ? n2 - view.getMeasuredHeight() : n2;
                            view.layout(n6, n5, view.getMeasuredWidth() + n6, view.getMeasuredHeight() + n5);
                            float f = n4 - n5;
                            float f2 = this.mExpanded ? 0.0f : f;
                            view.setTranslationY(f2);
                            f2 = this.mExpanded ? 1.0f : 0.0f;
                            view.setAlpha(f2);
                            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                            layoutParams.mCollapseDir.setFloatValues(new float[]{0.0f, f});
                            layoutParams.mExpandDir.setFloatValues(new float[]{f, 0.0f});
                            layoutParams.setAnimationsTarget(view);
                            layoutParams = (View)view.getTag(R.id.fab_label);
                            if (layoutParams != null) {
                                n2 = this.mLabelsPosition == 0 ? n - layoutParams.getMeasuredWidth() : n + layoutParams.getMeasuredWidth();
                                int n7 = this.mLabelsPosition == 0 ? n2 : n;
                                if (this.mLabelsPosition == 0) {
                                    n2 = n;
                                }
                                int n8 = n5 - this.mLabelsVerticalOffset + (view.getMeasuredHeight() - layoutParams.getMeasuredHeight()) / 2;
                                layoutParams.layout(n7, n8, n2, layoutParams.getMeasuredHeight() + n8);
                                Object object = new Rect(Math.min(n6, n7), n5 - this.mButtonSpacing / 2, Math.max(view.getMeasuredWidth() + n6, n2), view.getMeasuredHeight() + n5 + this.mButtonSpacing / 2);
                                this.mTouchDelegateGroup.addTouchDelegate(new TouchDelegate((Rect)object, view));
                                f2 = this.mExpanded ? 0.0f : f;
                                layoutParams.setTranslationY(f2);
                                f2 = this.mExpanded ? 1.0f : 0.0f;
                                layoutParams.setAlpha(f2);
                                object = (LayoutParams)layoutParams.getLayoutParams();
                                ((LayoutParams)((Object)object)).mCollapseDir.setFloatValues(new float[]{0.0f, f});
                                ((LayoutParams)((Object)object)).mExpandDir.setFloatValues(new float[]{f, 0.0f});
                                ((LayoutParams)((Object)object)).setAnimationsTarget((View)layoutParams);
                            }
                            n2 = bl2 ? n5 - this.mButtonSpacing : view.getMeasuredHeight() + n5 + this.mButtonSpacing;
                            n5 = n2;
                        }
                    }
                    n2 = n5;
                }
                return;
            }
            case 2: 
            case 3: {
                boolean bl3 = this.mExpandDirection == 2;
                n3 = bl3 ? n3 - n - this.mAddButton.getMeasuredWidth() : 0;
                int n9 = n4 - n2 - this.mMaxButtonHeight + (this.mMaxButtonHeight - this.mAddButton.getMeasuredHeight()) / 2;
                this.mAddButton.layout(n3, n9, this.mAddButton.getMeasuredWidth() + n3, this.mAddButton.getMeasuredHeight() + n9);
                n = bl3 ? n3 - this.mButtonSpacing : this.mAddButton.getMeasuredWidth() + n3 + this.mButtonSpacing;
                for (n2 = this.mButtonsCount - 1; n2 >= 0; --n2) {
                    View view = this.getChildAt(n2);
                    n4 = n;
                    if (view != this.mAddButton) {
                        if (view.getVisibility() == 8) {
                            n4 = n;
                        } else {
                            if (bl3) {
                                n -= view.getMeasuredWidth();
                            }
                            n4 = n9 + (this.mAddButton.getMeasuredHeight() - view.getMeasuredHeight()) / 2;
                            view.layout(n, n4, view.getMeasuredWidth() + n, view.getMeasuredHeight() + n4);
                            float f = n3 - n;
                            float f3 = this.mExpanded ? 0.0f : f;
                            view.setTranslationX(f3);
                            f3 = this.mExpanded ? 1.0f : 0.0f;
                            view.setAlpha(f3);
                            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                            layoutParams.mCollapseDir.setFloatValues(new float[]{0.0f, f});
                            layoutParams.mExpandDir.setFloatValues(new float[]{f, 0.0f});
                            layoutParams.setAnimationsTarget(view);
                            n = bl3 ? (n -= this.mButtonSpacing) : view.getMeasuredWidth() + n + this.mButtonSpacing;
                            n4 = n;
                        }
                    }
                    n = n4;
                }
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4 = 0;
        this.measureChildren(n, n2);
        n2 = 0;
        n = 0;
        this.mMaxButtonWidth = 0;
        this.mMaxButtonHeight = 0;
        int n5 = 0;
        for (n3 = 0; n3 < this.mButtonsCount; ++n3) {
            int n6;
            int n7;
            int n8;
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 8) {
                n6 = n2;
                n8 = n5;
                n7 = n;
            } else {
                switch (this.mExpandDirection) {
                    case 0: 
                    case 1: {
                        this.mMaxButtonWidth = Math.max(this.mMaxButtonWidth, view.getMeasuredWidth());
                        n += view.getMeasuredHeight();
                    }
                    default: {
                        break;
                    }
                    case 2: 
                    case 3: {
                        n2 += view.getMeasuredWidth();
                        this.mMaxButtonHeight = Math.max(this.mMaxButtonHeight, view.getMeasuredHeight());
                    }
                }
                n7 = n;
                n8 = n5;
                n6 = n2;
                if (!this.expandsHorizontally()) {
                    view = (TextView)view.getTag(R.id.fab_label);
                    n7 = n;
                    n8 = n5;
                    n6 = n2;
                    if (view != null) {
                        n8 = Math.max(n5, view.getMeasuredWidth());
                        n7 = n;
                        n6 = n2;
                    }
                }
            }
            n = n7;
            n5 = n8;
            n2 = n6;
        }
        if (!this.expandsHorizontally()) {
            n3 = this.mMaxButtonWidth;
            n2 = n4;
            if (n5 > 0) {
                n2 = this.mLabelsMargin + n5;
            }
            n2 = n3 + n2;
        } else {
            n = this.mMaxButtonHeight;
        }
        switch (this.mExpandDirection) {
            case 0: 
            case 1: {
                n = this.adjustForOvershoot(n + this.mButtonSpacing * (this.mButtonsCount - 1));
            }
            default: {
                break;
            }
            case 2: 
            case 3: {
                n2 = this.adjustForOvershoot(n2 + this.mButtonSpacing * (this.mButtonsCount - 1));
            }
        }
        this.setMeasuredDimension(n2, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        SavedState savedState = (SavedState)((Object)object);
        this.mExpanded = savedState.mExpanded;
        this.mTouchDelegateGroup.setEnabled(this.mExpanded);
        if (this.mRotatingDrawable != null) {
            RotatingDrawable rotatingDrawable = this.mRotatingDrawable;
            float f = this.mExpanded ? 135.0f : 0.0f;
            rotatingDrawable.setRotation(f);
        }
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mExpanded = this.mExpanded;
        return savedState;
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.mAddButton.setEnabled(bl);
    }

    public void setOnFloatingActionsMenuUpdateListener(OnFloatingActionsMenuUpdateListener onFloatingActionsMenuUpdateListener) {
        this.mListener = onFloatingActionsMenuUpdateListener;
    }

    public void toggle() {
        if (this.mExpanded) {
            this.collapse();
            return;
        }
        this.expand();
    }

    private class LayoutParams
    extends ViewGroup.LayoutParams {
        private boolean animationsSetToPlay;
        private ObjectAnimator mCollapseAlpha;
        private ObjectAnimator mCollapseDir;
        private ObjectAnimator mExpandAlpha;
        private ObjectAnimator mExpandDir;

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mExpandDir = new ObjectAnimator();
            this.mExpandAlpha = new ObjectAnimator();
            this.mCollapseDir = new ObjectAnimator();
            this.mCollapseAlpha = new ObjectAnimator();
            this.mExpandDir.setInterpolator((TimeInterpolator)sExpandInterpolator);
            this.mExpandAlpha.setInterpolator((TimeInterpolator)sAlphaExpandInterpolator);
            this.mCollapseDir.setInterpolator((TimeInterpolator)sCollapseInterpolator);
            this.mCollapseAlpha.setInterpolator((TimeInterpolator)sCollapseInterpolator);
            this.mCollapseAlpha.setProperty(View.ALPHA);
            this.mCollapseAlpha.setFloatValues(new float[]{1.0f, 0.0f});
            this.mExpandAlpha.setProperty(View.ALPHA);
            this.mExpandAlpha.setFloatValues(new float[]{0.0f, 1.0f});
            switch (FloatingActionsMenu.this.mExpandDirection) {
                default: {
                    return;
                }
                case 0: 
                case 1: {
                    this.mCollapseDir.setProperty(View.TRANSLATION_Y);
                    this.mExpandDir.setProperty(View.TRANSLATION_Y);
                    return;
                }
                case 2: 
                case 3: 
            }
            this.mCollapseDir.setProperty(View.TRANSLATION_X);
            this.mExpandDir.setProperty(View.TRANSLATION_X);
        }

        private void addLayerTypeListener(Animator animator2, final View view) {
            animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    view.setLayerType(0, null);
                }

                public void onAnimationStart(Animator animator2) {
                    view.setLayerType(2, null);
                }
            });
        }

        public void setAnimationsTarget(View view) {
            this.mCollapseAlpha.setTarget((Object)view);
            this.mCollapseDir.setTarget((Object)view);
            this.mExpandAlpha.setTarget((Object)view);
            this.mExpandDir.setTarget((Object)view);
            if (!this.animationsSetToPlay) {
                this.addLayerTypeListener((Animator)this.mExpandDir, view);
                this.addLayerTypeListener((Animator)this.mCollapseDir, view);
                FloatingActionsMenu.this.mCollapseAnimation.play((Animator)this.mCollapseAlpha);
                FloatingActionsMenu.this.mCollapseAnimation.play((Animator)this.mCollapseDir);
                FloatingActionsMenu.this.mExpandAnimation.play((Animator)this.mExpandAlpha);
                FloatingActionsMenu.this.mExpandAnimation.play((Animator)this.mExpandDir);
                this.animationsSetToPlay = true;
            }
        }

    }

    public static interface OnFloatingActionsMenuUpdateListener {
        public void onMenuCollapsed();

        public void onMenuExpanded();
    }

    private static class RotatingDrawable
    extends LayerDrawable {
        private float mRotation;

        public RotatingDrawable(Drawable drawable2) {
            super(new Drawable[]{drawable2});
        }

        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(this.mRotation, (float)this.getBounds().centerX(), (float)this.getBounds().centerY());
            super.draw(canvas);
            canvas.restore();
        }

        public float getRotation() {
            return this.mRotation;
        }

        public void setRotation(float f) {
            this.mRotation = f;
            this.invalidateSelf();
        }
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public boolean mExpanded;

        /*
         * Enabled aggressive block sorting
         */
        private SavedState(Parcel parcel) {
            boolean bl = true;
            super(parcel);
            if (parcel.readInt() != 1) {
                bl = false;
            }
            this.mExpanded = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            n = this.mExpanded ? 1 : 0;
            parcel.writeInt(n);
        }

    }

}

