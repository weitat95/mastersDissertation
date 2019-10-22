/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewConfiguration
 *  android.widget.TextView
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class PagerTabStrip
extends PagerTitleStrip {
    private boolean mDrawFullUnderline = false;
    private boolean mDrawFullUnderlineSet = false;
    private int mFullUnderlineHeight;
    private boolean mIgnoreTap;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mMinPaddingBottom;
    private int mMinStripHeight;
    private int mMinTextSpacing;
    private int mTabAlpha = 255;
    private int mTabPadding;
    private final Paint mTabPaint = new Paint();
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;

    public PagerTabStrip(Context context) {
        this(context, null);
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIndicatorColor = this.mTextColor;
        this.mTabPaint.setColor(this.mIndicatorColor);
        float f = context.getResources().getDisplayMetrics().density;
        this.mIndicatorHeight = (int)(3.0f * f + 0.5f);
        this.mMinPaddingBottom = (int)(6.0f * f + 0.5f);
        this.mMinTextSpacing = (int)(64.0f * f);
        this.mTabPadding = (int)(16.0f * f + 0.5f);
        this.mFullUnderlineHeight = (int)(1.0f * f + 0.5f);
        this.mMinStripHeight = (int)(32.0f * f + 0.5f);
        this.mTouchSlop = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        this.setTextSpacing(this.getTextSpacing());
        this.setWillNotDraw(false);
        this.mPrevText.setFocusable(true);
        this.mPrevText.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1);
            }
        });
        this.mNextText.setFocusable(true);
        this.mNextText.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() + 1);
            }
        });
        if (this.getBackground() == null) {
            this.mDrawFullUnderline = true;
        }
    }

    public boolean getDrawFullUnderline() {
        return this.mDrawFullUnderline;
    }

    @Override
    int getMinHeight() {
        return Math.max(super.getMinHeight(), this.mMinStripHeight);
    }

    public int getTabIndicatorColor() {
        return this.mIndicatorColor;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.getHeight();
        int n2 = this.mCurrText.getLeft();
        int n3 = this.mTabPadding;
        int n4 = this.mCurrText.getRight();
        int n5 = this.mTabPadding;
        int n6 = this.mIndicatorHeight;
        this.mTabPaint.setColor(this.mTabAlpha << 24 | this.mIndicatorColor & 0xFFFFFF);
        canvas.drawRect((float)(n2 - n3), (float)(n - n6), (float)(n4 + n5), (float)n, this.mTabPaint);
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor(0xFF000000 | this.mIndicatorColor & 0xFFFFFF);
            canvas.drawRect((float)this.getPaddingLeft(), (float)(n - this.mFullUnderlineHeight), (float)(this.getWidth() - this.getPaddingRight()), (float)n, this.mTabPaint);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n != 0 && this.mIgnoreTap) {
            return false;
        }
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        switch (n) {
            case 0: {
                this.mInitialMotionX = f;
                this.mInitialMotionY = f2;
                this.mIgnoreTap = false;
                return true;
            }
            case 2: {
                if (!(Math.abs(f - this.mInitialMotionX) > (float)this.mTouchSlop)) {
                    if (!(Math.abs(f2 - this.mInitialMotionY) > (float)this.mTouchSlop)) return true;
                }
                this.mIgnoreTap = true;
                return true;
            }
            case 1: {
                if (f < (float)(this.mCurrText.getLeft() - this.mTabPadding)) {
                    this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                    return true;
                }
                if (!(f > (float)(this.mCurrText.getRight() + this.mTabPadding))) return true;
                this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1);
                return true;
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBackgroundColor(int n) {
        super.setBackgroundColor(n);
        if (!this.mDrawFullUnderlineSet) {
            boolean bl = (0xFF000000 & n) == 0;
            this.mDrawFullUnderline = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        if (!this.mDrawFullUnderlineSet) {
            boolean bl = drawable2 == null;
            this.mDrawFullUnderline = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBackgroundResource(int n) {
        super.setBackgroundResource(n);
        if (!this.mDrawFullUnderlineSet) {
            boolean bl = n == 0;
            this.mDrawFullUnderline = bl;
        }
    }

    public void setDrawFullUnderline(boolean bl) {
        this.mDrawFullUnderline = bl;
        this.mDrawFullUnderlineSet = true;
        this.invalidate();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        int n5 = n4;
        if (n4 < this.mMinPaddingBottom) {
            n5 = this.mMinPaddingBottom;
        }
        super.setPadding(n, n2, n3, n5);
    }

    public void setTabIndicatorColor(int n) {
        this.mIndicatorColor = n;
        this.mTabPaint.setColor(this.mIndicatorColor);
        this.invalidate();
    }

    public void setTabIndicatorColorResource(int n) {
        this.setTabIndicatorColor(ContextCompat.getColor(this.getContext(), n));
    }

    @Override
    public void setTextSpacing(int n) {
        int n2 = n;
        if (n < this.mMinTextSpacing) {
            n2 = this.mMinTextSpacing;
        }
        super.setTextSpacing(n2);
    }

    @Override
    void updateTextPositions(int n, float f, boolean bl) {
        Rect rect = this.mTempRect;
        int n2 = this.getHeight();
        int n3 = this.mCurrText.getLeft();
        int n4 = this.mTabPadding;
        int n5 = this.mCurrText.getRight();
        int n6 = this.mTabPadding;
        int n7 = n2 - this.mIndicatorHeight;
        rect.set(n3 - n4, n7, n5 + n6, n2);
        super.updateTextPositions(n, f, bl);
        this.mTabAlpha = (int)(Math.abs(f - 0.5f) * 2.0f * 255.0f);
        rect.union(this.mCurrText.getLeft() - this.mTabPadding, n7, this.mCurrText.getRight() + this.mTabPadding, n2);
        this.invalidate(rect);
    }

}

