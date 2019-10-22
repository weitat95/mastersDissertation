/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.shopify.view.qardio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.shopify.view.collections.CollectionListActivity;
import com.getqardio.android.shopify.view.qardio.NoConnectivtyFragment;
import com.getqardio.android.ui.activity.BaseActivity;

public class ShopNotAvailableActivity
extends BaseActivity
implements NoConnectivtyFragment.OnConnectivityChangeListener {
    @Override
    public void onConnectivityAvailable() {
        this.startActivity(new Intent((Context)this, CollectionListActivity.class));
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968612);
    }
}

