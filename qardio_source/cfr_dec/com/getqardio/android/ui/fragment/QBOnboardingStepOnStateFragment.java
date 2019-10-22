/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingStateFragment;
import com.getqardio.android.ui.fragment.OnBoardingDeviceAddressProvider;
import com.getqardio.android.ui.fragment.QBOnboardingStepOnStateFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBOnboardingStepOnStateFragment$$Lambda$2;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class QBOnboardingStepOnStateFragment
extends AbstractQBOnboardingStateFragment {
    private BroadcastReceiver baseReceiver;
    private LocalBroadcastManager broadcastManager;
    private int connectionRetries = 0;
    private Handler handler = new Handler();
    private QardioBaseManager qardioBaseManager;
    private boolean tryingReconnect = false;

    public QBOnboardingStepOnStateFragment() {
        this.baseReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                object = intent.getAction();
                Timber.d("onReceive action - %s", object);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 314987133: {
                        if (!((String)object).equals("com.qardio.base.TIMEOUT")) break;
                        n = 0;
                        break;
                    }
                    case 1424235813: {
                        if (!((String)object).equals("com.qardio.base.CONNECTED")) break;
                        n = 1;
                        break;
                    }
                    case 421757567: {
                        if (!((String)object).equals("com.qardio.base.DISCONNECTED")) break;
                        n = 2;
                        break;
                    }
                    case -598500176: {
                        if (!((String)object).equals("com.qardio.base.CONFIGURATION_MODE")) break;
                        n = 3;
                        break;
                    }
                    case 814720241: {
                        if (!((String)object).equals("com.qardio.base.QB_CONNECTION_ERROR")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 0: {
                        QBOnboardingStepOnStateFragment.this.handleTimeout();
                        return;
                    }
                    case 1: {
                        QBOnboardingStepOnStateFragment.this.title.setText((CharSequence)QBOnboardingStepOnStateFragment.this.getString(2131362331));
                        ((ProgressBar)QBOnboardingStepOnStateFragment.this.rootView.findViewById(2131821254)).setVisibility(0);
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        QBOnboardingStepOnStateFragment.this.handleConnection((String)object);
                        return;
                    }
                    case 2: {
                        QBOnboardingStepOnStateFragment.this.handleDisconnect();
                        return;
                    }
                    case 3: {
                        Timber.d(String.format("Time: %d - Base in configuration mode", System.currentTimeMillis()), new Object[0]);
                        QBOnboardingStepOnStateFragment.this.qardioBaseManager.enableEngineeringNotifications();
                        QBOnboardingStepOnStateFragment.this.handleConfiguration();
                        return;
                    }
                    case 4: 
                }
                QBOnboardingStepOnStateFragment.this.handleConnectionError();
            }
        };
    }

    private void handleConfiguration() {
        this.onDoneClickListener.onDoneClick();
    }

    private void handleConnection(String string2) {
        Timber.d("handleConnection address - %s", string2);
        this.qardioBaseManager.enableStateNotifications();
        new Handler().postDelayed(QBOnboardingStepOnStateFragment$$Lambda$2.lambdaFactory$(this), 1000L);
        ((OnBoardingDeviceAddressProvider)this.getActivity()).setDeviceAddress(string2);
    }

    private void handleConnectionError() {
        this.tryingReconnect = true;
        ++this.connectionRetries;
        if (this.connectionRetries < 50) {
            this.qardioBaseManager.disconnect();
            this.handler.postDelayed(QBOnboardingStepOnStateFragment$$Lambda$1.lambdaFactory$(this), TimeUnit.MILLISECONDS.toMillis(200L));
            return;
        }
        this.tryingReconnect = false;
        this.getActivity().finish();
    }

    private void handleDisconnect() {
        if (this.getActivity() != null && !this.tryingReconnect) {
            this.getActivity().finish();
        }
    }

    private void handleTimeout() {
        this.getActivity().finish();
    }

    public static QBOnboardingStepOnStateFragment newInstance(int n, int n2, boolean bl) {
        QBOnboardingStepOnStateFragment qBOnboardingStepOnStateFragment = new QBOnboardingStepOnStateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.argument.TITLE_RES_ID", n);
        bundle.putInt("com.getqardio.android.argument.MESSAGE_RES_ID", n2);
        bundle.putBoolean("com.getqardio.android.argument.SHOW_PROGRESS", bl);
        qBOnboardingStepOnStateFragment.setArguments(bundle);
        return qBOnboardingStepOnStateFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837945;
    }

    /* synthetic */ void lambda$handleConnection$1() {
        this.qardioBaseManager.enableConfigurationMode();
    }

    /* synthetic */ void lambda$handleConnectionError$0() {
        this.qardioBaseManager.scanAndConnect();
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        QardioBaseDeviceAnalyticsTracker.trackStepOnScreen((Context)this.getActivity());
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
        object = ActivityUtils.getActionBar(this.getActivity());
        if (object != null) {
            ((ActionBar)object).setTitle(2131362337);
        }
    }

    public void onPause() {
        super.onPause();
        this.broadcastManager.unregisterReceiver(this.baseReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.CONNECTED");
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        intentFilter.addAction("com.qardio.base.CONFIGURATION_MODE");
        intentFilter.addAction("com.qardio.base.QB_CONNECTION_ERROR");
        this.broadcastManager.registerReceiver(this.baseReceiver, intentFilter);
        Timber.d("scanAndConnect", new Object[0]);
        this.qardioBaseManager.scanAndConnect();
    }

    public void onStart() {
        super.onStart();
        this.qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
    }

    public void onStop() {
        super.onStop();
        this.connectionRetries = 0;
    }

    @Override
    protected int predictNextImage() {
        return 2130837940;
    }

}

