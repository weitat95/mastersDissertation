/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Collection;
import io.reactivex.Single;
import java.util.List;

public interface CollectionNextPageInteractor {
    public Single<List<Collection>> execute(String var1, int var2);
}

