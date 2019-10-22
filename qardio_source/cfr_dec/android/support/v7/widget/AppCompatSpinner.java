/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AppCompatSpinner
extends Spinner
implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = new int[]{16843505};
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private DropdownPopup mPopup;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    private final Rect mTempRect = new Rect();

    public AppCompatSpinner(Context context) {
        this(context, null);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, n2, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AppCompatSpinner(Context var1_1, AttributeSet var2_3, int var3_4, int var4_5, Resources.Theme var5_6) {
        super(var1_1, var2_3, var3_4);
        var10_7 = TintTypedArray.obtainStyledAttributes(var1_1, var2_3, R.styleable.Spinner, var3_4, 0);
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
        if (var5_6 /* !! */  != null) {
            this.mPopupContext = new ContextThemeWrapper(var1_1, (Resources.Theme)var5_6 /* !! */ );
        } else {
            var6_8 = var10_7.getResourceId(R.styleable.Spinner_popupTheme, 0);
            if (var6_8 != 0) {
                this.mPopupContext = new ContextThemeWrapper(var1_1, var6_8);
            } else {
                var5_6 /* !! */  = Build.VERSION.SDK_INT < 23 ? var1_1 : null;
                this.mPopupContext = var5_6 /* !! */ ;
            }
        }
        if (this.mPopupContext != null) {
            block17: {
                var7_9 = var4_5;
                if (var4_5 == -1) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        var8_10 = null;
                        var5_6 /* !! */  = null;
                        try {
                            var9_11 = var1_1.obtainStyledAttributes(var2_3, AppCompatSpinner.ATTRS_ANDROID_SPINNERMODE, var3_4, 0);
                            var6_8 = var4_5;
                            var5_6 /* !! */  = var9_11;
                            var8_10 = var9_11;
                            if (var9_11.hasValue(0)) {
                                var5_6 /* !! */  = var9_11;
                                var8_10 = var9_11;
                                var6_8 = var9_11.getInt(0, 0);
                            }
                            var7_9 = var6_8;
                            ** if (var9_11 == null) goto lbl-1000
                        }
                        catch (Exception var9_12) {
                            var8_10 = var5_6 /* !! */ ;
                            try {
                                Log.i((String)"AppCompatSpinner", (String)"Could not read android:spinnerMode", (Throwable)var9_12);
                                var7_9 = var4_5;
                                ** if (var5_6 /* !! */  == null) goto lbl-1000
                            }
                            catch (Throwable var1_2) {
                                if (var8_10 == null) throw var1_2;
                                var8_10.recycle();
                                throw var1_2;
                            }
lbl-1000:
                            // 1 sources
                            {
                                var5_6 /* !! */ .recycle();
                                var7_9 = var4_5;
                            }
lbl-1000:
                            // 2 sources
                            {
                                break block17;
                            }
                        }
lbl-1000:
                        // 1 sources
                        {
                            var9_11.recycle();
                            var7_9 = var6_8;
                        }
lbl-1000:
                        // 2 sources
                        {
                            break block17;
                        }
                    }
                    var7_9 = 1;
                }
            }
            if (var7_9 == 1) {
                var5_6 /* !! */  = new DropdownPopup(this.mPopupContext, var2_3, var3_4);
                var8_10 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, var2_3, R.styleable.Spinner, var3_4, 0);
                this.mDropDownWidth = var8_10.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
                var5_6 /* !! */ .setBackgroundDrawable(var8_10.getDrawable(R.styleable.Spinner_android_popupBackground));
                var5_6 /* !! */ .setPromptText(var10_7.getString(R.styleable.Spinner_android_prompt));
                var8_10.recycle();
                this.mPopup = var5_6 /* !! */ ;
                this.mForwardingListener = new ForwardingListener((View)this, (DropdownPopup)var5_6 /* !! */ ){
                    final /* synthetic */ DropdownPopup val$popup;
                    {
                        this.val$popup = dropdownPopup;
                        super(view);
                    }

                    @Override
                    public ShowableListMenu getPopup() {
                        return this.val$popup;
                    }

                    @Override
                    public boolean onForwardingStarted() {
                        if (!AppCompatSpinner.this.mPopup.isShowing()) {
                            AppCompatSpinner.this.mPopup.show();
                        }
                        return true;
                    }
                };
            }
        }
        if ((var5_6 /* !! */  = var10_7.getTextArray(R.styleable.Spinner_android_entries)) != null) {
            var1_1 = new ArrayAdapter(var1_1, 17367048, (Object[])var5_6 /* !! */ );
            var1_1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.setAdapter((SpinnerAdapter)var1_1);
        }
        var10_7.recycle();
        this.mPopupSet = true;
        if (this.mTempAdapter != null) {
            this.setAdapter(this.mTempAdapter);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(var2_3, var3_4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable2) {
        if (spinnerAdapter == null) {
            return 0;
        }
        int n = 0;
        View view = null;
        int n2 = 0;
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n4 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n5 = Math.max(0, this.getSelectedItemPosition());
        int n6 = Math.min(spinnerAdapter.getCount(), n5 + 15);
        for (n5 = Math.max(0, n5 - (15 - (n6 - n5))); n5 < n6; ++n5) {
            int n7 = spinnerAdapter.getItemViewType(n5);
            int n8 = n2;
            if (n7 != n2) {
                n8 = n7;
                view = null;
            }
            if ((view = spinnerAdapter.getView(n5, view, (ViewGroup)this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n3, n4);
            n = Math.max(n, view.getMeasuredWidth());
            n2 = n8;
        }
        n5 = n;
        if (drawable2 == null) return n5;
        drawable2.getPadding(this.mTempRect);
        return n + (this.mTempRect.left + this.mTempRect.right);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public int getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    public Drawable getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground();
        }
        return null;
    }

    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getPopupContext();
        }
        return null;
    }

    public CharSequence getPrompt() {
        if (this.mPopup != null) {
            return this.mPopup.getHintText();
        }
        return super.getPrompt();
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mPopup != null && View.MeasureSpec.getMode((int)n) == Integer.MIN_VALUE) {
            this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.compatMeasureContentWidth(this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize((int)n)), this.getMeasuredHeight());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mForwardingListener != null && this.mForwardingListener.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performClick() {
        if (this.mPopup != null) {
            if (!this.mPopup.isShowing()) {
                this.mPopup.show();
            }
            return true;
        }
        return super.performClick();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        Context context;
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        } else {
            super.setAdapter(spinnerAdapter);
            if (this.mPopup == null) return;
            {
                context = this.mPopupContext == null ? this.getContext() : this.mPopupContext;
            }
        }
        this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context.getTheme()));
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable2);
        }
    }

    public void setBackgroundResource(int n) {
        super.setBackgroundResource(n);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownHorizontalOffset(int n) {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(n);
            return;
        } else {
            if (Build.VERSION.SDK_INT < 16) return;
            {
                super.setDropDownHorizontalOffset(n);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownVerticalOffset(int n) {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(n);
            return;
        } else {
            if (Build.VERSION.SDK_INT < 16) return;
            {
                super.setDropDownVerticalOffset(n);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDropDownWidth(int n) {
        if (this.mPopup != null) {
            this.mDropDownWidth = n;
            return;
        } else {
            if (Build.VERSION.SDK_INT < 16) return;
            {
                super.setDropDownWidth(n);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPopupBackgroundDrawable(Drawable drawable2) {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(drawable2);
            return;
        } else {
            if (Build.VERSION.SDK_INT < 16) return;
            {
                super.setPopupBackgroundDrawable(drawable2);
                return;
            }
        }
    }

    public void setPopupBackgroundResource(int n) {
        this.setPopupBackgroundDrawable(AppCompatResources.getDrawable(this.getPopupContext(), n));
    }

    public void setPrompt(CharSequence charSequence) {
        if (this.mPopup != null) {
            this.mPopup.setPromptText(charSequence);
            return;
        }
        super.setPrompt(charSequence);
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    private static class DropDownAdapter
    implements ListAdapter,
    SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        /*
         * Enabled aggressive block sorting
         */
        public DropDownAdapter(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter)spinnerAdapter;
            }
            if (theme == null) return;
            {
                if (Build.VERSION.SDK_INT >= 23 && spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter) {
                    if ((spinnerAdapter = (android.widget.ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == theme) return;
                    {
                        spinnerAdapter.setDropDownViewTheme(theme);
                        return;
                    }
                } else {
                    if (!(spinnerAdapter instanceof ThemedSpinnerAdapter) || (spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() != null) return;
                    {
                        spinnerAdapter.setDropDownViewTheme(theme);
                        return;
                    }
                }
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            if (this.mAdapter == null) {
                return 0;
            }
            return this.mAdapter.getCount();
        }

        public View getDropDownView(int n, View view, ViewGroup viewGroup) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getDropDownView(n, view, viewGroup);
        }

        public Object getItem(int n) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getItem(n);
        }

        public long getItemId(int n) {
            if (this.mAdapter == null) {
                return -1L;
            }
            return this.mAdapter.getItemId(n);
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return this.getCount() == 0;
        }

        public boolean isEnabled(int n) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(n);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class DropdownPopup
    extends ListPopupWindow {
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect;

        public DropdownPopup(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
            this.mVisibleRect = new Rect();
            this.setAnchorView((View)AppCompatSpinner.this);
            this.setModal(true);
            this.setPromptPosition(0);
            this.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    AppCompatSpinner.this.setSelection(n);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         */
        void computeContentWidth() {
            Drawable drawable2 = this.getBackground();
            int n = 0;
            if (drawable2 != null) {
                drawable2.getPadding(AppCompatSpinner.this.mTempRect);
                n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right : -AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left;
            } else {
                drawable2 = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right = 0;
                drawable2.left = 0;
            }
            int n2 = AppCompatSpinner.this.getPaddingLeft();
            int n3 = AppCompatSpinner.this.getPaddingRight();
            int n4 = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int n5 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.getBackground());
                int n6 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right;
                int n7 = n5;
                if (n5 > n6) {
                    n7 = n6;
                }
                this.setContentWidth(Math.max(n7, n4 - n2 - n3));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                this.setContentWidth(n4 - n2 - n3);
            } else {
                this.setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? (n += n4 - n3 - this.getWidth()) : (n += n2);
            this.setHorizontalOffset(n);
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect);
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void show() {
            ViewTreeObserver viewTreeObserver;
            boolean bl = this.isShowing();
            this.computeContentWidth();
            this.setInputMethodMode(2);
            super.show();
            this.getListView().setChoiceMode(1);
            this.setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (bl || (viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) == null) {
                return;
            }
            final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    if (!DropdownPopup.this.isVisibleToUser((View)AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss();
                        return;
                    }
                    DropdownPopup.this.computeContentWidth();
                    DropdownPopup.super.show();
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            this.setOnDismissListener(new PopupWindow.OnDismissListener(){

                public void onDismiss() {
                    ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
                    }
                }
            });
        }

    }

}

