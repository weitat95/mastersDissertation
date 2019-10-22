/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

public abstract class ActionBar {
    public boolean closeOptionsMenu() {
        return false;
    }

    public boolean collapseActionView() {
        return false;
    }

    public void dispatchMenuVisibilityChanged(boolean bl) {
    }

    public abstract int getDisplayOptions();

    public int getHideOffset() {
        return 0;
    }

    public Context getThemedContext() {
        return null;
    }

    public abstract void hide();

    public boolean invalidateOptionsMenu() {
        return false;
    }

    public abstract boolean isShowing();

    public void onConfigurationChanged(Configuration configuration) {
    }

    void onDestroy() {
    }

    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        return false;
    }

    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public boolean openOptionsMenu() {
        return false;
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
    }

    public abstract void setDisplayHomeAsUpEnabled(boolean var1);

    public abstract void setDisplayShowCustomEnabled(boolean var1);

    public abstract void setDisplayShowHomeEnabled(boolean var1);

    public void setElevation(float f) {
        if (f != 0.0f) {
            throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
        }
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl) {
            throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
        }
    }

    public void setHomeActionContentDescription(int n) {
    }

    public void setHomeAsUpIndicator(int n) {
    }

    public void setHomeAsUpIndicator(Drawable drawable2) {
    }

    public void setHomeButtonEnabled(boolean bl) {
    }

    public abstract void setIcon(int var1);

    public void setShowHideAnimationEnabled(boolean bl) {
    }

    public abstract void setTitle(int var1);

    public abstract void setTitle(CharSequence var1);

    public void setWindowTitle(CharSequence charSequence) {
    }

    public abstract void show();

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return null;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity = 0;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBarLayout);
            this.gravity = context.getInt(R.styleable.ActionBarLayout_android_layout_gravity, 0);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public static interface OnMenuVisibilityListener {
        public void onMenuVisibilityChanged(boolean var1);
    }

    @Deprecated
    public static abstract class Tab {
        public abstract CharSequence getContentDescription();

        public abstract View getCustomView();

        public abstract Drawable getIcon();

        public abstract CharSequence getText();

        public abstract void select();
    }

}

