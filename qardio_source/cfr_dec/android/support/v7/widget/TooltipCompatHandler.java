/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnHoverListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityManager
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.TooltipPopup;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;

class TooltipCompatHandler
implements View.OnAttachStateChangeListener,
View.OnHoverListener,
View.OnLongClickListener {
    private static TooltipCompatHandler sActiveHandler;
    private final View mAnchor;
    private int mAnchorX;
    private int mAnchorY;
    private boolean mFromTouch;
    private final Runnable mHideRunnable;
    private TooltipPopup mPopup;
    private final Runnable mShowRunnable = new Runnable(){

        @Override
        public void run() {
            TooltipCompatHandler.this.show(false);
        }
    };
    private final CharSequence mTooltipText;

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.mHideRunnable = new Runnable(){

            @Override
            public void run() {
                TooltipCompatHandler.this.hide();
            }
        };
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mAnchor.setOnLongClickListener((View.OnLongClickListener)this);
        this.mAnchor.setOnHoverListener((View.OnHoverListener)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            if (this.mPopup != null) {
                this.mPopup.hide();
                this.mPopup = null;
                this.mAnchor.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
            } else {
                Log.e((String)"TooltipCompatHandler", (String)"sActiveHandler.mPopup == null");
            }
        }
        this.mAnchor.removeCallbacks(this.mShowRunnable);
        this.mAnchor.removeCallbacks(this.mHideRunnable);
    }

    public static void setTooltipText(View view, CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            if (sActiveHandler != null && TooltipCompatHandler.sActiveHandler.mAnchor == view) {
                sActiveHandler.hide();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        new TooltipCompatHandler(view, charSequence);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void show(boolean bl) {
        if (!ViewCompat.isAttachedToWindow(this.mAnchor)) {
            return;
        }
        if (sActiveHandler != null) {
            sActiveHandler.hide();
        }
        sActiveHandler = this;
        this.mFromTouch = bl;
        this.mPopup = new TooltipPopup(this.mAnchor.getContext());
        this.mPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
        this.mAnchor.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        long l = this.mFromTouch ? 2500L : ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1 ? 3000L - (long)ViewConfiguration.getLongPressTimeout() : 15000L - (long)ViewConfiguration.getLongPressTimeout());
        this.mAnchor.removeCallbacks(this.mHideRunnable);
        this.mAnchor.postDelayed(this.mHideRunnable, l);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        view = (AccessibilityManager)this.mAnchor.getContext().getSystemService("accessibility");
        if (view.isEnabled()) {
            if (view.isTouchExplorationEnabled()) return false;
        }
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 7: {
                if (!this.mAnchor.isEnabled()) return false;
                if (this.mPopup != null) return false;
                this.mAnchorX = (int)motionEvent.getX();
                this.mAnchorY = (int)motionEvent.getY();
                this.mAnchor.removeCallbacks(this.mShowRunnable);
                this.mAnchor.postDelayed(this.mShowRunnable, (long)ViewConfiguration.getLongPressTimeout());
                return false;
            }
            case 10: 
        }
        this.hide();
        return false;
    }

    public boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        this.show(true);
        return true;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        this.hide();
    }

}

