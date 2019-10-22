/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Base64
 */
package com.shopify.buy3.pay;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.shopify.buy3.pay.CardNetworkType;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PayHelper$$Lambda$1;
import com.shopify.buy3.pay.PaymentToken;
import com.shopify.buy3.pay.Util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class PayHelper {
    public static final Map<CardNetworkType, Integer> SUPPORTED_CARD_NETWORKS_MAP;

    static {
        HashMap<CardNetworkType, Integer> hashMap = new HashMap<CardNetworkType, Integer>(5);
        hashMap.put(CardNetworkType.VISA, 5);
        hashMap.put(CardNetworkType.MASTERCARD, 4);
        hashMap.put(CardNetworkType.DISCOVER, 2);
        hashMap.put(CardNetworkType.AMERICAN_EXPRESS, 1);
        hashMap.put(CardNetworkType.JCB, 3);
        SUPPORTED_CARD_NETWORKS_MAP = Collections.unmodifiableMap(hashMap);
    }

    static Set<Integer> convertSupportedCardNetworks(Set<CardNetworkType> object) {
        Util.checkNotNull(object, "supportedCardNetworks can't be null");
        LinkedHashSet<CardNetworkType> linkedHashSet = new LinkedHashSet<CardNetworkType>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = (CardNetworkType)((Object)object.next());
            if ((object2 = SUPPORTED_CARD_NETWORKS_MAP.get(object2)) == null) continue;
            linkedHashSet.add((CardNetworkType)((Object)object2));
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PaymentToken extractPaymentToken(FullWallet object, String arrby) {
        Util.checkNotNull(object, "fullWallet can't be null");
        Util.checkNotEmpty((String)arrby, "androidPayPublicKey can't be empty");
        try {
            arrby = MessageDigest.getInstance("SHA-256").digest(arrby.getBytes("UTF-8"));
            return new PaymentToken(((FullWallet)object).getPaymentMethodToken().getToken(), Base64.encodeToString((byte[])arrby, (int)0));
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static boolean handleWalletResponse(int n, int n2, Intent object, WalletResponseHandler walletResponseHandler) {
        FullWallet fullWallet;
        void var3_4;
        block8: {
            block7: {
                if (n != 501 && n != 500 && n != 502) break block7;
                if (n2 != -1) {
                    var3_4.onWalletRequestCancel(n);
                    return true;
                }
                if (object == null) {
                    var3_4.onWalletError(n, -1);
                    return true;
                }
                n2 = object.getIntExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", -1);
                if (n2 != -1) {
                    var3_4.onWalletError(n, n2);
                    return true;
                }
                MaskedWallet maskedWallet = (MaskedWallet)object.getParcelableExtra("com.google.android.gms.wallet.EXTRA_MASKED_WALLET");
                fullWallet = (FullWallet)object.getParcelableExtra("com.google.android.gms.wallet.EXTRA_FULL_WALLET");
                if (maskedWallet != null) {
                    var3_4.onMaskedWallet(maskedWallet);
                    return true;
                }
                if (fullWallet != null) break block8;
            }
            return false;
        }
        var3_4.onFullWallet(fullWallet);
        return true;
    }

    public static void initializeWalletFragment(SupportWalletFragment supportWalletFragment, MaskedWallet maskedWallet) {
        supportWalletFragment.initialize(WalletFragmentInitParams.newBuilder().setMaskedWallet(maskedWallet).setMaskedWalletRequestCode(501).build());
    }

    public static boolean isAndroidPayEnabledInManifest(Context context) {
        try {
            boolean bl = context.getPackageManager().getApplicationInfo((String)context.getPackageName(), (int)128).metaData.getBoolean("com.google.android.gms.wallet.api.enabled");
            return bl;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void isReadyToPay(Context var0, GoogleApiClient var1_3, Set<CardNetworkType> var2_4, AndroidPayReadyCallback var3_5) {
        block7: {
            Util.checkNotNull(var1_3, "apiClient can't be null");
            Util.checkNotNull(var3_5, "delegate can't be null");
            Util.checkNotNull(var2_4, "supportedCardNetworks can't be null");
            var4_6 = PayHelper.convertSupportedCardNetworks((Set<CardNetworkType>)var2_4);
            var2_4 = var4_6;
            if (var4_6.isEmpty()) {
                var2_4 = new HashSet<Integer>(PayHelper.SUPPORTED_CARD_NETWORKS_MAP.values());
            }
            try {
                MessageDigest.getInstance("SHA-256");
                "foo".getBytes("UTF-8");
                if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable((Context)var0) != 0) {
                    var3_5.onResult(false);
                    return;
                }
                break block7;
            }
            catch (NoSuchAlgorithmException var0_1) {}
            ** GOTO lbl-1000
        }
        var0 = IsReadyToPayRequest.newBuilder();
        var2_4 = var2_4.iterator();
        do {
            if (!var2_4.hasNext()) {
                Wallet.Payments.isReadyToPay(var1_3, var0.build()).setResultCallback(PayHelper$$Lambda$1.lambdaFactory$(var3_5));
                return;
            }
            var0.addAllowedCardNetwork((Integer)var2_4.next());
        } while (true);
        catch (UnsupportedEncodingException var0_2) {}
lbl-1000:
        // 2 sources
        {
            var3_5.onResult(false);
            return;
        }
    }

    static /* synthetic */ void lambda$isReadyToPay$0(AndroidPayReadyCallback androidPayReadyCallback, BooleanResult booleanResult) {
        if (booleanResult.getStatus().isSuccess()) {
            androidPayReadyCallback.onResult(booleanResult.getValue());
            return;
        }
        androidPayReadyCallback.onResult(false);
    }

    public static void newMaskedWallet(GoogleApiClient googleApiClient, MaskedWallet maskedWallet) {
        Wallet.Payments.changeMaskedWallet(googleApiClient, maskedWallet.getGoogleTransactionId(), null, 500);
    }

    public static void requestFullWallet(GoogleApiClient googleApiClient, PayCart object, MaskedWallet maskedWallet) {
        object = object.fullWalletRequest(maskedWallet);
        Wallet.Payments.loadFullWallet(googleApiClient, (FullWalletRequest)object, 502);
    }

    public static void requestMaskedWallet(GoogleApiClient googleApiClient, PayCart object, String string2) {
        object = object.maskedWalletRequest(string2);
        Wallet.Payments.loadMaskedWallet(googleApiClient, (MaskedWalletRequest)object, 500);
    }

    public static interface AndroidPayReadyCallback {
        public void onResult(boolean var1);
    }

    public static abstract class WalletResponseHandler {
        public void onFullWallet(FullWallet fullWallet) {
        }

        public void onMaskedWallet(MaskedWallet maskedWallet) {
        }

        public abstract void onWalletError(int var1, int var2);

        public void onWalletRequestCancel(int n) {
        }
    }

}

