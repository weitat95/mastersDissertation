/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.WindowInsets
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout
extends ViewGroup {
    static final boolean CAN_HIDE_DESCENDANTS;
    static final int[] LAYOUT_ATTRS;
    private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
    private static final int[] THEME_ATTRS;
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private boolean mDrawStatusBarBackground;
    private float mDrawerElevation;
    private int mDrawerState;
    private boolean mFirstLayout = true;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private Object mLastInsets;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    private DrawerListener mListener;
    private List<DrawerListener> mListeners;
    private int mLockModeEnd = 3;
    private int mLockModeLeft = 3;
    private int mLockModeRight = 3;
    private int mLockModeStart = 3;
    private int mMinDrawerMargin;
    private final ArrayList<View> mNonDrawerViews;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor = -1728053248;
    private float mScrimOpacity;
    private Paint mScrimPaint = new Paint();
    private Drawable mShadowEnd = null;
    private Drawable mShadowLeft = null;
    private Drawable mShadowLeftResolved;
    private Drawable mShadowRight = null;
    private Drawable mShadowRightResolved;
    private Drawable mShadowStart = null;
    private Drawable mStatusBarBackground;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = true;
        THEME_ATTRS = new int[]{16843828};
        LAYOUT_ATTRS = new int[]{16842931};
        boolean bl2 = Build.VERSION.SDK_INT >= 19;
        CAN_HIDE_DESCENDANTS = bl2;
        bl2 = Build.VERSION.SDK_INT >= 21 ? bl : false;
        SET_DRAWER_SHADOW_FROM_ELEVATION = bl2;
    }

    public DrawerLayout(Context context) {
        this(context, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DrawerLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.setDescendantFocusability(262144);
        float f = this.getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int)(64.0f * f + 0.5f);
        float f2 = 400.0f * f;
        this.mLeftCallback = new ViewDragCallback(3);
        this.mRightCallback = new ViewDragCallback(5);
        this.mLeftDragger = ViewDragHelper.create(this, 1.0f, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity(f2);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, 1.0f, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity(f2);
        this.mRightCallback.setDragger(this.mRightDragger);
        this.setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility((View)this, 1);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
        if (ViewCompat.getFitsSystemWindows((View)this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                this.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){

                    /*
                     * Enabled aggressive block sorting
                     */
                    @TargetApi(value=21)
                    public WindowInsets onApplyWindowInsets(View object, WindowInsets windowInsets) {
                        object = (DrawerLayout)((Object)object);
                        boolean bl = windowInsets.getSystemWindowInsetTop() > 0;
                        ((DrawerLayout)((Object)object)).setChildInsets((Object)windowInsets, bl);
                        return windowInsets.consumeSystemWindowInsets();
                    }
                });
                this.setSystemUiVisibility(1280);
                context = context.obtainStyledAttributes(THEME_ATTRS);
                try {
                    this.mStatusBarBackground = context.getDrawable(0);
                }
                finally {
                    context.recycle();
                }
            } else {
                this.mStatusBarBackground = null;
            }
        }
        this.mDrawerElevation = 10.0f * f;
        this.mNonDrawerViews = new ArrayList();
    }

    static String gravityToString(int n) {
        if ((n & 3) == 3) {
            return "LEFT";
        }
        if ((n & 5) == 5) {
            return "RIGHT";
        }
        return Integer.toHexString(n);
    }

    private static boolean hasOpaqueBackground(View view) {
        boolean bl = false;
        view = view.getBackground();
        boolean bl2 = bl;
        if (view != null) {
            bl2 = bl;
            if (view.getOpacity() == -1) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private boolean hasPeekingDrawer() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            if (!((LayoutParams)this.getChildAt((int)i).getLayoutParams()).isPeeking) continue;
            return true;
        }
        return false;
    }

    private boolean hasVisibleDrawer() {
        return this.findVisibleDrawer() != null;
    }

    static boolean includeChildForAccessibility(View view) {
        return ViewCompat.getImportantForAccessibility(view) != 4 && ViewCompat.getImportantForAccessibility(view) != 2;
    }

    private boolean mirror(Drawable drawable2, int n) {
        if (drawable2 == null || !DrawableCompat.isAutoMirrored(drawable2)) {
            return false;
        }
        DrawableCompat.setLayoutDirection(drawable2, n);
        return true;
    }

    private Drawable resolveLeftShadow() {
        int n = ViewCompat.getLayoutDirection((View)this);
        if (n == 0) {
            if (this.mShadowStart != null) {
                this.mirror(this.mShadowStart, n);
                return this.mShadowStart;
            }
        } else if (this.mShadowEnd != null) {
            this.mirror(this.mShadowEnd, n);
            return this.mShadowEnd;
        }
        return this.mShadowLeft;
    }

    private Drawable resolveRightShadow() {
        int n = ViewCompat.getLayoutDirection((View)this);
        if (n == 0) {
            if (this.mShadowEnd != null) {
                this.mirror(this.mShadowEnd, n);
                return this.mShadowEnd;
            }
        } else if (this.mShadowStart != null) {
            this.mirror(this.mShadowStart, n);
            return this.mShadowStart;
        }
        return this.mShadowRight;
    }

    private void resolveShadowDrawables() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return;
        }
        this.mShadowLeftResolved = this.resolveLeftShadow();
        this.mShadowRightResolved = this.resolveRightShadow();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateChildrenImportantForAccessibility(View view, boolean bl) {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view2 = this.getChildAt(n2);
            if (!bl && !this.isDrawerView(view2) || bl && view2 == view) {
                ViewCompat.setImportantForAccessibility(view2, 1);
            } else {
                ViewCompat.setImportantForAccessibility(view2, 4);
            }
            ++n2;
        }
        return;
    }

    public void addDrawerListener(DrawerListener drawerListener) {
        if (drawerListener == null) {
            return;
        }
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<DrawerListener>();
        }
        this.mListeners.add(drawerListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3;
        View view;
        if (this.getDescendantFocusability() == 393216) {
            return;
        }
        int n4 = this.getChildCount();
        int n5 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            view = this.getChildAt(n3);
            if (this.isDrawerView(view)) {
                if (!this.isDrawerOpen(view)) continue;
                n5 = 1;
                view.addFocusables(arrayList, n, n2);
                continue;
            }
            this.mNonDrawerViews.add(view);
        }
        if (n5 == 0) {
            n5 = this.mNonDrawerViews.size();
            for (n3 = 0; n3 < n5; ++n3) {
                view = this.mNonDrawerViews.get(n3);
                if (view.getVisibility() != 0) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        this.mNonDrawerViews.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
        if (this.findOpenDrawer() != null || this.isDrawerView(view)) {
            ViewCompat.setImportantForAccessibility(view, 4);
        } else {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
        if (!CAN_HIDE_DESCENDANTS) {
            ViewCompat.setAccessibilityDelegate(view, this.mChildAccessibilityDelegate);
        }
    }

    void cancelChildViewTouch() {
        if (!this.mChildrenCanceledTouch) {
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                this.getChildAt(i).dispatchTouchEvent(motionEvent);
            }
            motionEvent.recycle();
            this.mChildrenCanceledTouch = true;
        }
    }

    boolean checkDrawerViewAbsoluteGravity(View view, int n) {
        return (this.getDrawerViewAbsoluteGravity(view) & n) == n;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void closeDrawer(int n) {
        this.closeDrawer(n, true);
    }

    public void closeDrawer(int n, boolean bl) {
        View view = this.findDrawerWithGravity(n);
        if (view == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + DrawerLayout.gravityToString(n));
        }
        this.closeDrawer(view, bl);
    }

    public void closeDrawer(View view) {
        this.closeDrawer(view, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void closeDrawer(View view, boolean bl) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.onScreen = 0.0f;
            layoutParams.openState = 0;
        } else if (bl) {
            layoutParams.openState |= 4;
            if (this.checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
            } else {
                this.mRightDragger.smoothSlideViewTo(view, this.getWidth(), view.getTop());
            }
        } else {
            this.moveDrawerToOffset(view, 0.0f);
            this.updateDrawerState(layoutParams.gravity, 0, view);
            view.setVisibility(4);
        }
        this.invalidate();
    }

    public void closeDrawers() {
        this.closeDrawers(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    void closeDrawers(boolean bl) {
        int n = 0;
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n3 = n;
            if (this.isDrawerView(view)) {
                if (bl && !layoutParams.isPeeking) {
                    n3 = n;
                } else {
                    n3 = view.getWidth();
                    n = this.checkDrawerViewAbsoluteGravity(view, 3) ? (n |= this.mLeftDragger.smoothSlideViewTo(view, -n3, view.getTop())) : (n |= this.mRightDragger.smoothSlideViewTo(view, this.getWidth(), view.getTop()));
                    layoutParams.isPeeking = false;
                    n3 = n;
                }
            }
            n = n3;
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if (n != 0) {
            this.invalidate();
        }
    }

    public void computeScroll() {
        int n = this.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < n; ++i) {
            f = Math.max(f, ((LayoutParams)this.getChildAt((int)i).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = f;
        boolean bl = this.mLeftDragger.continueSettling(true);
        boolean bl2 = this.mRightDragger.continueSettling(true);
        if (bl || bl2) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void dispatchOnDrawerClosed(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if ((layoutParams.openState & 1) == 1) {
            layoutParams.openState = 0;
            if (this.mListeners != null) {
                for (int i = this.mListeners.size() - 1; i >= 0; --i) {
                    this.mListeners.get(i).onDrawerClosed(view);
                }
            }
            this.updateChildrenImportantForAccessibility(view, false);
            if (this.hasWindowFocus() && (view = this.getRootView()) != null) {
                view.sendAccessibilityEvent(32);
            }
        }
    }

    void dispatchOnDrawerOpened(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if ((layoutParams.openState & 1) == 0) {
            layoutParams.openState = 1;
            if (this.mListeners != null) {
                for (int i = this.mListeners.size() - 1; i >= 0; --i) {
                    this.mListeners.get(i).onDrawerOpened(view);
                }
            }
            this.updateChildrenImportantForAccessibility(view, true);
            if (this.hasWindowFocus()) {
                this.sendAccessibilityEvent(32);
            }
        }
    }

    void dispatchOnDrawerSlide(View view, float f) {
        if (this.mListeners != null) {
            for (int i = this.mListeners.size() - 1; i >= 0; --i) {
                this.mListeners.get(i).onDrawerSlide(view, f);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean drawChild(Canvas canvas, View view, long l) {
        int n = this.getHeight();
        boolean bl = this.isContentView(view);
        int n2 = 0;
        int n3 = 0;
        int n4 = this.getWidth();
        int n5 = canvas.save();
        int n6 = n4;
        if (bl) {
            int n7 = this.getChildCount();
            n2 = n3;
            for (n6 = 0; n6 < n7; ++n6) {
                View view2 = this.getChildAt(n6);
                n3 = n2;
                int n8 = n4;
                if (view2 != view) {
                    n3 = n2;
                    n8 = n4;
                    if (view2.getVisibility() == 0) {
                        n3 = n2;
                        n8 = n4;
                        if (DrawerLayout.hasOpaqueBackground(view2)) {
                            n3 = n2;
                            n8 = n4;
                            if (this.isDrawerView(view2)) {
                                int n9;
                                if (view2.getHeight() < n) {
                                    n8 = n4;
                                    n3 = n2;
                                } else if (this.checkDrawerViewAbsoluteGravity(view2, 3)) {
                                    n9 = view2.getRight();
                                    n3 = n2;
                                    n8 = n4;
                                    if (n9 > n2) {
                                        n3 = n9;
                                        n8 = n4;
                                    }
                                } else {
                                    n9 = view2.getLeft();
                                    n3 = n2;
                                    n8 = n4;
                                    if (n9 < n4) {
                                        n8 = n9;
                                        n3 = n2;
                                    }
                                }
                            }
                        }
                    }
                }
                n2 = n3;
                n4 = n8;
            }
            canvas.clipRect(n2, 0, n4, this.getHeight());
            n6 = n4;
        }
        boolean bl2 = super.drawChild(canvas, view, l);
        canvas.restoreToCount(n5);
        if (this.mScrimOpacity > 0.0f && bl) {
            n4 = (int)((float)((this.mScrimColor & 0xFF000000) >>> 24) * this.mScrimOpacity);
            n3 = this.mScrimColor;
            this.mScrimPaint.setColor(n4 << 24 | n3 & 0xFFFFFF);
            canvas.drawRect((float)n2, 0.0f, (float)n6, (float)this.getHeight(), this.mScrimPaint);
            return bl2;
        } else {
            if (this.mShadowLeftResolved != null && this.checkDrawerViewAbsoluteGravity(view, 3)) {
                n2 = this.mShadowLeftResolved.getIntrinsicWidth();
                n4 = view.getRight();
                n6 = this.mLeftDragger.getEdgeSize();
                float f = Math.max(0.0f, Math.min((float)n4 / (float)n6, 1.0f));
                this.mShadowLeftResolved.setBounds(n4, view.getTop(), n4 + n2, view.getBottom());
                this.mShadowLeftResolved.setAlpha((int)(255.0f * f));
                this.mShadowLeftResolved.draw(canvas);
                return bl2;
            }
            if (this.mShadowRightResolved == null || !this.checkDrawerViewAbsoluteGravity(view, 5)) return bl2;
            {
                n2 = this.mShadowRightResolved.getIntrinsicWidth();
                n4 = view.getLeft();
                n6 = this.getWidth();
                n3 = this.mRightDragger.getEdgeSize();
                float f = Math.max(0.0f, Math.min((float)(n6 - n4) / (float)n3, 1.0f));
                this.mShadowRightResolved.setBounds(n4 - n2, view.getTop(), n4, view.getBottom());
                this.mShadowRightResolved.setAlpha((int)(255.0f * f));
                this.mShadowRightResolved.draw(canvas);
                return bl2;
            }
        }
    }

    View findDrawerWithGravity(int n) {
        int n2 = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this));
        int n3 = this.getChildCount();
        for (n = 0; n < n3; ++n) {
            View view = this.getChildAt(n);
            if ((this.getDrawerViewAbsoluteGravity(view) & 7) != (n2 & 7)) continue;
            return view;
        }
        return null;
    }

    View findOpenDrawer() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if ((((LayoutParams)view.getLayoutParams()).openState & 1) != 1) continue;
            return view;
        }
        return null;
    }

    View findVisibleDrawer() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!this.isDrawerView(view) || !this.isDrawerVisible(view)) continue;
            return view;
        }
        return null;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public float getDrawerElevation() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return this.mDrawerElevation;
        }
        return 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getDrawerLockMode(int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        switch (n) {
            case 3: {
                if (this.mLockModeLeft != 3) {
                    return this.mLockModeLeft;
                }
                n = n2 == 0 ? this.mLockModeStart : this.mLockModeEnd;
                if (n == 3) return 0;
                return n;
            }
            case 5: {
                if (this.mLockModeRight != 3) {
                    return this.mLockModeRight;
                }
                n = n2 == 0 ? this.mLockModeEnd : this.mLockModeStart;
                if (n == 3) return 0;
                return n;
            }
            case 8388611: {
                if (this.mLockModeStart != 3) {
                    return this.mLockModeStart;
                }
                n = n2 == 0 ? this.mLockModeLeft : this.mLockModeRight;
                if (n == 3) return 0;
                return n;
            }
            default: {
                return 0;
            }
            case 8388613: 
        }
        if (this.mLockModeEnd != 3) {
            return this.mLockModeEnd;
        }
        n = n2 == 0 ? this.mLockModeRight : this.mLockModeLeft;
        if (n == 3) return 0;
        return n;
    }

    public int getDrawerLockMode(View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a drawer");
        }
        return this.getDrawerLockMode(((LayoutParams)view.getLayoutParams()).gravity);
    }

    public CharSequence getDrawerTitle(int n) {
        if ((n = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this))) == 3) {
            return this.mTitleLeft;
        }
        if (n == 5) {
            return this.mTitleRight;
        }
        return null;
    }

    int getDrawerViewAbsoluteGravity(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection((View)this));
    }

    float getDrawerViewOffset(View view) {
        return ((LayoutParams)view.getLayoutParams()).onScreen;
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.mStatusBarBackground;
    }

    boolean isContentView(View view) {
        return ((LayoutParams)view.getLayoutParams()).gravity == 0;
    }

    public boolean isDrawerOpen(int n) {
        View view = this.findDrawerWithGravity(n);
        if (view != null) {
            return this.isDrawerOpen(view);
        }
        return false;
    }

    public boolean isDrawerOpen(View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a drawer");
        }
        return (((LayoutParams)view.getLayoutParams()).openState & 1) == 1;
    }

    boolean isDrawerView(View view) {
        int n = GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view));
        if ((n & 3) != 0) {
            return true;
        }
        return (n & 5) != 0;
    }

    public boolean isDrawerVisible(int n) {
        View view = this.findDrawerWithGravity(n);
        if (view != null) {
            return this.isDrawerVisible(view);
        }
        return false;
    }

    public boolean isDrawerVisible(View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a drawer");
        }
        return ((LayoutParams)view.getLayoutParams()).onScreen > 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveDrawerToOffset(View view, float f) {
        float f2 = this.getDrawerViewOffset(view);
        int n = view.getWidth();
        int n2 = (int)((float)n * f2);
        n = (int)((float)n * f) - n2;
        if (!this.checkDrawerViewAbsoluteGravity(view, 3)) {
            n = -n;
        }
        view.offsetLeftAndRight(n);
        this.setDrawerViewOffset(view, f);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int n = Build.VERSION.SDK_INT >= 21 ? (this.mLastInsets != null ? ((WindowInsets)this.mLastInsets).getSystemWindowInsetTop() : 0) : 0;
            if (n > 0) {
                this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), n);
                this.mStatusBarBackground.draw(canvas);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl = false;
        int n = motionEvent.getActionMasked();
        boolean bl2 = this.mLeftDragger.shouldInterceptTouchEvent(motionEvent);
        boolean bl3 = this.mRightDragger.shouldInterceptTouchEvent(motionEvent);
        int n2 = 0;
        int n3 = 0;
        switch (n) {
            default: {
                n = n3;
                break;
            }
            case 0: {
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                this.mInitialMotionX = f;
                this.mInitialMotionY = f2;
                n = n2;
                if (this.mScrimOpacity > 0.0f) {
                    motionEvent = this.mLeftDragger.findTopChildUnder((int)f, (int)f2);
                    n = n2;
                    if (motionEvent != null) {
                        n = n2;
                        if (this.isContentView((View)motionEvent)) {
                            n = 1;
                        }
                    }
                }
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            }
            case 2: {
                n = n3;
                if (!this.mLeftDragger.checkTouchSlop(3)) break;
                this.mLeftCallback.removeCallbacks();
                this.mRightCallback.removeCallbacks();
                n = n3;
                break;
            }
            case 1: 
            case 3: {
                this.closeDrawers(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                n = n3;
            }
        }
        if (bl2 | bl3) return true;
        if (n != 0) return true;
        if (this.hasPeekingDrawer()) return true;
        if (!this.mChildrenCanceledTouch) return bl;
        return true;
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && this.hasVisibleDrawer()) {
            keyEvent.startTracking();
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (n == 4) {
            keyEvent = this.findVisibleDrawer();
            if (keyEvent != null && this.getDrawerLockMode((View)keyEvent) == 0) {
                this.closeDrawers();
            }
            return keyEvent != null;
        }
        return super.onKeyUp(n, keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.mInLayout = true;
        int n5 = n3 - n;
        int n6 = this.getChildCount();
        n3 = 0;
        do {
            if (n3 >= n6) {
                this.mInLayout = false;
                this.mFirstLayout = false;
                return;
            }
            View view = this.getChildAt(n3);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (this.isContentView(view)) {
                    view.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + view.getMeasuredWidth(), layoutParams.topMargin + view.getMeasuredHeight());
                } else {
                    float f;
                    int n7;
                    int n8 = view.getMeasuredWidth();
                    int n9 = view.getMeasuredHeight();
                    if (this.checkDrawerViewAbsoluteGravity(view, 3)) {
                        n7 = -n8 + (int)((float)n8 * layoutParams.onScreen);
                        f = (float)(n8 + n7) / (float)n8;
                    } else {
                        n7 = n5 - (int)((float)n8 * layoutParams.onScreen);
                        f = (float)(n5 - n7) / (float)n8;
                    }
                    boolean bl2 = f != layoutParams.onScreen;
                    switch (layoutParams.gravity & 0x70) {
                        default: {
                            view.layout(n7, layoutParams.topMargin, n7 + n8, layoutParams.topMargin + n9);
                            break;
                        }
                        case 80: {
                            n = n4 - n2;
                            view.layout(n7, n - layoutParams.bottomMargin - view.getMeasuredHeight(), n7 + n8, n - layoutParams.bottomMargin);
                            break;
                        }
                        case 16: {
                            int n10 = n4 - n2;
                            int n11 = (n10 - n9) / 2;
                            if (n11 < layoutParams.topMargin) {
                                n = layoutParams.topMargin;
                            } else {
                                n = n11;
                                if (n11 + n9 > n10 - layoutParams.bottomMargin) {
                                    n = n10 - layoutParams.bottomMargin - n9;
                                }
                            }
                            view.layout(n7, n, n7 + n8, n + n9);
                        }
                    }
                    if (bl2) {
                        this.setDrawerViewOffset(view, f);
                    }
                    n = layoutParams.onScreen > 0.0f ? 0 : 4;
                    if (view.getVisibility() != n) {
                        view.setVisibility(n);
                    }
                }
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        block31: {
            block30: {
                n5 = View.MeasureSpec.getMode((int)n);
                n7 = View.MeasureSpec.getMode((int)n2);
                n6 = View.MeasureSpec.getSize((int)n);
                n3 = View.MeasureSpec.getSize((int)n2);
                if (n5 != 1073741824) break block30;
                n8 = n3;
                n4 = n6;
                if (n7 == 1073741824) break block31;
            }
            if (!this.isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (n5 != Integer.MIN_VALUE && n5 == 0) {
                n6 = 300;
            }
            if (n7 == Integer.MIN_VALUE) {
                n4 = n6;
                n8 = n3;
            } else {
                n8 = n3;
                n4 = n6;
                if (n7 == 0) {
                    n8 = 300;
                    n4 = n6;
                }
            }
        }
        this.setMeasuredDimension(n4, n8);
        n7 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) ? 1 : 0;
        int n9 = ViewCompat.getLayoutDirection((View)this);
        n3 = 0;
        n6 = 0;
        int n10 = this.getChildCount();
        n5 = 0;
        while (n5 < n10) {
            View view = this.getChildAt(n5);
            if (view.getVisibility() != 8) {
                int n11;
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (n7 != 0) {
                    WindowInsets windowInsets;
                    WindowInsets windowInsets2;
                    n11 = GravityCompat.getAbsoluteGravity(layoutParams.gravity, n9);
                    if (ViewCompat.getFitsSystemWindows(view)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            windowInsets2 = (WindowInsets)this.mLastInsets;
                            if (n11 == 3) {
                                windowInsets = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), 0, windowInsets2.getSystemWindowInsetBottom());
                            } else {
                                windowInsets = windowInsets2;
                                if (n11 == 5) {
                                    windowInsets = windowInsets2.replaceSystemWindowInsets(0, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                                }
                            }
                            view.dispatchApplyWindowInsets(windowInsets);
                        }
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        windowInsets2 = (WindowInsets)this.mLastInsets;
                        if (n11 == 3) {
                            windowInsets = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), 0, windowInsets2.getSystemWindowInsetBottom());
                        } else {
                            windowInsets = windowInsets2;
                            if (n11 == 5) {
                                windowInsets = windowInsets2.replaceSystemWindowInsets(0, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                            }
                        }
                        layoutParams.leftMargin = windowInsets.getSystemWindowInsetLeft();
                        layoutParams.topMargin = windowInsets.getSystemWindowInsetTop();
                        layoutParams.rightMargin = windowInsets.getSystemWindowInsetRight();
                        layoutParams.bottomMargin = windowInsets.getSystemWindowInsetBottom();
                    }
                }
                if (this.isContentView(view)) {
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)(n4 - layoutParams.leftMargin - layoutParams.rightMargin), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n8 - layoutParams.topMargin - layoutParams.bottomMargin), (int)1073741824));
                } else {
                    int n12;
                    if (!this.isDrawerView(view)) {
                        throw new IllegalStateException("Child " + (Object)view + " at index " + n5 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                    }
                    if (SET_DRAWER_SHADOW_FROM_ELEVATION && ViewCompat.getElevation(view) != this.mDrawerElevation) {
                        ViewCompat.setElevation(view, this.mDrawerElevation);
                    }
                    if ((n11 = (n12 = this.getDrawerViewAbsoluteGravity(view) & 7) == 3 ? 1 : 0) != 0 && n3 != 0 || n11 == 0 && n6 != 0) {
                        throw new IllegalStateException("Child drawer has absolute gravity " + DrawerLayout.gravityToString(n12) + " but this " + "DrawerLayout" + " already has a " + "drawer view along that edge");
                    }
                    if (n11 != 0) {
                        n3 = 1;
                    } else {
                        n6 = 1;
                    }
                    view.measure(DrawerLayout.getChildMeasureSpec((int)n, (int)(this.mMinDrawerMargin + layoutParams.leftMargin + layoutParams.rightMargin), (int)layoutParams.width), DrawerLayout.getChildMeasureSpec((int)n2, (int)(layoutParams.topMargin + layoutParams.bottomMargin), (int)layoutParams.height));
                }
            }
            ++n5;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        } else {
            View view;
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(parcelable.getSuperState());
            if (parcelable.openDrawerGravity != 0 && (view = this.findDrawerWithGravity(parcelable.openDrawerGravity)) != null) {
                this.openDrawer(view);
            }
            if (parcelable.lockModeLeft != 3) {
                this.setDrawerLockMode(parcelable.lockModeLeft, 3);
            }
            if (parcelable.lockModeRight != 3) {
                this.setDrawerLockMode(parcelable.lockModeRight, 5);
            }
            if (parcelable.lockModeStart != 3) {
                this.setDrawerLockMode(parcelable.lockModeStart, 8388611);
            }
            if (parcelable.lockModeEnd == 3) return;
            {
                this.setDrawerLockMode(parcelable.lockModeEnd, 8388613);
                return;
            }
        }
    }

    public void onRtlPropertiesChanged(int n) {
        this.resolveShadowDrawables();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
            boolean bl = layoutParams.openState == 1;
            boolean bl2 = layoutParams.openState == 2;
            if (!bl && !bl2) continue;
            savedState.openDrawerGravity = layoutParams.gravity;
            break;
        }
        savedState.lockModeLeft = this.mLockModeLeft;
        savedState.lockModeRight = this.mLockModeRight;
        savedState.lockModeStart = this.mLockModeStart;
        savedState.lockModeEnd = this.mLockModeEnd;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mLeftDragger.processTouchEvent(motionEvent);
        this.mRightDragger.processTouchEvent(motionEvent);
        switch (motionEvent.getAction() & 0xFF) {
            default: {
                return true;
            }
            case 0: {
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                this.mInitialMotionX = f;
                this.mInitialMotionY = f2;
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                return true;
            }
            case 1: {
                float f = motionEvent.getX();
                float f3 = motionEvent.getY();
                boolean bl = true;
                motionEvent = this.mLeftDragger.findTopChildUnder((int)f, (int)f3);
                boolean bl2 = bl;
                if (motionEvent != null) {
                    bl2 = bl;
                    if (this.isContentView((View)motionEvent)) {
                        f -= this.mInitialMotionX;
                        f3 -= this.mInitialMotionY;
                        int n = this.mLeftDragger.getTouchSlop();
                        bl2 = bl;
                        if (f * f + f3 * f3 < (float)(n * n)) {
                            motionEvent = this.findOpenDrawer();
                            bl2 = bl;
                            if (motionEvent != null) {
                                bl2 = this.getDrawerLockMode((View)motionEvent) == 2;
                            }
                        }
                    }
                }
                this.closeDrawers(bl2);
                this.mDisallowInterceptRequested = false;
                return true;
            }
            case 3: 
        }
        this.closeDrawers(true);
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
    }

    public void openDrawer(int n) {
        this.openDrawer(n, true);
    }

    public void openDrawer(int n, boolean bl) {
        View view = this.findDrawerWithGravity(n);
        if (view == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + DrawerLayout.gravityToString(n));
        }
        this.openDrawer(view, bl);
    }

    public void openDrawer(View view) {
        this.openDrawer(view, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void openDrawer(View view, boolean bl) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.onScreen = 1.0f;
            layoutParams.openState = 1;
            this.updateChildrenImportantForAccessibility(view, true);
        } else if (bl) {
            layoutParams.openState |= 2;
            if (this.checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, 0, view.getTop());
            } else {
                this.mRightDragger.smoothSlideViewTo(view, this.getWidth() - view.getWidth(), view.getTop());
            }
        } else {
            this.moveDrawerToOffset(view, 1.0f);
            this.updateDrawerState(layoutParams.gravity, 0, view);
            view.setVisibility(0);
        }
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeDrawerListener(DrawerListener drawerListener) {
        if (drawerListener == null || this.mListeners == null) {
            return;
        }
        this.mListeners.remove(drawerListener);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        this.mDisallowInterceptRequested = bl;
        if (bl) {
            this.closeDrawers(true);
        }
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChildInsets(Object object, boolean bl) {
        this.mLastInsets = object;
        this.mDrawStatusBarBackground = bl;
        bl = !bl && this.getBackground() == null;
        this.setWillNotDraw(bl);
        this.requestLayout();
    }

    public void setDrawerElevation(float f) {
        this.mDrawerElevation = f;
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            if (!this.isDrawerView(view)) continue;
            ViewCompat.setElevation(view, this.mDrawerElevation);
        }
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        if (this.mListener != null) {
            this.removeDrawerListener(this.mListener);
        }
        if (drawerListener != null) {
            this.addDrawerListener(drawerListener);
        }
        this.mListener = drawerListener;
    }

    public void setDrawerLockMode(int n) {
        this.setDrawerLockMode(n, 3);
        this.setDrawerLockMode(n, 5);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawerLockMode(int n, int n2) {
        ViewDragHelper viewDragHelper;
        int n3 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this));
        switch (n2) {
            case 3: {
                this.mLockModeLeft = n;
                break;
            }
            case 5: {
                this.mLockModeRight = n;
                break;
            }
            case 8388611: {
                this.mLockModeStart = n;
                break;
            }
            case 8388613: {
                this.mLockModeEnd = n;
            }
        }
        if (n != 0) {
            viewDragHelper = n3 == 3 ? this.mLeftDragger : this.mRightDragger;
            viewDragHelper.cancel();
        }
        switch (n) {
            case 2: {
                viewDragHelper = this.findDrawerWithGravity(n3);
                if (viewDragHelper == null) return;
                {
                    this.openDrawer((View)viewDragHelper);
                    return;
                }
            }
            default: {
                return;
            }
            case 1: {
                viewDragHelper = this.findDrawerWithGravity(n3);
                if (viewDragHelper == null) return;
                this.closeDrawer((View)viewDragHelper);
                return;
            }
        }
    }

    public void setDrawerLockMode(int n, View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a " + "drawer with appropriate layout_gravity");
        }
        this.setDrawerLockMode(n, ((LayoutParams)view.getLayoutParams()).gravity);
    }

    public void setDrawerShadow(int n, int n2) {
        this.setDrawerShadow(ContextCompat.getDrawable(this.getContext(), n), n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawerShadow(Drawable drawable2, int n) {
        block4: {
            block7: {
                block2: {
                    block6: {
                        block5: {
                            block3: {
                                if (SET_DRAWER_SHADOW_FROM_ELEVATION) break block2;
                                if ((n & 0x800003) != 8388611) break block3;
                                this.mShadowStart = drawable2;
                                break block4;
                            }
                            if ((n & 0x800005) != 8388613) break block5;
                            this.mShadowEnd = drawable2;
                            break block4;
                        }
                        if ((n & 3) != 3) break block6;
                        this.mShadowLeft = drawable2;
                        break block4;
                    }
                    if ((n & 5) == 5) break block7;
                }
                return;
            }
            this.mShadowRight = drawable2;
        }
        this.resolveShadowDrawables();
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawerTitle(int n, CharSequence charSequence) {
        if ((n = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this))) == 3) {
            this.mTitleLeft = charSequence;
            return;
        } else {
            if (n != 5) return;
            {
                this.mTitleRight = charSequence;
                return;
            }
        }
    }

    void setDrawerViewOffset(View view, float f) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (f == layoutParams.onScreen) {
            return;
        }
        layoutParams.onScreen = f;
        this.dispatchOnDrawerSlide(view, f);
    }

    public void setScrimColor(int n) {
        this.mScrimColor = n;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarBackground(int n) {
        Drawable drawable2 = n != 0 ? ContextCompat.getDrawable(this.getContext(), n) : null;
        this.mStatusBarBackground = drawable2;
        this.invalidate();
    }

    public void setStatusBarBackground(Drawable drawable2) {
        this.mStatusBarBackground = drawable2;
        this.invalidate();
    }

    public void setStatusBarBackgroundColor(int n) {
        this.mStatusBarBackground = new ColorDrawable(n);
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateDrawerState(int n, int n2, View view) {
        n = this.mLeftDragger.getViewDragState();
        int n3 = this.mRightDragger.getViewDragState();
        n = n == 1 || n3 == 1 ? 1 : (n == 2 || n3 == 2 ? 2 : 0);
        if (view != null && n2 == 0) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.onScreen == 0.0f) {
                this.dispatchOnDrawerClosed(view);
            } else if (layoutParams.onScreen == 1.0f) {
                this.dispatchOnDrawerOpened(view);
            }
        }
        if (n != this.mDrawerState) {
            this.mDrawerState = n;
            if (this.mListeners != null) {
                for (n2 = this.mListeners.size() - 1; n2 >= 0; --n2) {
                    this.mListeners.get(n2).onDrawerStateChanged(n);
                }
            }
        }
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            int n = viewGroup.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = viewGroup.getChildAt(i);
                if (!DrawerLayout.includeChildForAccessibility(view)) continue;
                accessibilityNodeInfoCompat.addChild(view);
            }
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
        }

        @Override
        public boolean dispatchPopulateAccessibilityEvent(View object, AccessibilityEvent object2) {
            if (object2.getEventType() == 32) {
                object = object2.getText();
                object2 = DrawerLayout.this.findVisibleDrawer();
                if (object2 != null) {
                    int n = DrawerLayout.this.getDrawerViewAbsoluteGravity((View)object2);
                    object2 = DrawerLayout.this.getDrawerTitle(n);
                    if (object2 != null) {
                        object.add(object2);
                    }
                }
                return true;
            }
            return super.dispatchPopulateAccessibilityEvent((View)object, (AccessibilityEvent)object2);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)DrawerLayout.class.getName());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (CAN_HIDE_DESCENDANTS) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            } else {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
                accessibilityNodeInfoCompat.setSource(view);
                ViewParent viewParent = ViewCompat.getParentForAccessibility(view);
                if (viewParent instanceof View) {
                    accessibilityNodeInfoCompat.setParent((View)viewParent);
                }
                this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
                accessibilityNodeInfoCompat2.recycle();
                this.addChildrenForAccessibility(accessibilityNodeInfoCompat, (ViewGroup)view);
            }
            accessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
            accessibilityNodeInfoCompat.setFocusable(false);
            accessibilityNodeInfoCompat.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    static final class ChildAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        ChildAccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (!DrawerLayout.includeChildForAccessibility(view)) {
                accessibilityNodeInfoCompat.setParent(null);
            }
        }
    }

    public static interface DrawerListener {
        public void onDrawerClosed(View var1);

        public void onDrawerOpened(View var1);

        public void onDrawerSlide(View var1, float var2);

        public void onDrawerStateChanged(int var1);
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity = 0;
        boolean isPeeking;
        float onScreen;
        int openState;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = context.getInt(0, 0);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    protected static class SavedState
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
        int lockModeEnd;
        int lockModeLeft;
        int lockModeRight;
        int lockModeStart;
        int openDrawerGravity = 0;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.openDrawerGravity = parcel.readInt();
            this.lockModeLeft = parcel.readInt();
            this.lockModeRight = parcel.readInt();
            this.lockModeStart = parcel.readInt();
            this.lockModeEnd = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.openDrawerGravity);
            parcel.writeInt(this.lockModeLeft);
            parcel.writeInt(this.lockModeRight);
            parcel.writeInt(this.lockModeStart);
            parcel.writeInt(this.lockModeEnd);
        }

    }

    private class ViewDragCallback
    extends ViewDragHelper.Callback {
        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable = new Runnable(){

            @Override
            public void run() {
                ViewDragCallback.this.peekDrawer();
            }
        };

        ViewDragCallback(int n) {
            this.mAbsGravity = n;
        }

        private void closeOtherDrawer() {
            View view;
            int n = 3;
            if (this.mAbsGravity == 3) {
                n = 5;
            }
            if ((view = DrawerLayout.this.findDrawerWithGravity(n)) != null) {
                DrawerLayout.this.closeDrawer(view);
            }
        }

        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(n, 0));
            }
            n2 = DrawerLayout.this.getWidth();
            return Math.max(n2 - view.getWidth(), Math.min(n, n2));
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            if (DrawerLayout.this.isDrawerView(view)) {
                return view.getWidth();
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onEdgeDragStarted(int n, int n2) {
            View view = (n & 1) == 1 ? DrawerLayout.this.findDrawerWithGravity(3) : DrawerLayout.this.findDrawerWithGravity(5);
            if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.mDragger.captureChildView(view, n2);
            }
        }

        @Override
        public boolean onEdgeLock(int n) {
            return false;
        }

        @Override
        public void onEdgeTouched(int n, int n2) {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
        }

        @Override
        public void onViewCaptured(View view, int n) {
            ((LayoutParams)view.getLayoutParams()).isPeeking = false;
            this.closeOtherDrawer();
        }

        @Override
        public void onViewDragStateChanged(int n) {
            DrawerLayout.this.updateDrawerState(this.mAbsGravity, n, this.mDragger.getCapturedView());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            n2 = view.getWidth();
            float f = DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3) ? (float)(n2 + n) / (float)n2 : (float)(DrawerLayout.this.getWidth() - n) / (float)n2;
            DrawerLayout.this.setDrawerViewOffset(view, f);
            n = f == 0.0f ? 4 : 0;
            view.setVisibility(n);
            DrawerLayout.this.invalidate();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f, float f2) {
            int n;
            f2 = DrawerLayout.this.getDrawerViewOffset(view);
            int n2 = view.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                n = f > 0.0f || f == 0.0f && f2 > 0.5f ? 0 : -n2;
            } else {
                n = DrawerLayout.this.getWidth();
                if (f < 0.0f || f == 0.0f && f2 > 0.5f) {
                    n -= n2;
                }
            }
            this.mDragger.settleCapturedViewAt(n, view.getTop());
            DrawerLayout.this.invalidate();
        }

        /*
         * Enabled aggressive block sorting
         */
        void peekDrawer() {
            View view;
            int n = 0;
            int n2 = this.mDragger.getEdgeSize();
            boolean bl = this.mAbsGravity == 3;
            if (bl) {
                view = DrawerLayout.this.findDrawerWithGravity(3);
                if (view != null) {
                    n = -view.getWidth();
                }
                n += n2;
            } else {
                view = DrawerLayout.this.findDrawerWithGravity(5);
                n = DrawerLayout.this.getWidth() - n2;
            }
            if (view != null && (bl && view.getLeft() < n || !bl && view.getLeft() > n) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                this.mDragger.smoothSlideViewTo(view, n, view.getTop());
                layoutParams.isPeeking = true;
                DrawerLayout.this.invalidate();
                this.closeOtherDrawer();
                DrawerLayout.this.cancelChildViewTouch();
            }
        }

        public void removeCallbacks() {
            DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
        }

        public void setDragger(ViewDragHelper viewDragHelper) {
            this.mDragger = viewDragHelper;
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            return DrawerLayout.this.isDrawerView(view) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(view) == 0;
        }

    }

}

