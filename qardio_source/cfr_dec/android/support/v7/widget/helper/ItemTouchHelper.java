/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.support.v7.widget.helper.ItemTouchUIUtilImpl;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper
extends RecyclerView.ItemDecoration
implements RecyclerView.OnChildAttachStateChangeListener {
    int mActionState = 0;
    int mActivePointerId = -1;
    Callback mCallback;
    private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    GestureDetectorCompat mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    private final RecyclerView.OnItemTouchListener mOnItemTouchListener;
    View mOverdrawChild = null;
    int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList<View>();
    List<RecoverAnimation> mRecoverAnimations;
    RecyclerView mRecyclerView;
    final Runnable mScrollRunnable;
    RecyclerView.ViewHolder mSelected = null;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<RecyclerView.ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    VelocityTracker mVelocityTracker;

    public ItemTouchHelper(Callback callback) {
        this.mRecoverAnimations = new ArrayList<RecoverAnimation>();
        this.mScrollRunnable = new Runnable(){

            @Override
            public void run() {
                if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
                    if (ItemTouchHelper.this.mSelected != null) {
                        ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
                    }
                    ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                    ViewCompat.postOnAnimation((View)ItemTouchHelper.this.mRecyclerView, this);
                }
            }
        };
        this.mOnItemTouchListener = new RecyclerView.OnItemTouchListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public boolean onInterceptTouchEvent(RecyclerView object, MotionEvent motionEvent) {
                int n;
                ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
                int n2 = motionEvent.getActionMasked();
                if (n2 == 0) {
                    ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(0);
                    ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
                    ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
                    ItemTouchHelper.this.obtainVelocityTracker();
                    if (ItemTouchHelper.this.mSelected == null && (object = ItemTouchHelper.this.findAnimation(motionEvent)) != null) {
                        ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchX -= ((RecoverAnimation)object).mX;
                        itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchY -= ((RecoverAnimation)object).mY;
                        ItemTouchHelper.this.endRecoverAnimation(((RecoverAnimation)object).mViewHolder, true);
                        if (ItemTouchHelper.this.mPendingCleanup.remove((Object)object.mViewHolder.itemView)) {
                            ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, ((RecoverAnimation)object).mViewHolder);
                        }
                        ItemTouchHelper.this.select(((RecoverAnimation)object).mViewHolder, ((RecoverAnimation)object).mActionState);
                        ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, 0);
                    }
                } else if (n2 == 3 || n2 == 1) {
                    ItemTouchHelper.this.mActivePointerId = -1;
                    ItemTouchHelper.this.select(null, 0);
                } else if (ItemTouchHelper.this.mActivePointerId != -1 && (n = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId)) >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(n2, motionEvent, n);
                }
                if (ItemTouchHelper.this.mVelocityTracker != null) {
                    ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
                }
                return ItemTouchHelper.this.mSelected != null;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean bl) {
                if (!bl) {
                    return;
                }
                ItemTouchHelper.this.select(null, 0);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onTouchEvent(RecyclerView object, MotionEvent motionEvent) {
                int n;
                int n2;
                block11: {
                    block10: {
                        n = 0;
                        ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
                        if (ItemTouchHelper.this.mVelocityTracker != null) {
                            ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
                        }
                        if (ItemTouchHelper.this.mActivePointerId == -1) break block10;
                        n2 = motionEvent.getActionMasked();
                        int n3 = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                        if (n3 >= 0) {
                            ItemTouchHelper.this.checkSelectForSwipe(n2, motionEvent, n3);
                        }
                        if ((object = ItemTouchHelper.this.mSelected) == null) break block10;
                        switch (n2) {
                            default: {
                                return;
                            }
                            case 2: {
                                if (n3 < 0) break;
                                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n3);
                                ItemTouchHelper.this.moveIfNecessary((RecyclerView.ViewHolder)object);
                                ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                                ItemTouchHelper.this.mScrollRunnable.run();
                                ItemTouchHelper.this.mRecyclerView.invalidate();
                                return;
                            }
                            case 3: {
                                if (ItemTouchHelper.this.mVelocityTracker != null) {
                                    ItemTouchHelper.this.mVelocityTracker.clear();
                                }
                            }
                            case 1: {
                                ItemTouchHelper.this.select(null, 0);
                                ItemTouchHelper.this.mActivePointerId = -1;
                                return;
                            }
                            case 6: {
                                n2 = motionEvent.getActionIndex();
                                if (motionEvent.getPointerId(n2) == ItemTouchHelper.this.mActivePointerId) break block11;
                            }
                        }
                    }
                    return;
                }
                if (n2 == 0) {
                    n = 1;
                }
                ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(n);
                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n2);
            }
        };
        this.mCallback = callback;
    }

    private void addChildDrawingOrderCallback() {
        if (Build.VERSION.SDK_INT >= 21) {
            return;
        }
        if (this.mChildDrawingOrderCallback == null) {
            this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public int onGetChildDrawingOrder(int n, int n2) {
                    block6: {
                        block5: {
                            int n3;
                            if (ItemTouchHelper.this.mOverdrawChild == null) break block5;
                            int n4 = n3 = ItemTouchHelper.this.mOverdrawChildPosition;
                            if (n3 == -1) {
                                ItemTouchHelper.this.mOverdrawChildPosition = n4 = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
                            }
                            if (n2 == n - 1) {
                                return n4;
                            }
                            if (n2 >= n4) break block6;
                        }
                        return n2;
                    }
                    return n2 + 1;
                }
            };
        }
        this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int checkHorizontalSwipe(RecyclerView.ViewHolder viewHolder, int n) {
        if ((n & 0xC) != 0) {
            float f;
            float f2;
            int n2 = this.mDx > 0.0f ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                f2 = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                int n3 = f2 > 0.0f ? 8 : 4;
                f2 = Math.abs(f2);
                if ((n3 & n) != 0 && n2 == n3 && f2 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f2 > Math.abs(f)) {
                    return n3;
                }
            }
            f = this.mRecyclerView.getWidth();
            f2 = this.mCallback.getSwipeThreshold(viewHolder);
            if ((n & n2) != 0 && Math.abs(this.mDx) > f * f2) {
                return n2;
            }
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int checkVerticalSwipe(RecyclerView.ViewHolder viewHolder, int n) {
        if ((n & 3) != 0) {
            float f;
            float f2;
            int n2 = this.mDy > 0.0f ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                f2 = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                int n3 = f > 0.0f ? 2 : 1;
                f = Math.abs(f);
                if ((n3 & n) != 0 && n3 == n2 && f >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f > Math.abs(f2)) {
                    return n3;
                }
            }
            f2 = this.mRecyclerView.getHeight();
            f = this.mCallback.getSwipeThreshold(viewHolder);
            if ((n & n2) != 0 && Math.abs(this.mDy) > f2 * f) {
                return n2;
            }
        }
        return 0;
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; --i) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(0);
            this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        this.releaseVelocityTracker();
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder viewHolder) {
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new ArrayList<RecyclerView.ViewHolder>();
            this.mDistances = new ArrayList<Integer>();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int n = this.mCallback.getBoundingBoxMargin();
        int n2 = Math.round(this.mSelectedStartX + this.mDx) - n;
        int n3 = Math.round(this.mSelectedStartY + this.mDy) - n;
        int n4 = viewHolder.itemView.getWidth() + n2 + n * 2;
        int n5 = viewHolder.itemView.getHeight() + n3 + n * 2;
        int n6 = (n2 + n4) / 2;
        int n7 = (n3 + n5) / 2;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int n8 = layoutManager.getChildCount();
        n = 0;
        while (n < n8) {
            RecyclerView.ViewHolder viewHolder2;
            View view = layoutManager.getChildAt(n);
            if (view != viewHolder.itemView && view.getBottom() >= n3 && view.getTop() <= n5 && view.getRight() >= n2 && view.getLeft() <= n4 && this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, viewHolder2 = this.mRecyclerView.getChildViewHolder(view))) {
                int n9 = Math.abs(n6 - (view.getLeft() + view.getRight()) / 2);
                int n10 = Math.abs(n7 - (view.getTop() + view.getBottom()) / 2);
                int n11 = n9 * n9 + n10 * n10;
                n10 = 0;
                int n12 = this.mSwapTargets.size();
                for (n9 = 0; n9 < n12 && n11 > this.mDistances.get(n9); ++n10, ++n9) {
                }
                this.mSwapTargets.add(n10, viewHolder2);
                this.mDistances.add(n10, n11);
            }
            ++n;
        }
        return this.mSwapTargets;
    }

    /*
     * Enabled aggressive block sorting
     */
    private RecyclerView.ViewHolder findSwipedView(MotionEvent motionEvent) {
        block3: {
            block2: {
                RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
                if (this.mActivePointerId == -1) break block2;
                int n = motionEvent.findPointerIndex(this.mActivePointerId);
                float f = motionEvent.getX(n);
                float f2 = this.mInitialTouchX;
                float f3 = motionEvent.getY(n);
                float f4 = this.mInitialTouchY;
                f = Math.abs(f - f2);
                f3 = Math.abs(f3 - f4);
                if (!(f < (float)this.mSlop && f3 < (float)this.mSlop || f > f3 && layoutManager.canScrollHorizontally() || f3 > f && layoutManager.canScrollVertically()) && (motionEvent = this.findChildView(motionEvent)) != null) break block3;
            }
            return null;
        }
        return this.mRecyclerView.getChildViewHolder((View)motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getSelectedDxDy(float[] arrf) {
        arrf[0] = (this.mSelectedFlags & 0xC) != 0 ? this.mSelectedStartX + this.mDx - (float)this.mSelected.itemView.getLeft() : this.mSelected.itemView.getTranslationX();
        if ((this.mSelectedFlags & 3) != 0) {
            arrf[1] = this.mSelectedStartY + this.mDy - (float)this.mSelected.itemView.getTop();
            return;
        }
        arrf[1] = this.mSelected.itemView.getTranslationY();
    }

    private static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= (float)view.getWidth() + f3 && f2 >= f4 && f2 <= (float)view.getHeight() + f4;
    }

    private void initGestureDetector() {
        if (this.mGestureDetector != null) {
            return;
        }
        this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), (GestureDetector.OnGestureListener)new ItemTouchHelperGestureListener());
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get((Context)this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        this.initGestureDetector();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int swipeIfNecessary(RecyclerView.ViewHolder viewHolder) {
        int n;
        if (this.mActionState == 2) {
            return 0;
        }
        int n2 = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int n3 = (this.mCallback.convertToAbsoluteDirection(n2, ViewCompat.getLayoutDirection((View)this.mRecyclerView)) & 0xFF00) >> 8;
        if (n3 == 0) {
            return 0;
        }
        int n4 = (n2 & 0xFF00) >> 8;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            int n5 = this.checkHorizontalSwipe(viewHolder, n3);
            if (n5 > 0) {
                n2 = n5;
                if ((n4 & n5) != 0) return n2;
                return Callback.convertToRelativeDirection(n5, ViewCompat.getLayoutDirection((View)this.mRecyclerView));
            }
            n2 = n5 = this.checkVerticalSwipe(viewHolder, n3);
            if (n5 > 0) return n2;
            return 0;
        }
        n2 = n = this.checkVerticalSwipe(viewHolder, n3);
        if (n > 0) return n2;
        n = this.checkHorizontalSwipe(viewHolder, n3);
        if (n <= 0) return 0;
        n2 = n;
        if ((n4 & n) != 0) return n2;
        return Callback.convertToRelativeDirection(n, ViewCompat.getLayoutDirection((View)this.mRecyclerView));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        block5: {
            block4: {
                if (this.mRecyclerView == recyclerView) break block4;
                if (this.mRecyclerView != null) {
                    this.destroyCallbacks();
                }
                this.mRecyclerView = recyclerView;
                if (this.mRecyclerView != null) break block5;
            }
            return;
        }
        recyclerView = recyclerView.getResources();
        this.mSwipeEscapeVelocity = recyclerView.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
        this.mMaxSwipeVelocity = recyclerView.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
        this.setupCallbacks();
    }

    boolean checkSelectForSwipe(int n, MotionEvent motionEvent, int n2) {
        if (this.mSelected != null || n != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled()) {
            return false;
        }
        if (this.mRecyclerView.getScrollState() == 1) {
            return false;
        }
        RecyclerView.ViewHolder viewHolder = this.findSwipedView(motionEvent);
        if (viewHolder == null) {
            return false;
        }
        n = (0xFF00 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder)) >> 8;
        if (n == 0) {
            return false;
        }
        float f = motionEvent.getX(n2);
        float f2 = motionEvent.getY(n2);
        float f3 = Math.abs(f -= this.mInitialTouchX);
        float f4 = Math.abs(f2 -= this.mInitialTouchY);
        if (f3 < (float)this.mSlop && f4 < (float)this.mSlop) {
            return false;
        }
        if (f3 > f4) {
            if (f < 0.0f && (n & 4) == 0) {
                return false;
            }
            if (f > 0.0f && (n & 8) == 0) {
                return false;
            }
        } else {
            if (f2 < 0.0f && (n & 1) == 0) {
                return false;
            }
            if (f2 > 0.0f && (n & 2) == 0) {
                return false;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = motionEvent.getPointerId(0);
        this.select(viewHolder, 1);
        return true;
    }

    int endRecoverAnimation(RecyclerView.ViewHolder viewHolder, boolean bl) {
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; --i) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(i);
            if (recoverAnimation.mViewHolder != viewHolder) continue;
            recoverAnimation.mOverridden |= bl;
            if (!recoverAnimation.mEnded) {
                recoverAnimation.cancel();
            }
            this.mRecoverAnimations.remove(i);
            return recoverAnimation.mAnimationType;
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    RecoverAnimation findAnimation(MotionEvent object) {
        void var1_3;
        if (this.mRecoverAnimations.isEmpty()) {
            return var1_3;
        }
        View view = this.findChildView((MotionEvent)object);
        int n = this.mRecoverAnimations.size() - 1;
        while (n >= 0) {
            RecoverAnimation recoverAnimation;
            RecoverAnimation recoverAnimation2 = recoverAnimation = this.mRecoverAnimations.get(n);
            if (recoverAnimation.mViewHolder.itemView == view) {
                return var1_3;
            }
            --n;
        }
        return null;
    }

    View findChildView(MotionEvent object) {
        float f = object.getX();
        float f2 = object.getY();
        if (this.mSelected != null && ItemTouchHelper.hitTest((View)(object = this.mSelected.itemView), f, f2, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
            return object;
        }
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; --i) {
            object = this.mRecoverAnimations.get(i);
            View view = object.mViewHolder.itemView;
            if (!ItemTouchHelper.hitTest(view, f, f2, object.mX, object.mY)) continue;
            return view;
        }
        return this.mRecyclerView.findChildViewUnder(f, f2);
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    boolean hasRunningRecoverAnim() {
        int n = this.mRecoverAnimations.size();
        for (int i = 0; i < n; ++i) {
            if (this.mRecoverAnimations.get((int)i).mEnded) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        Object object;
        int n;
        int n2;
        int n3;
        int n4;
        block5: {
            block4: {
                if (this.mRecyclerView.isLayoutRequested() || this.mActionState != 2) break block4;
                float f = this.mCallback.getMoveThreshold(viewHolder);
                n = (int)(this.mSelectedStartX + this.mDx);
                n3 = (int)(this.mSelectedStartY + this.mDy);
                if ((float)Math.abs(n3 - viewHolder.itemView.getTop()) < (float)viewHolder.itemView.getHeight() * f && (float)Math.abs(n - viewHolder.itemView.getLeft()) < (float)viewHolder.itemView.getWidth() * f || (object = this.findSwapTargets(viewHolder)).size() == 0) break block4;
                if ((object = this.mCallback.chooseDropTarget(viewHolder, (List<RecyclerView.ViewHolder>)object, n, n3)) == null) {
                    this.mSwapTargets.clear();
                    this.mDistances.clear();
                    return;
                }
                n2 = ((RecyclerView.ViewHolder)object).getAdapterPosition();
                n4 = viewHolder.getAdapterPosition();
                if (this.mCallback.onMove(this.mRecyclerView, viewHolder, (RecyclerView.ViewHolder)object)) break block5;
            }
            return;
        }
        this.mCallback.onMoved(this.mRecyclerView, viewHolder, n4, (RecyclerView.ViewHolder)object, n2, n, n3);
    }

    void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onChildViewDetachedFromWindow(View object) {
        RecyclerView.ViewHolder viewHolder;
        block5: {
            block4: {
                this.removeChildDrawingOrderCallbackIfNecessary((View)object);
                viewHolder = this.mRecyclerView.getChildViewHolder((View)object);
                if (viewHolder == null) break block4;
                if (this.mSelected != null && viewHolder == this.mSelected) {
                    this.select(null, 0);
                    return;
                }
                this.endRecoverAnimation(viewHolder, false);
                if (this.mPendingCleanup.remove((Object)viewHolder.itemView)) break block5;
            }
            return;
        }
        this.mCallback.clearView(this.mRecyclerView, viewHolder);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        this.mOverdrawChildPosition = -1;
        float f = 0.0f;
        float f2 = 0.0f;
        if (this.mSelected != null) {
            this.getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        }
        this.mCallback.onDraw(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f = 0.0f;
        float f2 = 0.0f;
        if (this.mSelected != null) {
            this.getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    void postDispatchSwipe(final RecoverAnimation recoverAnimation, final int n) {
        this.mRecyclerView.post(new Runnable(){

            @Override
            public void run() {
                block3: {
                    block2: {
                        if (ItemTouchHelper.this.mRecyclerView == null || !ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() || recoverAnimation.mOverridden || recoverAnimation.mViewHolder.getAdapterPosition() == -1) break block2;
                        RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                        if (itemAnimator != null && itemAnimator.isRunning(null) || ItemTouchHelper.this.hasRunningRecoverAnim()) break block3;
                        ItemTouchHelper.this.mCallback.onSwiped(recoverAnimation.mViewHolder, n);
                    }
                    return;
                }
                ItemTouchHelper.this.mRecyclerView.post((Runnable)this);
            }
        });
    }

    void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean scrollIfNecessary() {
        int n;
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        long l = System.currentTimeMillis();
        long l2 = this.mDragScrollStartTimeInMs == Long.MIN_VALUE ? 0L : l - this.mDragScrollStartTimeInMs;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        int n2 = 0;
        int n3 = 0;
        layoutManager.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        int n4 = n2;
        if (layoutManager.canScrollHorizontally()) {
            n = (int)(this.mSelectedStartX + this.mDx);
            n4 = n - this.mTmpRect.left - this.mRecyclerView.getPaddingLeft();
            if (!(this.mDx < 0.0f) || n4 >= 0) {
                n4 = n2;
                if (this.mDx > 0.0f) {
                    n = this.mSelected.itemView.getWidth() + n + this.mTmpRect.right - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
                    n4 = n2;
                    if (n > 0) {
                        n4 = n;
                    }
                }
            }
        }
        n2 = n3;
        if (layoutManager.canScrollVertically()) {
            n = (int)(this.mSelectedStartY + this.mDy);
            n2 = n - this.mTmpRect.top - this.mRecyclerView.getPaddingTop();
            if (!(this.mDy < 0.0f) || n2 >= 0) {
                n2 = n3;
                if (this.mDy > 0.0f) {
                    n = this.mSelected.itemView.getHeight() + n + this.mTmpRect.bottom - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
                    n2 = n3;
                    if (n > 0) {
                        n2 = n;
                    }
                }
            }
        }
        n3 = n4;
        if (n4 != 0) {
            n3 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), n4, this.mRecyclerView.getWidth(), l2);
        }
        n4 = n2;
        if (n2 != 0) {
            n4 = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), n2, this.mRecyclerView.getHeight(), l2);
        }
        if (n3 == 0 && n4 == 0) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            this.mDragScrollStartTimeInMs = l;
        }
        this.mRecyclerView.scrollBy(n3, n4);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void select(RecyclerView.ViewHolder viewHolder, int n) {
        if (viewHolder == this.mSelected && n == this.mActionState) {
            return;
        }
        this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
        int n2 = this.mActionState;
        this.endRecoverAnimation(viewHolder, true);
        this.mActionState = n;
        if (n == 2) {
            this.mOverdrawChild = viewHolder.itemView;
            this.addChildDrawingOrderCallback();
        }
        int n3 = 0;
        final int n4 = 0;
        if (this.mSelected != null) {
            Object object = this.mSelected;
            if (((RecyclerView.ViewHolder)object).itemView.getParent() != null) {
                float f;
                float f2;
                n4 = n2 == 2 ? 0 : this.swipeIfNecessary((RecyclerView.ViewHolder)object);
                this.releaseVelocityTracker();
                switch (n4) {
                    default: {
                        f = 0.0f;
                        f2 = 0.0f;
                        break;
                    }
                    case 4: 
                    case 8: 
                    case 16: 
                    case 32: {
                        f2 = 0.0f;
                        f = Math.signum(this.mDx) * (float)this.mRecyclerView.getWidth();
                        break;
                    }
                    case 1: 
                    case 2: {
                        f = 0.0f;
                        f2 = Math.signum(this.mDy) * (float)this.mRecyclerView.getHeight();
                    }
                }
                n3 = n2 == 2 ? 8 : (n4 > 0 ? 2 : 4);
                this.getSelectedDxDy(this.mTmpPosition);
                float f3 = this.mTmpPosition[0];
                float f4 = this.mTmpPosition[1];
                object = new RecoverAnimation((RecyclerView.ViewHolder)object, n3, n2, f3, f4, f, f2, (RecyclerView.ViewHolder)object){
                    final /* synthetic */ RecyclerView.ViewHolder val$prevSelected;
                    {
                        this.val$prevSelected = viewHolder2;
                        super(viewHolder, n, n2, f, f2, f3, f4);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        block7: {
                            block6: {
                                super.onAnimationEnd(animator2);
                                if (this.mOverridden) break block6;
                                if (n4 <= 0) {
                                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, this.val$prevSelected);
                                } else {
                                    ItemTouchHelper.this.mPendingCleanup.add(this.val$prevSelected.itemView);
                                    this.mIsPendingCleanup = true;
                                    if (n4 > 0) {
                                        ItemTouchHelper.this.postDispatchSwipe(this, n4);
                                    }
                                }
                                if (ItemTouchHelper.this.mOverdrawChild == this.val$prevSelected.itemView) break block7;
                            }
                            return;
                        }
                        ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
                    }
                };
                ((RecoverAnimation)object).setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, n3, f - f3, f2 - f4));
                this.mRecoverAnimations.add((RecoverAnimation)object);
                ((RecoverAnimation)object).start();
                n3 = 1;
            } else {
                this.removeChildDrawingOrderCallbackIfNecessary(((RecyclerView.ViewHolder)object).itemView);
                this.mCallback.clearView(this.mRecyclerView, (RecyclerView.ViewHolder)object);
                n3 = n4;
            }
            this.mSelected = null;
        }
        if (viewHolder != null) {
            this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & (1 << n * 8 + 8) - 1) >> this.mActionState * 8;
            this.mSelectedStartX = viewHolder.itemView.getLeft();
            this.mSelectedStartY = viewHolder.itemView.getTop();
            this.mSelected = viewHolder;
            if (n == 2) {
                this.mSelected.itemView.performHapticFeedback(0);
            }
        }
        if ((viewHolder = this.mRecyclerView.getParent()) != null) {
            boolean bl = this.mSelected != null;
            viewHolder.requestDisallowInterceptTouchEvent(bl);
        }
        if (n3 == 0) {
            this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
        }
        this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
        this.mRecyclerView.invalidate();
    }

    void updateDxDy(MotionEvent motionEvent, int n, int n2) {
        float f = motionEvent.getX(n2);
        float f2 = motionEvent.getY(n2);
        this.mDx = f - this.mInitialTouchX;
        this.mDy = f2 - this.mInitialTouchY;
        if ((n & 4) == 0) {
            this.mDx = Math.max(0.0f, this.mDx);
        }
        if ((n & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if ((n & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((n & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    public static abstract class Callback {
        private static final Interpolator sDragScrollInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
        private static final ItemTouchUIUtil sUICallback = Build.VERSION.SDK_INT >= 21 ? new ItemTouchUIUtilImpl.Api21Impl() : new ItemTouchUIUtilImpl.BaseImpl();
        private int mCachedMaxScrollSpeed = -1;

        public static int convertToRelativeDirection(int n, int n2) {
            int n3 = n & 0xC0C0C;
            if (n3 == 0) {
                return n;
            }
            n &= ~n3;
            if (n2 == 0) {
                return n | n3 << 2;
            }
            return n | n3 << 1 & 0xFFF3F3F3 | (n3 << 1 & 0xC0C0C) << 2;
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public static int makeFlag(int n, int n2) {
            return n2 << n * 8;
        }

        public static int makeMovementFlags(int n, int n2) {
            return Callback.makeFlag(0, n2 | n) | Callback.makeFlag(1, n2) | Callback.makeFlag(2, n);
        }

        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder viewHolder, List<RecyclerView.ViewHolder> list, int n, int n2) {
            int n3 = viewHolder.itemView.getWidth();
            int n4 = viewHolder.itemView.getHeight();
            RecyclerView.ViewHolder viewHolder2 = null;
            int n5 = -1;
            int n6 = n - viewHolder.itemView.getLeft();
            int n7 = n2 - viewHolder.itemView.getTop();
            int n8 = list.size();
            for (int i = 0; i < n8; ++i) {
                int n9;
                RecyclerView.ViewHolder viewHolder3 = list.get(i);
                RecyclerView.ViewHolder viewHolder4 = viewHolder2;
                int n10 = n5;
                if (n6 > 0) {
                    n9 = viewHolder3.itemView.getRight() - (n + n3);
                    viewHolder4 = viewHolder2;
                    n10 = n5;
                    if (n9 < 0) {
                        viewHolder4 = viewHolder2;
                        n10 = n5;
                        if (viewHolder3.itemView.getRight() > viewHolder.itemView.getRight()) {
                            n9 = Math.abs(n9);
                            viewHolder4 = viewHolder2;
                            n10 = n5;
                            if (n9 > n5) {
                                n10 = n9;
                                viewHolder4 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder2 = viewHolder4;
                n5 = n10;
                if (n6 < 0) {
                    n9 = viewHolder3.itemView.getLeft() - n;
                    viewHolder2 = viewHolder4;
                    n5 = n10;
                    if (n9 > 0) {
                        viewHolder2 = viewHolder4;
                        n5 = n10;
                        if (viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft()) {
                            n9 = Math.abs(n9);
                            viewHolder2 = viewHolder4;
                            n5 = n10;
                            if (n9 > n10) {
                                n5 = n9;
                                viewHolder2 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder4 = viewHolder2;
                n10 = n5;
                if (n7 < 0) {
                    n9 = viewHolder3.itemView.getTop() - n2;
                    viewHolder4 = viewHolder2;
                    n10 = n5;
                    if (n9 > 0) {
                        viewHolder4 = viewHolder2;
                        n10 = n5;
                        if (viewHolder3.itemView.getTop() < viewHolder.itemView.getTop()) {
                            n9 = Math.abs(n9);
                            viewHolder4 = viewHolder2;
                            n10 = n5;
                            if (n9 > n5) {
                                n10 = n9;
                                viewHolder4 = viewHolder3;
                            }
                        }
                    }
                }
                viewHolder2 = viewHolder4;
                n5 = n10;
                if (n7 <= 0) continue;
                n9 = viewHolder3.itemView.getBottom() - (n2 + n4);
                viewHolder2 = viewHolder4;
                n5 = n10;
                if (n9 >= 0) continue;
                viewHolder2 = viewHolder4;
                n5 = n10;
                if (viewHolder3.itemView.getBottom() <= viewHolder.itemView.getBottom()) continue;
                n9 = Math.abs(n9);
                viewHolder2 = viewHolder4;
                n5 = n10;
                if (n9 <= n10) continue;
                n5 = n9;
                viewHolder2 = viewHolder3;
            }
            return viewHolder2;
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            sUICallback.clearView(viewHolder.itemView);
        }

        public int convertToAbsoluteDirection(int n, int n2) {
            int n3 = n & 0x303030;
            if (n3 == 0) {
                return n;
            }
            n &= ~n3;
            if (n2 == 0) {
                return n | n3 >> 2;
            }
            return n | n3 >> 1 & 0xFFCFCFCF | (n3 >> 1 & 0x303030) >> 2;
        }

        final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.convertToAbsoluteDirection(this.getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection((View)recyclerView));
        }

        public long getAnimationDuration(RecyclerView object, int n, float f, float f2) {
            if ((object = ((RecyclerView)object).getItemAnimator()) == null) {
                if (n == 8) {
                    return 200L;
                }
                return 250L;
            }
            if (n == 8) {
                return ((RecyclerView.ItemAnimator)object).getMoveDuration();
            }
            return ((RecyclerView.ItemAnimator)object).getRemoveDuration();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView var1, RecyclerView.ViewHolder var2);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (0xFF0000 & this.getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int n, int n2, int n3, long l) {
            n3 = this.getMaxDragScroll(recyclerView);
            int n4 = Math.abs(n2);
            int n5 = (int)Math.signum(n2);
            float f = Math.min(1.0f, 1.0f * (float)n4 / (float)n);
            n = (int)((float)(n5 * n3) * sDragViewScrollCapInterpolator.getInterpolation(f));
            f = l > 2000L ? 1.0f : (float)l / 2000.0f;
            if ((n = (int)((float)n * sDragScrollInterpolator.getInterpolation(f))) != 0) {
                return n;
            }
            if (n2 > 0) {
                return 1;
            }
            return -1;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int n, boolean bl) {
            sUICallback.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, n, bl);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int n, boolean bl) {
            sUICallback.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, n, bl);
        }

        void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<RecoverAnimation> list, int n, float f, float f2) {
            int n2;
            int n3 = list.size();
            for (n2 = 0; n2 < n3; ++n2) {
                RecoverAnimation recoverAnimation = list.get(n2);
                recoverAnimation.update();
                int n4 = canvas.save();
                this.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(n4);
            }
            if (viewHolder != null) {
                n2 = canvas.save();
                this.onChildDraw(canvas, recyclerView, viewHolder, f, f2, n, true);
                canvas.restoreToCount(n2);
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        void onDrawOver(Canvas object, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<RecoverAnimation> list, int n, float f, float f2) {
            void var4_6;
            void var3_5;
            int n2;
            int n3;
            void var2_4;
            int n4 = var4_6.size();
            for (n2 = 0; n2 < n4; ++n2) {
                RecoverAnimation recoverAnimation = (RecoverAnimation)var4_6.get(n2);
                int n5 = object.save();
                this.onChildDrawOver((Canvas)object, (RecyclerView)var2_4, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                object.restoreToCount(n5);
            }
            if (var3_5 != null) {
                void var6_8;
                void var7_9;
                n2 = object.save();
                this.onChildDrawOver((Canvas)object, (RecyclerView)var2_4, (RecyclerView.ViewHolder)var3_5, (float)var6_8, (float)var7_9, n3, true);
                object.restoreToCount(n2);
            }
            n2 = 0;
            for (n3 = n4 - 1; n3 >= 0; --n3) {
                RecoverAnimation recoverAnimation = (RecoverAnimation)var4_6.get(n3);
                if (recoverAnimation.mEnded && !recoverAnimation.mIsPendingCleanup) {
                    var4_6.remove(n3);
                    continue;
                }
                if (recoverAnimation.mEnded) continue;
                n2 = 1;
            }
            if (n2 != 0) {
                var2_4.invalidate();
            }
        }

        public abstract boolean onMove(RecyclerView var1, RecyclerView.ViewHolder var2, RecyclerView.ViewHolder var3);

        /*
         * Enabled aggressive block sorting
         */
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int n, RecyclerView.ViewHolder viewHolder2, int n2, int n3, int n4) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler)((Object)layoutManager)).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, n3, n4);
                return;
            } else {
                if (layoutManager.canScrollHorizontally()) {
                    if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                        recyclerView.scrollToPosition(n2);
                    }
                    if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                        recyclerView.scrollToPosition(n2);
                    }
                }
                if (!layoutManager.canScrollVertically()) return;
                {
                    if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                        recyclerView.scrollToPosition(n2);
                    }
                    if (layoutManager.getDecoratedBottom(viewHolder2.itemView) < recyclerView.getHeight() - recyclerView.getPaddingBottom()) return;
                    {
                        recyclerView.scrollToPosition(n2);
                        return;
                    }
                }
            }
        }

        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int n) {
            if (viewHolder != null) {
                sUICallback.onSelected(viewHolder.itemView);
            }
        }

        public abstract void onSwiped(RecyclerView.ViewHolder var1, int var2);

    }

    private class ItemTouchHelperGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        ItemTouchHelperGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onLongPress(MotionEvent object) {
            RecyclerView.ViewHolder viewHolder;
            block3: {
                block2: {
                    View view = ItemTouchHelper.this.findChildView((MotionEvent)object);
                    if (view == null || (viewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(view)) == null || !ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, viewHolder) || object.getPointerId(0) != ItemTouchHelper.this.mActivePointerId) break block2;
                    int n = object.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                    float f = object.getX(n);
                    float f2 = object.getY(n);
                    ItemTouchHelper.this.mInitialTouchX = f;
                    ItemTouchHelper.this.mInitialTouchY = f2;
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    ItemTouchHelper.this.mDy = 0.0f;
                    itemTouchHelper.mDx = 0.0f;
                    if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) break block3;
                }
                return;
            }
            ItemTouchHelper.this.select(viewHolder, 2);
        }
    }

    private static class RecoverAnimation
    implements Animator.AnimatorListener {
        final int mActionState;
        final int mAnimationType;
        boolean mEnded = false;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final ValueAnimator mValueAnimator;
        final RecyclerView.ViewHolder mViewHolder;
        float mX;
        float mY;

        RecoverAnimation(RecyclerView.ViewHolder viewHolder, int n, int n2, float f, float f2, float f3, float f4) {
            this.mActionState = n2;
            this.mAnimationType = n;
            this.mViewHolder = viewHolder;
            this.mStartDx = f;
            this.mStartDy = f2;
            this.mTargetX = f3;
            this.mTargetY = f4;
            this.mValueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
            this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation.this.setFraction(valueAnimator.getAnimatedFraction());
                }
            });
            this.mValueAnimator.setTarget((Object)viewHolder.itemView);
            this.mValueAnimator.addListener((Animator.AnimatorListener)this);
            this.setFraction(0.0f);
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        public void onAnimationCancel(Animator animator2) {
            this.setFraction(1.0f);
        }

        public void onAnimationEnd(Animator animator2) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public void onAnimationRepeat(Animator animator2) {
        }

        public void onAnimationStart(Animator animator2) {
        }

        public void setDuration(long l) {
            this.mValueAnimator.setDuration(l);
        }

        public void setFraction(float f) {
            this.mFraction = f;
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void update() {
            this.mX = this.mStartDx == this.mTargetX ? this.mViewHolder.itemView.getTranslationX() : this.mStartDx + this.mFraction * (this.mTargetX - this.mStartDx);
            if (this.mStartDy == this.mTargetY) {
                this.mY = this.mViewHolder.itemView.getTranslationY();
                return;
            }
            this.mY = this.mStartDy + this.mFraction * (this.mTargetY - this.mStartDy);
        }

    }

    public static abstract class SimpleCallback
    extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int n, int n2) {
            this.mDefaultSwipeDirs = n2;
            this.mDefaultDragDirs = n;
        }

        public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return SimpleCallback.makeMovementFlags(this.getDragDirs(recyclerView, viewHolder), this.getSwipeDirs(recyclerView, viewHolder));
        }

        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }
    }

    public static interface ViewDropHandler {
        public void prepareForDrop(View var1, View var2, int var3, int var4);
    }

}

