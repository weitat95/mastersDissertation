/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.List;

public class GridLayoutManager
extends LinearLayoutManager {
    int[] mCachedBorders;
    final Rect mDecorInsets;
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache;
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup;

    public GridLayoutManager(Context context, int n) {
        super(context);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, int n, int n2, boolean bl) {
        super(context, n2, bl);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        this.setSpanCount(GridLayoutManager.getProperties((Context)context, (AttributeSet)attributeSet, (int)n, (int)n2).spanCount);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, boolean bl) {
        int n3;
        int n4;
        if (bl) {
            n3 = 0;
            n4 = n;
            n2 = 1;
            n = n3;
        } else {
            --n;
            n4 = -1;
            n2 = -1;
        }
        n3 = 0;
        while (n != n4) {
            View view = this.mSet[n];
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.mSpanSize = this.getSpanSize(recycler, state, this.getPosition(view));
            layoutParams.mSpanIndex = n3;
            n3 += layoutParams.mSpanSize;
            n += n2;
        }
        return;
    }

    private void cachePreLayoutSpanMapping() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
            int n2 = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(n2, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(n2, layoutParams.getSpanIndex());
        }
    }

    private void calculateItemBorders(int n) {
        this.mCachedBorders = GridLayoutManager.calculateItemBorders(this.mCachedBorders, this.mSpanCount, n);
    }

    static int[] calculateItemBorders(int[] arrn, int n, int n2) {
        int[] arrn2;
        block7: {
            block6: {
                if (arrn == null || arrn.length != n + 1) break block6;
                arrn2 = arrn;
                if (arrn[arrn.length - 1] == n2) break block7;
            }
            arrn2 = new int[n + 1];
        }
        arrn2[0] = 0;
        int n3 = n2 / n;
        int n4 = n2 % n;
        int n5 = 0;
        n2 = 0;
        for (int i = 1; i <= n; ++i) {
            int n6;
            int n7 = n3;
            n2 = n6 = n2 + n4;
            int n8 = n7;
            if (n6 > 0) {
                n2 = n6;
                n8 = n7;
                if (n - n6 < n4) {
                    n8 = n7 + 1;
                    n2 = n6 - n;
                }
            }
            arrn2[i] = n5 += n8;
        }
        return arrn2;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        int n2 = 1;
        if (n != 1) {
            n2 = 0;
        }
        n = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (n2 != 0) {
            while (n > 0 && anchorInfo.mPosition > 0) {
                --anchorInfo.mPosition;
                n = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
            return;
        } else {
            int n3;
            int n4 = state.getItemCount();
            for (n2 = anchorInfo.mPosition; n2 < n4 - 1 && (n3 = this.getSpanIndex(recycler, state, n2 + 1)) > n; ++n2) {
                n = n3;
            }
            anchorInfo.mPosition = n2;
        }
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    private int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(n, this.mSpanCount);
        }
        int n2 = recycler.convertPreLayoutPositionToPostLayout(n);
        if (n2 == -1) {
            Log.w((String)"GridLayoutManager", (String)("Cannot find span size for pre layout position. " + n));
            return 0;
        }
        return this.mSpanSizeLookup.getSpanGroupIndex(n2, this.mSpanCount);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getSpanIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int n) {
        int n2;
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(n, this.mSpanCount);
        }
        int n3 = n2 = this.mPreLayoutSpanIndexCache.get(n, -1);
        if (n2 != -1) return n3;
        n3 = recycler.convertPreLayoutPositionToPostLayout(n);
        if (n3 != -1) return this.mSpanSizeLookup.getCachedSpanIndex(n3, this.mSpanCount);
        Log.w((String)"GridLayoutManager", (String)("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + n));
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getSpanSize(RecyclerView.Recycler recycler, RecyclerView.State state, int n) {
        int n2;
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(n);
        }
        int n3 = n2 = this.mPreLayoutSpanSizeCache.get(n, -1);
        if (n2 != -1) return n3;
        n3 = recycler.convertPreLayoutPositionToPostLayout(n);
        if (n3 != -1) return this.mSpanSizeLookup.getSpanSize(n3);
        Log.w((String)"GridLayoutManager", (String)("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + n));
        return 1;
    }

    private void guessMeasurement(float f, int n) {
        this.calculateItemBorders(Math.max(Math.round((float)this.mSpanCount * f), n));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChild(View view, int n, boolean bl) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int n2 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int n3 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int n4 = this.getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            n3 = GridLayoutManager.getChildMeasureSpec(n4, n, n3, layoutParams.width, false);
            n = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getHeightMode(), n2, layoutParams.height, true);
        } else {
            n = GridLayoutManager.getChildMeasureSpec(n4, n, n2, layoutParams.height, false);
            n3 = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getWidthMode(), n3, layoutParams.width, true);
        }
        this.measureChildWithDecorationsAndMargin(view, n3, n, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        if (bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams)) {
            view.measure(n, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateMeasurements() {
        int n = this.getOrientation() == 1 ? this.getWidth() - this.getPaddingRight() - this.getPaddingLeft() : this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        this.calculateItemBorders(n);
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = this.mSpanCount;
        for (int i = 0; i < this.mSpanCount && layoutState.hasMore(state) && n > 0; ++i) {
            int n2 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(n2, Math.max(0, layoutState.mScrollingOffset));
            n -= this.mSpanSizeLookup.getSpanSize(n2);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, int n3) {
        this.ensureLayoutState();
        View view = null;
        View view2 = null;
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        while (n != n2) {
            View view3 = this.getChildAt(n);
            int n7 = this.getPosition(view3);
            View view4 = view;
            View view5 = view2;
            if (n7 >= 0) {
                view4 = view;
                view5 = view2;
                if (n7 < n3) {
                    if (this.getSpanIndex(recycler, state, n7) != 0) {
                        view5 = view2;
                        view4 = view;
                    } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).isItemRemoved()) {
                        view4 = view;
                        view5 = view2;
                        if (view == null) {
                            view4 = view3;
                            view5 = view2;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view3) < n5) {
                            view4 = view3;
                            if (this.mOrientationHelper.getDecoratedEnd(view3) >= n4) return view4;
                        }
                        view4 = view;
                        view5 = view2;
                        if (view2 == null) {
                            view4 = view;
                            view5 = view3;
                        }
                    }
                }
            }
            n += n6;
            view = view4;
            view2 = view5;
        }
        if (view2 != null) return view2;
        view2 = view;
        return view2;
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
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    int getSpaceForSpanRange(int n, int n2) {
        if (this.mOrientation == 1 && this.isLayoutRTL()) {
            return this.mCachedBorders[this.mSpanCount - n] - this.mCachedBorders[this.mSpanCount - n - n2];
        }
        return this.mCachedBorders[n + n2] - this.mCachedBorders[n];
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State object, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        View view;
        int n = this.mOrientationHelper.getModeInOther();
        int n2 = n != 1073741824 ? 1 : 0;
        int n3 = this.getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (n2 != 0) {
            this.updateMeasurements();
        }
        boolean bl = layoutState.mItemDirection == 1;
        int n4 = 0;
        int n5 = 0;
        int n6 = this.mSpanCount;
        int n7 = n4;
        int n8 = n5;
        if (!bl) {
            n6 = this.getSpanIndex(recycler, (RecyclerView.State)object, layoutState.mCurrentPosition) + this.getSpanSize(recycler, (RecyclerView.State)object, layoutState.mCurrentPosition);
            n8 = n5;
            n7 = n4;
        }
        while (n7 < this.mSpanCount && layoutState.hasMore((RecyclerView.State)object) && n6 > 0) {
            n5 = layoutState.mCurrentPosition;
            n4 = this.getSpanSize(recycler, (RecyclerView.State)object, n5);
            if (n4 > this.mSpanCount) {
                throw new IllegalArgumentException("Item at position " + n5 + " requires " + n4 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
            }
            if ((n6 -= n4) < 0 || (view = layoutState.next(recycler)) == null) break;
            n8 += n4;
            this.mSet[n7] = view;
            ++n7;
        }
        if (n7 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        n6 = 0;
        float f = 0.0f;
        this.assignSpans(recycler, (RecyclerView.State)object, n7, n8, bl);
        for (n8 = 0; n8 < n7; ++n8) {
            recycler = this.mSet[n8];
            if (layoutState.mScrapList == null) {
                if (bl) {
                    this.addView((View)recycler);
                } else {
                    this.addView((View)recycler, 0);
                }
            } else if (bl) {
                this.addDisappearingView((View)recycler);
            } else {
                this.addDisappearingView((View)recycler, 0);
            }
            this.calculateItemDecorationsForChild((View)recycler, this.mDecorInsets);
            this.measureChild((View)recycler, n, false);
            n5 = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
            n4 = n6;
            if (n5 > n6) {
                n4 = n5;
            }
            object = (LayoutParams)recycler.getLayoutParams();
            float f2 = 1.0f * (float)this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) / (float)((LayoutParams)object).mSpanSize;
            float f3 = f;
            if (f2 > f) {
                f3 = f2;
            }
            n6 = n4;
            f = f3;
        }
        n8 = n6;
        if (n2 != 0) {
            this.guessMeasurement(f, n3);
            n6 = 0;
            n2 = 0;
            do {
                n8 = n6;
                if (n2 >= n7) break;
                recycler = this.mSet[n2];
                this.measureChild((View)recycler, 1073741824, true);
                n3 = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
                n8 = n6;
                if (n3 > n6) {
                    n8 = n3;
                }
                ++n2;
                n6 = n8;
            } while (true);
        }
        for (n6 = 0; n6 < n7; ++n6) {
            recycler = this.mSet[n6];
            if (this.mOrientationHelper.getDecoratedMeasurement((View)recycler) == n8) continue;
            object = (LayoutParams)recycler.getLayoutParams();
            view = ((LayoutParams)object).mDecorInsets;
            n2 = view.top + view.bottom + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin;
            n3 = view.left + view.right + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin;
            n4 = this.getSpaceForSpanRange(((LayoutParams)object).mSpanIndex, ((LayoutParams)object).mSpanSize);
            if (this.mOrientation == 1) {
                n3 = GridLayoutManager.getChildMeasureSpec(n4, 1073741824, n3, ((LayoutParams)object).width, false);
                n2 = View.MeasureSpec.makeMeasureSpec((int)(n8 - n2), (int)1073741824);
            } else {
                n3 = View.MeasureSpec.makeMeasureSpec((int)(n8 - n3), (int)1073741824);
                n2 = GridLayoutManager.getChildMeasureSpec(n4, 1073741824, n2, ((LayoutParams)object).height, false);
            }
            this.measureChildWithDecorationsAndMargin((View)recycler, n3, n2, true);
        }
        layoutChunkResult.mConsumed = n8;
        n6 = 0;
        n4 = 0;
        n2 = 0;
        n3 = 0;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                n3 = layoutState.mOffset;
                n2 = n3 - n8;
                n8 = n4;
            } else {
                n2 = layoutState.mOffset;
                n3 = n2 + n8;
                n8 = n4;
            }
        } else if (layoutState.mLayoutDirection == -1) {
            n4 = layoutState.mOffset;
            n6 = n4 - n8;
            n8 = n4;
        } else {
            n6 = layoutState.mOffset;
            n8 = n6 + n8;
        }
        n4 = 0;
        n5 = n8;
        n8 = n2;
        do {
            if (n4 >= n7) {
                Arrays.fill((Object[])this.mSet, null);
                return;
            }
            recycler = this.mSet[n4];
            object = (LayoutParams)recycler.getLayoutParams();
            if (this.mOrientation == 1) {
                if (this.isLayoutRTL()) {
                    n2 = this.getPaddingLeft() + this.mCachedBorders[this.mSpanCount - ((LayoutParams)object).mSpanIndex];
                    n6 = n2 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                } else {
                    n6 = this.getPaddingLeft() + this.mCachedBorders[((LayoutParams)object).mSpanIndex];
                    n2 = n6 + this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                }
            } else {
                n8 = this.getPaddingTop() + this.mCachedBorders[((LayoutParams)object).mSpanIndex];
                n3 = n8 + this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
                n2 = n5;
            }
            this.layoutDecoratedWithMargins((View)recycler, n6, n8, n2, n3);
            if (((RecyclerView.LayoutParams)((Object)object)).isItemRemoved() || ((RecyclerView.LayoutParams)((Object)object)).isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= recycler.hasFocusable();
            ++n4;
            n5 = n2;
        } while (true);
    }

    @Override
    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        super.onAnchorReady(recycler, state, anchorInfo, n);
        this.updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            this.ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, n);
        }
        this.ensureViewSet();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public View onFocusSearchFailed(View var1_1, int var2_2, RecyclerView.Recycler var3_3, RecyclerView.State var4_4) {
        var27_5 = this.findContainingItemView(var1_1);
        if (var27_5 == null) {
            return null;
        }
        var23_7 = (LayoutParams)var27_5.getLayoutParams();
        var17_8 = var23_7.mSpanIndex;
        var18_9 = var23_7.mSpanIndex + var23_7.mSpanSize;
        if (super.onFocusSearchFailed(var1_1, var2_2, var3_3, var4_4) == null) {
            return null;
        }
        var22_10 = this.convertFocusDirectionToLayoutDirection(var2_2) == 1;
        var2_2 = var22_10 != this.mShouldReverseLayout ? 1 : 0;
        if (var2_2 != 0) {
            var2_2 = this.getChildCount() - 1;
            var5_11 = -1;
            var6_12 = -1;
        } else {
            var2_2 = 0;
            var5_11 = 1;
            var6_12 = this.getChildCount();
        }
        var7_13 = this.mOrientation == 1 && this.isLayoutRTL() != false ? 1 : 0;
        var23_7 = null;
        var11_14 = -1;
        var12_15 = 0;
        var1_1 = null;
        var9_16 = -1;
        var10_17 = 0;
        var19_18 = this.getSpanGroupIndex(var3_3, var4_4, var2_2);
        var8_19 = var2_2;
        do {
            if (var8_19 == var6_12) ** GOTO lbl-1000
            var2_2 = this.getSpanGroupIndex(var3_3, var4_4, var8_19);
            var24_26 = this.getChildAt(var8_19);
            if (var24_26 == var27_5) ** GOTO lbl-1000
            if (var24_26.hasFocusable() && var2_2 != var19_18) {
                ** if (var23_7 != null) goto lbl-1000
lbl-1000:
                // 1 sources
                {
                    var16_23 = var9_16;
                    var15_22 = var10_17;
                    var26_27 = var1_1;
                    var14_21 = var11_14;
                    var13_20 = var12_15;
                    var25_6 = var23_7;
                    ** GOTO lbl102
                }
            }
            ** GOTO lbl45
lbl-1000:
            // 3 sources
            {
                if (var23_7 == null) return var1_1;
                return var23_7;
lbl45:
                // 1 sources
                var28_28 = (LayoutParams)var24_26.getLayoutParams();
                var20_24 = var28_28.mSpanIndex;
                var21_25 = var28_28.mSpanIndex + var28_28.mSpanSize;
                if (var24_26.hasFocusable() && var20_24 == var17_8) {
                    var25_6 = var24_26;
                    if (var21_25 == var18_9) return var25_6;
                }
                var14_21 = 0;
                if (var24_26.hasFocusable() && var23_7 == null || !var24_26.hasFocusable() && var1_1 == null) {
                    var2_2 = 1;
                } else {
                    var2_2 = Math.max(var20_24, var17_8);
                    var13_20 = Math.min(var21_25, var18_9) - var2_2;
                    if (var24_26.hasFocusable()) {
                        if (var13_20 > var12_15) {
                            var2_2 = 1;
                        } else {
                            var2_2 = var14_21;
                            if (var13_20 == var12_15) {
                                var13_20 = var20_24 > var11_14 ? 1 : 0;
                                var2_2 = var14_21;
                                if (var7_13 == var13_20) {
                                    var2_2 = 1;
                                }
                            }
                        }
                    } else {
                        var2_2 = var14_21;
                        if (var23_7 == null) {
                            var2_2 = var14_21;
                            if (this.isViewPartiallyVisible(var24_26, false, true)) {
                                if (var13_20 > var10_17) {
                                    var2_2 = 1;
                                } else {
                                    var2_2 = var14_21;
                                    if (var13_20 == var10_17) {
                                        var13_20 = var20_24 > var9_16 ? 1 : 0;
                                        var2_2 = var14_21;
                                        if (var7_13 == var13_20) {
                                            var2_2 = 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                var25_6 = var23_7;
                var13_20 = var12_15;
                var14_21 = var11_14;
                var26_27 = var1_1;
                var15_22 = var10_17;
                var16_23 = var9_16;
                if (var2_2 != 0) {
                    if (var24_26.hasFocusable()) {
                        var14_21 = var28_28.mSpanIndex;
                        var13_20 = Math.min(var21_25, var18_9) - Math.max(var20_24, var17_8);
                        var25_6 = var24_26;
                        var26_27 = var1_1;
                        var15_22 = var10_17;
                        var16_23 = var9_16;
                    } else {
                        var16_23 = var28_28.mSpanIndex;
                        var15_22 = Math.min(var21_25, var18_9) - Math.max(var20_24, var17_8);
                        var25_6 = var23_7;
                        var13_20 = var12_15;
                        var14_21 = var11_14;
                        var26_27 = var24_26;
                    }
                }
            }
lbl102:
            // 5 sources
            var8_19 += var5_11;
            var23_7 = var25_6;
            var12_15 = var13_20;
            var11_14 = var14_21;
            var1_1 = var26_27;
            var10_17 = var15_22;
            var9_16 = var16_23;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem((View)object, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)layoutParams;
        int n = this.getSpanGroupIndex(recycler, state, ((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition());
        if (this.mOrientation == 0) {
            int n2 = ((LayoutParams)((Object)object)).getSpanIndex();
            int n3 = ((LayoutParams)((Object)object)).getSpanSize();
            boolean bl = this.mSpanCount > 1 && ((LayoutParams)((Object)object)).getSpanSize() == this.mSpanCount;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, n3, n, 1, bl, false));
            return;
        }
        int n4 = ((LayoutParams)((Object)object)).getSpanIndex();
        int n5 = ((LayoutParams)((Object)object)).getSpanSize();
        boolean bl = this.mSpanCount > 1 && ((LayoutParams)((Object)object)).getSpanSize() == this.mSpanCount;
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, 1, n4, n5, bl, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            this.cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        this.clearPreLayoutSpanMappingCache();
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollHorizontallyBy(n, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollVerticallyBy(n, recycler, state);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setMeasuredDimension(Rect rect, int n, int n2) {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, n, n2);
        }
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            int n5 = GridLayoutManager.chooseSize(n2, rect.height() + n4, this.getMinimumHeight());
            n2 = GridLayoutManager.chooseSize(n, this.mCachedBorders[this.mCachedBorders.length - 1] + n3, this.getMinimumWidth());
            n = n5;
        } else {
            int n6 = GridLayoutManager.chooseSize(n, rect.width() + n3, this.getMinimumWidth());
            n = GridLayoutManager.chooseSize(n2, this.mCachedBorders[this.mCachedBorders.length - 1] + n4, this.getMinimumHeight());
            n2 = n6;
        }
        this.setMeasuredDimension(n2, n);
    }

    public void setSpanCount(int n) {
        if (n == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (n < 1) {
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + n);
        }
        this.mSpanCount = n;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.requestLayout();
    }

    @Override
    public void setStackFromEnd(boolean bl) {
        if (bl) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }

    public static final class DefaultSpanSizeLookup
    extends SpanSizeLookup {
        @Override
        public int getSpanIndex(int n, int n2) {
            return n % n2;
        }

        @Override
        public int getSpanSize(int n) {
            return 1;
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        int mSpanIndex = -1;
        int mSpanSize = 0;

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

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        int findReferenceIndexFromCache(int n) {
            int n2 = 0;
            int n3 = this.mSpanIndexCache.size() - 1;
            while (n2 <= n3) {
                int n4 = n2 + n3 >>> 1;
                if (this.mSpanIndexCache.keyAt(n4) < n) {
                    n2 = n4 + 1;
                    continue;
                }
                n3 = n4 - 1;
            }
            n = n2 - 1;
            if (n >= 0 && n < this.mSpanIndexCache.size()) {
                return this.mSpanIndexCache.keyAt(n);
            }
            return -1;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        int getCachedSpanIndex(int n, int n2) {
            int n3;
            if (!this.mCacheSpanIndices) {
                return this.getSpanIndex(n, n2);
            }
            int n4 = n3 = this.mSpanIndexCache.get(n, -1);
            if (n3 != -1) return n4;
            n2 = this.getSpanIndex(n, n2);
            this.mSpanIndexCache.put(n, n2);
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getSpanGroupIndex(int n, int n2) {
            int n3 = 0;
            int n4 = 0;
            int n5 = this.getSpanSize(n);
            for (int i = 0; i < n; ++i) {
                int n6;
                int n7 = this.getSpanSize(i);
                int n8 = n3 + n7;
                if (n8 == n2) {
                    n3 = 0;
                    n6 = n4 + 1;
                } else {
                    n6 = n4;
                    n3 = n8;
                    if (n8 > n2) {
                        n3 = n7;
                        n6 = n4 + 1;
                    }
                }
                n4 = n6;
            }
            n = n4;
            if (n3 + n5 <= n2) return n;
            return n4 + 1;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getSpanIndex(int n, int n2) {
            int n3 = this.getSpanSize(n);
            if (n3 == n2) {
                return 0;
            }
            int n4 = 0;
            int n5 = 0;
            int n6 = n4;
            int n7 = n5;
            if (this.mCacheSpanIndices) {
                n6 = n4;
                n7 = n5;
                if (this.mSpanIndexCache.size() > 0) {
                    int n8 = this.findReferenceIndexFromCache(n);
                    n6 = n4;
                    n7 = n5;
                    if (n8 >= 0) {
                        n6 = this.mSpanIndexCache.get(n8) + this.getSpanSize(n8);
                        n7 = n8 + 1;
                    }
                }
            }
            while (n7 < n) {
                n4 = this.getSpanSize(n7);
                n5 = n6 + n4;
                if (n5 == n2) {
                    n6 = 0;
                } else {
                    n6 = n5;
                    if (n5 > n2) {
                        n6 = n4;
                    }
                }
                ++n7;
            }
            n = n6;
            if (n6 + n3 <= n2) return n;
            return 0;
        }

        public abstract int getSpanSize(int var1);

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }
    }

}

