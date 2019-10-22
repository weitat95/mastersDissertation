/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.RxUtil;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$1;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$10;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$11;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$12;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$13;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$14;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$15;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$16;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$17;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$18;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$19;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$2;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$20;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$21;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$22;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$23;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$24;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$25;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$26;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$27;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$28;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$29;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$3;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$30;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$31;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$32;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$33;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$34;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$35;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$36;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$37;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$38;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$39;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$4;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$40;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$41;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$42;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$43;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$5;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$6;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$7;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$8;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository$$Lambda$9;
import com.getqardio.android.shopify.domain.repository.UserError;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.MutationGraphCall;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class CheckoutRepository {
    private final GraphClient graphClient;

    public CheckoutRepository(GraphClient graphClient) {
        this.graphClient = Util.checkNotNull(graphClient, "graphClient == null");
    }

    static /* synthetic */ void lambda$checkout$5(String string2, Storefront.NodeQueryDefinition nodeQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(string2), nodeQueryDefinition);
    }

    static /* synthetic */ Storefront.Checkout lambda$checkout$6(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Checkout)queryRoot.getNode();
    }

    static /* synthetic */ void lambda$complete$20(String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutCompleteWithTokenizedPayment(new ID(string2), tokenizedPaymentInput, CheckoutRepository$$Lambda$32.lambdaFactory$(checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition));
    }

    static /* synthetic */ SingleSource lambda$complete$21(Storefront.CheckoutCompleteWithTokenizedPaymentPayload checkoutCompleteWithTokenizedPaymentPayload) throws Exception {
        if (checkoutCompleteWithTokenizedPaymentPayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutCompleteWithTokenizedPaymentPayload);
        }
        return Single.error(new UserError(Util.mapItems(checkoutCompleteWithTokenizedPaymentPayload.getUserErrors(), CheckoutRepository$$Lambda$31.lambdaFactory$())));
    }

    static /* synthetic */ void lambda$create$0(Storefront.CheckoutCreateInput checkoutCreateInput, Storefront.CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutCreate(checkoutCreateInput, checkoutCreatePayloadQueryDefinition);
    }

    static /* synthetic */ void lambda$null$1(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    static /* synthetic */ void lambda$null$10(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    static /* synthetic */ void lambda$null$11(Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition, Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        checkoutShippingLineUpdatePayloadQueryDefinition.define(checkoutShippingLineUpdatePayloadQuery.userErrors(CheckoutRepository$$Lambda$39.lambdaFactory$()));
    }

    static /* synthetic */ void lambda$null$14(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    static /* synthetic */ void lambda$null$15(Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition, Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        checkoutEmailUpdatePayloadQueryDefinition.define(checkoutEmailUpdatePayloadQuery.userErrors(CheckoutRepository$$Lambda$36.lambdaFactory$()));
    }

    static /* synthetic */ void lambda$null$18(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    static /* synthetic */ void lambda$null$19(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition.define(checkoutCompleteWithTokenizedPaymentPayloadQuery.userErrors(CheckoutRepository$$Lambda$33.lambdaFactory$()));
    }

    static /* synthetic */ void lambda$null$2(Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition, Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        checkoutShippingAddressUpdatePayloadQueryDefinition.define(checkoutShippingAddressUpdatePayloadQuery.userErrors(CheckoutRepository$$Lambda$43.lambdaFactory$()));
    }

    static /* synthetic */ void lambda$null$7(Storefront.CheckoutQueryDefinition checkoutQueryDefinition, Storefront.NodeQuery nodeQuery) {
        nodeQuery.onCheckout(checkoutQueryDefinition);
    }

    static /* synthetic */ void lambda$paymentById$22(String string2, Storefront.NodeQueryDefinition nodeQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(string2), nodeQueryDefinition);
    }

    static /* synthetic */ Storefront.Payment lambda$paymentById$23(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Payment)queryRoot.getNode();
    }

    static /* synthetic */ void lambda$shippingRates$8(String string2, Storefront.CheckoutQueryDefinition checkoutQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(string2), CheckoutRepository$$Lambda$40.lambdaFactory$(checkoutQueryDefinition));
    }

    static /* synthetic */ Storefront.Checkout lambda$shippingRates$9(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Checkout)queryRoot.getNode();
    }

    static /* synthetic */ void lambda$updateEmail$16(String string2, String string3, Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutEmailUpdate(new ID(string2), string3, CheckoutRepository$$Lambda$35.lambdaFactory$(checkoutEmailUpdatePayloadQueryDefinition));
    }

    static /* synthetic */ SingleSource lambda$updateEmail$17(Storefront.CheckoutEmailUpdatePayload checkoutEmailUpdatePayload) throws Exception {
        if (checkoutEmailUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutEmailUpdatePayload);
        }
        return Single.error(new UserError(Util.mapItems(checkoutEmailUpdatePayload.getUserErrors(), CheckoutRepository$$Lambda$34.lambdaFactory$())));
    }

    static /* synthetic */ void lambda$updateShippingAddress$3(Storefront.MailingAddressInput mailingAddressInput, String string2, Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutShippingAddressUpdate(mailingAddressInput, new ID(string2), CheckoutRepository$$Lambda$42.lambdaFactory$(checkoutShippingAddressUpdatePayloadQueryDefinition));
    }

    static /* synthetic */ SingleSource lambda$updateShippingAddress$4(Storefront.CheckoutShippingAddressUpdatePayload checkoutShippingAddressUpdatePayload) throws Exception {
        if (checkoutShippingAddressUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutShippingAddressUpdatePayload);
        }
        return Single.error(new UserError(Util.mapItems(checkoutShippingAddressUpdatePayload.getUserErrors(), CheckoutRepository$$Lambda$41.lambdaFactory$())));
    }

    static /* synthetic */ void lambda$updateShippingLine$12(String string2, String string3, Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutShippingLineUpdate(new ID(string2), string3, CheckoutRepository$$Lambda$38.lambdaFactory$(checkoutShippingLineUpdatePayloadQueryDefinition));
    }

    static /* synthetic */ SingleSource lambda$updateShippingLine$13(Storefront.CheckoutShippingLineUpdatePayload checkoutShippingLineUpdatePayload) throws Exception {
        if (checkoutShippingLineUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutShippingLineUpdatePayload);
        }
        return Single.error(new UserError(Util.mapItems(checkoutShippingLineUpdatePayload.getUserErrors(), CheckoutRepository$$Lambda$37.lambdaFactory$())));
    }

    public Single<Storefront.Checkout> checkout(String string2, Storefront.NodeQueryDefinition nodeQueryDefinition) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotNull(nodeQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(CheckoutRepository$$Lambda$8.lambdaFactory$(string2, nodeQueryDefinition))).cachePolicy(HttpCachePolicy.NETWORK_FIRST.expireAfter(5L, TimeUnit.MINUTES))).map(CheckoutRepository$$Lambda$9.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Payment> complete(String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotNull(tokenizedPaymentInput, "paymentInput == null");
        Util.checkNotNull(checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(CheckoutRepository$$Lambda$23.lambdaFactory$(string2, tokenizedPaymentInput, checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition)))).map(CheckoutRepository$$Lambda$24.lambdaFactory$()).flatMap(CheckoutRepository$$Lambda$25.lambdaFactory$()).map(CheckoutRepository$$Lambda$26.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Checkout> create(Storefront.CheckoutCreateInput checkoutCreateInput, Storefront.CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition) {
        Util.checkNotNull(checkoutCreateInput, "input == null");
        Util.checkNotNull(checkoutCreatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(CheckoutRepository$$Lambda$1.lambdaFactory$(checkoutCreateInput, checkoutCreatePayloadQueryDefinition)))).map(CheckoutRepository$$Lambda$2.lambdaFactory$()).map(CheckoutRepository$$Lambda$3.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Payment> paymentById(String object, Storefront.NodeQueryDefinition nodeQueryDefinition) {
        Util.checkNotBlank((String)object, "paymentId can't be empty");
        Util.checkNotNull(nodeQueryDefinition, "query == null");
        object = this.graphClient.queryGraph(Storefront.query(CheckoutRepository$$Lambda$27.lambdaFactory$((String)object, nodeQueryDefinition))).cachePolicy(HttpCachePolicy.NETWORK_ONLY);
        object.getClass();
        return Single.fromCallable(CheckoutRepository$$Lambda$28.lambdaFactory$((GraphCall)object)).flatMap(CheckoutRepository$$Lambda$29.lambdaFactory$()).map(CheckoutRepository$$Lambda$30.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.AvailableShippingRates> shippingRates(String object, Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        Util.checkNotBlank((String)object, "checkoutId can't be empty");
        Util.checkNotNull(checkoutQueryDefinition, "query == null");
        object = this.graphClient.queryGraph(Storefront.query(CheckoutRepository$$Lambda$10.lambdaFactory$((String)object, checkoutQueryDefinition))).cachePolicy(HttpCachePolicy.NETWORK_ONLY);
        object.getClass();
        return Single.fromCallable(CheckoutRepository$$Lambda$11.lambdaFactory$((GraphCall)object)).flatMap(CheckoutRepository$$Lambda$12.lambdaFactory$()).map(CheckoutRepository$$Lambda$13.lambdaFactory$()).map(CheckoutRepository$$Lambda$14.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Checkout> updateEmail(String string2, String string3, Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotBlank(string3, "email can't be empty");
        Util.checkNotNull(checkoutEmailUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(CheckoutRepository$$Lambda$19.lambdaFactory$(string2, string3, checkoutEmailUpdatePayloadQueryDefinition)))).map(CheckoutRepository$$Lambda$20.lambdaFactory$()).flatMap(CheckoutRepository$$Lambda$21.lambdaFactory$()).map(CheckoutRepository$$Lambda$22.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Checkout> updateShippingAddress(String string2, Storefront.MailingAddressInput mailingAddressInput, Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotNull(mailingAddressInput, "input == null");
        Util.checkNotNull(checkoutShippingAddressUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(CheckoutRepository$$Lambda$4.lambdaFactory$(mailingAddressInput, string2, checkoutShippingAddressUpdatePayloadQueryDefinition)))).map(CheckoutRepository$$Lambda$5.lambdaFactory$()).flatMap(CheckoutRepository$$Lambda$6.lambdaFactory$()).map(CheckoutRepository$$Lambda$7.lambdaFactory$()).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Checkout> updateShippingLine(String string2, String string3, Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        Util.checkNotNull(checkoutShippingLineUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(CheckoutRepository$$Lambda$15.lambdaFactory$(string2, string3, checkoutShippingLineUpdatePayloadQueryDefinition)))).map(CheckoutRepository$$Lambda$16.lambdaFactory$()).flatMap(CheckoutRepository$$Lambda$17.lambdaFactory$()).map(CheckoutRepository$$Lambda$18.lambdaFactory$()).subscribeOn(Schedulers.io());
    }
}

