/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v4.graphics.drawable.TintAwareDrawable;

class DrawableWrapperApi14
extends Drawable
implements Drawable.Callback,
DrawableWrapper,
TintAwareDrawable {
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private PorterDuff.Mode mCurrentMode;
    Drawable mDrawable;
    private boolean mMutated;
    DrawableWrapperState mState;

    DrawableWrapperApi14(Drawable drawable2) {
        this.mState = this.mutateConstantState();
        this.setWrappedDrawable(drawable2);
    }

    DrawableWrapperApi14(DrawableWrapperState drawableWrapperState, Resources resources) {
        this.mState = drawableWrapperState;
        this.updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        if (this.mState != null && this.mState.mDrawableState != null) {
            this.setWrappedDrawable(this.newDrawableFromState(this.mState.mDrawableState, resources));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateTint(int[] arrn) {
        block3: {
            PorterDuff.Mode mode;
            int n;
            block4: {
                block2: {
                    if (!this.isCompatTintEnabled()) break block2;
                    ColorStateList colorStateList = this.mState.mTint;
                    mode = this.mState.mTintMode;
                    if (colorStateList == null || mode == null) break block3;
                    n = colorStateList.getColorForState(arrn, colorStateList.getDefaultColor());
                    if (!this.mColorFilterSet || n != this.mCurrentColor || mode != this.mCurrentMode) break block4;
                }
                return false;
            }
            this.setColorFilter(n, mode);
            this.mCurrentColor = n;
            this.mCurrentMode = mode;
            this.mColorFilterSet = true;
            return true;
        }
        this.mColorFilterSet = false;
        this.clearColorFilter();
        return false;
    }

    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getChangingConfigurations() {
        int n;
        int n2 = super.getChangingConfigurations();
        if (this.mState != null) {
            n = this.mState.getChangingConfigurations();
            do {
                return n | n2 | this.mDrawable.getChangingConfigurations();
                break;
            } while (true);
        }
        n = 0;
        return n | n2 | this.mDrawable.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        if (this.mState != null && this.mState.canConstantState()) {
            this.mState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mState;
        }
        return null;
    }

    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.mDrawable.getPadding(rect);
    }

    public int[] getState() {
        return this.mDrawable.getState();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    @Override
    public final Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void invalidateDrawable(Drawable drawable2) {
        this.invalidateSelf();
    }

    protected boolean isCompatTintEnabled() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isStateful() {
        ColorStateList colorStateList;
        if (this.isCompatTintEnabled() && this.mState != null) {
            colorStateList = this.mState.mTint;
            do {
                return colorStateList != null && colorStateList.isStateful() || this.mDrawable.isStateful();
                break;
            } while (true);
        }
        colorStateList = null;
        return colorStateList != null && colorStateList.isStateful() || this.mDrawable.isStateful();
    }

    public void jumpToCurrentState() {
        this.mDrawable.jumpToCurrentState();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = this.mutateConstantState();
            if (this.mDrawable != null) {
                this.mDrawable.mutate();
            }
            if (this.mState != null) {
                DrawableWrapperState drawableWrapperState = this.mState;
                Drawable.ConstantState constantState = this.mDrawable != null ? this.mDrawable.getConstantState() : null;
                drawableWrapperState.mDrawableState = constantState;
            }
            this.mMutated = true;
        }
        return this;
    }

    DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateBase(this.mState, null);
    }

    protected Drawable newDrawableFromState(Drawable.ConstantState constantState, Resources resources) {
        return constantState.newDrawable(resources);
    }

    protected void onBoundsChange(Rect rect) {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int n) {
        return this.mDrawable.setLevel(n);
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    public void setAlpha(int n) {
        this.mDrawable.setAlpha(n);
    }

    public void setChangingConfigurations(int n) {
        this.mDrawable.setChangingConfigurations(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }

    public void setDither(boolean bl) {
        this.mDrawable.setDither(bl);
    }

    public void setFilterBitmap(boolean bl) {
        this.mDrawable.setFilterBitmap(bl);
    }

    public boolean setState(int[] arrn) {
        boolean bl = this.mDrawable.setState(arrn);
        return this.updateTint(arrn) || bl;
    }

    @Override
    public void setTint(int n) {
        this.setTintList(ColorStateList.valueOf((int)n));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.updateTint(this.getState());
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        this.mState.mTintMode = mode;
        this.updateTint(this.getState());
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        return super.setVisible(bl, bl2) || this.mDrawable.setVisible(bl, bl2);
    }

    @Override
    public final void setWrappedDrawable(Drawable drawable2) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback)this);
            this.setVisible(drawable2.isVisible(), true);
            this.setState(drawable2.getState());
            this.setLevel(drawable2.getLevel());
            this.setBounds(drawable2.getBounds());
            if (this.mState != null) {
                this.mState.mDrawableState = drawable2.getConstantState();
            }
        }
        this.invalidateSelf();
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }

    protected static abstract class DrawableWrapperState
    extends Drawable.ConstantState {
        int mChangingConfigurations;
        Drawable.ConstantState mDrawableState;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;

        DrawableWrapperState(DrawableWrapperState drawableWrapperState, Resources resources) {
            if (drawableWrapperState != null) {
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations;
                this.mDrawableState = drawableWrapperState.mDrawableState;
                this.mTint = drawableWrapperState.mTint;
                this.mTintMode = drawableWrapperState.mTintMode;
            }
        }

        boolean canConstantState() {
            return this.mDrawableState != null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getChangingConfigurations() {
            int n;
            int n2 = this.mChangingConfigurations;
            if (this.mDrawableState != null) {
                n = this.mDrawableState.getChangingConfigurations();
                do {
                    return n | n2;
                    break;
                } while (true);
            }
            n = 0;
            return n | n2;
        }

        public Drawable newDrawable() {
            return this.newDrawable(null);
        }

        public abstract Drawable newDrawable(Resources var1);
    }

    private static class DrawableWrapperStateBase
    extends DrawableWrapperState {
        DrawableWrapperStateBase(DrawableWrapperState drawableWrapperState, Resources resources) {
            super(drawableWrapperState, resources);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new DrawableWrapperApi14(this, resources);
        }
    }

}

