/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.LinearLayout
 */
package com.getqardio.android.shopify.view.collections;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.CollectionListView;

public final class CollectionListView_ViewBinding
implements Unbinder {
    private CollectionListView target;

    public CollectionListView_ViewBinding(CollectionListView collectionListView) {
        this(collectionListView, (View)collectionListView);
    }

    public CollectionListView_ViewBinding(CollectionListView collectionListView, View view) {
        this.target = collectionListView;
        collectionListView.listView = Utils.findRequiredViewAsType(view, 2131820758, "field 'listView'", RecyclerView.class);
        collectionListView.swipeRefreshLayoutView = Utils.findRequiredViewAsType(view, 2131820767, "field 'swipeRefreshLayoutView'", SwipeRefreshLayout.class);
        collectionListView.loadingShopify = Utils.findRequiredViewAsType(view, 2131820765, "field 'loadingShopify'", LinearLayout.class);
    }

    public void unbind() {
        CollectionListView collectionListView = this.target;
        if (collectionListView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        collectionListView.listView = null;
        collectionListView.swipeRefreshLayoutView = null;
        collectionListView.loadingShopify = null;
    }
}

