/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.getqardio.android.shopify;

import android.content.Context;
import android.net.Uri;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;

public class ShopifyAnalytics {
    private static final String EVENT_ADD_CART = "added to cart";
    private static final String EVENT_CLICK_BUY_NOW = "clicked on buy now";
    private static final String EVENT_CLICK_CART = "clicked on cart";
    private static final String EVENT_INITIATE_CHECKOUT = "initiate checkout";
    private static final String EVENT_VIEWED_PRODUCT = "viewed product";
    private static final String PROPERTY_KEY_SCREEN = "screen name";
    private static final String PROPERTY_KEY_SKU = "sku";
    private static final String PROPERTY_KEY_STORE = "store";
    private static ShopifyAnalytics instance = new ShopifyAnalytics();
    private Context appContext;

    private ShopifyAnalytics() {
    }

    public static final ShopifyAnalytics getInstance() {
        return instance;
    }

    public Uri appendGoogleAnalyticsTracking(String string2) {
        return Uri.parse((String)(string2 + "&utm_source=qardioapp&utm_medium=referral&utm_campaign=store%20" + ShopifyKeyManager.getInstance().getStoreFromCountry()));
    }

    public void init(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public void trackAddToCart(String string2, String string3) {
        Properties properties = new Properties();
        properties.putValue(PROPERTY_KEY_SKU, string2);
        properties.putValue(PROPERTY_KEY_STORE, string3);
        Analytics.with(this.appContext).track(EVENT_ADD_CART, properties);
    }

    public void trackCartClick(String string2) {
        Properties properties = new Properties();
        properties.putValue(PROPERTY_KEY_STORE, string2);
        Analytics.with(this.appContext).track(EVENT_CLICK_CART, properties);
    }

    public void trackClickBuyNow(String string2) {
        Properties properties = new Properties();
        properties.putValue(PROPERTY_KEY_SCREEN, string2);
        Analytics.with(this.appContext).track(EVENT_CLICK_BUY_NOW, properties);
    }

    public void trackFetchShopSettingsCrash(Exception object) {
        object = new Properties();
        Analytics.with(this.appContext).track("crash shopify", (Properties)object);
    }

    public void trackInitiateCheckout(int n, double d, String string2, String string3) {
        Properties properties = new Properties();
        properties.put("quantity", (Object)n);
        properties.put("revenue", (Object)d);
        properties.put(PROPERTY_KEY_STORE, (Object)string2);
        properties.put("payment method", (Object)string3);
        Analytics.with(this.appContext).track(EVENT_INITIATE_CHECKOUT, properties);
    }

    public void trackViewProduct(String string2, String string3) {
        Properties properties = new Properties();
        properties.putValue(PROPERTY_KEY_SKU, string2);
        string2 = string3;
        if ("GB".equalsIgnoreCase(string3)) {
            string2 = "UK";
        }
        properties.putValue(PROPERTY_KEY_STORE, string2);
        Analytics.with(this.appContext).track(EVENT_VIEWED_PRODUCT, properties);
    }
}

