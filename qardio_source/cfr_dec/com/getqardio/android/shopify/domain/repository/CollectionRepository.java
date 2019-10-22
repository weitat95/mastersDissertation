/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.shopify.domain.repository;

import android.text.TextUtils;
import com.getqardio.android.shopify.RxUtil;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$1;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$2;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$3;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$4;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$5;
import com.getqardio.android.shopify.domain.repository.CollectionRepository$$Lambda$6;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public final class CollectionRepository {
    private final GraphClient graphClient;

    public CollectionRepository(GraphClient graphClient) {
        this.graphClient = Util.checkNotNull(graphClient, "graphClient == null");
    }

    static /* synthetic */ void lambda$nextPage$2(int n, String string2, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.shop(CollectionRepository$$Lambda$5.lambdaFactory$(n, string2, collectionConnectionQueryDefinition));
    }

    static /* synthetic */ void lambda$null$0(String string2, Storefront.ShopQuery.CollectionsArguments collectionsArguments) {
        String string3 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = null;
        }
        collectionsArguments.after(string3).sortKey(Storefront.CollectionSortKeys.TITLE);
    }

    static /* synthetic */ void lambda$null$1(int n, String string2, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition, Storefront.ShopQuery shopQuery) {
        shopQuery.collections(n, CollectionRepository$$Lambda$6.lambdaFactory$(string2), collectionConnectionQueryDefinition);
    }

    public Single<List<Storefront.CollectionEdge>> nextPage(String string2, int n, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition) {
        Util.checkNotNull(collectionConnectionQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(CollectionRepository$$Lambda$1.lambdaFactory$(n, string2, collectionConnectionQueryDefinition)))).map(CollectionRepository$$Lambda$2.lambdaFactory$()).map(CollectionRepository$$Lambda$3.lambdaFactory$()).map(CollectionRepository$$Lambda$4.lambdaFactory$()).subscribeOn(Schedulers.io());
    }
}

