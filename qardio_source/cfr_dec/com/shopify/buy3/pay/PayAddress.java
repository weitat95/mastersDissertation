/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.shopify.buy3.pay;

import android.text.TextUtils;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.shopify.buy3.pay.Util;

public final class PayAddress {
    public final String address1;
    public final String address2;
    public final String city;
    public final String country;
    public final String firstName;
    public final String lastName;
    public final String phone;
    public final String province;
    public final String zip;

    private PayAddress(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
        this.address1 = string2;
        this.address2 = string3;
        this.city = string4;
        this.country = string5;
        this.firstName = string6;
        this.lastName = string7;
        this.phone = string8;
        this.province = string9;
        this.zip = string10;
    }

    private static String[] extractFirstAndLastNames(String string2) {
        String[] arrstring = new String[2];
        String[] arrstring2 = string2.split(" ");
        if (arrstring2.length > 0) {
            arrstring[0] = arrstring2[0];
        }
        string2 = "";
        for (int i = 1; arrstring2.length > i; ++i) {
            string2 = string2 + arrstring2[i];
        }
        arrstring[1] = string2.trim();
        return arrstring;
    }

    public static PayAddress fromUserAddress(UserAddress userAddress) {
        Util.checkNotNull(userAddress, "userAddress == null");
        String[] arrstring = PayAddress.extractFirstAndLastNames(userAddress.getName());
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty((CharSequence)userAddress.getAddress2())) {
            stringBuilder.append(userAddress.getAddress2());
            stringBuilder.append(", ");
        }
        if (!TextUtils.isEmpty((CharSequence)userAddress.getAddress3())) {
            stringBuilder.append(userAddress.getAddress3());
            stringBuilder.append(", ");
        }
        if (!TextUtils.isEmpty((CharSequence)userAddress.getAddress4())) {
            stringBuilder.append(userAddress.getAddress4());
            stringBuilder.append(", ");
        }
        if (!TextUtils.isEmpty((CharSequence)userAddress.getAddress5())) {
            stringBuilder.append(userAddress.getAddress5());
        }
        return new PayAddress(userAddress.getAddress1(), stringBuilder.toString(), userAddress.getLocality(), userAddress.getCountryCode(), arrstring[0], arrstring[1], userAddress.getPhoneNumber(), userAddress.getAdministrativeArea(), userAddress.getPostalCode());
    }

    public String toString() {
        return "PayAddress{address1='" + this.address1 + '\'' + ", address2='" + this.address2 + '\'' + ", city='" + this.city + '\'' + ", country='" + this.country + '\'' + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", phone='" + this.phone + '\'' + ", province='" + this.province + '\'' + ", zip='" + this.zip + '\'' + '}';
    }
}

