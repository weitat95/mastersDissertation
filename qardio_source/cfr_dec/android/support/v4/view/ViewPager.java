/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.Scroller
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager
extends ViewGroup {
    private static final Comparator<ItemInfo> COMPARATOR;
    static final int[] LAYOUT_ATTRS;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId = -1;
    PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private EdgeEffect mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffect mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            @Override
            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public ViewPager(Context context) {
        super(context);
        this.mEndScrollRunnable = new Runnable(){

            @Override
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.initViewPager();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEndScrollRunnable = new /* invalid duplicate definition of identical inner class */;
        this.initViewPager();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculatePageOffsets(ItemInfo itemInfo, int n, ItemInfo itemInfo2) {
        int n2;
        int n3;
        float f;
        int n4;
        int n5;
        float f2;
        float f3;
        block12: {
            block15: {
                block14: {
                    block13: {
                        n2 = this.mAdapter.getCount();
                        n5 = this.getClientWidth();
                        f2 = n5 > 0 ? (float)this.mPageMargin / (float)n5 : 0.0f;
                        if (itemInfo2 == null) break block12;
                        n5 = itemInfo2.position;
                        if (n5 >= itemInfo.position) break block13;
                        n4 = 0;
                        f3 = itemInfo2.offset + itemInfo2.widthFactor + f2;
                        ++n5;
                        break block14;
                    }
                    if (n5 <= itemInfo.position) break block12;
                    n4 = this.mItems.size() - 1;
                    f3 = itemInfo2.offset;
                    --n5;
                    break block15;
                }
                while (n5 <= itemInfo.position && n4 < this.mItems.size()) {
                    itemInfo2 = this.mItems.get(n4);
                    do {
                        f = f3;
                        n3 = n5;
                        if (n5 <= itemInfo2.position) break;
                        f = f3;
                        n3 = n5;
                        if (n4 >= this.mItems.size() - 1) break;
                        itemInfo2 = this.mItems.get(++n4);
                    } while (true);
                    while (n3 < itemInfo2.position) {
                        f += this.mAdapter.getPageWidth(n3) + f2;
                        ++n3;
                    }
                    itemInfo2.offset = f;
                    f3 = f + (itemInfo2.widthFactor + f2);
                    n5 = n3 + 1;
                }
                break block12;
            }
            while (n5 >= itemInfo.position && n4 >= 0) {
                itemInfo2 = this.mItems.get(n4);
                do {
                    f = f3;
                    n3 = n5;
                    if (n5 >= itemInfo2.position) break;
                    f = f3;
                    n3 = n5;
                    if (n4 <= 0) break;
                    itemInfo2 = this.mItems.get(--n4);
                } while (true);
                while (n3 > itemInfo2.position) {
                    f -= this.mAdapter.getPageWidth(n3) + f2;
                    --n3;
                }
                itemInfo2.offset = f3 = f - (itemInfo2.widthFactor + f2);
                n5 = n3 - 1;
            }
        }
        n3 = this.mItems.size();
        f = itemInfo.offset;
        n5 = itemInfo.position - 1;
        f3 = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f3;
        f3 = itemInfo.position == n2 - 1 ? itemInfo.offset + itemInfo.widthFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f3;
        f3 = f;
        for (n4 = n - 1; n4 >= 0; --n4, --n5) {
            itemInfo2 = this.mItems.get(n4);
            while (n5 > itemInfo2.position) {
                f3 -= this.mAdapter.getPageWidth(n5) + f2;
                --n5;
            }
            itemInfo2.offset = f3 -= itemInfo2.widthFactor + f2;
            if (itemInfo2.position != 0) continue;
            this.mFirstOffset = f3;
        }
        f3 = itemInfo.offset + itemInfo.widthFactor + f2;
        n5 = itemInfo.position + 1;
        n4 = n + 1;
        n = n5;
        n5 = n4;
        do {
            if (n5 >= n3) {
                this.mNeedCalculatePageOffsets = false;
                return;
            }
            itemInfo = this.mItems.get(n5);
            while (n < itemInfo.position) {
                f3 += this.mAdapter.getPageWidth(n) + f2;
                ++n;
            }
            if (itemInfo.position == n2 - 1) {
                this.mLastOffset = itemInfo.widthFactor + f3 - 1.0f;
            }
            itemInfo.offset = f3;
            f3 += itemInfo.widthFactor + f2;
            ++n5;
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void completeScroll(boolean bl) {
        int n;
        int n2 = 1;
        int n3 = this.mScrollState == 2 ? 1 : 0;
        if (n3 != 0) {
            this.setScrollingCacheEnabled(false);
            if (this.mScroller.isFinished()) {
                n2 = 0;
            }
            if (n2 != 0) {
                this.mScroller.abortAnimation();
                n2 = this.getScrollX();
                n = this.getScrollY();
                int n4 = this.mScroller.getCurrX();
                int n5 = this.mScroller.getCurrY();
                if (n2 != n4 || n != n5) {
                    this.scrollTo(n4, n5);
                    if (n4 != n2) {
                        this.pageScrolled(n4);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        n = 0;
        n2 = n3;
        for (n3 = n; n3 < this.mItems.size(); ++n3) {
            ItemInfo itemInfo = this.mItems.get(n3);
            if (!itemInfo.scrolling) continue;
            n2 = 1;
            itemInfo.scrolling = false;
        }
        if (n2 != 0) {
            if (!bl) {
                this.mEndScrollRunnable.run();
                return;
            }
            ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int determineTargetPage(int n, float f, int n2, int n3) {
        if (Math.abs(n3) > this.mFlingDistance && Math.abs(n2) > this.mMinimumVelocity) {
            if (n2 <= 0) {
                ++n;
            }
        } else {
            float f2 = n >= this.mCurItem ? 0.4f : 0.6f;
            n += (int)(f + f2);
        }
        n2 = n;
        if (this.mItems.size() <= 0) return n2;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        return Math.max(itemInfo.position, Math.min(n, itemInfo2.position));
    }

    private void dispatchOnPageScrolled(int n, float f, int n2) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(n, f, n2);
        }
        if (this.mOnPageChangeListeners != null) {
            int n3 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n3; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageScrolled(n, f, n2);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(n, f, n2);
        }
    }

    private void dispatchOnPageSelected(int n) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(n);
        }
        if (this.mOnPageChangeListeners != null) {
            int n2 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n2; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageSelected(n);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(n);
        }
    }

    private void dispatchOnScrollStateChanged(int n) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(n);
        }
        if (this.mOnPageChangeListeners != null) {
            int n2 = this.mOnPageChangeListeners.size();
            for (int i = 0; i < n2; ++i) {
                OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageScrollStateChanged(n);
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableLayers(boolean bl) {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = bl ? this.mPageTransformerLayerType : 0;
            this.getChildAt(n2).setLayerType(n3, null);
            ++n2;
        }
        return;
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        } else {
            rect2.left = view.getLeft();
            rect2.right = view.getRight();
            rect2.top = view.getTop();
            rect2.bottom = view.getBottom();
            for (rect = view.getParent(); rect instanceof ViewGroup && rect != this; rect2.left += rect.getLeft(), rect2.right += rect.getRight(), rect2.top += rect.getTop(), rect2.bottom += rect.getBottom(), rect = rect.getParent()) {
                rect = (ViewGroup)rect;
            }
        }
        return rect2;
    }

    private int getClientWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ItemInfo infoForCurrentScrollPosition() {
        float f = 0.0f;
        int n = this.getClientWidth();
        float f2 = n > 0 ? (float)this.getScrollX() / (float)n : 0.0f;
        if (n > 0) {
            f = (float)this.mPageMargin / (float)n;
        }
        int n2 = -1;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean bl = true;
        ItemInfo itemInfo = null;
        n = 0;
        do {
            ItemInfo itemInfo2 = itemInfo;
            if (n >= this.mItems.size()) return itemInfo2;
            itemInfo2 = this.mItems.get(n);
            int n3 = n;
            ItemInfo itemInfo3 = itemInfo2;
            if (!bl) {
                n3 = n;
                itemInfo3 = itemInfo2;
                if (itemInfo2.position != n2 + 1) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f3 + f4 + f;
                    itemInfo3.position = n2 + 1;
                    itemInfo3.widthFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                    n3 = n - 1;
                }
            }
            f3 = itemInfo3.offset;
            f4 = itemInfo3.widthFactor;
            if (!bl) {
                itemInfo2 = itemInfo;
                if (!(f2 >= f3)) return itemInfo2;
            }
            if (f2 < f4 + f3 + f) return itemInfo3;
            if (n3 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            bl = false;
            n2 = itemInfo3.position;
            f4 = itemInfo3.widthFactor;
            n = n3 + 1;
            itemInfo = itemInfo3;
        } while (true);
    }

    private static boolean isDecorView(View view) {
        return view.getClass().getAnnotation(DecorView.class) != null;
    }

    private boolean isGutterDrag(float f, float f2) {
        return f < (float)this.mGutterSize && f2 > 0.0f || f > (float)(this.getWidth() - this.mGutterSize) && f2 < 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(n);
            this.mActivePointerId = motionEvent.getPointerId(n);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean pageScrolled(int n) {
        block4: {
            block6: {
                block5: {
                    if (this.mItems.size() != 0) break block4;
                    if (this.mFirstLayout) break block5;
                    this.mCalledSuper = false;
                    this.onPageScrolled(0, 0.0f, 0);
                    if (!this.mCalledSuper) break block6;
                }
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo itemInfo = this.infoForCurrentScrollPosition();
        int n2 = this.getClientWidth();
        int n3 = this.mPageMargin;
        float f = (float)this.mPageMargin / (float)n2;
        int n4 = itemInfo.position;
        f = ((float)n / (float)n2 - itemInfo.offset) / (itemInfo.widthFactor + f);
        n = (int)((float)(n2 + n3) * f);
        this.mCalledSuper = false;
        this.onPageScrolled(n4, f, n);
        if (!this.mCalledSuper) {
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performDrag(float f) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        float f2 = this.mLastMotionX;
        this.mLastMotionX = f;
        float f3 = (float)this.getScrollX() + (f2 - f);
        int n = this.getClientWidth();
        f = (float)n * this.mFirstOffset;
        f2 = (float)n * this.mLastOffset;
        boolean bl4 = true;
        boolean bl5 = true;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            bl4 = false;
            f = itemInfo.offset * (float)n;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            bl5 = false;
            f2 = itemInfo2.offset * (float)n;
        }
        if (f3 < f) {
            if (bl4) {
                this.mLeftEdge.onPull(Math.abs(f - f3) / (float)n);
                bl3 = true;
            }
        } else {
            bl3 = bl;
            f = f3;
            if (f3 > f2) {
                bl3 = bl2;
                if (bl5) {
                    this.mRightEdge.onPull(Math.abs(f3 - f2) / (float)n);
                    bl3 = true;
                }
                f = f2;
            }
        }
        this.mLastMotionX += f - (float)((int)f);
        this.scrollTo((int)f, this.getScrollY());
        this.pageScrolled((int)f);
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recomputeScrollPosition(int n, int n2, int n3, int n4) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            if (this.mScroller.isFinished()) {
                int n5 = this.getPaddingLeft();
                int n6 = this.getPaddingRight();
                int n7 = this.getPaddingLeft();
                int n8 = this.getPaddingRight();
                float f = (float)this.getScrollX() / (float)(n2 - n7 - n8 + n4);
                this.scrollTo((int)((float)(n - n5 - n6 + n3) * f), this.getScrollY());
                return;
            }
            this.mScroller.setFinalX(this.getCurrentItem() * this.getClientWidth());
            return;
        } else {
            ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
            float f = itemInfo != null ? Math.min(itemInfo.offset, this.mLastOffset) : 0.0f;
            if ((n = (int)((float)(n - this.getPaddingLeft() - this.getPaddingRight()) * f)) == this.getScrollX()) return;
            {
                this.completeScroll(false);
                this.scrollTo(n, this.getScrollY());
                return;
            }
        }
    }

    private void removeNonDecorViews() {
        int n = 0;
        while (n < this.getChildCount()) {
            int n2 = n;
            if (!((LayoutParams)this.getChildAt((int)n).getLayoutParams()).isDecor) {
                this.removeViewAt(n);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        this.endDrag();
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        return this.mLeftEdge.isFinished() || this.mRightEdge.isFinished();
    }

    private void scrollToItem(int n, boolean bl, int n2, boolean bl2) {
        ItemInfo itemInfo = this.infoForPosition(n);
        int n3 = 0;
        if (itemInfo != null) {
            n3 = (int)((float)this.getClientWidth() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset)));
        }
        if (bl) {
            this.smoothScrollTo(n3, 0, n2);
            if (bl2) {
                this.dispatchOnPageSelected(n);
            }
            return;
        }
        if (bl2) {
            this.dispatchOnPageSelected(n);
        }
        this.completeScroll(false);
        this.scrollTo(n3, 0);
        this.pageScrolled(n3);
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled != bl) {
            this.mScrollingCacheEnabled = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                this.mDrawingOrderedChildren.add(view);
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3 = arrayList.size();
        int n4 = this.getDescendantFocusability();
        if (n4 != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        if (n4 == 262144 && n3 != arrayList.size() || !this.isFocusable() || (n2 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode() || arrayList == null) {
            return;
        }
        arrayList.add((View)this);
    }

    ItemInfo addNewItem(int n, int n2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n;
        itemInfo.object = this.mAdapter.instantiateItem(this, n);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(n);
        if (n2 < 0 || n2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
            return itemInfo;
        }
        this.mItems.add(n2, itemInfo);
        return itemInfo;
    }

    public void addOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>();
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
            view.addTouchables(arrayList);
        }
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        layoutParams.isDecor |= ViewPager.isDecorView(view);
        if (this.mInLayout) {
            if (layoutParams != null && layoutParams.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams.needsMeasure = true;
            this.addViewInLayout(view, n, layoutParams2);
            return;
        }
        super.addView(view, n, layoutParams2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean arrowScroll(int var1_1) {
        block16: {
            block15: {
                block14: {
                    var6_2 = this.findFocus();
                    if (var6_2 != this) break block14;
                    var5_3 = null;
                    break block15;
                }
                var5_3 = var6_2;
                if (var6_2 != null) break block16;
            }
lbl9:
            // 3 sources
            do {
                var4_6 = false;
                var6_2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, var5_3, var1_1);
                if (var6_2 != null && var6_2 != var5_3) {
                    if (var1_1 == 17) {
                        var2_4 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_2).left;
                        var3_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_3).left;
                        var4_6 = var5_3 != null && var2_4 >= var3_5 ? this.pageLeft() : var6_2.requestFocus();
                    } else if (var1_1 == 66) {
                        var2_4 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_2).left;
                        var3_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_3).left;
                        var4_6 = var5_3 != null && var2_4 <= var3_5 ? this.pageRight() : var6_2.requestFocus();
                    }
                } else if (var1_1 == 17 || var1_1 == 1) {
                    var4_6 = this.pageLeft();
                } else if (var1_1 == 66 || var1_1 == 2) {
                    var4_6 = this.pageRight();
                }
                if (var4_6 == false) return var4_6;
                this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1_1));
                return var4_6;
                break;
            } while (true);
        }
        var3_5 = 0;
        var5_3 = var6_2.getParent();
        do {
            block18: {
                block17: {
                    var2_4 = var3_5;
                    if (!(var5_3 instanceof ViewGroup)) break block17;
                    if (var5_3 != this) break block18;
                    var2_4 = 1;
                }
                var5_3 = var6_2;
                if (var2_4 != 0) ** GOTO lbl9
                var7_7 = new StringBuilder();
                var7_7.append(var6_2.getClass().getSimpleName());
                var5_3 = var6_2.getParent();
                while (var5_3 instanceof ViewGroup) {
                    var7_7.append(" => ").append(var5_3.getClass().getSimpleName());
                    var5_3 = var5_3.getParent();
                }
                break;
            }
            var5_3 = var5_3.getParent();
        } while (true);
        Log.e((String)"ViewPager", (String)("arrowScroll tried to find focus based on non-child current focused view " + var7_7.toString()));
        var5_3 = null;
        ** while (true)
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                View view2 = viewGroup.getChildAt(i);
                if (n2 + n4 < view2.getLeft() || n2 + n4 >= view2.getRight() || n3 + n5 < view2.getTop() || n3 + n5 >= view2.getBottom() || !this.canScroll(view2, true, n, n2 + n4 - view2.getLeft(), n3 + n5 - view2.getTop())) continue;
                return true;
            }
        }
        return bl && view.canScrollHorizontally(-n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean canScrollHorizontally(int n) {
        boolean bl = true;
        boolean bl2 = true;
        if (this.mAdapter == null) {
            return false;
        }
        int n2 = this.getClientWidth();
        int n3 = this.getScrollX();
        if (n < 0) {
            if (n3 <= (int)((float)n2 * this.mFirstOffset)) return false;
            return bl2;
        }
        if (n <= 0) return false;
        if (n3 >= (int)((float)n2 * this.mLastOffset)) return false;
        return bl;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
                if (!this.pageScrolled(n3)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(0, n4);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void dataSetChanged() {
        int n;
        this.mExpectedAdapterCount = n = this.mAdapter.getCount();
        int n2 = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < n ? 1 : 0;
        int n3 = this.mCurItem;
        int n4 = 0;
        int n5 = 0;
        while (n5 < this.mItems.size()) {
            int n6;
            int n7;
            int n8;
            ItemInfo itemInfo = this.mItems.get(n5);
            int n9 = this.mAdapter.getItemPosition(itemInfo.object);
            if (n9 == -1) {
                n7 = n3;
                n8 = n4;
                n6 = n5;
            } else if (n9 == -2) {
                this.mItems.remove(n5);
                n9 = n5 - 1;
                n5 = n4;
                if (n4 == 0) {
                    this.mAdapter.startUpdate(this);
                    n5 = 1;
                }
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                n2 = 1;
                n6 = n9;
                n8 = n5;
                n7 = n3;
                if (this.mCurItem == itemInfo.position) {
                    n7 = Math.max(0, Math.min(this.mCurItem, n - 1));
                    n2 = 1;
                    n6 = n9;
                    n8 = n5;
                }
            } else {
                n6 = n5;
                n8 = n4;
                n7 = n3;
                if (itemInfo.position != n9) {
                    if (itemInfo.position == this.mCurItem) {
                        n3 = n9;
                    }
                    itemInfo.position = n9;
                    n2 = 1;
                    n6 = n5;
                    n8 = n4;
                    n7 = n3;
                }
            }
            n5 = n6 + 1;
            n4 = n8;
            n3 = n7;
        }
        if (n4 != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (n2 != 0) {
            n4 = this.getChildCount();
            for (n2 = 0; n2 < n4; ++n2) {
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(n2).getLayoutParams();
                if (layoutParams.isDecor) continue;
                layoutParams.widthFactor = 0.0f;
            }
            this.setCurrentItemInternal(n3, false, true);
            this.requestLayout();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            return true;
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n = 0;
        int n2 = 0;
        int n3 = this.getOverScrollMode();
        if (n3 == 0 || n3 == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1) {
            if (!this.mLeftEdge.isFinished()) {
                n = canvas.save();
                n2 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                n3 = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float)(-n2 + this.getPaddingTop()), this.mFirstOffset * (float)n3);
                this.mLeftEdge.setSize(n2, n3);
                n2 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(n);
            }
            n = n2;
            if (!this.mRightEdge.isFinished()) {
                n3 = canvas.save();
                n = this.getWidth();
                int n4 = this.getHeight();
                int n5 = this.getPaddingTop();
                int n6 = this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float)(-this.getPaddingTop()), -(this.mLastOffset + 1.0f) * (float)n);
                this.mRightEdge.setSize(n4 - n5 - n6, n);
                n = n2 | this.mRightEdge.draw(canvas);
                canvas.restoreToCount(n3);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (n != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mMarginDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            drawable2.setState(this.getDrawableState());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) return false;
        {
            switch (keyEvent.getKeyCode()) {
                default: {
                    return false;
                }
                case 21: {
                    if (!keyEvent.hasModifiers(2)) return this.arrowScroll(17);
                    return this.pageLeft();
                }
                case 22: {
                    if (!keyEvent.hasModifiers(2)) return this.arrowScroll(66);
                    return this.pageRight();
                }
                case 61: {
                    if (keyEvent.hasNoModifiers()) {
                        return this.arrowScroll(2);
                    }
                    if (!keyEvent.hasModifiers(1)) return false;
                    return this.arrowScroll(1);
                }
            }
        }
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int getChildDrawingOrder(int n, int n2) {
        if (this.mDrawingOrder == 2) {
            n = n - 1 - n2;
            do {
                return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n).getLayoutParams()).childIndex;
                break;
            } while (true);
        }
        n = n2;
        return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent == null || !(viewParent instanceof View)) {
                return null;
            }
            view = (View)viewParent;
        }
        return this.infoForChild(view);
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    ItemInfo infoForPosition(int n) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.position != n) continue;
            return itemInfo;
        }
        return null;
    }

    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int)(400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int)(25.0f * f);
        this.mCloseEnough = (int)(2.0f * f);
        this.mDefaultGutterSize = (int)(16.0f * f);
        ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){
            private final Rect mTempRect = new Rect();

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                if (((WindowInsetsCompat)(object = ViewCompat.onApplyWindowInsets((View)object, windowInsetsCompat))).isConsumed()) {
                    return object;
                }
                windowInsetsCompat = this.mTempRect;
                ((Rect)windowInsetsCompat).left = ((WindowInsetsCompat)object).getSystemWindowInsetLeft();
                ((Rect)windowInsetsCompat).top = ((WindowInsetsCompat)object).getSystemWindowInsetTop();
                ((Rect)windowInsetsCompat).right = ((WindowInsetsCompat)object).getSystemWindowInsetRight();
                ((Rect)windowInsetsCompat).bottom = ((WindowInsetsCompat)object).getSystemWindowInsetBottom();
                int n = ViewPager.this.getChildCount();
                for (int i = 0; i < n; ++i) {
                    WindowInsetsCompat windowInsetsCompat2 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), (WindowInsetsCompat)object);
                    ((Rect)windowInsetsCompat).left = Math.min(windowInsetsCompat2.getSystemWindowInsetLeft(), ((Rect)windowInsetsCompat).left);
                    ((Rect)windowInsetsCompat).top = Math.min(windowInsetsCompat2.getSystemWindowInsetTop(), ((Rect)windowInsetsCompat).top);
                    ((Rect)windowInsetsCompat).right = Math.min(windowInsetsCompat2.getSystemWindowInsetRight(), ((Rect)windowInsetsCompat).right);
                    ((Rect)windowInsetsCompat).bottom = Math.min(windowInsetsCompat2.getSystemWindowInsetBottom(), ((Rect)windowInsetsCompat).bottom);
                }
                return ((WindowInsetsCompat)object).replaceSystemWindowInsets(((Rect)windowInsetsCompat).left, ((Rect)windowInsetsCompat).top, ((Rect)windowInsetsCompat).right, ((Rect)windowInsetsCompat).bottom);
            }
        });
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        if (this.mScroller != null && !this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int n = this.getScrollX();
            int n2 = this.getWidth();
            float f = (float)this.mPageMargin / (float)n2;
            int n3 = 0;
            Object object = this.mItems.get(0);
            float f2 = ((ItemInfo)object).offset;
            int n4 = this.mItems.size();
            int n5 = this.mItems.get((int)(n4 - 1)).position;
            for (int i = ((ItemInfo)object).position; i < n5; ++i) {
                float f3;
                while (i > ((ItemInfo)object).position && n3 < n4) {
                    object = this.mItems;
                    object = (ItemInfo)((ArrayList)object).get(++n3);
                }
                if (i == ((ItemInfo)object).position) {
                    f3 = (((ItemInfo)object).offset + ((ItemInfo)object).widthFactor) * (float)n2;
                    f2 = ((ItemInfo)object).offset + ((ItemInfo)object).widthFactor + f;
                } else {
                    float f4 = this.mAdapter.getPageWidth(i);
                    f3 = (f2 + f4) * (float)n2;
                    f2 += f4 + f;
                }
                if ((float)this.mPageMargin + f3 > (float)n) {
                    this.mMarginDrawable.setBounds(Math.round(f3), this.mTopPageBounds, Math.round((float)this.mPageMargin + f3), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f3 > (float)(n + n2)) break;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction() & 0xFF;
        if (n == 3 || n == 1) {
            this.resetTouch();
            return false;
        }
        if (n != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (n) {
            case 2: {
                n = this.mActivePointerId;
                if (n == -1) break;
                n = motionEvent.findPointerIndex(n);
                float f = motionEvent.getX(n);
                float f2 = f - this.mLastMotionX;
                float f3 = Math.abs(f2);
                float f4 = motionEvent.getY(n);
                float f5 = Math.abs(f4 - this.mInitialMotionY);
                if (f2 != 0.0f && !this.isGutterDrag(this.mLastMotionX, f2) && this.canScroll((View)this, false, (int)f2, (int)f, (int)f4)) {
                    this.mLastMotionX = f;
                    this.mLastMotionY = f4;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                if (f3 > (float)this.mTouchSlop && 0.5f * f3 > f5) {
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                    f2 = f2 > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                    this.mLastMotionX = f2;
                    this.mLastMotionY = f4;
                    this.setScrollingCacheEnabled(true);
                } else if (f5 > (float)this.mTouchSlop) {
                    this.mIsUnableToDrag = true;
                }
                if (!this.mIsBeingDragged || !this.performDrag(f)) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 0: {
                float f;
                this.mInitialMotionX = f = motionEvent.getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f = motionEvent.getY();
                this.mLastMotionY = f;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mIsScrollStarted = true;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                    break;
                }
                this.completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                break;
            }
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams;
        int n5;
        View view;
        int n6 = this.getChildCount();
        int n7 = n3 - n;
        int n8 = n4 - n2;
        n2 = this.getPaddingLeft();
        n = this.getPaddingTop();
        int n9 = this.getPaddingRight();
        n4 = this.getPaddingBottom();
        int n10 = this.getScrollX();
        int n11 = 0;
        for (int i = 0; i < n6; ++i) {
            view = this.getChildAt(i);
            int n12 = n11;
            int n13 = n4;
            n5 = n2;
            int n14 = n9;
            n3 = n;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n12 = n11;
                n13 = n4;
                n5 = n2;
                n14 = n9;
                n3 = n;
                if (layoutParams.isDecor) {
                    n3 = layoutParams.gravity;
                    n14 = layoutParams.gravity;
                    switch (n3 & 7) {
                        default: {
                            n3 = n2;
                            n5 = n2;
                            break;
                        }
                        case 3: {
                            n3 = n2;
                            n5 = n2 + view.getMeasuredWidth();
                            break;
                        }
                        case 1: {
                            n3 = Math.max((n7 - view.getMeasuredWidth()) / 2, n2);
                            n5 = n2;
                            break;
                        }
                        case 5: {
                            n3 = n7 - n9 - view.getMeasuredWidth();
                            n9 += view.getMeasuredWidth();
                            n5 = n2;
                        }
                    }
                    switch (n14 & 0x70) {
                        default: {
                            n2 = n;
                            break;
                        }
                        case 48: {
                            n2 = n;
                            n += view.getMeasuredHeight();
                            break;
                        }
                        case 16: {
                            n2 = Math.max((n8 - view.getMeasuredHeight()) / 2, n);
                            break;
                        }
                        case 80: {
                            n2 = n8 - n4 - view.getMeasuredHeight();
                            n4 += view.getMeasuredHeight();
                        }
                    }
                    view.layout(n3 += n10, n2, view.getMeasuredWidth() + n3, view.getMeasuredHeight() + n2);
                    n12 = n11 + 1;
                    n3 = n;
                    n14 = n9;
                    n13 = n4;
                }
            }
            n11 = n12;
            n4 = n13;
            n2 = n5;
            n9 = n14;
            n = n3;
        }
        n9 = n7 - n2 - n9;
        for (n3 = 0; n3 < n6; ++n3) {
            ItemInfo itemInfo;
            view = this.getChildAt(n3);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (itemInfo = this.infoForChild(view)) == null) continue;
            n5 = n2 + (int)((float)n9 * itemInfo.offset);
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n9 * layoutParams.widthFactor)), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n8 - n - n4), (int)1073741824));
            }
            view.layout(n5, n, view.getMeasuredWidth() + n5, view.getMeasuredHeight() + n);
        }
        this.mTopPageBounds = n;
        this.mBottomPageBounds = n8 - n4;
        this.mDecorChildCount = n11;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        View view;
        LayoutParams layoutParams;
        int n3;
        this.setMeasuredDimension(ViewPager.getDefaultSize((int)0, (int)n), ViewPager.getDefaultSize((int)0, (int)n2));
        n = this.getMeasuredWidth();
        this.mGutterSize = Math.min(n / 10, this.mDefaultGutterSize);
        n = n - this.getPaddingLeft() - this.getPaddingRight();
        n2 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n4 = this.getChildCount();
        for (int i = 0; i < n4; ++i) {
            view = this.getChildAt(i);
            n3 = n2;
            int n5 = n;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = n2;
                n5 = n;
                if (layoutParams != null) {
                    n3 = n2;
                    n5 = n;
                    if (layoutParams.isDecor) {
                        int n6;
                        n5 = layoutParams.gravity & 7;
                        int n7 = layoutParams.gravity & 0x70;
                        int n8 = Integer.MIN_VALUE;
                        n3 = Integer.MIN_VALUE;
                        n7 = n7 == 48 || n7 == 80 ? 1 : 0;
                        boolean bl = n5 == 3 || n5 == 5;
                        if (n7 != 0) {
                            n5 = 1073741824;
                        } else {
                            n5 = n8;
                            if (bl) {
                                n3 = 1073741824;
                                n5 = n8;
                            }
                        }
                        int n9 = n;
                        n8 = n2;
                        int n10 = n9;
                        if (layoutParams.width != -2) {
                            n5 = n6 = 1073741824;
                            n10 = n9;
                            if (layoutParams.width != -1) {
                                n10 = layoutParams.width;
                                n5 = n6;
                            }
                        }
                        n9 = n8;
                        if (layoutParams.height != -2) {
                            n3 = n6 = 1073741824;
                            n9 = n8;
                            if (layoutParams.height != -1) {
                                n9 = layoutParams.height;
                                n3 = n6;
                            }
                        }
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n10, (int)n5), View.MeasureSpec.makeMeasureSpec((int)n9, (int)n3));
                        if (n7 != 0) {
                            n3 = n2 - view.getMeasuredHeight();
                            n5 = n;
                        } else {
                            n3 = n2;
                            n5 = n;
                            if (bl) {
                                n5 = n - view.getMeasuredWidth();
                                n3 = n2;
                            }
                        }
                    }
                }
            }
            n2 = n3;
            n = n5;
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        n3 = this.getChildCount();
        n2 = 0;
        while (n2 < n3) {
            view = this.getChildAt(n2);
            if (!(view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) != null && layoutParams.isDecor)) {
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n * layoutParams.widthFactor)), (int)1073741824), this.mChildHeightMeasureSpec);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPageScrolled(int n, float f, int n2) {
        int n3;
        View view;
        if (this.mDecorChildCount > 0) {
            int n4 = this.getScrollX();
            n3 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = this.getWidth();
            int n7 = this.getChildCount();
            for (int i = 0; i < n7; ++i) {
                int n8;
                int n9;
                view = this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.isDecor) {
                    n9 = n5;
                    n8 = n3;
                } else {
                    switch (layoutParams.gravity & 7) {
                        default: {
                            n9 = n3;
                            break;
                        }
                        case 3: {
                            n9 = n3;
                            n3 += view.getWidth();
                            break;
                        }
                        case 1: {
                            n9 = Math.max((n6 - view.getMeasuredWidth()) / 2, n3);
                            break;
                        }
                        case 5: {
                            n9 = n6 - n5 - view.getMeasuredWidth();
                            n5 += view.getMeasuredWidth();
                        }
                    }
                    int n10 = n9 + n4 - view.getLeft();
                    n8 = n3;
                    n9 = n5;
                    if (n10 != 0) {
                        view.offsetLeftAndRight(n10);
                        n8 = n3;
                        n9 = n5;
                    }
                }
                n3 = n8;
                n5 = n9;
            }
        }
        this.dispatchOnPageScrolled(n, f, n2);
        if (this.mPageTransformer != null) {
            n2 = this.getScrollX();
            n3 = this.getChildCount();
            for (n = 0; n < n3; ++n) {
                view = this.getChildAt(n);
                if (((LayoutParams)view.getLayoutParams()).isDecor) continue;
                f = (float)(view.getLeft() - n2) / (float)this.getClientWidth();
                this.mPageTransformer.transformPage(view, f);
            }
        }
        this.mCalledSuper = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3;
        int n4 = this.getChildCount();
        if ((n & 2) != 0) {
            n2 = 0;
            n3 = 1;
        } else {
            n2 = n4 - 1;
            n3 = -1;
            n4 = -1;
        }
        while (n2 != n4) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n2);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n, rect)) {
                return true;
            }
            n2 += n3;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(parcelable.adapterState, parcelable.loader);
            this.setCurrentItemInternal(parcelable.position, false, true);
            return;
        }
        this.mRestoredCurItem = parcelable.position;
        this.mRestoredAdapterState = parcelable.adapterState;
        this.mRestoredClassLoader = parcelable.loader;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            this.recomputeScrollPosition(n, n3, this.mPageMargin, this.mPageMargin);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        if (this.mFakeDragging) {
            return true;
        }
        if (var1_1.getAction() == 0 && var1_1.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null) return false;
        if (this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(var1_1);
        var6_2 = var1_1.getAction();
        var9_4 = var10_3 = false;
        switch (var6_2 & 255) {
            default: {
                var9_4 = var10_3;
                break;
            }
            case 0: {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                this.populate();
                this.mInitialMotionX = var2_5 = var1_1.getX();
                this.mLastMotionX = var2_5;
                this.mInitialMotionY = var2_5 = var1_1.getY();
                this.mLastMotionY = var2_5;
                this.mActivePointerId = var1_1.getPointerId(0);
                var9_4 = var10_3;
                break;
            }
            case 2: {
                if (!this.mIsBeingDragged) {
                    var6_2 = var1_1.findPointerIndex(this.mActivePointerId);
                    if (var6_2 == -1) {
                        var9_4 = this.resetTouch();
                        break;
                    }
                    var2_6 = var1_1.getX(var6_2);
                    var4_8 = Math.abs(var2_6 - this.mLastMotionX);
                    var3_9 = var1_1.getY(var6_2);
                    var5_10 = Math.abs(var3_9 - this.mLastMotionY);
                    if (var4_8 > (float)this.mTouchSlop && var4_8 > var5_10) {
                        this.mIsBeingDragged = true;
                        this.requestParentDisallowInterceptTouchEvent(true);
                        var2_6 = var2_6 - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                        this.mLastMotionX = var2_6;
                        this.mLastMotionY = var3_9;
                        this.setScrollState(1);
                        this.setScrollingCacheEnabled(true);
                        var11_11 = this.getParent();
                        if (var11_11 != null) {
                            var11_11.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                var9_4 = var10_3;
                if (this.mIsBeingDragged) {
                    var9_4 = false | this.performDrag(var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId)));
                    break;
                }
                ** GOTO lbl81
            }
            case 1: {
                var9_4 = var10_3;
                if (this.mIsBeingDragged) {
                    var11_12 = this.mVelocityTracker;
                    var11_12.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    var6_2 = (int)var11_12.getXVelocity(this.mActivePointerId);
                    this.mPopulatePending = true;
                    var7_14 = this.getClientWidth();
                    var8_15 = this.getScrollX();
                    var11_13 = this.infoForCurrentScrollPosition();
                    var2_7 = (float)this.mPageMargin / (float)var7_14;
                    this.setCurrentItemInternal(this.determineTargetPage(var11_13.position, ((float)var8_15 / (float)var7_14 - var11_13.offset) / (var11_13.widthFactor + var2_7), var6_2, (int)(var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, var6_2);
                    var9_4 = this.resetTouch();
                    break;
                }
                ** GOTO lbl81
            }
            case 3: {
                var9_4 = var10_3;
                if (this.mIsBeingDragged) {
                    this.scrollToItem(this.mCurItem, true, 0, false);
                    var9_4 = this.resetTouch();
                    break;
                }
                ** GOTO lbl81
            }
            case 5: {
                var6_2 = var1_1.getActionIndex();
                this.mLastMotionX = var1_1.getX(var6_2);
                this.mActivePointerId = var1_1.getPointerId(var6_2);
                var9_4 = var10_3;
            }
lbl81:
            // 5 sources
            case 4: {
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(var1_1);
                this.mLastMotionX = var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId));
                var9_4 = var10_3;
            }
        }
        if (var9_4 == false) return true;
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    boolean pageLeft() {
        if (this.mCurItem > 0) {
            this.setCurrentItem(this.mCurItem - 1, true);
            return true;
        }
        return false;
    }

    boolean pageRight() {
        if (this.mAdapter != null && this.mCurItem < this.mAdapter.getCount() - 1) {
            this.setCurrentItem(this.mCurItem + 1, true);
            return true;
        }
        return false;
    }

    void populate() {
        this.populate(this.mCurItem);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void populate(int n) {
        ItemInfo itemInfo;
        void var13_54;
        Object object;
        int n2;
        void var13_23;
        block50: {
            int n3;
            block51: {
                float f;
                int n4;
                void var13_15;
                int n5;
                ItemInfo itemInfo2;
                float f2;
                object = null;
                if (this.mCurItem != n) {
                    object = this.infoForPosition(this.mCurItem);
                    this.mCurItem = n;
                }
                if (this.mAdapter == null) {
                    this.sortChildDrawingOrder();
                    return;
                }
                if (this.mPopulatePending) {
                    this.sortChildDrawingOrder();
                    return;
                }
                if (this.getWindowToken() == null) return;
                this.mAdapter.startUpdate(this);
                n = this.mOffscreenPageLimit;
                int n6 = Math.max(0, this.mCurItem - n);
                int n7 = this.mAdapter.getCount();
                int n8 = Math.min(n7 - 1, this.mCurItem + n);
                if (n7 != this.mExpectedAdapterCount) {
                    void var13_7;
                    try {
                        String string2 = this.getResources().getResourceName(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n7 + " Pager id: " + (String)var13_7 + " Pager class: " + ((Object)((Object)this)).getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        String string3 = Integer.toHexString(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n7 + " Pager id: " + (String)var13_7 + " Pager class: " + ((Object)((Object)this)).getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                }
                itemInfo = null;
                n = 0;
                do {
                    block53: {
                        void var13_13;
                        block52: {
                            ItemInfo itemInfo3 = itemInfo;
                            if (n >= this.mItems.size()) break block52;
                            itemInfo2 = this.mItems.get(n);
                            if (itemInfo2.position < this.mCurItem) break block53;
                            ItemInfo itemInfo4 = itemInfo;
                            if (itemInfo2.position == this.mCurItem) {
                                ItemInfo itemInfo5 = itemInfo2;
                            }
                        }
                        itemInfo = var13_13;
                        if (var13_13 == null) {
                            itemInfo = var13_13;
                            if (n7 > 0) {
                                itemInfo = this.addNewItem(this.mCurItem, n);
                            }
                        }
                        if (itemInfo == null) break block50;
                        f = 0.0f;
                        n4 = n - 1;
                        if (n4 >= 0) {
                            ItemInfo itemInfo6 = this.mItems.get(n4);
                            break;
                        }
                        Object var13_26 = null;
                        break;
                    }
                    ++n;
                } while (true);
                float f3 = (n5 = this.getClientWidth()) <= 0 ? 0.0f : 2.0f - itemInfo.widthFactor + (float)this.getPaddingLeft() / (float)n5;
                int n9 = this.mCurItem - 1;
                itemInfo2 = var13_15;
                n3 = n;
                do {
                    void var13_30;
                    block57: {
                        block55: {
                            block56: {
                                block54: {
                                    if (n9 < 0) break block54;
                                    if (!(f >= f3) || n9 >= n6) break block55;
                                    if (itemInfo2 != null) break block56;
                                }
                                f = itemInfo.widthFactor;
                                n9 = n3 + 1;
                                if (f < 2.0f) {
                                    void var13_18;
                                    if (n9 < this.mItems.size()) {
                                        ItemInfo itemInfo7 = this.mItems.get(n9);
                                    } else {
                                        Object var13_38 = null;
                                    }
                                    f3 = n5 <= 0 ? 0.0f : (float)this.getPaddingRight() / (float)n5 + 2.0f;
                                    itemInfo2 = var13_18;
                                    break;
                                }
                                break block51;
                            }
                            n = n3;
                            f2 = f;
                            ItemInfo itemInfo8 = itemInfo2;
                            n2 = n4;
                            if (n9 == itemInfo2.position) {
                                n = n3;
                                f2 = f;
                                ItemInfo itemInfo9 = itemInfo2;
                                n2 = n4;
                                if (!itemInfo2.scrolling) {
                                    this.mItems.remove(n4);
                                    this.mAdapter.destroyItem(this, n9, itemInfo2.object);
                                    n2 = n4 - 1;
                                    n = n3 - 1;
                                    if (n2 >= 0) {
                                        ItemInfo itemInfo10 = this.mItems.get(n2);
                                        f2 = f;
                                    } else {
                                        Object var13_31 = null;
                                        f2 = f;
                                    }
                                }
                            }
                            break block57;
                        }
                        if (itemInfo2 != null && n9 == itemInfo2.position) {
                            f2 = f + itemInfo2.widthFactor;
                            n2 = n4 - 1;
                            if (n2 >= 0) {
                                ItemInfo itemInfo11 = this.mItems.get(n2);
                            } else {
                                Object var13_34 = null;
                            }
                            n = n3;
                        } else {
                            f2 = f + this.addNewItem((int)n9, (int)(n4 + 1)).widthFactor;
                            n = n3 + 1;
                            if (n4 >= 0) {
                                ItemInfo itemInfo12 = this.mItems.get(n4);
                            } else {
                                Object var13_37 = null;
                            }
                            n2 = n4;
                        }
                    }
                    --n9;
                    n3 = n;
                    f = f2;
                    itemInfo2 = var13_30;
                    n4 = n2;
                } while (true);
                for (n2 = this.mCurItem + 1; n2 < n7; ++n2) {
                    void var13_42;
                    if (f >= f3 && n2 > n8) {
                        if (itemInfo2 == null) break;
                        f2 = f;
                        ItemInfo itemInfo13 = itemInfo2;
                        n = n9;
                        if (n2 == itemInfo2.position) {
                            f2 = f;
                            ItemInfo itemInfo14 = itemInfo2;
                            n = n9;
                            if (!itemInfo2.scrolling) {
                                this.mItems.remove(n9);
                                this.mAdapter.destroyItem(this, n2, itemInfo2.object);
                                if (n9 < this.mItems.size()) {
                                    ItemInfo itemInfo15 = this.mItems.get(n9);
                                    n = n9;
                                    f2 = f;
                                } else {
                                    Object var13_43 = null;
                                    f2 = f;
                                    n = n9;
                                }
                            }
                        }
                    } else if (itemInfo2 != null && n2 == itemInfo2.position) {
                        f2 = f + itemInfo2.widthFactor;
                        n = n9 + 1;
                        if (n < this.mItems.size()) {
                            ItemInfo itemInfo16 = this.mItems.get(n);
                        } else {
                            Object var13_46 = null;
                        }
                    } else {
                        ItemInfo itemInfo17 = this.addNewItem(n2, n9);
                        n = n9 + 1;
                        f2 = f + itemInfo17.widthFactor;
                        if (n < this.mItems.size()) {
                            ItemInfo itemInfo18 = this.mItems.get(n);
                        } else {
                            Object var13_50 = null;
                        }
                    }
                    f = f2;
                    itemInfo2 = var13_42;
                    n9 = n;
                }
            }
            this.calculatePageOffsets(itemInfo, n3, (ItemInfo)object);
        }
        object = this.mAdapter;
        n = this.mCurItem;
        if (itemInfo != null) {
            Object object2 = itemInfo.object;
        } else {
            Object var13_51 = null;
        }
        ((PagerAdapter)object).setPrimaryItem(this, n, (Object)var13_23);
        this.mAdapter.finishUpdate(this);
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            object = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
            layoutParams.childIndex = n;
            if (layoutParams.isDecor || layoutParams.widthFactor != 0.0f || (object = this.infoForChild((View)object)) == null) continue;
            layoutParams.widthFactor = ((ItemInfo)object).widthFactor;
            layoutParams.position = ((ItemInfo)object).position;
        }
        this.sortChildDrawingOrder();
        if (!this.hasFocus()) return;
        View view = this.findFocus();
        if (view != null) {
            ItemInfo itemInfo19 = this.infoForAnyChild(view);
        } else {
            Object var13_57 = null;
        }
        if (var13_54 != null) {
            if (var13_54.position == this.mCurItem) return;
        }
        n = 0;
        while (n < this.getChildCount()) {
            View view2 = this.getChildAt(n);
            object = this.infoForChild(view2);
            if (object != null && ((ItemInfo)object).position == this.mCurItem) {
                if (view2.requestFocus(2)) return;
            }
            ++n;
        }
    }

    public void removeOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners != null) {
            this.mAdapterChangeListeners.remove(onAdapterChangeListener);
        }
    }

    public void removeOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove(onPageChangeListener);
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(PagerAdapter pagerAdapter) {
        int n;
        Object object;
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate(this);
            for (n = 0; n < this.mItems.size(); ++n) {
                object = this.mItems.get(n);
                this.mAdapter.destroyItem(this, ((ItemInfo)object).position, ((ItemInfo)object).object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        object = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean bl = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if (this.mAdapterChangeListeners != null && !this.mAdapterChangeListeners.isEmpty()) {
            int n2 = this.mAdapterChangeListeners.size();
            for (n = 0; n < n2; ++n) {
                this.mAdapterChangeListeners.get(n).onAdapterChanged(this, (PagerAdapter)object, pagerAdapter);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCurrentItem(int n) {
        this.mPopulatePending = false;
        boolean bl = !this.mFirstLayout;
        this.setCurrentItemInternal(n, bl, false);
    }

    public void setCurrentItem(int n, boolean bl) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, bl, false);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2) {
        this.setCurrentItemInternal(n, bl, bl2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setCurrentItemInternal(int n, boolean bl, boolean bl2, int n2) {
        int n3;
        boolean bl3 = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        if (!bl2 && this.mCurItem == n && this.mItems.size() != 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        if (n < 0) {
            n3 = 0;
        } else {
            n3 = n;
            if (n >= this.mAdapter.getCount()) {
                n3 = this.mAdapter.getCount() - 1;
            }
        }
        if (n3 > this.mCurItem + (n = this.mOffscreenPageLimit) || n3 < this.mCurItem - n) {
            for (n = 0; n < this.mItems.size(); ++n) {
                this.mItems.get((int)n).scrolling = true;
            }
        }
        bl2 = this.mCurItem != n3 ? bl3 : false;
        if (!this.mFirstLayout) {
            this.populate(n3);
            this.scrollToItem(n3, bl, n2, bl2);
            return;
        }
        this.mCurItem = n3;
        if (bl2) {
            this.dispatchOnPageSelected(n3);
        }
        this.requestLayout();
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n) {
        int n2 = n;
        if (n < 1) {
            Log.w((String)"ViewPager", (String)("Requested offscreen page limit " + n + " too small; defaulting to " + 1));
            n2 = 1;
        }
        if (n2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = n2;
            this.populate();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int n) {
        int n2 = this.mPageMargin;
        this.mPageMargin = n;
        int n3 = this.getWidth();
        this.recomputeScrollPosition(n3, n3, n, n2);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n) {
        this.setPageMarginDrawable(ContextCompat.getDrawable(this.getContext(), n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageMarginDrawable(Drawable drawable2) {
        this.mMarginDrawable = drawable2;
        if (drawable2 != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable2 == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setPageTransformer(boolean bl, PageTransformer pageTransformer) {
        this.setPageTransformer(bl, pageTransformer, 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageTransformer(boolean bl, PageTransformer pageTransformer, int n) {
        int n2 = 1;
        boolean bl2 = pageTransformer != null;
        boolean bl3 = this.mPageTransformer != null;
        boolean bl4 = bl2 != bl3;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabled(bl2);
        if (bl2) {
            if (bl) {
                n2 = 2;
            }
            this.mDrawingOrder = n2;
            this.mPageTransformerLayerType = n;
        } else {
            this.mDrawingOrder = 0;
        }
        if (bl4) {
            this.populate();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setScrollState(int n) {
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        if (this.mPageTransformer != null) {
            boolean bl = n != 0;
            this.enableLayers(bl);
        }
        this.dispatchOnScrollStateChanged(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    void smoothScrollTo(int n, int n2, int n3) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        int n4 = this.mScroller != null && !this.mScroller.isFinished() ? 1 : 0;
        if (n4 != 0) {
            n4 = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            this.setScrollingCacheEnabled(false);
        } else {
            n4 = this.getScrollX();
        }
        int n5 = this.getScrollY();
        int n6 = n - n4;
        if (n6 == 0 && (n2 -= n5) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n = this.getClientWidth();
        int n7 = n / 2;
        float f = Math.min(1.0f, 1.0f * (float)Math.abs(n6) / (float)n);
        float f2 = n7;
        float f3 = n7;
        f = this.distanceInfluenceForSnapDuration(f);
        n3 = Math.abs(n3);
        if (n3 > 0) {
            n = Math.round(1000.0f * Math.abs((f2 + f3 * f) / (float)n3)) * 4;
        } else {
            f2 = n;
            f3 = this.mAdapter.getPageWidth(this.mCurItem);
            n = (int)((1.0f + (float)Math.abs(n6) / ((float)this.mPageMargin + f2 * f3)) * 100.0f);
        }
        n = Math.min(n, 600);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(n4, n5, n6, n2, n);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mMarginDrawable;
    }

    @Inherited
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DecorView {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = context.getInteger(0, 48);
            context.recycle();
        }
    }

    class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        private boolean canScroll() {
            return ViewPager.this.mAdapter != null && ViewPager.this.mAdapter.getCount() > 1;
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            accessibilityEvent.setScrollable(this.canScroll());
            if (accessibilityEvent.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
                accessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
                accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
                accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
            }
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(this.canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            if (super.performAccessibilityAction(view, n, bundle)) {
                return true;
            }
            switch (n) {
                default: {
                    return false;
                }
                case 4096: {
                    if (ViewPager.this.canScrollHorizontally(1)) {
                        ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1);
                        return true;
                    }
                    return false;
                }
                case 8192: 
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1);
                return true;
            }
            return false;
        }
    }

    public static interface OnAdapterChangeListener {
        public void onAdapterChanged(ViewPager var1, PagerAdapter var2, PagerAdapter var3);
    }

    public static interface OnPageChangeListener {
        public void onPageScrollStateChanged(int var1);

        public void onPageScrolled(int var1, float var2, int var3);

        public void onPageSelected(int var1);
    }

    public static interface PageTransformer {
        public void transformPage(View var1, float var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        PagerObserver() {
        }

        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState
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
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }

    }

    public static class SimpleOnPageChangeListener
    implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override
        public int compare(View object, View object2) {
            object = (LayoutParams)object.getLayoutParams();
            object2 = (LayoutParams)object2.getLayoutParams();
            if (object.isDecor != object2.isDecor) {
                if (object.isDecor) {
                    return 1;
                }
                return -1;
            }
            return object.position - object2.position;
        }
    }

}

