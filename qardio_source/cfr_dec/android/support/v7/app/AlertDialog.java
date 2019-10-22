/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Message
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.Window
 *  android.widget.ListAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.appcompat.R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;

public class AlertDialog
extends AppCompatDialog
implements DialogInterface {
    final AlertController mAlert = new AlertController(this.getContext(), this, this.getWindow());

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, int n) {
        super(context, AlertDialog.resolveDialogTheme(context, n));
    }

    protected AlertDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        this.setCancelable(bl);
        this.setOnCancelListener(onCancelListener);
    }

    static int resolveDialogTheme(Context context, int n) {
        if ((n >>> 24 & 0xFF) >= 1) {
            return n;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(n, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(n, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    public void setButton(int n, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(n, charSequence, onClickListener, null);
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public static class Builder {
        private final AlertController.AlertParams P;
        private final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int n) {
            this.P = new AlertController.AlertParams((Context)new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, n)));
            this.mTheme = n;
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener);
            }
            return alertDialog;
        }

        public Context getContext() {
            return this.P.mContext;
        }

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCancelable(boolean bl) {
            this.P.mCancelable = bl;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public Builder setIcon(Drawable drawable2) {
            this.P.mIcon = drawable2;
            return this;
        }

        public Builder setItems(CharSequence[] arrcharSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = arrcharSequence;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(int n) {
            this.P.mMessage = this.P.mContext.getText(n);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setNegativeButton(int n, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = this.P.mContext.getText(n);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setPositiveButton(int n, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = this.P.mContext.getText(n);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setTitle(int n) {
            this.P.mTitle = this.P.mContext.getText(n);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public Builder setView(View view) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            this.P.mViewSpacingSpecified = false;
            return this;
        }

        public AlertDialog show() {
            AlertDialog alertDialog = this.create();
            alertDialog.show();
            return alertDialog;
        }
    }

}

