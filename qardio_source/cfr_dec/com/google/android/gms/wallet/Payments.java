/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wallet;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;

public interface Payments {
    public void changeMaskedWallet(GoogleApiClient var1, String var2, String var3, int var4);

    public PendingResult<BooleanResult> isReadyToPay(GoogleApiClient var1, IsReadyToPayRequest var2);

    public void loadFullWallet(GoogleApiClient var1, FullWalletRequest var2, int var3);

    public void loadMaskedWallet(GoogleApiClient var1, MaskedWalletRequest var2, int var3);
}

