/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.AnimationUtils;
import android.support.v4.math.MathUtils;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

final class CollapsingTextHelper {
    private static final Paint DEBUG_DRAW_PAINT;
    private static final boolean USE_SCALING_TEXTURE;
    private boolean mBoundsChanged;
    private final Rect mCollapsedBounds;
    private float mCollapsedDrawX;
    private float mCollapsedDrawY;
    private int mCollapsedShadowColor;
    private float mCollapsedShadowDx;
    private float mCollapsedShadowDy;
    private float mCollapsedShadowRadius;
    private ColorStateList mCollapsedTextColor;
    private int mCollapsedTextGravity = 16;
    private float mCollapsedTextSize = 15.0f;
    private Typeface mCollapsedTypeface;
    private final RectF mCurrentBounds;
    private float mCurrentDrawX;
    private float mCurrentDrawY;
    private float mCurrentTextSize;
    private Typeface mCurrentTypeface;
    private boolean mDrawTitle;
    private final Rect mExpandedBounds;
    private float mExpandedDrawX;
    private float mExpandedDrawY;
    private float mExpandedFraction;
    private int mExpandedShadowColor;
    private float mExpandedShadowDx;
    private float mExpandedShadowDy;
    private float mExpandedShadowRadius;
    private ColorStateList mExpandedTextColor;
    private int mExpandedTextGravity = 16;
    private float mExpandedTextSize = 15.0f;
    private Bitmap mExpandedTitleTexture;
    private Typeface mExpandedTypeface;
    private boolean mIsRtl;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private int[] mState;
    private CharSequence mText;
    private final TextPaint mTextPaint;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = Build.VERSION.SDK_INT < 18;
        USE_SCALING_TEXTURE = bl;
        DEBUG_DRAW_PAINT = null;
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.mView = view;
        this.mTextPaint = new TextPaint(129);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
        this.mCurrentBounds = new RectF();
    }

    private boolean areTypefacesDifferent(Typeface typeface, Typeface typeface2) {
        return typeface != null && !typeface.equals((Object)typeface2) || typeface == null && typeface2 != null;
    }

    private static int blendColors(int n, int n2, float f) {
        float f2 = 1.0f - f;
        float f3 = Color.alpha((int)n);
        float f4 = Color.alpha((int)n2);
        float f5 = Color.red((int)n);
        float f6 = Color.red((int)n2);
        float f7 = Color.green((int)n);
        float f8 = Color.green((int)n2);
        float f9 = Color.blue((int)n);
        float f10 = Color.blue((int)n2);
        return Color.argb((int)((int)(f3 * f2 + f4 * f)), (int)((int)(f5 * f2 + f6 * f)), (int)((int)(f7 * f2 + f8 * f)), (int)((int)(f9 * f2 + f10 * f)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateBaseOffsets() {
        float f;
        float f2;
        int n = 1;
        float f3 = this.mCurrentTextSize;
        this.calculateUsingTextSize(this.mCollapsedTextSize);
        float f4 = this.mTextToDraw != null ? this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()) : 0.0f;
        int n2 = this.mCollapsedTextGravity;
        int n3 = this.mIsRtl ? 1 : 0;
        n3 = GravityCompat.getAbsoluteGravity(n2, n3);
        switch (n3 & 0x70) {
            default: {
                f = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f;
                f2 = this.mTextPaint.descent();
                this.mCollapsedDrawY = (float)this.mCollapsedBounds.centerY() + (f - f2);
                break;
            }
            case 80: {
                this.mCollapsedDrawY = this.mCollapsedBounds.bottom;
                break;
            }
            case 48: {
                this.mCollapsedDrawY = (float)this.mCollapsedBounds.top - this.mTextPaint.ascent();
                break;
            }
        }
        switch (n3 & 0x800007) {
            default: {
                this.mCollapsedDrawX = this.mCollapsedBounds.left;
                break;
            }
            case 1: {
                this.mCollapsedDrawX = (float)this.mCollapsedBounds.centerX() - f4 / 2.0f;
                break;
            }
            case 5: {
                this.mCollapsedDrawX = (float)this.mCollapsedBounds.right - f4;
            }
        }
        this.calculateUsingTextSize(this.mExpandedTextSize);
        f4 = this.mTextToDraw != null ? this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()) : 0.0f;
        n2 = this.mExpandedTextGravity;
        n3 = this.mIsRtl ? n : 0;
        n3 = GravityCompat.getAbsoluteGravity(n2, n3);
        switch (n3 & 0x70) {
            default: {
                f = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f;
                f2 = this.mTextPaint.descent();
                this.mExpandedDrawY = (float)this.mExpandedBounds.centerY() + (f - f2);
                break;
            }
            case 80: {
                this.mExpandedDrawY = this.mExpandedBounds.bottom;
                break;
            }
            case 48: {
                this.mExpandedDrawY = (float)this.mExpandedBounds.top - this.mTextPaint.ascent();
            }
        }
        switch (n3 & 0x800007) {
            default: {
                this.mExpandedDrawX = this.mExpandedBounds.left;
                break;
            }
            case 1: {
                this.mExpandedDrawX = (float)this.mExpandedBounds.centerX() - f4 / 2.0f;
                break;
            }
            case 5: {
                this.mExpandedDrawX = (float)this.mExpandedBounds.right - f4;
            }
        }
        this.clearTexture();
        this.setInterpolatedTextSize(f3);
    }

    private void calculateCurrentOffsets() {
        this.calculateOffsets(this.mExpandedFraction);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean calculateIsRtl(CharSequence charSequence) {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat;
        boolean bl = true;
        if (ViewCompat.getLayoutDirection(this.mView) != 1) {
            bl = false;
        }
        if (bl) {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
            return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        }
        textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateOffsets(float f) {
        this.interpolateBounds(f);
        this.mCurrentDrawX = CollapsingTextHelper.lerp(this.mExpandedDrawX, this.mCollapsedDrawX, f, this.mPositionInterpolator);
        this.mCurrentDrawY = CollapsingTextHelper.lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f, this.mPositionInterpolator);
        this.setInterpolatedTextSize(CollapsingTextHelper.lerp(this.mExpandedTextSize, this.mCollapsedTextSize, f, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            this.mTextPaint.setColor(CollapsingTextHelper.blendColors(this.getCurrentExpandedTextColor(), this.getCurrentCollapsedTextColor(), f));
        } else {
            this.mTextPaint.setColor(this.getCurrentCollapsedTextColor());
        }
        this.mTextPaint.setShadowLayer(CollapsingTextHelper.lerp(this.mExpandedShadowRadius, this.mCollapsedShadowRadius, f, null), CollapsingTextHelper.lerp(this.mExpandedShadowDx, this.mCollapsedShadowDx, f, null), CollapsingTextHelper.lerp(this.mExpandedShadowDy, this.mCollapsedShadowDy, f, null), CollapsingTextHelper.blendColors(this.mExpandedShadowColor, this.mCollapsedShadowColor, f));
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateUsingTextSize(float f) {
        CharSequence charSequence;
        block10: {
            block9: {
                float f2;
                boolean bl = true;
                if (this.mText == null) break block9;
                float f3 = this.mCollapsedBounds.width();
                float f4 = this.mExpandedBounds.width();
                boolean bl2 = false;
                boolean bl3 = false;
                if (CollapsingTextHelper.isClose(f, this.mCollapsedTextSize)) {
                    f2 = this.mCollapsedTextSize;
                    this.mScale = 1.0f;
                    if (this.areTypefacesDifferent(this.mCurrentTypeface, this.mCollapsedTypeface)) {
                        this.mCurrentTypeface = this.mCollapsedTypeface;
                        bl3 = true;
                    }
                    f = f3;
                } else {
                    f2 = this.mExpandedTextSize;
                    bl3 = bl2;
                    if (this.areTypefacesDifferent(this.mCurrentTypeface, this.mExpandedTypeface)) {
                        this.mCurrentTypeface = this.mExpandedTypeface;
                        bl3 = true;
                    }
                    this.mScale = CollapsingTextHelper.isClose(f, this.mExpandedTextSize) ? 1.0f : f / this.mExpandedTextSize;
                    f = this.mCollapsedTextSize / this.mExpandedTextSize;
                    f = f4 * f > f3 ? Math.min(f3 / f, f4) : f4;
                }
                bl2 = bl3;
                if (f > 0.0f) {
                    bl3 = this.mCurrentTextSize != f2 || this.mBoundsChanged || bl3;
                    this.mCurrentTextSize = f2;
                    this.mBoundsChanged = false;
                    bl2 = bl3;
                }
                if (this.mTextToDraw != null && !bl2) break block9;
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                this.mTextPaint.setTypeface(this.mCurrentTypeface);
                TextPaint textPaint = this.mTextPaint;
                if (this.mScale == 1.0f) {
                    bl = false;
                }
                textPaint.setLinearText(bl);
                charSequence = TextUtils.ellipsize((CharSequence)this.mText, (TextPaint)this.mTextPaint, (float)f, (TextUtils.TruncateAt)TextUtils.TruncateAt.END);
                if (!TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mTextToDraw)) break block10;
            }
            return;
        }
        this.mTextToDraw = charSequence;
        this.mIsRtl = this.calculateIsRtl(this.mTextToDraw);
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureExpandedTexture() {
        block3: {
            block2: {
                if (this.mExpandedTitleTexture != null || this.mExpandedBounds.isEmpty() || TextUtils.isEmpty((CharSequence)this.mTextToDraw)) break block2;
                this.calculateOffsets(0.0f);
                this.mTextureAscent = this.mTextPaint.ascent();
                this.mTextureDescent = this.mTextPaint.descent();
                int n = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
                int n2 = Math.round(this.mTextureDescent - this.mTextureAscent);
                if (n <= 0 || n2 <= 0) break block2;
                this.mExpandedTitleTexture = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                new Canvas(this.mExpandedTitleTexture).drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, (float)n2 - this.mTextPaint.descent(), (Paint)this.mTextPaint);
                if (this.mTexturePaint == null) break block3;
            }
            return;
        }
        this.mTexturePaint = new Paint(3);
    }

    private int getCurrentCollapsedTextColor() {
        if (this.mState != null) {
            return this.mCollapsedTextColor.getColorForState(this.mState, 0);
        }
        return this.mCollapsedTextColor.getDefaultColor();
    }

    private int getCurrentExpandedTextColor() {
        if (this.mState != null) {
            return this.mExpandedTextColor.getColorForState(this.mState, 0);
        }
        return this.mExpandedTextColor.getDefaultColor();
    }

    private void interpolateBounds(float f) {
        this.mCurrentBounds.left = CollapsingTextHelper.lerp(this.mExpandedBounds.left, this.mCollapsedBounds.left, f, this.mPositionInterpolator);
        this.mCurrentBounds.top = CollapsingTextHelper.lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f, this.mPositionInterpolator);
        this.mCurrentBounds.right = CollapsingTextHelper.lerp(this.mExpandedBounds.right, this.mCollapsedBounds.right, f, this.mPositionInterpolator);
        this.mCurrentBounds.bottom = CollapsingTextHelper.lerp(this.mExpandedBounds.bottom, this.mCollapsedBounds.bottom, f, this.mPositionInterpolator);
    }

    private static boolean isClose(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    private static float lerp(float f, float f2, float f3, Interpolator interpolator) {
        float f4 = f3;
        if (interpolator != null) {
            f4 = interpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f4);
    }

    private Typeface readFontFamilyTypeface(int n) {
        TypedArray typedArray;
        block4: {
            typedArray = this.mView.getContext().obtainStyledAttributes(n, new int[]{16843692});
            String string2 = typedArray.getString(0);
            if (string2 == null) break block4;
            string2 = Typeface.create((String)string2, (int)0);
            return string2;
        }
        typedArray.recycle();
        return null;
        finally {
            typedArray.recycle();
        }
    }

    private static boolean rectEquals(Rect rect, int n, int n2, int n3, int n4) {
        return rect.left == n && rect.top == n2 && rect.right == n3 && rect.bottom == n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setInterpolatedTextSize(float f) {
        this.calculateUsingTextSize(f);
        boolean bl = USE_SCALING_TEXTURE && this.mScale != 1.0f;
        this.mUseTexture = bl;
        if (this.mUseTexture) {
            this.ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n = canvas.save();
        if (this.mTextToDraw != null && this.mDrawTitle) {
            float f;
            float f2;
            float f3 = this.mCurrentDrawX;
            float f4 = this.mCurrentDrawY;
            boolean bl = this.mUseTexture && this.mExpandedTitleTexture != null;
            if (bl) {
                f2 = this.mTextureAscent * this.mScale;
                f = this.mTextureDescent;
                f = this.mScale;
            } else {
                f2 = this.mTextPaint.ascent() * this.mScale;
                this.mTextPaint.descent();
                f = this.mScale;
            }
            f = f4;
            if (bl) {
                f = f4 + f2;
            }
            if (this.mScale != 1.0f) {
                canvas.scale(this.mScale, this.mScale, f3, f);
            }
            if (bl) {
                canvas.drawBitmap(this.mExpandedTitleTexture, f3, f, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), f3, f, (Paint)this.mTextPaint);
            }
        }
        canvas.restoreToCount(n);
    }

    ColorStateList getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    int getCollapsedTextGravity() {
        return this.mCollapsedTextGravity;
    }

    float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    Typeface getCollapsedTypeface() {
        if (this.mCollapsedTypeface != null) {
            return this.mCollapsedTypeface;
        }
        return Typeface.DEFAULT;
    }

    int getExpandedTextGravity() {
        return this.mExpandedTextGravity;
    }

    Typeface getExpandedTypeface() {
        if (this.mExpandedTypeface != null) {
            return this.mExpandedTypeface;
        }
        return Typeface.DEFAULT;
    }

    float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    CharSequence getText() {
        return this.mText;
    }

    final boolean isStateful() {
        return this.mCollapsedTextColor != null && this.mCollapsedTextColor.isStateful() || this.mExpandedTextColor != null && this.mExpandedTextColor.isStateful();
    }

    /*
     * Enabled aggressive block sorting
     */
    void onBoundsChanged() {
        boolean bl = this.mCollapsedBounds.width() > 0 && this.mCollapsedBounds.height() > 0 && this.mExpandedBounds.width() > 0 && this.mExpandedBounds.height() > 0;
        this.mDrawTitle = bl;
    }

    public void recalculate() {
        if (this.mView.getHeight() > 0 && this.mView.getWidth() > 0) {
            this.calculateBaseOffsets();
            this.calculateCurrentOffsets();
        }
    }

    void setCollapsedBounds(int n, int n2, int n3, int n4) {
        if (!CollapsingTextHelper.rectEquals(this.mCollapsedBounds, n, n2, n3, n4)) {
            this.mCollapsedBounds.set(n, n2, n3, n4);
            this.mBoundsChanged = true;
            this.onBoundsChanged();
        }
    }

    void setCollapsedTextAppearance(int n) {
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), n, R.styleable.TextAppearance);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mCollapsedTextSize);
        }
        this.mCollapsedShadowColor = tintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.mCollapsedShadowDx = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mCollapsedShadowDy = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mCollapsedShadowRadius = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArray.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mCollapsedTypeface = this.readFontFamilyTypeface(n);
        }
        this.recalculate();
    }

    void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.mCollapsedTextColor != colorStateList) {
            this.mCollapsedTextColor = colorStateList;
            this.recalculate();
        }
    }

    void setCollapsedTextGravity(int n) {
        if (this.mCollapsedTextGravity != n) {
            this.mCollapsedTextGravity = n;
            this.recalculate();
        }
    }

    void setCollapsedTypeface(Typeface typeface) {
        if (this.areTypefacesDifferent(this.mCollapsedTypeface, typeface)) {
            this.mCollapsedTypeface = typeface;
            this.recalculate();
        }
    }

    void setExpandedBounds(int n, int n2, int n3, int n4) {
        if (!CollapsingTextHelper.rectEquals(this.mExpandedBounds, n, n2, n3, n4)) {
            this.mExpandedBounds.set(n, n2, n3, n4);
            this.mBoundsChanged = true;
            this.onBoundsChanged();
        }
    }

    void setExpandedTextAppearance(int n) {
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), n, R.styleable.TextAppearance);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mExpandedTextSize);
        }
        this.mExpandedShadowColor = tintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.mExpandedShadowDx = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mExpandedShadowDy = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mExpandedShadowRadius = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArray.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mExpandedTypeface = this.readFontFamilyTypeface(n);
        }
        this.recalculate();
    }

    void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.mExpandedTextColor != colorStateList) {
            this.mExpandedTextColor = colorStateList;
            this.recalculate();
        }
    }

    void setExpandedTextGravity(int n) {
        if (this.mExpandedTextGravity != n) {
            this.mExpandedTextGravity = n;
            this.recalculate();
        }
    }

    void setExpandedTextSize(float f) {
        if (this.mExpandedTextSize != f) {
            this.mExpandedTextSize = f;
            this.recalculate();
        }
    }

    void setExpandedTypeface(Typeface typeface) {
        if (this.areTypefacesDifferent(this.mExpandedTypeface, typeface)) {
            this.mExpandedTypeface = typeface;
            this.recalculate();
        }
    }

    void setExpansionFraction(float f) {
        if ((f = MathUtils.clamp(f, 0.0f, 1.0f)) != this.mExpandedFraction) {
            this.mExpandedFraction = f;
            this.calculateCurrentOffsets();
        }
    }

    void setPositionInterpolator(Interpolator interpolator) {
        this.mPositionInterpolator = interpolator;
        this.recalculate();
    }

    final boolean setState(int[] arrn) {
        this.mState = arrn;
        if (this.isStateful()) {
            this.recalculate();
            return true;
        }
        return false;
    }

    void setText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.mText)) {
            this.mText = charSequence;
            this.mTextToDraw = null;
            this.clearTexture();
            this.recalculate();
        }
    }

    void setTextSizeInterpolator(Interpolator interpolator) {
        this.mTextSizeInterpolator = interpolator;
        this.recalculate();
    }

    void setTypefaces(Typeface typeface) {
        this.mExpandedTypeface = typeface;
        this.mCollapsedTypeface = typeface;
        this.recalculate();
    }
}

