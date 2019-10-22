/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.FrameLayout
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbx;
import com.google.android.gms.common.internal.zzby;
import com.google.android.gms.dynamic.zzq;

public final class SignInButton
extends FrameLayout
implements View.OnClickListener {
    private int mColor;
    private int mSize;
    private View zzflq;
    private View.OnClickListener zzflr = null;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SignInButton, 0, 0);
        try {
            this.mSize = context.getInt(R.styleable.SignInButton_buttonSize, 0);
            this.mColor = context.getInt(R.styleable.SignInButton_colorScheme, 2);
            this.setStyle(this.mSize, this.mColor);
            return;
        }
        finally {
            context.recycle();
        }
    }

    public final void onClick(View view) {
        if (this.zzflr != null && view == this.zzflq) {
            this.zzflr.onClick((View)this);
        }
    }

    public final void setColorScheme(int n) {
        this.setStyle(this.mSize, n);
    }

    public final void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.zzflq.setEnabled(bl);
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.zzflr = onClickListener;
        if (this.zzflq != null) {
            this.zzflq.setOnClickListener((View.OnClickListener)this);
        }
    }

    @Deprecated
    public final void setScopes(Scope[] arrscope) {
        this.setStyle(this.mSize, this.mColor);
    }

    public final void setSize(int n) {
        this.setStyle(n, this.mColor);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setStyle(int n, int n2) {
        this.mSize = n;
        this.mColor = n2;
        Context context = this.getContext();
        if (this.zzflq != null) {
            this.removeView(this.zzflq);
        }
        try {
            this.zzflq = zzbx.zzc(context, this.mSize, this.mColor);
        }
        catch (zzq zzq2) {
            Log.w((String)"SignInButton", (String)"Sign in button not found, using placeholder instead");
            n = this.mSize;
            n2 = this.mColor;
            zzby zzby2 = new zzby(context);
            zzby2.zza(context.getResources(), n, n2);
            this.zzflq = zzby2;
        }
        this.addView(this.zzflq);
        this.zzflq.setEnabled(this.isEnabled());
        this.zzflq.setOnClickListener((View.OnClickListener)this);
    }

    @Deprecated
    public final void setStyle(int n, int n2, Scope[] arrscope) {
        this.setStyle(n, n2);
    }
}

