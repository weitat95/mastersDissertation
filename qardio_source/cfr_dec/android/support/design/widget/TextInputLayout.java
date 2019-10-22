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
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.ViewStructure
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CheckableImageButton;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.DrawableUtils;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewGroupUtils;
import android.support.design.widget.ViewUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintTypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class TextInputLayout
extends LinearLayout {
    private ValueAnimator mAnimator;
    final CollapsingTextHelper mCollapsingTextHelper;
    boolean mCounterEnabled;
    private int mCounterMaxLength;
    private int mCounterOverflowTextAppearance;
    private boolean mCounterOverflowed;
    private int mCounterTextAppearance;
    private TextView mCounterView;
    private ColorStateList mDefaultTextColor;
    EditText mEditText;
    private CharSequence mError;
    private boolean mErrorEnabled;
    private boolean mErrorShown;
    private int mErrorTextAppearance;
    TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private boolean mHasPasswordToggleTintList;
    private boolean mHasPasswordToggleTintMode;
    private boolean mHasReconstructedEditTextBackground;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private boolean mHintEnabled;
    private boolean mHintExpanded;
    private boolean mInDrawableStateChanged;
    private LinearLayout mIndicatorArea;
    private int mIndicatorsAdded;
    private final FrameLayout mInputFrame;
    private Drawable mOriginalEditTextEndDrawable;
    private CharSequence mOriginalHint;
    private CharSequence mPasswordToggleContentDesc;
    private Drawable mPasswordToggleDrawable;
    private Drawable mPasswordToggleDummyDrawable;
    private boolean mPasswordToggleEnabled;
    private ColorStateList mPasswordToggleTintList;
    private PorterDuff.Mode mPasswordToggleTintMode;
    private CheckableImageButton mPasswordToggleView;
    private boolean mPasswordToggledVisible;
    private boolean mRestoringSavedState;
    private Paint mTmpPaint;
    private final Rect mTmpRect = new Rect();
    private Typeface mTypeface;

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context object, AttributeSet attributeSet, int n) {
        super((Context)object, attributeSet);
        this.mCollapsingTextHelper = new CollapsingTextHelper((View)this);
        ThemeUtils.checkAppCompatTheme((Context)object);
        this.setOrientation(1);
        this.setWillNotDraw(false);
        this.setAddStatesFromChildren(true);
        this.mInputFrame = new FrameLayout((Context)object);
        this.mInputFrame.setAddStatesFromChildren(true);
        this.addView((View)this.mInputFrame);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator((Interpolator)new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
        object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.TextInputLayout, n, R.style.Widget_Design_TextInputLayout);
        this.mHintEnabled = ((TintTypedArray)object).getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        this.setHint(((TintTypedArray)object).getText(R.styleable.TextInputLayout_android_hint));
        this.mHintAnimationEnabled = ((TintTypedArray)object).getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (((TintTypedArray)object).hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            attributeSet = ((TintTypedArray)object).getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = attributeSet;
            this.mDefaultTextColor = attributeSet;
        }
        if (((TintTypedArray)object).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            this.setHintTextAppearance(((TintTypedArray)object).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean bl = ((TintTypedArray)object).getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        boolean bl2 = ((TintTypedArray)object).getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        this.setCounterMaxLength(((TintTypedArray)object).getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.mCounterTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.mCounterOverflowTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.mPasswordToggleEnabled = ((TintTypedArray)object).getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.mPasswordToggleDrawable = ((TintTypedArray)object).getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
        this.mPasswordToggleContentDesc = ((TintTypedArray)object).getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (((TintTypedArray)object).hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.mHasPasswordToggleTintList = true;
            this.mPasswordToggleTintList = ((TintTypedArray)object).getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (((TintTypedArray)object).hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.mHasPasswordToggleTintMode = true;
            this.mPasswordToggleTintMode = ViewUtils.parseTintMode(((TintTypedArray)object).getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        ((TintTypedArray)object).recycle();
        this.setErrorEnabled(bl);
        this.setCounterEnabled(bl2);
        this.applyPasswordToggleTint();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setAccessibilityDelegate((View)this, new TextInputAccessibilityDelegate());
    }

    private void addIndicator(TextView textView, int n) {
        if (this.mIndicatorArea == null) {
            this.mIndicatorArea = new LinearLayout(this.getContext());
            this.mIndicatorArea.setOrientation(0);
            this.addView((View)this.mIndicatorArea, -1, -2);
            Space space = new Space(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0, 1.0f);
            this.mIndicatorArea.addView((View)space, (ViewGroup.LayoutParams)layoutParams);
            if (this.mEditText != null) {
                this.adjustIndicatorPadding();
            }
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorArea.addView((View)textView, n);
        ++this.mIndicatorsAdded;
    }

    private void adjustIndicatorPadding() {
        ViewCompat.setPaddingRelative((View)this.mIndicatorArea, ViewCompat.getPaddingStart((View)this.mEditText), 0, ViewCompat.getPaddingEnd((View)this.mEditText), this.mEditText.getPaddingBottom());
    }

    private void applyPasswordToggleTint() {
        if (this.mPasswordToggleDrawable != null && (this.mHasPasswordToggleTintList || this.mHasPasswordToggleTintMode)) {
            this.mPasswordToggleDrawable = DrawableCompat.wrap(this.mPasswordToggleDrawable).mutate();
            if (this.mHasPasswordToggleTintList) {
                DrawableCompat.setTintList(this.mPasswordToggleDrawable, this.mPasswordToggleTintList);
            }
            if (this.mHasPasswordToggleTintMode) {
                DrawableCompat.setTintMode(this.mPasswordToggleDrawable, this.mPasswordToggleTintMode);
            }
            if (this.mPasswordToggleView != null && this.mPasswordToggleView.getDrawable() != this.mPasswordToggleDrawable) {
                this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
            }
        }
    }

    private static boolean arrayContains(int[] arrn, int n) {
        boolean bl = false;
        int n2 = arrn.length;
        int n3 = 0;
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n3 >= n2) break block3;
                    if (arrn[n3] != n) break block4;
                    bl2 = true;
                }
                return bl2;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void collapseHint(boolean bl) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(1.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        }
        this.mHintExpanded = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureBackgroundDrawableStateWorkaround() {
        Drawable drawable2;
        block5: {
            block4: {
                Drawable drawable3;
                int n = Build.VERSION.SDK_INT;
                if (n != 21 && n != 22 || (drawable3 = this.mEditText.getBackground()) == null || this.mHasReconstructedEditTextBackground) break block4;
                drawable2 = drawable3.getConstantState().newDrawable();
                if (drawable3 instanceof DrawableContainer) {
                    this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer)drawable3, drawable2.getConstantState());
                }
                if (!this.mHasReconstructedEditTextBackground) break block5;
            }
            return;
        }
        ViewCompat.setBackground((View)this.mEditText, drawable2);
        this.mHasReconstructedEditTextBackground = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void expandHint(boolean bl) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(0.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        }
        this.mHintExpanded = true;
    }

    private boolean hasPasswordTransformation() {
        return this.mEditText != null && this.mEditText.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void passwordVisibilityToggleRequested(boolean bl) {
        if (this.mPasswordToggleEnabled) {
            int n = this.mEditText.getSelectionEnd();
            if (this.hasPasswordTransformation()) {
                this.mEditText.setTransformationMethod(null);
                this.mPasswordToggledVisible = true;
            } else {
                this.mEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
                this.mPasswordToggledVisible = false;
            }
            this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
            if (bl) {
                this.mPasswordToggleView.jumpDrawablesToCurrentState();
            }
            this.mEditText.setSelection(n);
        }
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean bl) {
        int n = viewGroup.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(bl);
            if (!(view instanceof ViewGroup)) continue;
            TextInputLayout.recursiveSetEnabled((ViewGroup)view, bl);
        }
    }

    private void removeIndicator(TextView textView) {
        if (this.mIndicatorArea != null) {
            int n;
            this.mIndicatorArea.removeView((View)textView);
            this.mIndicatorsAdded = n = this.mIndicatorsAdded - 1;
            if (n == 0) {
                this.mIndicatorArea.setVisibility(8);
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.mEditText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i((String)"TextInputLayout", (String)"EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.mEditText = editText;
        if (!this.hasPasswordTransformation()) {
            this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface());
        }
        this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
        int n = this.mEditText.getGravity();
        this.mCollapsingTextHelper.setCollapsedTextGravity(n & 0xFFFFFF8F | 0x30);
        this.mCollapsingTextHelper.setExpandedTextGravity(n);
        this.mEditText.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                TextInputLayout textInputLayout = TextInputLayout.this;
                boolean bl = !TextInputLayout.this.mRestoringSavedState;
                textInputLayout.updateLabelState(bl);
                if (TextInputLayout.this.mCounterEnabled) {
                    TextInputLayout.this.updateCounter(editable.length());
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        if (this.mDefaultTextColor == null) {
            this.mDefaultTextColor = this.mEditText.getHintTextColors();
        }
        if (this.mHintEnabled && TextUtils.isEmpty((CharSequence)this.mHint)) {
            this.mOriginalHint = this.mEditText.getHint();
            this.setHint(this.mOriginalHint);
            this.mEditText.setHint(null);
        }
        if (this.mCounterView != null) {
            this.updateCounter(this.mEditText.getText().length());
        }
        if (this.mIndicatorArea != null) {
            this.adjustIndicatorPadding();
        }
        this.updatePasswordToggleView();
        this.updateLabelState(false, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setError(final CharSequence charSequence, boolean bl) {
        boolean bl2 = true;
        this.mError = charSequence;
        if (!this.mErrorEnabled) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                return;
            }
            this.setErrorEnabled(true);
        }
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            bl2 = false;
        }
        this.mErrorShown = bl2;
        this.mErrorView.animate().cancel();
        if (this.mErrorShown) {
            this.mErrorView.setText(charSequence);
            this.mErrorView.setVisibility(0);
            if (bl) {
                if (this.mErrorView.getAlpha() == 1.0f) {
                    this.mErrorView.setAlpha(0.0f);
                }
                this.mErrorView.animate().alpha(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationStart(Animator animator2) {
                        TextInputLayout.this.mErrorView.setVisibility(0);
                    }
                }).start();
            } else {
                this.mErrorView.setAlpha(1.0f);
            }
        } else if (this.mErrorView.getVisibility() == 0) {
            if (bl) {
                this.mErrorView.animate().alpha(0.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationEnd(Animator animator2) {
                        TextInputLayout.this.mErrorView.setText(charSequence);
                        TextInputLayout.this.mErrorView.setVisibility(4);
                    }
                }).start();
            } else {
                this.mErrorView.setText(charSequence);
                this.mErrorView.setVisibility(4);
            }
        }
        this.updateEditTextBackground();
        this.updateLabelState(bl);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHint = charSequence;
        this.mCollapsingTextHelper.setText(charSequence);
    }

    private boolean shouldShowPasswordIcon() {
        return this.mPasswordToggleEnabled && (this.hasPasswordTransformation() || this.mPasswordToggledVisible);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateEditTextBackground() {
        Drawable drawable2;
        if (this.mEditText == null || (drawable2 = this.mEditText.getBackground()) == null) {
            return;
        }
        this.ensureBackgroundDrawableStateWorkaround();
        Drawable drawable3 = drawable2;
        if (android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        if (this.mErrorShown && this.mErrorView != null) {
            drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mErrorView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        if (this.mCounterOverflowed && this.mCounterView != null) {
            drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mCounterView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        DrawableCompat.clearColorFilter(drawable3);
        this.mEditText.refreshDrawableState();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateInputLayoutMargins() {
        int n;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.mInputFrame.getLayoutParams();
        if (this.mHintEnabled) {
            if (this.mTmpPaint == null) {
                this.mTmpPaint = new Paint();
            }
            this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
            this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
            n = (int)(-this.mTmpPaint.ascent());
        } else {
            n = 0;
        }
        if (n != layoutParams.topMargin) {
            layoutParams.topMargin = n;
            this.mInputFrame.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updatePasswordToggleView() {
        Drawable[] arrdrawable;
        block10: {
            block9: {
                if (this.mEditText == null) break block9;
                if (this.shouldShowPasswordIcon()) {
                    if (this.mPasswordToggleView == null) {
                        this.mPasswordToggleView = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_text_input_password_icon, (ViewGroup)this.mInputFrame, false);
                        this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
                        this.mPasswordToggleView.setContentDescription(this.mPasswordToggleContentDesc);
                        this.mInputFrame.addView((View)this.mPasswordToggleView);
                        this.mPasswordToggleView.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View view) {
                                TextInputLayout.this.passwordVisibilityToggleRequested(false);
                            }
                        });
                    }
                    if (this.mEditText != null && ViewCompat.getMinimumHeight((View)this.mEditText) <= 0) {
                        this.mEditText.setMinimumHeight(ViewCompat.getMinimumHeight((View)this.mPasswordToggleView));
                    }
                    this.mPasswordToggleView.setVisibility(0);
                    this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
                    if (this.mPasswordToggleDummyDrawable == null) {
                        this.mPasswordToggleDummyDrawable = new ColorDrawable();
                    }
                    this.mPasswordToggleDummyDrawable.setBounds(0, 0, this.mPasswordToggleView.getMeasuredWidth(), 1);
                    Drawable[] arrdrawable2 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText);
                    if (arrdrawable2[2] != this.mPasswordToggleDummyDrawable) {
                        this.mOriginalEditTextEndDrawable = arrdrawable2[2];
                    }
                    TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrdrawable2[0], arrdrawable2[1], this.mPasswordToggleDummyDrawable, arrdrawable2[3]);
                    this.mPasswordToggleView.setPadding(this.mEditText.getPaddingLeft(), this.mEditText.getPaddingTop(), this.mEditText.getPaddingRight(), this.mEditText.getPaddingBottom());
                    return;
                }
                if (this.mPasswordToggleView != null && this.mPasswordToggleView.getVisibility() == 0) {
                    this.mPasswordToggleView.setVisibility(8);
                }
                if (this.mPasswordToggleDummyDrawable != null && (arrdrawable = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText))[2] == this.mPasswordToggleDummyDrawable) break block10;
            }
            return;
        }
        TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrdrawable[0], arrdrawable[1], this.mOriginalEditTextEndDrawable, arrdrawable[3]);
        this.mPasswordToggleDummyDrawable = null;
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = layoutParams2.gravity & 0xFFFFFF8F | 0x10;
            this.mInputFrame.addView(view, (ViewGroup.LayoutParams)layoutParams2);
            this.mInputFrame.setLayoutParams(layoutParams);
            this.updateInputLayoutMargins();
            this.setEditText((EditText)view);
            return;
        }
        super.addView(view, n, layoutParams);
    }

    void animateToExpansionFraction(float f) {
        if (this.mCollapsingTextHelper.getExpansionFraction() == f) {
            return;
        }
        if (this.mAnimator == null) {
            this.mAnimator = new ValueAnimator();
            this.mAnimator.setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_INTERPOLATOR);
            this.mAnimator.setDuration(200L);
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(((Float)valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        this.mAnimator.setFloatValues(new float[]{this.mCollapsingTextHelper.getExpansionFraction(), f});
        this.mAnimator.start();
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n) {
        if (this.mOriginalHint == null || this.mEditText == null) {
            super.dispatchProvideAutofillStructure(viewStructure, n);
            return;
        }
        CharSequence charSequence = this.mEditText.getHint();
        this.mEditText.setHint(this.mOriginalHint);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, n);
            return;
        }
        finally {
            this.mEditText.setHint(charSequence);
        }
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.mRestoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.mRestoringSavedState = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mHintEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawableStateChanged() {
        boolean bl = true;
        if (this.mInDrawableStateChanged) {
            return;
        }
        this.mInDrawableStateChanged = true;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        boolean bl2 = false;
        if (!ViewCompat.isLaidOut((View)this) || !this.isEnabled()) {
            bl = false;
        }
        this.updateLabelState(bl);
        this.updateEditTextBackground();
        if (this.mCollapsingTextHelper != null) {
            bl2 = false | this.mCollapsingTextHelper.setState(arrn);
        }
        if (bl2) {
            this.invalidate();
        }
        this.mInDrawableStateChanged = false;
    }

    public int getCounterMaxLength() {
        return this.mCounterMaxLength;
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    public CharSequence getError() {
        if (this.mErrorEnabled) {
            return this.mError;
        }
        return null;
    }

    public CharSequence getHint() {
        if (this.mHintEnabled) {
            return this.mHint;
        }
        return null;
    }

    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.mPasswordToggleContentDesc;
    }

    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.mPasswordToggleDrawable;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mHintEnabled && this.mEditText != null) {
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect((ViewGroup)this, (View)this.mEditText, rect);
            n = rect.left + this.mEditText.getCompoundPaddingLeft();
            n3 = rect.right - this.mEditText.getCompoundPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(n, rect.top + this.mEditText.getCompoundPaddingTop(), n3, rect.bottom - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(n, this.getPaddingTop(), n3, n4 - n2 - this.getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    protected void onMeasure(int n, int n2) {
        this.updatePasswordToggleView();
        super.onMeasure(n, n2);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.setError(parcelable.error);
        if (parcelable.isPasswordToggledVisible) {
            this.passwordVisibilityToggleRequested(true);
        }
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mErrorShown) {
            savedState.error = this.getError();
        }
        savedState.isPasswordToggledVisible = this.mPasswordToggledVisible;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCounterEnabled(boolean bl) {
        if (this.mCounterEnabled != bl) {
            if (bl) {
                this.mCounterView = new AppCompatTextView(this.getContext());
                this.mCounterView.setId(R.id.textinput_counter);
                if (this.mTypeface != null) {
                    this.mCounterView.setTypeface(this.mTypeface);
                }
                this.mCounterView.setMaxLines(1);
                try {
                    TextViewCompat.setTextAppearance(this.mCounterView, this.mCounterTextAppearance);
                }
                catch (Exception exception) {
                    TextViewCompat.setTextAppearance(this.mCounterView, R.style.TextAppearance_AppCompat_Caption);
                    this.mCounterView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.error_color_material));
                }
                this.addIndicator(this.mCounterView, -1);
                if (this.mEditText == null) {
                    this.updateCounter(0);
                } else {
                    this.updateCounter(this.mEditText.getText().length());
                }
            } else {
                this.removeIndicator(this.mCounterView);
                this.mCounterView = null;
            }
            this.mCounterEnabled = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCounterMaxLength(int n) {
        if (this.mCounterMaxLength != n) {
            this.mCounterMaxLength = n > 0 ? n : -1;
            if (this.mCounterEnabled) {
                n = this.mEditText == null ? 0 : this.mEditText.getText().length();
                this.updateCounter(n);
            }
        }
    }

    public void setEnabled(boolean bl) {
        TextInputLayout.recursiveSetEnabled((ViewGroup)this, bl);
        super.setEnabled(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setError(CharSequence charSequence) {
        boolean bl = ViewCompat.isLaidOut((View)this) && this.isEnabled() && (this.mErrorView == null || !TextUtils.equals((CharSequence)this.mErrorView.getText(), (CharSequence)charSequence));
        this.setError(charSequence, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setErrorEnabled(boolean bl) {
        if (this.mErrorEnabled != bl) {
            if (this.mErrorView != null) {
                this.mErrorView.animate().cancel();
            }
            if (bl) {
                boolean bl2;
                this.mErrorView = new AppCompatTextView(this.getContext());
                this.mErrorView.setId(R.id.textinput_error);
                if (this.mTypeface != null) {
                    this.mErrorView.setTypeface(this.mTypeface);
                }
                boolean bl3 = false;
                try {
                    TextViewCompat.setTextAppearance(this.mErrorView, this.mErrorTextAppearance);
                    bl2 = bl3;
                    if (Build.VERSION.SDK_INT >= 23) {
                        int n = this.mErrorView.getTextColors().getDefaultColor();
                        bl2 = bl3;
                        if (n == -65281) {
                            bl2 = true;
                        }
                    }
                }
                catch (Exception exception) {
                    bl2 = true;
                }
                if (bl2) {
                    TextViewCompat.setTextAppearance(this.mErrorView, R.style.TextAppearance_AppCompat_Caption);
                    this.mErrorView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.error_color_material));
                }
                this.mErrorView.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion((View)this.mErrorView, 1);
                this.addIndicator(this.mErrorView, 0);
            } else {
                this.mErrorShown = false;
                this.updateEditTextBackground();
                this.removeIndicator(this.mErrorView);
                this.mErrorView = null;
            }
            this.mErrorEnabled = bl;
        }
    }

    public void setErrorTextAppearance(int n) {
        this.mErrorTextAppearance = n;
        if (this.mErrorView != null) {
            TextViewCompat.setTextAppearance(this.mErrorView, n);
        }
    }

    public void setHint(CharSequence charSequence) {
        if (this.mHintEnabled) {
            this.setHintInternal(charSequence);
            this.sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean bl) {
        this.mHintAnimationEnabled = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHintEnabled(boolean bl) {
        if (bl != this.mHintEnabled) {
            this.mHintEnabled = bl;
            CharSequence charSequence = this.mEditText.getHint();
            if (!this.mHintEnabled) {
                if (!TextUtils.isEmpty((CharSequence)this.mHint) && TextUtils.isEmpty((CharSequence)charSequence)) {
                    this.mEditText.setHint(this.mHint);
                }
                this.setHintInternal(null);
            } else if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (TextUtils.isEmpty((CharSequence)this.mHint)) {
                    this.setHint(charSequence);
                }
                this.mEditText.setHint(null);
            }
            if (this.mEditText != null) {
                this.updateInputLayoutMargins();
            }
        }
    }

    public void setHintTextAppearance(int n) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(n);
        this.mFocusedTextColor = this.mCollapsingTextHelper.getCollapsedTextColor();
        if (this.mEditText != null) {
            this.updateLabelState(false);
            this.updateInputLayoutMargins();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPasswordVisibilityToggleContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.setPasswordVisibilityToggleContentDescription(charSequence);
    }

    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.mPasswordToggleContentDesc = charSequence;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setContentDescription(charSequence);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPasswordVisibilityToggleDrawable(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setPasswordVisibilityToggleDrawable(drawable2);
    }

    public void setPasswordVisibilityToggleDrawable(Drawable drawable2) {
        this.mPasswordToggleDrawable = drawable2;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setImageDrawable(drawable2);
        }
    }

    public void setPasswordVisibilityToggleEnabled(boolean bl) {
        if (this.mPasswordToggleEnabled != bl) {
            this.mPasswordToggleEnabled = bl;
            if (!bl && this.mPasswordToggledVisible && this.mEditText != null) {
                this.mEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
            }
            this.mPasswordToggledVisible = false;
            this.updatePasswordToggleView();
        }
    }

    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.mPasswordToggleTintList = colorStateList;
        this.mHasPasswordToggleTintList = true;
        this.applyPasswordToggleTint();
    }

    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.mPasswordToggleTintMode = mode;
        this.mHasPasswordToggleTintMode = true;
        this.applyPasswordToggleTint();
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTypeface != null && !this.mTypeface.equals((Object)typeface) || this.mTypeface == null && typeface != null) {
            this.mTypeface = typeface;
            this.mCollapsingTextHelper.setTypefaces(typeface);
            if (this.mCounterView != null) {
                this.mCounterView.setTypeface(typeface);
            }
            if (this.mErrorView != null) {
                this.mErrorView.setTypeface(typeface);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateCounter(int n) {
        boolean bl = this.mCounterOverflowed;
        if (this.mCounterMaxLength == -1) {
            this.mCounterView.setText((CharSequence)String.valueOf(n));
            this.mCounterOverflowed = false;
        } else {
            boolean bl2 = n > this.mCounterMaxLength;
            this.mCounterOverflowed = bl2;
            if (bl != this.mCounterOverflowed) {
                TextView textView = this.mCounterView;
                int n2 = this.mCounterOverflowed ? this.mCounterOverflowTextAppearance : this.mCounterTextAppearance;
                TextViewCompat.setTextAppearance(textView, n2);
            }
            this.mCounterView.setText((CharSequence)this.getContext().getString(R.string.character_counter_pattern, new Object[]{n, this.mCounterMaxLength}));
        }
        if (this.mEditText != null && bl != this.mCounterOverflowed) {
            this.updateLabelState(false);
            this.updateEditTextBackground();
        }
    }

    void updateLabelState(boolean bl) {
        this.updateLabelState(bl, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateLabelState(boolean bl, boolean bl2) {
        boolean bl3 = this.isEnabled();
        boolean bl4 = this.mEditText != null && !TextUtils.isEmpty((CharSequence)this.mEditText.getText());
        boolean bl5 = TextInputLayout.arrayContains(this.getDrawableState(), 16842908);
        boolean bl6 = !TextUtils.isEmpty((CharSequence)this.getError());
        if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setExpandedTextColor(this.mDefaultTextColor);
        }
        if (bl3 && this.mCounterOverflowed && this.mCounterView != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mCounterView.getTextColors());
        } else if (bl3 && bl5 && this.mFocusedTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mFocusedTextColor);
        } else if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mDefaultTextColor);
        }
        if (bl4 || this.isEnabled() && (bl5 || bl6)) {
            if (!bl2 && !this.mHintExpanded) return;
            {
                this.collapseHint(bl);
                return;
            }
        } else {
            if (!bl2 && this.mHintExpanded) return;
            {
                this.expandHint(bl);
                return;
            }
        }
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        CharSequence error;
        boolean isPasswordToggledVisible;

        /*
         * Enabled aggressive block sorting
         */
        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            boolean bl = parcel.readInt() == 1;
            this.isPasswordToggledVisible = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            TextUtils.writeToParcel((CharSequence)this.error, (Parcel)parcel, (int)n);
            n = this.isPasswordToggledVisible ? 1 : 0;
            parcel.writeInt(n);
        }

    }

    private class TextInputAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        TextInputAccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)TextInputLayout.class.getSimpleName());
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            void var2_6;
            void var1_4;
            super.onInitializeAccessibilityNodeInfo((View)object, (AccessibilityNodeInfoCompat)var2_6);
            var2_6.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence charSequence = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                var2_6.setText(charSequence);
            }
            if (TextInputLayout.this.mEditText != null) {
                var2_6.setLabelFor((View)TextInputLayout.this.mEditText);
            }
            if (TextInputLayout.this.mErrorView != null) {
                CharSequence charSequence2 = TextInputLayout.this.mErrorView.getText();
            } else {
                Object var1_5 = null;
            }
            if (!TextUtils.isEmpty((CharSequence)var1_4)) {
                var2_6.setContentInvalid(true);
                var2_6.setError((CharSequence)var1_4);
            }
        }

        @Override
        public void onPopulateAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent((View)object, accessibilityEvent);
            object = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                accessibilityEvent.getText().add(object);
            }
        }
    }

}

