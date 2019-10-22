/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.widget.image;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.widget.image.ImageGalleryView;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class ImageGalleryView$PagerIndicatorListItemModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private ImageGalleryView.PagerIndicatorListItemModel.ItemViewHolder target;
    private View view2131820710;

    public ImageGalleryView$PagerIndicatorListItemModel$ItemViewHolder_ViewBinding(final ImageGalleryView.PagerIndicatorListItemModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        view = Utils.findRequiredView(view, 2131820710, "field 'imageView' and method 'onImageClick'");
        itemViewHolder.imageView = Utils.castView(view, 2131820710, "field 'imageView'", ShopifyDraweeView.class);
        this.view2131820710 = view;
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onImageClick();
            }
        });
    }

    public void unbind() {
        ImageGalleryView.PagerIndicatorListItemModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.imageView = null;
        this.view2131820710.setOnClickListener(null);
        this.view2131820710 = null;
    }

}

