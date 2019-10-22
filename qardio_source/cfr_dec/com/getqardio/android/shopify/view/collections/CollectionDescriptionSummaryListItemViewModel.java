/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.collections;

import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;

final class CollectionDescriptionSummaryListItemViewModel
extends ListItemViewModel<Collection> {
    CollectionDescriptionSummaryListItemViewModel(Collection collection) {
        super(collection, 2130968664);
    }

    @Override
    public ListItemViewHolder<Collection, ListItemViewModel<Collection>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof CollectionDescriptionSummaryListItemViewModel) {
            object = (Collection)((CollectionDescriptionSummaryListItemViewModel)object).payload();
            return ((Collection)this.payload()).equals(object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof CollectionDescriptionSummaryListItemViewModel) {
            object = (Collection)((CollectionDescriptionSummaryListItemViewModel)object).payload();
            return ((Collection)this.payload()).equalsById((Collection)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Collection, ListItemViewModel<Collection>> {
        @BindView
        TextView descriptionView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void bindModel(ListItemViewModel<Collection> textView, int n) {
            int n2 = 0;
            super.bindModel(textView, n);
            n = !TextUtils.isEmpty((CharSequence)((Collection)textView.payload()).description) ? 1 : 0;
            this.descriptionView.setText((CharSequence)((Collection)textView.payload()).description);
            textView = this.descriptionView;
            n = n != 0 ? n2 : 8;
            textView.setVisibility(n);
        }
    }

}

