/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class ActionMenuItemView
extends AppCompatTextView
implements MenuView.ItemView,
ActionMenuView.ActionMenuChildView,
View.OnClickListener {
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    MenuItemImpl mItemData;
    MenuBuilder.ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        Resources resources = context.getResources();
        this.mAllowTextWithIcon = this.shouldAllowTextWithIcon();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, n, 0);
        this.mMinWidth = context.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
        context.recycle();
        this.mMaxIconSize = (int)(32.0f * resources.getDisplayMetrics().density + 0.5f);
        this.setOnClickListener((View.OnClickListener)this);
        this.mSavedPaddingLeft = -1;
        this.setSaveEnabled(false);
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration configuration = this.getContext().getResources().getConfiguration();
        int n = configuration.screenWidthDp;
        int n2 = configuration.screenHeightDp;
        return n >= 480 || n >= 640 && n2 >= 480 || configuration.orientation == 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateTextButtonVisibility() {
        Object var5_2;
        boolean bl;
        boolean bl2;
        block7: {
            block6: {
                boolean bl3 = false;
                var5_2 = null;
                bl2 = !TextUtils.isEmpty((CharSequence)this.mTitle);
                if (this.mIcon == null) break block6;
                bl = bl3;
                if (!this.mItemData.showsTextAsAction()) break block7;
                if (this.mAllowTextWithIcon) break block6;
                bl = bl3;
                if (!this.mExpandedFormat) break block7;
            }
            bl = true;
        }
        CharSequence charSequence = (bl2 &= bl) ? this.mTitle : null;
        this.setText(charSequence);
        charSequence = this.mItemData.getContentDescription();
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            charSequence = bl2 ? null : this.mItemData.getTitle();
            this.setContentDescription(charSequence);
        } else {
            this.setContentDescription(charSequence);
        }
        if (!TextUtils.isEmpty((CharSequence)(charSequence = this.mItemData.getTooltipText()))) {
            TooltipCompat.setTooltipText((View)this, charSequence);
            return;
        }
        charSequence = bl2 ? var5_2 : this.mItemData.getTitle();
        TooltipCompat.setTooltipText((View)this, charSequence);
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public boolean hasText() {
        return !TextUtils.isEmpty((CharSequence)this.getText());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n) {
        this.mItemData = menuItemImpl;
        this.setIcon(menuItemImpl.getIcon());
        this.setTitle(menuItemImpl.getTitleForItemView(this));
        this.setId(menuItemImpl.getItemId());
        n = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n);
        this.setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    @Override
    public boolean needsDividerAfter() {
        return this.hasText();
    }

    @Override
    public boolean needsDividerBefore() {
        return this.hasText() && this.mItemData.getIcon() == null;
    }

    public void onClick(View view) {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mAllowTextWithIcon = this.shouldAllowTextWithIcon();
        this.updateTextButtonVisibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        boolean bl = this.hasText();
        if (bl && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
        super.onMeasure(n, n2);
        int n3 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n);
        int n4 = this.getMeasuredWidth();
        n = n3 == Integer.MIN_VALUE ? Math.min(n, this.mMinWidth) : this.mMinWidth;
        if (n3 != 1073741824 && this.mMinWidth > 0 && n4 < n) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824), n2);
        }
        if (!bl && this.mIcon != null) {
            super.setPadding((this.getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean prefersCondensedTitle() {
        return true;
    }

    public void setCheckable(boolean bl) {
    }

    public void setChecked(boolean bl) {
    }

    public void setExpandedFormat(boolean bl) {
        if (this.mExpandedFormat != bl) {
            this.mExpandedFormat = bl;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        if (drawable2 != null) {
            float f;
            int n;
            int n2 = drawable2.getIntrinsicWidth();
            int n3 = n = drawable2.getIntrinsicHeight();
            int n4 = n2;
            if (n2 > this.mMaxIconSize) {
                f = (float)this.mMaxIconSize / (float)n2;
                n4 = this.mMaxIconSize;
                n3 = (int)((float)n * f);
            }
            n2 = n3;
            n = n4;
            if (n3 > this.mMaxIconSize) {
                f = (float)this.mMaxIconSize / (float)n3;
                n2 = this.mMaxIconSize;
                n = (int)((float)n4 * f);
            }
            drawable2.setBounds(0, 0, n, n2);
        }
        this.setCompoundDrawables(drawable2, null, null, null);
        this.updateTextButtonVisibility();
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        this.mSavedPaddingLeft = n;
        super.setPadding(n, n2, n3, n4);
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    public void setShortcut(boolean bl, char c) {
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.updateTextButtonVisibility();
    }

    private class ActionMenuItemForwardingListener
    extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super((View)ActionMenuItemView.this);
        }

        @Override
        public ShowableListMenu getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        @Override
        protected boolean onForwardingStarted() {
            boolean bl;
            boolean bl2 = bl = false;
            if (ActionMenuItemView.this.mItemInvoker != null) {
                bl2 = bl;
                if (ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                    ShowableListMenu showableListMenu = this.getPopup();
                    bl2 = bl;
                    if (showableListMenu != null) {
                        bl2 = bl;
                        if (showableListMenu.isShowing()) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
    }

    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();
    }

}

