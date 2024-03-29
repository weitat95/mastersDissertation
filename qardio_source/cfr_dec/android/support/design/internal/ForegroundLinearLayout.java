/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.Gravity
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;

public class ForegroundLinearLayout
extends LinearLayoutCompat {
    private Drawable mForeground;
    boolean mForegroundBoundsChanged = false;
    private int mForegroundGravity = 119;
    protected boolean mForegroundInPadding = true;
    private final Rect mOverlayBounds;
    private final Rect mSelfBounds = new Rect();

    public ForegroundLinearLayout(Context context) {
        this(context, null);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mOverlayBounds = new Rect();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ForegroundLinearLayout, n, 0);
        this.mForegroundGravity = context.getInt(R.styleable.ForegroundLinearLayout_android_foregroundGravity, this.mForegroundGravity);
        attributeSet = context.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground);
        if (attributeSet != null) {
            this.setForeground((Drawable)attributeSet);
        }
        this.mForegroundInPadding = context.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mForeground != null) {
            Drawable drawable2 = this.mForeground;
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                Rect rect = this.mSelfBounds;
                Rect rect2 = this.mOverlayBounds;
                int n = this.getRight() - this.getLeft();
                int n2 = this.getBottom() - this.getTop();
                if (this.mForegroundInPadding) {
                    rect.set(0, 0, n, n2);
                } else {
                    rect.set(this.getPaddingLeft(), this.getPaddingTop(), n - this.getPaddingRight(), n2 - this.getPaddingBottom());
                }
                Gravity.apply((int)this.mForegroundGravity, (int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Rect)rect, (Rect)rect2);
                drawable2.setBounds(rect2);
            }
            drawable2.draw(canvas);
        }
    }

    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mForeground != null) {
            this.mForeground.setHotspot(f, f2);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mForeground != null && this.mForeground.isStateful()) {
            this.mForeground.setState(this.getDrawableState());
        }
    }

    public Drawable getForeground() {
        return this.mForeground;
    }

    public int getForegroundGravity() {
        return this.mForegroundGravity;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mForeground != null) {
            this.mForeground.jumpToCurrentState();
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mForegroundBoundsChanged |= bl;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mForegroundBoundsChanged = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setForeground(Drawable drawable2) {
        if (this.mForeground != drawable2) {
            if (this.mForeground != null) {
                this.mForeground.setCallback(null);
                this.unscheduleDrawable(this.mForeground);
            }
            this.mForeground = drawable2;
            if (drawable2 != null) {
                this.setWillNotDraw(false);
                drawable2.setCallback((Drawable.Callback)this);
                if (drawable2.isStateful()) {
                    drawable2.setState(this.getDrawableState());
                }
                if (this.mForegroundGravity == 119) {
                    drawable2.getPadding(new Rect());
                }
            } else {
                this.setWillNotDraw(true);
            }
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setForegroundGravity(int n) {
        if (this.mForegroundGravity != n) {
            int n2 = n;
            if ((0x800007 & n) == 0) {
                n2 = n | 0x800003;
            }
            n = n2;
            if ((n2 & 0x70) == 0) {
                n = n2 | 0x30;
            }
            this.mForegroundGravity = n;
            if (this.mForegroundGravity == 119 && this.mForeground != null) {
                Rect rect = new Rect();
                this.mForeground.getPadding(rect);
            }
            this.requestLayout();
        }
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mForeground;
    }
}

