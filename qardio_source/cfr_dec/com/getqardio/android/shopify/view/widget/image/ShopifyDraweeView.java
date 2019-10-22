/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.getqardio.android.shopify.view.widget.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.getqardio.android.shopify.util.ImageUtility;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView$$Lambda$1;

public final class ShopifyDraweeView
extends SimpleDraweeView {
    private int lastMeasureHeight = -1;
    private int lastMeasureWidth = -1;
    private String shopifyImageBaseUrl;

    public ShopifyDraweeView(Context context) {
        super(context);
    }

    public ShopifyDraweeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ShopifyDraweeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public ShopifyDraweeView(Context context, GenericDraweeHierarchy genericDraweeHierarchy) {
        super(context, genericDraweeHierarchy);
    }

    private ImageRequest getShopifyImageRequest(String string2, int n, int n2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return ImageRequestBuilder.newBuilderWithSource(Uri.EMPTY).build();
        }
        return ImageRequestBuilder.newBuilderWithSource(Uri.parse((String)ImageUtility.getSizedImageUrl(string2, n, n2).replace(".jpg", ".progressive.jpg").replace(".jpeg", ".progressive.jpeg"))).setProgressiveRenderingEnabled(true).build();
    }

    private void performLoadShopifyImage(int n, int n2) {
        if (n > 0 && n2 > 0) {
            this.postOnAnimation(ShopifyDraweeView$$Lambda$1.lambdaFactory$(this, this.getShopifyImageRequest(this.shopifyImageBaseUrl, n, n2)));
        }
    }

    /* synthetic */ void lambda$performLoadShopifyImage$0(ImageRequest imageRequest) {
        this.loadImage(imageRequest);
    }

    public void loadImage(ImageRequest imageRequest) {
        this.setController(((PipelineDraweeControllerBuilder)((PipelineDraweeControllerBuilder)Fresco.newDraweeControllerBuilder().setOldController(this.getController())).setImageRequest(imageRequest)).build());
    }

    public void loadShopifyImage(String string2) {
        this.shopifyImageBaseUrl = string2;
        if (TextUtils.isEmpty((CharSequence)this.shopifyImageBaseUrl)) {
            this.loadImage(ImageRequestBuilder.newBuilderWithSource(Uri.EMPTY).build());
            return;
        }
        this.performLoadShopifyImage(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        Integer n4;
        n = n3 = n;
        if (View.MeasureSpec.getMode((int)n3) == 1073741824) {
            n4 = (Integer)this.getTag(2130772017);
            n = n3;
            if (n4 != null) {
                n = n3;
                if (n4 > 0) {
                    n = View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824);
                }
            }
        }
        n3 = n2;
        if (View.MeasureSpec.getMode((int)n2) == 1073741824) {
            n4 = (Integer)this.getTag(2130772016);
            n3 = n2;
            if (n4 != null) {
                n3 = n2;
                if (n4 > 0) {
                    n3 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824);
                }
            }
        }
        super.onMeasure(n, n3);
        n = this.getMeasuredWidth();
        n2 = this.getMeasuredHeight();
        if (!(TextUtils.isEmpty((CharSequence)this.shopifyImageBaseUrl) || this.lastMeasureWidth == n && this.lastMeasureHeight == n2)) {
            this.lastMeasureWidth = n;
            this.lastMeasureHeight = n2;
            this.performLoadShopifyImage(n, n2);
        }
    }
}

