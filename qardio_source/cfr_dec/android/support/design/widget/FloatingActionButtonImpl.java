/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CircularBorderDrawable;
import android.support.design.widget.ShadowDrawableWrapper;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.StateListAnimator;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

class FloatingActionButtonImpl {
    static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] EMPTY_STATE_SET;
    static final int[] ENABLED_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] PRESSED_ENABLED_STATE_SET;
    int mAnimState = 0;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    private float mRotation;
    ShadowDrawableWrapper mShadowDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    private final StateListAnimator mStateListAnimator;
    private final Rect mTmpRect = new Rect();
    final VisibilityAwareImageButton mView;

    static {
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        ENABLED_STATE_SET = new int[]{16842910};
        EMPTY_STATE_SET = new int[0];
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        this.mView = visibilityAwareImageButton;
        this.mShadowViewDelegate = shadowViewDelegate;
        this.mStateListAnimator = new StateListAnimator();
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, this.createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(ENABLED_STATE_SET, this.createAnimator(new ResetElevationAnimation()));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, this.createAnimator(new DisabledElevationAnimation()));
        this.mRotation = this.mView.getRotation();
    }

    private ValueAnimator createAnimator(ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener((Animator.AnimatorListener)shadowAnimatorImpl);
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)shadowAnimatorImpl);
        valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        return valueAnimator;
    }

    private static ColorStateList createColorStateList(int n) {
        int[][] arrarrn = new int[3][];
        int[] arrn = new int[3];
        arrarrn[0] = FOCUSED_ENABLED_STATE_SET;
        arrn[0] = n;
        int n2 = 0 + 1;
        arrarrn[n2] = PRESSED_ENABLED_STATE_SET;
        arrn[n2] = n;
        n = n2 + 1;
        arrarrn[n] = new int[0];
        arrn[n] = 0;
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.mRotation % 90.0f != 0.0f) {
                if (this.mView.getLayerType() != 1) {
                    this.mView.setLayerType(1, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-this.mRotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-this.mRotation);
        }
    }

    CircularBorderDrawable createBorderDrawable(int n, ColorStateList colorStateList) {
        Context context = this.mView.getContext();
        CircularBorderDrawable circularBorderDrawable = this.newCircularDrawable();
        circularBorderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        circularBorderDrawable.setBorderWidth(n);
        circularBorderDrawable.setBorderTint(colorStateList);
        return circularBorderDrawable;
    }

    GradientDrawable createShapeDrawable() {
        GradientDrawable gradientDrawable = this.newGradientDrawableForShape();
        gradientDrawable.setShape(1);
        gradientDrawable.setColor(-1);
        return gradientDrawable;
    }

    final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    float getElevation() {
        return this.mElevation;
    }

    void getPadding(Rect rect) {
        this.mShadowDrawable.getPadding(rect);
    }

    /*
     * Enabled aggressive block sorting
     */
    void hide(final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl) {
        block5: {
            block4: {
                if (this.isOrWillBeHidden()) break block4;
                this.mView.animate().cancel();
                if (this.shouldAnimateVisibilityChange()) {
                    this.mAnimState = 1;
                    this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){
                        private boolean mCancelled;

                        public void onAnimationCancel(Animator animator2) {
                            this.mCancelled = true;
                        }

                        /*
                         * Enabled aggressive block sorting
                         */
                        public void onAnimationEnd(Animator object) {
                            FloatingActionButtonImpl.this.mAnimState = 0;
                            if (!this.mCancelled) {
                                object = FloatingActionButtonImpl.this.mView;
                                int n = bl ? 8 : 4;
                                ((VisibilityAwareImageButton)((Object)object)).internalSetVisibility(n, bl);
                                if (internalVisibilityChangedListener != null) {
                                    internalVisibilityChangedListener.onHidden();
                                }
                            }
                        }

                        public void onAnimationStart(Animator animator2) {
                            FloatingActionButtonImpl.this.mView.internalSetVisibility(0, bl);
                            this.mCancelled = false;
                        }
                    });
                    return;
                }
                VisibilityAwareImageButton visibilityAwareImageButton = this.mView;
                int n = bl ? 8 : 4;
                visibilityAwareImageButton.internalSetVisibility(n, bl);
                if (internalVisibilityChangedListener != null) break block5;
            }
            return;
        }
        internalVisibilityChangedListener.onHidden();
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isOrWillBeHidden() {
        if (this.mView.getVisibility() == 0) {
            if (this.mAnimState == 1) return true;
            return false;
        }
        if (this.mAnimState == 2) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isOrWillBeShown() {
        if (this.mView.getVisibility() != 0) {
            if (this.mAnimState == 2) return true;
            return false;
        }
        if (this.mAnimState == 1) return false;
        return true;
    }

    void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    void onAttachedToWindow() {
        if (this.requirePreDrawListener()) {
            this.ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    void onCompatShadowChanged() {
    }

    void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    void onDrawableStateChanged(int[] arrn) {
        this.mStateListAnimator.setState(arrn);
    }

    void onElevationsChanged(float f, float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(f, this.mPressedTranslationZ + f);
            this.updatePadding();
        }
    }

    void onPaddingUpdated(Rect rect) {
    }

    void onPreDraw() {
        float f = this.mView.getRotation();
        if (this.mRotation != f) {
            this.mRotation = f;
            this.updateFromViewRotation();
        }
    }

    boolean requirePreDrawListener() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void setBackgroundDrawable(ColorStateList arrdrawable, PorterDuff.Mode mode, int n, int n2) {
        this.mShapeDrawable = DrawableCompat.wrap((Drawable)this.createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, (ColorStateList)arrdrawable);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        this.mRippleDrawable = DrawableCompat.wrap((Drawable)this.createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, FloatingActionButtonImpl.createColorStateList(n));
        if (n2 > 0) {
            this.mBorderDrawable = this.createBorderDrawable(n2, (ColorStateList)arrdrawable);
            arrdrawable = new Drawable[]{this.mBorderDrawable, this.mShapeDrawable, this.mRippleDrawable};
        } else {
            this.mBorderDrawable = null;
            arrdrawable = new Drawable[]{this.mShapeDrawable, this.mRippleDrawable};
        }
        this.mContentBackground = new LayerDrawable(arrdrawable);
        this.mShadowDrawable = new ShadowDrawableWrapper(this.mView.getContext(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(colorStateList);
        }
    }

    void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
    }

    final void setElevation(float f) {
        if (this.mElevation != f) {
            this.mElevation = f;
            this.onElevationsChanged(f, this.mPressedTranslationZ);
        }
    }

    final void setPressedTranslationZ(float f) {
        if (this.mPressedTranslationZ != f) {
            this.mPressedTranslationZ = f;
            this.onElevationsChanged(this.mElevation, f);
        }
    }

    void setRippleColor(int n) {
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, FloatingActionButtonImpl.createColorStateList(n));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void show(final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl) {
        block6: {
            block5: {
                if (this.isOrWillBeShown()) break block5;
                this.mView.animate().cancel();
                if (this.shouldAnimateVisibilityChange()) {
                    this.mAnimState = 2;
                    if (this.mView.getVisibility() != 0) {
                        this.mView.setAlpha(0.0f);
                        this.mView.setScaleY(0.0f);
                        this.mView.setScaleX(0.0f);
                    }
                    this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                        public void onAnimationEnd(Animator animator2) {
                            FloatingActionButtonImpl.this.mAnimState = 0;
                            if (internalVisibilityChangedListener != null) {
                                internalVisibilityChangedListener.onShown();
                            }
                        }

                        public void onAnimationStart(Animator animator2) {
                            FloatingActionButtonImpl.this.mView.internalSetVisibility(0, bl);
                        }
                    });
                    return;
                }
                this.mView.internalSetVisibility(0, bl);
                this.mView.setAlpha(1.0f);
                this.mView.setScaleY(1.0f);
                this.mView.setScaleX(1.0f);
                if (internalVisibilityChangedListener != null) break block6;
            }
            return;
        }
        internalVisibilityChangedListener.onShown();
    }

    final void updatePadding() {
        Rect rect = this.mTmpRect;
        this.getPadding(rect);
        this.onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    private class DisabledElevationAnimation
    extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return 0.0f;
        }
    }

    private class ElevateToTranslationZAnimation
    extends ShadowAnimatorImpl {
        ElevateToTranslationZAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation + FloatingActionButtonImpl.this.mPressedTranslationZ;
        }
    }

    static interface InternalVisibilityChangedListener {
        public void onHidden();

        public void onShown();
    }

    private class ResetElevationAnimation
    extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation;
        }
    }

    private abstract class ShadowAnimatorImpl
    extends AnimatorListenerAdapter
    implements ValueAnimator.AnimatorUpdateListener {
        private float mShadowSizeEnd;
        private float mShadowSizeStart;
        private boolean mValidValues;

        private ShadowAnimatorImpl() {
        }

        protected abstract float getTargetShadowSize();

        public void onAnimationEnd(Animator animator2) {
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
            this.mValidValues = false;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.mValidValues) {
                this.mShadowSizeStart = FloatingActionButtonImpl.this.mShadowDrawable.getShadowSize();
                this.mShadowSizeEnd = this.getTargetShadowSize();
                this.mValidValues = true;
            }
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + (this.mShadowSizeEnd - this.mShadowSizeStart) * valueAnimator.getAnimatedFraction());
        }
    }

}

