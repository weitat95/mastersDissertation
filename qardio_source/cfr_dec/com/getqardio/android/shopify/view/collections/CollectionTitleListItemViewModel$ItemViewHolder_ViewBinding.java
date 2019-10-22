/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.collections;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.CollectionTitleListItemViewModel;

public final class CollectionTitleListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private CollectionTitleListItemViewModel.ItemViewHolder target;

    public CollectionTitleListItemViewModel$ItemViewHolder_ViewBinding(CollectionTitleListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        itemViewHolder.titleView = Utils.findRequiredViewAsType(view, 2131820567, "field 'titleView'", TextView.class);
    }

    public void unbind() {
        CollectionTitleListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.titleView = null;
    }
}

