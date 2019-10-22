/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintAwareDrawable {
    public void setTint(int var1);

    public void setTintList(ColorStateList var1);

    public void setTintMode(PorterDuff.Mode var1);
}

