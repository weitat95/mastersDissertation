/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.Menu
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;

public interface DecorToolbar {
    public boolean canShowOverflowMenu();

    public void collapseActionView();

    public void dismissPopupMenus();

    public Context getContext();

    public int getDisplayOptions();

    public Menu getMenu();

    public int getNavigationMode();

    public CharSequence getTitle();

    public ViewGroup getViewGroup();

    public int getVisibility();

    public boolean hasExpandedActionView();

    public boolean hideOverflowMenu();

    public void initIndeterminateProgress();

    public void initProgress();

    public boolean isOverflowMenuShowPending();

    public boolean isOverflowMenuShowing();

    public void setCollapsible(boolean var1);

    public void setDisplayOptions(int var1);

    public void setEmbeddedTabView(ScrollingTabContainerView var1);

    public void setHomeButtonEnabled(boolean var1);

    public void setIcon(int var1);

    public void setIcon(Drawable var1);

    public void setLogo(int var1);

    public void setMenu(Menu var1, MenuPresenter.Callback var2);

    public void setMenuCallbacks(MenuPresenter.Callback var1, MenuBuilder.Callback var2);

    public void setMenuPrepared();

    public void setNavigationContentDescription(int var1);

    public void setNavigationIcon(int var1);

    public void setNavigationIcon(Drawable var1);

    public void setTitle(CharSequence var1);

    public void setVisibility(int var1);

    public void setWindowCallback(Window.Callback var1);

    public void setWindowTitle(CharSequence var1);

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int var1, long var2);

    public boolean showOverflowMenu();
}

