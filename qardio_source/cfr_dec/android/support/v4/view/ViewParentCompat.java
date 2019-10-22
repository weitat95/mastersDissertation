/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.view;

import android.os.Build;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

public final class ViewParentCompat {
    static final ViewParentCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new ViewParentCompatApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new ViewParentCompatApi19Impl() : new ViewParentCompatBaseImpl());

    public static boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean bl) {
        return IMPL.onNestedFling(viewParent, view, f, f2, bl);
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
        return IMPL.onNestedPreFling(viewParent, view, f, f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] arrn, int n3) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedPreScroll(view, n, n2, arrn, n3);
            return;
        } else {
            if (n3 != 0) return;
            {
                IMPL.onNestedPreScroll(viewParent, view, n, n2, arrn);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4, int n5) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedScroll(view, n, n2, n3, n4, n5);
            return;
        } else {
            if (n5 != 0) return;
            {
                IMPL.onNestedScroll(viewParent, view, n, n2, n3, n4);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n, int n2) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedScrollAccepted(view, view2, n, n2);
            return;
        } else {
            if (n2 != 0) return;
            {
                IMPL.onNestedScrollAccepted(viewParent, view, view2, n);
                return;
            }
        }
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n, int n2) {
        if (viewParent instanceof NestedScrollingParent2) {
            return ((NestedScrollingParent2)viewParent).onStartNestedScroll(view, view2, n, n2);
        }
        if (n2 == 0) {
            return IMPL.onStartNestedScroll(viewParent, view, view2, n);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onStopNestedScroll(ViewParent viewParent, View view, int n) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onStopNestedScroll(view, n);
            return;
        } else {
            if (n != 0) return;
            {
                IMPL.onStopNestedScroll(viewParent, view);
                return;
            }
        }
    }

    @Deprecated
    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view, accessibilityEvent);
    }

    static class ViewParentCompatApi19Impl
    extends ViewParentCompatBaseImpl {
        ViewParentCompatApi19Impl() {
        }
    }

    static class ViewParentCompatApi21Impl
    extends ViewParentCompatApi19Impl {
        ViewParentCompatApi21Impl() {
        }

        @Override
        public boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean bl) {
            try {
                bl = viewParent.onNestedFling(view, f, f2, bl);
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onNestedFling"), (Throwable)abstractMethodError);
                return false;
            }
        }

        @Override
        public boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
            try {
                boolean bl = viewParent.onNestedPreFling(view, f, f2);
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onNestedPreFling"), (Throwable)abstractMethodError);
                return false;
            }
        }

        @Override
        public void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] arrn) {
            try {
                viewParent.onNestedPreScroll(view, n, n2, arrn);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onNestedPreScroll"), (Throwable)abstractMethodError);
                return;
            }
        }

        @Override
        public void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4) {
            try {
                viewParent.onNestedScroll(view, n, n2, n3, n4);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onNestedScroll"), (Throwable)abstractMethodError);
                return;
            }
        }

        @Override
        public void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n) {
            try {
                viewParent.onNestedScrollAccepted(view, view2, n);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onNestedScrollAccepted"), (Throwable)abstractMethodError);
                return;
            }
        }

        @Override
        public boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n) {
            try {
                boolean bl = viewParent.onStartNestedScroll(view, view2, n);
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onStartNestedScroll"), (Throwable)abstractMethodError);
                return false;
            }
        }

        @Override
        public void onStopNestedScroll(ViewParent viewParent, View view) {
            try {
                viewParent.onStopNestedScroll(view);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                Log.e((String)"ViewParentCompat", (String)("ViewParent " + (Object)viewParent + " does not implement interface " + "method onStopNestedScroll"), (Throwable)abstractMethodError);
                return;
            }
        }
    }

    static class ViewParentCompatBaseImpl {
        ViewParentCompatBaseImpl() {
        }

        public boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean bl) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent)viewParent).onNestedFling(view, f, f2, bl);
            }
            return false;
        }

        public boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent)viewParent).onNestedPreFling(view, f, f2);
            }
            return false;
        }

        public void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] arrn) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent)viewParent).onNestedPreScroll(view, n, n2, arrn);
            }
        }

        public void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent)viewParent).onNestedScroll(view, n, n2, n3, n4);
            }
        }

        public void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent)viewParent).onNestedScrollAccepted(view, view2, n);
            }
        }

        public boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent)viewParent).onStartNestedScroll(view, view2, n);
            }
            return false;
        }

        public void onStopNestedScroll(ViewParent viewParent, View view) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent)viewParent).onStopNestedScroll(view);
            }
        }
    }

}

