/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.Window
 */
package com.getqardio.android.shopify.view.collections;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.CollectionListActivity;
import com.getqardio.android.shopify.view.collections.CollectionListView;

public final class CollectionListActivity_ViewBinding
implements Unbinder {
    private CollectionListActivity target;

    public CollectionListActivity_ViewBinding(CollectionListActivity collectionListActivity) {
        this(collectionListActivity, collectionListActivity.getWindow().getDecorView());
    }

    public CollectionListActivity_ViewBinding(CollectionListActivity collectionListActivity, View view) {
        this.target = collectionListActivity;
        collectionListActivity.collectionListView = Utils.findRequiredViewAsType(view, 2131820764, "field 'collectionListView'", CollectionListView.class);
        collectionListActivity.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
    }

    public void unbind() {
        CollectionListActivity collectionListActivity = this.target;
        if (collectionListActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        collectionListActivity.collectionListView = null;
        collectionListActivity.toolbarView = null;
    }
}

