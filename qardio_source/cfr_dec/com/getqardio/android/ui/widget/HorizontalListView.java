/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.ScrollView
 *  android.widget.Scroller
 */
package com.getqardio.android.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import com.getqardio.android.R;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HorizontalListView
extends AdapterView<ListAdapter> {
    protected ListAdapter mAdapter;
    private DataSetObserver mAdapterDataObserver;
    private boolean mBlockTouchAction = false;
    private OnScrollStateChangedListener.ScrollState mCurrentScrollState;
    protected int mCurrentX;
    private int mCurrentlySelectedAdapterIndex;
    private boolean mDataChanged = false;
    private Runnable mDelayedLayout;
    private int mDisplayOffset;
    private Drawable mDivider = null;
    private int mDividerWidth = 0;
    private EdgeEffectCompat mEdgeGlowLeft;
    private EdgeEffectCompat mEdgeGlowRight;
    protected Scroller mFlingTracker = new Scroller(this.getContext());
    private GestureDetector mGestureDetector;
    private final GestureListener mGestureListener = new GestureListener();
    private boolean mHasNotifiedRunningLowOnData = false;
    private int mHeightMeasureSpec;
    private boolean mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = false;
    private int mLeftViewAdapterIndex;
    private int mMaxX = Integer.MAX_VALUE;
    protected int mNextX;
    private View.OnClickListener mOnClickListener;
    private OnScrollStateChangedListener mOnScrollStateChangedListener = null;
    private Rect mRect;
    private List<Queue<View>> mRemovedViewsCache = new ArrayList<Queue<View>>();
    private Integer mRestoreX = null;
    private int mRightViewAdapterIndex;
    private RunningOutOfDataListener mRunningOutOfDataListener = null;
    private int mRunningOutOfDataThreshold = 0;
    private View mViewBeingTouched = null;

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRect = new Rect();
        this.mCurrentScrollState = OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE;
        this.mAdapterDataObserver = new DataSetObserver(){

            public void onChanged() {
                HorizontalListView.this.mDataChanged = true;
                HorizontalListView.this.mHasNotifiedRunningLowOnData = false;
                HorizontalListView.this.unpressTouchedChild();
                HorizontalListView.this.invalidate();
                HorizontalListView.this.requestLayout();
            }

            public void onInvalidated() {
                HorizontalListView.this.mHasNotifiedRunningLowOnData = false;
                HorizontalListView.this.unpressTouchedChild();
                HorizontalListView.this.reset();
                HorizontalListView.this.invalidate();
                HorizontalListView.this.requestLayout();
            }
        };
        this.mDelayedLayout = new Runnable(){

            @Override
            public void run() {
                HorizontalListView.this.requestLayout();
            }
        };
        this.mEdgeGlowLeft = new EdgeEffectCompat(context);
        this.mEdgeGlowRight = new EdgeEffectCompat(context);
        this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)this.mGestureListener);
        this.bindGestureDetector();
        this.initView();
        this.retrieveXmlConfiguration(context, attributeSet);
        this.setWillNotDraw(false);
        if (this.mFlingTracker != null) {
            this.mFlingTracker.setFriction(0.009f);
        }
    }

    private void addAndMeasureChild(View view, int n) {
        this.addViewInLayout(view, n, this.getLayoutParams(view), true);
        this.measureChild(view);
    }

    private void bindGestureDetector() {
        this.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return HorizontalListView.this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    private float determineFlingAbsorbVelocity() {
        return this.mFlingTracker.getCurrVelocity();
    }

    private void determineIfLowOnData() {
        if (this.mRunningOutOfDataListener != null && this.mAdapter != null && this.mAdapter.getCount() - (this.mRightViewAdapterIndex + 1) < this.mRunningOutOfDataThreshold && !this.mHasNotifiedRunningLowOnData) {
            this.mHasNotifiedRunningLowOnData = true;
            this.mRunningOutOfDataListener.onRunningOutOfData();
        }
    }

    private boolean determineMaxX() {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.isLastItemInAdapter(this.mRightViewAdapterIndex)) {
            View view = this.getRightmostChild();
            bl2 = bl;
            if (view != null) {
                int n = this.mMaxX;
                this.mMaxX = this.mCurrentX + (view.getRight() - this.getPaddingLeft()) - this.getRenderWidth();
                if (this.mMaxX < 0) {
                    this.mMaxX = 0;
                }
                bl2 = bl;
                if (this.mMaxX != n) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    private void drawDivider(Canvas canvas, Rect rect) {
        if (this.mDivider != null) {
            this.mDivider.setBounds(rect);
            this.mDivider.draw(canvas);
        }
    }

    private void drawDividers(Canvas canvas) {
        int n = this.getChildCount();
        Rect rect = this.mRect;
        this.mRect.top = this.getPaddingTop();
        this.mRect.bottom = this.mRect.top + this.getRenderHeight();
        for (int i = 0; i < n; ++i) {
            if (i == n - 1 && this.isLastItemInAdapter(this.mRightViewAdapterIndex)) continue;
            View view = this.getChildAt(i);
            rect.left = view.getRight();
            rect.right = view.getRight() + this.mDividerWidth;
            if (rect.left < this.getPaddingLeft()) {
                rect.left = this.getPaddingLeft();
            }
            if (rect.right > this.getWidth() - this.getPaddingRight()) {
                rect.right = this.getWidth() - this.getPaddingRight();
            }
            this.drawDivider(canvas, rect);
            if (i != 0 || view.getLeft() <= this.getPaddingLeft()) continue;
            rect.left = this.getPaddingLeft();
            rect.right = view.getLeft();
            this.drawDivider(canvas, rect);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawEdgeGlow(Canvas canvas) {
        if (this.mEdgeGlowLeft != null && !this.mEdgeGlowLeft.isFinished() && this.isEdgeGlowEnabled()) {
            int n = canvas.save();
            int n2 = this.getHeight();
            canvas.rotate(-90.0f, 0.0f, 0.0f);
            canvas.translate((float)(-n2 + this.getPaddingBottom()), 0.0f);
            this.mEdgeGlowLeft.setSize(this.getRenderHeight(), this.getRenderWidth());
            if (this.mEdgeGlowLeft.draw(canvas)) {
                this.invalidate();
            }
            canvas.restoreToCount(n);
            return;
        } else {
            if (this.mEdgeGlowRight == null || this.mEdgeGlowRight.isFinished() || !this.isEdgeGlowEnabled()) return;
            {
                int n = canvas.save();
                int n3 = this.getWidth();
                canvas.rotate(90.0f, 0.0f, 0.0f);
                canvas.translate((float)this.getPaddingTop(), (float)(-n3));
                this.mEdgeGlowRight.setSize(this.getRenderHeight(), this.getRenderWidth());
                if (this.mEdgeGlowRight.draw(canvas)) {
                    this.invalidate();
                }
                canvas.restoreToCount(n);
                return;
            }
        }
    }

    private void fillList(int n) {
        int n2 = 0;
        View view = this.getRightmostChild();
        if (view != null) {
            n2 = view.getRight();
        }
        this.fillListRight(n2, n);
        n2 = 0;
        view = this.getLeftmostChild();
        if (view != null) {
            n2 = view.getLeft();
        }
        this.fillListLeft(n2, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fillListLeft(int n, int n2) {
        while (n + n2 - this.mDividerWidth > 0 && this.mLeftViewAdapterIndex >= 1) {
            --this.mLeftViewAdapterIndex;
            View view = this.mAdapter.getView(this.mLeftViewAdapterIndex, this.getRecycledView(this.mLeftViewAdapterIndex), (ViewGroup)this);
            this.addAndMeasureChild(view, 0);
            int n3 = this.mLeftViewAdapterIndex == 0 ? view.getMeasuredWidth() : this.mDividerWidth + view.getMeasuredWidth();
            n3 = n - n3;
            int n4 = this.mDisplayOffset;
            n = n3 + n2 == 0 ? view.getMeasuredWidth() : this.mDividerWidth + view.getMeasuredWidth();
            this.mDisplayOffset = n4 - n;
            n = n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fillListRight(int n, int n2) {
        while (n + n2 + this.mDividerWidth < this.getWidth() && this.mRightViewAdapterIndex + 1 < this.mAdapter.getCount()) {
            ++this.mRightViewAdapterIndex;
            if (this.mLeftViewAdapterIndex < 0) {
                this.mLeftViewAdapterIndex = this.mRightViewAdapterIndex;
            }
            View view = this.mAdapter.getView(this.mRightViewAdapterIndex, this.getRecycledView(this.mRightViewAdapterIndex), (ViewGroup)this);
            this.addAndMeasureChild(view, -1);
            int n3 = this.mRightViewAdapterIndex == 0 ? 0 : this.mDividerWidth;
            n += n3 + view.getMeasuredWidth();
            this.determineIfLowOnData();
        }
    }

    private View getChild(int n) {
        if (n >= this.mLeftViewAdapterIndex && n <= this.mRightViewAdapterIndex) {
            return this.getChildAt(n - this.mLeftViewAdapterIndex);
        }
        return null;
    }

    private int getChildIndex(int n, int n2) {
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            this.getChildAt(i).getHitRect(this.mRect);
            if (!this.mRect.contains(n, n2)) continue;
            return i;
        }
        return -1;
    }

    private ViewGroup.LayoutParams getLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view = layoutParams;
        if (layoutParams == null) {
            view = new ViewGroup.LayoutParams(-2, -1);
        }
        return view;
    }

    private View getLeftmostChild() {
        return this.getChildAt(0);
    }

    private View getRecycledView(int n) {
        if (this.isItemViewTypeValid(n = this.mAdapter.getItemViewType(n))) {
            return this.mRemovedViewsCache.get(n).poll();
        }
        return null;
    }

    private int getRenderHeight() {
        return this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
    }

    private int getRenderWidth() {
        return this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    private View getRightmostChild() {
        return this.getChildAt(this.getChildCount() - 1);
    }

    private void initView() {
        this.mLeftViewAdapterIndex = -1;
        this.mRightViewAdapterIndex = -1;
        this.mDisplayOffset = 0;
        this.mCurrentX = 0;
        this.mNextX = 0;
        this.mMaxX = Integer.MAX_VALUE;
        this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }

    private void initializeRecycledViewCache(int n) {
        this.mRemovedViewsCache.clear();
        for (int i = 0; i < n; ++i) {
            this.mRemovedViewsCache.add(new LinkedList());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isEdgeGlowEnabled() {
        return this.mAdapter != null && !this.mAdapter.isEmpty() && this.mMaxX > 0;
    }

    private boolean isItemViewTypeValid(int n) {
        return n < this.mRemovedViewsCache.size();
    }

    private boolean isLastItemInAdapter(int n) {
        return n == this.mAdapter.getCount() - 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChild(View view) {
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams(view);
        int n = ViewGroup.getChildMeasureSpec((int)this.mHeightMeasureSpec, (int)(this.getPaddingTop() + this.getPaddingBottom()), (int)layoutParams.height);
        int n2 = layoutParams.width > 0 ? View.MeasureSpec.makeMeasureSpec((int)layoutParams.width, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        view.measure(n2, n);
    }

    private void positionChildren(int n) {
        int n2 = this.getChildCount();
        if (n2 > 0) {
            this.mDisplayOffset += n;
            int n3 = this.mDisplayOffset;
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                int n4 = n3 + this.getPaddingLeft();
                int n5 = this.getPaddingTop();
                view.layout(n4, n5, n4 + view.getMeasuredWidth(), n5 + view.getMeasuredHeight());
                n3 += view.getMeasuredWidth() + this.mDividerWidth;
            }
        }
    }

    private void recycleView(int n, View view) {
        if (this.isItemViewTypeValid(n = this.mAdapter.getItemViewType(n))) {
            this.mRemovedViewsCache.get(n).offer(view);
        }
    }

    private void releaseEdgeGlow() {
        if (this.mEdgeGlowLeft != null) {
            this.mEdgeGlowLeft.onRelease();
        }
        if (this.mEdgeGlowRight != null) {
            this.mEdgeGlowRight.onRelease();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void removeNonVisibleChildren(int n) {
        View view = this.getLeftmostChild();
        while (view != null && view.getRight() + n <= 0) {
            int n2 = this.mDisplayOffset;
            int n3 = this.isLastItemInAdapter(this.mLeftViewAdapterIndex) ? view.getMeasuredWidth() : this.mDividerWidth + view.getMeasuredWidth();
            this.mDisplayOffset = n3 + n2;
            this.recycleView(this.mLeftViewAdapterIndex, view);
            this.removeViewInLayout(view);
            ++this.mLeftViewAdapterIndex;
            view = this.getLeftmostChild();
        }
        view = this.getRightmostChild();
        while (view != null && view.getLeft() + n >= this.getWidth()) {
            this.recycleView(this.mRightViewAdapterIndex, view);
            this.removeViewInLayout(view);
            --this.mRightViewAdapterIndex;
            view = this.getRightmostChild();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void requestParentListViewToNotInterceptTouchEvents(Boolean bl) {
        if (this.mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent == bl) return;
        HorizontalListView horizontalListView = this;
        while (horizontalListView.getParent() instanceof View) {
            if (horizontalListView.getParent() instanceof ListView || horizontalListView.getParent() instanceof ScrollView) {
                horizontalListView.getParent().requestDisallowInterceptTouchEvent(bl.booleanValue());
                this.mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = bl;
                return;
            }
            horizontalListView = (View)horizontalListView.getParent();
        }
    }

    private void reset() {
        this.initView();
        this.removeAllViewsInLayout();
        this.requestLayout();
    }

    private void retrieveXmlConfiguration(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            int n;
            context = context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalListView);
            attributeSet = context.getDrawable(1);
            if (attributeSet != null) {
                this.setDivider((Drawable)attributeSet);
            }
            if ((n = context.getDimensionPixelSize(3, 0)) != 0) {
                this.setDividerWidth(n);
            }
            context.recycle();
        }
    }

    private void setCurrentScrollState(OnScrollStateChangedListener.ScrollState scrollState) {
        if (this.mCurrentScrollState != scrollState && this.mOnScrollStateChangedListener != null) {
            this.mOnScrollStateChangedListener.onScrollStateChanged(scrollState);
        }
        this.mCurrentScrollState = scrollState;
    }

    private void unpressTouchedChild() {
        if (this.mViewBeingTouched != null) {
            this.mViewBeingTouched.setPressed(false);
            this.refreshDrawableState();
            this.mViewBeingTouched = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateOverscrollAnimation(int n) {
        if (this.mEdgeGlowLeft == null || this.mEdgeGlowRight == null) return;
        int n2 = this.mCurrentX + n;
        if (this.mFlingTracker != null && !this.mFlingTracker.isFinished()) return;
        if (n2 < 0) {
            n = Math.abs(n);
            this.mEdgeGlowLeft.onPull((float)n / (float)this.getRenderWidth());
            if (this.mEdgeGlowRight.isFinished()) return;
            {
                this.mEdgeGlowRight.onRelease();
                return;
            }
        }
        if (n2 <= this.mMaxX) return;
        n = Math.abs(n);
        this.mEdgeGlowRight.onPull((float)n / (float)this.getRenderWidth());
        if (this.mEdgeGlowLeft.isFinished()) {
            return;
        }
        this.mEdgeGlowLeft.onRelease();
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.drawEdgeGlow(canvas);
    }

    protected void dispatchSetPressed(boolean bl) {
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getFirstVisiblePosition() {
        return this.mLeftViewAdapterIndex;
    }

    public int getLastVisiblePosition() {
        return this.mRightViewAdapterIndex;
    }

    protected float getLeftFadingEdgeStrength() {
        int n = this.getHorizontalFadingEdgeLength();
        if (this.mCurrentX == 0) {
            return 0.0f;
        }
        if (this.mCurrentX < n) {
            return (float)this.mCurrentX / (float)n;
        }
        return 1.0f;
    }

    protected float getRightFadingEdgeStrength() {
        int n = this.getHorizontalFadingEdgeLength();
        if (this.mCurrentX == this.mMaxX) {
            return 0.0f;
        }
        if (this.mMaxX - this.mCurrentX < n) {
            return (float)(this.mMaxX - this.mCurrentX) / (float)n;
        }
        return 1.0f;
    }

    public View getSelectedView() {
        return this.getChild(this.mCurrentlySelectedAdapterIndex);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onDown(MotionEvent motionEvent) {
        int n;
        boolean bl = !this.mFlingTracker.isFinished();
        this.mBlockTouchAction = bl;
        this.mFlingTracker.forceFinished(true);
        this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        this.unpressTouchedChild();
        if (!this.mBlockTouchAction && (n = this.getChildIndex((int)motionEvent.getX(), (int)motionEvent.getY())) >= 0) {
            this.mViewBeingTouched = this.getChildAt(n);
            if (this.mViewBeingTouched != null) {
                this.mViewBeingTouched.setPressed(true);
                this.refreshDrawableState();
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawDividers(canvas);
    }

    protected boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mFlingTracker.fling(this.mNextX, 0, (int)(-f), 0, 0, this.mMaxX, 0, 0);
        this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        this.requestLayout();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"WrongCall"})
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        block14: {
            block13: {
                int n5;
                super.onLayout(bl, n, n2, n3, n4);
                if (this.mAdapter == null) break block13;
                this.invalidate();
                if (this.mDataChanged) {
                    n5 = this.mCurrentX;
                    this.initView();
                    this.removeAllViewsInLayout();
                    this.mNextX = n5;
                    this.mDataChanged = false;
                }
                if (this.mRestoreX != null) {
                    this.mNextX = this.mRestoreX;
                    this.mRestoreX = null;
                }
                if (this.mFlingTracker.computeScrollOffset()) {
                    this.mNextX = this.mFlingTracker.getCurrX();
                }
                if (this.mNextX < 0) {
                    this.mNextX = 0;
                    if (this.mEdgeGlowLeft.isFinished()) {
                        this.mEdgeGlowLeft.onAbsorb((int)this.determineFlingAbsorbVelocity());
                    }
                    this.mFlingTracker.forceFinished(true);
                    this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
                } else if (this.mNextX > this.mMaxX) {
                    this.mNextX = this.mMaxX;
                    if (this.mEdgeGlowRight.isFinished()) {
                        this.mEdgeGlowRight.onAbsorb((int)this.determineFlingAbsorbVelocity());
                    }
                    this.mFlingTracker.forceFinished(true);
                    this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
                }
                n5 = this.mCurrentX - this.mNextX;
                this.removeNonVisibleChildren(n5);
                this.fillList(n5);
                this.positionChildren(n5);
                this.mCurrentX = this.mNextX;
                if (this.determineMaxX()) {
                    this.onLayout(bl, n, n2, n3, n4);
                    return;
                }
                if (!this.mFlingTracker.isFinished()) {
                    ViewCompat.postOnAnimation((View)this, this.mDelayedLayout);
                    return;
                }
                if (this.mCurrentScrollState == OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING) break block14;
            }
            return;
        }
        this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.mHeightMeasureSpec = n2;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            parcelable = (Bundle)parcelable;
            this.mRestoreX = parcelable.getInt("BUNDLE_ID_CURRENT_X");
            super.onRestoreInstanceState(parcelable.getParcelable("BUNDLE_ID_PARENT_STATE"));
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("BUNDLE_ID_PARENT_STATE", super.onSaveInstanceState());
        bundle.putInt("BUNDLE_ID_CURRENT_X", this.mCurrentX);
        return bundle;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            if (motionEvent.getAction() != 3) return super.onTouchEvent(motionEvent);
            this.unpressTouchedChild();
            this.releaseEdgeGlow();
            this.requestParentListViewToNotInterceptTouchEvents(false);
            return super.onTouchEvent(motionEvent);
        }
        if (this.mFlingTracker == null || this.mFlingTracker.isFinished()) {
            this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        }
        this.requestParentListViewToNotInterceptTouchEvents(false);
        this.releaseEdgeGlow();
        return super.onTouchEvent(motionEvent);
    }

    public void scrollTo(int n) {
        this.mFlingTracker.startScroll(this.mNextX, 0, n - this.mNextX, 0);
        this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        this.requestLayout();
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mAdapterDataObserver);
        }
        if (listAdapter != null) {
            this.mHasNotifiedRunningLowOnData = false;
            this.mAdapter = listAdapter;
            this.mAdapter.registerDataSetObserver(this.mAdapterDataObserver);
        }
        this.initializeRecycledViewCache(this.mAdapter.getViewTypeCount());
        this.reset();
    }

    public void setDivider(Drawable drawable2) {
        this.mDivider = drawable2;
        if (drawable2 != null) {
            this.setDividerWidth(drawable2.getIntrinsicWidth());
            return;
        }
        this.setDividerWidth(0);
    }

    public void setDividerWidth(int n) {
        this.mDividerWidth = n;
        this.requestLayout();
        this.invalidate();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener onScrollStateChangedListener) {
        this.mOnScrollStateChangedListener = onScrollStateChangedListener;
    }

    public void setRunningOutOfDataListener(RunningOutOfDataListener runningOutOfDataListener, int n) {
        this.mRunningOutOfDataListener = runningOutOfDataListener;
        this.mRunningOutOfDataThreshold = n;
    }

    public void setSelection(int n) {
        this.mCurrentlySelectedAdapterIndex = n;
    }

    private class GestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            HorizontalListView.this.unpressTouchedChild();
            int n = HorizontalListView.this.getChildIndex((int)motionEvent.getX(), (int)motionEvent.getY());
            if (n >= 0 && !HorizontalListView.this.mBlockTouchAction) {
                motionEvent = HorizontalListView.this.getChildAt(n);
                AdapterView.OnItemLongClickListener onItemLongClickListener = HorizontalListView.this.getOnItemLongClickListener();
                if (onItemLongClickListener != null && onItemLongClickListener.onItemLongClick((AdapterView)HorizontalListView.this, (View)motionEvent, n = HorizontalListView.this.mLeftViewAdapterIndex + n, HorizontalListView.this.mAdapter.getItemId(n))) {
                    HorizontalListView.this.performHapticFeedback(0);
                }
            }
        }

        public boolean onScroll(MotionEvent object, MotionEvent motionEvent, float f, float f2) {
            HorizontalListView.this.requestParentListViewToNotInterceptTouchEvents(true);
            HorizontalListView.this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_TOUCH_SCROLL);
            HorizontalListView.this.unpressTouchedChild();
            object = HorizontalListView.this;
            object.mNextX += (int)f;
            HorizontalListView.this.updateOverscrollAnimation(Math.round(f));
            HorizontalListView.this.requestLayout();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            HorizontalListView.this.unpressTouchedChild();
            AdapterView.OnItemClickListener onItemClickListener = HorizontalListView.this.getOnItemClickListener();
            int n = HorizontalListView.this.getChildIndex((int)motionEvent.getX(), (int)motionEvent.getY());
            if (n >= 0 && !HorizontalListView.this.mBlockTouchAction) {
                motionEvent = HorizontalListView.this.getChildAt(n);
                n = HorizontalListView.this.mLeftViewAdapterIndex + n;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick((AdapterView)HorizontalListView.this, (View)motionEvent, n, HorizontalListView.this.mAdapter.getItemId(n));
                    return true;
                }
            }
            if (HorizontalListView.this.mOnClickListener != null && !HorizontalListView.this.mBlockTouchAction) {
                HorizontalListView.this.mOnClickListener.onClick((View)HorizontalListView.this);
            }
            return false;
        }
    }

    public static interface OnScrollStateChangedListener {
        public void onScrollStateChanged(ScrollState var1);

        public static enum ScrollState {
            SCROLL_STATE_IDLE,
            SCROLL_STATE_TOUCH_SCROLL,
            SCROLL_STATE_FLING;

        }

    }

    public static interface RunningOutOfDataListener {
        public void onRunningOutOfData();
    }

}

