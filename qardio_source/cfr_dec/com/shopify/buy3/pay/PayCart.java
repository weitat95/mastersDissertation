/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.shopify.buy3.pay;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.shopify.buy3.pay.Util;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class PayCart
implements Parcelable {
    public static final Parcelable.Creator<PayCart> CREATOR = new Parcelable.Creator<PayCart>(){

        public PayCart createFromParcel(Parcel parcel) {
            return new PayCart(parcel);
        }

        public PayCart[] newArray(int n) {
            return new PayCart[n];
        }
    };
    public final String countryCode;
    public final String currencyCode;
    public final List<LineItem> lineItems;
    public final String merchantName;
    public final boolean phoneNumberRequired;
    public final boolean shippingAddressRequired;
    public final BigDecimal shippingPrice;
    public final List<String> shipsToCountries;
    public final BigDecimal subtotal;
    public final BigDecimal taxPrice;
    public final BigDecimal totalPrice;

    /*
     * Enabled aggressive block sorting
     */
    protected PayCart(Parcel parcel) {
        boolean bl = false;
        this.currencyCode = parcel.readString();
        this.countryCode = parcel.readString();
        this.merchantName = parcel.readString();
        this.shipsToCountries = Collections.unmodifiableList(parcel.createStringArrayList());
        boolean bl2 = parcel.readByte() != 0;
        this.shippingAddressRequired = bl2;
        bl2 = bl;
        if (parcel.readByte() != 0) {
            bl2 = true;
        }
        this.phoneNumberRequired = bl2;
        this.lineItems = Collections.unmodifiableList(parcel.createTypedArrayList(LineItem.CREATOR));
        this.subtotal = BigDecimal.valueOf(parcel.readDouble()).setScale(2, RoundingMode.HALF_EVEN);
        this.taxPrice = parcel.readByte() == 1 ? BigDecimal.valueOf(parcel.readDouble()).setScale(2, RoundingMode.HALF_EVEN) : null;
        this.shippingPrice = parcel.readByte() == 1 ? BigDecimal.valueOf(parcel.readDouble()).setScale(2, RoundingMode.HALF_EVEN) : null;
        this.totalPrice = BigDecimal.valueOf(parcel.readDouble()).setScale(2, RoundingMode.HALF_EVEN);
    }

    /*
     * Enabled aggressive block sorting
     */
    PayCart(String object, String string2, String string3, List<String> list, boolean bl, boolean bl2, List<LineItem> list2, BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3, BigDecimal bigDecimal4) {
        Object var12_12 = null;
        this.currencyCode = Util.checkNotEmpty((String)object, "currencyCode can't be empty");
        this.countryCode = Util.checkNotEmpty(string2, "countryCode can't be empty");
        this.merchantName = Util.checkNotEmpty(string3, "merchantName can't be empty");
        this.shipsToCountries = Collections.unmodifiableList(list);
        this.shippingAddressRequired = bl;
        this.phoneNumberRequired = bl2;
        this.lineItems = list2;
        this.subtotal = bigDecimal.setScale(2, RoundingMode.HALF_EVEN);
        object = bigDecimal2 != null ? bigDecimal2.setScale(2, RoundingMode.HALF_EVEN) : null;
        this.taxPrice = object;
        object = var12_12;
        if (bigDecimal3 != null) {
            object = bigDecimal3.setScale(2, RoundingMode.HALF_EVEN);
        }
        this.shippingPrice = object;
        this.totalPrice = bigDecimal4.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static Collection<CountrySpecification> shippingCountrySpecifications(Collection<String> collection) {
        Object object = collection;
        if (collection.contains("*")) {
            object = Arrays.asList(Locale.getISOCountries());
        }
        collection = new ArrayList<String>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            collection.add((String)((Object)new CountrySpecification((String)object.next())));
        }
        return collection;
    }

    Cart.Builder cartBuilder() {
        Cart.Builder builder = Cart.newBuilder().setCurrencyCode(this.currencyCode).setTotalPrice(this.totalPrice.toString()).setLineItems(this.lineItems);
        if (this.taxPrice != null) {
            builder.addLineItem(LineItem.newBuilder().setCurrencyCode(this.currencyCode).setDescription("Tax").setRole(1).setTotalPrice(this.taxPrice.toString()).build());
        }
        if (this.shippingPrice != null) {
            builder.addLineItem(LineItem.newBuilder().setCurrencyCode(this.currencyCode).setDescription("Shipping").setRole(2).setTotalPrice(this.shippingPrice.toString()).build());
        }
        return builder;
    }

    public int describeContents() {
        return 0;
    }

    public FullWalletRequest fullWalletRequest(MaskedWallet maskedWallet) {
        Util.checkNotNull(maskedWallet, "maskedWallet can't be empty");
        return FullWalletRequest.newBuilder().setGoogleTransactionId(maskedWallet.getGoogleTransactionId()).setCart(this.cartBuilder().build()).build();
    }

    public MaskedWalletRequest maskedWalletRequest(String object) {
        object = PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(2).addParameter("publicKey", Util.checkNotEmpty((String)object, "androidPayPublicKey can't be empty")).build();
        object = MaskedWalletRequest.newBuilder().setMerchantName(this.merchantName).setPhoneNumberRequired(this.phoneNumberRequired).setShippingAddressRequired(this.shippingAddressRequired).setCurrencyCode(this.currencyCode).setCountryCode(this.countryCode).setEstimatedTotalPrice(this.totalPrice.toString()).setPaymentMethodTokenizationParameters((PaymentMethodTokenizationParameters)object).setCart(this.cartBuilder().build());
        ((MaskedWalletRequest.Builder)object).addAllowedCountrySpecificationsForShipping(PayCart.shippingCountrySpecifications(this.shipsToCountries));
        return ((MaskedWalletRequest.Builder)object).build();
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.currencyCode = this.currencyCode;
        builder.countryCode = this.countryCode;
        builder.merchantName = this.merchantName;
        builder.shipsToCountries = this.shipsToCountries;
        builder.shippingAddressRequired = this.shippingAddressRequired;
        builder.phoneNumberRequired = this.phoneNumberRequired;
        builder.lineItems = new ArrayList<LineItem>(this.lineItems);
        builder.subtotal = this.subtotal;
        builder.taxPrice = this.taxPrice;
        builder.shippingPrice = this.shippingPrice;
        builder.totalPrice = this.totalPrice;
        return builder;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        int n2 = 1;
        parcel.writeString(this.currencyCode);
        parcel.writeString(this.countryCode);
        parcel.writeString(this.merchantName);
        parcel.writeStringList(this.shipsToCountries);
        n = this.shippingAddressRequired ? 1 : 0;
        parcel.writeByte((byte)n);
        n = this.phoneNumberRequired ? 1 : 0;
        parcel.writeByte((byte)n);
        parcel.writeTypedList(this.lineItems);
        parcel.writeDouble(this.subtotal.doubleValue());
        n = this.taxPrice != null ? 1 : 0;
        parcel.writeByte((byte)n);
        if (this.taxPrice != null) {
            parcel.writeDouble(this.taxPrice.doubleValue());
        }
        n = this.shippingPrice != null ? n2 : 0;
        parcel.writeByte((byte)n);
        if (this.shippingPrice != null) {
            parcel.writeDouble(this.shippingPrice.doubleValue());
        }
        parcel.writeDouble(this.totalPrice.doubleValue());
    }

    public static final class Builder {
        String countryCode;
        String currencyCode;
        BigDecimal lineItemSubtotal;
        List<LineItem> lineItems;
        String merchantName;
        boolean phoneNumberRequired;
        boolean shippingAddressRequired;
        BigDecimal shippingPrice;
        List<String> shipsToCountries = Collections.singletonList("*");
        BigDecimal subtotal;
        BigDecimal taxPrice;
        BigDecimal totalPrice;

        Builder() {
            this.lineItems = new ArrayList<LineItem>();
            this.lineItemSubtotal = BigDecimal.ZERO;
        }

        public Builder addLineItem(String object, int n, BigDecimal bigDecimal) {
            Util.checkNotEmpty((String)object, "title can't be empty");
            if (n <= 0) {
                throw new IllegalArgumentException("quantity can't be less than 1");
            }
            Util.checkNotNull(bigDecimal, "price == null");
            object = LineItem.newBuilder().setQuantity(Integer.toString(n)).setUnitPrice(bigDecimal.toString()).setTotalPrice(bigDecimal.multiply(BigDecimal.valueOf(n)).toString()).setDescription((String)object).setCurrencyCode(this.currencyCode).setRole(0).build();
            this.lineItems.add((LineItem)object);
            this.lineItemSubtotal = this.lineItemSubtotal.add(bigDecimal.multiply(BigDecimal.valueOf(n)));
            return this;
        }

        public PayCart build() {
            BigDecimal bigDecimal;
            BigDecimal bigDecimal2 = bigDecimal = this.subtotal;
            if (bigDecimal == null) {
                bigDecimal2 = this.lineItemSubtotal;
            }
            BigDecimal bigDecimal3 = bigDecimal = this.totalPrice;
            if (bigDecimal == null) {
                bigDecimal = bigDecimal3 = bigDecimal2;
                if (this.shippingPrice != null) {
                    bigDecimal = bigDecimal3.add(this.shippingPrice);
                }
                bigDecimal3 = bigDecimal;
                if (this.taxPrice != null) {
                    bigDecimal3 = bigDecimal.add(this.taxPrice);
                }
            }
            return new PayCart(this.currencyCode, this.countryCode, this.merchantName, this.shipsToCountries, this.shippingAddressRequired, this.phoneNumberRequired, this.lineItems, bigDecimal2, this.taxPrice, this.shippingPrice, bigDecimal3);
        }

        public Builder countryCode(String string2) {
            this.countryCode = Util.checkNotEmpty(string2, "countryCode can't be empty");
            return this;
        }

        public Builder currencyCode(String string2) {
            this.currencyCode = Util.checkNotEmpty(string2, "currencyCode can't be empty");
            return this;
        }

        public Builder merchantName(String string2) {
            this.merchantName = Util.checkNotEmpty(string2, "merchantName can't be empty");
            return this;
        }

        public Builder phoneNumberRequired(boolean bl) {
            this.phoneNumberRequired = bl;
            return this;
        }

        public Builder shippingAddressRequired(boolean bl) {
            this.shippingAddressRequired = bl;
            return this;
        }

        public Builder shippingPrice(BigDecimal bigDecimal) {
            this.shippingPrice = bigDecimal;
            return this;
        }

        public Builder subtotal(BigDecimal bigDecimal) {
            this.subtotal = Util.checkNotNull(bigDecimal, "subtotal == null");
            return this;
        }

        public Builder taxPrice(BigDecimal bigDecimal) {
            this.taxPrice = Util.checkNotNull(bigDecimal, "taxPrice == null");
            return this;
        }

        public Builder totalPrice(BigDecimal bigDecimal) {
            this.totalPrice = Util.checkNotNull(bigDecimal, "totalPrice == null");
            return this;
        }
    }

}

