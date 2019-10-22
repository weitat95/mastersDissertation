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
 *  android.view.View
 *  android.widget.FrameLayout
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ScrimInsetsFrameLayout
extends FrameLayout {
    Drawable mInsetForeground;
    Rect mInsets;
    private Rect mTempRect = new Rect();

    public ScrimInsetsFrameLayout(Context context) {
        this(context, null);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrimInsetsFrameLayout, n, R.style.Widget_Design_ScrimInsetsFrameLayout);
        this.mInsetForeground = context.getDrawable(R.styleable.ScrimInsetsFrameLayout_insetForeground);
        context.recycle();
        this.setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                void var2_3;
                if (ScrimInsetsFrameLayout.this.mInsets == null) {
                    ScrimInsetsFrameLayout.this.mInsets = new Rect();
                }
                ScrimInsetsFrameLayout.this.mInsets.set(var2_3.getSystemWindowInsetLeft(), var2_3.getSystemWindowInsetTop(), var2_3.getSystemWindowInsetRight(), var2_3.getSystemWindowInsetBottom());
                ScrimInsetsFrameLayout.this.onInsetsChanged((WindowInsetsCompat)var2_3);
                ScrimInsetsFrameLayout scrimInsetsFrameLayout = ScrimInsetsFrameLayout.this;
                boolean bl = !var2_3.hasSystemWindowInsets() || ScrimInsetsFrameLayout.this.mInsetForeground == null;
                scrimInsetsFrameLayout.setWillNotDraw(bl);
                ViewCompat.postInvalidateOnAnimation((View)ScrimInsetsFrameLayout.this);
                return var2_3.consumeSystemWindowInsets();
            }
        });
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (this.mInsets != null && this.mInsetForeground != null) {
            int n3 = canvas.save();
            canvas.translate((float)this.getScrollX(), (float)this.getScrollY());
            this.mTempRect.set(0, 0, n, this.mInsets.top);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas);
            this.mTempRect.set(0, n2 - this.mInsets.bottom, n, n2);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas);
            this.mTempRect.set(0, this.mInsets.top, this.mInsets.left, n2 - this.mInsets.bottom);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas);
            this.mTempRect.set(n - this.mInsets.right, this.mInsets.top, n, n2 - this.mInsets.bottom);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas);
            canvas.restoreToCount(n3);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mInsetForeground != null) {
            this.mInsetForeground.setCallback((Drawable.Callback)this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mInsetForeground != null) {
            this.mInsetForeground.setCallback(null);
        }
    }

    protected void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
    }

}

