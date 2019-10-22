/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuPresenter;
import android.view.Menu;
import android.view.Window;

public interface DecorContentParent {
    public boolean canShowOverflowMenu();

    public void dismissPopups();

    public boolean hideOverflowMenu();

    public void initFeature(int var1);

    public boolean isOverflowMenuShowPending();

    public boolean isOverflowMenuShowing();

    public void setMenu(Menu var1, MenuPresenter.Callback var2);

    public void setMenuPrepared();

    public void setWindowCallback(Window.Callback var1);

    public void setWindowTitle(CharSequence var1);

    public boolean showOverflowMenu();
}

