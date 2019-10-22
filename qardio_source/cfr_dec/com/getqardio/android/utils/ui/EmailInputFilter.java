/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.Spanned
 */
package com.getqardio.android.utils.ui;

import android.text.Spanned;
import com.getqardio.android.utils.ui.BaseInputFilter;

public class EmailInputFilter
extends BaseInputFilter {
    @Override
    protected boolean isCharAllowed(Spanned spanned, char c) {
        return !Character.isWhitespace(c);
    }
}

