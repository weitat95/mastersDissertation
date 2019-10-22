/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.design.widget.ThemeUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BottomNavigationView
extends FrameLayout {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int[] DISABLED_STATE_SET = new int[]{-16842910};
    private final MenuBuilder mMenu;
    private MenuInflater mMenuInflater;
    private final BottomNavigationMenuView mMenuView;
    private final BottomNavigationPresenter mPresenter = new BottomNavigationPresenter();
    private OnNavigationItemReselectedListener mReselectedListener;
    private OnNavigationItemSelectedListener mSelectedListener;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public BottomNavigationView(Context context, AttributeSet object, int n) {
        super(context, (AttributeSet)object, n);
        ThemeUtils.checkAppCompatTheme(context);
        this.mMenu = new BottomNavigationMenu(context);
        this.mMenuView = new BottomNavigationMenuView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mPresenter.setBottomNavigationMenuView(this.mMenuView);
        this.mPresenter.setId(1);
        this.mMenuView.setPresenter(this.mPresenter);
        this.mMenu.addMenuPresenter(this.mPresenter);
        this.mPresenter.initForMenu(this.getContext(), this.mMenu);
        object = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.BottomNavigationView, n, R.style.Widget_Design_BottomNavigationView);
        if (((TintTypedArray)object).hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            this.mMenuView.setIconTintList(((TintTypedArray)object).getColorStateList(R.styleable.BottomNavigationView_itemIconTint));
        } else {
            this.mMenuView.setIconTintList(this.createDefaultColorStateList(16842808));
        }
        if (((TintTypedArray)object).hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
            this.mMenuView.setItemTextColor(((TintTypedArray)object).getColorStateList(R.styleable.BottomNavigationView_itemTextColor));
        } else {
            this.mMenuView.setItemTextColor(this.createDefaultColorStateList(16842808));
        }
        if (((TintTypedArray)object).hasValue(R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation((View)this, ((TintTypedArray)object).getDimensionPixelSize(R.styleable.BottomNavigationView_elevation, 0));
        }
        n = ((TintTypedArray)object).getResourceId(R.styleable.BottomNavigationView_itemBackground, 0);
        this.mMenuView.setItemBackgroundRes(n);
        if (((TintTypedArray)object).hasValue(R.styleable.BottomNavigationView_menu)) {
            this.inflateMenu(((TintTypedArray)object).getResourceId(R.styleable.BottomNavigationView_menu, 0));
        }
        ((TintTypedArray)object).recycle();
        this.addView((View)this.mMenuView, (ViewGroup.LayoutParams)layoutParams);
        if (Build.VERSION.SDK_INT < 21) {
            this.addCompatibilityTopDivider(context);
        }
        this.mMenu.setCallback(new MenuBuilder.Callback(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                if (BottomNavigationView.this.mReselectedListener != null && menuItem.getItemId() == BottomNavigationView.this.getSelectedItemId()) {
                    BottomNavigationView.this.mReselectedListener.onNavigationItemReselected(menuItem);
                    return true;
                } else {
                    if (BottomNavigationView.this.mSelectedListener != null && !BottomNavigationView.this.mSelectedListener.onNavigationItemSelected(menuItem)) return true;
                    return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        });
    }

    private void addCompatibilityTopDivider(Context context) {
        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.design_bottom_navigation_shadow_color));
        view.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, this.getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
        this.addView(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    private ColorStateList createDefaultColorStateList(int n) {
        ColorStateList colorStateList;
        int[] arrn;
        block3: {
            block2: {
                arrn = new TypedValue();
                if (!this.getContext().getTheme().resolveAttribute(n, (TypedValue)arrn, true)) break block2;
                colorStateList = AppCompatResources.getColorStateList(this.getContext(), arrn.resourceId);
                if (this.getContext().getTheme().resolveAttribute(R.attr.colorPrimary, (TypedValue)arrn, true)) break block3;
            }
            return null;
        }
        n = arrn.data;
        int n2 = colorStateList.getDefaultColor();
        arrn = DISABLED_STATE_SET;
        int[] arrn2 = CHECKED_STATE_SET;
        int[] arrn3 = EMPTY_STATE_SET;
        int n3 = colorStateList.getColorForState(DISABLED_STATE_SET, n2);
        return new ColorStateList((int[][])new int[][]{arrn, arrn2, arrn3}, new int[]{n3, n, n2});
    }

    private MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(this.getContext());
        }
        return this.mMenuInflater;
    }

    public int getItemBackgroundResource() {
        return this.mMenuView.getItemBackgroundRes();
    }

    public ColorStateList getItemIconTintList() {
        return this.mMenuView.getIconTintList();
    }

    public ColorStateList getItemTextColor() {
        return this.mMenuView.getItemTextColor();
    }

    public int getMaxItemCount() {
        return 5;
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public int getSelectedItemId() {
        return this.mMenuView.getSelectedItemId();
    }

    public void inflateMenu(int n) {
        this.mPresenter.setUpdateSuspended(true);
        this.getMenuInflater().inflate(n, (Menu)this.mMenu);
        this.mPresenter.setUpdateSuspended(false);
        this.mPresenter.updateMenuView(true);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.mMenu.restorePresenterStates(parcelable.menuPresenterState);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.menuPresenterState = new Bundle();
        this.mMenu.savePresenterStates(savedState.menuPresenterState);
        return savedState;
    }

    public void setItemBackgroundResource(int n) {
        this.mMenuView.setItemBackgroundRes(n);
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.mMenuView.setIconTintList(colorStateList);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.mMenuView.setItemTextColor(colorStateList);
    }

    public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener onNavigationItemReselectedListener) {
        this.mReselectedListener = onNavigationItemReselectedListener;
    }

    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.mSelectedListener = onNavigationItemSelectedListener;
    }

    public void setSelectedItemId(int n) {
        MenuItem menuItem = this.mMenu.findItem(n);
        if (menuItem != null && !this.mMenu.performItemAction(menuItem, this.mPresenter, 0)) {
            menuItem.setChecked(true);
        }
    }

    public static interface OnNavigationItemReselectedListener {
        public void onNavigationItemReselected(MenuItem var1);
    }

    public static interface OnNavigationItemSelectedListener {
        public boolean onNavigationItemSelected(MenuItem var1);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        Bundle menuPresenterState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.readFromParcel(parcel, classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private void readFromParcel(Parcel parcel, ClassLoader classLoader) {
            this.menuPresenterState = parcel.readBundle(classLoader);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeBundle(this.menuPresenterState);
        }

    }

}

