/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.ListView
 */
package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.appcompat.R;
import android.support.v7.widget.ListViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

class DropDownListView
extends ListViewCompat {
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private ListViewAutoScrollHelper mScrollHelper;

    public DropDownListView(Context context, boolean bl) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.mHijackFocus = bl;
        this.setCacheColorHint(0);
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        this.setPressed(false);
        this.drawableStateChanged();
        View view = this.getChildAt(this.mMotionPosition - this.getFirstVisiblePosition());
        if (view != null) {
            view.setPressed(false);
        }
        if (this.mClickAnimation != null) {
            this.mClickAnimation.cancel();
            this.mClickAnimation = null;
        }
    }

    private void clickPressedItem(View view, int n) {
        this.performItemClick(view, n, this.getItemIdAtPosition(n));
    }

    private void setPressedItem(View view, int n, float f, float f2) {
        View view2;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            this.drawableHotspotChanged(f, f2);
        }
        if (!this.isPressed()) {
            this.setPressed(true);
        }
        this.layoutChildren();
        if (this.mMotionPosition != -1 && (view2 = this.getChildAt(this.mMotionPosition - this.getFirstVisiblePosition())) != null && view2 != view && view2.isPressed()) {
            view2.setPressed(false);
        }
        this.mMotionPosition = n;
        float f3 = view.getLeft();
        float f4 = view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(f - f3, f2 - f4);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        this.positionSelectorLikeTouchCompat(n, view, f, f2);
        this.setSelectorEnabled(false);
        this.refreshDrawableState();
    }

    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    public boolean isInTouchMode() {
        return this.mHijackFocus && this.mListSelectionHidden || super.isInTouchMode();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onForwardedEvent(MotionEvent motionEvent, int n) {
        boolean bl = true;
        boolean bl2 = true;
        int n2 = 0;
        int n3 = motionEvent.getActionMasked();
        switch (n3) {
            default: {
                bl = bl2;
                n = n2;
                break;
            }
            case 3: {
                bl = false;
                n = n2;
                break;
            }
            case 1: {
                bl = false;
            }
            case 2: {
                int n4 = motionEvent.findPointerIndex(n);
                if (n4 < 0) {
                    bl = false;
                    n = n2;
                    break;
                }
                n = (int)motionEvent.getX(n4);
                int n5 = (int)motionEvent.getY(n4);
                n4 = this.pointToPosition(n, n5);
                if (n4 == -1) {
                    n = 1;
                    break;
                }
                View view = this.getChildAt(n4 - this.getFirstVisiblePosition());
                this.setPressedItem(view, n4, n, n5);
                bl2 = true;
                n = n2;
                bl = bl2;
                if (n3 != 1) break;
                this.clickPressedItem(view, n4);
                n = n2;
                bl = bl2;
            }
        }
        if (!bl || n != 0) {
            this.clearPressedItem();
        }
        if (bl) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new ListViewAutoScrollHelper(this);
            }
            this.mScrollHelper.setEnabled(true);
            this.mScrollHelper.onTouch((View)this, motionEvent);
            return bl;
        } else {
            if (this.mScrollHelper == null) return bl;
            {
                this.mScrollHelper.setEnabled(false);
                return bl;
            }
        }
    }

    void setListSelectionHidden(boolean bl) {
        this.mListSelectionHidden = bl;
    }

    @Override
    protected boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState || super.touchModeDrawsInPressedStateCompat();
    }
}

