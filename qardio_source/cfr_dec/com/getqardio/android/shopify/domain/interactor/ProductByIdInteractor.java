/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.ProductDetails;
import io.reactivex.Single;

public interface ProductByIdInteractor {
    public Single<ProductDetails> execute(String var1);
}

