/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat {
    static final PopupWindowCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 23 ? new PopupWindowCompatApi23Impl() : (Build.VERSION.SDK_INT >= 21 ? new PopupWindowCompatApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new PopupWindowCompatApi19Impl() : new PopupWindowCompatBaseImpl()));

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
        IMPL.setOverlapAnchor(popupWindow, bl);
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int n) {
        IMPL.setWindowLayoutType(popupWindow, n);
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
        IMPL.showAsDropDown(popupWindow, view, n, n2, n3);
    }

    static class PopupWindowCompatApi19Impl
    extends PopupWindowCompatBaseImpl {
        PopupWindowCompatApi19Impl() {
        }

        @Override
        public void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
            popupWindow.showAsDropDown(view, n, n2, n3);
        }
    }

    static class PopupWindowCompatApi21Impl
    extends PopupWindowCompatApi19Impl {
        private static Field sOverlapAnchorField;

        static {
            try {
                sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                sOverlapAnchorField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.i((String)"PopupWindowCompatApi21", (String)"Could not fetch mOverlapAnchor field from PopupWindow", (Throwable)noSuchFieldException);
            }
        }

        PopupWindowCompatApi21Impl() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
            if (sOverlapAnchorField == null) return;
            try {
                sOverlapAnchorField.set((Object)popupWindow, bl);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)"PopupWindowCompatApi21", (String)"Could not set overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
                return;
            }
        }
    }

    static class PopupWindowCompatApi23Impl
    extends PopupWindowCompatApi21Impl {
        PopupWindowCompatApi23Impl() {
        }

        @Override
        public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
            popupWindow.setOverlapAnchor(bl);
        }

        @Override
        public void setWindowLayoutType(PopupWindow popupWindow, int n) {
            popupWindow.setWindowLayoutType(n);
        }
    }

    static class PopupWindowCompatBaseImpl {
        private static Method sSetWindowLayoutTypeMethod;
        private static boolean sSetWindowLayoutTypeMethodAttempted;

        PopupWindowCompatBaseImpl() {
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void setWindowLayoutType(PopupWindow popupWindow, int n) {
            if (!sSetWindowLayoutTypeMethodAttempted) {
                try {
                    sSetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                    sSetWindowLayoutTypeMethod.setAccessible(true);
                }
                catch (Exception exception) {}
                sSetWindowLayoutTypeMethodAttempted = true;
            }
            if (sSetWindowLayoutTypeMethod == null) return;
            try {
                sSetWindowLayoutTypeMethod.invoke((Object)popupWindow, n);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        public void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
            int n4 = n;
            if ((GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
                n4 = n - (popupWindow.getWidth() - view.getWidth());
            }
            popupWindow.showAsDropDown(view, n4, n2);
        }
    }

}

