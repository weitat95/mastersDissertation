/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.Observable
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.Display
 *  android.view.FocusFinder
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.OverScroller
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.FastScroller;
import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.support.v7.widget.ViewBoundsCheck;
import android.support.v7.widget.ViewInfoStore;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
extends ViewGroup
implements NestedScrollingChild2,
ScrollingView {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    private static final boolean ALLOW_THREAD_GAP_WORK;
    private static final int[] CLIP_TO_PADDING_ATTR;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    private static final int[] NESTED_SCROLLING_ATTRS;
    static final boolean POST_UPDATES_ON_ANIMATION;
    static final Interpolator sQuinticInterpolator;
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout = false;
    private int mDispatchScrollCounter = 0;
    private int mEatRequestLayout = 0;
    private int mEatenAccessibilityChangeFlags;
    boolean mEnableFastScroller;
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    LayoutManager mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter = 0;
    boolean mLayoutRequestEaten;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout = true;
    final Recycler mRecycler = new Recycler();
    RecyclerListener mRecyclerListener;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;
    private float mScaledVerticalScrollFactor = Float.MIN_VALUE;
    private final int[] mScrollConsumed;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId = -1;
    private int mScrollState = 0;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect;
    private final Rect mTempRect2;
    final RectF mTempRectF;
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore = new ViewInfoStore();

    /*
     * Enabled aggressive block sorting
     */
    static {
        NESTED_SCROLLING_ATTRS = new int[]{16843830};
        CLIP_TO_PADDING_ATTR = new int[]{16842987};
        boolean bl = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = bl;
        bl = Build.VERSION.SDK_INT >= 23;
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bl;
        bl = Build.VERSION.SDK_INT >= 16;
        POST_UPDATES_ON_ANIMATION = bl;
        bl = Build.VERSION.SDK_INT >= 21;
        ALLOW_THREAD_GAP_WORK = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        FORCE_ABS_FOCUS_SEARCH_DIRECTION = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        IGNORE_DETACHED_FOCUSED_CHILD = bl;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
        sQuinticInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public RecyclerView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mUpdateChildViewsRunnable = new Runnable(){

            @Override
            public void run() {
                if (!RecyclerView.this.mFirstLayoutComplete || RecyclerView.this.isLayoutRequested()) {
                    return;
                }
                if (!RecyclerView.this.mIsAttached) {
                    RecyclerView.this.requestLayout();
                    return;
                }
                if (RecyclerView.this.mLayoutFrozen) {
                    RecyclerView.this.mLayoutRequestEaten = true;
                    return;
                }
                RecyclerView.this.consumePendingUpdateOperations();
            }
        };
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mItemDecorations = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList();
        this.mItemAnimator = new DefaultItemAnimator();
        this.mViewFlinger = new ViewFlinger();
        GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mPrefetchRegistry = layoutPrefetchRegistryImpl;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList<ViewHolder>();
        this.mItemAnimatorRunner = new Runnable(){

            @Override
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback(){

            @Override
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processDisappeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void processPersistent(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (!RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                    {
                        RecyclerView.this.postAnimationRunner();
                        return;
                    }
                } else {
                    if (!RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                    {
                        RecyclerView.this.postAnimationRunner();
                        return;
                    }
                }
            }

            @Override
            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        if (attributeSet != null) {
            layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, CLIP_TO_PADDING_ATTR, n, 0);
            this.mClipToPadding = layoutPrefetchRegistryImpl.getBoolean(0, true);
            layoutPrefetchRegistryImpl.recycle();
        } else {
            this.mClipToPadding = true;
        }
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        layoutPrefetchRegistryImpl = ViewConfiguration.get((Context)context);
        this.mTouchSlop = layoutPrefetchRegistryImpl.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mMinFlingVelocity = layoutPrefetchRegistryImpl.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = layoutPrefetchRegistryImpl.getScaledMaximumFlingVelocity();
        boolean bl = this.getOverScrollMode() == 2;
        this.setWillNotDraw(bl);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.initAdapterManager();
        this.initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        bl = true;
        if (attributeSet != null) {
            layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, 0);
            String string2 = layoutPrefetchRegistryImpl.getString(R.styleable.RecyclerView_layoutManager);
            if (layoutPrefetchRegistryImpl.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
                this.setDescendantFocusability(262144);
            }
            this.mEnableFastScroller = layoutPrefetchRegistryImpl.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
            if (this.mEnableFastScroller) {
                this.initFastScroller((StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
            }
            layoutPrefetchRegistryImpl.recycle();
            this.createLayoutManager(context, string2, attributeSet, n, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                context = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, n, 0);
                bl = context.getBoolean(0, true);
                context.recycle();
            }
        } else {
            this.setDescendantFocusability(262144);
        }
        this.setNestedScrollingEnabled(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean bl = view.getParent() == this;
        this.mRecycler.unscrapView(this.getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!bl) {
            this.mChildHelper.addView(view, true);
            return;
        }
        this.mChildHelper.hide(view);
    }

    private void animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean bl, boolean bl2) {
        viewHolder.setIsRecyclable(false);
        if (bl) {
            this.addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (bl2) {
                this.addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            this.addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private void cancelTouch() {
        this.resetTouch();
        this.setScrollState(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void clearNestedRecyclerViewIfNotNested(ViewHolder viewHolder) {
        if (viewHolder.mNestedRecyclerView == null) return;
        View view = (View)viewHolder.mNestedRecyclerView.get();
        while (view != null) {
            if (view == viewHolder.itemView) {
                return;
            }
            if ((view = view.getParent()) instanceof View) continue;
            view = null;
        }
        viewHolder.mNestedRecyclerView = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void createLayoutManager(Context var1_1, String var2_8, AttributeSet var3_9, int var4_10, int var5_11) {
        if (var2_8 == null) return;
        if ((var2_8 = var2_8.trim()).isEmpty() != false) return;
        var7_12 = this.getFullClassName((Context)var1_1, (String)var2_8);
        try {
            var2_8 = this.isInEditMode() != false ? this.getClass().getClassLoader() : var1_1.getClassLoader();
            var8_13 = var2_8.loadClass(var7_12).asSubclass(LayoutManager.class);
            var2_8 = null;
            try {
                var6_14 = var8_13.getConstructor(RecyclerView.LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
            }
            catch (NoSuchMethodException var6_15) {
                try {
                    var1_1 = var8_13.getConstructor(new Class[0]);
                }
                catch (NoSuchMethodException var1_2) {
                    var1_2.initCause(var6_15);
                    throw new IllegalStateException(var3_9.getPositionDescription() + ": Error creating LayoutManager " + var7_12, var1_2);
                }
lbl17:
                // 2 sources
                var1_1.setAccessible(true);
                this.setLayoutManager((LayoutManager)var1_1.newInstance((Object[])var2_8));
                return;
            }
            var2_8 = new Object[]{var1_1, var3_9, var4_10, var5_11};
            var1_1 = var6_14;
            ** GOTO lbl17
        }
        catch (ClassNotFoundException var1_3) {
            throw new IllegalStateException(var3_9.getPositionDescription() + ": Unable to find LayoutManager " + var7_12, var1_3);
        }
        catch (InvocationTargetException var1_4) {
            throw new IllegalStateException(var3_9.getPositionDescription() + ": Could not instantiate the LayoutManager: " + var7_12, var1_4);
        }
        catch (InstantiationException var1_5) {
            throw new IllegalStateException(var3_9.getPositionDescription() + ": Could not instantiate the LayoutManager: " + var7_12, var1_5);
        }
        catch (IllegalAccessException var1_6) {
            throw new IllegalStateException(var3_9.getPositionDescription() + ": Cannot access non-public constructor " + var7_12, var1_6);
        }
        catch (ClassCastException var1_7) {
            throw new IllegalStateException(var3_9.getPositionDescription() + ": Class is not a LayoutManager " + var7_12, var1_7);
        }
    }

    private boolean didChildRangeChange(int n, int n2) {
        boolean bl = false;
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mMinMaxLayoutPositions[0] != n || this.mMinMaxLayoutPositions[1] != n2) {
            bl = true;
        }
        return bl;
    }

    private void dispatchContentChangedIfNecessary() {
        int n = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (n != 0 && this.isAccessibilityEnabled()) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
            accessibilityEvent.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, n);
            this.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep1() {
        int n;
        int n2;
        ItemAnimator.ItemHolderInfo itemHolderInfo;
        this.mState.assertLayoutStep(1);
        this.fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        this.eatRequestLayout();
        this.mViewInfoStore.clear();
        this.onEnterLayoutOrScroll();
        this.processAdapterUpdatesAndSetAnimationFlags();
        this.saveFocusInfo();
        Object object = this.mState;
        boolean bl = this.mState.mRunSimpleAnimations && this.mItemsChanged;
        ((State)object).mTrackOldChangeHolders = bl;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            n2 = this.mChildHelper.getChildCount();
            for (n = 0; n < n2; ++n) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid() && !this.mAdapter.hasStableIds()) continue;
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object), ((ViewHolder)object).getUnmodifiedPayloads());
                this.mViewInfoStore.addToPreLayout((ViewHolder)object, itemHolderInfo);
                if (!this.mState.mTrackOldChangeHolders || !((ViewHolder)object).isUpdated() || ((ViewHolder)object).isRemoved() || ((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid()) continue;
                long l = this.getChangedHolderKey((ViewHolder)object);
                this.mViewInfoStore.addToOldChangeHolders(l, (ViewHolder)object);
            }
        }
        if (!this.mState.mRunPredictiveAnimations) {
            this.clearOldPositions();
        } else {
            this.saveOldPositions();
            bl = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = bl;
            for (n = 0; n < this.mChildHelper.getChildCount(); ++n) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (((ViewHolder)object).shouldIgnore() || this.mViewInfoStore.isInPreLayout((ViewHolder)object)) continue;
                int n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                bl = ((ViewHolder)object).hasAnyOfTheFlags(8192);
                n2 = n3;
                if (!bl) {
                    n2 = n3 | 0x1000;
                }
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, n2, ((ViewHolder)object).getUnmodifiedPayloads());
                if (bl) {
                    this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, itemHolderInfo);
                    continue;
                }
                this.mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)object, itemHolderInfo);
            }
            this.clearOldPositions();
        }
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep2() {
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State state = this.mState;
        boolean bl = this.mState.mRunSimpleAnimations && this.mItemAnimator != null;
        state.mRunSimpleAnimations = bl;
        this.mState.mLayoutStep = 4;
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (viewHolder.shouldIgnore()) continue;
                long l = this.getChangedHolderKey(viewHolder);
                ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
                ViewHolder viewHolder2 = this.mViewInfoStore.getFromOldChangeHolders(l);
                if (viewHolder2 != null && !viewHolder2.shouldIgnore()) {
                    boolean bl = this.mViewInfoStore.isDisappearing(viewHolder2);
                    boolean bl2 = this.mViewInfoStore.isDisappearing(viewHolder);
                    if (bl && viewHolder2 == viewHolder) {
                        this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                        continue;
                    }
                    ItemAnimator.ItemHolderInfo itemHolderInfo2 = this.mViewInfoStore.popFromPreLayout(viewHolder2);
                    this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                    itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
                    if (itemHolderInfo2 == null) {
                        this.handleMissingPreInfoForChangeError(l, viewHolder, viewHolder2);
                        continue;
                    }
                    this.animateChange(viewHolder2, viewHolder, itemHolderInfo2, itemHolderInfo, bl, bl2);
                    continue;
                }
                this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        this.mState.mPreviousLayoutItemCount = this.mState.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        this.mViewInfoStore.clear();
        if (this.didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            this.dispatchOnScrolled(0, 0);
        }
        this.recoverFocusFromState();
        this.resetFocusInfo();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if (n != 0) {
                this.mActiveOnItemTouchListener.onTouchEvent(this, motionEvent);
                if (n != 3 && n != 1) return true;
                {
                    this.mActiveOnItemTouchListener = null;
                    return true;
                }
            }
            this.mActiveOnItemTouchListener = null;
        }
        if (n == 0) return false;
        int n2 = this.mOnItemTouchListeners.size();
        for (n = 0; n < n2; ++n) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(n);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n == 3 || n == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int n2 = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n2; ++i) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || n == 3) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void findMinMaxChildLayoutPositions(int[] arrn) {
        int n = this.mChildHelper.getChildCount();
        if (n == 0) {
            arrn[0] = -1;
            arrn[1] = -1;
            return;
        }
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        int n4 = 0;
        do {
            int n5;
            if (n4 >= n) {
                arrn[0] = n2;
                arrn[1] = n3;
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n4));
            if (viewHolder.shouldIgnore()) {
                n5 = n2;
                n2 = n3;
            } else {
                int n6 = viewHolder.getLayoutPosition();
                int n7 = n2;
                if (n6 < n2) {
                    n7 = n6;
                }
                n2 = n3;
                n5 = n7;
                if (n6 > n3) {
                    n2 = n6;
                    n5 = n7;
                }
            }
            ++n4;
            n3 = n2;
            n2 = n5;
        } while (true);
    }

    static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView)view;
        }
        view = (ViewGroup)view;
        int n = view.getChildCount();
        for (int i = 0; i < n; ++i) {
            RecyclerView recyclerView = RecyclerView.findNestedRecyclerView(view.getChildAt(i));
            if (recyclerView == null) continue;
            return recyclerView;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View findNextViewToFocus() {
        ViewHolder viewHolder;
        int n = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int n2 = this.mState.getItemCount();
        int n3 = n;
        do {
            if (n3 >= n2 || (viewHolder = this.findViewHolderForAdapterPosition(n3)) == null) break;
            if (viewHolder.itemView.hasFocusable()) {
                return viewHolder.itemView;
            }
            ++n3;
        } while (true);
        n = Math.min(n2, n) - 1;
        while (n >= 0 && (viewHolder = this.findViewHolderForAdapterPosition(n)) != null) {
            if (viewHolder.itemView.hasFocusable()) {
                return viewHolder.itemView;
            }
            --n;
        }
        return null;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).mViewHolder;
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set(view.getLeft() - rect2.left - layoutParams.leftMargin, view.getTop() - rect2.top - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    private int getDeepestFocusedViewWithId(View view) {
        int n = view.getId();
        while (!view.isFocused() && view instanceof ViewGroup && view.hasFocus()) {
            View view2;
            view = view2 = ((ViewGroup)view).getFocusedChild();
            if (view2.getId() == -1) continue;
            n = view2.getId();
            view = view2;
        }
        return n;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private String getFullClassName(Context object, String string2) {
        void var1_3;
        void var2_5;
        if (var2_5.charAt(0) == '.') {
            String string3 = object.getPackageName() + (String)var2_5;
            return var1_3;
        } else {
            void var1_4 = var2_5;
            if (var2_5.contains(".")) return var1_3;
            return RecyclerView.class.getPackage().getName() + '.' + (String)var2_5;
        }
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        }
        return this.mScrollingChildHelper;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleMissingPreInfoForChangeError(long l, ViewHolder viewHolder, ViewHolder viewHolder2) {
        ViewHolder viewHolder3;
        int n = this.mChildHelper.getChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                Log.e((String)"RecyclerView", (String)("Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + viewHolder2 + " cannot be found but it is necessary for " + viewHolder + this.exceptionLabel()));
                return;
            }
            viewHolder3 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
            if (viewHolder3 != viewHolder && this.getChangedHolderKey(viewHolder3) == l) {
                if (this.mAdapter == null || !this.mAdapter.hasStableIds()) break;
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + viewHolder3 + " \n View Holder 2:" + viewHolder + this.exceptionLabel());
            }
            ++n2;
        } while (true);
        throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + viewHolder3 + " \n View Holder 2:" + viewHolder + this.exceptionLabel());
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasUpdatedView() {
        int n = this.mChildHelper.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.isUpdated()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback(){

            @Override
            public void addView(View view, int n) {
                RecyclerView.this.addView(view, n);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override
            public void attachViewToParent(View view, int n, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder != null) {
                    if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + viewHolder + RecyclerView.this.exceptionLabel());
                    }
                    viewHolder.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent(view, n, layoutParams);
            }

            @Override
            public void detachViewFromParent(int n) {
                Object object = this.getChildAt(n);
                if (object != null && (object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    if (((ViewHolder)object).isTmpDetached() && !((ViewHolder)object).shouldIgnore()) {
                        throw new IllegalArgumentException("called detach on an already detached child " + object + RecyclerView.this.exceptionLabel());
                    }
                    ((ViewHolder)object).addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(n);
            }

            @Override
            public View getChildAt(int n) {
                return RecyclerView.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override
            public void onEnteredHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onEnteredHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void onLeftHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onLeftHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void removeAllViews() {
                int n = this.getChildCount();
                for (int i = 0; i < n; ++i) {
                    View view = this.getChildAt(i);
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            @Override
            public void removeViewAt(int n) {
                View view = RecyclerView.this.getChildAt(n);
                if (view != null) {
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                }
                RecyclerView.this.removeViewAt(n);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isPreferredNextFocus(View view, View view2, int n) {
        boolean bl = false;
        boolean bl2 = true;
        if (view2 == null) return false;
        if (view2 == this) {
            return false;
        }
        boolean bl3 = bl2;
        if (view == null) return bl3;
        if (n != 2 && n != 1) {
            return this.isPreferredNextFocusAbsolute(view, view2, n);
        }
        int n2 = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
        if (n == 2) {
            bl = true;
        }
        n2 = bl ^ n2 ? 66 : 17;
        bl3 = bl2;
        if (this.isPreferredNextFocusAbsolute(view, view2, n2)) return bl3;
        if (n != 2) return this.isPreferredNextFocusAbsolute(view, view2, 33);
        return this.isPreferredNextFocusAbsolute(view, view2, 130);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isPreferredNextFocusAbsolute(View view, View view2, int n) {
        block6: {
            this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
            this.mTempRect2.set(0, 0, view2.getWidth(), view2.getHeight());
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect2);
            switch (n) {
                default: {
                    throw new IllegalArgumentException("direction must be absolute. received:" + n + this.exceptionLabel());
                }
                case 17: {
                    if ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left) break;
                    return false;
                }
                case 66: {
                    if ((this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right) break;
                    return false;
                }
                case 33: {
                    if ((this.mTempRect.bottom > this.mTempRect2.bottom || this.mTempRect.top >= this.mTempRect2.bottom) && this.mTempRect.top > this.mTempRect2.top) break;
                    return false;
                }
                case 130: {
                    if (this.mTempRect.top >= this.mTempRect2.top && this.mTempRect.bottom > this.mTempRect2.top || this.mTempRect.bottom >= this.mTempRect2.bottom) break block6;
                }
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mScrollPointerId) {
            int n2;
            n = n == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(n);
            this.mLastTouchX = n2 = (int)(motionEvent.getX(n) + 0.5f);
            this.mInitialTouchX = n2;
            this.mLastTouchY = n = (int)(motionEvent.getY(n) + 0.5f);
            this.mInitialTouchY = n;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processAdapterUpdatesAndSetAnimationFlags() {
        boolean bl = true;
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            this.mLayout.onItemsChanged(this);
        }
        if (this.predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean bl2 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        State state = this.mState;
        boolean bl3 = !(!this.mFirstLayoutComplete || this.mItemAnimator == null || !this.mDataSetHasChangedAfterLayout && !bl2 && !this.mLayout.mRequestedSimpleAnimations || this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds());
        state.mRunSimpleAnimations = bl3;
        state = this.mState;
        bl3 = this.mState.mRunSimpleAnimations && bl2 && !this.mDataSetHasChangedAfterLayout && this.predictiveItemAnimationsEnabled() ? bl : false;
        state.mRunPredictiveAnimations = bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void pullGlows(float f, float f2, float f3, float f4) {
        boolean bl = false;
        if (f2 < 0.0f) {
            this.ensureLeftGlow();
            EdgeEffectCompat.onPull(this.mLeftGlow, -f2 / (float)this.getWidth(), 1.0f - f3 / (float)this.getHeight());
            bl = true;
        } else if (f2 > 0.0f) {
            this.ensureRightGlow();
            EdgeEffectCompat.onPull(this.mRightGlow, f2 / (float)this.getWidth(), f3 / (float)this.getHeight());
            bl = true;
        }
        if (f4 < 0.0f) {
            this.ensureTopGlow();
            EdgeEffectCompat.onPull(this.mTopGlow, -f4 / (float)this.getHeight(), f / (float)this.getWidth());
            bl = true;
        } else if (f4 > 0.0f) {
            this.ensureBottomGlow();
            EdgeEffectCompat.onPull(this.mBottomGlow, f4 / (float)this.getHeight(), 1.0f - f / (float)this.getWidth());
            bl = true;
        }
        if (bl || f2 != 0.0f || f4 != 0.0f) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void recoverFocusFromState() {
        void var1_8;
        void var2_16;
        block16: {
            block13: {
                void var1_5;
                Object var2_10;
                block14: {
                    View view;
                    block15: {
                        if (!this.mPreserveFocusAfterLayout || this.mAdapter == null || !this.hasFocus() || this.getDescendantFocusability() == 393216 || this.getDescendantFocusability() == 131072 && this.isFocused()) break block13;
                        if (this.isFocused()) break block14;
                        view = this.getFocusedChild();
                        if (!IGNORE_DETACHED_FOCUSED_CHILD || view.getParent() != null && view.hasFocus()) break block15;
                        if (this.mChildHelper.getChildCount() == 0) {
                            this.requestFocus();
                            return;
                        }
                        break block14;
                    }
                    if (!this.mChildHelper.isHidden(view)) break block13;
                }
                Object var1_2 = var2_10 = null;
                if (this.mState.mFocusedItemId != -1L) {
                    Object var1_3 = var2_10;
                    if (this.mAdapter.hasStableIds()) {
                        ViewHolder viewHolder = this.findViewHolderForItemId(this.mState.mFocusedItemId);
                    }
                }
                Object var2_11 = null;
                if (var1_5 == null || this.mChildHelper.isHidden(var1_5.itemView) || !var1_5.itemView.hasFocusable()) {
                    Object var1_6 = var2_11;
                    if (this.mChildHelper.getChildCount() > 0) {
                        View view = this.findNextViewToFocus();
                    }
                } else {
                    View view = var1_5.itemView;
                }
                if (var1_8 != null) break block16;
            }
            return;
        }
        void var2_12 = var1_8;
        if ((long)this.mState.mFocusedSubChildId != -1L) {
            View view = var1_8.findViewById(this.mState.mFocusedSubChildId);
            void var2_13 = var1_8;
            if (view != null) {
                void var2_14 = var1_8;
                if (view.isFocusable()) {
                    View view2 = view;
                }
            }
        }
        var2_16.requestFocus();
    }

    private void releaseGlows() {
        boolean bl = false;
        if (this.mLeftGlow != null) {
            this.mLeftGlow.onRelease();
            bl = this.mLeftGlow.isFinished();
        }
        boolean bl2 = bl;
        if (this.mTopGlow != null) {
            this.mTopGlow.onRelease();
            bl2 = bl | this.mTopGlow.isFinished();
        }
        bl = bl2;
        if (this.mRightGlow != null) {
            this.mRightGlow.onRelease();
            bl = bl2 | this.mRightGlow.isFinished();
        }
        bl2 = bl;
        if (this.mBottomGlow != null) {
            this.mBottomGlow.onRelease();
            bl2 = bl | this.mBottomGlow.isFinished();
        }
        if (bl2) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void requestChildOnScreen(View view, View view2) {
        Rect rect;
        boolean bl = true;
        Object object = view2 != null ? view2 : view;
        this.mTempRect.set(0, 0, object.getWidth(), object.getHeight());
        object = object.getLayoutParams();
        if (object instanceof LayoutParams) {
            object = (LayoutParams)((Object)object);
            if (!((LayoutParams)object).mInsetsDirty) {
                object = ((LayoutParams)object).mDecorInsets;
                rect = this.mTempRect;
                rect.left -= ((Rect)object).left;
                rect = this.mTempRect;
                rect.right += ((Rect)object).right;
                rect = this.mTempRect;
                rect.top -= ((Rect)object).top;
                rect = this.mTempRect;
                rect.bottom += ((Rect)object).bottom;
            }
        }
        if (view2 != null) {
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        object = this.mLayout;
        rect = this.mTempRect;
        boolean bl2 = !this.mFirstLayoutComplete;
        if (view2 != null) {
            bl = false;
        }
        ((LayoutManager)object).requestChildRectangleOnScreen(this, view, rect, bl2, bl);
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1L;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        this.stopNestedScroll(0);
        this.releaseGlows();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void saveFocusInfo() {
        Object object = null;
        ViewHolder viewHolder = object;
        if (this.mPreserveFocusAfterLayout) {
            viewHolder = object;
            if (this.hasFocus()) {
                viewHolder = object;
                if (this.mAdapter != null) {
                    viewHolder = this.getFocusedChild();
                }
            }
        }
        if ((viewHolder = viewHolder == null ? null : this.findContainingViewHolder((View)viewHolder)) == null) {
            this.resetFocusInfo();
            return;
        }
        object = this.mState;
        long l = this.mAdapter.hasStableIds() ? viewHolder.getItemId() : -1L;
        ((State)object).mFocusedItemId = l;
        object = this.mState;
        int n = this.mDataSetHasChangedAfterLayout ? -1 : (viewHolder.isRemoved() ? viewHolder.mOldPosition : viewHolder.getAdapterPosition());
        ((State)object).mFocusedItemPosition = n;
        this.mState.mFocusedSubChildId = this.getDeepestFocusedViewWithId(viewHolder.itemView);
    }

    private void setAdapterInternal(Adapter adapter, boolean bl, boolean bl2) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!bl || bl2) {
            this.removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        Adapter adapter2 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(adapter2, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter2, this.mAdapter, bl);
        this.mState.mStructureChanged = true;
        this.setDataSetChangedAfterLayout();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void absorbGlows(int n, int n2) {
        if (n < 0) {
            this.ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-n);
        } else if (n > 0) {
            this.ensureRightGlow();
            this.mRightGlow.onAbsorb(n);
        }
        if (n2 < 0) {
            this.ensureTopGlow();
            this.mTopGlow.onAbsorb(-n2);
        } else if (n2 > 0) {
            this.ensureBottomGlow();
            this.mBottomGlow.onAbsorb(n2);
        }
        if (n != 0 || n2 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, arrayList, n, n2)) {
            super.addFocusables(arrayList, n, n2);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration, -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addItemDecoration(ItemDecoration itemDecoration, int n) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(n, itemDecoration);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList<OnScrollListener>();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    void animateAppearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void animateDisappearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        this.addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void assertNotInLayoutOrScroll(String string2) {
        if (this.isComputingLayout()) {
            if (string2 == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + this.exceptionLabel());
            }
            throw new IllegalStateException(string2);
        }
        if (this.mDispatchScrollCounter > 0) {
            Log.w((String)"RecyclerView", (String)"Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", (Throwable)new IllegalStateException("" + this.exceptionLabel()));
        }
    }

    boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        return this.mItemAnimator == null || this.mItemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && this.mLayout.checkLayoutParams((LayoutParams)layoutParams);
    }

    void clearOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.clearOldPosition();
        }
        this.mRecycler.clearOldPositions();
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeHorizontalScrollExtent() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollExtent(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeHorizontalScrollOffset() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollOffset(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeHorizontalScrollRange() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollRange(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeVerticalScrollExtent() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollExtent(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeVerticalScrollOffset() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollOffset(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int computeVerticalScrollRange() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollRange(this.mState);
    }

    void considerReleasingGlowsOnScroll(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.mLeftGlow != null) {
            bl2 = bl;
            if (!this.mLeftGlow.isFinished()) {
                bl2 = bl;
                if (n > 0) {
                    this.mLeftGlow.onRelease();
                    bl2 = this.mLeftGlow.isFinished();
                }
            }
        }
        bl = bl2;
        if (this.mRightGlow != null) {
            bl = bl2;
            if (!this.mRightGlow.isFinished()) {
                bl = bl2;
                if (n < 0) {
                    this.mRightGlow.onRelease();
                    bl = bl2 | this.mRightGlow.isFinished();
                }
            }
        }
        bl2 = bl;
        if (this.mTopGlow != null) {
            bl2 = bl;
            if (!this.mTopGlow.isFinished()) {
                bl2 = bl;
                if (n2 > 0) {
                    this.mTopGlow.onRelease();
                    bl2 = bl | this.mTopGlow.isFinished();
                }
            }
        }
        bl = bl2;
        if (this.mBottomGlow != null) {
            bl = bl2;
            if (!this.mBottomGlow.isFinished()) {
                bl = bl2;
                if (n2 < 0) {
                    this.mBottomGlow.onRelease();
                    bl = bl2 | this.mBottomGlow.isFinished();
                }
            }
        }
        if (bl) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            TraceCompat.beginSection("RV FullInvalidate");
            this.dispatchLayout();
            TraceCompat.endSection();
            return;
        } else {
            if (!this.mAdapterHelper.hasPendingUpdates()) return;
            {
                if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                    TraceCompat.beginSection("RV PartialInvalidate");
                    this.eatRequestLayout();
                    this.onEnterLayoutOrScroll();
                    this.mAdapterHelper.preProcess();
                    if (!this.mLayoutRequestEaten) {
                        if (this.hasUpdatedView()) {
                            this.dispatchLayout();
                        } else {
                            this.mAdapterHelper.consumePostponedUpdates();
                        }
                    }
                    this.resumeRequestLayout(true);
                    this.onExitLayoutOrScroll();
                    TraceCompat.endSection();
                    return;
                }
                if (!this.mAdapterHelper.hasPendingUpdates()) return;
                {
                    TraceCompat.beginSection("RV FullInvalidate");
                    this.dispatchLayout();
                    TraceCompat.endSection();
                    return;
                }
            }
        }
    }

    void defaultOnMeasure(int n, int n2) {
        this.setMeasuredDimension(LayoutManager.chooseSize(n, this.getPaddingLeft() + this.getPaddingRight(), ViewCompat.getMinimumWidth((View)this)), LayoutManager.chooseSize(n2, this.getPaddingTop() + this.getPaddingBottom(), ViewCompat.getMinimumHeight((View)this)));
    }

    void dispatchChildAttached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildAttachedToWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewAttachedToWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i = this.mOnChildAttachStateListeners.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewAttachedToWindow(view);
            }
        }
    }

    void dispatchChildDetached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildDetachedFromWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewDetachedFromWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i = this.mOnChildAttachStateListeners.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewDetachedFromWindow(view);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e((String)"RecyclerView", (String)"No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)"RecyclerView", (String)"No layout manager attached; skipping layout");
            return;
        }
        this.mState.mIsMeasuring = false;
        if (this.mState.mLayoutStep == 1) {
            this.dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else if (this.mAdapterHelper.hasUpdates() || this.mLayout.getWidth() != this.getWidth() || this.mLayout.getHeight() != this.getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
        }
        this.dispatchLayoutStep3();
    }

    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.getScrollingChildHelper().dispatchNestedFling(f, f2, bl);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    void dispatchOnScrollStateChanged(int n) {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(n);
        }
        this.onScrollStateChanged(n);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, n);
        }
        if (this.mScrollListeners != null) {
            for (int i = this.mScrollListeners.size() - 1; i >= 0; --i) {
                this.mScrollListeners.get(i).onScrollStateChanged(this, n);
            }
        }
    }

    void dispatchOnScrolled(int n, int n2) {
        ++this.mDispatchScrollCounter;
        int n3 = this.getScrollX();
        int n4 = this.getScrollY();
        this.onScrollChanged(n3, n4, n3, n4);
        this.onScrolled(n, n2);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, n, n2);
        }
        if (this.mScrollListeners != null) {
            for (n3 = this.mScrollListeners.size() - 1; n3 >= 0; --n3) {
                this.mScrollListeners.get(n3).onScrolled(this, n, n2);
            }
        }
        --this.mDispatchScrollCounter;
    }

    /*
     * Enabled aggressive block sorting
     */
    void dispatchPendingImportantForAccessibilityChanges() {
        int n = this.mPendingAccessibilityImportanceChange.size() - 1;
        do {
            int n2;
            if (n < 0) {
                this.mPendingAccessibilityImportanceChange.clear();
                return;
            }
            ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(n);
            if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (n2 = viewHolder.mPendingAccessibilityState) != -1) {
                ViewCompat.setImportantForAccessibility(viewHolder.itemView, n2);
                viewHolder.mPendingAccessibilityState = -1;
            }
            --n;
        } while (true);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n;
        int n2;
        int n3 = 1;
        super.draw(canvas);
        int n4 = this.mItemDecorations.size();
        for (n2 = 0; n2 < n4; ++n2) {
            this.mItemDecorations.get(n2).onDrawOver(canvas, this, this.mState);
        }
        n4 = n2 = 0;
        if (this.mLeftGlow != null) {
            n4 = n2;
            if (!this.mLeftGlow.isFinished()) {
                n = canvas.save();
                n2 = this.mClipToPadding ? this.getPaddingBottom() : 0;
                canvas.rotate(270.0f);
                canvas.translate((float)(-this.getHeight() + n2), 0.0f);
                n4 = this.mLeftGlow != null && this.mLeftGlow.draw(canvas) ? 1 : 0;
                canvas.restoreToCount(n);
            }
        }
        n2 = n4;
        if (this.mTopGlow != null) {
            n2 = n4;
            if (!this.mTopGlow.isFinished()) {
                n = canvas.save();
                if (this.mClipToPadding) {
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                n2 = this.mTopGlow != null && this.mTopGlow.draw(canvas) ? 1 : 0;
                n2 = n4 | n2;
                canvas.restoreToCount(n);
            }
        }
        n4 = n2;
        if (this.mRightGlow != null) {
            n4 = n2;
            if (!this.mRightGlow.isFinished()) {
                n = canvas.save();
                int n5 = this.getWidth();
                n4 = this.mClipToPadding ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate((float)(-n4), (float)(-n5));
                n4 = this.mRightGlow != null && this.mRightGlow.draw(canvas) ? 1 : 0;
                n4 = n2 | n4;
                canvas.restoreToCount(n);
            }
        }
        n2 = n4;
        if (this.mBottomGlow != null) {
            n2 = n4;
            if (!this.mBottomGlow.isFinished()) {
                n = canvas.save();
                canvas.rotate(180.0f);
                if (this.mClipToPadding) {
                    canvas.translate((float)(-this.getWidth() + this.getPaddingRight()), (float)(-this.getHeight() + this.getPaddingBottom()));
                } else {
                    canvas.translate((float)(-this.getWidth()), (float)(-this.getHeight()));
                }
                n2 = this.mBottomGlow != null && this.mBottomGlow.draw(canvas) ? n3 : 0;
                n2 = n4 | n2;
                canvas.restoreToCount(n);
            }
        }
        n4 = n2;
        if (n2 == 0) {
            n4 = n2;
            if (this.mItemAnimator != null) {
                n4 = n2;
                if (this.mItemDecorations.size() > 0) {
                    n4 = n2;
                    if (this.mItemAnimator.isRunning()) {
                        n4 = 1;
                    }
                }
            }
        }
        if (n4 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long l) {
        return super.drawChild(canvas, view, l);
    }

    void eatRequestLayout() {
        ++this.mEatRequestLayout;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mBottomGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mBottomGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mLeftGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mLeftGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mRightGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mRightGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mTopGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mTopGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    String exceptionLabel() {
        return " " + super.toString() + ", adapter:" + this.mAdapter + ", layout:" + this.mLayout + ", context:" + (Object)this.getContext();
    }

    final void fillRemainingScrollValues(State state) {
        if (this.getScrollState() == 2) {
            OverScroller overScroller = this.mViewFlinger.mScroller;
            state.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
            state.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        state.mRemainingScrollHorizontal = 0;
        state.mRemainingScrollVertical = 0;
    }

    public View findChildViewUnder(float f, float f2) {
        for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
            View view = this.mChildHelper.getChildAt(i);
            float f3 = view.getTranslationX();
            float f4 = view.getTranslationY();
            if (!(f >= (float)view.getLeft() + f3) || !(f <= (float)view.getRight() + f3) || !(f2 >= (float)view.getTop() + f4) || !(f2 <= (float)view.getBottom() + f4)) continue;
            return view;
        }
        return null;
    }

    public View findContainingItemView(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        view = viewParent;
        while (view != null && view != this && view instanceof View) {
            view2 = view;
            view = view2.getParent();
        }
        if (view == this) {
            return view2;
        }
        return null;
    }

    public ViewHolder findContainingViewHolder(View view) {
        if ((view = this.findContainingItemView(view)) == null) {
            return null;
        }
        return this.getChildViewHolder(view);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ViewHolder findViewHolderForAdapterPosition(int n) {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        int n3 = 0;
        while (n3 < n2) {
            ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n3));
            ViewHolder viewHolder3 = viewHolder;
            if (viewHolder2 != null) {
                viewHolder3 = viewHolder;
                if (!viewHolder2.isRemoved()) {
                    viewHolder3 = viewHolder;
                    if (this.getAdapterPositionFor(viewHolder2) == n) {
                        viewHolder = viewHolder2;
                        if (!this.mChildHelper.isHidden(viewHolder2.itemView)) return viewHolder;
                        viewHolder3 = viewHolder2;
                    }
                }
            }
            ++n3;
            viewHolder = viewHolder3;
        }
        return viewHolder;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ViewHolder findViewHolderForItemId(long l) {
        if (this.mAdapter == null) return null;
        if (!this.mAdapter.hasStableIds()) {
            return null;
        }
        int n = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        int n2 = 0;
        while (n2 < n) {
            ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n2));
            ViewHolder viewHolder3 = viewHolder;
            if (viewHolder2 != null) {
                viewHolder3 = viewHolder;
                if (!viewHolder2.isRemoved()) {
                    viewHolder3 = viewHolder;
                    if (viewHolder2.getItemId() == l) {
                        viewHolder = viewHolder2;
                        if (!this.mChildHelper.isHidden(viewHolder2.itemView)) return viewHolder;
                        viewHolder3 = viewHolder2;
                    }
                }
            }
            ++n2;
            viewHolder = viewHolder3;
        }
        return viewHolder;
    }

    /*
     * Enabled aggressive block sorting
     */
    ViewHolder findViewHolderForPosition(int n, boolean bl) {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        for (int i = 0; i < n2; ++i) {
            ViewHolder viewHolder2;
            block3: {
                ViewHolder viewHolder3;
                block5: {
                    block4: {
                        viewHolder3 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
                        viewHolder2 = viewHolder;
                        if (viewHolder3 == null) break block3;
                        viewHolder2 = viewHolder;
                        if (viewHolder3.isRemoved()) break block3;
                        if (!bl) break block4;
                        if (viewHolder3.mPosition == n) break block5;
                        viewHolder2 = viewHolder;
                        break block3;
                    }
                    viewHolder2 = viewHolder;
                    if (viewHolder3.getLayoutPosition() != n) break block3;
                }
                viewHolder = viewHolder3;
                if (!this.mChildHelper.isHidden(viewHolder3.itemView)) break;
                viewHolder2 = viewHolder3;
            }
            viewHolder = viewHolder2;
        }
        return viewHolder;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fling(int n, int n2) {
        int n3;
        boolean bl;
        boolean bl2;
        int n4;
        block17: {
            block16: {
                block15: {
                    block14: {
                        if (this.mLayout == null) {
                            Log.e((String)"RecyclerView", (String)"Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            return false;
                        }
                        if (this.mLayoutFrozen) return false;
                        bl2 = this.mLayout.canScrollHorizontally();
                        bl = this.mLayout.canScrollVertically();
                        if (!bl2) break block14;
                        n4 = n;
                        if (Math.abs(n) >= this.mMinFlingVelocity) break block15;
                    }
                    n4 = 0;
                }
                if (!bl) break block16;
                n3 = n2;
                if (Math.abs(n2) >= this.mMinFlingVelocity) break block17;
            }
            n3 = 0;
        }
        if (n4 == 0 && n3 == 0 || this.dispatchNestedPreFling(n4, n3)) return false;
        {
            boolean bl3 = bl2 || bl;
            this.dispatchNestedFling(n4, n3, bl3);
            if (this.mOnFlingListener != null && this.mOnFlingListener.onFling(n4, n3)) {
                return true;
            }
            if (!bl3) return false;
            {
                n = 0;
                if (bl2) {
                    n = false | true;
                }
                n2 = n;
                if (bl) {
                    n2 = n | 2;
                }
                this.startNestedScroll(n2, 1);
                n = Math.max(-this.mMaxFlingVelocity, Math.min(n4, this.mMaxFlingVelocity));
                n2 = Math.max(-this.mMaxFlingVelocity, Math.min(n3, this.mMaxFlingVelocity));
                this.mViewFlinger.fling(n, n2);
                return true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public View focusSearch(View view, int n) {
        int n2;
        View view2 = this.mLayout.onInterceptFocusSearch(view, n);
        if (view2 != null) {
            return view2;
        }
        int n3 = this.mAdapter != null && this.mLayout != null && !this.isComputingLayout() && !this.mLayoutFrozen ? 1 : 0;
        view2 = FocusFinder.getInstance();
        if (n3 != 0 && (n == 2 || n == 1)) {
            int n4;
            int n5 = 0;
            n3 = n;
            if (this.mLayout.canScrollVertically()) {
                n4 = n == 2 ? 130 : 33;
                n2 = view2.findNextFocus((ViewGroup)this, view, n4) == null ? 1 : 0;
                n5 = n2;
                n3 = n;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    n3 = n4;
                    n5 = n2;
                }
            }
            int n6 = n5;
            n2 = n3;
            if (n5 == 0) {
                n6 = n5;
                n2 = n3;
                if (this.mLayout.canScrollHorizontally()) {
                    n = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
                    n4 = n3 == 2 ? 1 : 0;
                    n = (n4 ^ n) != 0 ? 66 : 17;
                    n4 = view2.findNextFocus((ViewGroup)this, view, n) == null ? 1 : 0;
                    n6 = n4;
                    n2 = n3;
                    if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                        n2 = n;
                        n6 = n4;
                    }
                }
            }
            if (n6 != 0) {
                this.consumePendingUpdateOperations();
                if (this.findContainingItemView(view) == null) {
                    return null;
                }
                this.eatRequestLayout();
                this.mLayout.onFocusSearchFailed(view, n2, this.mRecycler, this.mState);
                this.resumeRequestLayout(false);
            }
            view2 = view2.findNextFocus((ViewGroup)this, view, n2);
        } else {
            View view3;
            view2 = view3 = view2.findNextFocus((ViewGroup)this, view, n);
            n2 = n;
            if (view3 == null) {
                view2 = view3;
                n2 = n;
                if (n3 != 0) {
                    this.consumePendingUpdateOperations();
                    if (this.findContainingItemView(view) == null) {
                        return null;
                    }
                    this.eatRequestLayout();
                    view2 = this.mLayout.onFocusSearchFailed(view, n, this.mRecycler, this.mState);
                    this.resumeRequestLayout(false);
                    n2 = n;
                }
            }
        }
        if (view2 != null && !view2.hasFocusable()) {
            if (this.getFocusedChild() == null) {
                return super.focusSearch(view, n2);
            }
            this.requestChildOnScreen(view2, null);
            return view;
        }
        if (!this.isPreferredNextFocus(view, view2, n2)) return super.focusSearch(view, n2);
        return view2;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager" + this.exceptionLabel());
        }
        return this.mLayout.generateDefaultLayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager" + this.exceptionLabel());
        }
        return this.mLayout.generateLayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager" + this.exceptionLabel());
        }
        return this.mLayout.generateLayoutParams(layoutParams);
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    int getAdapterPositionFor(ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(524) || !viewHolder.isBound()) {
            return -1;
        }
        return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
    }

    public int getBaseline() {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    long getChangedHolderKey(ViewHolder viewHolder) {
        if (this.mAdapter.hasStableIds()) {
            return viewHolder.getItemId();
        }
        return viewHolder.mPosition;
    }

    public int getChildAdapterPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return ((ViewHolder)object).getAdapterPosition();
        }
        return -1;
    }

    protected int getChildDrawingOrder(int n, int n2) {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(n, n2);
        }
        return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(n, n2);
    }

    public int getChildLayoutPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return ((ViewHolder)object).getLayoutPosition();
        }
        return -1;
    }

    public ViewHolder getChildViewHolder(View view) {
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent != this) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a direct child of " + this);
        }
        return RecyclerView.getChildViewHolderInt(view);
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0L;
    }

    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public boolean hasNestedScrollingParent() {
        return this.getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasNestedScrollingParent(int n) {
        return this.getScrollingChildHelper().hasNestedScrollingParent(n);
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback(){

            void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                switch (updateOp.cmd) {
                    default: {
                        return;
                    }
                    case 1: {
                        RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    case 2: {
                        RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    case 4: {
                        RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                        return;
                    }
                    case 8: 
                }
                RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public ViewHolder findViewHolder(int n) {
                ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n, true);
                if (viewHolder == null) {
                    return null;
                }
                ViewHolder viewHolder2 = viewHolder;
                if (!RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) return viewHolder2;
                return null;
            }

            @Override
            public void markViewHoldersUpdated(int n, int n2, Object object) {
                RecyclerView.this.viewRangeUpdate(n, n2, object);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override
            public void offsetPositionsForAdd(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForInsert(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForMove(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForMove(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                State state = RecyclerView.this.mState;
                state.mDeletedInvisibleItemCountSincePreviousLayout += n2;
            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }
        });
    }

    void initFastScroller(StateListDrawable stateListDrawable, Drawable drawable2, StateListDrawable stateListDrawable2, Drawable drawable3) {
        if (stateListDrawable == null || drawable2 == null || stateListDrawable2 == null || drawable3 == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + this.exceptionLabel());
        }
        Resources resources = this.getContext().getResources();
        new FastScroller(this, stateListDrawable, drawable2, stateListDrawable2, drawable3, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    boolean isAccessibilityEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.getScrollingChildHelper().isNestedScrollingEnabled();
    }

    void jumpToPositionForSmoothScroller(int n) {
        if (this.mLayout == null) {
            return;
        }
        this.mLayout.scrollToPosition(n);
        this.awakenScrollBars();
    }

    void markItemDecorInsetsDirty() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ((LayoutParams)this.mChildHelper.getUnfilteredChildAt((int)i).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    void markKnownViewsInvalid() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(6);
        }
        this.markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void offsetChildrenHorizontal(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetLeftAndRight(n);
        }
    }

    public void offsetChildrenVertical(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetTopAndBottom(n);
        }
    }

    void offsetPositionRecordsForInsert(int n, int n2) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n) continue;
            viewHolder.offsetPosition(n2, false);
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForInsert(n, n2);
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    void offsetPositionRecordsForMove(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = this.mChildHelper.getUnfilteredChildCount();
        if (n < n2) {
            n4 = n;
            n5 = n2;
            n3 = -1;
        } else {
            n4 = n2;
            n5 = n;
            n3 = 1;
        }
        int n7 = 0;
        do {
            if (n7 >= n6) {
                this.mRecycler.offsetPositionRecordsForMove(n, n2);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n7));
            if (viewHolder != null && viewHolder.mPosition >= n4 && viewHolder.mPosition <= n5) {
                if (viewHolder.mPosition == n) {
                    viewHolder.offsetPosition(n2 - n, false);
                } else {
                    viewHolder.offsetPosition(n3, false);
                }
                this.mState.mStructureChanged = true;
            }
            ++n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.mRecycler.offsetPositionRecordsForRemove(n, n2, bl);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n4));
            if (viewHolder != null && !viewHolder.shouldIgnore()) {
                if (viewHolder.mPosition >= n + n2) {
                    viewHolder.offsetPosition(-n2, bl);
                    this.mState.mStructureChanged = true;
                } else if (viewHolder.mPosition >= n) {
                    viewHolder.flagRemovedAndOffsetPosition(n - 1, -n2, bl);
                    this.mState.mStructureChanged = true;
                }
            }
            ++n4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onAttachedToWindow() {
        boolean bl = true;
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || this.isLayoutRequested()) {
            bl = false;
        }
        this.mFirstLayoutComplete = bl;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = GapWorker.sGapWorker.get();
            if (this.mGapWorker == null) {
                float f;
                this.mGapWorker = new GapWorker();
                Display display = ViewCompat.getDisplay((View)this);
                float f2 = f = 60.0f;
                if (!this.isInEditMode()) {
                    f2 = f;
                    if (display != null) {
                        float f3 = display.getRefreshRate();
                        f2 = f;
                        if (f3 >= 30.0f) {
                            f2 = f3;
                        }
                    }
                }
                this.mGapWorker.mFrameIntervalNs = (long)(1.0E9f / f2);
                GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        this.stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        this.removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mItemDecorations.get(i).onDraw(canvas, this, this.mState);
        }
    }

    void onEnterLayoutOrScroll() {
        ++this.mLayoutOrScrollCounter;
    }

    void onExitLayoutOrScroll() {
        this.onExitLayoutOrScroll(true);
    }

    void onExitLayoutOrScroll(boolean bl) {
        --this.mLayoutOrScrollCounter;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (bl) {
                this.dispatchContentChangedIfNecessary();
                this.dispatchPendingImportantForAccessibilityChanges();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        if (this.mLayout == null) return false;
        if (this.mLayoutFrozen) return false;
        if (motionEvent.getAction() != 8) return false;
        if ((motionEvent.getSource() & 2) != 0) {
            f2 = this.mLayout.canScrollVertically() ? -motionEvent.getAxisValue(9) : 0.0f;
            f = this.mLayout.canScrollHorizontally() ? motionEvent.getAxisValue(10) : 0.0f;
        } else {
            if ((motionEvent.getSource() & 0x400000) == 0) return false;
            f = motionEvent.getAxisValue(26);
            if (this.mLayout.canScrollVertically()) {
                f2 = -f;
                f = 0.0f;
            } else {
                if (!this.mLayout.canScrollHorizontally()) return false;
                f2 = 0.0f;
            }
        }
        if (f2 == 0.0f && f == 0.0f) {
            return false;
        }
        this.scrollByInternal((int)(this.mScaledHorizontalScrollFactor * f), (int)(this.mScaledVerticalScrollFactor * f2), motionEvent);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent arrn) {
        if (this.mLayoutFrozen) {
            return false;
        }
        if (this.dispatchOnItemTouchIntercept((MotionEvent)arrn)) {
            this.cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        boolean bl = this.mLayout.canScrollHorizontally();
        boolean bl2 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)arrn);
        int n = arrn.getActionMasked();
        int n2 = arrn.getActionIndex();
        switch (n) {
            case 0: {
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = arrn.getPointerId(0);
                this.mLastTouchX = n2 = (int)(arrn.getX() + 0.5f);
                this.mInitialTouchX = n2;
                this.mLastTouchY = n2 = (int)(arrn.getY() + 0.5f);
                this.mInitialTouchY = n2;
                if (this.mScrollState == 2) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                }
                arrn = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                arrn[0] = 0;
                n2 = 0;
                if (bl) {
                    n2 = false | true;
                }
                n = n2;
                if (bl2) {
                    n = n2 | 2;
                }
                this.startNestedScroll(n, 0);
                break;
            }
            case 5: {
                this.mScrollPointerId = arrn.getPointerId(n2);
                this.mLastTouchX = n = (int)(arrn.getX(n2) + 0.5f);
                this.mInitialTouchX = n;
                this.mLastTouchY = n2 = (int)(arrn.getY(n2) + 0.5f);
                this.mInitialTouchY = n2;
                break;
            }
            case 2: {
                n2 = arrn.findPointerIndex(this.mScrollPointerId);
                if (n2 < 0) {
                    Log.e((String)"RecyclerView", (String)("Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?"));
                    return false;
                }
                int n3 = (int)(arrn.getX(n2) + 0.5f);
                int n4 = (int)(arrn.getY(n2) + 0.5f);
                if (this.mScrollState == 1) break;
                int n5 = this.mInitialTouchX;
                int n6 = this.mInitialTouchY;
                n2 = n = 0;
                if (bl) {
                    n2 = n;
                    if (Math.abs(n3 - n5) > this.mTouchSlop) {
                        this.mLastTouchX = n3;
                        n2 = 1;
                    }
                }
                n = n2;
                if (bl2) {
                    n = n2;
                    if (Math.abs(n4 - n6) > this.mTouchSlop) {
                        this.mLastTouchY = n4;
                        n = 1;
                    }
                }
                if (n == 0) break;
                this.setScrollState(1);
                break;
            }
            case 6: {
                this.onPointerUp((MotionEvent)arrn);
                break;
            }
            case 1: {
                this.mVelocityTracker.clear();
                this.stopNestedScroll(0);
                break;
            }
            case 3: {
                this.cancelTouch();
            }
        }
        return this.mScrollState == 1;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        TraceCompat.beginSection("RV OnLayout");
        this.dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        boolean bl = false;
        if (this.mLayout == null) {
            this.defaultOnMeasure(n, n2);
            return;
        }
        if (this.mLayout.mAutoMeasure) {
            int n3 = View.MeasureSpec.getMode((int)n);
            int n4 = View.MeasureSpec.getMode((int)n2);
            boolean bl2 = bl;
            if (n3 == 1073741824) {
                bl2 = bl;
                if (n4 == 1073741824) {
                    bl2 = true;
                }
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            if (bl2 || this.mAdapter == null) return;
            {
                if (this.mState.mLayoutStep == 1) {
                    this.dispatchLayoutStep1();
                }
                this.mLayout.setMeasureSpecs(n, n2);
                this.mState.mIsMeasuring = true;
                this.dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(n, n2);
                if (!this.mLayout.shouldMeasureTwice()) return;
                {
                    this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824));
                    this.mState.mIsMeasuring = true;
                    this.dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren(n, n2);
                    return;
                }
            }
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            this.eatRequestLayout();
            this.onEnterLayoutOrScroll();
            this.processAdapterUpdatesAndSetAnimationFlags();
            this.onExitLayoutOrScroll();
            if (this.mState.mRunPredictiveAnimations) {
                this.mState.mInPreLayout = true;
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                this.mState.mInPreLayout = false;
            }
            this.mAdapterUpdateDuringMeasure = false;
            this.resumeRequestLayout(false);
        } else if (this.mState.mRunPredictiveAnimations) {
            this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());
            return;
        }
        this.mState.mItemCount = this.mAdapter != null ? this.mAdapter.getItemCount() : 0;
        this.eatRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
        this.resumeRequestLayout(false);
        this.mState.mInPreLayout = false;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        if (this.isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(n, rect);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        } else {
            this.mPendingSavedState = (SavedState)parcelable;
            super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
            if (this.mLayout == null || this.mPendingSavedState.mLayoutState == null) return;
            {
                this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
                return;
            }
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            savedState.copyFrom(this.mPendingSavedState);
            return savedState;
        }
        if (this.mLayout != null) {
            savedState.mLayoutState = this.mLayout.onSaveInstanceState();
            return savedState;
        }
        savedState.mLayoutState = null;
        return savedState;
    }

    public void onScrollStateChanged(int n) {
    }

    public void onScrolled(int n, int n2) {
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3 || n2 != n4) {
            this.invalidateGlows();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        if (this.mLayoutFrozen != false) return false;
        if (this.mIgnoreMotionEventTillDown) {
            return false;
        }
        if (this.dispatchOnItemTouch((MotionEvent)var1_1)) {
            this.cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        var13_2 = this.mLayout.canScrollHorizontally();
        var14_3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        var10_4 = 0;
        var15_5 = MotionEvent.obtain((MotionEvent)var1_1);
        var6_6 = var1_1.getActionMasked();
        var5_7 = var1_1.getActionIndex();
        if (var6_6 == 0) {
            var16_8 = this.mNestedOffsets;
            this.mNestedOffsets[1] = 0;
            var16_8[0] = 0;
        }
        var15_5.offsetLocation((float)this.mNestedOffsets[0], (float)this.mNestedOffsets[1]);
        var4_9 = var10_4;
        switch (var6_6) {
            default: {
                var4_9 = var10_4;
                break;
            }
            case 0: {
                this.mScrollPointerId = var1_1.getPointerId(0);
                this.mLastTouchX = var4_9 = (int)(var1_1.getX() + 0.5f);
                this.mInitialTouchX = var4_9;
                this.mLastTouchY = var4_9 = (int)(var1_1.getY() + 0.5f);
                this.mInitialTouchY = var4_9;
                var4_9 = 0;
                if (var13_2) {
                    var4_9 = false | true;
                }
                var5_7 = var4_9;
                if (var14_3) {
                    var5_7 = var4_9 | 2;
                }
                this.startNestedScroll(var5_7, 0);
                var4_9 = var10_4;
                break;
            }
            case 5: {
                this.mScrollPointerId = var1_1.getPointerId(var5_7);
                this.mLastTouchX = var4_9 = (int)(var1_1.getX(var5_7) + 0.5f);
                this.mInitialTouchX = var4_9;
                this.mLastTouchY = var4_9 = (int)(var1_1.getY(var5_7) + 0.5f);
                this.mInitialTouchY = var4_9;
                var4_9 = var10_4;
                break;
            }
            case 2: {
                var4_9 = var1_1.findPointerIndex(this.mScrollPointerId);
                if (var4_9 < 0) {
                    Log.e((String)"RecyclerView", (String)("Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?"));
                    return false;
                }
                var11_10 = (int)(var1_1.getX(var4_9) + 0.5f);
                var12_11 = (int)(var1_1.getY(var4_9) + 0.5f);
                var7_12 = this.mLastTouchX - var11_10;
                var6_6 = this.mLastTouchY - var12_11;
                var5_7 = var7_12;
                var4_9 = var6_6;
                if (this.dispatchNestedPreScroll(var7_12, var6_6, this.mScrollConsumed, this.mScrollOffset, 0)) {
                    var5_7 = var7_12 - this.mScrollConsumed[0];
                    var4_9 = var6_6 - this.mScrollConsumed[1];
                    var15_5.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
                    var1_1 = this.mNestedOffsets;
                    var1_1[0] = var1_1[0] + this.mScrollOffset[0];
                    var1_1 = this.mNestedOffsets;
                    var1_1[1] = var1_1[1] + this.mScrollOffset[1];
                }
                var6_6 = var5_7;
                var7_12 = var4_9;
                if (this.mScrollState != 1) {
                    var7_12 = 0;
                    var8_13 = var5_7;
                    var6_6 = var7_12;
                    if (var13_2) {
                        var8_13 = var5_7;
                        var6_6 = var7_12;
                        if (Math.abs(var5_7) > this.mTouchSlop) {
                            var8_13 = var5_7 > 0 ? var5_7 - this.mTouchSlop : var5_7 + this.mTouchSlop;
                            var6_6 = 1;
                        }
                    }
                    var5_7 = var4_9;
                    var9_14 = var6_6;
                    if (var14_3) {
                        var5_7 = var4_9;
                        var9_14 = var6_6;
                        if (Math.abs(var4_9) > this.mTouchSlop) {
                            var5_7 = var4_9 > 0 ? var4_9 - this.mTouchSlop : var4_9 + this.mTouchSlop;
                            var9_14 = 1;
                        }
                    }
                    var6_6 = var8_13;
                    var7_12 = var5_7;
                    if (var9_14 != 0) {
                        this.setScrollState(1);
                        var7_12 = var5_7;
                        var6_6 = var8_13;
                    }
                }
                var4_9 = var10_4;
                if (this.mScrollState != 1) ** GOTO lbl126
                this.mLastTouchX = var11_10 - this.mScrollOffset[0];
                this.mLastTouchY = var12_11 - this.mScrollOffset[1];
                var4_9 = var13_2 != false ? var6_6 : 0;
                if (this.scrollByInternal(var4_9, var5_7 = var14_3 != false ? var7_12 : 0, var15_5)) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }
                var4_9 = var10_4;
                if (this.mGapWorker == null) ** GOTO lbl126
                if (var6_6 != 0) ** GOTO lbl109
                var4_9 = var10_4;
                if (var7_12 == 0) ** GOTO lbl126
lbl109:
                // 2 sources
                this.mGapWorker.postFromTraversal(this, var6_6, var7_12);
                var4_9 = var10_4;
                break;
            }
            case 6: {
                this.onPointerUp((MotionEvent)var1_1);
                var4_9 = var10_4;
                break;
            }
            case 1: {
                this.mVelocityTracker.addMovement(var15_5);
                var4_9 = 1;
                this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaxFlingVelocity);
                var2_15 = var13_2 != false ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                var3_16 = var14_3 != false ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                if (var2_15 == 0.0f && var3_16 == 0.0f || !this.fling((int)var2_15, (int)var3_16)) {
                    this.setScrollState(0);
                }
                this.resetTouch();
            }
lbl126:
            // 5 sources
            case 4: {
                break;
            }
            case 3: {
                this.cancelTouch();
                var4_9 = var10_4;
            }
        }
        if (var4_9 == 0) {
            this.mVelocityTracker.addMovement(var15_5);
        }
        var15_5.recycle();
        return true;
    }

    void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation((View)this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            long l = this.getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l, viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    void removeAndRecycleViews() {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        if (this.mLayout != null) {
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean removeAnimatingView(View object) {
        this.eatRequestLayout();
        boolean bl = this.mChildHelper.removeViewIfHidden((View)object);
        if (bl) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(object);
            this.mRecycler.unscrapView(viewHolder);
            this.mRecycler.recycleViewHolderInternal(viewHolder);
        }
        boolean bl2 = !bl;
        this.resumeRequestLayout(bl2);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void removeDetachedView(View view, boolean bl) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder != null) {
            if (viewHolder.isTmpDetached()) {
                viewHolder.clearTmpDetachFlag();
            } else if (!viewHolder.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + viewHolder + this.exceptionLabel());
            }
        }
        view.clearAnimation();
        this.dispatchChildDetached(view);
        super.removeDetachedView(view, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeItemDecoration(ItemDecoration itemDecoration) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            boolean bl = this.getOverScrollMode() == 2;
            this.setWillNotDraw(bl);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            return;
        }
        this.mOnChildAttachStateListeners.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove(onScrollListener);
        }
    }

    void repositionShadowingViews() {
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.mChildHelper.getChildAt(i);
            ViewHolder viewHolder = this.getChildViewHolder(view);
            if (viewHolder == null || viewHolder.mShadowingHolder == null) continue;
            viewHolder = viewHolder.mShadowingHolder.itemView;
            int n2 = view.getLeft();
            int n3 = view.getTop();
            if (n2 == viewHolder.getLeft() && n3 == viewHolder.getTop()) continue;
            viewHolder.layout(n2, n3, viewHolder.getWidth() + n2, viewHolder.getHeight() + n3);
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        int n = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n; ++i) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(bl);
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        if (this.mEatRequestLayout == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
            return;
        }
        this.mLayoutRequestEaten = true;
    }

    void resumeRequestLayout(boolean bl) {
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!bl) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (bl && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                this.dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        --this.mEatRequestLayout;
    }

    void saveOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.saveOldPosition();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void scrollBy(int n, int n2) {
        if (this.mLayout == null) {
            Log.e((String)"RecyclerView", (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) return;
        boolean bl = this.mLayout.canScrollHorizontally();
        boolean bl2 = this.mLayout.canScrollVertically();
        if (!bl && !bl2) return;
        if (bl) {
        } else {
            n = 0;
        }
        if (!bl2) {
            n2 = 0;
        }
        this.scrollByInternal(n, n2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean scrollByInternal(int n, int n2, MotionEvent arrn) {
        boolean bl = false;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        this.consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            this.eatRequestLayout();
            this.onEnterLayoutOrScroll();
            TraceCompat.beginSection("RV Scroll");
            this.fillRemainingScrollValues(this.mState);
            n7 = n8;
            n3 = n4;
            if (n != 0) {
                n7 = this.mLayout.scrollHorizontallyBy(n, this.mRecycler, this.mState);
                n3 = n - n7;
            }
            n9 = n10;
            n5 = n6;
            if (n2 != 0) {
                n9 = this.mLayout.scrollVerticallyBy(n2, this.mRecycler, this.mState);
                n5 = n2 - n9;
            }
            TraceCompat.endSection();
            this.repositionShadowingViews();
            this.onExitLayoutOrScroll();
            this.resumeRequestLayout(false);
        }
        if (!this.mItemDecorations.isEmpty()) {
            this.invalidate();
        }
        if (this.dispatchNestedScroll(n7, n9, n3, n5, this.mScrollOffset, 0)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (arrn != null) {
                arrn.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
            }
            arrn = this.mNestedOffsets;
            arrn[0] = arrn[0] + this.mScrollOffset[0];
            arrn = this.mNestedOffsets;
            arrn[1] = arrn[1] + this.mScrollOffset[1];
        } else if (this.getOverScrollMode() != 2) {
            if (arrn != null && !MotionEventCompat.isFromSource((MotionEvent)arrn, 8194)) {
                this.pullGlows(arrn.getX(), n3, arrn.getY(), n5);
            }
            this.considerReleasingGlowsOnScroll(n, n2);
        }
        if (n7 != 0 || n9 != 0) {
            this.dispatchOnScrolled(n7, n9);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        if (n7 != 0) return true;
        if (n9 == 0) return bl;
        return true;
    }

    public void scrollTo(int n, int n2) {
        Log.w((String)"RecyclerView", (String)"RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        this.stopScroll();
        if (this.mLayout == null) {
            Log.e((String)"RecyclerView", (String)"Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.scrollToPosition(n);
        this.awakenScrollBars();
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate((View)this, this.mAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, false, true);
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        boolean bl = this.mChildDrawingOrderCallback != null;
        this.setChildrenDrawingOrderEnabled(bl);
    }

    boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int n) {
        if (this.isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = n;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, n);
        return true;
    }

    public void setClipToPadding(boolean bl) {
        if (bl != this.mClipToPadding) {
            this.invalidateGlows();
        }
        this.mClipToPadding = bl;
        super.setClipToPadding(bl);
        if (this.mFirstLayoutComplete) {
            this.requestLayout();
        }
    }

    void setDataSetChangedAfterLayout() {
        this.mDataSetHasChangedAfterLayout = true;
        this.markKnownViewsInvalid();
    }

    public void setHasFixedSize(boolean bl) {
        this.mHasFixedSize = bl;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int n) {
        this.mRecycler.setViewCacheSize(n);
    }

    public void setLayoutFrozen(boolean bl) {
        block5: {
            block4: {
                if (bl == this.mLayoutFrozen) break block4;
                this.assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
                if (bl) break block5;
                this.mLayoutFrozen = false;
                if (this.mLayoutRequestEaten && this.mLayout != null && this.mAdapter != null) {
                    this.requestLayout();
                }
                this.mLayoutRequestEaten = false;
            }
            return;
        }
        long l = SystemClock.uptimeMillis();
        this.onTouchEvent(MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0));
        this.mLayoutFrozen = true;
        this.mIgnoreMotionEventTillDown = true;
        this.stopScroll();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager == this.mLayout) {
            return;
        }
        this.stopScroll();
        if (this.mLayout != null) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            this.mRecycler.clear();
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            this.mRecycler.clear();
        }
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                throw new IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView:" + layoutManager.mRecyclerView.exceptionLabel());
            }
            this.mLayout.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.dispatchAttachedToWindow(this);
            }
        }
        this.mRecycler.updateViewCacheSize();
        this.requestLayout();
    }

    public void setNestedScrollingEnabled(boolean bl) {
        this.getScrollingChildHelper().setNestedScrollingEnabled(bl);
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean bl) {
        this.mPreserveFocusAfterLayout = bl;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    void setScrollState(int n) {
        if (n == this.mScrollState) {
            return;
        }
        this.mScrollState = n;
        if (n != 2) {
            this.stopScrollersInternal();
        }
        this.dispatchOnScrollStateChanged(n);
    }

    public void setScrollingTouchSlop(int n) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        switch (n) {
            default: {
                Log.w((String)"RecyclerView", (String)("setScrollingTouchSlop(): bad argument constant " + n + "; using default value"));
            }
            case 0: {
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                return;
            }
            case 1: 
        }
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.isComputingLayout()) {
            int n = 0;
            if (accessibilityEvent != null) {
                n = AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent);
            }
            int n2 = n;
            if (n == 0) {
                n2 = 0;
            }
            this.mEatenAccessibilityChangeFlags |= n2;
            return true;
        }
        return false;
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void smoothScrollBy(int n, int n2, Interpolator interpolator) {
        if (this.mLayout == null) {
            Log.e((String)"RecyclerView", (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        } else {
            if (this.mLayoutFrozen) return;
            {
                if (!this.mLayout.canScrollHorizontally()) {
                    n = 0;
                }
                if (!this.mLayout.canScrollVertically()) {
                    n2 = 0;
                }
                if (n == 0 && n2 == 0) return;
                {
                    this.mViewFlinger.smoothScrollBy(n, n2, interpolator);
                    return;
                }
            }
        }
    }

    public void smoothScrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)"RecyclerView", (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.smoothScrollToPosition(this, this.mState, n);
    }

    public boolean startNestedScroll(int n) {
        return this.getScrollingChildHelper().startNestedScroll(n);
    }

    public boolean startNestedScroll(int n, int n2) {
        return this.getScrollingChildHelper().startNestedScroll(n, n2);
    }

    @Override
    public void stopNestedScroll() {
        this.getScrollingChildHelper().stopNestedScroll();
    }

    public void stopNestedScroll(int n) {
        this.getScrollingChildHelper().stopNestedScroll(n);
    }

    public void stopScroll() {
        this.setScrollState(0);
        this.stopScrollersInternal();
    }

    /*
     * Enabled aggressive block sorting
     */
    void viewRangeUpdate(int n, int n2, Object object) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.mRecycler.viewRangeUpdate(n, n2);
                return;
            }
            View view = this.mChildHelper.getUnfilteredChildAt(n4);
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= n && viewHolder.mPosition < n + n2) {
                viewHolder.addFlags(2);
                viewHolder.addChangePayload(object);
                ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
            }
            ++n4;
        } while (true);
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public final void bindViewHolder(VH object, int n) {
            ((ViewHolder)object).mPosition = n;
            if (this.hasStableIds()) {
                ((ViewHolder)object).mItemId = this.getItemId(n);
            }
            ((ViewHolder)object).setFlags(1, 519);
            TraceCompat.beginSection("RV OnBindView");
            this.onBindViewHolder(object, n, ((ViewHolder)object).getUnmodifiedPayloads());
            ((ViewHolder)object).clearPayload();
            object = ((ViewHolder)object).itemView.getLayoutParams();
            if (object instanceof LayoutParams) {
                ((LayoutParams)object).mInsetsDirty = true;
            }
            TraceCompat.endSection();
        }

        public final VH createViewHolder(ViewGroup object, int n) {
            TraceCompat.beginSection("RV CreateView");
            object = this.onCreateViewHolder((ViewGroup)object, n);
            object.mItemViewType = n;
            TraceCompat.endSection();
            return (VH)object;
        }

        public abstract int getItemCount();

        public long getItemId(int n) {
            return -1L;
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int n) {
            this.mObservable.notifyItemRangeChanged(n, 1);
        }

        public final void notifyItemChanged(int n, Object object) {
            this.mObservable.notifyItemRangeChanged(n, 1, object);
        }

        public final void notifyItemInserted(int n) {
            this.mObservable.notifyItemRangeInserted(n, 1);
        }

        public final void notifyItemMoved(int n, int n2) {
            this.mObservable.notifyItemMoved(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2) {
            this.mObservable.notifyItemRangeChanged(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2, Object object) {
            this.mObservable.notifyItemRangeChanged(n, n2, object);
        }

        public final void notifyItemRangeInserted(int n, int n2) {
            this.mObservable.notifyItemRangeInserted(n, n2);
        }

        public final void notifyItemRangeRemoved(int n, int n2) {
            this.mObservable.notifyItemRangeRemoved(n, n2);
        }

        public final void notifyItemRemoved(int n) {
            this.mObservable.notifyItemRangeRemoved(n, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH var1, int var2);

        public void onBindViewHolder(VH VH, int n, List<Object> list) {
            this.onBindViewHolder(VH, n);
        }

        public abstract VH onCreateViewHolder(ViewGroup var1, int var2);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH VH) {
            return false;
        }

        public void onViewAttachedToWindow(VH VH) {
        }

        public void onViewDetachedFromWindow(VH VH) {
        }

        public void onViewRecycled(VH VH) {
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver((Object)adapterDataObserver);
        }

        public void setHasStableIds(boolean bl) {
            if (this.hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = bl;
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver((Object)adapterDataObserver);
        }
    }

    static class AdapterDataObservable
    extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onChanged();
            }
        }

        public void notifyItemMoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(n, n2, 1);
            }
        }

        public void notifyItemRangeChanged(int n, int n2) {
            this.notifyItemRangeChanged(n, n2, null);
        }

        public void notifyItemRangeChanged(int n, int n2, Object object) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(n, n2, object);
            }
        }

        public void notifyItemRangeInserted(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(n, n2);
            }
        }

        public void notifyItemRangeRemoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(n, n2);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int n, int n2) {
        }

        public void onItemRangeChanged(int n, int n2, Object object) {
            this.onItemRangeChanged(n, n2);
        }

        public void onItemRangeInserted(int n, int n2) {
        }

        public void onItemRangeMoved(int n, int n2, int n3) {
        }

        public void onItemRangeRemoved(int n, int n2) {
        }
    }

    public static interface ChildDrawingOrderCallback {
        public int onGetChildDrawingOrder(int var1, int var2);
    }

    public static abstract class ItemAnimator {
        private long mAddDuration = 120L;
        private long mChangeDuration = 250L;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250L;
        private long mRemoveDuration = 120L;

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int n = viewHolder.mFlags & 0xE;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            int n2 = n;
            if ((n & 4) == 0) {
                int n3 = viewHolder.getOldPosition();
                int n4 = viewHolder.getAdapterPosition();
                n2 = n;
                if (n3 != -1) {
                    n2 = n;
                    if (n4 != -1) {
                        n2 = n;
                        if (n3 != n4) {
                            n2 = n | 0x800;
                        }
                    }
                }
            }
            return n2;
        }

        public abstract boolean animateAppearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animateChange(ViewHolder var1, ViewHolder var2, ItemHolderInfo var3, ItemHolderInfo var4);

        public abstract boolean animateDisappearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animatePersistence(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
            return this.canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            this.onAnimationFinished(viewHolder);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished(viewHolder);
            }
        }

        public final void dispatchAnimationsFinished() {
            int n = this.mFinishedListeners.size();
            for (int i = 0; i < n; ++i) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimation(ViewHolder var1);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean bl;
            block3: {
                block2: {
                    bl = this.isRunning();
                    if (itemAnimatorFinishedListener == null) break block2;
                    if (bl) break block3;
                    itemAnimatorFinishedListener.onAnimationsFinished();
                }
                return bl;
            }
            this.mFinishedListeners.add(itemAnimatorFinishedListener);
            return bl;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public ItemHolderInfo recordPostLayoutInformation(State state, ViewHolder viewHolder) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public ItemHolderInfo recordPreLayoutInformation(State state, ViewHolder viewHolder, int n, List<Object> list) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        void setListener(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public static interface ItemAnimatorFinishedListener {
            public void onAnimationsFinished();
        }

        static interface ItemAnimatorListener {
            public void onAnimationFinished(ViewHolder var1);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return this.setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int n) {
                viewHolder = viewHolder.itemView;
                this.left = viewHolder.getLeft();
                this.top = viewHolder.getTop();
                this.right = viewHolder.getRight();
                this.bottom = viewHolder.getBottom();
                return this;
            }
        }

    }

    private class ItemAnimatorRestoreListener
    implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int n, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            this.getItemOffsets(rect, ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        ViewBoundsCheck mHorizontalBoundCheck;
        private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedRight(view) + layoutParams.rightMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedLeft(view) - layoutParams.leftMargin;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingLeft();
            }
        };
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        SmoothScroller mSmoothScroller;
        ViewBoundsCheck mVerticalBoundCheck;
        private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedBottom(view) + layoutParams.bottomMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedTop(view) - layoutParams.topMargin;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingTop();
            }
        };
        private int mWidth;
        private int mWidthMode;

        public LayoutManager() {
            this.mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
            this.mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void addViewInt(View view, int n, boolean bl) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (bl || viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (viewHolder.wasReturnedFromScrap() || viewHolder.isScrap()) {
                if (viewHolder.isScrap()) {
                    viewHolder.unScrap();
                } else {
                    viewHolder.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, n, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int n2 = this.mChildHelper.indexOfChild(view);
                int n3 = n;
                if (n == -1) {
                    n3 = this.mChildHelper.getChildCount();
                }
                if (n2 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(view) + this.mRecyclerView.exceptionLabel());
                }
                if (n2 != n3) {
                    this.mRecyclerView.mLayout.moveView(n2, n3);
                }
            } else {
                this.mChildHelper.addView(view, n, false);
                layoutParams.mInsetsDirty = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(view);
                }
            }
            if (layoutParams.mPendingInvalidate) {
                viewHolder.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public static int chooseSize(int n, int n2, int n3) {
            int n4;
            int n5 = View.MeasureSpec.getMode((int)n);
            n = n4 = View.MeasureSpec.getSize((int)n);
            switch (n5) {
                default: {
                    n = Math.max(n2, n3);
                }
                case 1073741824: {
                    return n;
                }
                case Integer.MIN_VALUE: 
            }
            return Math.min(n4, Math.max(n2, n3));
        }

        private void detachViewInternal(int n, View view) {
            this.mChildHelper.detachViewFromParent(n);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int getChildMeasureSpec(int n, int n2, int n3, int n4, boolean bl) {
            int n5 = Math.max(0, n - n3);
            n3 = 0;
            n = 0;
            if (bl) {
                if (n4 >= 0) {
                    n3 = n4;
                    n = 1073741824;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                if (n4 != -1) {
                    if (n4 != -2) return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                    n3 = 0;
                    n = 0;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                switch (n2) {
                    default: {
                        return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                    }
                    case Integer.MIN_VALUE: 
                    case 1073741824: {
                        n3 = n5;
                        n = n2;
                        return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                    }
                    case 0: 
                }
                n3 = 0;
                n = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            if (n4 >= 0) {
                n3 = n4;
                n = 1073741824;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            if (n4 == -1) {
                n3 = n5;
                n = n2;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            if (n4 != -2) return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            n3 = n5;
            if (n2 != Integer.MIN_VALUE && n2 != 1073741824) {
                n = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            n = Integer.MIN_VALUE;
            return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
        }

        /*
         * Enabled aggressive block sorting
         */
        private int[] getChildRectangleOnScreenScrollAmount(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingTop();
            int n3 = this.getWidth() - this.getPaddingRight();
            int n4 = this.getHeight();
            int n5 = this.getPaddingBottom();
            int n6 = view.getLeft() + rect.left - view.getScrollX();
            int n7 = view.getTop() + rect.top - view.getScrollY();
            int n8 = n6 + rect.width();
            int n9 = rect.height();
            int n10 = Math.min(0, n6 - n);
            int n11 = Math.min(0, n7 - n2);
            int n12 = Math.max(0, n8 - n3);
            n4 = Math.max(0, n7 + n9 - (n4 - n5));
            if (this.getLayoutDirection() == 1) {
                n10 = n12 != 0 ? n12 : Math.max(n10, n8 - n3);
            } else if (n10 == 0) {
                n10 = Math.min(n6 - n, n12);
            }
            if (n11 == 0) {
                n11 = Math.min(n7 - n2, n4);
            }
            return new int[]{n10, n11};
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int n, int n2) {
            Properties properties = new Properties();
            context = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, n2);
            properties.orientation = context.getInt(R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = context.getInt(R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = context.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = context.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            context.recycle();
            return properties;
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int n, int n2) {
            block3: {
                block2: {
                    if ((recyclerView = recyclerView.getFocusedChild()) == null) break block2;
                    int n3 = this.getPaddingLeft();
                    int n4 = this.getPaddingTop();
                    int n5 = this.getWidth();
                    int n6 = this.getPaddingRight();
                    int n7 = this.getHeight();
                    int n8 = this.getPaddingBottom();
                    Rect rect = this.mRecyclerView.mTempRect;
                    this.getDecoratedBoundsWithMargins((View)recyclerView, rect);
                    if (rect.left - n < n5 - n6 && rect.right - n > n3 && rect.top - n2 < n7 - n8 && rect.bottom - n2 > n4) break block3;
                }
                return false;
            }
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static boolean isMeasurementUpToDate(int n, int n2, int n3) {
            boolean bl = true;
            int n4 = View.MeasureSpec.getMode((int)n2);
            n2 = View.MeasureSpec.getSize((int)n2);
            if (n3 > 0 && n != n3) {
                return false;
            }
            boolean bl2 = bl;
            switch (n4) {
                case 0: {
                    return bl2;
                }
                default: {
                    return false;
                }
                case Integer.MIN_VALUE: {
                    bl2 = bl;
                    if (n2 >= n) return bl2;
                    return false;
                }
                case 1073741824: 
            }
            bl2 = bl;
            if (n2 == n) return bl2;
            return false;
        }

        private void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int n, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) {
                return;
            }
            if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                this.removeViewAt(n);
                recycler.recycleViewHolderInternal(viewHolder);
                return;
            }
            this.detachViewAt(n);
            recycler.scrapView(view);
            this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
        }

        public void addDisappearingView(View view) {
            this.addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int n) {
            this.addViewInt(view, n, true);
        }

        public void addView(View view) {
            this.addView(view, -1);
        }

        public void addView(View view, int n) {
            this.addViewInt(view, n, false);
        }

        public void assertNotInLayoutOrScroll(String string2) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(string2);
            }
        }

        public void attachView(View view, int n) {
            this.attachView(view, n, (LayoutParams)view.getLayoutParams());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void attachView(View view, int n, LayoutParams layoutParams) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            this.mChildHelper.attachViewToParent(view, n, (ViewGroup.LayoutParams)layoutParams, viewHolder.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            if (this.mRecyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int n, int n2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int n, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                this.scrapOrRecycleView(recycler, i, this.getChildAt(i));
            }
        }

        public void detachViewAt(int n) {
            this.detachViewInternal(n, this.getChildAt(n));
        }

        void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            this.onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            this.onDetachedFromWindow(recyclerView, recycler);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public View findContainingItemView(View view) {
            if (this.mRecyclerView == null) {
                return null;
            }
            View view2 = this.mRecyclerView.findContainingItemView(view);
            if (view2 == null) {
                return null;
            }
            view = view2;
            if (!this.mChildHelper.isHidden(view2)) return view;
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public View findViewByPosition(int n) {
            int n2 = this.getChildCount();
            int n3 = 0;
            while (n3 < n2) {
                View view = this.getChildAt(n3);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (!(viewHolder == null || viewHolder.getLayoutPosition() != n || viewHolder.shouldIgnore() || !this.mRecyclerView.mState.isPreLayout() && viewHolder.isRemoved())) {
                    return view;
                }
                ++n3;
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
        }

        public View getChildAt(int n) {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildAt(n);
            }
            return null;
        }

        public int getChildCount() {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildCount();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !this.canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + this.getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - this.getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + this.getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - this.getTopDecorationHeight(view);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public View getFocusedChild() {
            if (this.mRecyclerView == null) {
                return null;
            }
            View view = this.mRecyclerView.getFocusedChild();
            if (view == null) return null;
            View view2 = view;
            if (!this.mChildHelper.isHidden(view)) return view2;
            return null;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getItemCount() {
            if (this.mRecyclerView == null) return 0;
            Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) return 0;
            return adapter.getItemCount();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.left;
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
        }

        public int getPaddingBottom() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingLeft() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(View view) {
            return ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.right;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getRowCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !this.canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.top;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void getTransformedBoundingBox(View view, boolean bl, Rect rect) {
            Rect rect2;
            if (bl) {
                rect2 = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.mRecyclerView != null && (rect2 = view.getMatrix()) != null && !rect2.isIdentity()) {
                RectF rectF = this.mRecyclerView.mTempRectF;
                rectF.set(rect);
                rect2.mapRect(rectF);
                rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewGroup.LayoutParams layoutParams = this.getChildAt(i).getLayoutParams();
                if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
                return true;
            }
            return false;
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isSmoothScrolling() {
            return this.mSmoothScroller != null && this.mSmoothScroller.isRunning();
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean isViewPartiallyVisible(View view, boolean bl, boolean bl2) {
            boolean bl3 = true;
            bl2 = this.mHorizontalBoundCheck.isViewWithinBoundFlags(view, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(view, 24579);
            if (bl) {
                return bl2;
            }
            if (bl2) return false;
            return bl3;
        }

        public void layoutDecoratedWithMargins(View view, int n, int n2, int n3, int n4) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(rect.left + n + layoutParams.leftMargin, rect.top + n2 + layoutParams.topMargin, n3 - rect.right - layoutParams.rightMargin, n4 - rect.bottom - layoutParams.bottomMargin);
        }

        public void measureChildWithMargins(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n, n2);
            }
        }

        public void moveView(int n, int n2) {
            View view = this.getChildAt(n);
            if (view == null) {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + n + this.mRecyclerView.toString());
            }
            this.detachViewAt(n);
            this.attachView(view, n2);
        }

        public void offsetChildrenHorizontal(int n) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(n);
            }
        }

        public void offsetChildrenVertical(int n) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(n);
            }
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n, int n2) {
            return false;
        }

        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.onDetachedFromWindow(recyclerView);
        }

        public View onFocusSearchFailed(View view, int n, Recycler recycler, State state) {
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent accessibilityEvent) {
            block7: {
                block6: {
                    boolean bl = true;
                    if (this.mRecyclerView == null) return;
                    if (accessibilityEvent == null) break block6;
                    boolean bl2 = bl;
                    if (!this.mRecyclerView.canScrollVertically(1)) {
                        bl2 = bl;
                        if (!this.mRecyclerView.canScrollVertically(-1)) {
                            bl2 = bl;
                            if (!this.mRecyclerView.canScrollHorizontally(-1)) {
                                bl2 = this.mRecyclerView.canScrollHorizontally(1) ? bl : false;
                            }
                        }
                    }
                    accessibilityEvent.setScrollable(bl2);
                    if (this.mRecyclerView.mAdapter != null) break block7;
                }
                return;
            }
            accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n = this.canScrollVertically() ? this.getPosition(view) : 0;
            int n2 = this.canScrollHorizontally() ? this.getPosition(view) : 0;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, 1, n2, 1, false, false));
        }

        void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView)) {
                this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
            }
        }

        public View onInterceptFocusSearch(View view, int n) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
            this.onItemsUpdated(recyclerView, n, n2);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e((String)"RecyclerView", (String)"You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(Recycler recycler, State state, int n, int n2) {
            this.mRecyclerView.defaultOnMeasure(n, n2);
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return this.onRequestChildFocus(recyclerView, view, view2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            return this.isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int n) {
        }

        boolean performAccessibilityAction(int n, Bundle bundle) {
            return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n, bundle);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean performAccessibilityAction(Recycler recycler, State state, int n, Bundle bundle) {
            int n2;
            block10: {
                block9: {
                    if (this.mRecyclerView == null) break block9;
                    int n3 = 0;
                    int n4 = 0;
                    int n5 = 0;
                    n2 = 0;
                    switch (n) {
                        default: {
                            n = n5;
                            break;
                        }
                        case 8192: {
                            n5 = n3;
                            if (this.mRecyclerView.canScrollVertically(-1)) {
                                n5 = -(this.getHeight() - this.getPaddingTop() - this.getPaddingBottom());
                            }
                            n = n5;
                            if (!this.mRecyclerView.canScrollHorizontally(-1)) break;
                            n2 = -(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
                            n = n5;
                            break;
                        }
                        case 4096: {
                            n5 = n4;
                            if (this.mRecyclerView.canScrollVertically(1)) {
                                n5 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                            }
                            n = n5;
                            if (!this.mRecyclerView.canScrollHorizontally(1)) break;
                            n2 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                            n = n5;
                        }
                    }
                    if (n != 0 || n2 != 0) break block10;
                }
                return false;
            }
            this.mRecyclerView.scrollBy(n2, n);
            return true;
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int n, Bundle bundle) {
            return false;
        }

        boolean performAccessibilityActionForItem(View view, int n, Bundle bundle) {
            return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n, bundle);
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                if (RecyclerView.getChildViewHolderInt(this.getChildAt(i)).shouldIgnore()) continue;
                this.removeAndRecycleViewAt(i, recycler);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void removeAndRecycleScrapInt(Recycler recycler) {
            int n = recycler.getScrapCount();
            for (int i = n - 1; i >= 0; --i) {
                View view = recycler.getScrapViewAt(i);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder.shouldIgnore()) continue;
                viewHolder.setIsRecyclable(false);
                if (viewHolder.isTmpDetached()) {
                    this.mRecyclerView.removeDetachedView(view, false);
                }
                if (this.mRecyclerView.mItemAnimator != null) {
                    this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
                }
                viewHolder.setIsRecyclable(true);
                recycler.quickRecycleScrapView(view);
            }
            recycler.clearScrap();
            if (n > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            this.removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int n, Recycler recycler) {
            View view = this.getChildAt(n);
            this.removeViewAt(n);
            recycler.recycleView(view);
        }

        public boolean removeCallbacks(Runnable runnable) {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeView(View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int n) {
            if (this.getChildAt(n) != null) {
                this.mChildHelper.removeViewAt(n);
            }
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            return this.requestChildRectangleOnScreen(recyclerView, view, rect, bl, false);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View arrn, Rect rect, boolean bl, boolean bl2) {
            boolean bl3 = false;
            arrn = this.getChildRectangleOnScreenScrollAmount(recyclerView, (View)arrn, rect, bl);
            int n = arrn[0];
            int n2 = arrn[1];
            if (bl2) {
                bl2 = bl3;
                if (!this.isFocusedChildVisibleAfterScrolling(recyclerView, n, n2)) return bl2;
            }
            if (n == 0) {
                bl2 = bl3;
                if (n2 == 0) return bl2;
            }
            if (bl) {
                recyclerView.scrollBy(n, n2);
                do {
                    return true;
                    break;
                } while (true);
            }
            recyclerView.smoothScrollBy(n, n2);
            return true;
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int n) {
        }

        public int scrollVerticallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void setAutoMeasureEnabled(boolean bl) {
            this.mAutoMeasure = bl;
        }

        void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)1073741824));
        }

        public final void setItemPrefetchEnabled(boolean bl) {
            if (bl != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = bl;
                this.mPrefetchMaxCountObserved = 0;
                if (this.mRecyclerView != null) {
                    this.mRecyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        void setMeasureSpecs(int n, int n2) {
            this.mWidth = View.MeasureSpec.getSize((int)n);
            this.mWidthMode = View.MeasureSpec.getMode((int)n);
            if (this.mWidthMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize((int)n2);
            this.mHeightMode = View.MeasureSpec.getMode((int)n2);
            if (this.mHeightMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        public void setMeasuredDimension(int n, int n2) {
            this.mRecyclerView.setMeasuredDimension(n, n2);
        }

        public void setMeasuredDimension(Rect rect, int n, int n2) {
            int n3 = rect.width();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = rect.height();
            int n7 = this.getPaddingTop();
            int n8 = this.getPaddingBottom();
            this.setMeasuredDimension(LayoutManager.chooseSize(n, n3 + n4 + n5, this.getMinimumWidth()), LayoutManager.chooseSize(n2, n6 + n7 + n8, this.getMinimumHeight()));
        }

        void setMeasuredDimensionFromChildren(int n, int n2) {
            int n3 = this.getChildCount();
            if (n3 == 0) {
                this.mRecyclerView.defaultOnMeasure(n, n2);
                return;
            }
            int n4 = Integer.MAX_VALUE;
            int n5 = Integer.MAX_VALUE;
            int n6 = Integer.MIN_VALUE;
            int n7 = Integer.MIN_VALUE;
            for (int i = 0; i < n3; ++i) {
                View view = this.getChildAt(i);
                Rect rect = this.mRecyclerView.mTempRect;
                this.getDecoratedBoundsWithMargins(view, rect);
                int n8 = n4;
                if (rect.left < n4) {
                    n8 = rect.left;
                }
                n4 = n6;
                if (rect.right > n6) {
                    n4 = rect.right;
                }
                int n9 = n5;
                if (rect.top < n5) {
                    n9 = rect.top;
                }
                n5 = n7;
                if (rect.bottom > n7) {
                    n5 = rect.bottom;
                }
                n6 = n4;
                n7 = n5;
                n4 = n8;
                n5 = n9;
            }
            this.mRecyclerView.mTempRect.set(n4, n5, n6, n7);
            this.setMeasuredDimension(this.mRecyclerView.mTempRect, n, n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        boolean shouldMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !LayoutManager.isMeasurementUpToDate(view.getWidth(), n, layoutParams.width) || !LayoutManager.isMeasurementUpToDate(view.getHeight(), n2, layoutParams.height);
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean shouldReMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            return !this.mMeasurementCacheEnabled || !LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n, layoutParams.width) || !LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n2, layoutParams.height);
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int n) {
            Log.e((String)"RecyclerView", (String)"You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller != null && smoothScroller != this.mSmoothScroller && this.mSmoothScroller.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public static interface LayoutPrefetchRegistry {
            public void addPosition(int var1, int var2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }
    }

    public static interface OnChildAttachStateChangeListener {
        public void onChildViewAttachedToWindow(View var1);

        public void onChildViewDetachedFromWindow(View var1);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int var1, int var2);
    }

    public static interface OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

        public void onRequestDisallowInterceptTouchEvent(boolean var1);

        public void onTouchEvent(RecyclerView var1, MotionEvent var2);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        }

        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        }
    }

    public static class RecycledViewPool {
        private int mAttachCount = 0;
        SparseArray<ScrapData> mScrap = new SparseArray();

        private ScrapData getScrapDataForType(int n) {
            ScrapData scrapData;
            ScrapData scrapData2 = scrapData = (ScrapData)this.mScrap.get(n);
            if (scrapData == null) {
                scrapData2 = new ScrapData();
                this.mScrap.put(n, (Object)scrapData2);
            }
            return scrapData2;
        }

        void attach(Adapter adapter) {
            ++this.mAttachCount;
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); ++i) {
                ((ScrapData)this.mScrap.valueAt((int)i)).mScrapHeap.clear();
            }
        }

        void detach() {
            --this.mAttachCount;
        }

        void factorInBindTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mBindRunningAverageNs = this.runningAverage(scrapData.mBindRunningAverageNs, l);
        }

        void factorInCreateTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mCreateRunningAverageNs = this.runningAverage(scrapData.mCreateRunningAverageNs, l);
        }

        public ViewHolder getRecycledView(int n) {
            Object object = (ScrapData)this.mScrap.get(n);
            if (object != null && !((ScrapData)object).mScrapHeap.isEmpty()) {
                object = ((ScrapData)object).mScrapHeap;
                return (ViewHolder)((ArrayList)object).remove(((ArrayList)object).size() - 1);
            }
            return null;
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            if (adapter != null) {
                this.detach();
            }
            if (!bl && this.mAttachCount == 0) {
                this.clear();
            }
            if (adapter2 != null) {
                this.attach(adapter2);
            }
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int n = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = this.getScrapDataForType((int)n).mScrapHeap;
            if (((ScrapData)this.mScrap.get((int)n)).mMaxScrap <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        long runningAverage(long l, long l2) {
            if (l == 0L) {
                return l2;
            }
            return l / 4L * 3L + l2 / 4L;
        }

        boolean willBindInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mBindRunningAverageNs;
            return l3 == 0L || l + l3 < l2;
        }

        boolean willCreateInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mCreateRunningAverageNs;
            return l3 == 0L || l + l3 < l2;
        }

        static class ScrapData {
            long mBindRunningAverageNs = 0L;
            long mCreateRunningAverageNs = 0L;
            int mMaxScrap = 5;
            ArrayList<ViewHolder> mScrapHeap = new ArrayList();

            ScrapData() {
            }
        }

    }

    public final class Recycler {
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax = 2;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax = 2;

        private void attachAccessibilityDelegateOnBind(ViewHolder viewHolder) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                View view = viewHolder.itemView;
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view)) {
                    viewHolder.addFlags(16384);
                    ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl) {
            int n;
            for (n = viewGroup.getChildCount() - 1; n >= 0; --n) {
                View view = viewGroup.getChildAt(n);
                if (!(view instanceof ViewGroup)) continue;
                this.invalidateDisplayListInt((ViewGroup)view, true);
            }
            if (!bl) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            n = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(n);
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder viewHolder, int n, int n2, long l) {
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            int n3 = viewHolder.getItemViewType();
            long l2 = RecyclerView.this.getNanoTime();
            if (l != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(n3, l2, l)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n);
            l = RecyclerView.this.getNanoTime();
            this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), l - l2);
            this.attachAccessibilityDelegateOnBind(viewHolder);
            if (RecyclerView.this.mState.isPreLayout()) {
                viewHolder.mPreLayoutPosition = n2;
            }
            return true;
        }

        void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean bl) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            if (viewHolder.hasAnyOfTheFlags(16384)) {
                viewHolder.setFlags(0, 16384);
                ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            }
            if (bl) {
                this.dispatchViewRecycled(viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            this.getRecycledViewPool().putRecycledView(viewHolder);
        }

        public void clear() {
            this.mAttachedScrap.clear();
            this.recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int n;
            int n2 = this.mCachedViews.size();
            for (n = 0; n < n2; ++n) {
                this.mCachedViews.get(n).clearOldPosition();
            }
            n2 = this.mAttachedScrap.size();
            for (n = 0; n < n2; ++n) {
                this.mAttachedScrap.get(n).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                n2 = this.mChangedScrap.size();
                for (n = 0; n < n2; ++n) {
                    this.mChangedScrap.get(n).clearOldPosition();
                }
            }
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        public int convertPreLayoutPositionToPostLayout(int n) {
            if (n < 0 || n >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("invalid position " + n + ". State " + "item count is " + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
            }
            if (!RecyclerView.this.mState.isPreLayout()) {
                return n;
            }
            return RecyclerView.this.mAdapterHelper.findPositionOffset(n);
        }

        void dispatchViewRecycled(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            }
        }

        ViewHolder getChangedScrapViewForPosition(int n) {
            ViewHolder viewHolder;
            int n2;
            if (this.mChangedScrap == null || (n2 = this.mChangedScrap.size()) == 0) {
                return null;
            }
            for (int i = 0; i < n2; ++i) {
                viewHolder = this.mChangedScrap.get(i);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (RecyclerView.this.mAdapter.hasStableIds() && (n = RecyclerView.this.mAdapterHelper.findPositionOffset(n)) > 0 && n < RecyclerView.this.mAdapter.getItemCount()) {
                long l = RecyclerView.this.mAdapter.getItemId(n);
                for (n = 0; n < n2; ++n) {
                    viewHolder = this.mChangedScrap.get(n);
                    if (viewHolder.wasReturnedFromScrap() || viewHolder.getItemId() != l) continue;
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            return null;
        }

        RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        ViewHolder getScrapOrCachedViewForId(long l, int n, boolean bl) {
            int n2;
            ViewHolder viewHolder;
            ViewHolder viewHolder2;
            for (n2 = this.mAttachedScrap.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.getItemId() != l || viewHolder.wasReturnedFromScrap()) continue;
                if (n == viewHolder.getItemViewType()) {
                    viewHolder.addFlags(32);
                    viewHolder2 = viewHolder;
                    if (!viewHolder.isRemoved()) return viewHolder2;
                    viewHolder2 = viewHolder;
                    if (RecyclerView.this.mState.isPreLayout()) return viewHolder2;
                    viewHolder.setFlags(2, 14);
                    return viewHolder;
                }
                if (bl) continue;
                this.mAttachedScrap.remove(n2);
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                this.quickRecycleScrapView(viewHolder.itemView);
            }
            n2 = this.mCachedViews.size() - 1;
            while (n2 >= 0) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.getItemId() == l) {
                    if (n == viewHolder.getItemViewType()) {
                        viewHolder2 = viewHolder;
                        if (bl) return viewHolder2;
                        this.mCachedViews.remove(n2);
                        return viewHolder;
                    }
                    if (!bl) {
                        this.recycleCachedViewAt(n2);
                        return null;
                    }
                }
                --n2;
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int n, boolean bl) {
            int n2;
            ViewHolder viewHolder;
            int n3 = this.mAttachedScrap.size();
            for (n2 = 0; n2 < n3; ++n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (!bl && (viewHolder = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n)) != null) {
                ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt((View)viewHolder);
                RecyclerView.this.mChildHelper.unhide((View)viewHolder);
                n = RecyclerView.this.mChildHelper.indexOfChild((View)viewHolder);
                if (n == -1) {
                    throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + viewHolder2 + RecyclerView.this.exceptionLabel());
                }
                RecyclerView.this.mChildHelper.detachViewFromParent(n);
                this.scrapView((View)viewHolder);
                viewHolder2.addFlags(8224);
                return viewHolder2;
            }
            n3 = this.mCachedViews.size();
            n2 = 0;
            while (n2 < n3) {
                ViewHolder viewHolder3 = this.mCachedViews.get(n2);
                if (!viewHolder3.isInvalid() && viewHolder3.getLayoutPosition() == n) {
                    viewHolder = viewHolder3;
                    if (bl) return viewHolder;
                    this.mCachedViews.remove(n2);
                    return viewHolder3;
                }
                ++n2;
            }
            return null;
        }

        View getScrapViewAt(int n) {
            return this.mAttachedScrap.get((int)n).itemView;
        }

        public View getViewForPosition(int n) {
            return this.getViewForPosition(n, false);
        }

        View getViewForPosition(int n, boolean bl) {
            return this.tryGetViewHolderForPositionByDeadline((int)n, (boolean)bl, (long)Long.MAX_VALUE).itemView;
        }

        void markItemDecorInsetsDirty() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                LayoutParams layoutParams = (LayoutParams)this.mCachedViews.get((int)i).itemView.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.mInsetsDirty = true;
            }
        }

        void markKnownViewsInvalid() {
            if (RecyclerView.this.mAdapter != null && RecyclerView.this.mAdapter.hasStableIds()) {
                int n = this.mCachedViews.size();
                for (int i = 0; i < n; ++i) {
                    ViewHolder viewHolder = this.mCachedViews.get(i);
                    if (viewHolder == null) continue;
                    viewHolder.addFlags(6);
                    viewHolder.addChangePayload(null);
                }
            } else {
                this.recycleAndClearCachedViews();
            }
        }

        void offsetPositionRecordsForInsert(int n, int n2) {
            int n3 = this.mCachedViews.size();
            for (int i = 0; i < n3; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || viewHolder.mPosition < n) continue;
                viewHolder.offsetPosition(n2, true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void offsetPositionRecordsForMove(int n, int n2) {
            int n3;
            int n4;
            int n5;
            if (n < n2) {
                n5 = n;
                n3 = n2;
                n4 = -1;
            } else {
                n5 = n2;
                n3 = n;
                n4 = 1;
            }
            int n6 = this.mCachedViews.size();
            int n7 = 0;
            while (n7 < n6) {
                ViewHolder viewHolder = this.mCachedViews.get(n7);
                if (viewHolder != null && viewHolder.mPosition >= n5 && viewHolder.mPosition <= n3) {
                    if (viewHolder.mPosition == n) {
                        viewHolder.offsetPosition(n2 - n, false);
                    } else {
                        viewHolder.offsetPosition(n4, false);
                    }
                }
                ++n7;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         */
        void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
            int n3 = this.mCachedViews.size() - 1;
            while (n3 >= 0) {
                ViewHolder viewHolder = this.mCachedViews.get(n3);
                if (viewHolder != null) {
                    if (viewHolder.mPosition >= n + n2) {
                        viewHolder.offsetPosition(-n2, bl);
                    } else if (viewHolder.mPosition >= n) {
                        viewHolder.addFlags(8);
                        this.recycleCachedViewAt(n3);
                    }
                }
                --n3;
            }
            return;
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            this.clear();
            this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl);
        }

        void quickRecycleScrapView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).mScrapContainer = null;
            ((ViewHolder)object).mInChangeScrap = false;
            ((ViewHolder)object).clearReturnedFromScrapFlag();
            this.recycleViewHolderInternal((ViewHolder)object);
        }

        void recycleAndClearCachedViews() {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                this.recycleCachedViewAt(i);
            }
            this.mCachedViews.clear();
            if (ALLOW_THREAD_GAP_WORK) {
                RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        void recycleCachedViewAt(int n) {
            this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n), true);
            this.mCachedViews.remove(n);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void recycleView(View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolder.isScrap()) {
                viewHolder.unScrap();
            } else if (viewHolder.wasReturnedFromScrap()) {
                viewHolder.clearReturnedFromScrapFlag();
            }
            this.recycleViewHolderInternal(viewHolder);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        void recycleViewHolderInternal(ViewHolder var1_1) {
            block10: {
                var7_2 = false;
                if (var1_1.isScrap() || var1_1.itemView.getParent() != null) {
                    var8_3 = new StringBuilder().append("Scrapped or attached views may not be recycled. isScrap:").append(var1_1.isScrap()).append(" isAttached:");
                    if (var1_1.itemView.getParent() == null) throw new IllegalArgumentException(var8_3.append(var7_2).append(RecyclerView.this.exceptionLabel()).toString());
                    var7_2 = true;
                    throw new IllegalArgumentException(var8_3.append(var7_2).append(RecyclerView.this.exceptionLabel()).toString());
                }
                if (var1_1.isTmpDetached()) {
                    throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + var1_1 + RecyclerView.this.exceptionLabel());
                }
                if (var1_1.shouldIgnore()) {
                    throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.exceptionLabel());
                }
                var7_2 = ViewHolder.access$900(var1_1);
                var2_4 = RecyclerView.this.mAdapter != null && var7_2 != false && RecyclerView.this.mAdapter.onFailedToRecycleView(var1_1) != false ? 1 : 0;
                var3_5 = 0;
                var6_6 = 0;
                var5_7 = 0;
                if (var2_4 != 0) break block10;
                var4_8 = var5_7;
                if (!var1_1.isRecyclable()) ** GOTO lbl48
            }
            var2_4 = var6_6;
            if (this.mViewCacheMax <= 0) ** GOTO lbl42
            var2_4 = var6_6;
            if (var1_1.hasAnyOfTheFlags(526)) ** GOTO lbl42
            var2_4 = var3_5 = this.mCachedViews.size();
            if (var3_5 >= this.mViewCacheMax) {
                var2_4 = var3_5;
                if (var3_5 > 0) {
                    this.recycleCachedViewAt(0);
                    var2_4 = var3_5 - 1;
                }
            }
            var4_8 = var3_5 = var2_4;
            if (!RecyclerView.access$800()) ** GOTO lbl40
            var4_8 = var3_5;
            if (var2_4 <= 0) ** GOTO lbl40
            var4_8 = var3_5;
            if (RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(var1_1.mPosition)) ** GOTO lbl40
            --var2_4;
            do {
                block11: {
                    if (var2_4 >= 0 && RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(var3_5 = this.mCachedViews.get((int)var2_4).mPosition)) break block11;
                    var4_8 = var2_4 + 1;
lbl40:
                    // 4 sources
                    this.mCachedViews.add(var4_8, var1_1);
                    var2_4 = 1;
lbl42:
                    // 3 sources
                    var3_5 = var2_4;
                    var4_8 = var5_7;
                    if (var2_4 == 0) {
                        this.addViewHolderToRecycledViewPool(var1_1, true);
                        var4_8 = 1;
                        var3_5 = var2_4;
                    }
lbl48:
                    // 4 sources
                    RecyclerView.this.mViewInfoStore.removeViewHolder(var1_1);
                    if (var3_5 != 0) return;
                    if (var4_8 != 0) return;
                    if (var7_2 == false) return;
                    var1_1.mOwnerRecyclerView = null;
                    return;
                }
                --var2_4;
            } while (true);
        }

        void scrapView(View object) {
            if (((ViewHolder)(object = RecyclerView.getChildViewHolderInt((View)object))).hasAnyOfTheFlags(12) || !((ViewHolder)object).isUpdated() || RecyclerView.this.canReuseUpdatedViewHolder((ViewHolder)object)) {
                if (((ViewHolder)object).isInvalid() && !((ViewHolder)object).isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
                    throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.exceptionLabel());
                }
                ((ViewHolder)object).setScrapContainer(this, false);
                this.mAttachedScrap.add((ViewHolder)object);
                return;
            }
            if (this.mChangedScrap == null) {
                this.mChangedScrap = new ArrayList();
            }
            ((ViewHolder)object).setScrapContainer(this, true);
            this.mChangedScrap.add((ViewHolder)object);
        }

        void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
            }
        }

        void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        public void setViewCacheSize(int n) {
            this.mRequestedCacheMax = n;
            this.updateViewCacheSize();
        }

        /*
         * Enabled aggressive block sorting
         */
        ViewHolder tryGetViewHolderForPositionByDeadline(int n, boolean bl, long l) {
            if (n < 0 || n >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("Invalid item position " + n + "(" + n + "). Item count:" + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
            }
            int n2 = 0;
            Object object = null;
            if (RecyclerView.this.mState.isPreLayout()) {
                object = this.getChangedScrapViewForPosition(n);
                n2 = object != null ? 1 : 0;
            }
            Object object2 = object;
            int n3 = n2;
            if (object == null) {
                object2 = object = this.getScrapOrHiddenOrCachedHolderForPosition(n, bl);
                n3 = n2;
                if (object != null) {
                    if (!this.validateViewHolderForOffsetPosition((ViewHolder)object)) {
                        if (!bl) {
                            ((ViewHolder)object).addFlags(4);
                            if (((ViewHolder)object).isScrap()) {
                                RecyclerView.this.removeDetachedView(((ViewHolder)object).itemView, false);
                                ((ViewHolder)object).unScrap();
                            } else if (((ViewHolder)object).wasReturnedFromScrap()) {
                                ((ViewHolder)object).clearReturnedFromScrapFlag();
                            }
                            this.recycleViewHolderInternal((ViewHolder)object);
                        }
                        object2 = null;
                        n3 = n2;
                    } else {
                        n3 = 1;
                        object2 = object;
                    }
                }
            }
            object = object2;
            n2 = n3;
            if (object2 == null) {
                int n4 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
                if (n4 < 0 || n4 >= RecyclerView.this.mAdapter.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + n + "(offset:" + n4 + ")." + "state:" + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
                }
                int n5 = RecyclerView.this.mAdapter.getItemViewType(n4);
                object = object2;
                n2 = n3;
                if (RecyclerView.this.mAdapter.hasStableIds()) {
                    object = object2 = this.getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(n4), n5, bl);
                    n2 = n3;
                    if (object2 != null) {
                        ((ViewHolder)object2).mPosition = n4;
                        n2 = 1;
                        object = object2;
                    }
                }
                object2 = object;
                if (object == null) {
                    object2 = object;
                    if (this.mViewCacheExtension != null) {
                        View view = this.mViewCacheExtension.getViewForPositionAndType(this, n, n5);
                        object2 = object;
                        if (view != null) {
                            object = RecyclerView.this.getChildViewHolder(view);
                            if (object == null) {
                                throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder" + RecyclerView.this.exceptionLabel());
                            }
                            object2 = object;
                            if (((ViewHolder)object).shouldIgnore()) {
                                throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view." + RecyclerView.this.exceptionLabel());
                            }
                        }
                    }
                }
                object = object2;
                if (object2 == null) {
                    object = object2 = this.getRecycledViewPool().getRecycledView(n5);
                    if (object2 != null) {
                        ((ViewHolder)object2).resetInternal();
                        object = object2;
                        if (FORCE_INVALIDATE_DISPLAY_LIST) {
                            this.invalidateDisplayListInt((ViewHolder)object2);
                            object = object2;
                        }
                    }
                }
                if (object == null) {
                    long l2 = RecyclerView.this.getNanoTime();
                    if (l != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(n5, l2, l)) {
                        return null;
                    }
                    object = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, n5);
                    if (ALLOW_THREAD_GAP_WORK && (object2 = RecyclerView.findNestedRecyclerView(((ViewHolder)object).itemView)) != null) {
                        ((ViewHolder)object).mNestedRecyclerView = new WeakReference<Object>(object2);
                    }
                    long l3 = RecyclerView.this.getNanoTime();
                    this.mRecyclerPool.factorInCreateTime(n5, l3 - l2);
                }
            }
            if (n2 != 0 && !RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object).hasAnyOfTheFlags(8192)) {
                ((ViewHolder)object).setFlags(0, 8192);
                if (RecyclerView.this.mState.mRunSimpleAnimations) {
                    n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                    object2 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (ViewHolder)object, n3 | 0x1000, ((ViewHolder)object).getUnmodifiedPayloads());
                    RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, (ItemAnimator.ItemHolderInfo)object2);
                }
            }
            bl = false;
            if (RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object).isBound()) {
                ((ViewHolder)object).mPreLayoutPosition = n;
            } else if (!((ViewHolder)object).isBound() || ((ViewHolder)object).needsUpdate() || ((ViewHolder)object).isInvalid()) {
                bl = this.tryBindViewHolderByDeadline((ViewHolder)object, RecyclerView.this.mAdapterHelper.findPositionOffset(n), n, l);
            }
            if ((object2 = ((ViewHolder)object).itemView.getLayoutParams()) == null) {
                object2 = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                ((ViewHolder)object).itemView.setLayoutParams((ViewGroup.LayoutParams)object2);
            } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object2)) {
                object2 = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object2);
                ((ViewHolder)object).itemView.setLayoutParams((ViewGroup.LayoutParams)object2);
            } else {
                object2 = (LayoutParams)((Object)object2);
            }
            ((LayoutParams)object2).mViewHolder = object;
            bl = n2 != 0 && bl;
            ((LayoutParams)object2).mPendingInvalidate = bl;
            return object;
        }

        /*
         * Enabled aggressive block sorting
         */
        void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        /*
         * Enabled aggressive block sorting
         */
        void updateViewCacheSize() {
            int n = RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0;
            this.mViewCacheMax = this.mRequestedCacheMax + n;
            for (n = this.mCachedViews.size() - 1; n >= 0 && this.mCachedViews.size() > this.mViewCacheMax; --n) {
                this.recycleCachedViewAt(n);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            boolean bl = true;
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition < 0) throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder + RecyclerView.this.exceptionLabel());
            if (viewHolder.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder + RecyclerView.this.exceptionLabel());
            }
            if (!RecyclerView.this.mState.isPreLayout() && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                return false;
            }
            boolean bl2 = bl;
            if (!RecyclerView.this.mAdapter.hasStableIds()) return bl2;
            bl2 = bl;
            if (viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) return bl2;
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        void viewRangeUpdate(int n, int n2) {
            int n3 = this.mCachedViews.size() - 1;
            while (n3 >= 0) {
                int n4;
                ViewHolder viewHolder = this.mCachedViews.get(n3);
                if (viewHolder != null && (n4 = viewHolder.mPosition) >= n && n4 < n + n2) {
                    viewHolder.addFlags(2);
                    this.recycleCachedViewAt(n3);
                }
                --n3;
            }
            return;
        }
    }

    public static interface RecyclerListener {
        public void onViewRecycled(ViewHolder var1);
    }

    private class RecyclerViewDataObserver
    extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        @Override
        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            RecyclerView.this.mState.mStructureChanged = true;
            RecyclerView.this.setDataSetChangedAfterLayout();
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override
        public void onItemRangeChanged(int n, int n2, Object object) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(n, n2, object)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeInserted(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeMoved(int n, int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(n, n2, n3)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeRemoved(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() {
            if (POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                ViewCompat.postOnAnimation((View)RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
                return;
            }
            RecyclerView.this.mAdapterUpdateDuringMeasure = true;
            RecyclerView.this.requestLayout();
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
        Parcelable mLayoutState;

        /*
         * Enabled aggressive block sorting
         */
        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = LayoutManager.class.getClassLoader();
            }
            this.mLayoutState = parcel.readParcelable(classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        void copyFrom(SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        /*
         * Enabled aggressive block sorting
         */
        private void onAnimation(int n, int n2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
                this.stop();
            }
            this.mPendingInitialRun = false;
            if (this.mTargetView != null) {
                if (this.getChildPosition(this.mTargetView) == this.mTargetPosition) {
                    this.onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    this.stop();
                } else {
                    Log.e((String)"RecyclerView", (String)"Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                this.onSeekTargetStep(n, n2, recyclerView.mState, this.mRecyclingAction);
                boolean bl = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (bl) {
                    if (!this.mRunning) {
                        this.stop();
                        return;
                    }
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public View findViewByPosition(int n) {
            return this.mRecyclerView.mLayout.findViewByPosition(n);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        protected void normalize(PointF pointF) {
            float f = (float)Math.sqrt(pointF.x * pointF.x + pointF.y * pointF.y);
            pointF.x /= f;
            pointF.y /= f;
        }

        protected void onChildAttachedToWindow(View view) {
            if (this.getChildPosition(view) == this.getTargetPosition()) {
                this.mTargetView = view;
            }
        }

        protected abstract void onSeekTargetStep(int var1, int var2, State var3, Action var4);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(View var1, State var2, Action var3);

        public void setTargetPosition(int n) {
            this.mTargetPosition = n;
        }

        void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            this.mRecyclerView.mState.mTargetPosition = this.mTargetPosition;
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = this.findViewByPosition(this.getTargetPosition());
            this.onStart();
            this.mRecyclerView.mViewFlinger.postOnAnimation();
        }

        protected final void stop() {
            if (!this.mRunning) {
                return;
            }
            this.onStop();
            this.mRecyclerView.mState.mTargetPosition = -1;
            this.mTargetView = null;
            this.mTargetPosition = -1;
            this.mPendingInitialRun = false;
            this.mRunning = false;
            this.mLayoutManager.onSmoothScrollerStopped(this);
            this.mLayoutManager = null;
            this.mRecyclerView = null;
        }

        public static class Action {
            private boolean mChanged = false;
            private int mConsecutiveUpdates = 0;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition = -1;

            public Action(int n, int n2) {
                this(n, n2, Integer.MIN_VALUE, null);
            }

            public Action(int n, int n2, int n3, Interpolator interpolator) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator;
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            boolean hasJumpTarget() {
                return this.mJumpToPosition >= 0;
            }

            public void jumpTo(int n) {
                this.mJumpToPosition = n;
            }

            /*
             * Enabled aggressive block sorting
             */
            void runIfNecessary(RecyclerView recyclerView) {
                if (this.mJumpToPosition >= 0) {
                    int n = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(n);
                    this.mChanged = false;
                    return;
                }
                if (!this.mChanged) {
                    this.mConsecutiveUpdates = 0;
                    return;
                }
                this.validate();
                if (this.mInterpolator == null) {
                    if (this.mDuration == Integer.MIN_VALUE) {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                    } else {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                    }
                } else {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                }
                ++this.mConsecutiveUpdates;
                if (this.mConsecutiveUpdates > 10) {
                    Log.e((String)"RecyclerView", (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.mChanged = false;
            }

            public void update(int n, int n2, int n3, Interpolator interpolator) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator;
                this.mChanged = true;
            }
        }

        public static interface ScrollVectorProvider {
            public PointF computeScrollVectorForPosition(int var1);
        }

    }

    public static class State {
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        int mRemainingScrollHorizontal;
        int mRemainingScrollVertical;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        void assertLayoutStep(int n) {
            if ((this.mLayoutStep & n) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(n) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
            }
        }

        public int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return this.mItemCount;
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            return this.mTargetPosition != -1;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        void prepareForNestedPrefetch(Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler var1, int var2, int var3);
    }

    class ViewFlinger
    implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private OverScroller mScroller;

        ViewFlinger() {
            this.mScroller = new OverScroller(RecyclerView.this.getContext(), sQuinticInterpolator);
        }

        /*
         * Enabled aggressive block sorting
         */
        private int computeScrollDuration(int n, int n2, int n3, int n4) {
            int n5;
            int n6 = Math.abs(n);
            boolean bl = n6 > (n5 = Math.abs(n2));
            n3 = (int)Math.sqrt(n3 * n3 + n4 * n4);
            n2 = (int)Math.sqrt(n * n + n2 * n2);
            n = bl ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            n4 = n / 2;
            float f = Math.min(1.0f, 1.0f * (float)n2 / (float)n);
            float f2 = n4;
            float f3 = n4;
            f = this.distanceInfluenceForSnapDuration(f);
            if (n3 > 0) {
                n = Math.round(1000.0f * Math.abs((f2 + f3 * f) / (float)n3)) * 4;
                return Math.min(n, 2000);
            }
            n2 = bl ? n6 : n5;
            n = (int)(((float)n2 / (float)n + 1.0f) * 300.0f);
            return Math.min(n, 2000);
        }

        private void disableRunOnAnimationRequests() {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private float distanceInfluenceForSnapDuration(float f) {
            return (float)Math.sin((f - 0.5f) * 0.47123894f);
        }

        private void enableRunOnAnimationRequests() {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                this.postOnAnimation();
            }
        }

        public void fling(int n, int n2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, n, n2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.postOnAnimation();
        }

        void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            RecyclerView.this.removeCallbacks((Runnable)this);
            ViewCompat.postOnAnimation((View)RecyclerView.this, this);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (RecyclerView.this.mLayout == null) {
                this.stop();
                return;
            }
            this.disableRunOnAnimationRequests();
            RecyclerView.this.consumePendingUpdateOperations();
            OverScroller overScroller = this.mScroller;
            SmoothScroller smoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
            if (overScroller.computeScrollOffset()) {
                int[] arrn = RecyclerView.this.mScrollConsumed;
                int n = overScroller.getCurrX();
                int n2 = overScroller.getCurrY();
                int n3 = n - this.mLastFlingX;
                int n4 = n2 - this.mLastFlingY;
                int n5 = 0;
                int n6 = 0;
                this.mLastFlingX = n;
                this.mLastFlingY = n2;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                int n11 = n3;
                int n12 = n4;
                if (RecyclerView.this.dispatchNestedPreScroll(n3, n4, arrn, null, 1)) {
                    n11 = n3 - arrn[0];
                    n12 = n4 - arrn[1];
                }
                if (RecyclerView.this.mAdapter != null) {
                    RecyclerView.this.eatRequestLayout();
                    RecyclerView.this.onEnterLayoutOrScroll();
                    TraceCompat.beginSection("RV Scroll");
                    RecyclerView.this.fillRemainingScrollValues(RecyclerView.this.mState);
                    if (n11 != 0) {
                        n4 = RecyclerView.this.mLayout.scrollHorizontallyBy(n11, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                        n8 = n11 - n4;
                    } else {
                        n4 = 0;
                    }
                    if (n12 != 0) {
                        n6 = RecyclerView.this.mLayout.scrollVerticallyBy(n12, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                        n10 = n12 - n6;
                    }
                    TraceCompat.endSection();
                    RecyclerView.this.repositionShadowingViews();
                    RecyclerView.this.onExitLayoutOrScroll();
                    RecyclerView.this.resumeRequestLayout(false);
                    n3 = n4;
                    n5 = n6;
                    n7 = n8;
                    n9 = n10;
                    if (smoothScroller != null) {
                        n3 = n4;
                        n5 = n6;
                        n7 = n8;
                        n9 = n10;
                        if (!smoothScroller.isPendingInitialRun()) {
                            n3 = n4;
                            n5 = n6;
                            n7 = n8;
                            n9 = n10;
                            if (smoothScroller.isRunning()) {
                                n3 = RecyclerView.this.mState.getItemCount();
                                if (n3 == 0) {
                                    smoothScroller.stop();
                                    n9 = n10;
                                    n7 = n8;
                                    n5 = n6;
                                    n3 = n4;
                                } else if (smoothScroller.getTargetPosition() >= n3) {
                                    smoothScroller.setTargetPosition(n3 - 1);
                                    smoothScroller.onAnimation(n11 - n8, n12 - n10);
                                    n3 = n4;
                                    n5 = n6;
                                    n7 = n8;
                                    n9 = n10;
                                } else {
                                    smoothScroller.onAnimation(n11 - n8, n12 - n10);
                                    n3 = n4;
                                    n5 = n6;
                                    n7 = n8;
                                    n9 = n10;
                                }
                            }
                        }
                    }
                } else {
                    n3 = 0;
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(n11, n12);
                }
                if (!(RecyclerView.this.dispatchNestedScroll(n3, n5, n7, n9, null, 1) || n7 == 0 && n9 == 0)) {
                    n6 = (int)overScroller.getCurrVelocity();
                    n4 = 0;
                    if (n7 != n) {
                        n4 = n7 < 0 ? -n6 : (n7 > 0 ? n6 : 0);
                    }
                    n8 = 0;
                    if (n9 != n2) {
                        n8 = n9 < 0 ? -n6 : (n9 > 0 ? n6 : 0);
                    }
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        RecyclerView.this.absorbGlows(n4, n8);
                    }
                    if (!(n4 == 0 && n7 != n && overScroller.getFinalX() != 0 || n8 == 0 && n9 != n2 && overScroller.getFinalY() != 0)) {
                        overScroller.abortAnimation();
                    }
                }
                if (n3 != 0 || n5 != 0) {
                    RecyclerView.this.dispatchOnScrolled(n3, n5);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                n4 = n12 != 0 && RecyclerView.this.mLayout.canScrollVertically() && n5 == n12 ? 1 : 0;
                n8 = n11 != 0 && RecyclerView.this.mLayout.canScrollHorizontally() && n3 == n11 ? 1 : 0;
                n4 = n11 == 0 && n12 == 0 || n8 != 0 || n4 != 0 ? 1 : 0;
                if (overScroller.isFinished() || n4 == 0 && !RecyclerView.this.hasNestedScrollingParent(1)) {
                    RecyclerView.this.setScrollState(0);
                    if (ALLOW_THREAD_GAP_WORK) {
                        RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    }
                    RecyclerView.this.stopNestedScroll(1);
                } else {
                    this.postOnAnimation();
                    if (RecyclerView.this.mGapWorker != null) {
                        RecyclerView.this.mGapWorker.postFromTraversal(RecyclerView.this, n11, n12);
                    }
                }
            }
            if (smoothScroller != null) {
                if (smoothScroller.isPendingInitialRun()) {
                    smoothScroller.onAnimation(0, 0);
                }
                if (!this.mReSchedulePostAnimationCallback) {
                    smoothScroller.stop();
                }
            }
            this.enableRunOnAnimationRequests();
        }

        public void smoothScrollBy(int n, int n2) {
            this.smoothScrollBy(n, n2, 0, 0);
        }

        public void smoothScrollBy(int n, int n2, int n3) {
            this.smoothScrollBy(n, n2, n3, sQuinticInterpolator);
        }

        public void smoothScrollBy(int n, int n2, int n3, int n4) {
            this.smoothScrollBy(n, n2, this.computeScrollDuration(n, n2, n3, n4));
        }

        public void smoothScrollBy(int n, int n2, int n3, Interpolator interpolator) {
            if (this.mInterpolator != interpolator) {
                this.mInterpolator = interpolator;
                this.mScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, n, n2, n3);
            if (Build.VERSION.SDK_INT < 23) {
                this.mScroller.computeScrollOffset();
            }
            this.postOnAnimation();
        }

        public void smoothScrollBy(int n, int n2, Interpolator interpolator) {
            int n3 = this.computeScrollDuration(n, n2, 0, 0);
            Interpolator interpolator2 = interpolator;
            if (interpolator == null) {
                interpolator2 = sQuinticInterpolator;
            }
            this.smoothScrollBy(n, n2, n3, interpolator2);
        }

        public void stop() {
            RecyclerView.this.removeCallbacks((Runnable)this);
            this.mScroller.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1L;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        static /* synthetic */ boolean access$900(ViewHolder viewHolder) {
            return viewHolder.doesTransientStatePreventRecycling();
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList<Object>();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        private boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 0x10) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        private void onEnteredHiddenState(RecyclerView recyclerView) {
            this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        private void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private boolean shouldBeKeptAsChild() {
            return (this.mFlags & 0x10) != 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        void addChangePayload(Object object) {
            if (object == null) {
                this.addFlags(1024);
                return;
            } else {
                if ((this.mFlags & 0x400) != 0) return;
                {
                    this.createPayloadsIfNeeded();
                    this.mPayloads.add(object);
                    return;
                }
            }
        }

        void addFlags(int n) {
            this.mFlags |= n;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= 0xFFFFFBFF;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= 0xFFFFFFDF;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= 0xFFFFFEFF;
        }

        void flagRemovedAndOffsetPosition(int n, int n2, boolean bl) {
            this.addFlags(8);
            this.offsetPosition(n2, bl);
            this.mPosition = n;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView == null) {
                return -1;
            }
            return this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 0x400) == 0) {
                if (this.mPayloads == null || this.mPayloads.size() == 0) {
                    return FULLUPDATE_PAYLOADS;
                }
                return this.mUnmodifiedPayloads;
            }
            return FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int n) {
            return (this.mFlags & n) != 0;
        }

        boolean isAdapterPositionUnknown() {
            return (this.mFlags & 0x200) != 0 || this.isInvalid();
        }

        boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 0x10) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        boolean isScrap() {
            return this.mScrapContainer != null;
        }

        boolean isTmpDetached() {
            return (this.mFlags & 0x100) != 0;
        }

        boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        void offsetPosition(int n, boolean bl) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (bl) {
                this.mPreLayoutPosition += n;
            }
            this.mPosition += n;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            this.clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int n, int n2) {
            this.mFlags = this.mFlags & ~n2 | n & n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void setIsRecyclable(boolean bl) {
            int n = bl ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            this.mIsRecyclableCount = n;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                Log.e((String)"View", (String)("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this));
                return;
            } else {
                if (!bl && this.mIsRecyclableCount == 1) {
                    this.mFlags |= 0x10;
                    return;
                }
                if (!bl || this.mIsRecyclableCount != 0) return;
                {
                    this.mFlags &= 0xFFFFFFEF;
                    return;
                }
            }
        }

        void setScrapContainer(Recycler recycler, boolean bl) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = bl;
        }

        boolean shouldIgnore() {
            return (this.mFlags & 0x80) != 0;
        }

        void stopIgnoring() {
            this.mFlags &= 0xFFFFFF7F;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ViewHolder{" + Integer.toHexString(this.hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (this.isScrap()) {
                StringBuilder stringBuilder2 = stringBuilder.append(" scrap ");
                String string2 = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder2.append(string2);
            }
            if (this.isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!this.isBound()) {
                stringBuilder.append(" unbound");
            }
            if (this.needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (this.isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (this.shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (this.isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.isRecyclable()) {
                stringBuilder.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (this.isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            return (this.mFlags & 0x20) != 0;
        }
    }

}

