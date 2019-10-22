/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.TextUtils
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.getqardio.android.ui.widget.CustomEditText;
import com.getqardio.android.utils.ui.EmailInputFilter;

public class EmailEditText
extends CustomEditText {
    private boolean lockError = false;

    public EmailEditText(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public EmailEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet, 0);
    }

    public EmailEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet, n);
    }

    private void init(Context context, AttributeSet attributeSet, int n) {
        this.setInputType(32);
        this.setFilters(new InputFilter[]{new EmailInputFilter()});
    }

    public void lockError() {
        this.lockError = true;
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (!bl && this.lockError) {
            this.unlockError();
            this.setError(null);
        }
    }

    @Override
    public void setError(CharSequence charSequence) {
        if (!this.lockError) {
            super.setError(charSequence);
        }
    }

    @Override
    public void setError(CharSequence charSequence, Drawable drawable2) {
        if (!this.lockError) {
            super.setError(charSequence, drawable2);
        }
    }

    public void unlockError() {
        this.lockError = false;
    }

    public void unlockErrorIfChanged(String string2) {
        if (!TextUtils.equals((CharSequence)string2, (CharSequence)this.getEditableText().toString())) {
            this.unlockError();
        }
    }
}

