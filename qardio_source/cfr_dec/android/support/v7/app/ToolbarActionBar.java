/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.util.ArrayList;

class ToolbarActionBar
extends ActionBar {
    DecorToolbar mDecorToolbar;
    private boolean mLastMenuVisibility;
    private boolean mMenuCallbackSet;
    private final Toolbar.OnMenuItemClickListener mMenuClicker;
    private final Runnable mMenuInvalidator;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
    boolean mToolbarMenuPrepared;
    Window.Callback mWindowCallback;

    ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        this.mMenuInvalidator = new Runnable(){

            @Override
            public void run() {
                ToolbarActionBar.this.populateOptionsMenu();
            }
        };
        this.mMenuClicker = new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem);
            }
        };
        this.mDecorToolbar = new ToolbarWidgetWrapper(toolbar, false);
        this.mWindowCallback = new ToolbarCallbackWrapper(callback);
        this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
        toolbar.setOnMenuItemClickListener(this.mMenuClicker);
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    private Menu getMenu() {
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
            this.mMenuCallbackSet = true;
        }
        return this.mDecorToolbar.getMenu();
    }

    @Override
    public boolean closeOptionsMenu() {
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override
    public boolean collapseActionView() {
        if (this.mDecorToolbar.hasExpandedActionView()) {
            this.mDecorToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void dispatchMenuVisibilityChanged(boolean bl) {
        if (bl != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = bl;
            int n = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < n; ++i) {
                this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(bl);
            }
        }
    }

    @Override
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override
    public Context getThemedContext() {
        return this.mDecorToolbar.getContext();
    }

    public Window.Callback getWrappedWindowCallback() {
        return this.mWindowCallback;
    }

    @Override
    public void hide() {
        this.mDecorToolbar.setVisibility(8);
    }

    @Override
    public boolean invalidateOptionsMenu() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
        ViewCompat.postOnAnimation((View)this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
        return true;
    }

    @Override
    public boolean isShowing() {
        return this.mDecorToolbar.getVisibility() == 0;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override
    void onDestroy() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        boolean bl = false;
        Menu menu2 = this.getMenu();
        if (menu2 == null) return bl;
        int n2 = keyEvent != null ? keyEvent.getDeviceId() : -1;
        bl = KeyCharacterMap.load((int)n2).getKeyboardType() != 1;
        menu2.setQwertyMode(bl);
        return menu2.performShortcut(n, keyEvent, 0);
    }

    @Override
    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            this.openOptionsMenu();
        }
        return true;
    }

    @Override
    public boolean openOptionsMenu() {
        return this.mDecorToolbar.showOverflowMenu();
    }

    void populateOptionsMenu() {
        MenuBuilder menuBuilder = null;
        Menu menu2 = this.getMenu();
        if (menu2 instanceof MenuBuilder) {
            menuBuilder = (MenuBuilder)menu2;
        }
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged();
        }
        try {
            menu2.clear();
            if (!this.mWindowCallback.onCreatePanelMenu(0, menu2) || !this.mWindowCallback.onPreparePanel(0, null, menu2)) {
                menu2.clear();
            }
            return;
        }
        finally {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
        }
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayHomeAsUpEnabled(boolean bl) {
        int n = bl ? 4 : 0;
        this.setDisplayOptions(n, 4);
    }

    public void setDisplayOptions(int n, int n2) {
        int n3 = this.mDecorToolbar.getDisplayOptions();
        this.mDecorToolbar.setDisplayOptions(n & n2 | ~n2 & n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayShowCustomEnabled(boolean bl) {
        int n = bl ? 16 : 0;
        this.setDisplayOptions(n, 16);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayShowHomeEnabled(boolean bl) {
        int n = bl ? 2 : 0;
        this.setDisplayOptions(n, 2);
    }

    @Override
    public void setElevation(float f) {
        ViewCompat.setElevation((View)this.mDecorToolbar.getViewGroup(), f);
    }

    @Override
    public void setHomeActionContentDescription(int n) {
        this.mDecorToolbar.setNavigationContentDescription(n);
    }

    @Override
    public void setHomeAsUpIndicator(int n) {
        this.mDecorToolbar.setNavigationIcon(n);
    }

    @Override
    public void setHomeAsUpIndicator(Drawable drawable2) {
        this.mDecorToolbar.setNavigationIcon(drawable2);
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
    }

    @Override
    public void setIcon(int n) {
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setTitle(int n) {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        CharSequence charSequence = n != 0 ? this.mDecorToolbar.getContext().getText(n) : null;
        decorToolbar.setTitle(charSequence);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override
    public void show() {
        this.mDecorToolbar.setVisibility(0);
    }

    private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        private boolean mClosingActionMenu;

        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            if (this.mClosingActionMenu) {
                return;
            }
            this.mClosingActionMenu = true;
            ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
            if (ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)menuBuilder);
            }
            this.mClosingActionMenu = false;
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)menuBuilder);
                return true;
            }
            return false;
        }
    }

    private final class MenuBuilderCallback
    implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback == null) return;
            {
                if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                    ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)menuBuilder);
                    return;
                } else {
                    if (!ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, (Menu)menuBuilder)) return;
                    {
                        ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)menuBuilder);
                        return;
                    }
                }
            }
        }
    }

    private class ToolbarCallbackWrapper
    extends WindowCallbackWrapper {
        public ToolbarCallbackWrapper(Window.Callback callback) {
            super(callback);
        }

        @Override
        public View onCreatePanelView(int n) {
            if (n == 0) {
                return new View(ToolbarActionBar.this.mDecorToolbar.getContext());
            }
            return super.onCreatePanelView(n);
        }

        @Override
        public boolean onPreparePanel(int n, View view, Menu menu2) {
            boolean bl = super.onPreparePanel(n, view, menu2);
            if (bl && !ToolbarActionBar.this.mToolbarMenuPrepared) {
                ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
                ToolbarActionBar.this.mToolbarMenuPrepared = true;
            }
            return bl;
        }
    }

}

