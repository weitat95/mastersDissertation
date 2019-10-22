/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.InputFilter
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.text.TextUtils
 */
package com.getqardio.android.utils.ui;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

public abstract class BaseInputFilter
implements InputFilter {
    /*
     * Enabled aggressive block sorting
     */
    public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
        n4 = 1;
        StringBuilder stringBuilder = new StringBuilder(n2 - n);
        for (n3 = n; n3 < n2; ++n3) {
            char c = charSequence.charAt(n3);
            if (this.isCharAllowed(spanned, c)) {
                stringBuilder.append(c);
                continue;
            }
            n4 = 0;
        }
        if (n4 != 0) {
            return null;
        }
        if (charSequence instanceof Spanned) {
            spanned = new SpannableString((CharSequence)stringBuilder);
            TextUtils.copySpansFrom((Spanned)((Spanned)charSequence), (int)n, (int)stringBuilder.length(), null, (Spannable)spanned, (int)0);
            return spanned;
        }
        return stringBuilder;
    }

    protected abstract boolean isCharAllowed(Spanned var1, char var2);
}

