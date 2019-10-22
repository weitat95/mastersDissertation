/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.view.LayoutInflater
 *  android.widget.SpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;

public interface ThemedSpinnerAdapter
extends SpinnerAdapter {
    public Resources.Theme getDropDownViewTheme();

    public void setDropDownViewTheme(Resources.Theme var1);

    public static final class Helper {
        private final Context mContext;
        private final LayoutInflater mInflater;

        public Helper(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from((Context)context);
        }
    }

}

