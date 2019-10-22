/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.RxUtil;
import com.getqardio.android.shopify.domain.repository.ShopRepository$$Lambda$1;
import com.getqardio.android.shopify.domain.repository.ShopRepository$$Lambda$2;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class ShopRepository {
    private final GraphClient graphClient;

    public ShopRepository(GraphClient graphClient) {
        this.graphClient = Util.checkNotNull(graphClient, "graphClient == null");
    }

    static /* synthetic */ void lambda$shopSettings$0(Storefront.ShopQueryDefinition shopQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.shop(shopQueryDefinition);
    }

    public Single<Storefront.Shop> shopSettings(Storefront.ShopQueryDefinition shopQueryDefinition) {
        Util.checkNotNull(shopQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(ShopRepository$$Lambda$1.lambdaFactory$(shopQueryDefinition)))).map(ShopRepository$$Lambda$2.lambdaFactory$()).subscribeOn(Schedulers.io());
    }
}

