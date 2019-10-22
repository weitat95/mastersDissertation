/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityRecord
 */
package android.support.v4.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.FocusStrategy;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityRecord;
import java.util.ArrayList;
import java.util.List;

public abstract class ExploreByTouchHelper
extends AccessibilityDelegateCompat {
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>(){};
    private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>(){};
    private int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    private final View mHost;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect;
    private final Rect mTempParentRect;
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempVisibleRect;

    public ExploreByTouchHelper(View view) {
        this.mTempParentRect = new Rect();
        this.mTempVisibleRect = new Rect();
        this.mTempGlobalRect = new int[2];
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mHost = view;
        this.mManager = (AccessibilityManager)view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(view) == 0) {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
    }

    private boolean clearAccessibilityFocus(int n) {
        if (this.mAccessibilityFocusedVirtualViewId == n) {
            this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
            this.mHost.invalidate();
            this.sendEventForVirtualView(n, 65536);
            return true;
        }
        return false;
    }

    private AccessibilityEvent createEvent(int n, int n2) {
        switch (n) {
            default: {
                return this.createEventForChild(n, n2);
            }
            case -1: 
        }
        return this.createEventForHost(n2);
    }

    private AccessibilityEvent createEventForChild(int n, int n2) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n2);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.obtainAccessibilityNodeInfo(n);
        accessibilityEvent.getText().add(accessibilityNodeInfoCompat.getText());
        accessibilityEvent.setContentDescription(accessibilityNodeInfoCompat.getContentDescription());
        accessibilityEvent.setScrollable(accessibilityNodeInfoCompat.isScrollable());
        accessibilityEvent.setPassword(accessibilityNodeInfoCompat.isPassword());
        accessibilityEvent.setEnabled(accessibilityNodeInfoCompat.isEnabled());
        accessibilityEvent.setChecked(accessibilityNodeInfoCompat.isChecked());
        this.onPopulateEventForVirtualView(n, accessibilityEvent);
        if (accessibilityEvent.getText().isEmpty() && accessibilityEvent.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEvent.setClassName(accessibilityNodeInfoCompat.getClassName());
        AccessibilityRecordCompat.setSource((AccessibilityRecord)accessibilityEvent, this.mHost, n);
        accessibilityEvent.setPackageName((CharSequence)this.mHost.getContext().getPackageName());
        return accessibilityEvent;
    }

    private AccessibilityEvent createEventForHost(int n) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n);
        this.mHost.onInitializeAccessibilityEvent(accessibilityEvent);
        return accessibilityEvent;
    }

    /*
     * Enabled aggressive block sorting
     */
    private AccessibilityNodeInfoCompat createNodeForChild(int n) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain();
        accessibilityNodeInfoCompat.setEnabled(true);
        accessibilityNodeInfoCompat.setFocusable(true);
        accessibilityNodeInfoCompat.setClassName("android.view.View");
        accessibilityNodeInfoCompat.setBoundsInParent(INVALID_PARENT_BOUNDS);
        accessibilityNodeInfoCompat.setBoundsInScreen(INVALID_PARENT_BOUNDS);
        accessibilityNodeInfoCompat.setParent(this.mHost);
        this.onPopulateNodeForVirtualView(n, accessibilityNodeInfoCompat);
        if (accessibilityNodeInfoCompat.getText() == null && accessibilityNodeInfoCompat.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompat.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals((Object)INVALID_PARENT_BOUNDS)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int n2 = accessibilityNodeInfoCompat.getActions();
        if ((n2 & 0x40) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((n2 & 0x80) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompat.setPackageName(this.mHost.getContext().getPackageName());
        accessibilityNodeInfoCompat.setSource(this.mHost, n);
        if (this.mAccessibilityFocusedVirtualViewId == n) {
            accessibilityNodeInfoCompat.setAccessibilityFocused(true);
            accessibilityNodeInfoCompat.addAction(128);
        } else {
            accessibilityNodeInfoCompat.setAccessibilityFocused(false);
            accessibilityNodeInfoCompat.addAction(64);
        }
        boolean bl = this.mKeyboardFocusedVirtualViewId == n;
        if (bl) {
            accessibilityNodeInfoCompat.addAction(2);
        } else if (accessibilityNodeInfoCompat.isFocusable()) {
            accessibilityNodeInfoCompat.addAction(1);
        }
        accessibilityNodeInfoCompat.setFocused(bl);
        this.mHost.getLocationOnScreen(this.mTempGlobalRect);
        accessibilityNodeInfoCompat.getBoundsInScreen(this.mTempScreenRect);
        if (this.mTempScreenRect.equals((Object)INVALID_PARENT_BOUNDS)) {
            accessibilityNodeInfoCompat.getBoundsInParent(this.mTempScreenRect);
            if (accessibilityNodeInfoCompat.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain();
                n = accessibilityNodeInfoCompat.mParentVirtualDescendantId;
                while (n != -1) {
                    accessibilityNodeInfoCompat2.setParent(this.mHost, -1);
                    accessibilityNodeInfoCompat2.setBoundsInParent(INVALID_PARENT_BOUNDS);
                    this.onPopulateNodeForVirtualView(n, accessibilityNodeInfoCompat2);
                    accessibilityNodeInfoCompat2.getBoundsInParent(this.mTempParentRect);
                    this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top);
                    n = accessibilityNodeInfoCompat2.mParentVirtualDescendantId;
                }
                accessibilityNodeInfoCompat2.recycle();
            }
            this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
        }
        if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
            this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
            if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                accessibilityNodeInfoCompat.setBoundsInScreen(this.mTempScreenRect);
                if (this.isVisibleToUser(this.mTempScreenRect)) {
                    accessibilityNodeInfoCompat.setVisibleToUser(true);
                }
            }
        }
        return accessibilityNodeInfoCompat;
    }

    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, accessibilityNodeInfoCompat);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        this.getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfoCompat.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            accessibilityNodeInfoCompat.addChild(this.mHost, arrayList.get(i));
        }
        return accessibilityNodeInfoCompat;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isVisibleToUser(Rect rect) {
        if (rect == null || rect.isEmpty() || this.mHost.getWindowVisibility() != 0) return false;
        rect = this.mHost.getParent();
        while (rect instanceof View) {
            if ((rect = (View)rect).getAlpha() <= 0.0f || rect.getVisibility() != 0) return false;
            {
                rect = rect.getParent();
            }
        }
        if (rect != null) return true;
        return false;
    }

    private boolean performActionForChild(int n, int n2, Bundle bundle) {
        switch (n2) {
            default: {
                return this.onPerformActionForVirtualView(n, n2, bundle);
            }
            case 64: {
                return this.requestAccessibilityFocus(n);
            }
            case 128: {
                return this.clearAccessibilityFocus(n);
            }
            case 1: {
                return this.requestKeyboardFocusForVirtualView(n);
            }
            case 2: 
        }
        return this.clearKeyboardFocusForVirtualView(n);
    }

    private boolean performActionForHost(int n, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.mHost, n, bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean requestAccessibilityFocus(int n) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled() || this.mAccessibilityFocusedVirtualViewId == n) {
            return false;
        }
        if (this.mAccessibilityFocusedVirtualViewId != Integer.MIN_VALUE) {
            this.clearAccessibilityFocus(this.mAccessibilityFocusedVirtualViewId);
        }
        this.mAccessibilityFocusedVirtualViewId = n;
        this.mHost.invalidate();
        this.sendEventForVirtualView(n, 32768);
        return true;
    }

    private void updateHoveredVirtualView(int n) {
        if (this.mHoveredVirtualViewId == n) {
            return;
        }
        int n2 = this.mHoveredVirtualViewId;
        this.mHoveredVirtualViewId = n;
        this.sendEventForVirtualView(n, 128);
        this.sendEventForVirtualView(n2, 256);
    }

    public final boolean clearKeyboardFocusForVirtualView(int n) {
        if (this.mKeyboardFocusedVirtualViewId != n) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        this.onVirtualViewKeyboardFocusChanged(n, false);
        this.sendEventForVirtualView(n, 8);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean bl = true;
        if (!this.mManager.isEnabled()) return false;
        if (!this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 7: 
            case 9: {
                int n = this.getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
                this.updateHoveredVirtualView(n);
                if (n == Integer.MIN_VALUE) return false;
                return bl;
            }
            case 10: 
        }
        if (this.mAccessibilityFocusedVirtualViewId == Integer.MIN_VALUE) return false;
        this.updateHoveredVirtualView(Integer.MIN_VALUE);
        return true;
    }

    @Override
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    protected abstract int getVirtualViewAt(float var1, float var2);

    protected abstract void getVisibleVirtualViews(List<Integer> var1);

    AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int n) {
        if (n == -1) {
            return this.createNodeForHost();
        }
        return this.createNodeForChild(n);
    }

    @Override
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        this.onPopulateEventForHost(accessibilityEvent);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        this.onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    protected abstract boolean onPerformActionForVirtualView(int var1, int var2, Bundle var3);

    protected void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    protected abstract void onPopulateNodeForVirtualView(int var1, AccessibilityNodeInfoCompat var2);

    protected void onVirtualViewKeyboardFocusChanged(int n, boolean bl) {
    }

    boolean performAction(int n, int n2, Bundle bundle) {
        switch (n) {
            default: {
                return this.performActionForChild(n, n2, bundle);
            }
            case -1: 
        }
        return this.performActionForHost(n2, bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean requestKeyboardFocusForVirtualView(int n) {
        if (!this.mHost.isFocused() && !this.mHost.requestFocus() || this.mKeyboardFocusedVirtualViewId == n) {
            return false;
        }
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            this.clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId);
        }
        this.mKeyboardFocusedVirtualViewId = n;
        this.onVirtualViewKeyboardFocusChanged(n, true);
        this.sendEventForVirtualView(n, 8);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean sendEventForVirtualView(int n, int n2) {
        ViewParent viewParent;
        if (n == Integer.MIN_VALUE || !this.mManager.isEnabled() || (viewParent = this.mHost.getParent()) == null) {
            return false;
        }
        AccessibilityEvent accessibilityEvent = this.createEvent(n, n2);
        return ViewParentCompat.requestSendAccessibilityEvent(viewParent, this.mHost, accessibilityEvent);
    }

    private class MyNodeProvider
    extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        @Override
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(n));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public AccessibilityNodeInfoCompat findFocus(int n) {
            if ((n = n == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId) == Integer.MIN_VALUE) {
                return null;
            }
            return this.createAccessibilityNodeInfo(n);
        }

        @Override
        public boolean performAction(int n, int n2, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(n, n2, bundle);
        }
    }

}

