/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.ShopSettings;
import io.reactivex.Single;

public interface ShopSettingInteractor {
    public Single<ShopSettings> execute();
}

