/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Product;
import io.reactivex.Single;
import java.util.List;

public interface CollectionProductNextPageInteractor {
    public Single<List<Product>> execute(String var1, String var2, int var3);
}

