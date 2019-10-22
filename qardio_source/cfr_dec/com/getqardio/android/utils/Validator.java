/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.util.Patterns
 *  android.widget.EditText
 */
package com.getqardio.android.utils;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import com.getqardio.android.CustomApplication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {
    public static float getMaxWeightForUnit(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return 200.9f;
            }
            case 2: {
                return 31.63f;
            }
            case 1: 
        }
        return 442.9f;
    }

    public static float getMinWeightForUnit(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return 10.0f;
            }
            case 2: {
                return 1.58f;
            }
            case 1: 
        }
        return 22.05f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isEditValid(Pattern pattern, EditText editText, int n, int n2, boolean bl) {
        if (TextUtils.isEmpty((CharSequence)editText.getText().toString()) || !TextUtils.isEmpty((CharSequence)editText.getText().toString()) && !pattern.matcher(editText.getText().toString()).matches()) {
            if (!bl) return false;
            {
                editText.setError((CharSequence)CustomApplication.getApplication().getString(n));
            }
            return false;
        }
        if (editText.getText().toString().length() > 80) {
            if (!bl) return false;
            {
                editText.setError((CharSequence)CustomApplication.getApplication().getString(n2));
                return false;
            }
        }
        if (!bl) return true;
        {
            editText.setError(null);
        }
        return true;
    }

    public static boolean isEmailValid(EditText editText, int n, int n2) {
        return Validator.isEmailValid(editText, n, n2, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isEmailValid(EditText editText, int n, int n2, boolean bl) {
        String string2 = editText.getEditableText().toString();
        boolean bl2 = !TextUtils.isEmpty((CharSequence)string2) && Patterns.EMAIL_ADDRESS.matcher(string2).matches();
        if (bl) {
            if (!bl2) {
                editText.setError((CharSequence)editText.getResources().getString(n));
            } else {
                editText.setError(null);
            }
        }
        if (bl2) {
            editText.setError(null);
        }
        return bl2;
    }

    public static boolean isHeightMaxValid(int n, float f) {
        return n == 0 && f <= 250.0f || n == 1 && f <= 8.2f;
    }

    public static boolean isHeightMinValid(int n, float f) {
        return n == 0 && f >= 100.0f || n == 1 && f >= 3.281f;
    }

    public static boolean isNameValid(EditText editText, boolean bl, int n, boolean bl2) {
        String string2 = editText.getText().toString();
        int n2 = string2.trim().length();
        if (bl && n2 == 0 || string2.length() > 80) {
            if (bl2) {
                editText.setError((CharSequence)CustomApplication.getApplication().getString(n));
            }
            return false;
        }
        if (bl2) {
            editText.setError(null);
        }
        return true;
    }

    public static boolean isWeightMaxValid(int n, float f) {
        return n == 0 && f <= 200.9f || n == 2 && f <= 31.63f || n == 1 && f <= 442.9f;
    }

    public static boolean isWeightMinValid(int n, float f) {
        return n == 0 && f >= 10.0f || n == 2 && f >= 1.58f || n == 1 && f >= 22.05f;
    }
}

