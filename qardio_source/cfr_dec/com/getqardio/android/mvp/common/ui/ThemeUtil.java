/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.util.TypedValue
 */
package com.getqardio.android.mvp.common.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ThemeUtil {
    public static int getSelectableItemBackground(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843534, typedValue, true);
        return typedValue.resourceId;
    }
}

