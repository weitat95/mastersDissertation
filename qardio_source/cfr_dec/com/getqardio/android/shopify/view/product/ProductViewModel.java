/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import android.arch.lifecycle.LiveData;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.view.ViewModel;
import java.util.UUID;

public interface ProductViewModel
extends ViewModel {
    public static final int REQUEST_ID_PRODUCT_DETAILS = UUID.randomUUID().hashCode();

    public void addToCart();

    public LiveData<ProductDetails> productLiveData();

    public void refetch();
}

