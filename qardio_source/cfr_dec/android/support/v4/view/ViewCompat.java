/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 *  android.view.Display
 *  android.view.PointerIcon
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.WindowInsets
 *  android.view.WindowManager
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v4.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class ViewCompat {
    static final ViewCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 26 ? new ViewCompatApi26Impl() : (Build.VERSION.SDK_INT >= 24 ? new ViewCompatApi24Impl() : (Build.VERSION.SDK_INT >= 23 ? new ViewCompatApi23Impl() : (Build.VERSION.SDK_INT >= 21 ? new ViewCompatApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new ViewCompatApi19Impl() : (Build.VERSION.SDK_INT >= 18 ? new ViewCompatApi18Impl() : (Build.VERSION.SDK_INT >= 17 ? new ViewCompatApi17Impl() : (Build.VERSION.SDK_INT >= 16 ? new ViewCompatApi16Impl() : (Build.VERSION.SDK_INT >= 15 ? new ViewCompatApi15Impl() : new ViewCompatBaseImpl()))))))));

    public static ViewPropertyAnimatorCompat animate(View view) {
        return IMPL.animate(view);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.dispatchApplyWindowInsets(view, windowInsetsCompat);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return IMPL.getBackgroundTintList(view);
    }

    public static PorterDuff.Mode getBackgroundTintMode(View view) {
        return IMPL.getBackgroundTintMode(view);
    }

    public static Rect getClipBounds(View view) {
        return IMPL.getClipBounds(view);
    }

    public static Display getDisplay(View view) {
        return IMPL.getDisplay(view);
    }

    public static float getElevation(View view) {
        return IMPL.getElevation(view);
    }

    public static boolean getFitsSystemWindows(View view) {
        return IMPL.getFitsSystemWindows(view);
    }

    public static int getImportantForAccessibility(View view) {
        return IMPL.getImportantForAccessibility(view);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.getLayoutDirection(view);
    }

    public static int getMinimumHeight(View view) {
        return IMPL.getMinimumHeight(view);
    }

    public static int getMinimumWidth(View view) {
        return IMPL.getMinimumWidth(view);
    }

    public static int getPaddingEnd(View view) {
        return IMPL.getPaddingEnd(view);
    }

    public static int getPaddingStart(View view) {
        return IMPL.getPaddingStart(view);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return IMPL.getParentForAccessibility(view);
    }

    public static String getTransitionName(View view) {
        return IMPL.getTransitionName(view);
    }

    @Deprecated
    public static float getTranslationY(View view) {
        return view.getTranslationY();
    }

    public static float getTranslationZ(View view) {
        return IMPL.getTranslationZ(view);
    }

    public static int getWindowSystemUiVisibility(View view) {
        return IMPL.getWindowSystemUiVisibility(view);
    }

    public static float getZ(View view) {
        return IMPL.getZ(view);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return IMPL.hasAccessibilityDelegate(view);
    }

    public static boolean hasOnClickListeners(View view) {
        return IMPL.hasOnClickListeners(view);
    }

    public static boolean hasOverlappingRendering(View view) {
        return IMPL.hasOverlappingRendering(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.hasTransientState(view);
    }

    public static boolean isAttachedToWindow(View view) {
        return IMPL.isAttachedToWindow(view);
    }

    public static boolean isLaidOut(View view) {
        return IMPL.isLaidOut(view);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        return IMPL.isNestedScrollingEnabled(view);
    }

    public static boolean isPaddingRelative(View view) {
        return IMPL.isPaddingRelative(view);
    }

    public static void offsetLeftAndRight(View view, int n) {
        IMPL.offsetLeftAndRight(view, n);
    }

    public static void offsetTopAndBottom(View view, int n) {
        IMPL.offsetTopAndBottom(view, n);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.onApplyWindowInsets(view, windowInsetsCompat);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        IMPL.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    public static boolean performAccessibilityAction(View view, int n, Bundle bundle) {
        return IMPL.performAccessibilityAction(view, n, bundle);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    public static void postInvalidateOnAnimation(View view, int n, int n2, int n3, int n4) {
        IMPL.postInvalidateOnAnimation(view, n, n2, n3, n4);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.postOnAnimation(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long l) {
        IMPL.postOnAnimationDelayed(view, runnable, l);
    }

    public static void requestApplyInsets(View view) {
        IMPL.requestApplyInsets(view);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        IMPL.setAccessibilityDelegate(view, accessibilityDelegateCompat);
    }

    public static void setAccessibilityLiveRegion(View view, int n) {
        IMPL.setAccessibilityLiveRegion(view, n);
    }

    public static void setBackground(View view, Drawable drawable2) {
        IMPL.setBackground(view, drawable2);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        IMPL.setBackgroundTintList(view, colorStateList);
    }

    public static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        IMPL.setBackgroundTintMode(view, mode);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean bl) {
        IMPL.setChildrenDrawingOrderEnabled(viewGroup, bl);
    }

    public static void setClipBounds(View view, Rect rect) {
        IMPL.setClipBounds(view, rect);
    }

    public static void setElevation(View view, float f) {
        IMPL.setElevation(view, f);
    }

    @Deprecated
    public static void setFitsSystemWindows(View view, boolean bl) {
        view.setFitsSystemWindows(bl);
    }

    public static void setHasTransientState(View view, boolean bl) {
        IMPL.setHasTransientState(view, bl);
    }

    public static void setImportantForAccessibility(View view, int n) {
        IMPL.setImportantForAccessibility(view, n);
    }

    public static void setLayerPaint(View view, Paint paint) {
        IMPL.setLayerPaint(view, paint);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        IMPL.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
    }

    public static void setPaddingRelative(View view, int n, int n2, int n3, int n4) {
        IMPL.setPaddingRelative(view, n, n2, n3, n4);
    }

    public static void setPointerIcon(View view, PointerIconCompat pointerIconCompat) {
        IMPL.setPointerIcon(view, pointerIconCompat);
    }

    public static void setScrollIndicators(View view, int n, int n2) {
        IMPL.setScrollIndicators(view, n, n2);
    }

    public static void setTranslationZ(View view, float f) {
        IMPL.setTranslationZ(view, f);
    }

    public static void stopNestedScroll(View view) {
        IMPL.stopNestedScroll(view);
    }

    static class ViewCompatApi15Impl
    extends ViewCompatBaseImpl {
        ViewCompatApi15Impl() {
        }

        @Override
        public boolean hasOnClickListeners(View view) {
            return view.hasOnClickListeners();
        }
    }

    static class ViewCompatApi16Impl
    extends ViewCompatApi15Impl {
        ViewCompatApi16Impl() {
        }

        @Override
        public boolean getFitsSystemWindows(View view) {
            return view.getFitsSystemWindows();
        }

        @Override
        public int getImportantForAccessibility(View view) {
            return view.getImportantForAccessibility();
        }

        @Override
        public int getMinimumHeight(View view) {
            return view.getMinimumHeight();
        }

        @Override
        public int getMinimumWidth(View view) {
            return view.getMinimumWidth();
        }

        @Override
        public ViewParent getParentForAccessibility(View view) {
            return view.getParentForAccessibility();
        }

        @Override
        public boolean hasOverlappingRendering(View view) {
            return view.hasOverlappingRendering();
        }

        @Override
        public boolean hasTransientState(View view) {
            return view.hasTransientState();
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            return view.performAccessibilityAction(n, bundle);
        }

        @Override
        public void postInvalidateOnAnimation(View view) {
            view.postInvalidateOnAnimation();
        }

        @Override
        public void postInvalidateOnAnimation(View view, int n, int n2, int n3, int n4) {
            view.postInvalidateOnAnimation(n, n2, n3, n4);
        }

        @Override
        public void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        @Override
        public void postOnAnimationDelayed(View view, Runnable runnable, long l) {
            view.postOnAnimationDelayed(runnable, l);
        }

        @Override
        public void requestApplyInsets(View view) {
            view.requestFitSystemWindows();
        }

        @Override
        public void setBackground(View view, Drawable drawable2) {
            view.setBackground(drawable2);
        }

        @Override
        public void setHasTransientState(View view, boolean bl) {
            view.setHasTransientState(bl);
        }

        @Override
        public void setImportantForAccessibility(View view, int n) {
            int n2 = n;
            if (n == 4) {
                n2 = 2;
            }
            view.setImportantForAccessibility(n2);
        }
    }

    static class ViewCompatApi17Impl
    extends ViewCompatApi16Impl {
        ViewCompatApi17Impl() {
        }

        @Override
        public Display getDisplay(View view) {
            return view.getDisplay();
        }

        @Override
        public int getLayoutDirection(View view) {
            return view.getLayoutDirection();
        }

        @Override
        public int getPaddingEnd(View view) {
            return view.getPaddingEnd();
        }

        @Override
        public int getPaddingStart(View view) {
            return view.getPaddingStart();
        }

        @Override
        public int getWindowSystemUiVisibility(View view) {
            return view.getWindowSystemUiVisibility();
        }

        @Override
        public boolean isPaddingRelative(View view) {
            return view.isPaddingRelative();
        }

        @Override
        public void setLayerPaint(View view, Paint paint) {
            view.setLayerPaint(paint);
        }

        @Override
        public void setPaddingRelative(View view, int n, int n2, int n3, int n4) {
            view.setPaddingRelative(n, n2, n3, n4);
        }
    }

    static class ViewCompatApi18Impl
    extends ViewCompatApi17Impl {
        ViewCompatApi18Impl() {
        }

        @Override
        public Rect getClipBounds(View view) {
            return view.getClipBounds();
        }

        @Override
        public void setClipBounds(View view, Rect rect) {
            view.setClipBounds(rect);
        }
    }

    static class ViewCompatApi19Impl
    extends ViewCompatApi18Impl {
        ViewCompatApi19Impl() {
        }

        @Override
        public boolean isAttachedToWindow(View view) {
            return view.isAttachedToWindow();
        }

        @Override
        public boolean isLaidOut(View view) {
            return view.isLaidOut();
        }

        @Override
        public void setAccessibilityLiveRegion(View view, int n) {
            view.setAccessibilityLiveRegion(n);
        }

        @Override
        public void setImportantForAccessibility(View view, int n) {
            view.setImportantForAccessibility(n);
        }
    }

    static class ViewCompatApi21Impl
    extends ViewCompatApi19Impl {
        private static ThreadLocal<Rect> sThreadLocalRect;

        ViewCompatApi21Impl() {
        }

        private static Rect getEmptyTempRect() {
            Rect rect;
            if (sThreadLocalRect == null) {
                sThreadLocalRect = new ThreadLocal();
            }
            Rect rect2 = rect = sThreadLocalRect.get();
            if (rect == null) {
                rect2 = new Rect();
                sThreadLocalRect.set(rect2);
            }
            rect2.setEmpty();
            return rect2;
        }

        @Override
        public WindowInsetsCompat dispatchApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat = (WindowInsets)WindowInsetsCompat.unwrap(windowInsetsCompat);
            WindowInsets windowInsets = object.dispatchApplyWindowInsets((WindowInsets)windowInsetsCompat);
            object = windowInsetsCompat;
            if (windowInsets != windowInsetsCompat) {
                object = new WindowInsets(windowInsets);
            }
            return WindowInsetsCompat.wrap(object);
        }

        @Override
        public ColorStateList getBackgroundTintList(View view) {
            return view.getBackgroundTintList();
        }

        @Override
        public PorterDuff.Mode getBackgroundTintMode(View view) {
            return view.getBackgroundTintMode();
        }

        @Override
        public float getElevation(View view) {
            return view.getElevation();
        }

        @Override
        public String getTransitionName(View view) {
            return view.getTransitionName();
        }

        @Override
        public float getTranslationZ(View view) {
            return view.getTranslationZ();
        }

        @Override
        public float getZ(View view) {
            return view.getZ();
        }

        @Override
        public boolean isNestedScrollingEnabled(View view) {
            return view.isNestedScrollingEnabled();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void offsetLeftAndRight(View view, int n) {
            Rect rect = ViewCompatApi21Impl.getEmptyTempRect();
            boolean bl = false;
            ViewParent viewParent = view.getParent();
            if (viewParent instanceof View) {
                View view2 = (View)viewParent;
                rect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
                bl = !rect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
            super.offsetLeftAndRight(view, n);
            if (bl && rect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((View)viewParent).invalidate(rect);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void offsetTopAndBottom(View view, int n) {
            Rect rect = ViewCompatApi21Impl.getEmptyTempRect();
            boolean bl = false;
            ViewParent viewParent = view.getParent();
            if (viewParent instanceof View) {
                View view2 = (View)viewParent;
                rect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
                bl = !rect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
            super.offsetTopAndBottom(view, n);
            if (bl && rect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((View)viewParent).invalidate(rect);
            }
        }

        @Override
        public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat = (WindowInsets)WindowInsetsCompat.unwrap(windowInsetsCompat);
            WindowInsets windowInsets = object.onApplyWindowInsets((WindowInsets)windowInsetsCompat);
            object = windowInsetsCompat;
            if (windowInsets != windowInsetsCompat) {
                object = new WindowInsets(windowInsets);
            }
            return WindowInsetsCompat.wrap(object);
        }

        @Override
        public void requestApplyInsets(View view) {
            view.requestApplyInsets();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            view.setBackgroundTintList(colorStateList);
            if (Build.VERSION.SDK_INT == 21) {
                colorStateList = view.getBackground();
                boolean bl = view.getBackgroundTintList() != null && view.getBackgroundTintMode() != null;
                if (colorStateList != null && bl) {
                    if (colorStateList.isStateful()) {
                        colorStateList.setState(view.getDrawableState());
                    }
                    view.setBackground((Drawable)colorStateList);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
            view.setBackgroundTintMode(mode);
            if (Build.VERSION.SDK_INT == 21) {
                mode = view.getBackground();
                boolean bl = view.getBackgroundTintList() != null && view.getBackgroundTintMode() != null;
                if (mode != null && bl) {
                    if (mode.isStateful()) {
                        mode.setState(view.getDrawableState());
                    }
                    view.setBackground((Drawable)mode);
                }
            }
        }

        @Override
        public void setElevation(View view, float f) {
            view.setElevation(f);
        }

        @Override
        public void setOnApplyWindowInsetsListener(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            if (onApplyWindowInsetsListener == null) {
                view.setOnApplyWindowInsetsListener(null);
                return;
            }
            view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){

                public WindowInsets onApplyWindowInsets(View view, WindowInsets object) {
                    object = WindowInsetsCompat.wrap(object);
                    return (WindowInsets)WindowInsetsCompat.unwrap(onApplyWindowInsetsListener.onApplyWindowInsets(view, (WindowInsetsCompat)object));
                }
            });
        }

        @Override
        public void setTranslationZ(View view, float f) {
            view.setTranslationZ(f);
        }

        @Override
        public void stopNestedScroll(View view) {
            view.stopNestedScroll();
        }

    }

    static class ViewCompatApi23Impl
    extends ViewCompatApi21Impl {
        ViewCompatApi23Impl() {
        }

        @Override
        public void offsetLeftAndRight(View view, int n) {
            view.offsetLeftAndRight(n);
        }

        @Override
        public void offsetTopAndBottom(View view, int n) {
            view.offsetTopAndBottom(n);
        }

        @Override
        public void setScrollIndicators(View view, int n, int n2) {
            view.setScrollIndicators(n, n2);
        }
    }

    static class ViewCompatApi24Impl
    extends ViewCompatApi23Impl {
        ViewCompatApi24Impl() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setPointerIcon(View view, PointerIconCompat object) {
            object = object != null ? ((PointerIconCompat)object).getPointerIcon() : null;
            view.setPointerIcon((PointerIcon)object);
        }
    }

    static class ViewCompatApi26Impl
    extends ViewCompatApi24Impl {
        ViewCompatApi26Impl() {
        }
    }

    static class ViewCompatBaseImpl {
        static boolean sAccessibilityDelegateCheckFailed = false;
        static Field sAccessibilityDelegateField;
        private static Method sChildrenDrawingOrderMethod;
        private static Field sMinHeightField;
        private static boolean sMinHeightFieldFetched;
        private static Field sMinWidthField;
        private static boolean sMinWidthFieldFetched;
        private static WeakHashMap<View, String> sTransitionNameMap;
        WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        ViewCompatBaseImpl() {
        }

        private static void tickleInvalidationFlag(View view) {
            float f = view.getTranslationY();
            view.setTranslationY(1.0f + f);
            view.setTranslationY(f);
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            if (this.mViewPropertyAnimatorCompatMap == null) {
                this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat = this.mViewPropertyAnimatorCompatMap.get((Object)view);
            if (viewPropertyAnimatorCompat == null) {
                viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
                this.mViewPropertyAnimatorCompatMap.put(view, viewPropertyAnimatorCompat2);
            }
            return viewPropertyAnimatorCompat2;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public ColorStateList getBackgroundTintList(View view) {
            if (view instanceof TintableBackgroundView) {
                return ((TintableBackgroundView)view).getSupportBackgroundTintList();
            }
            return null;
        }

        public PorterDuff.Mode getBackgroundTintMode(View view) {
            if (view instanceof TintableBackgroundView) {
                return ((TintableBackgroundView)view).getSupportBackgroundTintMode();
            }
            return null;
        }

        public Rect getClipBounds(View view) {
            return null;
        }

        public Display getDisplay(View view) {
            if (this.isAttachedToWindow(view)) {
                return ((WindowManager)view.getContext().getSystemService("window")).getDefaultDisplay();
            }
            return null;
        }

        public float getElevation(View view) {
            return 0.0f;
        }

        public boolean getFitsSystemWindows(View view) {
            return false;
        }

        long getFrameTime() {
            return ValueAnimator.getFrameDelay();
        }

        public int getImportantForAccessibility(View view) {
            return 0;
        }

        public int getLayoutDirection(View view) {
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int getMinimumHeight(View view) {
            if (!sMinHeightFieldFetched) {
                try {
                    sMinHeightField = View.class.getDeclaredField("mMinHeight");
                    sMinHeightField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {}
                sMinHeightFieldFetched = true;
            }
            if (sMinHeightField == null) return 0;
            try {
                return (Integer)sMinHeightField.get((Object)view);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int getMinimumWidth(View view) {
            if (!sMinWidthFieldFetched) {
                try {
                    sMinWidthField = View.class.getDeclaredField("mMinWidth");
                    sMinWidthField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {}
                sMinWidthFieldFetched = true;
            }
            if (sMinWidthField == null) return 0;
            try {
                return (Integer)sMinWidthField.get((Object)view);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return 0;
        }

        public int getPaddingEnd(View view) {
            return view.getPaddingRight();
        }

        public int getPaddingStart(View view) {
            return view.getPaddingLeft();
        }

        public ViewParent getParentForAccessibility(View view) {
            return view.getParent();
        }

        public String getTransitionName(View view) {
            if (sTransitionNameMap == null) {
                return null;
            }
            return sTransitionNameMap.get((Object)view);
        }

        public float getTranslationZ(View view) {
            return 0.0f;
        }

        public int getWindowSystemUiVisibility(View view) {
            return 0;
        }

        public float getZ(View view) {
            return this.getTranslationZ(view) + this.getElevation(view);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean hasAccessibilityDelegate(View object) {
            boolean bl = true;
            if (sAccessibilityDelegateCheckFailed) {
                return false;
            }
            if (sAccessibilityDelegateField == null) {
                sAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
                sAccessibilityDelegateField.setAccessible(true);
            }
            try {
                Object object2 = sAccessibilityDelegateField.get(object);
                if (object2 == null) return false;
                return bl;
            }
            catch (Throwable throwable) {
                sAccessibilityDelegateCheckFailed = true;
                return false;
            }
            catch (Throwable throwable) {
                sAccessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public boolean hasOnClickListeners(View view) {
            return false;
        }

        public boolean hasOverlappingRendering(View view) {
            return true;
        }

        public boolean hasTransientState(View view) {
            return false;
        }

        public boolean isAttachedToWindow(View view) {
            return view.getWindowToken() != null;
        }

        public boolean isLaidOut(View view) {
            return view.getWidth() > 0 && view.getHeight() > 0;
        }

        public boolean isNestedScrollingEnabled(View view) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild)view).isNestedScrollingEnabled();
            }
            return false;
        }

        public boolean isPaddingRelative(View view) {
            return false;
        }

        public void offsetLeftAndRight(View view, int n) {
            view.offsetLeftAndRight(n);
            if (view.getVisibility() == 0) {
                ViewCompatBaseImpl.tickleInvalidationFlag(view);
                view = view.getParent();
                if (view instanceof View) {
                    ViewCompatBaseImpl.tickleInvalidationFlag(view);
                }
            }
        }

        public void offsetTopAndBottom(View view, int n) {
            view.offsetTopAndBottom(n);
            if (view.getVisibility() == 0) {
                ViewCompatBaseImpl.tickleInvalidationFlag(view);
                view = view.getParent();
                if (view instanceof View) {
                    ViewCompatBaseImpl.tickleInvalidationFlag(view);
                }
            }
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat.unwrap());
        }

        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            return false;
        }

        public void postInvalidateOnAnimation(View view) {
            view.postInvalidate();
        }

        public void postInvalidateOnAnimation(View view, int n, int n2, int n3, int n4) {
            view.postInvalidate(n, n2, n3, n4);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            view.postDelayed(runnable, this.getFrameTime());
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long l) {
            view.postDelayed(runnable, this.getFrameTime() + l);
        }

        public void requestApplyInsets(View view) {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
            accessibilityDelegateCompat = accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge();
            view.setAccessibilityDelegate((View.AccessibilityDelegate)accessibilityDelegateCompat);
        }

        public void setAccessibilityLiveRegion(View view, int n) {
        }

        public void setBackground(View view, Drawable drawable2) {
            view.setBackgroundDrawable(drawable2);
        }

        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            if (view instanceof TintableBackgroundView) {
                ((TintableBackgroundView)view).setSupportBackgroundTintList(colorStateList);
            }
        }

        public void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
            if (view instanceof TintableBackgroundView) {
                ((TintableBackgroundView)view).setSupportBackgroundTintMode(mode);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean bl) {
            if (sChildrenDrawingOrderMethod == null) {
                try {
                    sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.e((String)"ViewCompat", (String)"Unable to find childrenDrawingOrderEnabled", (Throwable)noSuchMethodException);
                }
                sChildrenDrawingOrderMethod.setAccessible(true);
            }
            try {
                sChildrenDrawingOrderMethod.invoke((Object)viewGroup, bl);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)"ViewCompat", (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)illegalAccessException);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e((String)"ViewCompat", (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)illegalArgumentException);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.e((String)"ViewCompat", (String)"Unable to invoke childrenDrawingOrderEnabled", (Throwable)invocationTargetException);
                return;
            }
        }

        public void setClipBounds(View view, Rect rect) {
        }

        public void setElevation(View view, float f) {
        }

        public void setHasTransientState(View view, boolean bl) {
        }

        public void setImportantForAccessibility(View view, int n) {
        }

        public void setLayerPaint(View view, Paint paint) {
            view.setLayerType(view.getLayerType(), paint);
            view.invalidate();
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        }

        public void setPaddingRelative(View view, int n, int n2, int n3, int n4) {
            view.setPadding(n, n2, n3, n4);
        }

        public void setPointerIcon(View view, PointerIconCompat pointerIconCompat) {
        }

        public void setScrollIndicators(View view, int n, int n2) {
        }

        public void setTranslationZ(View view, float f) {
        }

        public void stopNestedScroll(View view) {
            if (view instanceof NestedScrollingChild) {
                ((NestedScrollingChild)view).stopNestedScroll();
            }
        }
    }

}

