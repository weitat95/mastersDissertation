/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.shopify.view.qardio;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.ActivityUtils;

public class NoConnectivtyFragment
extends Fragment {
    private boolean isWaitingForConnectivity;
    private OnConnectivityChangeListener listener;
    private BroadcastReceiver receiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (Utils.isNetworkAvaliable(context) && NoConnectivtyFragment.this.listener != null) {
                NoConnectivtyFragment.this.listener.onConnectivityAvailable();
            }
        }
    };

    public static NoConnectivtyFragment newInstance() {
        return new NoConnectivtyFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968607, viewGroup, false);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362486);
        this.listener = (OnConnectivityChangeListener)this.getActivity();
        return layoutInflater;
    }

    public void onDestroy() {
        super.onDestroy();
        this.listener = null;
    }

    public void onPause() {
        super.onPause();
        if (this.isWaitingForConnectivity) {
            this.getActivity().unregisterReceiver(this.receiver);
            this.isWaitingForConnectivity = false;
        }
    }

    public void onResume() {
        super.onResume();
        if (!Utils.isNetworkAvaliable((Context)this.getActivity())) {
            this.isWaitingForConnectivity = true;
            this.getActivity().registerReceiver(this.receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public static interface OnConnectivityChangeListener {
        public void onConnectivityAvailable();
    }

}

