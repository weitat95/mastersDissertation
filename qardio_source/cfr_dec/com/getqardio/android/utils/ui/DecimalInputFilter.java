/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.Spanned
 */
package com.getqardio.android.utils.ui;

import android.text.Spanned;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BaseInputFilter;
import java.text.DecimalFormatSymbols;

public class DecimalInputFilter
extends BaseInputFilter {
    private char decimalSymbol = DecimalFormatSymbols.getInstance(Utils.getLocale()).getDecimalSeparator();

    @Override
    protected boolean isCharAllowed(Spanned spanned, char c) {
        return Character.isDigit(c) || spanned != null && !spanned.toString().contains(String.valueOf(c)) && c == this.decimalSymbol;
    }
}

