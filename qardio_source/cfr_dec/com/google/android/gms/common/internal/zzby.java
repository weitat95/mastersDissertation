/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.widget.Button
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzi;

public final class zzby
extends Button {
    public zzby(Context context) {
        this(context, null);
    }

    private zzby(Context context, AttributeSet attributeSet) {
        super(context, null, 16842824);
    }

    private static int zzf(int n, int n2, int n3, int n4) {
        switch (n) {
            default: {
                throw new IllegalStateException(new StringBuilder(33).append("Unknown color scheme: ").append(n).toString());
            }
            case 1: {
                n2 = n3;
            }
            case 0: {
                return n2;
            }
            case 2: 
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(Resources resources, int n, int n2) {
        this.setTypeface(Typeface.DEFAULT_BOLD);
        this.setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        this.setMinHeight((int)(f * 48.0f + 0.5f));
        this.setMinWidth((int)(f * 48.0f + 0.5f));
        int n3 = zzby.zzf(n2, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light);
        int n4 = zzby.zzf(n2, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light);
        switch (n) {
            default: {
                throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(n).toString());
            }
            case 2: {
                n4 = n3;
                break;
            }
            case 0: 
            case 1: 
        }
        Drawable drawable2 = DrawableCompat.wrap(resources.getDrawable(n4));
        DrawableCompat.setTintList(drawable2, resources.getColorStateList(R.color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(drawable2, PorterDuff.Mode.SRC_ATOP);
        this.setBackgroundDrawable(drawable2);
        this.setTextColor(zzbq.checkNotNull(resources.getColorStateList(zzby.zzf(n2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
        switch (n) {
            default: {
                throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(n).toString());
            }
            case 0: {
                this.setText((CharSequence)resources.getString(R.string.common_signin_button_text));
                break;
            }
            case 1: {
                this.setText((CharSequence)resources.getString(R.string.common_signin_button_text_long));
                break;
            }
            case 2: {
                this.setText(null);
            }
        }
        this.setTransformationMethod(null);
        if (zzi.zzcs(this.getContext())) {
            this.setGravity(19);
        }
    }
}

