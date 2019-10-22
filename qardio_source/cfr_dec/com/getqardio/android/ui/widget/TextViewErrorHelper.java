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
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TextViewErrorHelper {
    private ErrorPopup errorPopup;
    private Drawables mDrawables;
    private CharSequence mError;
    Drawable mErrorIcon;
    private View mErrorView;
    private boolean mShowErrorAfterAttach;
    private TextView textView;

    public TextViewErrorHelper(TextView textView) {
        this.textView = textView;
        this.mErrorIcon = textView.getResources().getDrawable(2130837710);
        this.mErrorView = ((LayoutInflater)textView.getContext().getSystemService("layout_inflater")).inflate(2130968702, null);
    }

    private void chooseSize(PopupWindow popupWindow, CharSequence charSequence, TextView textView) {
        int n;
        int n2 = textView.getPaddingLeft() + textView.getPaddingRight();
        int n3 = textView.getPaddingTop();
        int n4 = textView.getPaddingBottom();
        int n5 = n = this.textView.getWidth() - n2;
        if (n < 0) {
            n5 = 200;
        }
        charSequence = new StaticLayout(charSequence, textView.getPaint(), n5, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        float f = 0.0f;
        for (n5 = 0; n5 < charSequence.getLineCount(); ++n5) {
            f = Math.max(f, charSequence.getLineWidth(n5));
        }
        n5 = (int)Math.ceil(f);
        n = charSequence.getHeight();
        popupWindow.setWidth(n2 + n5);
        popupWindow.setHeight(n + (n3 + n4) + 50);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getErrorX() {
        int n;
        float f = this.textView.getResources().getDisplayMetrics().density;
        Drawables drawables = this.mDrawables;
        int n2 = this.textView.getWidth();
        int n3 = this.errorPopup.getWidth();
        int n4 = this.textView.getPaddingRight();
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
        int n2 = this.textView.getBottom();
        int n3 = this.textView.getTop();
        int n4 = this.textView.getCompoundPaddingBottom();
        int n5 = this.textView.getCompoundPaddingTop();
        Drawables drawables = this.mDrawables;
        int n6 = this.textView.getCompoundPaddingTop();
        int n7 = drawables != null ? drawables.mDrawableHeightRight : 0;
        n2 = (n2 - n3 - n4 - n5 - n7) / 2;
        n7 = n;
        if (drawables != null) {
            n7 = drawables.mDrawableHeightRight;
        }
        return n6 + n2 + n7 - this.textView.getHeight() - 2;
    }

    private void hideError() {
        if (this.errorPopup != null && this.errorPopup.isShowing()) {
            this.errorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private void showError() {
        if (this.textView.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.errorPopup == null) {
            float f = this.textView.getResources().getDisplayMetrics().density;
            float f2 = this.textView.getResources().getDimension(2131427516);
            this.errorPopup = new ErrorPopup(this.mErrorView, (int)(f2 * f + 0.5f), (int)(50.0f * f + 0.5f));
            this.errorPopup.setFocusable(false);
            this.errorPopup.setInputMethodMode(1);
            this.errorPopup.setBackgroundDrawable((Drawable)new BitmapDrawable());
            this.errorPopup.setOutsideTouchable(false);
            this.errorPopup.setTouchInterceptor(new View.OnTouchListener(){

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (TextViewErrorHelper.this.errorPopup != null) {
                        TextViewErrorHelper.this.errorPopup.dismiss();
                    }
                    return false;
                }
            });
        }
        TextView textView = (TextView)this.errorPopup.getContentView().findViewById(2131821046);
        this.errorPopup.fixDirection(this.errorPopup.isAboveAnchor());
        this.chooseSize(this.errorPopup, this.mError, textView);
        textView.setText(this.mError);
        this.errorPopup.showAsDropDown((View)textView, this.getErrorX(), this.getErrorY());
    }

    public void onAttachedToWindow() {
        if (this.mShowErrorAfterAttach && this.mError != null) {
            this.showError();
            this.mShowErrorAfterAttach = false;
        }
    }

    public void onDetachedFromWindow() {
        if (this.mError != null) {
            this.hideError();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onFocusChanged(boolean bl) {
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
        block20: {
            block22: {
                block21: {
                    drawables = this.mDrawables;
                    boolean bl = drawable2 != null || drawable3 != null || drawable4 != null || drawable5 != null;
                    if (bl) break block20;
                    if (drawables == null) break block21;
                    if (drawables.mDrawablePadding != 0) break block22;
                    this.mDrawables = null;
                }
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
        int[] arrn = this.textView.getDrawableState();
        if (drawable2 != null) {
            drawable2.setState(arrn);
            drawable2.copyBounds((Rect)drawables);
            drawable2.setCallback((Drawable.Callback)this.textView);
            drawables2.mDrawableSizeLeft = drawables.width();
            drawables2.mDrawableHeightLeft = drawables.height();
        } else {
            drawables2.mDrawableHeightLeft = 0;
            drawables2.mDrawableSizeLeft = 0;
        }
        if (drawable4 != null) {
            drawable4.setState(arrn);
            drawable4.copyBounds((Rect)drawables);
            drawable4.setCallback((Drawable.Callback)this.textView);
            drawables2.mDrawableSizeRight = drawables.width();
            drawables2.mDrawableHeightRight = drawables.height();
        } else {
            drawables2.mDrawableHeightRight = 0;
            drawables2.mDrawableSizeRight = 0;
        }
        if (drawable3 != null) {
            drawable3.setState(arrn);
            drawable3.copyBounds((Rect)drawables);
            drawable3.setCallback((Drawable.Callback)this.textView);
            drawables2.mDrawableSizeTop = drawables.height();
            drawables2.mDrawableWidthTop = drawables.width();
        } else {
            drawables2.mDrawableWidthTop = 0;
            drawables2.mDrawableSizeTop = 0;
        }
        if (drawable5 != null) {
            drawable5.setState(arrn);
            drawable5.copyBounds((Rect)drawables);
            drawable5.setCallback((Drawable.Callback)this.textView);
            drawables2.mDrawableSizeBottom = drawables.height();
            drawables2.mDrawableWidthBottom = drawables.width();
        } else {
            drawables2.mDrawableWidthBottom = 0;
            drawables2.mDrawableSizeBottom = 0;
        }
        this.mDrawables = drawables2;
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
     */
    public void setError(CharSequence charSequence, Drawable drawable2) {
        this.mError = charSequence;
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            this.textView.setCompoundDrawables(drawables.mDrawableLeft, drawables.mDrawableTop, drawable2, drawables.mDrawableBottom);
        } else {
            this.textView.setCompoundDrawables(null, null, drawable2, null);
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
            if (!this.textView.isFocused()) return;
            {
                this.showError();
                return;
            }
        }
    }

    public void setFrame() {
        if (this.errorPopup != null) {
            this.errorPopup.update((View)this.textView, this.getErrorX(), this.getErrorY(), this.errorPopup.getWidth(), this.errorPopup.getHeight());
        }
    }

    public void setTheError(CharSequence charSequence) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        this.mError = TextUtils.stringOrSpannedString((CharSequence)charSequence);
    }

    static class Drawables {
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

        Drawables() {
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
                this.mView.setBackground(TextViewErrorHelper.this.textView.getResources().getDrawable(2130837712));
                return;
            }
            this.mView.setBackground(TextViewErrorHelper.this.textView.getResources().getDrawable(2130837711));
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

