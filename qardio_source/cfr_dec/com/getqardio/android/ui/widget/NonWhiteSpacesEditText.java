/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.text.Editable
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import com.getqardio.android.ui.widget.CustomEditText;

public class NonWhiteSpacesEditText
extends CustomEditText {
    public NonWhiteSpacesEditText(Context context) {
        super(context);
    }

    public NonWhiteSpacesEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NonWhiteSpacesEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (!bl) {
            this.setText((CharSequence)this.getEditableText().toString().trim());
        }
    }
}

