/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.accessibility.AccessibilityRecord
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;

public class AccessibilityRecordCompat {
    private static final AccessibilityRecordCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 16 ? new AccessibilityRecordCompatApi16Impl() : (Build.VERSION.SDK_INT >= 15 ? new AccessibilityRecordCompatApi15Impl() : new AccessibilityRecordCompatBaseImpl());
    private final AccessibilityRecord mRecord;

    public static void setMaxScrollX(AccessibilityRecord accessibilityRecord, int n) {
        IMPL.setMaxScrollX(accessibilityRecord, n);
    }

    public static void setMaxScrollY(AccessibilityRecord accessibilityRecord, int n) {
        IMPL.setMaxScrollY(accessibilityRecord, n);
    }

    public static void setSource(AccessibilityRecord accessibilityRecord, View view, int n) {
        IMPL.setSource(accessibilityRecord, view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityRecordCompat)object;
        if (this.mRecord == null) {
            if (((AccessibilityRecordCompat)object).mRecord == null) return true;
            return false;
        }
        if (!this.mRecord.equals((Object)((AccessibilityRecordCompat)object).mRecord)) return false;
        return true;
    }

    @Deprecated
    public int hashCode() {
        if (this.mRecord == null) {
            return 0;
        }
        return this.mRecord.hashCode();
    }

    static class AccessibilityRecordCompatApi15Impl
    extends AccessibilityRecordCompatBaseImpl {
        AccessibilityRecordCompatApi15Impl() {
        }

        @Override
        public void setMaxScrollX(AccessibilityRecord accessibilityRecord, int n) {
            accessibilityRecord.setMaxScrollX(n);
        }

        @Override
        public void setMaxScrollY(AccessibilityRecord accessibilityRecord, int n) {
            accessibilityRecord.setMaxScrollY(n);
        }
    }

    static class AccessibilityRecordCompatApi16Impl
    extends AccessibilityRecordCompatApi15Impl {
        AccessibilityRecordCompatApi16Impl() {
        }

        @Override
        public void setSource(AccessibilityRecord accessibilityRecord, View view, int n) {
            accessibilityRecord.setSource(view, n);
        }
    }

    static class AccessibilityRecordCompatBaseImpl {
        AccessibilityRecordCompatBaseImpl() {
        }

        public void setMaxScrollX(AccessibilityRecord accessibilityRecord, int n) {
        }

        public void setMaxScrollY(AccessibilityRecord accessibilityRecord, int n) {
        }

        public void setSource(AccessibilityRecord accessibilityRecord, View view, int n) {
        }
    }

}

