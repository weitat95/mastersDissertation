/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.LayoutState;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ScrollbarHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager
extends RecyclerView.LayoutManager
implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    private final AnchorInfo mAnchorInfo;
    private final Runnable mCheckForGapsRunnable;
    private int mFullSizeSpec;
    private int mGapStrategy;
    private boolean mLaidOutInvalidFullSpan;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup;
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private int[] mPrefetchDistances;
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    boolean mReverseLayout;
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled;
    private int mSpanCount;
    Span[] mSpans;
    private final Rect mTmpRect;

    /*
     * Enabled aggressive block sorting
     */
    public StaggeredGridLayoutManager(int n, int n2) {
        boolean bl = true;
        this.mSpanCount = -1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mLazySpanLookup = new LazySpanLookup();
        this.mGapStrategy = 2;
        this.mTmpRect = new Rect();
        this.mAnchorInfo = new AnchorInfo();
        this.mLaidOutInvalidFullSpan = false;
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable(){

            @Override
            public void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        this.mOrientation = n2;
        this.setSpanCount(n);
        if (this.mGapStrategy == 0) {
            bl = false;
        }
        this.setAutoMeasureEnabled(bl);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public StaggeredGridLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        void var3_4;
        void var4_5;
        void var2_3;
        boolean bl = true;
        this.mSpanCount = -1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mLazySpanLookup = new LazySpanLookup();
        this.mGapStrategy = 2;
        this.mTmpRect = new Rect();
        this.mAnchorInfo = new AnchorInfo();
        this.mLaidOutInvalidFullSpan = false;
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new /* invalid duplicate definition of identical inner class */;
        RecyclerView.LayoutManager.Properties properties = StaggeredGridLayoutManager.getProperties(object, (AttributeSet)var2_3, (int)var3_4, (int)var4_5);
        this.setOrientation(properties.orientation);
        this.setSpanCount(properties.spanCount);
        this.setReverseLayout(properties.reverseLayout);
        if (this.mGapStrategy == 0) {
            bl = false;
        }
        this.setAutoMeasureEnabled(bl);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    private void appendViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; --i) {
            this.mSpans[i].appendToSpan(view);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize != this.mSpanCount) {
                this.mPendingSavedState.invalidateSpanInfo();
                this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
            } else {
                for (int i = 0; i < this.mSpanCount; ++i) {
                    int n;
                    this.mSpans[i].clear();
                    int n2 = n = this.mPendingSavedState.mSpanOffsets[i];
                    if (n != Integer.MIN_VALUE) {
                        n2 = this.mPendingSavedState.mAnchorLayoutFromEnd ? n + this.mPrimaryOrientation.getEndAfterPadding() : n + this.mPrimaryOrientation.getStartAfterPadding();
                    }
                    this.mSpans[i].setLine(n2);
                }
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        this.setReverseLayout(this.mPendingSavedState.mReverseLayout);
        this.resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        } else {
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.mSpanLookupSize > 1) {
            this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
            this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
        }
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.mLayoutDirection == 1) {
            if (layoutParams.mFullSpan) {
                this.appendViewToAllSpans(view);
                return;
            }
            layoutParams.mSpan.appendToSpan(view);
            return;
        }
        if (layoutParams.mFullSpan) {
            this.prependViewToAllSpans(view);
            return;
        }
        layoutParams.mSpan.prependToSpan(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int calculateScrollDirectionForPosition(int n) {
        int n2 = -1;
        if (this.getChildCount() == 0) {
            if (!this.mShouldReverseLayout) return -1;
            return 1;
        }
        boolean bl = n < this.getFirstChildPosition();
        if (bl == this.mShouldReverseLayout) return 1;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkSpanForGap(Span span) {
        if (this.mShouldReverseLayout) {
            if (span.getEndLine() >= this.mPrimaryOrientation.getEndAfterPadding()) return false;
            if (!span.getLayoutParams((View)span.mViews.get((int)(span.mViews.size() - 1))).mFullSpan) return true;
            return false;
        }
        if (span.getStartLine() <= this.mPrimaryOrientation.getStartAfterPadding()) return false;
        if (span.getLayoutParams((View)span.mViews.get((int)0)).mFullSpan) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeScrollExtent(RecyclerView.State state) {
        boolean bl = true;
        if (this.getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean bl2 = !this.mSmoothScrollbarEnabled;
        View view = this.findFirstVisibleItemClosestToStart(bl2);
        if (!this.mSmoothScrollbarEnabled) {
            bl2 = bl;
            return ScrollbarHelper.computeScrollExtent(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled);
        }
        bl2 = false;
        return ScrollbarHelper.computeScrollExtent(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeScrollOffset(RecyclerView.State state) {
        boolean bl = true;
        if (this.getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean bl2 = !this.mSmoothScrollbarEnabled;
        View view = this.findFirstVisibleItemClosestToStart(bl2);
        if (!this.mSmoothScrollbarEnabled) {
            bl2 = bl;
            return ScrollbarHelper.computeScrollOffset(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
        }
        bl2 = false;
        return ScrollbarHelper.computeScrollOffset(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeScrollRange(RecyclerView.State state) {
        boolean bl = true;
        if (this.getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean bl2 = !this.mSmoothScrollbarEnabled;
        View view = this.findFirstVisibleItemClosestToStart(bl2);
        if (!this.mSmoothScrollbarEnabled) {
            bl2 = bl;
            return ScrollbarHelper.computeScrollRange(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled);
        }
        bl2 = false;
        return ScrollbarHelper.computeScrollRange(state, orientationHelper, view, this.findFirstVisibleItemClosestToEnd(bl2), this, this.mSmoothScrollbarEnabled);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = -1;
        int n3 = Integer.MIN_VALUE;
        int n4 = 1;
        switch (n) {
            default: {
                return Integer.MIN_VALUE;
            }
            case 1: {
                n = n2;
                if (this.mOrientation == 1) return n;
                n = n2;
                if (!this.isLayoutRTL()) return n;
                return 1;
            }
            case 2: {
                if (this.mOrientation == 1) {
                    return 1;
                }
                n = n2;
                if (this.isLayoutRTL()) return n;
                return 1;
            }
            case 33: {
                n = n2;
                if (this.mOrientation == 1) return n;
                return Integer.MIN_VALUE;
            }
            case 130: {
                n = n3;
                if (this.mOrientation != 1) return n;
                return 1;
            }
            case 17: {
                n = n2;
                if (this.mOrientation == 0) return n;
                return Integer.MIN_VALUE;
            }
            case 66: 
        }
        if (this.mOrientation != 0) return Integer.MIN_VALUE;
        return n4;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; ++i) {
            fullSpanItem.mGapPerSpan[i] = n - this.mSpans[i].getEndLine(n);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; ++i) {
            fullSpanItem.mGapPerSpan[i] = this.mSpans[i].getStartLine(n) - n;
        }
        return fullSpanItem;
    }

    private void createOrientationHelpers() {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        int n = this.mLayoutState.mInfinite ? (layoutState.mLayoutDirection == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE) : (layoutState.mLayoutDirection == 1 ? layoutState.mEndLine + layoutState.mAvailable : layoutState.mStartLine - layoutState.mAvailable);
        this.updateAllRemainingSpans(layoutState.mLayoutDirection, n);
        int n2 = this.mShouldReverseLayout ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
        int n3 = 0;
        while (layoutState.hasMore(state) && (this.mLayoutState.mInfinite || !this.mRemainingSpans.isEmpty())) {
            int n4;
            Span span;
            LazySpanLookup.FullSpanItem fullSpanItem;
            int n5;
            int n6;
            View view = layoutState.next(recycler);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n7 = layoutParams.getViewLayoutPosition();
            n3 = this.mLazySpanLookup.getSpan(n7);
            int n8 = n3 == -1 ? 1 : 0;
            if (n8 != 0) {
                span = layoutParams.mFullSpan ? this.mSpans[0] : this.getNextSpan(layoutState);
                this.mLazySpanLookup.setSpan(n7, span);
            } else {
                span = this.mSpans[n3];
            }
            layoutParams.mSpan = span;
            if (layoutState.mLayoutDirection == 1) {
                this.addView(view);
            } else {
                this.addView(view, 0);
            }
            this.measureChildWithDecorationsAndMargin(view, layoutParams, false);
            if (layoutState.mLayoutDirection == 1) {
                n3 = layoutParams.mFullSpan ? this.getMaxEnd(n2) : span.getEndLine(n2);
                n6 = n3 + this.mPrimaryOrientation.getDecoratedMeasurement(view);
                n4 = n3;
                n5 = n6;
                if (n8 != 0) {
                    n4 = n3;
                    n5 = n6;
                    if (layoutParams.mFullSpan) {
                        fullSpanItem = this.createFullSpanItemFromEnd(n3);
                        fullSpanItem.mGapDir = -1;
                        fullSpanItem.mPosition = n7;
                        this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
                        n5 = n6;
                        n4 = n3;
                    }
                }
            } else {
                n3 = layoutParams.mFullSpan ? this.getMinStart(n2) : span.getStartLine(n2);
                n4 = n6 = n3 - this.mPrimaryOrientation.getDecoratedMeasurement(view);
                n5 = n3;
                if (n8 != 0) {
                    n4 = n6;
                    n5 = n3;
                    if (layoutParams.mFullSpan) {
                        fullSpanItem = this.createFullSpanItemFromStart(n3);
                        fullSpanItem.mGapDir = 1;
                        fullSpanItem.mPosition = n7;
                        this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
                        n4 = n6;
                        n5 = n3;
                    }
                }
            }
            if (layoutParams.mFullSpan && layoutState.mItemDirection == -1) {
                if (n8 != 0) {
                    this.mLaidOutInvalidFullSpan = true;
                } else {
                    n3 = layoutState.mLayoutDirection == 1 ? (!this.areAllEndsEqual() ? 1 : 0) : (!this.areAllStartsEqual() ? 1 : 0);
                    if (n3 != 0) {
                        fullSpanItem = this.mLazySpanLookup.getFullSpanItem(n7);
                        if (fullSpanItem != null) {
                            fullSpanItem.mHasUnwantedGapAfter = true;
                        }
                        this.mLaidOutInvalidFullSpan = true;
                    }
                }
            }
            this.attachViewToSpans(view, layoutParams, layoutState);
            if (this.isLayoutRTL() && this.mOrientation == 1) {
                n3 = layoutParams.mFullSpan ? this.mSecondaryOrientation.getEndAfterPadding() : this.mSecondaryOrientation.getEndAfterPadding() - (this.mSpanCount - 1 - span.mIndex) * this.mSizePerSpan;
                n6 = n3 - this.mSecondaryOrientation.getDecoratedMeasurement(view);
                n8 = n3;
                n3 = n6;
            } else {
                n3 = layoutParams.mFullSpan ? this.mSecondaryOrientation.getStartAfterPadding() : span.mIndex * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
                n8 = n3 + this.mSecondaryOrientation.getDecoratedMeasurement(view);
            }
            if (this.mOrientation == 1) {
                this.layoutDecoratedWithMargins(view, n3, n4, n8, n5);
            } else {
                this.layoutDecoratedWithMargins(view, n4, n3, n5, n8);
            }
            if (layoutParams.mFullSpan) {
                this.updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, n);
            } else {
                this.updateRemainingSpans(span, this.mLayoutState.mLayoutDirection, n);
            }
            this.recycle(recycler, this.mLayoutState);
            if (this.mLayoutState.mStopInFocusable && view.hasFocusable()) {
                if (layoutParams.mFullSpan) {
                    this.mRemainingSpans.clear();
                } else {
                    this.mRemainingSpans.set(span.mIndex, false);
                }
            }
            n3 = 1;
        }
        if (n3 == 0) {
            this.recycle(recycler, this.mLayoutState);
        }
        if (this.mLayoutState.mLayoutDirection == -1) {
            n = this.getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
            n = this.mPrimaryOrientation.getStartAfterPadding() - n;
        } else {
            n = this.getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
        }
        if (n > 0) {
            return Math.min(layoutState.mAvailable, n);
        }
        return 0;
    }

    private int findFirstReferenceChildPosition(int n) {
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            int n3 = this.getPosition(this.getChildAt(i));
            if (n3 < 0 || n3 >= n) continue;
            return n3;
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int n) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            int n2 = this.getPosition(this.getChildAt(i));
            if (n2 < 0 || n2 >= n) continue;
            return n2;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n;
        block3: {
            block2: {
                n = this.getMaxEnd(Integer.MIN_VALUE);
                if (n == Integer.MIN_VALUE || (n = this.mPrimaryOrientation.getEndAfterPadding() - n) <= 0) break block2;
                n -= -this.scrollBy(-n, recycler, state);
                if (bl && n > 0) break block3;
            }
            return;
        }
        this.mPrimaryOrientation.offsetChildren(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n;
        block3: {
            block2: {
                n = this.getMinStart(Integer.MAX_VALUE);
                if (n == Integer.MAX_VALUE || (n -= this.mPrimaryOrientation.getStartAfterPadding()) <= 0) break block2;
                n -= this.scrollBy(n, recycler, state);
                if (bl && n > 0) break block3;
            }
            return;
        }
        this.mPrimaryOrientation.offsetChildren(-n);
    }

    private int getMaxEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getEndLine(n);
            int n4 = n2;
            if (n3 > n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMaxStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getStartLine(n);
            int n4 = n2;
            if (n3 > n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMinEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getEndLine(n);
            int n4 = n2;
            if (n3 < n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMinStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getStartLine(n);
            int n4 = n2;
            if (n3 < n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Span getNextSpan(LayoutState object) {
        int n;
        int n2;
        Object object2;
        int n3;
        if (this.preferLastSpan(((LayoutState)object).mLayoutDirection)) {
            n2 = this.mSpanCount - 1;
            n3 = -1;
            n = -1;
        } else {
            n2 = 0;
            n3 = this.mSpanCount;
            n = 1;
        }
        if (((LayoutState)object).mLayoutDirection == 1) {
            object = null;
            int n4 = Integer.MAX_VALUE;
            int n5 = this.mPrimaryOrientation.getStartAfterPadding();
            do {
                object2 = object;
                if (n2 == n3) return object2;
                object2 = this.mSpans[n2];
                int n6 = ((Span)object2).getEndLine(n5);
                int n7 = n4;
                if (n6 < n4) {
                    object = object2;
                    n7 = n6;
                }
                n2 += n;
                n4 = n7;
            } while (true);
        }
        object = null;
        int n8 = Integer.MIN_VALUE;
        int n9 = this.mPrimaryOrientation.getEndAfterPadding();
        while (n2 != n3) {
            object2 = this.mSpans[n2];
            int n10 = ((Span)object2).getStartLine(n9);
            int n11 = n8;
            if (n10 > n8) {
                object = object2;
                n11 = n10;
            }
            n2 += n;
            n8 = n11;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleUpdate(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6 = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
        if (n3 == 8) {
            if (n < n2) {
                n5 = n2 + 1;
                n4 = n;
            } else {
                n5 = n + 1;
                n4 = n2;
            }
        } else {
            n4 = n;
            n5 = n + n2;
        }
        this.mLazySpanLookup.invalidateAfter(n4);
        switch (n3) {
            case 1: {
                this.mLazySpanLookup.offsetForAddition(n, n2);
                break;
            }
            case 2: {
                this.mLazySpanLookup.offsetForRemoval(n, n2);
                break;
            }
            case 8: {
                this.mLazySpanLookup.offsetForRemoval(n, 1);
                this.mLazySpanLookup.offsetForAddition(n2, 1);
            }
        }
        if (n5 <= n6 || n4 > (n = this.mShouldReverseLayout ? this.getFirstChildPosition() : this.getLastChildPosition())) {
            return;
        }
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        this.calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        n = this.updateSpecWithExtra(n, layoutParams.leftMargin + this.mTmpRect.left, layoutParams.rightMargin + this.mTmpRect.right);
        n2 = this.updateSpecWithExtra(n2, layoutParams.topMargin + this.mTmpRect.top, layoutParams.bottomMargin + this.mTmpRect.bottom);
        bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams);
        if (bl) {
            view.measure(n, n2);
        }
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean bl) {
        if (layoutParams.mFullSpan) {
            if (this.mOrientation == 1) {
                this.measureChildWithDecorationsAndMargin(view, this.mFullSizeSpec, StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), 0, layoutParams.height, true), bl);
                return;
            }
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), 0, layoutParams.width, true), this.mFullSizeSpec, bl);
            return;
        }
        if (this.mOrientation == 1) {
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getWidthMode(), 0, layoutParams.width, false), StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), 0, layoutParams.height, true), bl);
            return;
        }
        this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), 0, layoutParams.width, true), StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getHeightMode(), 0, layoutParams.height, false), bl);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void onLayoutChildren(RecyclerView.Recycler var1_1, RecyclerView.State var2_2, boolean var3_3) {
        block23: {
            block22: {
                block21: {
                    block25: {
                        block19: {
                            block20: {
                                block18: {
                                    block24: {
                                        var6_4 = 1;
                                        var8_5 = this.mAnchorInfo;
                                        if ((this.mPendingSavedState != null || this.mPendingScrollPosition != -1) && var2_2.getItemCount() == 0) {
                                            this.removeAndRecycleAllViews(var1_1);
                                            var8_5.reset();
                                            do {
                                                return;
                                                break;
                                            } while (true);
                                        }
                                        if (var8_5.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) break block24;
                                        var4_6 = 1;
lbl10:
                                        // 2 sources
                                        do {
                                            if (var4_6 == 0) ** GOTO lbl18
                                            var8_5.reset();
                                            if (this.mPendingSavedState == null) break block18;
                                            this.applyPendingSavedState(var8_5);
lbl15:
                                            // 2 sources
                                            do {
                                                this.updateAnchorInfoForLayout(var2_2, var8_5);
                                                var8_5.mValid = true;
lbl18:
                                                // 2 sources
                                                if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && (var8_5.mLayoutFromEnd != this.mLastLayoutFromEnd || this.isLayoutRTL() != this.mLastLayoutRTL)) {
                                                    this.mLazySpanLookup.clear();
                                                    var8_5.mInvalidateOffsets = true;
                                                }
                                                if (this.getChildCount() <= 0 || this.mPendingSavedState != null && this.mPendingSavedState.mSpanOffsetsSize >= 1) break block19;
                                                if (!var8_5.mInvalidateOffsets) break block20;
                                                for (var4_6 = 0; var4_6 < this.mSpanCount; ++var4_6) {
                                                    this.mSpans[var4_6].clear();
                                                    if (var8_5.mOffset == Integer.MIN_VALUE) continue;
                                                    this.mSpans[var4_6].setLine(var8_5.mOffset);
                                                }
                                                break block19;
                                                break;
                                            } while (true);
                                            break;
                                        } while (true);
                                    }
                                    var4_6 = 0;
                                    ** while (true)
                                }
                                this.resolveShouldLayoutReverse();
                                var8_5.mLayoutFromEnd = this.mShouldReverseLayout;
                                ** while (true)
                            }
                            if (var4_6 == 0 && this.mAnchorInfo.mSpanReferenceLines != null) break block25;
                            for (var4_6 = 0; var4_6 < this.mSpanCount; ++var4_6) {
                                this.mSpans[var4_6].cacheReferenceLineAndClear(this.mShouldReverseLayout, var8_5.mOffset);
                            }
                            this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
                        }
                        do {
                            this.detachAndScrapAttachedViews(var1_1);
                            this.mLayoutState.mRecycle = false;
                            this.mLaidOutInvalidFullSpan = false;
                            this.updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
                            this.updateLayoutState(var8_5.mPosition, var2_2);
                            if (!var8_5.mLayoutFromEnd) break block21;
                            this.setLayoutStateDirection(-1);
                            this.fill(var1_1, this.mLayoutState, var2_2);
                            this.setLayoutStateDirection(1);
                            this.mLayoutState.mCurrentPosition = var8_5.mPosition + this.mLayoutState.mItemDirection;
                            this.fill(var1_1, this.mLayoutState, var2_2);
lbl57:
                            // 2 sources
                            do {
                                this.repositionToWrapContentIfNecessary();
                                if (this.getChildCount() > 0) {
                                    if (!this.mShouldReverseLayout) break block22;
                                    this.fixEndGap(var1_1, var2_2, true);
                                    this.fixStartGap(var1_1, var2_2, false);
                                }
lbl63:
                                // 4 sources
                                do {
                                    var5_8 = var7_7 = false;
                                    if (!var3_3) ** GOTO lbl81
                                    var5_8 = var7_7;
                                    if (var2_2.isPreLayout()) ** GOTO lbl81
                                    if (this.mGapStrategy == 0 || this.getChildCount() <= 0) break block23;
                                    var4_6 = var6_4;
                                    if (!this.mLaidOutInvalidFullSpan) {
                                        if (this.hasGapsToFix() == null) break block23;
                                        var4_6 = var6_4;
                                    }
lbl73:
                                    // 4 sources
                                    do {
                                        var5_8 = var7_7;
                                        if (var4_6 != 0) {
                                            this.removeCallbacks(this.mCheckForGapsRunnable);
                                            var5_8 = var7_7;
                                            if (this.checkForGaps()) {
                                                var5_8 = true;
                                            }
                                        }
lbl81:
                                        // 7 sources
                                        if (var2_2.isPreLayout()) {
                                            this.mAnchorInfo.reset();
                                        }
                                        this.mLastLayoutFromEnd = var8_5.mLayoutFromEnd;
                                        this.mLastLayoutRTL = this.isLayoutRTL();
                                        if (!var5_8) ** continue;
                                        this.mAnchorInfo.reset();
                                        this.onLayoutChildren(var1_1, var2_2, false);
                                        return;
                                        break;
                                    } while (true);
                                    break;
                                } while (true);
                                break;
                            } while (true);
                            break;
                        } while (true);
                    }
                    var4_6 = 0;
                    do {
                        if (var4_6 >= this.mSpanCount) ** continue;
                        var9_9 = this.mSpans[var4_6];
                        var9_9.clear();
                        var9_9.setLine(this.mAnchorInfo.mSpanReferenceLines[var4_6]);
                        ++var4_6;
                    } while (true);
                }
                this.setLayoutStateDirection(1);
                this.fill(var1_1, this.mLayoutState, var2_2);
                this.setLayoutStateDirection(-1);
                this.mLayoutState.mCurrentPosition = var8_5.mPosition + this.mLayoutState.mItemDirection;
                this.fill(var1_1, this.mLayoutState, var2_2);
                ** while (true)
            }
            this.fixStartGap(var1_1, var2_2, true);
            this.fixEndGap(var1_1, var2_2, false);
            ** while (true)
        }
        var4_6 = 0;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean preferLastSpan(int n) {
        if (this.mOrientation == 0) {
            boolean bl = n == -1;
            if (bl != this.mShouldReverseLayout) return true;
            return false;
        }
        boolean bl = n == -1;
        if ((bl = bl == this.mShouldReverseLayout) != this.isLayoutRTL()) return false;
        return true;
    }

    private void prependViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; --i) {
            this.mSpans[i].prependToSpan(view);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.mRecycle || layoutState.mInfinite) {
            return;
        }
        if (layoutState.mAvailable == 0) {
            if (layoutState.mLayoutDirection == -1) {
                this.recycleFromEnd(recycler, layoutState.mEndLine);
                return;
            }
            this.recycleFromStart(recycler, layoutState.mStartLine);
            return;
        }
        if (layoutState.mLayoutDirection == -1) {
            int n = layoutState.mStartLine - this.getMaxStart(layoutState.mStartLine);
            n = n < 0 ? layoutState.mEndLine : layoutState.mEndLine - Math.min(n, layoutState.mAvailable);
            this.recycleFromEnd(recycler, n);
            return;
        }
        int n = this.getMinEnd(layoutState.mEndLine) - layoutState.mEndLine;
        n = n < 0 ? layoutState.mStartLine : layoutState.mStartLine + Math.min(n, layoutState.mAvailable);
        this.recycleFromStart(recycler, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recycleFromEnd(RecyclerView.Recycler recycler, int n) {
        View view;
        block0: for (int i = this.getChildCount() - 1; i >= 0 && this.mPrimaryOrientation.getDecoratedStart(view = this.getChildAt(i)) >= n && this.mPrimaryOrientation.getTransformedStartWithDecoration(view) >= n; --i) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.mFullSpan) {
                int n2;
                for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                    if (this.mSpans[n2].mViews.size() == 1) break block0;
                }
                for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                    this.mSpans[n2].popEnd();
                }
            } else {
                if (layoutParams.mSpan.mViews.size() == 1) break;
                layoutParams.mSpan.popEnd();
            }
            this.removeAndRecycleView(view, recycler);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recycleFromStart(RecyclerView.Recycler recycler, int n) {
        View view;
        block0 : while (this.getChildCount() > 0 && this.mPrimaryOrientation.getDecoratedEnd(view = this.getChildAt(0)) <= n && this.mPrimaryOrientation.getTransformedEndWithDecoration(view) <= n) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.mFullSpan) {
                int n2;
                for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                    if (this.mSpans[n2].mViews.size() == 1) break block0;
                }
                for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                    this.mSpans[n2].popStart();
                }
            } else {
                if (layoutParams.mSpan.mViews.size() == 1) break;
                layoutParams.mSpan.popStart();
            }
            this.removeAndRecycleView(view, recycler);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void repositionToWrapContentIfNecessary() {
        if (this.mSecondaryOrientation.getMode() != 1073741824) {
            int n;
            int n2;
            View view;
            float f = 0.0f;
            int n3 = this.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                view = this.getChildAt(n2);
                float f2 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
                if (f2 < f) continue;
                float f3 = f2;
                if (((LayoutParams)view.getLayoutParams()).isFullSpan()) {
                    f3 = 1.0f * f2 / (float)this.mSpanCount;
                }
                f = Math.max(f, f3);
            }
            int n4 = this.mSizePerSpan;
            n2 = n = Math.round((float)this.mSpanCount * f);
            if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
                n2 = Math.min(n, this.mSecondaryOrientation.getTotalSpace());
            }
            this.updateMeasureSpecs(n2);
            if (this.mSizePerSpan != n4) {
                for (n2 = 0; n2 < n3; ++n2) {
                    view = this.getChildAt(n2);
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    if (layoutParams.mFullSpan) continue;
                    if (this.isLayoutRTL() && this.mOrientation == 1) {
                        view.offsetLeftAndRight(-(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * this.mSizePerSpan - -(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * n4);
                        continue;
                    }
                    n = layoutParams.mSpan.mIndex * this.mSizePerSpan;
                    int n5 = layoutParams.mSpan.mIndex * n4;
                    if (this.mOrientation == 1) {
                        view.offsetLeftAndRight(n - n5);
                        continue;
                    }
                    view.offsetTopAndBottom(n - n5);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resolveShouldLayoutReverse() {
        boolean bl = true;
        if (this.mOrientation == 1 || !this.isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
            return;
        }
        if (this.mReverseLayout) {
            bl = false;
        }
        this.mShouldReverseLayout = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setLayoutStateDirection(int n) {
        int n2 = 1;
        this.mLayoutState.mLayoutDirection = n;
        LayoutState layoutState = this.mLayoutState;
        boolean bl = this.mShouldReverseLayout;
        boolean bl2 = n == -1;
        n = bl == bl2 ? n2 : -1;
        layoutState.mItemDirection = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateAllRemainingSpans(int n, int n2) {
        int n3 = 0;
        while (n3 < this.mSpanCount) {
            if (!this.mSpans[n3].mViews.isEmpty()) {
                this.updateRemainingSpans(this.mSpans[n3], n, n2);
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateAnchorFromChildren(RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.mLastLayoutFromEnd ? this.findLastReferenceChildPosition(state.getItemCount()) : this.findFirstReferenceChildPosition(state.getItemCount());
        anchorInfo.mPosition = n;
        anchorInfo.mOffset = Integer.MIN_VALUE;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateLayoutState(int n, RecyclerView.State object) {
        boolean bl;
        int n2;
        boolean bl2 = true;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.mCurrentPosition = n;
        int n3 = 0;
        int n4 = n2 = 0;
        int n5 = n3;
        if (this.isSmoothScrolling()) {
            int n6 = ((RecyclerView.State)object).getTargetScrollPosition();
            n4 = n2;
            n5 = n3;
            if (n6 != -1) {
                boolean bl3 = this.mShouldReverseLayout;
                bl = n6 < n;
                if (bl3 == bl) {
                    n4 = this.mPrimaryOrientation.getTotalSpace();
                    n5 = n3;
                } else {
                    n5 = this.mPrimaryOrientation.getTotalSpace();
                    n4 = n2;
                }
            }
        }
        if (this.getClipToPadding()) {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - n5;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + n4;
        } else {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + n4;
            this.mLayoutState.mStartLine = -n5;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        object = this.mLayoutState;
        bl = this.mPrimaryOrientation.getMode() == 0 && this.mPrimaryOrientation.getEnd() == 0 ? bl2 : false;
        ((LayoutState)object).mInfinite = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateRemainingSpans(Span span, int n, int n2) {
        int n3 = span.getDeletedSize();
        if (n == -1) {
            if (span.getStartLine() + n3 > n2) return;
            {
                this.mRemainingSpans.set(span.mIndex, false);
                return;
            }
        } else {
            if (span.getEndLine() - n3 < n2) return;
            {
                this.mRemainingSpans.set(span.mIndex, false);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int updateSpecWithExtra(int n, int n2, int n3) {
        int n4;
        if (n2 == 0 && n3 == 0 || (n4 = View.MeasureSpec.getMode((int)n)) != Integer.MIN_VALUE && n4 != 1073741824) {
            return n;
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n) - n2 - n3), (int)n4);
    }

    boolean areAllEndsEqual() {
        int n = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; ++i) {
            if (this.mSpans[i].getEndLine(Integer.MIN_VALUE) == n) continue;
            return false;
        }
        return true;
    }

    boolean areAllStartsEqual() {
        int n = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; ++i) {
            if (this.mSpans[i].getStartLine(Integer.MIN_VALUE) == n) continue;
            return false;
        }
        return true;
    }

    @Override
    public void assertNotInLayoutOrScroll(String string2) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(string2);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override
    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean checkForGaps() {
        int n;
        int n2;
        if (this.getChildCount() == 0 || this.mGapStrategy == 0 || !this.isAttachedToWindow()) {
            return false;
        }
        if (this.mShouldReverseLayout) {
            n = this.getLastChildPosition();
            n2 = this.getFirstChildPosition();
        } else {
            n = this.getFirstChildPosition();
            n2 = this.getLastChildPosition();
        }
        if (n == 0 && this.hasGapsToFix() != null) {
            this.mLazySpanLookup.clear();
            this.requestSimpleAnimationsInNextLayout();
            this.requestLayout();
            return true;
        }
        if (!this.mLaidOutInvalidFullSpan) {
            return false;
        }
        int n3 = this.mShouldReverseLayout ? -1 : 1;
        LazySpanLookup.FullSpanItem fullSpanItem = this.mLazySpanLookup.getFirstFullSpanItemInRange(n, n2 + 1, n3, true);
        if (fullSpanItem == null) {
            this.mLaidOutInvalidFullSpan = false;
            this.mLazySpanLookup.forceInvalidateAfter(n2 + 1);
            return false;
        }
        LazySpanLookup.FullSpanItem fullSpanItem2 = this.mLazySpanLookup.getFirstFullSpanItemInRange(n, fullSpanItem.mPosition, n3 * -1, true);
        if (fullSpanItem2 == null) {
            this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem.mPosition);
        } else {
            this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem2.mPosition + 1);
        }
        this.requestSimpleAnimationsInNextLayout();
        this.requestLayout();
        return true;
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() != 0 && n != 0) {
            this.prepareLayoutStateForDelta(n, state);
            if (this.mPrefetchDistances == null || this.mPrefetchDistances.length < this.mSpanCount) {
                this.mPrefetchDistances = new int[this.mSpanCount];
            }
            n = 0;
            for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                int n3 = this.mLayoutState.mItemDirection == -1 ? this.mLayoutState.mStartLine - this.mSpans[n2].getStartLine(this.mLayoutState.mStartLine) : this.mSpans[n2].getEndLine(this.mLayoutState.mEndLine) - this.mLayoutState.mEndLine;
                int n4 = n;
                if (n3 >= 0) {
                    this.mPrefetchDistances[n] = n3;
                    n4 = n + 1;
                }
                n = n4;
            }
            Arrays.sort(this.mPrefetchDistances, 0, n);
            for (n2 = 0; n2 < n && this.mLayoutState.hasMore(state); layoutState.mCurrentPosition += this.mLayoutState.mItemDirection, ++n2) {
                layoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[n2]);
                LayoutState layoutState = this.mLayoutState;
            }
        }
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        n = this.calculateScrollDirectionForPosition(n);
        PointF pointF = new PointF();
        if (n == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = n;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = n;
        return pointF;
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    /*
     * Enabled aggressive block sorting
     */
    View findFirstVisibleItemClosestToEnd(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        View view = null;
        int n3 = this.getChildCount() - 1;
        while (n3 >= 0) {
            View view2 = this.getChildAt(n3);
            int n4 = this.mPrimaryOrientation.getDecoratedStart(view2);
            int n5 = this.mPrimaryOrientation.getDecoratedEnd(view2);
            View view3 = view;
            if (n5 > n) {
                if (n4 >= n2) {
                    view3 = view;
                } else {
                    if (n5 <= n2 || !bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                }
            }
            --n3;
            view = view3;
        }
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    View findFirstVisibleItemClosestToStart(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        int n3 = this.getChildCount();
        View view = null;
        int n4 = 0;
        while (n4 < n3) {
            View view2 = this.getChildAt(n4);
            int n5 = this.mPrimaryOrientation.getDecoratedStart(view2);
            View view3 = view;
            if (this.mPrimaryOrientation.getDecoratedEnd(view2) > n) {
                if (n5 >= n2) {
                    view3 = view;
                } else {
                    if (n5 >= n || !bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                }
            }
            ++n4;
            view = view3;
        }
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    int findFirstVisibleItemPositionInt() {
        View view = this.mShouldReverseLayout ? this.findFirstVisibleItemClosestToEnd(true) : this.findFirstVisibleItemClosestToStart(true);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        return super.getColumnCountForAccessibility(recycler, state);
    }

    int getFirstChildPosition() {
        if (this.getChildCount() == 0) {
            return 0;
        }
        return this.getPosition(this.getChildAt(0));
    }

    int getLastChildPosition() {
        int n = this.getChildCount();
        if (n == 0) {
            return 0;
        }
        return this.getPosition(this.getChildAt(n - 1));
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return super.getRowCountForAccessibility(recycler, state);
    }

    /*
     * Enabled aggressive block sorting
     */
    View hasGapsToFix() {
        int n;
        int n2;
        int n3 = this.getChildCount() - 1;
        BitSet bitSet = new BitSet(this.mSpanCount);
        bitSet.set(0, this.mSpanCount, true);
        int n4 = this.mOrientation == 1 && this.isLayoutRTL() ? 1 : -1;
        if (this.mShouldReverseLayout) {
            n = 0 - 1;
        } else {
            n2 = 0;
            n = n3 + 1;
            n3 = n2;
        }
        n2 = n3 < n ? 1 : -1;
        int n5 = n3;
        do {
            block14: {
                LayoutParams layoutParams;
                View view;
                View view2;
                int n6;
                block16: {
                    int n7;
                    block17: {
                        block13: {
                            block15: {
                                block12: {
                                    if (n5 == n) {
                                        return null;
                                    }
                                    view = this.getChildAt(n5);
                                    layoutParams = (LayoutParams)view.getLayoutParams();
                                    if (!bitSet.get(layoutParams.mSpan.mIndex)) break block12;
                                    if (this.checkSpanForGap(layoutParams.mSpan)) break block13;
                                    bitSet.clear(layoutParams.mSpan.mIndex);
                                }
                                if (layoutParams.mFullSpan || n5 + n2 == n) break block14;
                                view2 = this.getChildAt(n5 + n2);
                                n3 = 0;
                                if (!this.mShouldReverseLayout) break block15;
                                n6 = this.mPrimaryOrientation.getDecoratedEnd(view);
                                if (n6 < (n7 = this.mPrimaryOrientation.getDecoratedEnd(view2))) break block13;
                                if (n6 == n7) {
                                    n3 = 1;
                                }
                                break block16;
                            }
                            n6 = this.mPrimaryOrientation.getDecoratedStart(view);
                            if (n6 <= (n7 = this.mPrimaryOrientation.getDecoratedStart(view2))) break block17;
                        }
                        return view;
                    }
                    if (n6 == n7) {
                        n3 = 1;
                    }
                }
                if (n3 != 0) {
                    LayoutParams layoutParams2 = (LayoutParams)view2.getLayoutParams();
                    n3 = layoutParams.mSpan.mIndex - layoutParams2.mSpan.mIndex < 0 ? 1 : 0;
                    if (n3 != (n6 = n4 < 0 ? 1 : 0)) {
                        return view;
                    }
                }
            }
            n5 += n2;
        } while (true);
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    boolean isLayoutRTL() {
        return this.getLayoutDirection() == 1;
    }

    @Override
    public void offsetChildrenHorizontal(int n) {
        super.offsetChildrenHorizontal(n);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].onOffset(n);
        }
    }

    @Override
    public void offsetChildrenVertical(int n) {
        super.offsetChildrenVertical(n);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].onOffset(n);
        }
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        this.removeCallbacks(this.mCheckForGapsRunnable);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].clear();
        }
        recyclerView.requestLayout();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public View onFocusSearchFailed(View object, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        void var1_3;
        void var4_20;
        View view;
        View view2;
        int n2;
        int n3;
        if (this.getChildCount() == 0) {
            return var1_3;
        }
        View view3 = this.findContainingItemView((View)object);
        if (view3 == null) {
            return null;
        }
        this.resolveShouldLayoutReverse();
        int n4 = this.convertFocusDirectionToLayoutDirection(n2);
        if (n4 == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
        boolean bl = layoutParams.mFullSpan;
        Span span = layoutParams.mSpan;
        n2 = n4 == 1 ? this.getLastChildPosition() : this.getFirstChildPosition();
        this.updateLayoutState(n2, (RecyclerView.State)var4_20);
        this.setLayoutStateDirection(n4);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + n2;
        this.mLayoutState.mAvailable = (int)(0.33333334f * (float)this.mPrimaryOrientation.getTotalSpace());
        this.mLayoutState.mStopInFocusable = true;
        this.mLayoutState.mRecycle = false;
        this.fill((RecyclerView.Recycler)view2, this.mLayoutState, (RecyclerView.State)var4_20);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        if (!bl && (view2 = span.getFocusableViewAfter(n2, n4)) != null) {
            View view4 = view2;
            if (view2 != view3) return var1_3;
        }
        if (this.preferLastSpan(n4)) {
            for (n3 = this.mSpanCount - 1; n3 >= 0; --n3) {
                view2 = this.mSpans[n3].getFocusableViewAfter(n2, n4);
                if (view2 == null) continue;
                View view5 = view2;
                if (view2 != view3) return var1_3;
                {
                    continue;
                }
            }
        } else {
            for (n3 = 0; n3 < this.mSpanCount; ++n3) {
                view2 = this.mSpans[n3].getFocusableViewAfter(n2, n4);
                if (view2 == null) continue;
                View view6 = view2;
                if (view2 == view3) continue;
                return var1_3;
            }
        }
        n2 = !this.mReverseLayout ? 1 : 0;
        n3 = n4 == -1 ? 1 : 0;
        n2 = n2 == n3 ? 1 : 0;
        if (!bl && (view = this.findViewByPosition(n3 = n2 != 0 ? span.findFirstPartiallyVisibleItemPosition() : span.findLastPartiallyVisibleItemPosition())) != null && view != view3) {
            return view;
        }
        if (this.preferLastSpan(n4)) {
            for (n3 = this.mSpanCount - 1; n3 >= 0; --n3) {
                View view7;
                if (n3 == span.mIndex || (view7 = this.findViewByPosition(n4 = n2 != 0 ? this.mSpans[n3].findFirstPartiallyVisibleItemPosition() : this.mSpans[n3].findLastPartiallyVisibleItemPosition())) == null || view7 == view3) continue;
                return view7;
            }
            return null;
        }
        for (n3 = 0; n3 < this.mSpanCount; ++n3) {
            n4 = n2 != 0 ? this.mSpans[n3].findFirstPartiallyVisibleItemPosition() : this.mSpans[n3].findLastPartiallyVisibleItemPosition();
            View view8 = this.findViewByPosition(n4);
            if (view8 == null || view8 == view3) continue;
            return view8;
        }
        return null;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        View view;
        View view2;
        int n;
        block5: {
            block4: {
                super.onInitializeAccessibilityEvent(accessibilityEvent);
                if (this.getChildCount() <= 0) break block4;
                view2 = this.findFirstVisibleItemClosestToStart(false);
                view = this.findFirstVisibleItemClosestToEnd(false);
                if (view2 != null && view != null) break block5;
            }
            return;
        }
        int n2 = this.getPosition(view2);
        if (n2 < (n = this.getPosition(view))) {
            accessibilityEvent.setFromIndex(n2);
            accessibilityEvent.setToIndex(n);
            return;
        }
        accessibilityEvent.setFromIndex(n);
        accessibilityEvent.setToIndex(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler object, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        object = view.getLayoutParams();
        if (!(object instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)((Object)object);
        if (this.mOrientation == 0) {
            int n = ((LayoutParams)((Object)object)).getSpanIndex();
            int n2 = ((LayoutParams)object).mFullSpan ? this.mSpanCount : 1;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, n2, -1, -1, ((LayoutParams)object).mFullSpan, false));
            return;
        }
        int n = ((LayoutParams)((Object)object)).getSpanIndex();
        int n3 = ((LayoutParams)object).mFullSpan ? this.mSpanCount : 1;
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, n, n3, ((LayoutParams)object).mFullSpan, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 1);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.handleUpdate(n, n2, 8);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 2);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.handleUpdate(n, n2, 4);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.onLayoutChildren(recycler, state, true);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState)parcelable;
            this.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.mReverseLayout = this.mReverseLayout;
        savedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        savedState.mLastLayoutRTL = this.mLastLayoutRTL;
        if (this.mLazySpanLookup != null && this.mLazySpanLookup.mData != null) {
            savedState.mSpanLookup = this.mLazySpanLookup.mData;
            savedState.mSpanLookupSize = savedState.mSpanLookup.length;
            savedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        } else {
            savedState.mSpanLookupSize = 0;
        }
        if (this.getChildCount() <= 0) {
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
            savedState.mSpanOffsetsSize = 0;
            return savedState;
        }
        int n = this.mLastLayoutFromEnd ? this.getLastChildPosition() : this.getFirstChildPosition();
        savedState.mAnchorPosition = n;
        savedState.mVisibleAnchorPosition = this.findFirstVisibleItemPositionInt();
        savedState.mSpanOffsetsSize = this.mSpanCount;
        savedState.mSpanOffsets = new int[this.mSpanCount];
        int n2 = 0;
        do {
            int n3;
            SavedState savedState2 = savedState;
            if (n2 >= this.mSpanCount) return savedState2;
            if (this.mLastLayoutFromEnd) {
                n = n3 = this.mSpans[n2].getEndLine(Integer.MIN_VALUE);
                if (n3 != Integer.MIN_VALUE) {
                    n = n3 - this.mPrimaryOrientation.getEndAfterPadding();
                }
            } else {
                n = n3 = this.mSpans[n2].getStartLine(Integer.MIN_VALUE);
                if (n3 != Integer.MIN_VALUE) {
                    n = n3 - this.mPrimaryOrientation.getStartAfterPadding();
                }
            }
            savedState.mSpanOffsets[n2] = n;
            ++n2;
        } while (true);
    }

    @Override
    public void onScrollStateChanged(int n) {
        if (n == 0) {
            this.checkForGaps();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void prepareLayoutStateForDelta(int n, RecyclerView.State state) {
        int n2;
        int n3;
        if (n > 0) {
            n2 = 1;
            n3 = this.getLastChildPosition();
        } else {
            n2 = -1;
            n3 = this.getFirstChildPosition();
        }
        this.mLayoutState.mRecycle = true;
        this.updateLayoutState(n3, state);
        this.setLayoutStateDirection(n2);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + n3;
        this.mLayoutState.mAvailable = Math.abs(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() == 0 || n == 0) {
            return 0;
        }
        this.prepareLayoutStateForDelta(n, state);
        int n2 = this.fill(recycler, this.mLayoutState, state);
        if (this.mLayoutState.mAvailable >= n2) {
            n = n < 0 ? -n2 : n2;
        }
        this.mPrimaryOrientation.offsetChildren(-n);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        this.mLayoutState.mAvailable = 0;
        this.recycle(recycler, this.mLayoutState);
        return n;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        if (this.mPendingSavedState != null && this.mPendingSavedState.mAnchorPosition != n) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setMeasuredDimension(Rect rect, int n, int n2) {
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            int n5 = StaggeredGridLayoutManager.chooseSize(n2, rect.height() + n4, this.getMinimumHeight());
            n2 = StaggeredGridLayoutManager.chooseSize(n, this.mSizePerSpan * this.mSpanCount + n3, this.getMinimumWidth());
            n = n5;
        } else {
            int n6 = StaggeredGridLayoutManager.chooseSize(n, rect.width() + n3, this.getMinimumWidth());
            n = StaggeredGridLayoutManager.chooseSize(n2, this.mSizePerSpan * this.mSpanCount + n4, this.getMinimumHeight());
            n2 = n6;
        }
        this.setMeasuredDimension(n2, n);
    }

    public void setOrientation(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mOrientation) {
            return;
        }
        this.mOrientation = n;
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        this.mPrimaryOrientation = this.mSecondaryOrientation;
        this.mSecondaryOrientation = orientationHelper;
        this.requestLayout();
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (this.mPendingSavedState != null && this.mPendingSavedState.mReverseLayout != bl) {
            this.mPendingSavedState.mReverseLayout = bl;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSpanCount(int n) {
        this.assertNotInLayoutOrScroll(null);
        if (n != this.mSpanCount) {
            this.invalidateSpanAssignments();
            this.mSpanCount = n;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (n = 0; n < this.mSpanCount; ++n) {
                this.mSpans[n] = new Span(n);
            }
            this.requestLayout();
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(object.getContext());
        ((RecyclerView.SmoothScroller)object).setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean updateAnchorFromPendingData(RecyclerView.State state, AnchorInfo anchorInfo) {
        boolean bl = false;
        if (state.isPreLayout()) return false;
        if (this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= state.getItemCount()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        if (this.mPendingSavedState != null && this.mPendingSavedState.mAnchorPosition != -1 && this.mPendingSavedState.mSpanOffsetsSize >= 1) {
            anchorInfo.mOffset = Integer.MIN_VALUE;
            anchorInfo.mPosition = this.mPendingScrollPosition;
            return true;
        }
        state = this.findViewByPosition(this.mPendingScrollPosition);
        if (state != null) {
            int n = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
            anchorInfo.mPosition = n;
            if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                if (anchorInfo.mLayoutFromEnd) {
                    anchorInfo.mOffset = this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd((View)state);
                    return true;
                }
                anchorInfo.mOffset = this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart((View)state);
                return true;
            }
            if (this.mPrimaryOrientation.getDecoratedMeasurement((View)state) > this.mPrimaryOrientation.getTotalSpace()) {
                n = anchorInfo.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                anchorInfo.mOffset = n;
                return true;
            }
            n = this.mPrimaryOrientation.getDecoratedStart((View)state) - this.mPrimaryOrientation.getStartAfterPadding();
            if (n < 0) {
                anchorInfo.mOffset = -n;
                return true;
            }
            n = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd((View)state);
            if (n < 0) {
                anchorInfo.mOffset = n;
                return true;
            }
            anchorInfo.mOffset = Integer.MIN_VALUE;
            return true;
        }
        anchorInfo.mPosition = this.mPendingScrollPosition;
        if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
            if (this.calculateScrollDirectionForPosition(anchorInfo.mPosition) == 1) {
                bl = true;
            }
            anchorInfo.mLayoutFromEnd = bl;
            anchorInfo.assignCoordinateFromPadding();
        } else {
            anchorInfo.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
        }
        anchorInfo.mInvalidateOffsets = true;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateAnchorInfoForLayout(RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo) || this.updateAnchorFromChildren(state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        anchorInfo.mPosition = 0;
    }

    void updateMeasureSpecs(int n) {
        this.mSizePerSpan = n / this.mSpanCount;
        this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec((int)n, (int)this.mSecondaryOrientation.getMode());
    }

    class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;
        int[] mSpanReferenceLines;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        /*
         * Enabled aggressive block sorting
         */
        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() : StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            this.mOffset = n;
        }

        void assignCoordinateFromPadding(int n) {
            if (this.mLayoutFromEnd) {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - n;
                return;
            }
            this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + n;
        }

        void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            if (this.mSpanReferenceLines != null) {
                Arrays.fill(this.mSpanReferenceLines, -1);
            }
        }

        void saveSpanReferenceLines(Span[] arrspan) {
            int n = arrspan.length;
            if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < n) {
                this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
            }
            for (int i = 0; i < n; ++i) {
                this.mSpanReferenceLines[i] = arrspan[i].getStartLine(Integer.MIN_VALUE);
            }
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        boolean mFullSpan;
        Span mSpan;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public final int getSpanIndex() {
            if (this.mSpan == null) {
                return -1;
            }
            return this.mSpan.mIndex;
        }

        public boolean isFullSpan() {
            return this.mFullSpan;
        }
    }

    static class LazySpanLookup {
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        LazySpanLookup() {
        }

        /*
         * Enabled aggressive block sorting
         */
        private int invalidateFullSpansAfter(int n) {
            FullSpanItem fullSpanItem;
            int n2;
            block3: {
                block4: {
                    if (this.mFullSpanItems == null) break block4;
                    fullSpanItem = this.getFullSpanItem(n);
                    if (fullSpanItem != null) {
                        this.mFullSpanItems.remove(fullSpanItem);
                    }
                    int n3 = -1;
                    int n4 = this.mFullSpanItems.size();
                    int n5 = 0;
                    do {
                        block6: {
                            block5: {
                                n2 = n3;
                                if (n5 >= n4) break block5;
                                if (this.mFullSpanItems.get((int)n5).mPosition < n) break block6;
                                n2 = n5;
                            }
                            if (n2 == -1) break;
                            break block3;
                        }
                        ++n5;
                    } while (true);
                }
                return -1;
            }
            fullSpanItem = this.mFullSpanItems.get(n2);
            this.mFullSpanItems.remove(n2);
            return fullSpanItem.mPosition;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void offsetFullSpansForAddition(int n, int n2) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                    FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                    if (fullSpanItem.mPosition < n) continue;
                    fullSpanItem.mPosition += n2;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void offsetFullSpansForRemoval(int n, int n2) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                    FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                    if (fullSpanItem.mPosition < n) continue;
                    if (fullSpanItem.mPosition < n + n2) {
                        this.mFullSpanItems.remove(i);
                        continue;
                    }
                    fullSpanItem.mPosition -= n2;
                }
            }
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList<FullSpanItem>();
            }
            int n = this.mFullSpanItems.size();
            for (int i = 0; i < n; ++i) {
                FullSpanItem fullSpanItem2 = this.mFullSpanItems.get(i);
                if (fullSpanItem2.mPosition == fullSpanItem.mPosition) {
                    this.mFullSpanItems.remove(i);
                }
                if (fullSpanItem2.mPosition < fullSpanItem.mPosition) continue;
                this.mFullSpanItems.add(i, fullSpanItem);
                return;
            }
            this.mFullSpanItems.add(fullSpanItem);
        }

        void clear() {
            if (this.mData != null) {
                Arrays.fill(this.mData, -1);
            }
            this.mFullSpanItems = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        void ensureSize(int n) {
            if (this.mData == null) {
                this.mData = new int[Math.max(n, 10) + 1];
                Arrays.fill(this.mData, -1);
                return;
            } else {
                if (n < this.mData.length) return;
                {
                    int[] arrn = this.mData;
                    this.mData = new int[this.sizeForPosition(n)];
                    System.arraycopy(arrn, 0, this.mData, 0, arrn.length);
                    Arrays.fill(this.mData, arrn.length, this.mData.length, -1);
                    return;
                }
            }
        }

        int forceInvalidateAfter(int n) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                    if (this.mFullSpanItems.get((int)i).mPosition < n) continue;
                    this.mFullSpanItems.remove(i);
                }
            }
            return this.invalidateAfter(n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public FullSpanItem getFirstFullSpanItemInRange(int n, int n2, int n3, boolean bl) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            int n4 = this.mFullSpanItems.size();
            int n5 = 0;
            while (n5 < n4) {
                FullSpanItem fullSpanItem = this.mFullSpanItems.get(n5);
                if (fullSpanItem.mPosition >= n2) {
                    return null;
                }
                if (fullSpanItem.mPosition >= n) {
                    FullSpanItem fullSpanItem2 = fullSpanItem;
                    if (n3 == 0) return fullSpanItem2;
                    fullSpanItem2 = fullSpanItem;
                    if (fullSpanItem.mGapDir == n3) return fullSpanItem2;
                    if (bl) {
                        fullSpanItem2 = fullSpanItem;
                        if (fullSpanItem.mHasUnwantedGapAfter) return fullSpanItem2;
                    }
                }
                ++n5;
            }
            return null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public FullSpanItem getFullSpanItem(int n) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            int n2 = this.mFullSpanItems.size() - 1;
            while (n2 >= 0) {
                FullSpanItem fullSpanItem;
                FullSpanItem fullSpanItem2 = fullSpanItem = this.mFullSpanItems.get(n2);
                if (fullSpanItem.mPosition == n) return fullSpanItem2;
                --n2;
            }
            return null;
        }

        int getSpan(int n) {
            if (this.mData == null || n >= this.mData.length) {
                return -1;
            }
            return this.mData[n];
        }

        /*
         * Enabled aggressive block sorting
         */
        int invalidateAfter(int n) {
            if (this.mData == null || n >= this.mData.length) {
                return -1;
            }
            int n2 = this.invalidateFullSpansAfter(n);
            if (n2 == -1) {
                Arrays.fill(this.mData, n, this.mData.length, -1);
                return this.mData.length;
            }
            Arrays.fill(this.mData, n, n2 + 1, -1);
            return n2 + 1;
        }

        void offsetForAddition(int n, int n2) {
            if (this.mData == null || n >= this.mData.length) {
                return;
            }
            this.ensureSize(n + n2);
            System.arraycopy(this.mData, n, this.mData, n + n2, this.mData.length - n - n2);
            Arrays.fill(this.mData, n, n + n2, -1);
            this.offsetFullSpansForAddition(n, n2);
        }

        void offsetForRemoval(int n, int n2) {
            if (this.mData == null || n >= this.mData.length) {
                return;
            }
            this.ensureSize(n + n2);
            System.arraycopy(this.mData, n + n2, this.mData, n, this.mData.length - n - n2);
            Arrays.fill(this.mData, this.mData.length - n2, this.mData.length, -1);
            this.offsetFullSpansForRemoval(n, n2);
        }

        void setSpan(int n, Span span) {
            this.ensureSize(n);
            this.mData[n] = span.mIndex;
        }

        int sizeForPosition(int n) {
            int n2;
            for (n2 = this.mData.length; n2 <= n; n2 *= 2) {
            }
            return n2;
        }

        static class FullSpanItem
        implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>(){

                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public FullSpanItem[] newArray(int n) {
                    return new FullSpanItem[n];
                }
            };
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            FullSpanItem() {
            }

            /*
             * Enabled aggressive block sorting
             */
            FullSpanItem(Parcel parcel) {
                boolean bl = true;
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                if (parcel.readInt() != 1) {
                    bl = false;
                }
                this.mHasUnwantedGapAfter = bl;
                int n = parcel.readInt();
                if (n > 0) {
                    this.mGapPerSpan = new int[n];
                    parcel.readIntArray(this.mGapPerSpan);
                }
            }

            public int describeContents() {
                return 0;
            }

            int getGapForSpan(int n) {
                if (this.mGapPerSpan == null) {
                    return 0;
                }
                return this.mGapPerSpan[n];
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
            }

            /*
             * Enabled aggressive block sorting
             */
            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeInt(this.mPosition);
                parcel.writeInt(this.mGapDir);
                n = this.mHasUnwantedGapAfter ? 1 : 0;
                parcel.writeInt(n);
                if (this.mGapPerSpan != null && this.mGapPerSpan.length > 0) {
                    parcel.writeInt(this.mGapPerSpan.length);
                    parcel.writeIntArray(this.mGapPerSpan);
                    return;
                }
                parcel.writeInt(0);
            }

        }

    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<LazySpanLookup.FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        public SavedState() {
        }

        /*
         * Enabled aggressive block sorting
         */
        SavedState(Parcel parcel) {
            boolean bl = true;
            this.mAnchorPosition = parcel.readInt();
            this.mVisibleAnchorPosition = parcel.readInt();
            this.mSpanOffsetsSize = parcel.readInt();
            if (this.mSpanOffsetsSize > 0) {
                this.mSpanOffsets = new int[this.mSpanOffsetsSize];
                parcel.readIntArray(this.mSpanOffsets);
            }
            this.mSpanLookupSize = parcel.readInt();
            if (this.mSpanLookupSize > 0) {
                this.mSpanLookup = new int[this.mSpanLookupSize];
                parcel.readIntArray(this.mSpanLookup);
            }
            boolean bl2 = parcel.readInt() == 1;
            this.mReverseLayout = bl2;
            bl2 = parcel.readInt() == 1;
            this.mAnchorLayoutFromEnd = bl2;
            bl2 = parcel.readInt() == 1 ? bl : false;
            this.mLastLayoutRTL = bl2;
            this.mFullSpanItems = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.mSpanOffsetsSize = savedState.mSpanOffsetsSize;
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mVisibleAnchorPosition = savedState.mVisibleAnchorPosition;
            this.mSpanOffsets = savedState.mSpanOffsets;
            this.mSpanLookupSize = savedState.mSpanLookupSize;
            this.mSpanLookup = savedState.mSpanLookup;
            this.mReverseLayout = savedState.mReverseLayout;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = savedState.mLastLayoutRTL;
            this.mFullSpanItems = savedState.mFullSpanItems;
        }

        public int describeContents() {
            return 0;
        }

        void invalidateAnchorPositionInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        void invalidateSpanInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n) {
            int n2 = 1;
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mVisibleAnchorPosition);
            parcel.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                parcel.writeIntArray(this.mSpanOffsets);
            }
            parcel.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                parcel.writeIntArray(this.mSpanLookup);
            }
            n = this.mReverseLayout ? 1 : 0;
            parcel.writeInt(n);
            n = this.mAnchorLayoutFromEnd ? 1 : 0;
            parcel.writeInt(n);
            n = this.mLastLayoutRTL ? n2 : 0;
            parcel.writeInt(n);
            parcel.writeList(this.mFullSpanItems);
        }

    }

    class Span {
        int mCachedEnd = Integer.MIN_VALUE;
        int mCachedStart = Integer.MIN_VALUE;
        int mDeletedSize = 0;
        final int mIndex;
        ArrayList<View> mViews = new ArrayList();

        Span(int n) {
            this.mIndex = n;
        }

        void appendToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(view);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void cacheReferenceLineAndClear(boolean bl, int n) {
            int n2 = bl ? this.getEndLine(Integer.MIN_VALUE) : this.getStartLine(Integer.MIN_VALUE);
            this.clear();
            if (n2 == Integer.MIN_VALUE || bl && n2 < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() || !bl && n2 > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()) {
                return;
            }
            int n3 = n2;
            if (n != Integer.MIN_VALUE) {
                n3 = n2 + n;
            }
            this.mCachedEnd = n3;
            this.mCachedStart = n3;
        }

        void calculateCachedEnd() {
            Object object = this.mViews.get(this.mViews.size() - 1);
            LayoutParams layoutParams = this.getLayoutParams((View)object);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd((View)object);
            if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && ((LazySpanLookup.FullSpanItem)object).mGapDir == 1) {
                this.mCachedEnd += ((LazySpanLookup.FullSpanItem)object).getGapForSpan(this.mIndex);
            }
        }

        void calculateCachedStart() {
            Object object = this.mViews.get(0);
            LayoutParams layoutParams = this.getLayoutParams((View)object);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart((View)object);
            if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && ((LazySpanLookup.FullSpanItem)object).mGapDir == -1) {
                this.mCachedStart -= ((LazySpanLookup.FullSpanItem)object).getGapForSpan(this.mIndex);
            }
        }

        void clear() {
            this.mViews.clear();
            this.invalidateCache();
            this.mDeletedSize = 0;
        }

        public int findFirstPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }

        public int findLastPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
            }
            return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        int findOnePartiallyOrCompletelyVisibleChild(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
            int n3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            int n4 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            int n5 = n2 > n ? 1 : -1;
            int n6 = n;
            while (n6 != n2) {
                View view = this.mViews.get(n6);
                int n7 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                int n8 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
                n = bl3 ? (n7 <= n4 ? 1 : 0) : (n7 < n4 ? 1 : 0);
                boolean bl4 = bl3 ? n8 >= n3 : n8 > n3;
                if (n != 0 && bl4) {
                    if (bl && bl2) {
                        if (n7 >= n3 && n8 <= n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else {
                        if (bl2) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (n7 < n3 || n8 > n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    }
                }
                n6 += n5;
            }
            return -1;
        }

        int findOnePartiallyVisibleChild(int n, int n2, boolean bl) {
            return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, false, false, bl);
        }

        public int getDeletedSize() {
            return this.mDeletedSize;
        }

        int getEndLine() {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        int getEndLine(int n) {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            if (this.mViews.size() == 0) return n;
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public View getFocusableViewAfter(int n, int n2) {
            View view = null;
            View view2 = null;
            if (n2 == -1) {
                int n3 = this.mViews.size();
                n2 = 0;
                do {
                    view = view2;
                    if (n2 >= n3) return view;
                    View view3 = this.mViews.get(n2);
                    if (StaggeredGridLayoutManager.this.mReverseLayout) {
                        view = view2;
                        if (StaggeredGridLayoutManager.this.getPosition(view3) <= n) return view;
                    }
                    if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view3) >= n) {
                        return view2;
                    }
                    view = view2;
                    if (!view3.hasFocusable()) return view;
                    view2 = view3;
                    ++n2;
                } while (true);
            }
            n2 = this.mViews.size() - 1;
            view2 = view;
            do {
                view = view2;
                if (n2 < 0) return view;
                View view4 = this.mViews.get(n2);
                if (StaggeredGridLayoutManager.this.mReverseLayout) {
                    view = view2;
                    if (StaggeredGridLayoutManager.this.getPosition(view4) >= n) return view;
                }
                if (!StaggeredGridLayoutManager.this.mReverseLayout) {
                    view = view2;
                    if (StaggeredGridLayoutManager.this.getPosition(view4) <= n) return view;
                }
                view = view2;
                if (!view4.hasFocusable()) return view;
                view2 = view4;
                --n2;
            } while (true);
        }

        LayoutParams getLayoutParams(View view) {
            return (LayoutParams)view.getLayoutParams();
        }

        int getStartLine() {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        int getStartLine(int n) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            if (this.mViews.size() == 0) return n;
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        void invalidateCache() {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void onOffset(int n) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                this.mCachedStart += n;
            }
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                this.mCachedEnd += n;
            }
        }

        void popEnd() {
            int n = this.mViews.size();
            View view = this.mViews.remove(n - 1);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            if (n == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void popStart() {
            View view = this.mViews.remove(0);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        void prependToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(0, view);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        void setLine(int n) {
            this.mCachedStart = n;
            this.mCachedEnd = n;
        }
    }

}

