/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelperV17;
import android.support.v7.widget.AppCompatTextViewAutoSizeHelper;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.TintTypedArray;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelper {
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mStyle = 0;
    final TextView mView;

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    static AppCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager object, int n) {
        if ((context = ((AppCompatDrawableManager)object).getTintList(context, n)) != null) {
            object = new TintInfo();
            ((TintInfo)object).mHasTintList = true;
            ((TintInfo)object).mTintList = context;
            return object;
        }
        return null;
    }

    private void setTextSizeInternal(int n, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(n, f);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int n = tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) ? R.styleable.TextAppearance_android_fontFamily : R.styleable.TextAppearance_fontFamily;
            if (!context.isRestricted()) {
                try {
                    this.mFontTypeface = tintTypedArray.getFont(n, this.mStyle, this.mView);
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                }
                catch (Resources.NotFoundException notFoundException) {}
            }
            if (this.mFontTypeface == null) {
                this.mFontTypeface = Typeface.create((String)tintTypedArray.getString(n), (int)this.mStyle);
            }
        }
    }

    final void applyCompoundDrawableTint(Drawable drawable2, TintInfo tintInfo) {
        if (drawable2 != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable2, tintInfo, this.mView.getDrawableState());
        }
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] arrdrawable = this.mView.getCompoundDrawables();
            this.applyCompoundDrawableTint(arrdrawable[0], this.mDrawableLeftTint);
            this.applyCompoundDrawableTint(arrdrawable[1], this.mDrawableTopTint);
            this.applyCompoundDrawableTint(arrdrawable[2], this.mDrawableRightTint);
            this.applyCompoundDrawableTint(arrdrawable[3], this.mDrawableBottomTint);
        }
    }

    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    void loadFromAttributes(AttributeSet arrn, int n) {
        block26: {
            block25: {
                Context context = this.mView.getContext();
                Object object = AppCompatDrawableManager.get();
                Object object2 = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)arrn, R.styleable.AppCompatTextHelper, n, 0);
                int n2 = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
                if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
                    this.mDrawableLeftTint = AppCompatTextHelper.createTintInfo(context, (AppCompatDrawableManager)object, ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
                }
                if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
                    this.mDrawableTopTint = AppCompatTextHelper.createTintInfo(context, (AppCompatDrawableManager)object, ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
                }
                if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
                    this.mDrawableRightTint = AppCompatTextHelper.createTintInfo(context, (AppCompatDrawableManager)object, ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
                }
                if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
                    this.mDrawableBottomTint = AppCompatTextHelper.createTintInfo(context, (AppCompatDrawableManager)object, ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
                }
                ((TintTypedArray)object2).recycle();
                boolean bl = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                Object var16_12 = null;
                object2 = null;
                TintTypedArray tintTypedArray = null;
                Object object3 = null;
                object = null;
                Object object4 = null;
                ColorStateList colorStateList = null;
                ColorStateList colorStateList2 = null;
                if (n2 != -1) {
                    TintTypedArray tintTypedArray2 = TintTypedArray.obtainStyledAttributes(context, n2, R.styleable.TextAppearance);
                    bl2 = bl3;
                    bl4 = bl5;
                    if (!bl) {
                        bl2 = bl3;
                        bl4 = bl5;
                        if (tintTypedArray2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                            bl4 = true;
                            bl2 = tintTypedArray2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                        }
                    }
                    this.updateTypefaceAndStyle(context, tintTypedArray2);
                    object2 = var16_12;
                    colorStateList = colorStateList2;
                    if (Build.VERSION.SDK_INT < 23) {
                        object = tintTypedArray;
                        if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColor)) {
                            object = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColor);
                        }
                        if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                            object4 = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
                        }
                        object2 = object;
                        object3 = object4;
                        colorStateList = colorStateList2;
                        if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                            colorStateList = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                            object3 = object4;
                            object2 = object;
                        }
                    }
                    tintTypedArray2.recycle();
                    object = object3;
                }
                tintTypedArray = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)arrn, R.styleable.TextAppearance, n, 0);
                bl3 = bl2;
                bl5 = bl4;
                if (!bl) {
                    bl3 = bl2;
                    bl5 = bl4;
                    if (tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                        bl5 = true;
                        bl3 = tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                    }
                }
                object3 = object2;
                object4 = object;
                colorStateList2 = colorStateList;
                if (Build.VERSION.SDK_INT < 23) {
                    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
                        object2 = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
                    }
                    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                        object = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
                    }
                    object3 = object2;
                    object4 = object;
                    colorStateList2 = colorStateList;
                    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                        colorStateList2 = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                        object4 = object;
                        object3 = object2;
                    }
                }
                this.updateTypefaceAndStyle(context, tintTypedArray);
                tintTypedArray.recycle();
                if (object3 != null) {
                    this.mView.setTextColor((ColorStateList)object3);
                }
                if (object4 != null) {
                    this.mView.setHintTextColor((ColorStateList)object4);
                }
                if (colorStateList2 != null) {
                    this.mView.setLinkTextColor(colorStateList2);
                }
                if (!bl && bl5) {
                    this.setAllCaps(bl3);
                }
                if (this.mFontTypeface != null) {
                    this.mView.setTypeface(this.mFontTypeface, this.mStyle);
                }
                this.mAutoSizeTextHelper.loadFromAttributes((AttributeSet)arrn, n);
                if (Build.VERSION.SDK_INT < 26 || this.mAutoSizeTextHelper.getAutoSizeTextType() == 0 || (arrn = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes()).length <= 0) break block25;
                if ((float)this.mView.getAutoSizeStepGranularity() == -1.0f) break block26;
                this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
            }
            return;
        }
        this.mView.setAutoSizeTextTypeUniformWithPresetSizes(arrn, 0);
    }

    void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT < 26) {
            this.autoSizeText();
        }
    }

    void onSetTextAppearance(Context context, int n) {
        ColorStateList colorStateList;
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, n, R.styleable.TextAppearance);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            this.setAllCaps(tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(colorStateList);
        }
        this.updateTypefaceAndStyle(context, tintTypedArray);
        tintTypedArray.recycle();
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
    }

    void setAllCaps(boolean bl) {
        this.mView.setAllCaps(bl);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(int[] arrn, int n) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
    }

    void setAutoSizeTextTypeWithDefaults(int n) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(n);
    }

    void setTextSize(int n, float f) {
        if (Build.VERSION.SDK_INT < 26 && !this.isAutoSizeEnabled()) {
            this.setTextSizeInternal(n, f);
        }
    }
}

