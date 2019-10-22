/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.MenuItem
 */
package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.MenuItem;

public final class MenuItemCompat {
    static final MenuVersionImpl IMPL = Build.VERSION.SDK_INT >= 26 ? new MenuItemCompatApi26Impl() : new MenuItemCompatBaseImpl();

    public static MenuItem setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem)menuItem).setSupportActionProvider(actionProvider);
        }
        Log.w((String)"MenuItemCompat", (String)"setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    public static void setAlphabeticShortcut(MenuItem menuItem, char c, int n) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setAlphabeticShortcut(c, n);
            return;
        }
        IMPL.setAlphabeticShortcut(menuItem, c, n);
    }

    public static void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setContentDescription(charSequence);
            return;
        }
        IMPL.setContentDescription(menuItem, charSequence);
    }

    public static void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setIconTintList(colorStateList);
            return;
        }
        IMPL.setIconTintList(menuItem, colorStateList);
    }

    public static void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setIconTintMode(mode);
            return;
        }
        IMPL.setIconTintMode(menuItem, mode);
    }

    public static void setNumericShortcut(MenuItem menuItem, char c, int n) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setNumericShortcut(c, n);
            return;
        }
        IMPL.setNumericShortcut(menuItem, c, n);
    }

    public static void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem)menuItem).setTooltipText(charSequence);
            return;
        }
        IMPL.setTooltipText(menuItem, charSequence);
    }

    static class MenuItemCompatApi26Impl
    extends MenuItemCompatBaseImpl {
        MenuItemCompatApi26Impl() {
        }

        @Override
        public void setAlphabeticShortcut(MenuItem menuItem, char c, int n) {
            menuItem.setAlphabeticShortcut(c, n);
        }

        @Override
        public void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
            menuItem.setContentDescription(charSequence);
        }

        @Override
        public void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
            menuItem.setIconTintList(colorStateList);
        }

        @Override
        public void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
            menuItem.setIconTintMode(mode);
        }

        @Override
        public void setNumericShortcut(MenuItem menuItem, char c, int n) {
            menuItem.setNumericShortcut(c, n);
        }

        @Override
        public void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
            menuItem.setTooltipText(charSequence);
        }
    }

    static class MenuItemCompatBaseImpl
    implements MenuVersionImpl {
        MenuItemCompatBaseImpl() {
        }

        @Override
        public void setAlphabeticShortcut(MenuItem menuItem, char c, int n) {
        }

        @Override
        public void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
        }

        @Override
        public void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
        }

        @Override
        public void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
        }

        @Override
        public void setNumericShortcut(MenuItem menuItem, char c, int n) {
        }

        @Override
        public void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
        }
    }

    static interface MenuVersionImpl {
        public void setAlphabeticShortcut(MenuItem var1, char var2, int var3);

        public void setContentDescription(MenuItem var1, CharSequence var2);

        public void setIconTintList(MenuItem var1, ColorStateList var2);

        public void setIconTintMode(MenuItem var1, PorterDuff.Mode var2);

        public void setNumericShortcut(MenuItem var1, char var2, int var3);

        public void setTooltipText(MenuItem var1, CharSequence var2);
    }

}

