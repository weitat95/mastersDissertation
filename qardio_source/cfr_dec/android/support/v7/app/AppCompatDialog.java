/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Bundle
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class AppCompatDialog
extends Dialog
implements AppCompatCallback {
    private AppCompatDelegate mDelegate;

    public AppCompatDialog(Context context) {
        this(context, 0);
    }

    public AppCompatDialog(Context context, int n) {
        super(context, AppCompatDialog.getThemeResId(context, n));
        this.getDelegate().onCreate(null);
        this.getDelegate().applyDayNight();
    }

    protected AppCompatDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        super(context, bl, onCancelListener);
    }

    private static int getThemeResId(Context context, int n) {
        int n2 = n;
        if (n == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue, true);
            n2 = typedValue.resourceId;
        }
        return n2;
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getDelegate().addContentView(view, layoutParams);
    }

    public <T extends View> T findViewById(int n) {
        return this.getDelegate().findViewById(n);
    }

    public AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create(this, (AppCompatCallback)this);
        }
        return this.mDelegate;
    }

    public ActionBar getSupportActionBar() {
        return this.getDelegate().getSupportActionBar();
    }

    public void invalidateOptionsMenu() {
        this.getDelegate().invalidateOptionsMenu();
    }

    protected void onCreate(Bundle bundle) {
        this.getDelegate().installViewFactory();
        super.onCreate(bundle);
        this.getDelegate().onCreate(bundle);
    }

    protected void onStop() {
        super.onStop();
        this.getDelegate().onStop();
    }

    @Override
    public void onSupportActionModeFinished(ActionMode actionMode) {
    }

    @Override
    public void onSupportActionModeStarted(ActionMode actionMode) {
    }

    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    public void setContentView(int n) {
        this.getDelegate().setContentView(n);
    }

    public void setContentView(View view) {
        this.getDelegate().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getDelegate().setContentView(view, layoutParams);
    }

    public void setTitle(int n) {
        super.setTitle(n);
        this.getDelegate().setTitle(this.getContext().getString(n));
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.getDelegate().setTitle(charSequence);
    }

    public boolean supportRequestWindowFeature(int n) {
        return this.getDelegate().requestWindowFeature(n);
    }
}

