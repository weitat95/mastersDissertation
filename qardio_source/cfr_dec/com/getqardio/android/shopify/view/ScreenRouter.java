/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 */
package com.getqardio.android.shopify.view;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.getqardio.android.shopify.util.BiConsumer;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ScreenActionEvent;
import com.getqardio.android.shopify.view.ScreenRouter$$Lambda$1;
import com.getqardio.android.shopify.view.ScreenRouter$$Lambda$2;
import com.getqardio.android.shopify.view.ScreenRouter$$Lambda$3;
import com.getqardio.android.shopify.view.ScreenRouter$$Lambda$4;
import com.getqardio.android.shopify.view.ScreenRouter$$Lambda$5;
import com.getqardio.android.shopify.view.cart.AndroidPayConfirmationClickActionEvent;
import com.getqardio.android.shopify.view.cart.CartActivity;
import com.getqardio.android.shopify.view.cart.CartClickActionEvent;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity;
import com.getqardio.android.shopify.view.collections.CollectionClickActionEvent;
import com.getqardio.android.shopify.view.collections.CollectionProductClickActionEvent;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity;
import com.getqardio.android.shopify.view.products.ProductClickActionEvent;
import com.getqardio.android.shopify.view.products.ProductListActivity;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayCart;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ScreenRouter {
    private final Map<String, BiConsumer<Context, ? extends ScreenActionEvent>> routes = new LinkedHashMap<String, BiConsumer<Context, ? extends ScreenActionEvent>>();

    private ScreenRouter() {
        this.registerInternal(CollectionClickActionEvent.ACTION, ScreenRouter$$Lambda$1.lambdaFactory$()).registerInternal(CollectionProductClickActionEvent.ACTION, ScreenRouter$$Lambda$2.lambdaFactory$()).registerInternal(ProductClickActionEvent.ACTION, ScreenRouter$$Lambda$3.lambdaFactory$()).registerInternal(CartClickActionEvent.ACTION, ScreenRouter$$Lambda$4.lambdaFactory$()).registerInternal(AndroidPayConfirmationClickActionEvent.ACTION, ScreenRouter$$Lambda$5.lambdaFactory$());
    }

    static /* synthetic */ void lambda$new$0(Context context, CollectionClickActionEvent collectionClickActionEvent) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("collection_id", collectionClickActionEvent.id());
        intent.putExtra("collection_image_url", collectionClickActionEvent.imageUrl());
        intent.putExtra("collection_title", collectionClickActionEvent.title());
        intent.putExtra(ScreenActionEvent.class.getName(), (Parcelable)collectionClickActionEvent);
        context.startActivity(intent);
    }

    static /* synthetic */ void lambda$new$1(Context context, CollectionProductClickActionEvent collectionProductClickActionEvent) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product_id", collectionProductClickActionEvent.id());
        intent.putExtra("product_image_url", collectionProductClickActionEvent.imageUrl());
        intent.putExtra("product_title", collectionProductClickActionEvent.title());
        intent.putExtra("product_price", collectionProductClickActionEvent.price().doubleValue());
        intent.putExtra("product_sku", collectionProductClickActionEvent.sku());
        intent.putExtra(ScreenActionEvent.class.getName(), (Parcelable)collectionProductClickActionEvent);
        context.startActivity(intent);
    }

    static /* synthetic */ void lambda$new$2(Context context, ProductClickActionEvent productClickActionEvent) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product_id", productClickActionEvent.id());
        intent.putExtra("product_image_url", productClickActionEvent.imageUrl());
        intent.putExtra("product_title", productClickActionEvent.title());
        intent.putExtra("product_price", productClickActionEvent.price().doubleValue());
        intent.putExtra("product_sku", productClickActionEvent.sku());
        intent.putExtra(ScreenActionEvent.class.getName(), (Parcelable)productClickActionEvent);
        context.startActivity(intent);
    }

    static /* synthetic */ void lambda$new$3(Context context, CartClickActionEvent cartClickActionEvent) {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(ScreenActionEvent.class.getName(), (Parcelable)cartClickActionEvent);
        context.startActivity(intent);
    }

    static /* synthetic */ void lambda$new$4(Context context, AndroidPayConfirmationClickActionEvent androidPayConfirmationClickActionEvent) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(ScreenActionEvent.class.getName(), (Parcelable)androidPayConfirmationClickActionEvent);
        intent.putExtra("checkout_id", androidPayConfirmationClickActionEvent.checkoutId());
        intent.putExtra("pay_cart", (Parcelable)androidPayConfirmationClickActionEvent.payCart());
        intent.putExtra("masked_wallet", (Parcelable)androidPayConfirmationClickActionEvent.maskedWallet());
        context.startActivity(intent);
    }

    private <T extends ScreenActionEvent> ScreenRouter registerInternal(String string2, BiConsumer<Context, T> biConsumer) {
        this.routes.put(string2, biConsumer);
        return this;
    }

    public static <T extends ScreenActionEvent> void route(Context context, T t) {
        SingletonHolder.INSTANCE.routeInternal(context, t);
    }

    private <T extends ScreenActionEvent> void routeInternal(Context context, T t) {
        Util.checkNotNull(context, "context == null");
        Util.checkNotNull(t, "screenActionEvent == null");
        BiConsumer<Context, ? extends ScreenActionEvent> biConsumer = this.routes.get(t.action());
        if (biConsumer == null) {
            throw new IllegalArgumentException(String.format("Can't find route for: %s", t.action));
        }
        biConsumer.accept(context, t);
    }

    private static interface SingletonHolder {
        public static final ScreenRouter INSTANCE = new ScreenRouter();
    }

}

