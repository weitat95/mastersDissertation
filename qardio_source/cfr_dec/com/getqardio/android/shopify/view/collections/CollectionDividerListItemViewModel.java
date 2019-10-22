/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;

final class CollectionDividerListItemViewModel
extends ListItemViewModel<Collection> {
    CollectionDividerListItemViewModel(Collection collection) {
        super(collection, 2130968665);
    }

    @Override
    public ListItemViewHolder<Collection, ListItemViewModel<Collection>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof CollectionDividerListItemViewModel) {
            object = (Collection)((CollectionDividerListItemViewModel)object).payload();
            return ((Collection)this.payload()).equals(object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof CollectionDividerListItemViewModel) {
            object = (Collection)((CollectionDividerListItemViewModel)object).payload();
            return ((Collection)this.payload()).equalsById((Collection)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Collection, ListItemViewModel<Collection>> {
        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }
    }

}

