/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.IBinder
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.widget.PopupWindow
 *  android.widget.TextView
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.getqardio.android.ui.widget.CustomEditText$$Lambda$1;

public class CustomEditText
extends AppCompatEditText {
    private int errorHeight;
    private ErrorPopup errorPopup;
    private int errorWidth;
    private boolean hasFallen = false;
    private Drawables mDrawables;
    private CharSequence mError;
    Drawable mErrorIcon = this.getResources().getDrawable(2130837710);
    private View mErrorView;
    private CharSequence mPreviousError = "";
    private boolean mShowErrorAfterAttach;

    public CustomEditText(Context context) {
        super(context);
        this.init(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    private void chooseSize(PopupWindow popupWindow, CharSequence charSequence, TextView textView) {
        int n;
        int n2 = textView.getPaddingLeft() + textView.getPaddingRight();
        int n3 = textView.getPaddingTop();
        int n4 = textView.getPaddingBottom();
        int n5 = n = this.getWidth() - n2;
        if (n < 0) {
            n5 = 200;
        }
        charSequence = new StaticLayout(charSequence, textView.getPaint(), n5, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        float f = 0.0f;
        for (n5 = 0; n5 < charSequence.getLineCount(); ++n5) {
            f = Math.max(f, charSequence.getLineWidth(n5));
        }
        this.errorWidth = (int)Math.ceil(f) + n2;
        this.errorHeight = charSequence.getHeight() + (n3 + n4) + 50;
        popupWindow.setWidth(this.errorWidth);
        popupWindow.setHeight(this.errorHeight);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getErrorX() {
        int n;
        float f = this.getResources().getDisplayMetrics().density;
        Drawables drawables = this.mDrawables;
        int n2 = this.getWidth();
        int n3 = this.errorPopup.getWidth();
        int n4 = this.getPaddingRight();
        if (drawables != null) {
            n = drawables.mDrawableSizeRight;
            do {
                return n2 - n3 - n4 - n / 2 + (int)(15.0f * f + 0.5f);
                break;
            } while (true);
        }
        n = 0;
        return n2 - n3 - n4 - n / 2 + (int)(15.0f * f + 0.5f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getErrorY() {
        int n = 0;
        int n2 = this.getBottom();
        int n3 = this.getTop();
        int n4 = this.getCompoundPaddingBottom();
        int n5 = this.getCompoundPaddingTop();
        Drawables drawables = this.mDrawables;
        int n6 = this.getCompoundPaddingTop();
        int n7 = drawables != null ? drawables.mDrawableHeightRight : 0;
        n2 = (n2 - n3 - n4 - n5 - n7) / 2;
        n7 = n;
        if (drawables != null) {
            n7 = drawables.mDrawableHeightRight;
        }
        return n6 + n2 + n7 - this.getHeight() - 2;
    }

    private void hideError() {
        if (this.errorPopup != null && this.errorPopup.isShowing()) {
            this.errorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private void init(Context context) {
        this.mErrorView = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2130968702, null);
        this.setError(null, null);
    }

    private void setTheError(CharSequence charSequence) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        this.mError = TextUtils.stringOrSpannedString((CharSequence)charSequence);
    }

    private void showError() {
        if (this.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.errorPopup == null) {
            float f = this.getResources().getDisplayMetrics().density;
            float f2 = this.getResources().getDimension(2131427516);
            this.errorPopup = new ErrorPopup(this.mErrorView, (int)(f2 * f + 0.5f), (int)(50.0f * f + 0.5f));
            this.errorPopup.setFocusable(false);
            this.errorPopup.setInputMethodMode(1);
            this.errorPopup.setBackgroundDrawable((Drawable)new BitmapDrawable());
            this.errorPopup.setOutsideTouchable(false);
            this.errorPopup.setTouchInterceptor(CustomEditText$$Lambda$1.lambdaFactory$(this));
        }
        TextView textView = (TextView)this.errorPopup.getContentView().findViewById(2131821046);
        this.errorPopup.fixDirection(this.errorPopup.isAboveAnchor());
        this.chooseSize(this.errorPopup, this.mError, textView);
        textView.setText(this.mError);
        this.mPreviousError = this.mError;
        this.errorPopup.showAsDropDown((View)this, this.getErrorX(), this.getErrorY());
    }

    /* synthetic */ boolean lambda$showError$0(View view, MotionEvent motionEvent) {
        if (this.errorPopup != null) {
            this.errorPopup.dismiss();
        }
        return false;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.hasFallen && this.mShowErrorAfterAttach) {
            this.showError();
            this.mShowErrorAfterAttach = false;
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.hasFallen && this.mError != null) {
            this.hideError();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (bl) {
            if (this.mError == null) return;
            {
                this.showError();
                return;
            }
        } else {
            if (this.mError == null) return;
            {
                this.hideError();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCompoundDrawables(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        Drawables drawables;
        block21: {
            block22: {
                block20: {
                    super.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
                    if (this.hasFallen) break block20;
                    drawables = this.mDrawables;
                    boolean bl = drawable2 != null || drawable3 != null || drawable4 != null || drawable5 != null;
                    if (bl) break block21;
                    if (drawables != null) break block22;
                }
                return;
            }
            if (drawables.mDrawablePadding == 0) {
                this.mDrawables = null;
                return;
            }
            if (drawables.mDrawableLeft != null) {
                drawables.mDrawableLeft.setCallback(null);
            }
            drawables.mDrawableLeft = null;
            if (drawables.mDrawableTop != null) {
                drawables.mDrawableTop.setCallback(null);
            }
            drawables.mDrawableTop = null;
            if (drawables.mDrawableRight != null) {
                drawables.mDrawableRight.setCallback(null);
            }
            drawables.mDrawableRight = null;
            if (drawables.mDrawableBottom != null) {
                drawables.mDrawableBottom.setCallback(null);
            }
            drawables.mDrawableBottom = null;
            drawables.mDrawableHeightLeft = 0;
            drawables.mDrawableSizeLeft = 0;
            drawables.mDrawableHeightRight = 0;
            drawables.mDrawableSizeRight = 0;
            drawables.mDrawableWidthTop = 0;
            drawables.mDrawableSizeTop = 0;
            drawables.mDrawableWidthBottom = 0;
            drawables.mDrawableSizeBottom = 0;
            return;
        }
        Drawables drawables2 = drawables;
        if (drawables == null) {
            this.mDrawables = drawables2 = new Drawables();
        }
        if (drawables2.mDrawableLeft != drawable2 && drawables2.mDrawableLeft != null) {
            drawables2.mDrawableLeft.setCallback(null);
        }
        drawables2.mDrawableLeft = drawable2;
        if (drawables2.mDrawableTop != drawable3 && drawables2.mDrawableTop != null) {
            drawables2.mDrawableTop.setCallback(null);
        }
        drawables2.mDrawableTop = drawable3;
        if (drawables2.mDrawableRight != drawable4 && drawables2.mDrawableRight != null) {
            drawables2.mDrawableRight.setCallback(null);
        }
        drawables2.mDrawableRight = drawable4;
        if (drawables2.mDrawableBottom != drawable5 && drawables2.mDrawableBottom != null) {
            drawables2.mDrawableBottom.setCallback(null);
        }
        drawables2.mDrawableBottom = drawable5;
        drawables = drawables2.mCompoundRect;
        int[] arrn = this.getDrawableState();
        if (drawable2 != null) {
            drawable2.setState(arrn);
            drawable2.copyBounds((Rect)drawables);
            drawable2.setCallback((Drawable.Callback)this);
            drawables2.mDrawableSizeLeft = drawables.width();
            drawables2.mDrawableHeightLeft = drawables.height();
        } else {
            drawables2.mDrawableHeightLeft = 0;
            drawables2.mDrawableSizeLeft = 0;
        }
        if (drawable4 != null) {
            drawable4.setState(arrn);
            drawable4.copyBounds((Rect)drawables);
            drawable4.setCallback((Drawable.Callback)this);
            drawables2.mDrawableSizeRight = drawables.width();
            drawables2.mDrawableHeightRight = drawables.height();
        } else {
            drawables2.mDrawableHeightRight = 0;
            drawables2.mDrawableSizeRight = 0;
        }
        if (drawable3 != null) {
            drawable3.setState(arrn);
            drawable3.copyBounds((Rect)drawables);
            drawable3.setCallback((Drawable.Callback)this);
            drawables2.mDrawableSizeTop = drawables.height();
            drawables2.mDrawableWidthTop = drawables.width();
        } else {
            drawables2.mDrawableWidthTop = 0;
            drawables2.mDrawableSizeTop = 0;
        }
        if (drawable5 != null) {
            drawable5.setState(arrn);
            drawable5.copyBounds((Rect)drawables);
            drawable5.setCallback((Drawable.Callback)this);
            drawables2.mDrawableSizeBottom = drawables.height();
            drawables2.mDrawableWidthBottom = drawables.width();
            return;
        }
        drawables2.mDrawableWidthBottom = 0;
        drawables2.mDrawableSizeBottom = 0;
    }

    public void setError(CharSequence charSequence) {
        if ((charSequence = TextUtils.stringOrSpannedString((CharSequence)charSequence)) == null) {
            this.setError(null, null);
            return;
        }
        Drawable drawable2 = this.mErrorIcon;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        this.setError(charSequence, drawable2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setError(CharSequence charSequence, Drawable drawable2) {
        block8: {
            block7: {
                try {
                    this.setTheError(charSequence);
                    Drawables drawables = this.mDrawables;
                    if (drawables == null) break block7;
                    this.setCompoundDrawables(drawables.mDrawableLeft, drawables.mDrawableTop, drawable2, drawables.mDrawableBottom);
                    break block8;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    this.hasFallen = true;
                    super.setError(charSequence, drawable2);
                    return;
                }
            }
            this.setCompoundDrawables(null, null, drawable2, null);
        }
        if (charSequence == null) {
            if (this.errorPopup == null) return;
            {
                if (this.errorPopup.isShowing()) {
                    this.errorPopup.dismiss();
                }
                this.errorPopup = null;
                return;
            }
        } else {
            if (!this.isFocused()) return;
            {
                this.showError();
                return;
            }
        }
    }

    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        if (!this.hasFallen && this.errorPopup != null) {
            this.errorPopup.update((View)this, this.getErrorX(), this.getErrorY(), this.errorPopup.getWidth(), this.errorPopup.getHeight());
        }
        return bl;
    }

    private class Drawables {
        final Rect mCompoundRect = new Rect();
        Drawable mDrawableBottom;
        int mDrawableHeightLeft;
        int mDrawableHeightRight;
        Drawable mDrawableLeft;
        int mDrawablePadding;
        Drawable mDrawableRight;
        int mDrawableSizeBottom;
        int mDrawableSizeLeft;
        int mDrawableSizeRight;
        int mDrawableSizeTop;
        Drawable mDrawableTop;
        int mDrawableWidthBottom;
        int mDrawableWidthTop;

        private Drawables() {
        }
    }

    private class ErrorPopup
    extends PopupWindow {
        private boolean mAbove;
        private TextView mView;

        ErrorPopup(View view, int n, int n2) {
            super(view, n, n2);
            this.mAbove = false;
            this.mView = (TextView)view.findViewById(2131821046);
        }

        void fixDirection(boolean bl) {
            this.mAbove = bl;
            if (bl) {
                this.mView.setBackground(CustomEditText.this.getResources().getDrawable(2130837712));
                return;
            }
            this.mView.setBackground(CustomEditText.this.getResources().getDrawable(2130837711));
        }

        public void update(int n, int n2, int n3, int n4, boolean bl) {
            super.update(n, n2, n3, n4, bl);
            bl = this.isAboveAnchor();
            if (bl != this.mAbove) {
                this.fixDirection(bl);
            }
        }
    }

}

