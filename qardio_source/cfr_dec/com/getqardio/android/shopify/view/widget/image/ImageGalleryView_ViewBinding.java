/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.widget.image;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.widget.image.ImageGalleryView;

public final class ImageGalleryView_ViewBinding
implements Unbinder {
    private ImageGalleryView target;

    public ImageGalleryView_ViewBinding(ImageGalleryView imageGalleryView) {
        this(imageGalleryView, (View)imageGalleryView);
    }

    public ImageGalleryView_ViewBinding(ImageGalleryView imageGalleryView, View view) {
        this.target = imageGalleryView;
        imageGalleryView.pagerView = Utils.findRequiredViewAsType(view, 2131821095, "field 'pagerView'", RecyclerView.class);
        imageGalleryView.pagerIndicatorView = Utils.findRequiredViewAsType(view, 2131821096, "field 'pagerIndicatorView'", RecyclerView.class);
        imageGalleryView.pagerIndicatorFrameView = Utils.findRequiredView(view, 2131821097, "field 'pagerIndicatorFrameView'");
    }

    public void unbind() {
        ImageGalleryView imageGalleryView = this.target;
        if (imageGalleryView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        imageGalleryView.pagerView = null;
        imageGalleryView.pagerIndicatorView = null;
        imageGalleryView.pagerIndicatorFrameView = null;
    }
}

