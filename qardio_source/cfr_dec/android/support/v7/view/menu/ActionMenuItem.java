/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.view.ActionProvider
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class ActionMenuItem
implements SupportMenuItem {
    private final int mCategoryOrder;
    private MenuItem.OnMenuItemClickListener mClickListener;
    private CharSequence mContentDescription;
    private Context mContext;
    private int mFlags = 16;
    private final int mGroup;
    private boolean mHasIconTint = false;
    private boolean mHasIconTintMode = false;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private ColorStateList mIconTintList = null;
    private PorterDuff.Mode mIconTintMode = null;
    private final int mId;
    private Intent mIntent;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private int mShortcutAlphabeticModifiers = 4096;
    private char mShortcutNumericChar;
    private int mShortcutNumericModifiers = 4096;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;
    private CharSequence mTooltipText;

    public ActionMenuItem(Context context, int n, int n2, int n3, int n4, CharSequence charSequence) {
        this.mContext = context;
        this.mId = n2;
        this.mGroup = n;
        this.mCategoryOrder = n3;
        this.mOrdering = n4;
        this.mTitle = charSequence;
    }

    private void applyIconTint() {
        if (this.mIconDrawable != null && (this.mHasIconTint || this.mHasIconTintMode)) {
            this.mIconDrawable = DrawableCompat.wrap(this.mIconDrawable);
            this.mIconDrawable = this.mIconDrawable.mutate();
            if (this.mHasIconTint) {
                DrawableCompat.setTintList(this.mIconDrawable, this.mIconTintList);
            }
            if (this.mHasIconTintMode) {
                DrawableCompat.setTintMode(this.mIconDrawable, this.mIconTintMode);
            }
        }
    }

    @Override
    public boolean collapseActionView() {
        return false;
    }

    @Override
    public boolean expandActionView() {
        return false;
    }

    public android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override
    public View getActionView() {
        return null;
    }

    @Override
    public int getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers;
    }

    public char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public int getGroupId() {
        return this.mGroup;
    }

    public Drawable getIcon() {
        return this.mIconDrawable;
    }

    @Override
    public ColorStateList getIconTintList() {
        return this.mIconTintList;
    }

    @Override
    public PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getItemId() {
        return this.mId;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override
    public int getNumericModifiers() {
        return this.mShortcutNumericModifiers;
    }

    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public int getOrder() {
        return this.mOrdering;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    @Override
    public ActionProvider getSupportActionProvider() {
        return null;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getTitleCondensed() {
        if (this.mTitleCondensed != null) {
            return this.mTitleCondensed;
        }
        return this.mTitle;
    }

    @Override
    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public boolean hasSubMenu() {
        return false;
    }

    @Override
    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isChecked() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isEnabled() {
        return (this.mFlags & 0x10) != 0;
    }

    public boolean isVisible() {
        return (this.mFlags & 8) == 0;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SupportMenuItem setActionView(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SupportMenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setAlphabeticShortcut(char c) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c, int n) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 1 : 0;
        this.mFlags = n2 | n & 0xFFFFFFFE;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setChecked(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 2 : 0;
        this.mFlags = n2 | n & 0xFFFFFFFD;
        return this;
    }

    @Override
    public SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setEnabled(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 16 : 0;
        this.mFlags = n2 | n & 0xFFFFFFEF;
        return this;
    }

    public MenuItem setIcon(int n) {
        this.mIconResId = n;
        this.mIconDrawable = ContextCompat.getDrawable(this.mContext, n);
        this.applyIconTint();
        return this;
    }

    public MenuItem setIcon(Drawable drawable2) {
        this.mIconDrawable = drawable2;
        this.mIconResId = 0;
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.applyIconTint();
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        this.mShortcutNumericChar = c;
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c, int n) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        this.mShortcutNumericChar = c;
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2, int n, int n2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n2);
        return this;
    }

    @Override
    public void setShowAsAction(int n) {
    }

    @Override
    public SupportMenuItem setShowAsActionFlags(int n) {
        this.setShowAsAction(n);
        return this;
    }

    @Override
    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setTitle(int n) {
        this.mTitle = this.mContext.getResources().getString(n);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        return this;
    }

    @Override
    public SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setVisible(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 0 : 8;
        this.mFlags = n2 | n & 8;
        return this;
    }
}

