/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.widget.ListView
 */
package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListView;

public final class ListViewCompat {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean canScrollList(ListView listView, int n) {
        boolean bl = false;
        if (Build.VERSION.SDK_INT >= 19) {
            return listView.canScrollList(n);
        }
        int n2 = listView.getChildCount();
        boolean bl2 = bl;
        if (n2 == 0) return bl2;
        int n3 = listView.getFirstVisiblePosition();
        if (n > 0) {
            n = listView.getChildAt(n2 - 1).getBottom();
            if (n3 + n2 < listView.getCount()) return true;
            bl2 = bl;
            if (n <= listView.getHeight() - listView.getListPaddingBottom()) return bl2;
            return true;
        }
        n = listView.getChildAt(0).getTop();
        if (n3 > 0) return true;
        bl2 = bl;
        if (n >= listView.getListPaddingTop()) return bl2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void scrollListBy(ListView listView, int n) {
        if (Build.VERSION.SDK_INT >= 19) {
            listView.scrollListBy(n);
            return;
        } else {
            View view;
            int n2 = listView.getFirstVisiblePosition();
            if (n2 == -1 || (view = listView.getChildAt(0)) == null) return;
            {
                listView.setSelectionFromTop(n2, view.getTop() - n);
                return;
            }
        }
    }
}

