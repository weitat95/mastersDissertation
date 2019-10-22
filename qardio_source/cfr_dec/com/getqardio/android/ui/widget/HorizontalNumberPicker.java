/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ArgbEvaluator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.BoringLayout
 *  android.text.BoringLayout$Metrics
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.OverScroller
 */
package com.getqardio.android.ui.widget;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import com.getqardio.android.R;
import com.getqardio.android.utils.Utils;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class HorizontalNumberPicker
extends View {
    public static final String TAG = HorizontalNumberPicker.class.getName();
    private OverScroller mAdjustScrollerX;
    private BoringLayout.Metrics mBoringMetrics;
    private float mDividerSize = 0.0f;
    private TextUtils.TruncateAt mEllipsize;
    private OverScroller mFlingScrollerX;
    private RectF mItemClipBounds;
    private RectF mItemClipBoundsOffser;
    private int mItemWidth;
    private float mLastDownEventX;
    private BoringLayout[] mLayouts;
    private EdgeEffect mLeftEdgeEffect;
    private Marquee mMarquee;
    private int mMarqueeRepeatLimit = 3;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private OnItemClicked mOnItemClicked;
    private OnItemSelected mOnItemSelected;
    private final int mOverscrollDistance;
    private int mPressedItem = -1;
    private int mPreviousScrollerX;
    private EdgeEffect mRightEdgeEffect;
    private boolean mScrollingX;
    private int mSelectedItem;
    private int mSideItems = 1;
    private ColorStateList mTextColor;
    private TextDirectionHeuristicCompat mTextDir;
    private TextPaint mTextPaint;
    private final PickerTouchHelper mTouchHelper;
    private int mTouchSlop;
    private int[] mValues;
    private VelocityTracker mVelocityTracker;
    private int maxValue;
    private int minSelectableValue;
    private int minValue;

    public HorizontalNumberPicker(Context context) {
        this(context, null);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 2130771971);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HorizontalNumberPicker(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        this.mTextPaint = textPaint;
        attributeSet = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.HorizontalNumberPicker, n, 0);
        this.minSelectableValue = 5;
        int n2 = this.mSideItems;
        this.mTextColor = attributeSet.getColorStateList(1);
        if (this.mTextColor == null) {
            this.mTextColor = ColorStateList.valueOf((int)-16777216);
        }
        n = attributeSet.getInt(2, 3);
        this.mMarqueeRepeatLimit = attributeSet.getInt(3, this.mMarqueeRepeatLimit);
        this.mDividerSize = attributeSet.getDimension(5, this.mDividerSize);
        n2 = attributeSet.getInt(6, n2);
        float f = attributeSet.getDimension(0, -1.0f);
        if (f > -1.0f) {
            this.setTextSize(f);
        }
        switch (n) {
            case 1: {
                this.setEllipsize(TextUtils.TruncateAt.START);
                break;
            }
            case 2: {
                this.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            }
            case 3: {
                this.setEllipsize(TextUtils.TruncateAt.END);
                break;
            }
            case 4: {
                this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                break;
            }
        }
        attributeSet = this.mTextPaint.getFontMetricsInt();
        this.mBoringMetrics = new BoringLayout.Metrics();
        this.mBoringMetrics.ascent = attributeSet.ascent;
        this.mBoringMetrics.bottom = attributeSet.bottom;
        this.mBoringMetrics.descent = attributeSet.descent;
        this.mBoringMetrics.leading = attributeSet.leading;
        this.mBoringMetrics.top = attributeSet.top;
        this.mBoringMetrics.width = this.mItemWidth;
        this.setWillNotDraw(false);
        this.mFlingScrollerX = new OverScroller(context);
        this.mAdjustScrollerX = new OverScroller(context, (Interpolator)new DecelerateInterpolator(2.5f));
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity() / 4;
        this.mOverscrollDistance = context.getScaledOverscrollDistance();
        this.mPreviousScrollerX = Integer.MIN_VALUE;
        this.setSideItems(n2);
        this.mTouchHelper = new PickerTouchHelper(this);
        ViewCompat.setAccessibilityDelegate(this, this.mTouchHelper);
        return;
        finally {
            attributeSet.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void adjustToNearestItemX() {
        int n;
        int n2 = this.getScrollX();
        int n3 = Math.round((float)n2 / ((float)this.mItemWidth + this.mDividerSize * 1.0f));
        if (n3 < 0) {
            n = 0;
        } else {
            n = n3;
            if (n3 > this.mValues.length) {
                n = this.mValues.length;
            }
        }
        this.mSelectedItem = n;
        n3 = this.mItemWidth;
        int n4 = (int)this.mDividerSize;
        this.mAdjustScrollerX.startScroll(n2, 0, (n3 + n4) * n - n2, 0, 800);
        this.invalidate();
    }

    private void calculateItemSize(int n, int n2) {
        int n3 = this.mSideItems * 2 + 1;
        this.mItemWidth = (n - (int)this.mDividerSize * (n3 - 1)) / n3;
        this.mItemClipBounds = new RectF(0.0f, 0.0f, (float)this.mItemWidth, (float)n2);
        this.mItemClipBoundsOffser = new RectF(this.mItemClipBounds);
        this.scrollToItem(this.mSelectedItem);
        this.remakeLayout();
        this.startMarqueeIfNeeded();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void computeScrollX() {
        OverScroller overScroller;
        block10: {
            block9: {
                block8: {
                    OverScroller overScroller2;
                    overScroller = overScroller2 = this.mFlingScrollerX;
                    if (!overScroller2.isFinished()) break block8;
                    overScroller = overScroller2 = this.mAdjustScrollerX;
                    if (overScroller2.isFinished()) break block9;
                }
                if (overScroller.computeScrollOffset()) break block10;
            }
            return;
        }
        int n = overScroller.getCurrX();
        if (this.mPreviousScrollerX == Integer.MIN_VALUE) {
            this.mPreviousScrollerX = overScroller.getStartX();
        }
        int n2 = this.getScrollRange();
        if (this.mPreviousScrollerX >= 0 && n < 0) {
            this.mLeftEdgeEffect.onAbsorb((int)overScroller.getCurrVelocity());
        } else if (this.mPreviousScrollerX <= n2 && n > n2) {
            this.mRightEdgeEffect.onAbsorb((int)overScroller.getCurrVelocity());
        }
        this.overScrollBy(n - this.mPreviousScrollerX, 0, this.mPreviousScrollerX, this.getScrollY(), this.getScrollRange(), 0, this.mOverscrollDistance, 0, false);
        this.mPreviousScrollerX = n;
        if (overScroller.isFinished()) {
            this.onScrollerFinishedX(overScroller);
        }
        this.postInvalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawEdgeEffect(Canvas canvas, EdgeEffect edgeEffect, int n) {
        if (canvas == null || edgeEffect == null || n != 90 && n != 270 || edgeEffect.isFinished()) {
            return;
        }
        int n2 = canvas.getSaveCount();
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        canvas.rotate((float)n);
        if (n == 270) {
            canvas.translate((float)(-n4), (float)Math.max(0, this.getScrollX()));
        } else {
            canvas.translate(0.0f, -(Math.max((float)this.getScrollRange(), this.getScaleX()) + (float)n3));
        }
        edgeEffect.setSize(n4, n3);
        if (edgeEffect.draw(canvas)) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.postInvalidateOnAnimation();
            } else {
                this.postInvalidate();
            }
        }
        canvas.restoreToCount(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void finishScrolling() {
        this.adjustToNearestItemX();
        this.mScrollingX = false;
        this.startMarqueeIfNeeded();
        int n = this.getPositionFromCoordinates(this.getScrollX());
        int n2 = Arrays.binarySearch(this.mValues, this.minSelectableValue);
        if (n < n2) {
            this.smoothScrollBy(n2 - n);
            return;
        } else {
            if (this.mOnItemSelected == null) return;
            {
                this.post(new Runnable(){

                    @Override
                    public void run() {
                        int n = HorizontalNumberPicker.this.getPositionFromCoordinates(HorizontalNumberPicker.this.getScrollX());
                        HorizontalNumberPicker.this.mOnItemSelected.onItemSelected(HorizontalNumberPicker.this.mValues[n]);
                    }
                });
                return;
            }
        }
    }

    private void flingX(int n) {
        this.mPreviousScrollerX = Integer.MIN_VALUE;
        OverScroller overScroller = this.mFlingScrollerX;
        int n2 = this.getScrollX();
        int n3 = this.getScrollY();
        n = -n;
        int n4 = (int)((float)this.mItemWidth + this.mDividerSize);
        overScroller.fling(n2, n3, n, 0, 0, (this.mValues.length - 1) * n4, 0, 0, this.getWidth() / 2, 0);
        this.invalidate();
    }

    private void generateValues() {
        this.mValues = new int[this.maxValue - 1];
        for (int i = 0; i < this.mValues.length; ++i) {
            this.mValues[i] = i + 1;
        }
        this.setValues(this.mValues);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getColor(int n, int n2) {
        int n3 = (int)((float)this.mItemWidth + this.mDividerSize);
        float f = Math.abs(1.0f * (float)n % (float)n3 / 2.0f / ((float)n3 / 2.0f));
        f = (double)f > 0.5 ? (f -= 0.5f) : 0.5f - f;
        if (this.mPressedItem == n2) {
            ColorStateList colorStateList = this.mTextColor;
            n = this.mTextColor.getDefaultColor();
            n = colorStateList.getColorForState(new int[]{16842919}, n);
            n2 = this.mTextColor.getColorForState(new int[]{16842919, 16842913}, n);
            return (Integer)new ArgbEvaluator().evaluate(f * 2.0f, (Object)n2, (Object)n);
        }
        n = this.mTextColor.getDefaultColor();
        n2 = this.mTextColor.getColorForState(new int[]{16842913}, n);
        return (Integer)new ArgbEvaluator().evaluate(f * 2.0f, (Object)n2, (Object)n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getInBoundsX(int n) {
        if (n < 0) {
            return 0;
        }
        int n2 = n;
        if (n <= (this.mItemWidth + (int)this.mDividerSize) * (this.mValues.length - 1)) return n2;
        return (this.mItemWidth + (int)this.mDividerSize) * (this.mValues.length - 1);
    }

    private int getPositionFromCoordinates(int n) {
        return Math.round((float)n / ((float)this.mItemWidth + this.mDividerSize));
    }

    private int getPositionFromTouch(float f) {
        return this.getPositionFromCoordinates((int)((float)this.getScrollX() - ((float)this.mItemWidth + this.mDividerSize) * ((float)this.mSideItems + 0.5f) + f));
    }

    private int getPositionOnScreen(float f) {
        return (int)(f / ((float)this.mItemWidth + this.mDividerSize));
    }

    private int getRelativeInBound(int n) {
        int n2 = this.getScrollX();
        return this.getInBoundsX(n2 + n) - n2;
    }

    private int getScrollRange() {
        int n;
        int n2 = n = 0;
        if (this.mValues != null) {
            n2 = n;
            if (this.mValues.length != 0) {
                n2 = Math.max(0, (this.mItemWidth + (int)this.mDividerSize) * (this.mValues.length - 1));
            }
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getTextColor(int n) {
        int n2 = this.getScrollX();
        int n3 = this.mTextColor.getDefaultColor();
        int n4 = (int)((float)this.mItemWidth + this.mDividerSize);
        if (n2 > n4 * n - n4 / 2 && n2 < (n + 1) * n4 - n4 / 2) {
            return this.getColor(n2 - n4 / 2, n);
        }
        n2 = n3;
        if (n != this.mPressedItem) return n2;
        return this.mTextColor.getColorForState(new int[]{16842919}, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private TextDirectionHeuristicCompat getTextDirectionHeuristic() {
        boolean bl = true;
        if (Build.VERSION.SDK_INT < 17) {
            return TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        }
        if (this.getLayoutDirection() != 1) {
            bl = false;
        }
        switch (this.getTextDirection()) {
            default: {
                if (bl) {
                    return TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
                }
                return TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
            }
            case 2: {
                return TextDirectionHeuristicsCompat.ANYRTL_LTR;
            }
            case 3: {
                return TextDirectionHeuristicsCompat.LTR;
            }
            case 4: {
                return TextDirectionHeuristicsCompat.RTL;
            }
            case 5: 
        }
        return TextDirectionHeuristicsCompat.LOCALE;
    }

    private boolean isRtl(CharSequence charSequence) {
        if (this.mTextDir == null) {
            this.mTextDir = this.getTextDirectionHeuristic();
        }
        return this.mTextDir.isRtl(charSequence, 0, charSequence.length());
    }

    private void onScrollerFinishedX(OverScroller overScroller) {
        if (overScroller == this.mFlingScrollerX) {
            this.finishScrolling();
        }
    }

    private void remakeLayout() {
        if (this.mLayouts != null && this.mLayouts.length > 0 && this.getWidth() > 0) {
            for (int i = 0; i < this.mLayouts.length; ++i) {
                this.mLayouts[i].replaceOrMake((CharSequence)Utils.formatInteger(this.mValues[i]), this.mTextPaint, this.mItemWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, this.mBoringMetrics, false, this.mEllipsize, this.mItemWidth);
            }
        }
    }

    private void scrollToItem(int n) {
        this.scrollTo((this.mItemWidth + (int)this.mDividerSize) * n, 0);
    }

    private void selectItem() {
        if (this.mOnItemClicked != null) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    HorizontalNumberPicker.this.mOnItemClicked.onItemClicked(HorizontalNumberPicker.this.getSelectedItemPosition());
                }
            });
        }
        this.adjustToNearestItemX();
    }

    private void setTextSize(float f) {
        if (f != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(f);
            this.requestLayout();
            this.invalidate();
        }
    }

    private void smoothScrollBy(int n) {
        n = this.getRelativeInBound((this.mItemWidth + (int)this.mDividerSize) * n);
        this.mFlingScrollerX.startScroll(this.getScrollX(), 0, n, 0);
        this.stopMarqueeIfNeeded();
        this.invalidate();
    }

    private void startMarqueeIfNeeded() {
        this.stopMarqueeIfNeeded();
        int n = this.getSelectedItemPosition();
        if (n > 0 && this.mLayouts != null && this.mLayouts.length > n) {
            BoringLayout boringLayout = this.mLayouts[n];
            if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE && (float)this.mItemWidth < boringLayout.getLineWidth(0)) {
                this.mMarquee = new Marquee(this, (Layout)boringLayout, this.isRtl(String.valueOf(this.mValues[n])));
                this.mMarquee.start(this.mMarqueeRepeatLimit);
            }
        }
    }

    private void stopMarqueeIfNeeded() {
        if (this.mMarquee != null) {
            this.mMarquee.stop();
            this.mMarquee = null;
        }
    }

    public void computeScroll() {
        this.computeScrollX();
    }

    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    public void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    public int getSelectedItem() {
        if (this.mSelectedItem >= 0) {
            return this.mValues[this.mSelectedItem];
        }
        return 0;
    }

    public int getSelectedItemPosition() {
        return this.getPositionFromCoordinates(this.getScrollX());
    }

    public int getSideItems() {
        return this.mSideItems;
    }

    public int[] getValues() {
        return this.mValues;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = canvas.getSaveCount();
        canvas.save();
        int n2 = this.mSelectedItem;
        float f = (float)this.mItemWidth + this.mDividerSize;
        canvas.translate((float)this.mSideItems * f, 0.0f);
        if (this.mValues != null && this.mLayouts != null) {
            for (int i = 0; i < this.mValues.length; ++i) {
                RectF rectF;
                this.mTextPaint.setColor(this.getTextColor(i));
                BoringLayout boringLayout = this.mLayouts[i];
                int n3 = canvas.getSaveCount();
                canvas.save();
                float f2 = 0.0f;
                float f3 = boringLayout.getLineWidth(0);
                if (f3 > (float)this.mItemWidth) {
                    f2 = this.isRtl(String.valueOf(this.mValues[i])) ? 0.0f + (f3 - (float)this.mItemWidth) / 2.0f : 0.0f - (f3 - (float)this.mItemWidth) / 2.0f;
                }
                f3 = f2;
                if (this.mMarquee != null) {
                    f3 = f2;
                    if (i == n2) {
                        f3 = f2 + this.mMarquee.getScroll();
                    }
                }
                canvas.translate(-f3, (float)((canvas.getHeight() - boringLayout.getHeight()) / 2));
                if (f3 == 0.0f) {
                    rectF = this.mItemClipBounds;
                } else {
                    rectF = this.mItemClipBoundsOffser;
                    rectF.set(this.mItemClipBounds);
                    rectF.offset(f3, 0.0f);
                }
                canvas.clipRect(rectF);
                boringLayout.draw(canvas);
                if (this.mMarquee != null && i == n2 && this.mMarquee.shouldDrawGhost()) {
                    canvas.translate(this.mMarquee.getGhostOffset(), 0.0f);
                    boringLayout.draw(canvas);
                }
                canvas.restoreToCount(n3);
                canvas.translate(f, 0.0f);
            }
        }
        canvas.restoreToCount(n);
        this.drawEdgeEffect(canvas, this.mLeftEdgeEffect, 270);
        this.drawEdgeEffect(canvas, this.mRightEdgeEffect, 90);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (!this.isEnabled()) {
            return super.onKeyDown(n, keyEvent);
        }
        switch (n) {
            default: {
                return super.onKeyDown(n, keyEvent);
            }
            case 23: 
            case 66: {
                this.selectItem();
                return true;
            }
            case 21: {
                this.smoothScrollBy(-1);
                return true;
            }
            case 22: 
        }
        this.smoothScrollBy(1);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n2);
        int n4 = View.MeasureSpec.getSize((int)n);
        n = View.MeasureSpec.getSize((int)n2);
        if (n3 != 1073741824) {
            Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
            n2 = (int)(Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent)) + (this.getPaddingTop() + this.getPaddingBottom());
            n = n3 == Integer.MIN_VALUE ? Math.min(n, n2) : n2;
        }
        this.setMeasuredDimension(n4, n);
    }

    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        super.scrollTo(n, n2);
        if (!this.mFlingScrollerX.isFinished() && bl) {
            this.mFlingScrollerX.springBack(n, n2, 0, this.getScrollRange(), 0, 0);
        }
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setSelectedItem(((SavedState)((Object)object)).mSelItem);
    }

    @TargetApi(value=17)
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.mTextDir = this.getTextDirectionHeuristic();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mSelItem = this.mSelectedItem;
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.calculateItemSize(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 2: {
                float f;
                float f2 = motionEvent.getX();
                int n = (int)(this.mLastDownEventX - f2);
                if (!this.mScrollingX && (Math.abs(n) <= this.mTouchSlop || this.mValues == null || this.mValues.length <= 0)) break;
                if (!this.mScrollingX) {
                    n = 0;
                    this.mPressedItem = -1;
                    this.mScrollingX = true;
                    this.stopMarqueeIfNeeded();
                }
                int n2 = this.getScrollRange();
                if (this.overScrollBy(n, 0, this.getScrollX(), 0, n2, 0, this.mOverscrollDistance, 0, true)) {
                    this.mVelocityTracker.clear();
                }
                if ((f = (float)(this.getScrollX() + n)) < 0.0f) {
                    this.mLeftEdgeEffect.onPull((float)n / (float)this.getWidth());
                    if (!this.mRightEdgeEffect.isFinished()) {
                        this.mRightEdgeEffect.onRelease();
                    }
                } else if (f > (float)n2) {
                    this.mRightEdgeEffect.onPull((float)n / (float)this.getWidth());
                    if (!this.mLeftEdgeEffect.isFinished()) {
                        this.mLeftEdgeEffect.onRelease();
                    }
                }
                this.mLastDownEventX = f2;
                this.invalidate();
                break;
            }
            case 0: {
                if (!this.mAdjustScrollerX.isFinished()) {
                    this.mAdjustScrollerX.forceFinished(true);
                } else if (!this.mFlingScrollerX.isFinished()) {
                    this.mFlingScrollerX.forceFinished(true);
                } else {
                    this.mScrollingX = false;
                }
                this.mLastDownEventX = motionEvent.getX();
                if (!this.mScrollingX) {
                    this.mPressedItem = this.getPositionFromTouch(motionEvent.getX());
                }
                this.invalidate();
                break;
            }
            case 1: {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                int n = (int)velocityTracker.getXVelocity();
                if (this.mScrollingX && Math.abs(n) > this.mMinimumFlingVelocity) {
                    this.flingX(n);
                } else if (this.mValues != null) {
                    float f = motionEvent.getX();
                    if (!this.mScrollingX) {
                        n = this.getPositionOnScreen(f) - this.mSideItems;
                        if (n == 0) {
                            this.selectItem();
                        } else {
                            this.smoothScrollBy(n);
                        }
                    } else if (this.mScrollingX) {
                        this.finishScrolling();
                    }
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                if (this.mLeftEdgeEffect != null) {
                    this.mLeftEdgeEffect.onRelease();
                    this.mRightEdgeEffect.onRelease();
                }
            }
            case 3: {
                this.mPressedItem = -1;
                this.invalidate();
                if (this.mLeftEdgeEffect == null) break;
                this.mLeftEdgeEffect.onRelease();
                this.mRightEdgeEffect.onRelease();
            }
        }
        return true;
    }

    public void resetWeeksSelection() {
        this.minSelectableValue = this.minValue;
        this.setSelectedItemPosition(0);
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.mEllipsize != truncateAt) {
            this.mEllipsize = truncateAt;
            this.remakeLayout();
            this.invalidate();
        }
    }

    public void setMarqueeRepeatLimit(int n) {
        this.mMarqueeRepeatLimit = n;
    }

    public void setMinMaxValues(int n, int n2) {
        this.minValue = n;
        this.maxValue = n2;
        this.generateValues();
    }

    public void setMinSelectableValue(int n) {
        this.minSelectableValue = n;
        this.setSelectedItem(this.mSelectedItem);
    }

    public void setOnItemClickedListener(OnItemClicked onItemClicked) {
        this.mOnItemClicked = onItemClicked;
    }

    public void setOnItemSelectedListener(OnItemSelected onItemSelected) {
        this.mOnItemSelected = onItemSelected;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setOverScrollMode(int n) {
        if (n != 2) {
            Context context = this.getContext();
            this.mLeftEdgeEffect = new EdgeEffect(context);
            this.mRightEdgeEffect = new EdgeEffect(context);
        } else {
            this.mLeftEdgeEffect = null;
            this.mRightEdgeEffect = null;
        }
        super.setOverScrollMode(n);
    }

    public void setSelectedItem(int n) {
        System.out.println("Set selected item: item:" + n + ",min value:" + this.minSelectableValue);
        if (n < this.minSelectableValue) {
            n = this.minSelectableValue;
        }
        System.out.println("Item to select " + n);
        n = Arrays.binarySearch(this.mValues, n);
        System.out.println("Position " + n);
        if (n != -1) {
            this.setSelectedItemPosition(n);
        }
    }

    public void setSelectedItemPosition(int n) {
        this.mSelectedItem = n;
        this.scrollToItem(n);
    }

    public void setSideItems(int n) {
        if (this.mSideItems < 0) {
            throw new IllegalArgumentException("Number of items on each side must be grater or equal to 0.");
        }
        if (this.mSideItems != n) {
            this.mSideItems = n;
            this.calculateItemSize(this.getWidth(), this.getHeight());
        }
    }

    public void setValues(int[] arrn) {
        this.mValues = arrn;
        if (this.mItemWidth < 0) {
            this.mItemWidth = 0;
        }
        if (this.mValues != null) {
            this.mLayouts = new BoringLayout[this.mValues.length];
            for (int i = 0; i < this.mLayouts.length; ++i) {
                this.mLayouts[i] = new BoringLayout((CharSequence)String.valueOf(this.mValues[i]), this.mTextPaint, this.mItemWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, this.mBoringMetrics, false, this.mEllipsize, this.mItemWidth);
            }
        } else {
            this.mLayouts = new BoringLayout[0];
        }
        if (this.getWidth() > 0) {
            this.startMarqueeIfNeeded();
        }
        this.requestLayout();
        this.invalidate();
    }

    private static final class Marquee
    extends Handler {
        private float mFadeStop;
        private float mGhostOffset;
        private float mGhostStart;
        private final WeakReference<Layout> mLayout;
        private float mMaxFadeScroll;
        private float mMaxScroll;
        private int mRepeatLimit;
        private boolean mRtl;
        private float mScroll;
        private final float mScrollUnit;
        private byte mStatus = 0;
        private final WeakReference<HorizontalNumberPicker> mView;

        /*
         * Enabled aggressive block sorting
         */
        Marquee(HorizontalNumberPicker horizontalNumberPicker, Layout layout2, boolean bl) {
            float f = 30.0f * horizontalNumberPicker.getContext().getResources().getDisplayMetrics().density / 33.0f;
            this.mScrollUnit = bl ? -f : f;
            this.mView = new WeakReference<HorizontalNumberPicker>(horizontalNumberPicker);
            this.mLayout = new WeakReference<Layout>(layout2);
            this.mRtl = bl;
        }

        private void resetScroll() {
            this.mScroll = 0.0f;
            HorizontalNumberPicker horizontalNumberPicker = (HorizontalNumberPicker)((Object)this.mView.get());
            if (horizontalNumberPicker != null) {
                horizontalNumberPicker.invalidate();
            }
        }

        float getGhostOffset() {
            return this.mGhostOffset;
        }

        float getScroll() {
            return this.mScroll;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    return;
                }
                case 1: {
                    this.mStatus = (byte)2;
                    this.tick();
                    return;
                }
                case 2: {
                    this.tick();
                    return;
                }
                case 3: {
                    if (this.mStatus != 2) return;
                    if (this.mRepeatLimit >= 0) {
                        --this.mRepeatLimit;
                    }
                    this.start(this.mRepeatLimit);
                    return;
                }
            }
        }

        boolean shouldDrawGhost() {
            return this.mStatus == 2 && Math.abs(this.mScroll) > this.mGhostStart;
        }

        /*
         * Enabled aggressive block sorting
         */
        void start(int n) {
            if (n == 0) {
                this.stop();
                return;
            } else {
                this.mRepeatLimit = n;
                HorizontalNumberPicker horizontalNumberPicker = (HorizontalNumberPicker)((Object)this.mView.get());
                Layout layout2 = (Layout)this.mLayout.get();
                if (horizontalNumberPicker == null || layout2 == null) return;
                {
                    this.mStatus = 1;
                    this.mScroll = 0.0f;
                    n = horizontalNumberPicker.mItemWidth;
                    float f = layout2.getLineWidth(0);
                    float f2 = (float)n / 3.0f;
                    this.mGhostStart = f - (float)n + f2;
                    this.mMaxScroll = this.mGhostStart + (float)n;
                    this.mGhostOffset = f + f2;
                    this.mFadeStop = (float)n / 6.0f + f;
                    this.mMaxFadeScroll = this.mGhostStart + f + f;
                    if (this.mRtl) {
                        this.mGhostOffset *= -1.0f;
                    }
                    horizontalNumberPicker.invalidate();
                    this.sendEmptyMessageDelayed(1, 1200L);
                    return;
                }
            }
        }

        void stop() {
            this.mStatus = 0;
            this.removeMessages(1);
            this.removeMessages(3);
            this.removeMessages(2);
            this.resetScroll();
        }

        /*
         * Enabled aggressive block sorting
         */
        void tick() {
            HorizontalNumberPicker horizontalNumberPicker;
            block7: {
                block6: {
                    if (this.mStatus != 2) break block6;
                    this.removeMessages(2);
                    horizontalNumberPicker = (HorizontalNumberPicker)((Object)this.mView.get());
                    Layout layout2 = (Layout)this.mLayout.get();
                    if (horizontalNumberPicker != null && layout2 != null && (horizontalNumberPicker.isFocused() || horizontalNumberPicker.isSelected())) break block7;
                }
                return;
            }
            this.mScroll += this.mScrollUnit;
            if (Math.abs(this.mScroll) > this.mMaxScroll) {
                this.mScroll = this.mMaxScroll;
                if (this.mRtl) {
                    this.mScroll *= -1.0f;
                }
                this.sendEmptyMessageDelayed(3, 1200L);
            } else {
                this.sendEmptyMessageDelayed(2, 33L);
            }
            horizontalNumberPicker.invalidate();
        }
    }

    public static interface OnItemClicked {
        public void onItemClicked(int var1);
    }

    public static interface OnItemSelected {
        public void onItemSelected(int var1);
    }

    private static class PickerTouchHelper
    extends ExploreByTouchHelper {
        private HorizontalNumberPicker mPicker;

        public PickerTouchHelper(HorizontalNumberPicker horizontalNumberPicker) {
            super(horizontalNumberPicker);
            this.mPicker = horizontalNumberPicker;
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            f2 = (float)this.mPicker.mItemWidth + this.mPicker.mDividerSize;
            f = ((float)this.mPicker.getScrollX() + f - (float)this.mPicker.mSideItems * f2) / f2;
            if (f < 0.0f || f > (float)this.mPicker.mValues.length) {
                return Integer.MIN_VALUE;
            }
            return (int)f;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void getVisibleVirtualViews(List<Integer> list) {
            int n;
            int n2;
            float f = (float)this.mPicker.mItemWidth + this.mPicker.mDividerSize;
            float f2 = (float)this.mPicker.getScrollX() - (float)this.mPicker.mSideItems * f;
            int n3 = (int)(f2 / f);
            int n4 = n = this.mPicker.mSideItems * 2 + 1;
            if (f2 % f != 0.0f) {
                n4 = n + 1;
            }
            if (n3 < 0) {
                n = n4 + n3;
                n2 = 0;
            } else {
                n2 = n3;
                n = n4;
                if (n3 + n4 > this.mPicker.mValues.length) {
                    n = this.mPicker.mValues.length - n3;
                    n2 = n3;
                }
            }
            n4 = 0;
            while (n4 < n) {
                list.add(n2 + n4);
                ++n4;
            }
            return;
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            return false;
        }

        @Override
        protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription((CharSequence)String.valueOf(this.mPicker.mValues[n]));
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            float f = (float)this.mPicker.mItemWidth + this.mPicker.mDividerSize;
            float f2 = this.mPicker.getScrollX();
            float f3 = this.mPicker.mSideItems;
            int n2 = (int)((float)n * f - (f2 - f3 * f));
            int n3 = this.mPicker.mItemWidth;
            accessibilityNodeInfoCompat.setContentDescription(String.valueOf(this.mPicker.mValues[n]));
            accessibilityNodeInfoCompat.setBoundsInParent(new Rect(n2, 0, n2 + n3, this.mPicker.getHeight()));
            accessibilityNodeInfoCompat.addAction(16);
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
        private int mSelItem;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSelItem = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "HorizontalPicker.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " selItem=" + this.mSelItem + "}";
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.mSelItem);
        }

    }

}

