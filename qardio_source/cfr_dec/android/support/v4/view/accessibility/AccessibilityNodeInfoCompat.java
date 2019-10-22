/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo
 */
package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityNodeInfoCompat {
    static final AccessibilityNodeInfoBaseImpl IMPL = Build.VERSION.SDK_INT >= 24 ? new AccessibilityNodeInfoApi24Impl() : (Build.VERSION.SDK_INT >= 23 ? new AccessibilityNodeInfoApi23Impl() : (Build.VERSION.SDK_INT >= 22 ? new AccessibilityNodeInfoApi22Impl() : (Build.VERSION.SDK_INT >= 21 ? new AccessibilityNodeInfoApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoApi19Impl() : (Build.VERSION.SDK_INT >= 18 ? new AccessibilityNodeInfoApi18Impl() : (Build.VERSION.SDK_INT >= 17 ? new AccessibilityNodeInfoApi17Impl() : (Build.VERSION.SDK_INT >= 16 ? new AccessibilityNodeInfoApi16Impl() : new AccessibilityNodeInfoBaseImpl())))))));
    private final AccessibilityNodeInfo mInfo;
    public int mParentVirtualDescendantId = -1;

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo;
    }

    private static String getActionSymbolicName(int n) {
        switch (n) {
            default: {
                return "ACTION_UNKNOWN";
            }
            case 1: {
                return "ACTION_FOCUS";
            }
            case 2: {
                return "ACTION_CLEAR_FOCUS";
            }
            case 4: {
                return "ACTION_SELECT";
            }
            case 8: {
                return "ACTION_CLEAR_SELECTION";
            }
            case 16: {
                return "ACTION_CLICK";
            }
            case 32: {
                return "ACTION_LONG_CLICK";
            }
            case 64: {
                return "ACTION_ACCESSIBILITY_FOCUS";
            }
            case 128: {
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            }
            case 256: {
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            }
            case 512: {
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            }
            case 1024: {
                return "ACTION_NEXT_HTML_ELEMENT";
            }
            case 2048: {
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            }
            case 4096: {
                return "ACTION_SCROLL_FORWARD";
            }
            case 8192: {
                return "ACTION_SCROLL_BACKWARD";
            }
            case 65536: {
                return "ACTION_CUT";
            }
            case 16384: {
                return "ACTION_COPY";
            }
            case 32768: {
                return "ACTION_PASTE";
            }
            case 131072: 
        }
        return "ACTION_SET_SELECTION";
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((AccessibilityNodeInfo)accessibilityNodeInfoCompat.mInfo));
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((View)view));
    }

    public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    public void addAction(int n) {
        this.mInfo.addAction(n);
    }

    public void addChild(View view) {
        this.mInfo.addChild(view);
    }

    public void addChild(View view, int n) {
        IMPL.addChild(this.mInfo, view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityNodeInfoCompat)object;
        if (this.mInfo == null) {
            if (((AccessibilityNodeInfoCompat)object).mInfo == null) return true;
            return false;
        }
        if (!this.mInfo.equals((Object)((AccessibilityNodeInfoCompat)object).mInfo)) return false;
        return true;
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    public void getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.mInfo.getBoundsInScreen(rect);
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public int getMovementGranularities() {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public CharSequence getText() {
        return this.mInfo.getText();
    }

    public String getViewIdResourceName() {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public boolean isVisibleToUser() {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        return IMPL.removeAction(this.mInfo, accessibilityActionCompat.mAction);
    }

    public void setAccessibilityFocused(boolean bl) {
        IMPL.setAccessibilityFocused(this.mInfo, bl);
    }

    public void setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect);
    }

    public void setBoundsInScreen(Rect rect) {
        this.mInfo.setBoundsInScreen(rect);
    }

    public void setCanOpenPopup(boolean bl) {
        IMPL.setCanOpenPopup(this.mInfo, bl);
    }

    public void setCheckable(boolean bl) {
        this.mInfo.setCheckable(bl);
    }

    public void setChecked(boolean bl) {
        this.mInfo.setChecked(bl);
    }

    public void setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence);
    }

    public void setClickable(boolean bl) {
        this.mInfo.setClickable(bl);
    }

    public void setCollectionInfo(Object object) {
        IMPL.setCollectionInfo(this.mInfo, ((CollectionInfoCompat)object).mInfo);
    }

    public void setCollectionItemInfo(Object object) {
        IMPL.setCollectionItemInfo(this.mInfo, ((CollectionItemInfoCompat)object).mInfo);
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence);
    }

    public void setContentInvalid(boolean bl) {
        IMPL.setContentInvalid(this.mInfo, bl);
    }

    public void setDismissable(boolean bl) {
        IMPL.setDismissable(this.mInfo, bl);
    }

    public void setEnabled(boolean bl) {
        this.mInfo.setEnabled(bl);
    }

    public void setError(CharSequence charSequence) {
        IMPL.setError(this.mInfo, charSequence);
    }

    public void setFocusable(boolean bl) {
        this.mInfo.setFocusable(bl);
    }

    public void setFocused(boolean bl) {
        this.mInfo.setFocused(bl);
    }

    public void setLabelFor(View view) {
        IMPL.setLabelFor(this.mInfo, view);
    }

    public void setLongClickable(boolean bl) {
        this.mInfo.setLongClickable(bl);
    }

    public void setMovementGranularities(int n) {
        IMPL.setMovementGranularities(this.mInfo, n);
    }

    public void setPackageName(CharSequence charSequence) {
        this.mInfo.setPackageName(charSequence);
    }

    public void setParent(View view) {
        this.mInfo.setParent(view);
    }

    public void setParent(View view, int n) {
        this.mParentVirtualDescendantId = n;
        IMPL.setParent(this.mInfo, view, n);
    }

    public void setScrollable(boolean bl) {
        this.mInfo.setScrollable(bl);
    }

    public void setSelected(boolean bl) {
        this.mInfo.setSelected(bl);
    }

    public void setSource(View view) {
        this.mInfo.setSource(view);
    }

    public void setSource(View view, int n) {
        IMPL.setSource(this.mInfo, view, n);
    }

    public void setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence);
    }

    public void setVisibleToUser(boolean bl) {
        IMPL.setVisibleToUser(this.mInfo, bl);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        Rect rect = new Rect();
        this.getBoundsInParent(rect);
        stringBuilder.append("; boundsInParent: " + (Object)rect);
        this.getBoundsInScreen(rect);
        stringBuilder.append("; boundsInScreen: " + (Object)rect);
        stringBuilder.append("; packageName: ").append(this.getPackageName());
        stringBuilder.append("; className: ").append(this.getClassName());
        stringBuilder.append("; text: ").append(this.getText());
        stringBuilder.append("; contentDescription: ").append(this.getContentDescription());
        stringBuilder.append("; viewId: ").append(this.getViewIdResourceName());
        stringBuilder.append("; checkable: ").append(this.isCheckable());
        stringBuilder.append("; checked: ").append(this.isChecked());
        stringBuilder.append("; focusable: ").append(this.isFocusable());
        stringBuilder.append("; focused: ").append(this.isFocused());
        stringBuilder.append("; selected: ").append(this.isSelected());
        stringBuilder.append("; clickable: ").append(this.isClickable());
        stringBuilder.append("; longClickable: ").append(this.isLongClickable());
        stringBuilder.append("; enabled: ").append(this.isEnabled());
        stringBuilder.append("; password: ").append(this.isPassword());
        stringBuilder.append("; scrollable: " + this.isScrollable());
        stringBuilder.append("; [");
        int n = this.getActions();
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            int n3 = n & ~n2;
            stringBuilder.append(AccessibilityNodeInfoCompat.getActionSymbolicName(n2));
            n = n3;
            if (n3 == 0) continue;
            stringBuilder.append(", ");
            n = n3;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION;
        public static final AccessibilityActionCompat ACTION_CLICK;
        public static final AccessibilityActionCompat ACTION_COLLAPSE;
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
        public static final AccessibilityActionCompat ACTION_COPY;
        public static final AccessibilityActionCompat ACTION_CUT;
        public static final AccessibilityActionCompat ACTION_DISMISS;
        public static final AccessibilityActionCompat ACTION_EXPAND;
        public static final AccessibilityActionCompat ACTION_FOCUS;
        public static final AccessibilityActionCompat ACTION_LONG_CLICK;
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_PASTE;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityActionCompat ACTION_SCROLL_UP;
        public static final AccessibilityActionCompat ACTION_SELECT;
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public static final AccessibilityActionCompat ACTION_SET_SELECTION;
        public static final AccessibilityActionCompat ACTION_SET_TEXT;
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
        final Object mAction;

        static {
            ACTION_FOCUS = new AccessibilityActionCompat(1, null);
            ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
            ACTION_SELECT = new AccessibilityActionCompat(4, null);
            ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
            ACTION_CLICK = new AccessibilityActionCompat(16, null);
            ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
            ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
            ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
            ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
            ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
            ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
            ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
            ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
            ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
            ACTION_COPY = new AccessibilityActionCompat(16384, null);
            ACTION_PASTE = new AccessibilityActionCompat(32768, null);
            ACTION_CUT = new AccessibilityActionCompat(65536, null);
            ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
            ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
            ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
            ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
            ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
            ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(IMPL.getActionShowOnScreen());
            ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(IMPL.getActionScrollToPosition());
            ACTION_SCROLL_UP = new AccessibilityActionCompat(IMPL.getActionScrollUp());
            ACTION_SCROLL_LEFT = new AccessibilityActionCompat(IMPL.getActionScrollLeft());
            ACTION_SCROLL_DOWN = new AccessibilityActionCompat(IMPL.getActionScrollDown());
            ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(IMPL.getActionScrollRight());
            ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(IMPL.getActionContextClick());
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(IMPL.getActionSetProgress());
        }

        public AccessibilityActionCompat(int n, CharSequence charSequence) {
            this(IMPL.newAccessibilityAction(n, charSequence));
        }

        AccessibilityActionCompat(Object object) {
            this.mAction = object;
        }
    }

    static class AccessibilityNodeInfoApi16Impl
    extends AccessibilityNodeInfoBaseImpl {
        AccessibilityNodeInfoApi16Impl() {
        }

        @Override
        public void addChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
            accessibilityNodeInfo.addChild(view, n);
        }

        @Override
        public int getMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getMovementGranularities();
        }

        @Override
        public boolean isAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isAccessibilityFocused();
        }

        @Override
        public boolean isVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isVisibleToUser();
        }

        @Override
        public void setAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setAccessibilityFocused(bl);
        }

        @Override
        public void setMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo, int n) {
            accessibilityNodeInfo.setMovementGranularities(n);
        }

        @Override
        public void setParent(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
            accessibilityNodeInfo.setParent(view, n);
        }

        @Override
        public void setSource(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
            accessibilityNodeInfo.setSource(view, n);
        }

        @Override
        public void setVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setVisibleToUser(bl);
        }
    }

    static class AccessibilityNodeInfoApi17Impl
    extends AccessibilityNodeInfoApi16Impl {
        AccessibilityNodeInfoApi17Impl() {
        }

        @Override
        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            accessibilityNodeInfo.setLabelFor(view);
        }
    }

    static class AccessibilityNodeInfoApi18Impl
    extends AccessibilityNodeInfoApi17Impl {
        AccessibilityNodeInfoApi18Impl() {
        }

        @Override
        public String getViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getViewIdResourceName();
        }
    }

    static class AccessibilityNodeInfoApi19Impl
    extends AccessibilityNodeInfoApi18Impl {
        AccessibilityNodeInfoApi19Impl() {
        }

        @Override
        public Object obtainCollectionInfo(int n, int n2, boolean bl, int n3) {
            return AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl);
        }

        @Override
        public Object obtainCollectionItemInfo(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl);
        }

        @Override
        public void setCanOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setCanOpenPopup(bl);
        }

        @Override
        public void setCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
            accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)object);
        }

        @Override
        public void setCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
            accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)object);
        }

        @Override
        public void setContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setContentInvalid(bl);
        }

        @Override
        public void setDismissable(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setDismissable(bl);
        }
    }

    static class AccessibilityNodeInfoApi21Impl
    extends AccessibilityNodeInfoApi19Impl {
        AccessibilityNodeInfoApi21Impl() {
        }

        @Override
        public Object newAccessibilityAction(int n, CharSequence charSequence) {
            return new AccessibilityNodeInfo.AccessibilityAction(n, charSequence);
        }

        @Override
        public Object obtainCollectionInfo(int n, int n2, boolean bl, int n3) {
            return AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl, (int)n3);
        }

        @Override
        public Object obtainCollectionItemInfo(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl, (boolean)bl2);
        }

        @Override
        public boolean removeAction(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
            return accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction)object);
        }

        @Override
        public void setError(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
            accessibilityNodeInfo.setError(charSequence);
        }
    }

    static class AccessibilityNodeInfoApi22Impl
    extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() {
        }
    }

    static class AccessibilityNodeInfoApi23Impl
    extends AccessibilityNodeInfoApi22Impl {
        AccessibilityNodeInfoApi23Impl() {
        }

        @Override
        public Object getActionContextClick() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
        }

        @Override
        public Object getActionScrollDown() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
        }

        @Override
        public Object getActionScrollLeft() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
        }

        @Override
        public Object getActionScrollRight() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
        }

        @Override
        public Object getActionScrollToPosition() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
        }

        @Override
        public Object getActionScrollUp() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
        }

        @Override
        public Object getActionShowOnScreen() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
        }
    }

    static class AccessibilityNodeInfoApi24Impl
    extends AccessibilityNodeInfoApi23Impl {
        AccessibilityNodeInfoApi24Impl() {
        }

        @Override
        public Object getActionSetProgress() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
        }
    }

    static class AccessibilityNodeInfoBaseImpl {
        AccessibilityNodeInfoBaseImpl() {
        }

        public void addChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
        }

        public Object getActionContextClick() {
            return null;
        }

        public Object getActionScrollDown() {
            return null;
        }

        public Object getActionScrollLeft() {
            return null;
        }

        public Object getActionScrollRight() {
            return null;
        }

        public Object getActionScrollToPosition() {
            return null;
        }

        public Object getActionScrollUp() {
            return null;
        }

        public Object getActionSetProgress() {
            return null;
        }

        public Object getActionShowOnScreen() {
            return null;
        }

        public int getMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo) {
            return 0;
        }

        public String getViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public boolean isAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public Object newAccessibilityAction(int n, CharSequence charSequence) {
            return null;
        }

        public Object obtainCollectionInfo(int n, int n2, boolean bl, int n3) {
            return null;
        }

        public Object obtainCollectionItemInfo(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            return null;
        }

        public boolean removeAction(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
            return false;
        }

        public void setAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        }

        public void setCanOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        }

        public void setCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
        }

        public void setCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object object) {
        }

        public void setContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        }

        public void setDismissable(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        }

        public void setError(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        }

        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
        }

        public void setMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo, int n) {
        }

        public void setParent(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
        }

        public void setSource(AccessibilityNodeInfo accessibilityNodeInfo, View view, int n) {
        }

        public void setVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        }
    }

    public static class CollectionInfoCompat {
        final Object mInfo;

        CollectionInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionInfoCompat obtain(int n, int n2, boolean bl, int n3) {
            return new CollectionInfoCompat(IMPL.obtainCollectionInfo(n, n2, bl, n3));
        }
    }

    public static class CollectionItemInfoCompat {
        final Object mInfo;

        CollectionItemInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            return new CollectionItemInfoCompat(IMPL.obtainCollectionItemInfo(n, n2, n3, n4, bl, bl2));
        }
    }

}

