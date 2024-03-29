/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v4.internal.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

public interface SupportMenuItem
extends MenuItem {
    public boolean collapseActionView();

    public boolean expandActionView();

    public View getActionView();

    public int getAlphabeticModifiers();

    public CharSequence getContentDescription();

    public ColorStateList getIconTintList();

    public PorterDuff.Mode getIconTintMode();

    public int getNumericModifiers();

    public ActionProvider getSupportActionProvider();

    public CharSequence getTooltipText();

    public boolean isActionViewExpanded();

    public MenuItem setActionView(int var1);

    public MenuItem setActionView(View var1);

    public MenuItem setAlphabeticShortcut(char var1, int var2);

    public SupportMenuItem setContentDescription(CharSequence var1);

    public MenuItem setIconTintList(ColorStateList var1);

    public MenuItem setIconTintMode(PorterDuff.Mode var1);

    public MenuItem setNumericShortcut(char var1, int var2);

    public MenuItem setShortcut(char var1, char var2, int var3, int var4);

    public void setShowAsAction(int var1);

    public MenuItem setShowAsActionFlags(int var1);

    public SupportMenuItem setSupportActionProvider(ActionProvider var1);

    public SupportMenuItem setTooltipText(CharSequence var1);
}

