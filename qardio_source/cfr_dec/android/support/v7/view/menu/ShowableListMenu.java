/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.ListView
 */
package android.support.v7.view.menu;

import android.widget.ListView;

public interface ShowableListMenu {
    public void dismiss();

    public ListView getListView();

    public boolean isShowing();

    public void show();
}

