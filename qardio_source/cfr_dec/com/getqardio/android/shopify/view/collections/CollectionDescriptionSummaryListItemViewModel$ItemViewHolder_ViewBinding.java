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
import com.getqardio.android.shopify.view.collections.CollectionDescriptionSummaryListItemViewModel;

public final class CollectionDescriptionSummaryListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private CollectionDescriptionSummaryListItemViewModel.ItemViewHolder target;

    public CollectionDescriptionSummaryListItemViewModel$ItemViewHolder_ViewBinding(CollectionDescriptionSummaryListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        itemViewHolder.descriptionView = Utils.findRequiredViewAsType(view, 2131820998, "field 'descriptionView'", TextView.class);
    }

    public void unbind() {
        CollectionDescriptionSummaryListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.descriptionView = null;
    }
}

