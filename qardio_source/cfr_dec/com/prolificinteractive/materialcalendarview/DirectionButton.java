/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.TypedValue
 *  android.widget.ImageView
 */
package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.TypedValue;
import android.widget.ImageView;

class DirectionButton
extends ImageView {
    public DirectionButton(Context context) {
        super(context);
        this.setBackgroundResource(DirectionButton.getThemeSelectableBackgroundId(context));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getThemeSelectableBackgroundId(Context context) {
        int n;
        int n2 = n = context.getResources().getIdentifier("selectableItemBackgroundBorderless", "attr", context.getPackageName());
        if (n == 0) {
            n2 = Build.VERSION.SDK_INT >= 21 ? 16843868 : 16843534;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(n2, typedValue, true);
        return typedValue.resourceId;
    }

    public void setColor(int n) {
        this.setColorFilter(n, PorterDuff.Mode.SRC_ATOP);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        float f = bl ? 1.0f : 0.1f;
        this.setAlpha(f);
    }
}

