/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.shopify.view.products;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ScreenActionEvent;
import java.math.BigDecimal;

public final class ProductClickActionEvent
extends ScreenActionEvent
implements Parcelable {
    public static final String ACTION;
    public static final Parcelable.Creator<ProductClickActionEvent> CREATOR;
    private static final String EXTRAS_ID = "product_id";
    private static final String EXTRAS_IMAGE_URL = "product_image_url";
    private static final String EXTRAS_PRICE = "product_price";
    private static final String EXTRAS_SKU = "product_sku";
    private static final String EXTRAS_TITLE = "product_title";

    static {
        CREATOR = new Parcelable.Creator<ProductClickActionEvent>(){

            public ProductClickActionEvent createFromParcel(Parcel parcel) {
                return new ProductClickActionEvent(parcel);
            }

            public ProductClickActionEvent[] newArray(int n) {
                return new ProductClickActionEvent[n];
            }
        };
        ACTION = ProductClickActionEvent.class.getSimpleName();
    }

    ProductClickActionEvent(Parcel parcel) {
        super(parcel);
    }

    ProductClickActionEvent(Product product) {
        super(ACTION);
        Util.checkNotNull(product, "collectionProduct == null");
        this.payload.putString(EXTRAS_ID, product.id);
        this.payload.putString(EXTRAS_IMAGE_URL, product.image);
        this.payload.putString(EXTRAS_TITLE, product.title);
        this.payload.putString(EXTRAS_SKU, product.sku);
        this.payload.putDouble(EXTRAS_PRICE, product.price.doubleValue());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String id() {
        return this.payload().getString(EXTRAS_ID);
    }

    public String imageUrl() {
        return this.payload().getString(EXTRAS_IMAGE_URL);
    }

    public BigDecimal price() {
        return BigDecimal.valueOf(this.payload().getDouble(EXTRAS_PRICE, 0.0));
    }

    public String sku() {
        return this.payload().getString(EXTRAS_SKU);
    }

    public String title() {
        return this.payload().getString(EXTRAS_TITLE);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}

