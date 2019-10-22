/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.googlefit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.googlefit.GoogleFitOnboardActivity$$Lambda$1;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleFitOnboardActivity
extends BaseActivity
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;

    public void displayStartScreen() {
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (l != null) {
            this.startActivity(ChooseDeviceUtils.getMainIntent((Context)this, l));
            ((MvpApplication)this.getApplicationContext()).getSyncHelper().syncAll(this.getApplicationContext(), l);
        }
        this.finish();
    }

    /* synthetic */ void lambda$onCreate$0(View view) {
        this.googleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 1001 && n2 == -1) {
            this.googleApiClient.reconnect();
        }
    }

    @Override
    public void onConnected(Bundle object) {
        object = new Settings();
        object.userId = CustomApplication.getApplication().getCurrentUserId();
        object.allowIntegrationGoogleFit = true;
        DataHelper.SettingsHelper.saveSettings((Context)CustomApplication.getApplication(), (Settings)object, false);
        PreferenceManager.getDefaultSharedPreferences((Context)this).edit().putBoolean("google_fit_activity_tracker", true).apply();
        this.displayStartScreen();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.getErrorCode() == 4) {
            if (!connectionResult.hasResolution()) return;
            try {
                connectionResult.startResolutionForResult(this, 1001);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                sendIntentException.printStackTrace();
                return;
            }
        }
        PreferenceManager.getDefaultSharedPreferences((Context)this).edit().putBoolean("google_fit_activity_tracker", false).apply();
        this.displayStartScreen();
    }

    @Override
    public void onConnectionSuspended(int n) {
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968678);
        this.findViewById(2131821015).setVisibility(4);
        this.googleApiClient = GoogleFitUtils.buildOnBoardingClient(this, this);
        this.googleApiClient.registerConnectionCallbacks(this);
        this.googleApiClient.registerConnectionFailedListener(this);
        this.findViewById(2131821016).setOnClickListener(GoogleFitOnboardActivity$$Lambda$1.lambdaFactory$(this));
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131886097, menu2);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.googleApiClient != null && this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this.displayStartScreen();
        return true;
    }
}

