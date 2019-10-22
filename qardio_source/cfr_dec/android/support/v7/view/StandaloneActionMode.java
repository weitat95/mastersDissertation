/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v7.view;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

public class StandaloneActionMode
extends ActionMode
implements MenuBuilder.Callback {
    private ActionMode.Callback mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private MenuBuilder mMenu;

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean bl) {
        this.mContext = context;
        this.mContextView = actionBarContextView;
        this.mCallback = callback;
        this.mMenu = new MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
        this.mFocusable = bl;
    }

    @Override
    public void finish() {
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        this.mContextView.sendAccessibilityEvent(32);
        this.mCallback.onDestroyActionMode(this);
    }

    @Override
    public View getCustomView() {
        if (this.mCustomView != null) {
            return (View)this.mCustomView.get();
        }
        return null;
    }

    @Override
    public Menu getMenu() {
        return this.mMenu;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mContextView.getSubtitle();
    }

    @Override
    public CharSequence getTitle() {
        return this.mContextView.getTitle();
    }

    @Override
    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    @Override
    public boolean isTitleOptional() {
        return this.mContextView.isTitleOptional();
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem);
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        this.invalidate();
        this.mContextView.showOverflowMenu();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void setCustomView(View object) {
        void var1_3;
        this.mContextView.setCustomView((View)object);
        if (object != null) {
            WeakReference<View> weakReference = new WeakReference<View>((View)object);
        } else {
            Object var1_4 = null;
        }
        this.mCustomView = var1_3;
    }

    @Override
    public void setSubtitle(int n) {
        this.setSubtitle(this.mContext.getString(n));
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mContextView.setSubtitle(charSequence);
    }

    @Override
    public void setTitle(int n) {
        this.setTitle(this.mContext.getString(n));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mContextView.setTitle(charSequence);
    }

    @Override
    public void setTitleOptionalHint(boolean bl) {
        super.setTitleOptionalHint(bl);
        this.mContextView.setTitleOptional(bl);
    }
}

