/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.AppCompatPopupWindow;
import android.support.v7.widget.DropDownListView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

public class ListPopupWindow
implements ShowableListMenu {
    private static Method sClipToWindowEnabledMethod;
    private static Method sGetMaxAvailableHeightMethod;
    private static Method sSetEpicenterBoundsMethod;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible = false;
    private View mDropDownAnchorView;
    private int mDropDownGravity = 0;
    private int mDropDownHeight = -2;
    private int mDropDownHorizontalOffset;
    DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth = -2;
    private int mDropDownWindowLayoutType = 1002;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch = false;
    final Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private boolean mIsAnimatedFromAnchor = true;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum = Integer.MAX_VALUE;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    PopupWindow mPopup;
    private int mPromptPosition = 0;
    private View mPromptView;
    final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable();
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        try {
            sClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)"ListPopupWindow", (String)"Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)"ListPopupWindow", (String)"Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)"ListPopupWindow", (String)"Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            return;
        }
    }

    public ListPopupWindow(Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n, int n2) {
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, n, n2);
        this.mDropDownHorizontalOffset = typedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.mDropDownVerticalOffset = typedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArray.recycle();
        this.mPopup = new AppCompatPopupWindow(context, attributeSet, n, n2);
        this.mPopup.setInputMethodMode(1);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private int buildDropDown() {
        int n;
        boolean bl;
        Drawable drawable2;
        int n2 = 0;
        int n3 = 0;
        if (this.mDropDownList == null) {
            void var6_10;
            Context context = this.mContext;
            this.mShowDropDownRunnable = new Runnable(){

                @Override
                public void run() {
                    View view = ListPopupWindow.this.getAnchorView();
                    if (view != null && view.getWindowToken() != null) {
                        ListPopupWindow.this.show();
                    }
                }
            };
            bl = !this.mModal;
            this.mDropDownList = this.createDropDownListView(context, bl);
            if (this.mDropDownListHighlight != null) {
                this.mDropDownList.setSelector(this.mDropDownListHighlight);
            }
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
                    if (n != -1 && (object = ListPopupWindow.this.mDropDownList) != null) {
                        ((DropDownListView)((Object)object)).setListSelectionHidden(false);
                    }
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.mDropDownList.setOnScrollListener((AbsListView.OnScrollListener)this.mScrollListener);
            if (this.mItemSelectedListener != null) {
                this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
            }
            DropDownListView dropDownListView = this.mDropDownList;
            View view = this.mPromptView;
            DropDownListView dropDownListView2 = dropDownListView;
            if (view != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                context = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.mPromptPosition) {
                    default: {
                        Log.e((String)"ListPopupWindow", (String)("Invalid hint position " + this.mPromptPosition));
                        break;
                    }
                    case 1: {
                        linearLayout.addView((View)dropDownListView, (ViewGroup.LayoutParams)context);
                        linearLayout.addView(view);
                        break;
                    }
                    case 0: {
                        linearLayout.addView(view);
                        linearLayout.addView((View)dropDownListView, (ViewGroup.LayoutParams)context);
                    }
                }
                if (this.mDropDownWidth >= 0) {
                    n3 = Integer.MIN_VALUE;
                    n2 = this.mDropDownWidth;
                } else {
                    n3 = 0;
                    n2 = 0;
                }
                view.measure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)n3), 0);
                dropDownListView = (LinearLayout.LayoutParams)view.getLayoutParams();
                n3 = view.getMeasuredHeight() + ((LinearLayout.LayoutParams)dropDownListView).topMargin + ((LinearLayout.LayoutParams)dropDownListView).bottomMargin;
            }
            this.mPopup.setContentView((View)var6_10);
        } else {
            ViewGroup viewGroup = (ViewGroup)this.mPopup.getContentView();
            View view = this.mPromptView;
            n3 = n2;
            if (view != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
                n3 = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
        if ((drawable2 = this.mPopup.getBackground()) != null) {
            drawable2.getPadding(this.mTempRect);
            n = n2 = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
                n = n2;
            }
        } else {
            this.mTempRect.setEmpty();
            n = 0;
        }
        bl = this.mPopup.getInputMethodMode() == 2;
        int n4 = this.getMaxAvailableHeight(this.getAnchorView(), this.mDropDownVerticalOffset, bl);
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return n4 + n;
        }
        switch (this.mDropDownWidth) {
            default: {
                n2 = View.MeasureSpec.makeMeasureSpec((int)this.mDropDownWidth, (int)1073741824);
                break;
            }
            case -2: {
                n2 = View.MeasureSpec.makeMeasureSpec((int)(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right)), (int)Integer.MIN_VALUE);
                break;
            }
            case -1: {
                n2 = View.MeasureSpec.makeMeasureSpec((int)(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right)), (int)1073741824);
            }
        }
        n4 = this.mDropDownList.measureHeightOfChildrenCompat(n2, 0, -1, n4 - n3, -1);
        n2 = n3;
        if (n4 > 0) {
            n2 = n3 + (n + (this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom()));
        }
        return n4 + n2;
    }

    private int getMaxAvailableHeight(View view, int n, boolean bl) {
        if (sGetMaxAvailableHeightMethod != null) {
            try {
                int n2 = (Integer)sGetMaxAvailableHeightMethod.invoke((Object)this.mPopup, new Object[]{view, n, bl});
                return n2;
            }
            catch (Exception exception) {
                Log.i((String)"ListPopupWindow", (String)"Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.mPopup.getMaxAvailableHeight(view, n);
    }

    private void removePromptView() {
        ViewParent viewParent;
        if (this.mPromptView != null && (viewParent = this.mPromptView.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(this.mPromptView);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setPopupClipToScreenEnabled(boolean bl) {
        if (sClipToWindowEnabledMethod == null) return;
        try {
            sClipToWindowEnabledMethod.invoke((Object)this.mPopup, bl);
            return;
        }
        catch (Exception exception) {
            Log.i((String)"ListPopupWindow", (String)"Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            return;
        }
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.requestLayout();
        }
    }

    DropDownListView createDropDownListView(Context context, boolean bl) {
        return new DropDownListView(context, bl);
    }

    @Override
    public void dismiss() {
        this.mPopup.dismiss();
        this.removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks((Runnable)this.mResizePopupRunnable);
    }

    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    @Override
    public ListView getListView() {
        return this.mDropDownList;
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean isModal() {
        return this.mModal;
    }

    @Override
    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(ListAdapter listAdapter) {
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = listAdapter;
        if (this.mAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setAnchorView(View view) {
        this.mDropDownAnchorView = view;
    }

    public void setAnimationStyle(int n) {
        this.mPopup.setAnimationStyle(n);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        this.mPopup.setBackgroundDrawable(drawable2);
    }

    public void setContentWidth(int n) {
        Drawable drawable2 = this.mPopup.getBackground();
        if (drawable2 != null) {
            drawable2.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + n;
            return;
        }
        this.setWidth(n);
    }

    public void setDropDownGravity(int n) {
        this.mDropDownGravity = n;
    }

    public void setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect;
    }

    public void setHorizontalOffset(int n) {
        this.mDropDownHorizontalOffset = n;
    }

    public void setInputMethodMode(int n) {
        this.mPopup.setInputMethodMode(n);
    }

    public void setModal(boolean bl) {
        this.mModal = bl;
        this.mPopup.setFocusable(bl);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOverlapAnchor(boolean bl) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = bl;
    }

    public void setPromptPosition(int n) {
        this.mPromptPosition = n;
    }

    public void setSelection(int n) {
        DropDownListView dropDownListView = this.mDropDownList;
        if (this.isShowing() && dropDownListView != null) {
            dropDownListView.setListSelectionHidden(false);
            dropDownListView.setSelection(n);
            if (dropDownListView.getChoiceMode() != 0) {
                dropDownListView.setItemChecked(n, true);
            }
        }
    }

    public void setVerticalOffset(int n) {
        this.mDropDownVerticalOffset = n;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int n) {
        this.mDropDownWidth = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void show() {
        boolean bl = true;
        boolean bl2 = false;
        int n = -1;
        int n2 = this.buildDropDown();
        boolean bl3 = this.isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType);
        if (this.mPopup.isShowing()) {
            if (!ViewCompat.isAttachedToWindow(this.getAnchorView())) return;
            {
                PopupWindow popupWindow;
                int n3;
                int n4 = this.mDropDownWidth == -1 ? -1 : (this.mDropDownWidth == -2 ? this.getAnchorView().getWidth() : this.mDropDownWidth);
                if (this.mDropDownHeight == -1) {
                    if (!bl3) {
                        n2 = -1;
                    }
                    if (bl3) {
                        popupWindow = this.mPopup;
                        n3 = this.mDropDownWidth == -1 ? -1 : 0;
                        popupWindow.setWidth(n3);
                        this.mPopup.setHeight(0);
                    } else {
                        popupWindow = this.mPopup;
                        n3 = this.mDropDownWidth == -1 ? -1 : 0;
                        popupWindow.setWidth(n3);
                        this.mPopup.setHeight(-1);
                    }
                } else if (this.mDropDownHeight != -2) {
                    n2 = this.mDropDownHeight;
                }
                popupWindow = this.mPopup;
                bl = bl2;
                if (!this.mForceIgnoreOutsideTouch) {
                    bl = bl2;
                    if (!this.mDropDownAlwaysVisible) {
                        bl = true;
                    }
                }
                popupWindow.setOutsideTouchable(bl);
                popupWindow = this.mPopup;
                View view = this.getAnchorView();
                n3 = this.mDropDownHorizontalOffset;
                int n5 = this.mDropDownVerticalOffset;
                if (n4 < 0) {
                    n4 = -1;
                }
                if (n2 < 0) {
                    n2 = n;
                }
                popupWindow.update(view, n3, n5, n4, n2);
                return;
            }
        }
        int n6 = this.mDropDownWidth == -1 ? -1 : (this.mDropDownWidth == -2 ? this.getAnchorView().getWidth() : this.mDropDownWidth);
        if (this.mDropDownHeight == -1) {
            n2 = -1;
        } else if (this.mDropDownHeight != -2) {
            n2 = this.mDropDownHeight;
        }
        this.mPopup.setWidth(n6);
        this.mPopup.setHeight(n2);
        this.setPopupClipToScreenEnabled(true);
        PopupWindow popupWindow = this.mPopup;
        if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
            bl = false;
        }
        popupWindow.setOutsideTouchable(bl);
        this.mPopup.setTouchInterceptor((View.OnTouchListener)this.mTouchInterceptor);
        if (this.mOverlapAnchorSet) {
            PopupWindowCompat.setOverlapAnchor(this.mPopup, this.mOverlapAnchor);
        }
        if (sSetEpicenterBoundsMethod != null) {
            try {
                sSetEpicenterBoundsMethod.invoke((Object)this.mPopup, new Object[]{this.mEpicenterBounds});
            }
            catch (Exception exception) {
                Log.e((String)"ListPopupWindow", (String)"Could not invoke setEpicenterBounds on PopupWindow", (Throwable)exception);
            }
        }
        PopupWindowCompat.showAsDropDown(this.mPopup, this.getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.mDropDownList.setSelection(-1);
        if (!this.mModal || this.mDropDownList.isInTouchMode()) {
            this.clearListSelection();
        }
        if (this.mModal) {
            return;
        }
        this.mHandler.post((Runnable)this.mHideSelector);
    }

    private class ListSelectorHider
    implements Runnable {
        ListSelectorHider() {
        }

        @Override
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver
    extends DataSetObserver {
        PopupDataSetObserver() {
        }

        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener
    implements AbsListView.OnScrollListener {
        PopupScrollListener() {
        }

        public void onScroll(AbsListView absListView, int n, int n2, int n3) {
        }

        public void onScrollStateChanged(AbsListView absListView, int n) {
            if (n == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }

    private class PopupTouchInterceptor
    implements View.OnTouchListener {
        PopupTouchInterceptor() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int n = motionEvent.getAction();
            int n2 = (int)motionEvent.getX();
            int n3 = (int)motionEvent.getY();
            if (n == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && n2 >= 0 && n2 < ListPopupWindow.this.mPopup.getWidth() && n3 >= 0 && n3 < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed((Runnable)ListPopupWindow.this.mResizePopupRunnable, 250L);
                return false;
            }
            if (n != 1) return false;
            ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
            return false;
        }
    }

    private class ResizePopupRunnable
    implements Runnable {
        ResizePopupRunnable() {
        }

        @Override
        public void run() {
            if (ListPopupWindow.this.mDropDownList != null && ViewCompat.isAttachedToWindow((View)ListPopupWindow.this.mDropDownList) && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

}

