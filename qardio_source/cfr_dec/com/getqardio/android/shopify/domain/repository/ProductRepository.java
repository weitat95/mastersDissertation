/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.shopify.domain.repository;

import android.text.TextUtils;
import com.getqardio.android.shopify.RxUtil;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$1;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$10;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$2;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$3;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$4;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$5;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$6;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$7;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$8;
import com.getqardio.android.shopify.domain.repository.ProductRepository$$Lambda$9;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public final class ProductRepository {
    private final GraphClient graphClient;

    public ProductRepository(GraphClient graphClient) {
        this.graphClient = Util.checkNotNull(graphClient, "graphClient == null");
    }

    static /* synthetic */ void lambda$nextPage$3(String string2, int n, String string3, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(string2), ProductRepository$$Lambda$8.lambdaFactory$(n, string3, productConnectionQueryDefinition));
    }

    static /* synthetic */ Storefront.Collection lambda$nextPage$4(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Collection)queryRoot.getNode();
    }

    static /* synthetic */ void lambda$null$0(String string2, Storefront.CollectionQuery.ProductsArguments productsArguments) {
        String string3 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = null;
        }
        productsArguments.after(string3);
    }

    static /* synthetic */ void lambda$null$1(int n, String string2, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.CollectionQuery collectionQuery) {
        collectionQuery.products(n, ProductRepository$$Lambda$10.lambdaFactory$(string2), productConnectionQueryDefinition);
    }

    static /* synthetic */ void lambda$null$2(int n, String string2, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.NodeQuery nodeQuery) {
        nodeQuery.onCollection(ProductRepository$$Lambda$9.lambdaFactory$(n, string2, productConnectionQueryDefinition));
    }

    static /* synthetic */ void lambda$null$5(Storefront.ProductQueryDefinition productQueryDefinition, Storefront.NodeQuery nodeQuery) {
        nodeQuery.onProduct(productQueryDefinition);
    }

    static /* synthetic */ void lambda$product$6(String string2, Storefront.ProductQueryDefinition productQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(string2), ProductRepository$$Lambda$7.lambdaFactory$(productQueryDefinition));
    }

    static /* synthetic */ Storefront.Product lambda$product$7(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Product)queryRoot.getNode();
    }

    public Single<List<Storefront.ProductEdge>> nextPage(String string2, String string3, int n, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        Util.checkNotNull(string2, "collectionId == null");
        Util.checkNotNull(productConnectionQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(ProductRepository$$Lambda$1.lambdaFactory$(string2, n, string3, productConnectionQueryDefinition)))).map(ProductRepository$$Lambda$2.lambdaFactory$()).map(ProductRepository$$Lambda$3.lambdaFactory$()).map(ProductRepository$$Lambda$4.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Product> product(String string2, Storefront.ProductQueryDefinition productQueryDefinition) {
        Util.checkNotNull(string2, "productId == null");
        Util.checkNotNull(productQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(ProductRepository$$Lambda$5.lambdaFactory$(string2, productQueryDefinition)))).map(ProductRepository$$Lambda$6.lambdaFactory$()).subscribeOn(Schedulers.io());
    }
}

