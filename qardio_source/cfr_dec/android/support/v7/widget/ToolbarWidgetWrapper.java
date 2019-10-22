/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;

public class ToolbarWidgetWrapper
implements DecorToolbar {
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription = 0;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode = 0;
    private CharSequence mSubtitle;
    private View mTabView;
    CharSequence mTitle;
    private boolean mTitleSet;
    Toolbar mToolbar;
    Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean bl) {
        this(toolbar, bl, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_material);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ToolbarWidgetWrapper(Toolbar object, boolean bl, int n, int n2) {
        this.mToolbar = object;
        this.mTitle = ((Toolbar)((Object)object)).getTitle();
        this.mSubtitle = ((Toolbar)((Object)object)).getSubtitle();
        boolean bl2 = this.mTitle != null;
        this.mTitleSet = bl2;
        this.mNavIcon = ((Toolbar)((Object)object)).getNavigationIcon();
        object = TintTypedArray.obtainStyledAttributes(object.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.mDefaultNavigationIcon = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
        if (bl) {
            CharSequence charSequence = ((TintTypedArray)object).getText(R.styleable.ActionBar_title);
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                this.setTitle(charSequence);
            }
            if (!TextUtils.isEmpty((CharSequence)(charSequence = ((TintTypedArray)object).getText(R.styleable.ActionBar_subtitle)))) {
                this.setSubtitle(charSequence);
            }
            if ((charSequence = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_logo)) != null) {
                this.setLogo((Drawable)charSequence);
            }
            if ((charSequence = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_icon)) != null) {
                this.setIcon((Drawable)charSequence);
            }
            if (this.mNavIcon == null && this.mDefaultNavigationIcon != null) {
                this.setNavigationIcon(this.mDefaultNavigationIcon);
            }
            this.setDisplayOptions(((TintTypedArray)object).getInt(R.styleable.ActionBar_displayOptions, 0));
            n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
            if (n2 != 0) {
                this.setCustomView(LayoutInflater.from((Context)this.mToolbar.getContext()).inflate(n2, (ViewGroup)this.mToolbar, false));
                this.setDisplayOptions(this.mDisplayOpts | 0x10);
            }
            if ((n2 = ((TintTypedArray)object).getLayoutDimension(R.styleable.ActionBar_height, 0)) > 0) {
                charSequence = this.mToolbar.getLayoutParams();
                ((ViewGroup.LayoutParams)charSequence).height = n2;
                this.mToolbar.setLayoutParams((ViewGroup.LayoutParams)charSequence);
            }
            n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
            int n3 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
            if (n2 >= 0 || n3 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(n2, 0), Math.max(n3, 0));
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_titleTextStyle, 0)) != 0) {
                this.mToolbar.setTitleTextAppearance(this.mToolbar.getContext(), n2);
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0)) != 0) {
                this.mToolbar.setSubtitleTextAppearance(this.mToolbar.getContext(), n2);
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_popupTheme, 0)) != 0) {
                this.mToolbar.setPopupTheme(n2);
            }
        } else {
            this.mDisplayOpts = this.detectDisplayOptions();
        }
        ((TintTypedArray)object).recycle();
        this.setDefaultNavigationContentDescription(n);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            final ActionMenuItem mNavItem;
            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback != null && ToolbarWidgetWrapper.this.mMenuPrepared) {
                    ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, (MenuItem)this.mNavItem);
                }
            }
        });
    }

    private int detectDisplayOptions() {
        int n = 11;
        if (this.mToolbar.getNavigationIcon() != null) {
            n = 0xB | 4;
            this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        }
        return n;
    }

    private void setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
        }
    }

    private void updateHomeAccessibility() {
        block3: {
            block2: {
                if ((this.mDisplayOpts & 4) == 0) break block2;
                if (!TextUtils.isEmpty((CharSequence)this.mHomeDescription)) break block3;
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            }
            return;
        }
        this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) == 0) {
            this.mToolbar.setNavigationIcon(null);
            return;
        }
        Toolbar toolbar = this.mToolbar;
        Drawable drawable2 = this.mNavIcon != null ? this.mNavIcon : this.mDefaultNavigationIcon;
        toolbar.setNavigationIcon(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateToolbarLogo() {
        Drawable drawable2 = null;
        if ((this.mDisplayOpts & 2) != 0) {
            drawable2 = (this.mDisplayOpts & 1) != 0 ? (this.mLogo != null ? this.mLogo : this.mIcon) : this.mIcon;
        }
        this.mToolbar.setLogo(drawable2);
    }

    @Override
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override
    public Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override
    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }

    @Override
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override
    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override
    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override
    public void initIndeterminateProgress() {
        Log.i((String)"ToolbarWidgetWrapper", (String)"Progress display unsupported");
    }

    @Override
    public void initProgress() {
        Log.i((String)"ToolbarWidgetWrapper", (String)"Progress display unsupported");
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override
    public void setCollapsible(boolean bl) {
        this.mToolbar.setCollapsible(bl);
    }

    public void setCustomView(View view) {
        if (this.mCustomView != null && (this.mDisplayOpts & 0x10) != 0) {
            this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = view;
        if (view != null && (this.mDisplayOpts & 0x10) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDefaultNavigationContentDescription(int n) {
        block3: {
            block2: {
                if (n == this.mDefaultNavigationContentDescription) break block2;
                this.mDefaultNavigationContentDescription = n;
                if (TextUtils.isEmpty((CharSequence)this.mToolbar.getNavigationContentDescription())) break block3;
            }
            return;
        }
        this.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayOptions(int n) {
        int n2 = this.mDisplayOpts ^ n;
        this.mDisplayOpts = n;
        if (n2 != 0) {
            if ((n2 & 4) != 0) {
                if ((n & 4) != 0) {
                    this.updateHomeAccessibility();
                }
                this.updateNavigationIcon();
            }
            if ((n2 & 3) != 0) {
                this.updateToolbarLogo();
            }
            if ((n2 & 8) != 0) {
                if ((n & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle(null);
                    this.mToolbar.setSubtitle(null);
                }
            }
            if ((n2 & 0x10) != 0 && this.mCustomView != null) {
                if ((n & 0x10) == 0) {
                    this.mToolbar.removeView(this.mCustomView);
                    return;
                }
                this.mToolbar.addView(this.mCustomView);
            }
        }
    }

    @Override
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView);
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.gravity = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setIcon(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setIcon(drawable2);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.updateToolbarLogo();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setLogo(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setLogo(drawable2);
    }

    public void setLogo(Drawable drawable2) {
        this.mLogo = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setMenu(Menu menu2, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((MenuBuilder)menu2, this.mActionMenuPresenter);
    }

    @Override
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationContentDescription(int n) {
        String string2 = n == 0 ? null : this.getContext().getString(n);
        this.setNavigationContentDescription(string2);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationIcon(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setNavigationIcon(drawable2);
    }

    @Override
    public void setNavigationIcon(Drawable drawable2) {
        this.mNavIcon = drawable2;
        this.updateNavigationIcon();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitleSet = true;
        this.setTitleInt(charSequence);
    }

    @Override
    public void setVisibility(int n) {
        this.mToolbar.setVisibility(n);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.mTitleSet) {
            this.setTitleInt(charSequence);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int n, long l) {
        float f;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mToolbar);
        if (n == 0) {
            f = 1.0f;
            do {
                return viewPropertyAnimatorCompat.alpha(f).setDuration(l).setListener(new ViewPropertyAnimatorListenerAdapter(){
                    private boolean mCanceled = false;

                    @Override
                    public void onAnimationCancel(View view) {
                        this.mCanceled = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        if (!this.mCanceled) {
                            ToolbarWidgetWrapper.this.mToolbar.setVisibility(n);
                        }
                    }

                    @Override
                    public void onAnimationStart(View view) {
                        ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
                    }
                });
                break;
            } while (true);
        }
        f = 0.0f;
        return viewPropertyAnimatorCompat.alpha(f).setDuration(l).setListener(new /* invalid duplicate definition of identical inner class */);
    }

    @Override
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

}

