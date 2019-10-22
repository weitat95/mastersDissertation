/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.AbsSavedState
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.design.R;
import android.support.design.widget.DirectedAcyclicGraph;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewGroupUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Pools;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
extends ViewGroup
implements NestedScrollingParent2 {
    static final Class<?>[] CONSTRUCTOR_PARAMS;
    static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    static final String WIDGET_PACKAGE_NAME;
    static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
    private static final Pools.Pool<Rect> sRectPool;
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private View mBehaviorTouchView;
    private final DirectedAcyclicGraph<View> mChildDag;
    private final List<View> mDependencySortedChildren = new ArrayList<View>();
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    private boolean mNeedsPreDrawListener;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private View mNestedScrollingTarget;
    ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList;
    private final int[] mTempIntPair;
    private final List<View> mTempList1;

    /*
     * Enabled aggressive block sorting
     */
    static {
        Object object = CoordinatorLayout.class.getPackage();
        object = object != null ? ((Package)object).getName() : null;
        WIDGET_PACKAGE_NAME = object;
        TOP_SORTED_CHILDREN_COMPARATOR = Build.VERSION.SDK_INT >= 21 ? new ViewElevationComparator() : null;
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal();
        sRectPool = new Pools.SynchronizedPool<Rect>(12);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mChildDag = new DirectedAcyclicGraph();
        this.mTempList1 = new ArrayList<View>();
        this.mTempDependenciesList = new ArrayList<View>();
        this.mTempIntPair = new int[2];
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        ThemeUtils.checkAppCompatTheme(context);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, n, R.style.Widget_Design_CoordinatorLayout);
        n = attributeSet.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
        if (n != 0) {
            context = context.getResources();
            this.mKeylines = context.getIntArray(n);
            float f = context.getDisplayMetrics().density;
            int n2 = this.mKeylines.length;
            for (n = 0; n < n2; ++n) {
                this.mKeylines[n] = (int)((float)this.mKeylines[n] * f);
            }
        }
        this.mStatusBarBackground = attributeSet.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
        attributeSet.recycle();
        this.setupForInsets();
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)new HierarchyChangeListener());
    }

    private static Rect acquireTempRect() {
        Rect rect;
        Rect rect2 = rect = sRectPool.acquire();
        if (rect == null) {
            rect2 = new Rect();
        }
        return rect2;
    }

    private void constrainChildRect(LayoutParams layoutParams, Rect rect, int n, int n2) {
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        n3 = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, n3 - this.getPaddingRight() - n - layoutParams.rightMargin));
        n4 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, n4 - this.getPaddingBottom() - n2 - layoutParams.bottomMargin));
        rect.set(n3, n4, n3 + n, n4 + n2);
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int n = 0;
        int n2 = this.getChildCount();
        do {
            WindowInsetsCompat windowInsetsCompat2;
            block6: {
                block5: {
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (n >= n2) break block5;
                    View view = this.getChildAt(n);
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (!ViewCompat.getFitsSystemWindows(view)) break block6;
                    Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (behavior == null) break block6;
                    windowInsetsCompat2 = windowInsetsCompat = behavior.onApplyWindowInsets(this, view, windowInsetsCompat);
                    if (!windowInsetsCompat.isConsumed()) break block6;
                    windowInsetsCompat2 = windowInsetsCompat;
                }
                return windowInsetsCompat2;
            }
            ++n;
            windowInsetsCompat = windowInsetsCompat2;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    private void getDesiredAnchoredChildRectWithoutConstraints(View var1_1, int var2_2, Rect var3_3, Rect var4_4, LayoutParams var5_5, int var6_6, int var7_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous, and can't clone.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:509)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private int getKeyline(int n) {
        if (this.mKeylines == null) {
            Log.e((String)"CoordinatorLayout", (String)("No keylines defined for " + this + " - attempted index lookup " + n));
            return 0;
        }
        if (n < 0 || n >= this.mKeylines.length) {
            Log.e((String)"CoordinatorLayout", (String)("Keyline index " + n + " out of range for " + this));
            return 0;
        }
        return this.mKeylines[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getTopSortedChildren(List<View> list) {
        list.clear();
        boolean bl = this.isChildrenDrawingOrderEnabled();
        int n = this.getChildCount();
        for (int i = n - 1; i >= 0; --i) {
            int n2 = bl ? this.getChildDrawingOrder(n, i) : i;
            list.add(this.getChildAt(n2));
        }
        if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
            Collections.sort(list, TOP_SORTED_CHILDREN_COMPARATOR);
        }
    }

    private boolean hasDependencies(View view) {
        return this.mChildDag.hasOutgoingEdges(view);
    }

    private void layoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = CoordinatorLayout.acquireTempRect();
        rect.set(this.getPaddingLeft() + layoutParams.leftMargin, this.getPaddingTop() + layoutParams.topMargin, this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin, this.getHeight() - this.getPaddingBottom() - layoutParams.bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += this.mLastInsets.getSystemWindowInsetLeft();
            rect.top += this.mLastInsets.getSystemWindowInsetTop();
            rect.right -= this.mLastInsets.getSystemWindowInsetRight();
            rect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        GravityCompat.apply(CoordinatorLayout.resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, n);
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
        CoordinatorLayout.releaseTempRect(rect);
        CoordinatorLayout.releaseTempRect(rect2);
    }

    private void layoutChildWithAnchor(View view, View view2, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        layoutParams = CoordinatorLayout.acquireTempRect();
        Rect rect = CoordinatorLayout.acquireTempRect();
        try {
            this.getDescendantRect(view2, (Rect)layoutParams);
            this.getDesiredAnchoredChildRect(view, n, (Rect)layoutParams, rect);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
            return;
        }
        finally {
            CoordinatorLayout.releaseTempRect((Rect)layoutParams);
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void layoutChildWithKeyline(View view, int n, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n2);
        int n4 = this.getWidth();
        int n5 = this.getHeight();
        int n6 = view.getMeasuredWidth();
        int n7 = view.getMeasuredHeight();
        int n8 = n;
        if (n2 == 1) {
            n8 = n4 - n;
        }
        n = this.getKeyline(n8) - n6;
        n2 = 0;
        switch (n3 & 7) {
            case 5: {
                n += n6;
            }
            default: {
                break;
            }
            case 1: {
                n += n6 / 2;
            }
        }
        switch (n3 & 0x70) {
            case 80: {
                n2 = 0 + n7;
            }
            default: {
                break;
            }
            case 16: {
                n2 = 0 + n7 / 2;
            }
        }
        n = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(n, n4 - this.getPaddingRight() - n6 - layoutParams.rightMargin));
        n2 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(n2, n5 - this.getPaddingBottom() - n7 - layoutParams.bottomMargin));
        view.layout(n, n2, n + n6, n2 + n7);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void offsetChildByInset(View view, Rect rect, int n) {
        int n2;
        int n3;
        if (!ViewCompat.isLaidOut(view) || view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Behavior behavior = layoutParams.getBehavior();
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        Rect rect3 = CoordinatorLayout.acquireTempRect();
        rect3.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        if (behavior != null && behavior.getInsetDodgeRect(this, view, rect2)) {
            if (!rect3.contains(rect2)) {
                throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + rect2.toShortString() + " | Bounds:" + rect3.toShortString());
            }
        } else {
            rect2.set(rect3);
        }
        CoordinatorLayout.releaseTempRect(rect3);
        if (rect2.isEmpty()) {
            CoordinatorLayout.releaseTempRect(rect2);
            return;
        }
        int n4 = GravityCompat.getAbsoluteGravity(layoutParams.dodgeInsetEdges, n);
        n = n3 = 0;
        if ((n4 & 0x30) == 48) {
            n2 = rect2.top - layoutParams.topMargin - layoutParams.mInsetOffsetY;
            n = n3;
            if (n2 < rect.top) {
                this.setInsetOffsetY(view, rect.top - n2);
                n = 1;
            }
        }
        n3 = n;
        if ((n4 & 0x50) == 80) {
            n2 = this.getHeight() - rect2.bottom - layoutParams.bottomMargin + layoutParams.mInsetOffsetY;
            n3 = n;
            if (n2 < rect.bottom) {
                this.setInsetOffsetY(view, n2 - rect.bottom);
                n3 = 1;
            }
        }
        if (n3 == 0) {
            this.setInsetOffsetY(view, 0);
        }
        n = n3 = 0;
        if ((n4 & 3) == 3) {
            n2 = rect2.left - layoutParams.leftMargin - layoutParams.mInsetOffsetX;
            n = n3;
            if (n2 < rect.left) {
                this.setInsetOffsetX(view, rect.left - n2);
                n = 1;
            }
        }
        n3 = n;
        if ((n4 & 5) == 5) {
            n4 = this.getWidth() - rect2.right - layoutParams.rightMargin + layoutParams.mInsetOffsetX;
            n3 = n;
            if (n4 < rect.right) {
                this.setInsetOffsetX(view, n4 - rect.right);
                n3 = 1;
            }
        }
        if (n3 == 0) {
            this.setInsetOffsetX(view, 0);
        }
        CoordinatorLayout.releaseTempRect(rect2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Behavior parseBehavior(Context object, AttributeSet attributeSet, String string2) {
        String string3;
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return null;
        }
        if (string3.startsWith(".")) {
            string3 = object.getPackageName() + string3;
        } else if (string3.indexOf(46) < 0 && !TextUtils.isEmpty((CharSequence)WIDGET_PACKAGE_NAME)) {
            string3 = WIDGET_PACKAGE_NAME + '.' + string3;
        }
        try {
            void var1_3;
            Constructor<?> constructor = sConstructors.get();
            Map<String, Constructor<Behavior>> map = constructor;
            if (constructor == null) {
                map = new HashMap<String, Constructor<Behavior>>();
                sConstructors.set(map);
            }
            Constructor<Behavior> constructor2 = map.get(string3);
            constructor = constructor2;
            if (constructor2 == null) {
                constructor = Class.forName(string3, true, object.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
                constructor.setAccessible(true);
                map.put(string3, constructor);
            }
            return (Behavior)constructor.newInstance(object, var1_3);
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not inflate Behavior subclass " + string3, exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performIntercept(MotionEvent motionEvent, int n) {
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = false;
        LayoutParams layoutParams = null;
        int n2 = motionEvent.getActionMasked();
        List<View> list = this.mTempList1;
        this.getTopSortedChildren(list);
        int n3 = list.size();
        int n4 = 0;
        do {
            boolean bl4;
            boolean bl5;
            bl = bl2;
            if (n4 >= n3) break;
            View view = list.get(n4);
            LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
            Behavior behavior = layoutParams2.getBehavior();
            if ((bl2 || bl3) && n2 != 0) {
                layoutParams2 = layoutParams;
                bl4 = bl2;
                bl5 = bl3;
                if (behavior != null) {
                    layoutParams2 = layoutParams;
                    if (layoutParams == null) {
                        long l = SystemClock.uptimeMillis();
                        layoutParams2 = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
                    }
                    switch (n) {
                        default: {
                            bl5 = bl3;
                            bl4 = bl2;
                            break;
                        }
                        case 0: {
                            behavior.onInterceptTouchEvent(this, view, (MotionEvent)layoutParams2);
                            bl4 = bl2;
                            bl5 = bl3;
                            break;
                        }
                        case 1: {
                            behavior.onTouchEvent(this, view, (MotionEvent)layoutParams2);
                            bl4 = bl2;
                            bl5 = bl3;
                            break;
                        }
                    }
                }
            } else {
                bl = bl2;
                if (!bl2) {
                    bl = bl2;
                    if (behavior != null) {
                        switch (n) {
                            case 0: {
                                bl2 = behavior.onInterceptTouchEvent(this, view, motionEvent);
                                break;
                            }
                            case 1: {
                                bl2 = behavior.onTouchEvent(this, view, motionEvent);
                            }
                        }
                        bl = bl2;
                        if (bl2) {
                            this.mBehaviorTouchView = view;
                            bl = bl2;
                        }
                    }
                }
                bl4 = layoutParams2.didBlockInteraction();
                bl2 = layoutParams2.isBlockingInteractionBelow(this, view);
                bl3 = bl2 && !bl4;
                layoutParams2 = layoutParams;
                bl4 = bl;
                bl5 = bl3;
                if (bl2) {
                    layoutParams2 = layoutParams;
                    bl4 = bl;
                    bl5 = bl3;
                    if (!bl3) break;
                }
            }
            ++n4;
            layoutParams = layoutParams2;
            bl2 = bl4;
            bl3 = bl5;
        } while (true);
        list.clear();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        this.mChildDag.clear();
        int n = 0;
        int n2 = this.getChildCount();
        do {
            if (n >= n2) {
                this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
                Collections.reverse(this.mDependencySortedChildren);
                return;
            }
            View view = this.getChildAt(n);
            LayoutParams layoutParams = this.getResolvedLayoutParams(view);
            layoutParams.findAnchorView(this, view);
            this.mChildDag.addNode(view);
            for (int i = 0; i < n2; ++i) {
                View view2;
                if (i == n || !layoutParams.dependsOn(this, view, view2 = this.getChildAt(i))) continue;
                if (!this.mChildDag.contains(view2)) {
                    this.mChildDag.addNode(view2);
                }
                this.mChildDag.addEdge(view2, view);
            }
            ++n;
        } while (true);
    }

    private static void releaseTempRect(Rect rect) {
        rect.setEmpty();
        sRectPool.release(rect);
    }

    private void resetTouchBehaviors() {
        if (this.mBehaviorTouchView != null) {
            Behavior behavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            if (behavior != null) {
                long l = SystemClock.uptimeMillis();
                MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
                behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent);
                motionEvent.recycle();
            }
            this.mBehaviorTouchView = null;
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            ((LayoutParams)this.getChildAt(i).getLayoutParams()).resetTouchBehaviorTracking();
        }
        this.mDisallowInterceptReset = false;
    }

    private static int resolveAnchoredChildGravity(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 17;
        }
        return n2;
    }

    private static int resolveGravity(int n) {
        int n2 = n;
        if ((n & 7) == 0) {
            n2 = n | 0x800003;
        }
        n = n2;
        if ((n2 & 0x70) == 0) {
            n = n2 | 0x30;
        }
        return n;
    }

    private static int resolveKeylineGravity(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 8388661;
        }
        return n2;
    }

    private void setInsetOffsetX(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetX != n) {
            ViewCompat.offsetLeftAndRight(view, n - layoutParams.mInsetOffsetX);
            layoutParams.mInsetOffsetX = n;
        }
    }

    private void setInsetOffsetY(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetY != n) {
            ViewCompat.offsetTopAndBottom(view, n - layoutParams.mInsetOffsetY);
            layoutParams.mInsetOffsetY = n;
        }
    }

    private void setupForInsets() {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (ViewCompat.getFitsSystemWindows((View)this)) {
            if (this.mApplyWindowInsetsListener == null) {
                this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener(){

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
                    }
                };
            }
            ViewCompat.setOnApplyWindowInsetsListener((View)this, this.mApplyWindowInsetsListener);
            this.setSystemUiVisibility(1280);
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, null);
    }

    void addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void dispatchDependentViewsChanged(View view) {
        List list = this.mChildDag.getIncomingEdges(view);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                View view2 = (View)list.get(i);
                Behavior behavior = ((LayoutParams)view2.getLayoutParams()).getBehavior();
                if (behavior == null) continue;
                behavior.onDependentViewChanged(this, view2, view);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean doViewsOverlap(View view, View view2) {
        boolean bl = true;
        if (view.getVisibility() != 0) return false;
        if (view2.getVisibility() != 0) {
            return false;
        }
        Rect rect = CoordinatorLayout.acquireTempRect();
        boolean bl2 = view.getParent() != this;
        this.getChildRect(view, bl2, rect);
        view = CoordinatorLayout.acquireTempRect();
        bl2 = view2.getParent() != this;
        this.getChildRect(view2, bl2, (Rect)view);
        try {
            int n;
            int n2;
            bl2 = rect.left <= view.right && rect.top <= view.bottom && rect.right >= view.left && (n2 = rect.bottom) >= (n = view.top) ? bl : false;
            return bl2;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
            CoordinatorLayout.releaseTempRect((Rect)view);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        float f;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mBehavior != null && (f = layoutParams.mBehavior.getScrimOpacity(this, view)) > 0.0f) {
            if (this.mScrimPaint == null) {
                this.mScrimPaint = new Paint();
            }
            this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view));
            this.mScrimPaint.setAlpha(MathUtils.clamp(Math.round(255.0f * f), 0, 255));
            int n = canvas.save();
            if (view.isOpaque()) {
                canvas.clipRect((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom(), Region.Op.DIFFERENCE);
            }
            canvas.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.mScrimPaint);
            canvas.restoreToCount(n);
        }
        return super.drawChild(canvas, view, l);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Drawable drawable2 = this.mStatusBarBackground;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        if (bl2) {
            this.invalidate();
        }
    }

    void ensurePreDrawListener() {
        boolean bl = false;
        int n = this.getChildCount();
        int n2 = 0;
        do {
            block6: {
                boolean bl2;
                block5: {
                    bl2 = bl;
                    if (n2 >= n) break block5;
                    if (!this.hasDependencies(this.getChildAt(n2))) break block6;
                    bl2 = true;
                }
                if (bl2 != this.mNeedsPreDrawListener) {
                    if (!bl2) break;
                    this.addPreDrawListener();
                }
                return;
            }
            ++n2;
        } while (true);
        this.removePreDrawListener();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    void getChildRect(View view, boolean bl, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
            return;
        }
        if (bl) {
            this.getDescendantRect(view, rect);
            return;
        }
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public List<View> getDependencies(View object) {
        object = this.mChildDag.getOutgoingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object != null) {
            this.mTempDependenciesList.addAll((Collection<View>)object);
        }
        return this.mTempDependenciesList;
    }

    final List<View> getDependencySortedChildren() {
        this.prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    public List<View> getDependents(View object) {
        object = this.mChildDag.getIncomingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object != null) {
            this.mTempDependenciesList.addAll((Collection<View>)object);
        }
        return this.mTempDependenciesList;
    }

    void getDescendantRect(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect);
    }

    void getDesiredAnchoredChildRect(View view, int n, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredWidth();
        int n3 = view.getMeasuredHeight();
        this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect, rect2, layoutParams, n2, n3);
        this.constrainChildRect(layoutParams, rect2, n2, n3);
    }

    void getLastChildRect(View view, Rect rect) {
        rect.set(((LayoutParams)view.getLayoutParams()).getLastChildRect());
    }

    final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    LayoutParams getResolvedLayoutParams(View object) {
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            DefaultBehavior defaultBehavior;
            Class<?> class_ = object.getClass();
            Object var1_2 = null;
            do {
                void var1_3;
                DefaultBehavior defaultBehavior2;
                defaultBehavior = var1_3;
                if (class_ == null) break;
                defaultBehavior = defaultBehavior2 = class_.getAnnotation(DefaultBehavior.class);
                if (defaultBehavior2 != null) break;
                class_ = class_.getSuperclass();
            } while (true);
            if (defaultBehavior != null) {
                try {
                    layoutParams.setBehavior(defaultBehavior.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
                catch (Exception exception) {
                    Log.e((String)"CoordinatorLayout", (String)("Default behavior class " + defaultBehavior.value().getName() + " could not be instantiated. Did you forget a default constructor?"), (Throwable)exception);
                }
            }
            layoutParams.mBehaviorResolved = true;
        }
        return layoutParams;
    }

    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
    }

    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
    }

    public boolean isPointInChildBounds(View view, int n, int n2) {
        Rect rect = CoordinatorLayout.acquireTempRect();
        this.getDescendantRect(view, rect);
        try {
            boolean bl = rect.contains(n, n2);
            return bl;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    void offsetChildToAnchor(View view, int n) {
        block6: {
            int n2;
            int n3;
            Rect rect;
            Behavior behavior;
            Rect rect2;
            LayoutParams layoutParams;
            int n4;
            Rect rect3;
            block8: {
                block7: {
                    n2 = 0;
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (layoutParams.mAnchorView == null) break block6;
                    rect3 = CoordinatorLayout.acquireTempRect();
                    rect2 = CoordinatorLayout.acquireTempRect();
                    rect = CoordinatorLayout.acquireTempRect();
                    this.getDescendantRect(layoutParams.mAnchorView, rect3);
                    this.getChildRect(view, false, rect2);
                    n3 = view.getMeasuredWidth();
                    n4 = view.getMeasuredHeight();
                    this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect3, rect, layoutParams, n3, n4);
                    if (rect.left != rect2.left) break block7;
                    n = n2;
                    if (rect.top == rect2.top) break block8;
                }
                n = 1;
            }
            this.constrainChildRect(layoutParams, rect, n3, n4);
            n2 = rect.left - rect2.left;
            n3 = rect.top - rect2.top;
            if (n2 != 0) {
                ViewCompat.offsetLeftAndRight(view, n2);
            }
            if (n3 != 0) {
                ViewCompat.offsetTopAndBottom(view, n3);
            }
            if (n != 0 && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onDependentViewChanged(this, view, layoutParams.mAnchorView);
            }
            CoordinatorLayout.releaseTempRect(rect3);
            CoordinatorLayout.releaseTempRect(rect2);
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.resetTouchBehaviors();
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows((View)this)) {
            ViewCompat.requestApplyInsets((View)this);
        }
        this.mIsAttachedToWindow = true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final void onChildViewsChanged(int var1_1) {
        var4_2 = ViewCompat.getLayoutDirection((View)this);
        var5_3 = this.mDependencySortedChildren.size();
        var7_4 = CoordinatorLayout.acquireTempRect();
        var8_5 = CoordinatorLayout.acquireTempRect();
        var9_6 = CoordinatorLayout.acquireTempRect();
        var2_7 = 0;
        block11: do {
            block24: {
                if (var2_7 >= var5_3) {
                    CoordinatorLayout.releaseTempRect(var7_4);
                    CoordinatorLayout.releaseTempRect(var8_5);
                    CoordinatorLayout.releaseTempRect(var9_6);
                    return;
                }
                var10_10 = this.mDependencySortedChildren.get(var2_7);
                var11_11 = (LayoutParams)var10_10.getLayoutParams();
                if (var1_1 == 0 && var10_10.getVisibility() == 8) ** GOTO lbl47
                for (var3_8 = 0; var3_8 < var2_7; ++var3_8) {
                    var12_12 /* !! */  = this.mDependencySortedChildren.get(var3_8);
                    if (var11_11.mAnchorDirectChild != var12_12 /* !! */ ) continue;
                    this.offsetChildToAnchor(var10_10, var4_2);
                }
                this.getChildRect(var10_10, true, var8_5);
                if (var11_11.insetEdge != 0 && !var8_5.isEmpty()) {
                    var3_8 = GravityCompat.getAbsoluteGravity(var11_11.insetEdge, var4_2);
                    switch (var3_8 & 112) {
                        case 48: {
                            var7_4.top = Math.max(var7_4.top, var8_5.bottom);
                            break;
                        }
                        case 80: {
                            var7_4.bottom = Math.max(var7_4.bottom, this.getHeight() - var8_5.top);
                            break;
                        }
                    }
                    switch (var3_8 & 7) {
                        case 3: {
                            var7_4.left = Math.max(var7_4.left, var8_5.right);
                            break;
                        }
                        case 5: {
                            var7_4.right = Math.max(var7_4.right, this.getWidth() - var8_5.left);
                        }
                    }
                }
                if (var11_11.dodgeInsetEdges != 0 && var10_10.getVisibility() == 0) {
                    this.offsetChildByInset(var10_10, var7_4, var4_2);
                }
                if (var1_1 == 2) break block24;
                this.getLastChildRect(var10_10, var9_6);
                if (var9_6.equals((Object)var8_5)) ** GOTO lbl47
                this.recordLastChildRect(var10_10, var8_5);
            }
            var3_8 = var2_7 + 1;
            do {
                block25: {
                    if (var3_8 < var5_3) break block25;
lbl47:
                    // 3 sources
                    ++var2_7;
                    continue block11;
                }
                var11_11 = this.mDependencySortedChildren.get(var3_8);
                var12_12 /* !! */  = (LayoutParams)var11_11.getLayoutParams();
                var13_13 = var12_12 /* !! */ .getBehavior();
                if (var13_13 != null && var13_13.layoutDependsOn(this, var11_11, var10_10)) {
                    if (var1_1 == 0 && var12_12 /* !! */ .getChangedAfterNestedScroll()) {
                        var12_12 /* !! */ .resetChangedAfterNestedScroll();
                    } else {
                        switch (var1_1) {
                            default: {
                                var6_9 = var13_13.onDependentViewChanged(this, var11_11, var10_10);
                                break;
                            }
                            case 2: {
                                var13_13.onDependentViewRemoved(this, var11_11, var10_10);
                                var6_9 = true;
                            }
                        }
                        if (var1_1 == 1) {
                            var12_12 /* !! */ .setChangedAfterNestedScroll(var6_9);
                        }
                    }
                }
                ++var3_8;
            } while (true);
            break;
        } while (true);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.resetTouchBehaviors();
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mNestedScrollingTarget != null) {
            this.onStopNestedScroll(this.mNestedScrollingTarget);
        }
        this.mIsAttachedToWindow = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDraw(Canvas canvas) {
        int n;
        super.onDraw(canvas);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null && (n = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0) > 0) {
            this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), n);
            this.mStatusBarBackground.draw(canvas);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.resetTouchBehaviors();
        }
        boolean bl = this.performIntercept(motionEvent, 0);
        if (false) {
            throw new NullPointerException();
        }
        if (n == 1 || n == 3) {
            this.resetTouchBehaviors();
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = ViewCompat.getLayoutDirection((View)this);
        n3 = this.mDependencySortedChildren.size();
        n = 0;
        while (n < n3) {
            Behavior behavior;
            View view = this.mDependencySortedChildren.get(n);
            if (!(view.getVisibility() == 8 || (behavior = ((LayoutParams)view.getLayoutParams()).getBehavior()) != null && behavior.onLayoutChild(this, view, n2))) {
                this.onLayoutChild(view, n2);
            }
            ++n;
        }
        return;
    }

    public void onLayoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.checkAnchorChanged()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        if (layoutParams.mAnchorView != null) {
            this.layoutChildWithAnchor(view, layoutParams.mAnchorView, n);
            return;
        }
        if (layoutParams.keyline >= 0) {
            this.layoutChildWithKeyline(view, layoutParams.keyline, n);
            return;
        }
        this.layoutChild(view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        this.prepareChildren();
        this.ensurePreDrawListener();
        int n3 = this.getPaddingLeft();
        int n4 = this.getPaddingTop();
        int n5 = this.getPaddingRight();
        int n6 = this.getPaddingBottom();
        int n7 = ViewCompat.getLayoutDirection((View)this);
        boolean bl = n7 == 1;
        int n8 = View.MeasureSpec.getMode((int)n);
        int n9 = View.MeasureSpec.getSize((int)n);
        int n10 = View.MeasureSpec.getMode((int)n2);
        int n11 = View.MeasureSpec.getSize((int)n2);
        int n12 = this.getSuggestedMinimumWidth();
        int n13 = this.getSuggestedMinimumHeight();
        int n14 = 0;
        boolean bl2 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this);
        int n15 = this.mDependencySortedChildren.size();
        int n16 = 0;
        do {
            block8: {
                LayoutParams layoutParams;
                int n17;
                int n18;
                View view;
                int n19;
                int n20;
                Behavior behavior;
                block9: {
                    block11: {
                        block10: {
                            if (n16 >= n15) {
                                this.setMeasuredDimension(View.resolveSizeAndState((int)n12, (int)n, (int)(0xFF000000 & n14)), View.resolveSizeAndState((int)n13, (int)n2, (int)(n14 << 16)));
                                return;
                            }
                            view = this.mDependencySortedChildren.get(n16);
                            if (view.getVisibility() == 8) break block8;
                            layoutParams = (LayoutParams)view.getLayoutParams();
                            n17 = n19 = 0;
                            if (layoutParams.keyline < 0) break block9;
                            n17 = n19;
                            if (n8 == 0) break block9;
                            n18 = this.getKeyline(layoutParams.keyline);
                            n20 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n7) & 7;
                            if ((n20 != 3 || bl) && (n20 != 5 || !bl)) break block10;
                            n17 = Math.max(0, n9 - n5 - n18);
                            break block9;
                        }
                        if (n20 == 5 && !bl) break block11;
                        n17 = n19;
                        if (n20 != 3) break block9;
                        n17 = n19;
                        if (!bl) break block9;
                    }
                    n17 = Math.max(0, n18 - n3);
                }
                n18 = n;
                n20 = n2;
                int n21 = n18;
                n19 = n20;
                if (bl2) {
                    n21 = n18;
                    n19 = n20;
                    if (!ViewCompat.getFitsSystemWindows(view)) {
                        n20 = this.mLastInsets.getSystemWindowInsetLeft();
                        n21 = this.mLastInsets.getSystemWindowInsetRight();
                        n19 = this.mLastInsets.getSystemWindowInsetTop();
                        n18 = this.mLastInsets.getSystemWindowInsetBottom();
                        n21 = View.MeasureSpec.makeMeasureSpec((int)(n9 - (n20 + n21)), (int)n8);
                        n19 = View.MeasureSpec.makeMeasureSpec((int)(n11 - (n19 + n18)), (int)n10);
                    }
                }
                if ((behavior = layoutParams.getBehavior()) == null || !behavior.onMeasureChild(this, view, n21, n17, n19, 0)) {
                    this.onMeasureChild(view, n21, n17, n19, 0);
                }
                n12 = Math.max(n12, view.getMeasuredWidth() + (n3 + n5) + layoutParams.leftMargin + layoutParams.rightMargin);
                n13 = Math.max(n13, view.getMeasuredHeight() + (n4 + n6) + layoutParams.topMargin + layoutParams.bottomMargin);
                n14 = View.combineMeasuredStates((int)n14, (int)view.getMeasuredState());
            }
            ++n16;
        } while (true);
    }

    public void onMeasureChild(View view, int n, int n2, int n3, int n4) {
        this.measureChildWithMargins(view, n, n2, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        boolean bl2 = false;
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            boolean bl3;
            View view2 = this.getChildAt(i);
            if (view2.getVisibility() == 8) {
                bl3 = bl2;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                bl3 = bl2;
                if (((LayoutParams)((Object)object)).isNestedScrollAccepted(0)) {
                    object = ((LayoutParams)((Object)object)).getBehavior();
                    bl3 = bl2;
                    if (object != null) {
                        bl3 = bl2 | ((Behavior)object).onNestedFling(this, view2, view, f, f2, bl);
                    }
                }
            }
            bl2 = bl3;
        }
        if (bl2) {
            this.onChildViewsChanged(1);
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        boolean bl = false;
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            boolean bl2;
            View view2 = this.getChildAt(n2);
            if (view2.getVisibility() == 8) {
                bl2 = bl;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                bl2 = bl;
                if (((LayoutParams)((Object)object)).isNestedScrollAccepted(0)) {
                    object = ((LayoutParams)((Object)object)).getBehavior();
                    bl2 = bl;
                    if (object != null) {
                        bl2 = bl | ((Behavior)object).onNestedPreFling(this, view2, view, f, f2);
                    }
                }
            }
            ++n2;
            bl = bl2;
        }
        return bl;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.onNestedPreScroll(view, n, n2, arrn, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn, int n3) {
        int n4 = 0;
        int n5 = 0;
        boolean bl = false;
        int n6 = this.getChildCount();
        for (int i = 0; i < n6; ++i) {
            int n7;
            int n8;
            boolean bl2;
            View view2 = this.getChildAt(i);
            if (view2.getVisibility() == 8) {
                n8 = n5;
                n7 = n4;
                bl2 = bl;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                bl2 = bl;
                n7 = n4;
                n8 = n5;
                if (((LayoutParams)((Object)object)).isNestedScrollAccepted(n3)) {
                    object = ((LayoutParams)((Object)object)).getBehavior();
                    bl2 = bl;
                    n7 = n4;
                    n8 = n5;
                    if (object != null) {
                        int[] arrn2 = this.mTempIntPair;
                        this.mTempIntPair[1] = 0;
                        arrn2[0] = 0;
                        ((Behavior)object).onNestedPreScroll(this, view2, view, n, n2, this.mTempIntPair, n3);
                        n4 = n > 0 ? Math.max(n4, this.mTempIntPair[0]) : Math.min(n4, this.mTempIntPair[0]);
                        n5 = n2 > 0 ? Math.max(n5, this.mTempIntPair[1]) : Math.min(n5, this.mTempIntPair[1]);
                        bl2 = true;
                        n7 = n4;
                        n8 = n5;
                    }
                }
            }
            bl = bl2;
            n4 = n7;
            n5 = n8;
        }
        arrn[0] = n4;
        arrn[1] = n5;
        if (bl) {
            this.onChildViewsChanged(1);
        }
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.onNestedScroll(view, n, n2, n3, n4, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
        int n6 = this.getChildCount();
        boolean bl = false;
        for (int i = 0; i < n6; ++i) {
            boolean bl2;
            View view2 = this.getChildAt(i);
            if (view2.getVisibility() == 8) {
                bl2 = bl;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                bl2 = bl;
                if (((LayoutParams)((Object)object)).isNestedScrollAccepted(n5)) {
                    object = ((LayoutParams)((Object)object)).getBehavior();
                    bl2 = bl;
                    if (object != null) {
                        ((Behavior)object).onNestedScroll(this, view2, view, n, n2, n3, n4, n5);
                        bl2 = true;
                    }
                }
            }
            bl = bl2;
        }
        if (bl) {
            this.onChildViewsChanged(1);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.onNestedScrollAccepted(view, view2, n, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n, n2);
        this.mNestedScrollingTarget = view2;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            Behavior behavior;
            View view3 = this.getChildAt(n4);
            LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted(n2) && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onNestedScrollAccepted(this, view3, view, view2, n, n2);
            }
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        } else {
            object = (SavedState)object;
            super.onRestoreInstanceState(((AbsSavedState)object).getSuperState());
            object = ((SavedState)object).behaviorStates;
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                Parcelable parcelable;
                View view = this.getChildAt(i);
                int n2 = view.getId();
                Behavior behavior = this.getResolvedLayoutParams(view).getBehavior();
                if (n2 == -1 || behavior == null || (parcelable = (Parcelable)object.get(n2)) == null) continue;
                behavior.onRestoreInstanceState(this, view, parcelable);
            }
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            int n2 = view.getId();
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (n2 == -1 || behavior == null || (view = behavior.onSaveInstanceState(this, view)) == null) continue;
            sparseArray.append(n2, (Object)view);
        }
        savedState.behaviorStates = sparseArray;
        return savedState;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return this.onStartNestedScroll(view, view2, n, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onStartNestedScroll(View view, View view2, int n, int n2) {
        boolean bl = false;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            View view3 = this.getChildAt(n4);
            if (view3.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    boolean bl2 = behavior.onStartNestedScroll(this, view3, view, view2, n, n2);
                    bl |= bl2;
                    layoutParams.setNestedScrollAccepted(n2, bl2);
                } else {
                    layoutParams.setNestedScrollAccepted(n2, false);
                }
            }
            ++n4;
        }
        return bl;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.onStopNestedScroll(view, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onStopNestedScroll(View view, int n) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, n);
        int n2 = this.getChildCount();
        int n3 = 0;
        do {
            if (n3 >= n2) {
                this.mNestedScrollingTarget = null;
                return;
            }
            View view2 = this.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted(n)) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, view2, view, n);
                }
                layoutParams.resetNestedScroll(n);
                layoutParams.resetChangedAfterNestedScroll();
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        Object var9_4;
        int n;
        boolean bl3;
        Object var10_5;
        block12: {
            boolean bl4;
            block11: {
                bl4 = false;
                bl3 = false;
                var9_4 = null;
                var10_5 = null;
                n = motionEvent.getActionMasked();
                if (this.mBehaviorTouchView != null) break block11;
                bl2 = bl3 = this.performIntercept(motionEvent, 1);
                bl = bl4;
                if (!bl3) break block12;
            }
            Behavior behavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            bl2 = bl3;
            bl = bl4;
            if (behavior != null) {
                bl = behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent);
                bl2 = bl3;
            }
        }
        if (this.mBehaviorTouchView == null) {
            bl3 = bl | super.onTouchEvent(motionEvent);
            motionEvent = var10_5;
        } else {
            motionEvent = var10_5;
            bl3 = bl;
            if (bl2) {
                motionEvent = var9_4;
                if (!false) {
                    long l = SystemClock.uptimeMillis();
                    motionEvent = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
                }
                super.onTouchEvent(motionEvent);
                bl3 = bl;
            }
        }
        if (bl3 || n == 0) {
            // empty if block
        }
        if (motionEvent != null) {
            motionEvent.recycle();
        }
        if (n == 1 || n == 3) {
            this.resetTouchBehaviors();
        }
        return bl3;
    }

    void recordLastChildRect(View view, Rect rect) {
        ((LayoutParams)view.getLayoutParams()).setLastChildRect(rect);
    }

    void removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
        if (behavior != null && behavior.onRequestChildRectangleOnScreen(this, view, rect, bl)) {
            return true;
        }
        return super.requestChildRectangleOnScreen(view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        if (bl && !this.mDisallowInterceptReset) {
            this.resetTouchBehaviors();
            this.mDisallowInterceptReset = true;
        }
    }

    public void setFitsSystemWindows(boolean bl) {
        super.setFitsSystemWindows(bl);
        this.setupForInsets();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarBackground(Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mStatusBarBackground == drawable2) return;
        if (this.mStatusBarBackground != null) {
            this.mStatusBarBackground.setCallback(null);
        }
        if (drawable2 != null) {
            drawable3 = drawable2.mutate();
        }
        this.mStatusBarBackground = drawable3;
        if (this.mStatusBarBackground != null) {
            if (this.mStatusBarBackground.isStateful()) {
                this.mStatusBarBackground.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.mStatusBarBackground;
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
            this.mStatusBarBackground.setCallback((Drawable.Callback)this);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarBackgroundColor(int n) {
        this.setStatusBarBackground((Drawable)new ColorDrawable(n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarBackgroundResource(int n) {
        Drawable drawable2 = n != 0 ? ContextCompat.getDrawable(this.getContext(), n) : null;
        this.setStatusBarBackground(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        if (this.mStatusBarBackground != null && this.mStatusBarBackground.isVisible() != bl) {
            this.mStatusBarBackground.setVisible(bl, false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        boolean bl = true;
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
        if (!ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) {
            this.mLastInsets = windowInsetsCompat;
            boolean bl2 = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
            this.mDrawStatusBarBackground = bl2;
            bl2 = !this.mDrawStatusBarBackground && this.getBackground() == null ? bl : false;
            this.setWillNotDraw(bl2);
            windowInsetsCompat2 = this.dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat);
            this.requestLayout();
        }
        return windowInsetsCompat2;
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mStatusBarBackground;
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public static Object getTag(View view) {
            return ((LayoutParams)view.getLayoutParams()).mBehaviorTag;
        }

        public static void setTag(View view, Object object) {
            ((LayoutParams)view.getLayoutParams()).mBehaviorTag = object;
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
            return this.getScrimOpacity(coordinatorLayout, v) > 0.0f;
        }

        public boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, V v, Rect rect) {
            return false;
        }

        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
            return -16777216;
        }

        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
            return 0.0f;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onAttachedToLayoutParams(LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3, int n4) {
            return false;
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean bl) {
            return false;
        }

        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
            return false;
        }

        @Deprecated
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn) {
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn, int n3) {
            if (n3 == 0) {
                this.onNestedPreScroll(coordinatorLayout, v, view, n, n2, arrn);
            }
        }

        @Deprecated
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4) {
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4, int n5) {
            if (n5 == 0) {
                this.onNestedScroll(coordinatorLayout, v, view, n, n2, n3, n4);
            }
        }

        @Deprecated
        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
        }

        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n, int n2) {
            if (n2 == 0) {
                this.onNestedScrollAccepted(coordinatorLayout, v, view, view2, n);
            }
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean bl) {
            return false;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
            return false;
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n, int n2) {
            if (n2 == 0) {
                return this.onStartNestedScroll(coordinatorLayout, v, view, view2, n);
            }
            return false;
        }

        @Deprecated
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n) {
            if (n == 0) {
                this.onStopNestedScroll(coordinatorLayout, v, view);
            }
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface DefaultBehavior {
        public Class<? extends Behavior> value();
    }

    private class HierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int anchorGravity = 0;
        public int dodgeInsetEdges = 0;
        public int gravity = 0;
        public int insetEdge = 0;
        public int keyline = -1;
        View mAnchorDirectChild;
        int mAnchorId = -1;
        View mAnchorView;
        Behavior mBehavior;
        boolean mBehaviorResolved = false;
        Object mBehaviorTag;
        private boolean mDidAcceptNestedScrollNonTouch;
        private boolean mDidAcceptNestedScrollTouch;
        private boolean mDidBlockInteraction;
        private boolean mDidChangeAfterNestedScroll;
        int mInsetOffsetX;
        int mInsetOffsetY;
        final Rect mLastChildRect = new Rect();

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
            this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.insetEdge = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.dodgeInsetEdges = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.mBehaviorResolved = typedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
            if (this.mBehaviorResolved) {
                this.mBehavior = CoordinatorLayout.parseBehavior(context, attributeSet, typedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            typedArray.recycle();
            if (this.mBehavior != null) {
                this.mBehavior.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            this.mAnchorView = coordinatorLayout.findViewById(this.mAnchorId);
            if (this.mAnchorView != null) {
                if (this.mAnchorView == coordinatorLayout) {
                    if (coordinatorLayout.isInEditMode()) {
                        this.mAnchorDirectChild = null;
                        this.mAnchorView = null;
                        return;
                    }
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
                View view2 = this.mAnchorView;
                for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                    if (viewParent == view) {
                        if (coordinatorLayout.isInEditMode()) {
                            this.mAnchorDirectChild = null;
                            this.mAnchorView = null;
                            return;
                        }
                        throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                    }
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                }
                this.mAnchorDirectChild = view2;
                return;
            }
            if (coordinatorLayout.isInEditMode()) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return;
            }
            throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.mAnchorId) + " to anchor view " + (Object)view);
        }

        private boolean shouldDodge(View view, int n) {
            int n2 = GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).insetEdge, n);
            return n2 != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, n) & n2) == n2;
        }

        private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false;
            }
            View view2 = this.mAnchorView;
            for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout; viewParent = viewParent.getParent()) {
                if (viewParent == null || viewParent == view) {
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return false;
                }
                if (!(viewParent instanceof View)) continue;
                view2 = (View)viewParent;
            }
            this.mAnchorDirectChild = view2;
            return true;
        }

        boolean checkAnchorChanged() {
            return this.mAnchorView == null && this.mAnchorId != -1;
        }

        boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 == this.mAnchorDirectChild || this.shouldDodge(view2, ViewCompat.getLayoutDirection((View)coordinatorLayout)) || this.mBehavior != null && this.mBehavior.layoutDependsOn(coordinatorLayout, view, view2);
        }

        boolean didBlockInteraction() {
            if (this.mBehavior == null) {
                this.mDidBlockInteraction = false;
            }
            return this.mDidBlockInteraction;
        }

        View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mAnchorId == -1) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return null;
            }
            if (this.mAnchorView == null || !this.verifyAnchorView(view, coordinatorLayout)) {
                this.resolveAnchorView(view, coordinatorLayout);
            }
            return this.mAnchorView;
        }

        public int getAnchorId() {
            return this.mAnchorId;
        }

        public Behavior getBehavior() {
            return this.mBehavior;
        }

        boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll;
        }

        Rect getLastChildRect() {
            return this.mLastChildRect;
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mDidBlockInteraction) {
                return true;
            }
            boolean bl = this.mDidBlockInteraction;
            boolean bl2 = this.mBehavior != null ? this.mBehavior.blocksInteractionBelow(coordinatorLayout, view) : false;
            this.mDidBlockInteraction = bl2 |= bl;
            return bl2;
        }

        boolean isNestedScrollAccepted(int n) {
            switch (n) {
                default: {
                    return false;
                }
                case 0: {
                    return this.mDidAcceptNestedScrollTouch;
                }
                case 1: 
            }
            return this.mDidAcceptNestedScrollNonTouch;
        }

        void resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false;
        }

        void resetNestedScroll(int n) {
            this.setNestedScrollAccepted(n, false);
        }

        void resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false;
        }

        public void setBehavior(Behavior behavior) {
            if (this.mBehavior != behavior) {
                if (this.mBehavior != null) {
                    this.mBehavior.onDetachedFromLayoutParams();
                }
                this.mBehavior = behavior;
                this.mBehaviorTag = null;
                this.mBehaviorResolved = true;
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this);
                }
            }
        }

        void setChangedAfterNestedScroll(boolean bl) {
            this.mDidChangeAfterNestedScroll = bl;
        }

        void setLastChildRect(Rect rect) {
            this.mLastChildRect.set(rect);
        }

        void setNestedScrollAccepted(int n, boolean bl) {
            switch (n) {
                default: {
                    return;
                }
                case 0: {
                    this.mDidAcceptNestedScrollTouch = bl;
                    return;
                }
                case 1: 
            }
            this.mDidAcceptNestedScrollNonTouch = bl;
        }
    }

    class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
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
        SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel arrparcelable, ClassLoader classLoader) {
            super((Parcel)arrparcelable, classLoader);
            int n = arrparcelable.readInt();
            int[] arrn = new int[n];
            arrparcelable.readIntArray(arrn);
            arrparcelable = arrparcelable.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray(n);
            for (int i = 0; i < n; ++i) {
                this.behaviorStates.append(arrn[i], (Object)arrparcelable[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            int n2 = this.behaviorStates != null ? this.behaviorStates.size() : 0;
            parcel.writeInt(n2);
            int[] arrn = new int[n2];
            Parcelable[] arrparcelable = new Parcelable[n2];
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    parcel.writeIntArray(arrn);
                    parcel.writeParcelableArray(arrparcelable, n);
                    return;
                }
                arrn[n3] = this.behaviorStates.keyAt(n3);
                arrparcelable[n3] = (Parcelable)this.behaviorStates.valueAt(n3);
                ++n3;
            } while (true);
        }

    }

    static class ViewElevationComparator
    implements Comparator<View> {
        ViewElevationComparator() {
        }

        @Override
        public int compare(View view, View view2) {
            float f;
            float f2 = ViewCompat.getZ(view);
            if (f2 > (f = ViewCompat.getZ(view2))) {
                return -1;
            }
            if (f2 < f) {
                return 1;
            }
            return 0;
        }
    }

}

