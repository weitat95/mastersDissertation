/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package butterknife.internal;

import android.view.View;

public abstract class DebouncingOnClickListener
implements View.OnClickListener {
    private static final Runnable ENABLE_AGAIN;
    static boolean enabled;

    static {
        enabled = true;
        ENABLE_AGAIN = new Runnable(){

            @Override
            public void run() {
                enabled = true;
            }
        };
    }

    public abstract void doClick(View var1);

    public final void onClick(View view) {
        if (enabled) {
            enabled = false;
            view.post(ENABLE_AGAIN);
            this.doClick(view);
        }
    }

}

