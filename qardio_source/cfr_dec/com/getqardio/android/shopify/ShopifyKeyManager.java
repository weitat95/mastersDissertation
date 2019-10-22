/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.shopify;

import android.text.TextUtils;
import java.util.Locale;

public class ShopifyKeyManager {
    private static final String API_KEY_AU = "267c27f7a117a51bf0967f46aaca0b42";
    private static final String API_KEY_CA = "efe530260fe8e4715c4fafc18095e389";
    private static final String API_KEY_EU = "a3edbf09fdbcd6a77b615866705fe2ac";
    private static final String API_KEY_HK = "3334146e5fab3b8b4a4c819fe65e44fd";
    private static final String API_KEY_UK = "6260006d67d3fe15a50b163401bd5fef";
    private static final String API_KEY_US = "ef7e69aaeedbee2c80960f2e699a1bfe";
    private static final String DOMAIN_AU = "qardio-au.myshopify.com";
    private static final String DOMAIN_CA = "qardio-ca.myshopify.com";
    private static final String DOMAIN_EU = "qardio-eu.myshopify.com";
    private static final String DOMAIN_HK = "qardio-hk.myshopify.com";
    private static final String DOMAIN_UK = "qardio-uk.myshopify.com";
    private static final String DOMAIN_US = "qardio-2.myshopify.com";
    private static final String LOCALE_AU = "AU";
    private static final String LOCALE_CA = "CA";
    private static final String LOCALE_HK = "HK";
    private static final String LOCALE_UK = "GB";
    private static final String LOCALE_US = "US";
    private static final String PUBLIC_KEY_AU = "BCmokULBBOlxflID7iDuuDcd/9FLdT50sfoU6fNpRlpNsn/NHgFgcnzLq8GNPzBs5euMlqJ2RW1/s+A8NtkzZXM=";
    private static final String PUBLIC_KEY_CA = "BFMztPCJ9Lbhni9DKFAvSFejHcp6GdGfmNTyFoDHX2EwoYNdoCop4BkzxCN1GWzF5dyglY8Ws/+OPVAaHJs0Eio=";
    private static final String PUBLIC_KEY_EU = "BEvjo4U3K4ITSATKyWTFhKHvSbFzt3uK8sCnacQsWbS31oK8OWePVA6C7h66/2vSHQRW44uiL9oOWb5oOfm2hYw=";
    private static final String PUBLIC_KEY_HK = "BJX8QHoqmld7IWDSG60Zaq9kjZfPmEqlnZndHCLR4syeOPh1Ldn3WSvAD5mYurMmJoRUdyoHXuukEv5RakxdrnI=";
    private static final String PUBLIC_KEY_UK = "BB+GguC6TXPQLKeh6pELP+di5pq9ZkbhdZKsU6Jnxo4kXL8uhLPGv8nG38b/R3rz6NJfSW2T2sThBDKiXadmPig=";
    private static final String PUBLIC_KEY_US = "BBqLAgNG0f1vlR9i4tiOF9siUz7o0SbNOIdgMiWnqyamJnbm1RJ+L6Isg/NmVBRL5eq7qyX4nVU9g/DF+LBjm4U=";
    private static final String TAG = "Shopify";
    private static ShopifyKeyManager instance;
    private String country;

    private ShopifyKeyManager() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ShopifyKeyManager getInstance() {
        if (instance == null) {
            synchronized (ShopifyKeyManager.class) {
                if (instance == null) {
                    instance = new ShopifyKeyManager();
                }
            }
        }
        return instance;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Locale getLocale() {
        String string2 = this.getStoreFromCountry();
        int n = -1;
        switch (string2.hashCode()) {
            case 2718: {
                if (!string2.equals(LOCALE_US)) break;
                n = 0;
                break;
            }
            case 2267: {
                if (!string2.equals(LOCALE_UK)) break;
                n = 1;
                break;
            }
            case 2142: {
                if (!string2.equals(LOCALE_CA)) break;
                n = 2;
                break;
            }
            case 2307: {
                if (!string2.equals(LOCALE_HK)) break;
                n = 3;
                break;
            }
            case 2100: {
                if (!string2.equals(LOCALE_AU)) break;
                n = 4;
                break;
            }
        }
        switch (n) {
            default: {
                return new Locale("nl", "NL");
            }
            case 0: {
                return Locale.US;
            }
            case 1: {
                return Locale.UK;
            }
            case 2: {
                return Locale.CANADA;
            }
            case 3: {
                return new Locale("en", LOCALE_HK);
            }
            case 4: 
        }
        return new Locale("en", LOCALE_AU);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getStoreFromCountry() {
        if (TextUtils.isEmpty((CharSequence)this.country)) {
            return LOCALE_US;
        }
        String string2 = this.country;
        int n = -1;
        switch (string2.hashCode()) {
            case 2100: {
                if (!string2.equals(LOCALE_AU)) break;
                n = 0;
                break;
            }
            case 2142: {
                if (!string2.equals(LOCALE_CA)) break;
                n = 1;
                break;
            }
            case 2307: {
                if (!string2.equals(LOCALE_HK)) break;
                n = 2;
                break;
            }
            case 2099: {
                if (!string2.equals("AT")) break;
                n = 3;
                break;
            }
            case 2115: {
                if (!string2.equals("BE")) break;
                n = 4;
                break;
            }
            case 2117: {
                if (!string2.equals("BG")) break;
                n = 5;
                break;
            }
            case 2166: {
                if (!string2.equals("CY")) break;
                n = 6;
                break;
            }
            case 2167: {
                if (!string2.equals("CZ")) break;
                n = 7;
                break;
            }
            case 2183: {
                if (!string2.equals("DK")) break;
                n = 8;
                break;
            }
            case 2208: {
                if (!string2.equals("EE")) break;
                n = 9;
                break;
            }
            case 2243: {
                if (!string2.equals("FI")) break;
                n = 10;
                break;
            }
            case 2252: {
                if (!string2.equals("FR")) break;
                n = 11;
                break;
            }
            case 2177: {
                if (!string2.equals("DE")) break;
                n = 12;
                break;
            }
            case 2283: {
                if (!string2.equals("GR")) break;
                n = 13;
                break;
            }
            case 2317: {
                if (!string2.equals("HU")) break;
                n = 14;
                break;
            }
            case 2346: {
                if (!string2.equals("IS")) break;
                n = 15;
                break;
            }
            case 2332: {
                if (!string2.equals("IE")) break;
                n = 16;
                break;
            }
            case 2347: {
                if (!string2.equals("IT")) break;
                n = 17;
                break;
            }
            case 2442: {
                if (!string2.equals("LV")) break;
                n = 18;
                break;
            }
            case 2429: {
                if (!string2.equals("LI")) break;
                n = 19;
                break;
            }
            case 2440: {
                if (!string2.equals("LT")) break;
                n = 20;
                break;
            }
            case 2441: {
                if (!string2.equals("LU")) break;
                n = 21;
                break;
            }
            case 2471: {
                if (!string2.equals("MT")) break;
                n = 22;
                break;
            }
            case 2454: {
                if (!string2.equals("MC")) break;
                n = 23;
                break;
            }
            case 2494: {
                if (!string2.equals("NL")) break;
                n = 24;
                break;
            }
            case 2497: {
                if (!string2.equals("NO")) break;
                n = 25;
                break;
            }
            case 2556: {
                if (!string2.equals("PL")) break;
                n = 26;
                break;
            }
            case 2564: {
                if (!string2.equals("PT")) break;
                n = 27;
                break;
            }
            case 2621: {
                if (!string2.equals("RO")) break;
                n = 28;
                break;
            }
            case 2650: {
                if (!string2.equals("SM")) break;
                n = 29;
                break;
            }
            case 2648: {
                if (!string2.equals("SK")) break;
                n = 30;
                break;
            }
            case 2646: {
                if (!string2.equals("SI")) break;
                n = 31;
                break;
            }
            case 2222: {
                if (!string2.equals("ES")) break;
                n = 32;
                break;
            }
            case 2642: {
                if (!string2.equals("SE")) break;
                n = 33;
                break;
            }
            case 2149: {
                if (!string2.equals("CH")) break;
                n = 34;
                break;
            }
            case 2686: {
                if (!string2.equals("TR")) break;
                n = 35;
                break;
            }
            case 2272: {
                if (!string2.equals("GG")) break;
                n = 36;
                break;
            }
            case 2363: {
                if (!string2.equals("JE")) break;
                n = 37;
                break;
            }
            case 2340: {
                if (!string2.equals("IM")) break;
                n = 38;
                break;
            }
            case 2267: {
                if (!string2.equals(LOCALE_UK)) break;
                n = 39;
                break;
            }
            case 2718: {
                if (!string2.equals(LOCALE_US)) break;
                n = 40;
                break;
            }
        }
        switch (n) {
            default: {
                return LOCALE_US;
            }
            case 0: {
                return LOCALE_AU;
            }
            case 1: {
                return LOCALE_CA;
            }
            case 2: {
                return LOCALE_HK;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: {
                return "EU";
            }
            case 36: 
            case 37: 
            case 38: 
            case 39: 
        }
        return LOCALE_UK;
    }

    public int resolveAndroidPayEnvironment() {
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String resolveAndroidPayPublicKey() {
        String string2 = this.getStoreFromCountry();
        int n = -1;
        switch (string2.hashCode()) {
            case 2718: {
                if (!string2.equals(LOCALE_US)) break;
                n = 0;
                break;
            }
            case 2267: {
                if (!string2.equals(LOCALE_UK)) break;
                n = 1;
                break;
            }
            case 2142: {
                if (!string2.equals(LOCALE_CA)) break;
                n = 2;
                break;
            }
            case 2307: {
                if (!string2.equals(LOCALE_HK)) break;
                n = 3;
                break;
            }
            case 2100: {
                if (!string2.equals(LOCALE_AU)) break;
                n = 4;
                break;
            }
        }
        switch (n) {
            default: {
                return PUBLIC_KEY_EU;
            }
            case 0: {
                return PUBLIC_KEY_US;
            }
            case 1: {
                return PUBLIC_KEY_UK;
            }
            case 2: {
                return PUBLIC_KEY_CA;
            }
            case 3: {
                return PUBLIC_KEY_HK;
            }
            case 4: 
        }
        return PUBLIC_KEY_AU;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String resolveShopifyAPIKey() {
        String string2 = this.getStoreFromCountry();
        int n = -1;
        switch (string2.hashCode()) {
            case 2718: {
                if (!string2.equals(LOCALE_US)) break;
                n = 0;
                break;
            }
            case 2267: {
                if (!string2.equals(LOCALE_UK)) break;
                n = 1;
                break;
            }
            case 2142: {
                if (!string2.equals(LOCALE_CA)) break;
                n = 2;
                break;
            }
            case 2307: {
                if (!string2.equals(LOCALE_HK)) break;
                n = 3;
                break;
            }
            case 2100: {
                if (!string2.equals(LOCALE_AU)) break;
                n = 4;
                break;
            }
        }
        switch (n) {
            default: {
                return API_KEY_EU;
            }
            case 0: {
                return API_KEY_US;
            }
            case 1: {
                return API_KEY_UK;
            }
            case 2: {
                return API_KEY_CA;
            }
            case 3: {
                return API_KEY_HK;
            }
            case 4: 
        }
        return API_KEY_AU;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String resolveShopifyDomain() {
        String string2 = this.getStoreFromCountry();
        int n = -1;
        switch (string2.hashCode()) {
            case 2718: {
                if (!string2.equals(LOCALE_US)) break;
                n = 0;
                break;
            }
            case 2267: {
                if (!string2.equals(LOCALE_UK)) break;
                n = 1;
                break;
            }
            case 2142: {
                if (!string2.equals(LOCALE_CA)) break;
                n = 2;
                break;
            }
            case 2307: {
                if (!string2.equals(LOCALE_HK)) break;
                n = 3;
                break;
            }
            case 2100: {
                if (!string2.equals(LOCALE_AU)) break;
                n = 4;
                break;
            }
        }
        switch (n) {
            default: {
                return DOMAIN_EU;
            }
            case 0: {
                return DOMAIN_US;
            }
            case 1: {
                return DOMAIN_UK;
            }
            case 2: {
                return DOMAIN_CA;
            }
            case 3: {
                return DOMAIN_HK;
            }
            case 4: 
        }
        return DOMAIN_AU;
    }

    public void setCountry(String string2) {
        this.country = string2;
    }
}

