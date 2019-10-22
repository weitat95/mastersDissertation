/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

public final class AccessibilityEventCompat {
    private static final AccessibilityEventCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 19 ? new AccessibilityEventCompatApi19Impl() : (Build.VERSION.SDK_INT >= 16 ? new AccessibilityEventCompatApi16Impl() : new AccessibilityEventCompatBaseImpl());

    public static int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
        return IMPL.getContentChangeTypes(accessibilityEvent);
    }

    public static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int n) {
        IMPL.setContentChangeTypes(accessibilityEvent, n);
    }

    static class AccessibilityEventCompatApi16Impl
    extends AccessibilityEventCompatBaseImpl {
        AccessibilityEventCompatApi16Impl() {
        }
    }

    static class AccessibilityEventCompatApi19Impl
    extends AccessibilityEventCompatApi16Impl {
        AccessibilityEventCompatApi19Impl() {
        }

        @Override
        public int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
            return accessibilityEvent.getContentChangeTypes();
        }

        @Override
        public void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int n) {
            accessibilityEvent.setContentChangeTypes(n);
        }
    }

    static class AccessibilityEventCompatBaseImpl {
        AccessibilityEventCompatBaseImpl() {
        }

        public int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
            return 0;
        }

        public void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int n) {
        }
    }

}

