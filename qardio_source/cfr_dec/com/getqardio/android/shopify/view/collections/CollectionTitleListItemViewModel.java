/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.collections;

import android.widget.TextView;
import butterknife.BindView;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;

final class CollectionTitleListItemViewModel
extends ListItemViewModel<Collection> {
    CollectionTitleListItemViewModel(Collection collection) {
        super(collection, 2130968669);
    }

    @Override
    public ListItemViewHolder<Collection, ListItemViewModel<Collection>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof CollectionTitleListItemViewModel) {
            object = (Collection)((CollectionTitleListItemViewModel)object).payload();
            return ((Collection)this.payload()).equals(object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof CollectionTitleListItemViewModel) {
            object = (Collection)((CollectionTitleListItemViewModel)object).payload();
            return ((Collection)this.payload()).equalsById((Collection)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Collection, ListItemViewModel<Collection>> {
        @BindView
        TextView titleView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Collection> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.titleView.setText((CharSequence)listItemViewModel.payload().title);
        }
    }

}

