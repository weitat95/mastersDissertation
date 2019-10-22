/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 */
package com.getqardio.android.utils.ui;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardHelper {
    public static void hideKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        ((InputMethodManager)context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        ((InputMethodManager)context.getSystemService("input_method")).showSoftInput(view, 0);
    }
}

