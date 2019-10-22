/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.shopify.domain.interactor;

import android.text.TextUtils;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$10;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$11;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$12;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$13;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$14;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$15;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$16;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$5;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$6;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$7;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$8;
import com.getqardio.android.shopify.domain.interactor.Converters$$Lambda$9;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.pay.CardNetworkType;
import com.shopify.graphql.support.ID;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class Converters {
    private Converters() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Checkout convertToCheckout(Storefront.Checkout checkout) {
        Checkout.ShippingRate shippingRate;
        List<Checkout.LineItem> list = Util.mapItems(checkout.getLineItems().getEdges(), Converters$$Lambda$6.lambdaFactory$());
        if (checkout.getShippingLine() != null) {
            shippingRate = Converters.convertToShippingRate(checkout.getShippingLine());
            do {
                return new Checkout(checkout.getId().toString(), checkout.getWebUrl(), checkout.getCurrencyCode().toString(), checkout.getRequiresShipping(), list, Converters.convertToShippingRates(checkout.getAvailableShippingRates()), shippingRate, checkout.getTotalTax(), checkout.getSubtotalPrice(), checkout.getTotalPrice());
                break;
            } while (true);
        }
        shippingRate = null;
        return new Checkout(checkout.getId().toString(), checkout.getWebUrl(), checkout.getCurrencyCode().toString(), checkout.getRequiresShipping(), list, Converters.convertToShippingRates(checkout.getAvailableShippingRates()), shippingRate, checkout.getTotalTax(), checkout.getSubtotalPrice(), checkout.getTotalPrice());
    }

    static List<Collection> convertToCollections(List<Storefront.CollectionEdge> list) {
        return Util.mapItems(list, Converters$$Lambda$5.lambdaFactory$());
    }

    static Payment convertToPayment(Storefront.Payment payment) {
        return new Payment(payment.getId().toString(), payment.getErrorMessage(), payment.getReady());
    }

    static ProductDetails convertToProductDetails(Storefront.Product product) {
        List<String> list = Util.mapItems(product.getImages().getEdges(), Converters$$Lambda$2.lambdaFactory$());
        List<ProductDetails.Option> list2 = Util.mapItems(product.getOptions(), Converters$$Lambda$3.lambdaFactory$());
        List<ProductDetails.Variant> list3 = Util.mapItems(product.getVariants().getEdges(), Converters$$Lambda$4.lambdaFactory$());
        return new ProductDetails(product.getId().toString(), product.getTitle(), product.getDescriptionHtml(), product.getTags(), list, list2, list3);
    }

    static List<Product> convertToProducts(List<Storefront.ProductEdge> list) {
        return Util.mapItems(list, Converters$$Lambda$1.lambdaFactory$());
    }

    static Checkout.ShippingRate convertToShippingRate(Storefront.ShippingRate shippingRate) {
        return new Checkout.ShippingRate(shippingRate.getHandle(), shippingRate.getPrice(), shippingRate.getTitle());
    }

    /*
     * Enabled aggressive block sorting
     */
    static Checkout.ShippingRates convertToShippingRates(Storefront.AvailableShippingRates availableShippingRates) {
        if (availableShippingRates == null) {
            return new Checkout.ShippingRates(false, Collections.<Checkout.ShippingRate>emptyList());
        }
        List<Object> list = availableShippingRates.getShippingRates() != null ? availableShippingRates.getShippingRates() : Collections.emptyList();
        list = Util.mapItems(list, Converters$$Lambda$7.lambdaFactory$());
        return new Checkout.ShippingRates(availableShippingRates.getReady(), list);
    }

    static ShopSettings convertToShopSettings(Storefront.Shop shop) {
        return new ShopSettings(shop.getName(), new HashSet<CardNetworkType>(Util.mapItems(shop.getPaymentSettings().getAcceptedCardBrands(), Converters$$Lambda$8.lambdaFactory$())), shop.getPaymentSettings().getCountryCode().name());
    }

    static /* synthetic */ Checkout.LineItem lambda$convertToCheckout$11(Storefront.CheckoutLineItemEdge checkoutLineItemEdge) {
        return new Checkout.LineItem(checkoutLineItemEdge.getNode().getVariant().getId().toString(), checkoutLineItemEdge.getNode().getTitle(), checkoutLineItemEdge.getNode().getQuantity(), checkoutLineItemEdge.getNode().getVariant().getPrice());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static /* synthetic */ Collection lambda$convertToCollections$10(Storefront.CollectionEdge collectionEdge) {
        String string2;
        Storefront.Collection collection = collectionEdge.getNode();
        if (collection.getImage() != null) {
            string2 = collection.getImage().getSrc();
            do {
                return new Collection(collection.getId().toString(), collection.getTitle(), collection.getDescription(), string2, collectionEdge.getCursor(), Util.mapItems(collection.getProducts().getEdges(), Converters$$Lambda$9.lambdaFactory$()));
                break;
            } while (true);
        }
        string2 = null;
        return new Collection(collection.getId().toString(), collection.getTitle(), collection.getDescription(), string2, collectionEdge.getCursor(), Util.mapItems(collection.getProducts().getEdges(), Converters$$Lambda$9.lambdaFactory$()));
    }

    static /* synthetic */ String lambda$convertToProductDetails$3(Storefront.ImageEdge imageEdge) {
        return imageEdge.getNode().getSrc();
    }

    static /* synthetic */ ProductDetails.Option lambda$convertToProductDetails$4(Storefront.ProductOption productOption) {
        return new ProductDetails.Option(productOption.getId().toString(), productOption.getName(), productOption.getValues());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static /* synthetic */ ProductDetails.Variant lambda$convertToProductDetails$6(Storefront.ProductVariantEdge productVariantEdge) {
        boolean bl;
        List<ProductDetails.SelectedOption> list = Util.mapItems(productVariantEdge.getNode().getSelectedOptions(), Converters$$Lambda$13.lambdaFactory$());
        String string2 = productVariantEdge.getNode().getId().toString();
        String string3 = productVariantEdge.getNode().getTitle();
        if (productVariantEdge.getNode().getAvailableForSale() == null || Boolean.TRUE.equals(productVariantEdge.getNode().getAvailableForSale())) {
            bl = true;
            do {
                return new ProductDetails.Variant(string2, string3, bl, list, productVariantEdge.getNode().getPrice(), productVariantEdge.getNode().getCompareAtPrice());
                break;
            } while (true);
        }
        bl = false;
        return new ProductDetails.Variant(string2, string3, bl, list, productVariantEdge.getNode().getPrice(), productVariantEdge.getNode().getCompareAtPrice());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ Product lambda$convertToProducts$2(Storefront.ProductEdge productEdge) {
        Storefront.Product product = productEdge.getNode();
        List<Storefront.ImageEdge> list = product.getImages() != null ? product.getImages().getEdges() : null;
        String string2 = (String)Util.firstItem(list, Converters$$Lambda$14.lambdaFactory$());
        BigDecimal bigDecimal = Util.minItem(Util.mapItems(product.getVariants().getEdges(), Converters$$Lambda$15.lambdaFactory$()), BigDecimal.ZERO, Converters$$Lambda$16.lambdaFactory$());
        String string3 = "Unknown";
        BigDecimal bigDecimal2 = null;
        Object object = string3;
        BigDecimal bigDecimal3 = bigDecimal2;
        list = string3;
        try {
            if (TextUtils.isEmpty((CharSequence)product.getVariants().getEdges().get(0).getNode().getSku())) return new Product(product.getId().toString(), product.getTitle(), string2, bigDecimal, productEdge.getCursor(), (String)object, bigDecimal3);
            list = string3;
            list = object = product.getVariants().getEdges().get(0).getNode().getSku();
            bigDecimal3 = product.getVariants().getEdges().get(0).getNode().getCompareAtPrice();
            return new Product(product.getId().toString(), product.getTitle(), string2, bigDecimal, productEdge.getCursor(), (String)object, bigDecimal3);
        }
        catch (Exception exception) {
            object = list;
            bigDecimal3 = bigDecimal2;
            return new Product(product.getId().toString(), product.getTitle(), string2, bigDecimal, productEdge.getCursor(), (String)object, bigDecimal3);
        }
    }

    static /* synthetic */ Checkout.ShippingRate lambda$convertToShippingRates$12(Storefront.ShippingRate shippingRate) {
        return new Checkout.ShippingRate(shippingRate.getHandle(), shippingRate.getPrice(), shippingRate.getTitle());
    }

    static /* synthetic */ CardNetworkType lambda$convertToShopSettings$13(Storefront.CardBrand cardBrand) {
        return CardNetworkType.findByName(cardBrand.name());
    }

    static /* synthetic */ String lambda$null$0(Storefront.ImageEdge imageEdge) {
        if (imageEdge != null) {
            return imageEdge.getNode().getSrc();
        }
        return null;
    }

    static /* synthetic */ BigDecimal lambda$null$1(Storefront.ProductVariantEdge productVariantEdge) {
        return productVariantEdge.getNode().getPrice();
    }

    static /* synthetic */ ProductDetails.SelectedOption lambda$null$5(Storefront.SelectedOption selectedOption) {
        return new ProductDetails.SelectedOption(selectedOption.getName(), selectedOption.getValue());
    }

    static /* synthetic */ String lambda$null$7(Storefront.ImageEdge imageEdge) {
        if (imageEdge != null) {
            return imageEdge.getNode().getSrc();
        }
        return null;
    }

    static /* synthetic */ BigDecimal lambda$null$8(Storefront.ProductVariantEdge productVariantEdge) {
        return productVariantEdge.getNode().getPrice();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ Product lambda$null$9(Storefront.ProductEdge productEdge) {
        Storefront.Product product = productEdge.getNode();
        List<Storefront.ImageEdge> list = product.getImages() != null ? product.getImages().getEdges() : null;
        String string2 = (String)Util.firstItem(list, Converters$$Lambda$10.lambdaFactory$());
        List list2 = Util.mapItems(product.getVariants().getEdges(), Converters$$Lambda$11.lambdaFactory$());
        String string3 = "Unknown";
        BigDecimal bigDecimal = null;
        Object object = string3;
        BigDecimal bigDecimal2 = bigDecimal;
        list = string3;
        try {
            if (!TextUtils.isEmpty((CharSequence)product.getVariants().getEdges().get(0).getNode().getSku())) {
                list = string3;
                list = object = product.getVariants().getEdges().get(0).getNode().getSku();
                bigDecimal2 = product.getVariants().getEdges().get(0).getNode().getCompareAtPrice();
            }
        }
        catch (Exception exception) {
            object = list;
            bigDecimal2 = bigDecimal;
        }
        list = Util.minItem(list2, BigDecimal.ZERO, Converters$$Lambda$12.lambdaFactory$());
        return new Product(product.getId().toString(), product.getTitle(), string2, (BigDecimal)((Object)list), productEdge.getCursor(), (String)object, bigDecimal2);
    }
}

