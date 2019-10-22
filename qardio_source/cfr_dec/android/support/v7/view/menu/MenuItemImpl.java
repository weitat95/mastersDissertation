/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.ViewDebug
 *  android.view.ViewDebug$CapturedViewProperty
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package android.support.v7.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public final class MenuItemImpl
implements SupportMenuItem {
    private static String sDeleteShortcutLabel;
    private static String sEnterShortcutLabel;
    private static String sPrependShortcutLabel;
    private static String sSpaceShortcutLabel;
    private ActionProvider mActionProvider;
    private View mActionView;
    private final int mCategoryOrder;
    private MenuItem.OnMenuItemClickListener mClickListener;
    private CharSequence mContentDescription;
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
    private boolean mIsActionViewExpanded = false;
    private Runnable mItemCallback;
    MenuBuilder mMenu;
    private ContextMenu.ContextMenuInfo mMenuInfo;
    private boolean mNeedToApplyIconTint = false;
    private MenuItem.OnActionExpandListener mOnActionExpandListener;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private int mShortcutAlphabeticModifiers = 4096;
    private char mShortcutNumericChar;
    private int mShortcutNumericModifiers = 4096;
    private int mShowAsAction = 0;
    private SubMenuBuilder mSubMenu;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;
    private CharSequence mTooltipText;

    MenuItemImpl(MenuBuilder menuBuilder, int n, int n2, int n3, int n4, CharSequence charSequence, int n5) {
        this.mMenu = menuBuilder;
        this.mId = n2;
        this.mGroup = n;
        this.mCategoryOrder = n3;
        this.mOrdering = n4;
        this.mTitle = charSequence;
        this.mShowAsAction = n5;
    }

    private Drawable applyIconTintIfNecessary(Drawable drawable2) {
        Drawable drawable3;
        block5: {
            block6: {
                drawable3 = drawable2;
                if (drawable2 == null) break block5;
                drawable3 = drawable2;
                if (!this.mNeedToApplyIconTint) break block5;
                if (this.mHasIconTint) break block6;
                drawable3 = drawable2;
                if (!this.mHasIconTintMode) break block5;
            }
            drawable3 = DrawableCompat.wrap(drawable2).mutate();
            if (this.mHasIconTint) {
                DrawableCompat.setTintList(drawable3, this.mIconTintList);
            }
            if (this.mHasIconTintMode) {
                DrawableCompat.setTintMode(drawable3, this.mIconTintMode);
            }
            this.mNeedToApplyIconTint = false;
        }
        return drawable3;
    }

    public void actionFormatChanged() {
        this.mMenu.onItemActionRequestChanged(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean collapseActionView() {
        block5: {
            block4: {
                if ((this.mShowAsAction & 8) == 0) break block4;
                if (this.mActionView == null) {
                    return true;
                }
                if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionCollapse((MenuItem)this)) break block5;
            }
            return false;
        }
        return this.mMenu.collapseItemActionView(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean expandActionView() {
        if (!this.hasCollapsibleActionView() || this.mOnActionExpandListener != null && !this.mOnActionExpandListener.onMenuItemActionExpand((MenuItem)this)) {
            return false;
        }
        return this.mMenu.expandItemActionView(this);
    }

    public android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override
    public View getActionView() {
        if (this.mActionView != null) {
            return this.mActionView;
        }
        if (this.mActionProvider != null) {
            this.mActionView = this.mActionProvider.onCreateActionView(this);
            return this.mActionView;
        }
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
        if (this.mIconDrawable != null) {
            return this.applyIconTintIfNecessary(this.mIconDrawable);
        }
        if (this.mIconResId != 0) {
            Drawable drawable2 = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId);
            this.mIconResId = 0;
            this.mIconDrawable = drawable2;
            return this.applyIconTintIfNecessary(drawable2);
        }
        return null;
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

    @ViewDebug.CapturedViewProperty
    public int getItemId() {
        return this.mId;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.mMenuInfo;
    }

    @Override
    public int getNumericModifiers() {
        return this.mShortcutNumericModifiers;
    }

    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public int getOrder() {
        return this.mCategoryOrder;
    }

    public int getOrdering() {
        return this.mOrdering;
    }

    char getShortcut() {
        if (this.mMenu.isQwertyMode()) {
            return this.mShortcutAlphabeticChar;
        }
        return this.mShortcutNumericChar;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    String getShortcutLabel() {
        char c = this.getShortcut();
        if (c == '\u0000') {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(sPrependShortcutLabel);
        switch (c) {
            default: {
                stringBuilder.append(c);
                do {
                    return stringBuilder.toString();
                    break;
                } while (true);
            }
            case '\n': {
                stringBuilder.append(sEnterShortcutLabel);
                return stringBuilder.toString();
            }
            case '\b': {
                stringBuilder.append(sDeleteShortcutLabel);
                return stringBuilder.toString();
            }
            case ' ': 
        }
        stringBuilder.append(sSpaceShortcutLabel);
        return stringBuilder.toString();
    }

    public SubMenu getSubMenu() {
        return this.mSubMenu;
    }

    @Override
    public ActionProvider getSupportActionProvider() {
        return this.mActionProvider;
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getTitle() {
        return this.mTitle;
    }

    /*
     * Enabled aggressive block sorting
     */
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle;
        CharSequence charSequence2 = charSequence;
        if (Build.VERSION.SDK_INT >= 18) return charSequence2;
        charSequence2 = charSequence;
        if (charSequence == null) return charSequence2;
        charSequence2 = charSequence;
        if (charSequence instanceof String) return charSequence2;
        return charSequence.toString();
    }

    CharSequence getTitleForItemView(MenuView.ItemView itemView) {
        if (itemView != null && itemView.prefersCondensedTitle()) {
            return this.getTitleCondensed();
        }
        return this.getTitle();
    }

    @Override
    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public boolean hasCollapsibleActionView() {
        boolean bl;
        boolean bl2 = bl = false;
        if ((this.mShowAsAction & 8) != 0) {
            if (this.mActionView == null && this.mActionProvider != null) {
                this.mActionView = this.mActionProvider.onCreateActionView(this);
            }
            bl2 = bl;
            if (this.mActionView != null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public boolean hasSubMenu() {
        return this.mSubMenu != null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean invoke() {
        block8: {
            block7: {
                if (this.mClickListener != null && this.mClickListener.onMenuItemClick((MenuItem)this) || this.mMenu.dispatchMenuItemSelected(this.mMenu, this)) break block7;
                if (this.mItemCallback != null) {
                    this.mItemCallback.run();
                    return true;
                }
                if (this.mIntent != null) {
                    try {
                        this.mMenu.getContext().startActivity(this.mIntent);
                        return true;
                    }
                    catch (ActivityNotFoundException activityNotFoundException) {
                        Log.e((String)"MenuItemImpl", (String)"Can't find activity to handle intent; ignoring", (Throwable)activityNotFoundException);
                    }
                }
                if (this.mActionProvider == null || !this.mActionProvider.onPerformDefaultAction()) break block8;
            }
            return true;
        }
        return false;
    }

    public boolean isActionButton() {
        return (this.mFlags & 0x20) == 32;
    }

    @Override
    public boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded;
    }

    public boolean isCheckable() {
        return (this.mFlags & 1) == 1;
    }

    public boolean isChecked() {
        return (this.mFlags & 2) == 2;
    }

    public boolean isEnabled() {
        return (this.mFlags & 0x10) != 0;
    }

    public boolean isExclusiveCheckable() {
        return (this.mFlags & 4) != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isVisible() {
        if (this.mActionProvider != null && this.mActionProvider.overridesItemVisibility()) {
            if ((this.mFlags & 8) == 0 && this.mActionProvider.isVisible()) return true;
            return false;
        }
        if ((this.mFlags & 8) != 0) return false;
        return true;
    }

    public boolean requestsActionButton() {
        return (this.mShowAsAction & 1) == 1;
    }

    public boolean requiresActionButton() {
        return (this.mShowAsAction & 2) == 2;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override
    public SupportMenuItem setActionView(int n) {
        Context context = this.mMenu.getContext();
        this.setActionView(LayoutInflater.from((Context)context).inflate(n, (ViewGroup)new LinearLayout(context), false));
        return this;
    }

    @Override
    public SupportMenuItem setActionView(View view) {
        this.mActionView = view;
        this.mActionProvider = null;
        if (view != null && view.getId() == -1 && this.mId > 0) {
            view.setId(this.mId);
        }
        this.mMenu.onItemActionRequestChanged(this);
        return this;
    }

    public void setActionViewExpanded(boolean bl) {
        this.mIsActionViewExpanded = bl;
        this.mMenu.onItemsChanged(false);
    }

    public MenuItem setAlphabeticShortcut(char c) {
        if (this.mShortcutAlphabeticChar == c) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c, int n) {
        if (this.mShortcutAlphabeticChar == c && this.mShortcutAlphabeticModifiers == n) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = this.mFlags;
        int n3 = bl ? 1 : 0;
        this.mFlags = n3 | n2 & 0xFFFFFFFE;
        if (n != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
        return this;
    }

    public MenuItem setChecked(boolean bl) {
        if ((this.mFlags & 4) != 0) {
            this.mMenu.setExclusiveItemChecked(this);
            return this;
        }
        this.setCheckedInt(bl);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    void setCheckedInt(boolean bl) {
        int n = this.mFlags;
        int n2 = this.mFlags;
        int n3 = bl ? 2 : 0;
        this.mFlags = n3 | n2 & 0xFFFFFFFD;
        if (n != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
    }

    @Override
    public SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItem setEnabled(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 0x10) : (this.mFlags &= 0xFFFFFFEF);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setExclusiveCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 4 : 0;
        this.mFlags = n2 | n & 0xFFFFFFFB;
    }

    public MenuItem setIcon(int n) {
        this.mIconDrawable = null;
        this.mIconResId = n;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable2) {
        this.mIconResId = 0;
        this.mIconDrawable = drawable2;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public void setIsActionButton(boolean bl) {
        if (bl) {
            this.mFlags |= 0x20;
            return;
        }
        this.mFlags &= 0xFFFFFFDF;
    }

    void setMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mMenuInfo = contextMenuInfo;
    }

    public MenuItem setNumericShortcut(char c) {
        if (this.mShortcutNumericChar == c) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c, int n) {
        if (this.mShortcutNumericChar == c && this.mShortcutNumericModifiers == n) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        this.mShortcutNumericChar = c;
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2, int n, int n2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public void setShowAsAction(int n) {
        switch (n & 3) {
            default: {
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
            }
            case 0: 
            case 1: 
            case 2: 
        }
        this.mShowAsAction = n;
        this.mMenu.onItemActionRequestChanged(this);
    }

    @Override
    public SupportMenuItem setShowAsActionFlags(int n) {
        this.setShowAsAction(n);
        return this;
    }

    public void setSubMenu(SubMenuBuilder subMenuBuilder) {
        this.mSubMenu = subMenuBuilder;
        subMenuBuilder.setHeaderTitle(this.getTitle());
    }

    @Override
    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        if (this.mActionProvider != null) {
            this.mActionProvider.reset();
        }
        this.mActionView = null;
        this.mActionProvider = actionProvider;
        this.mMenu.onItemsChanged(true);
        if (this.mActionProvider != null) {
            this.mActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener(){

                @Override
                public void onActionProviderVisibilityChanged(boolean bl) {
                    MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
                }
            });
        }
        return this;
    }

    public MenuItem setTitle(int n) {
        return this.setTitle(this.mMenu.getContext().getString(n));
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mMenu.onItemsChanged(false);
        if (this.mSubMenu != null) {
            this.mSubMenu.setHeaderTitle(charSequence);
        }
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        if (charSequence == null) {
            charSequence = this.mTitle;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setVisible(boolean bl) {
        if (this.setVisibleInt(bl)) {
            this.mMenu.onItemVisibleChanged(this);
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean setVisibleInt(boolean bl) {
        boolean bl2 = false;
        int n = this.mFlags;
        int n2 = this.mFlags;
        int n3 = bl ? 0 : 8;
        this.mFlags = n3 | n2 & 0xFFFFFFF7;
        bl = bl2;
        if (n == this.mFlags) return bl;
        return true;
    }

    public boolean shouldShowIcon() {
        return this.mMenu.getOptionalIconsVisible();
    }

    boolean shouldShowShortcut() {
        return this.mMenu.isShortcutsVisible() && this.getShortcut() != '\u0000';
    }

    public boolean showsTextAsAction() {
        return (this.mShowAsAction & 4) == 4;
    }

    public String toString() {
        if (this.mTitle != null) {
            return this.mTitle.toString();
        }
        return null;
    }

}

