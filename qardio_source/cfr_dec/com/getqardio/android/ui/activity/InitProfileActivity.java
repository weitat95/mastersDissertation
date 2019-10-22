/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.googlefit.GoogleFitOnboardActivity;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.ui.fragment.InitProfileFragment;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class InitProfileActivity
extends BaseActivity
implements InitProfileFragment.InitProfileFragmentCallback,
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private boolean showGoogleFitOnboarding = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, InitProfileActivity.class);
    }

    public void displayGoogleFitOnboarding() {
        this.startActivity(new Intent((Context)this, GoogleFitOnboardActivity.class));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void displayStartScreen() {
        if (this.showGoogleFitOnboarding) {
            this.displayGoogleFitOnboarding();
        } else {
            Long l = CustomApplication.getApplication().getCurrentUserId();
            if (l != null) {
                this.startActivity(ChooseDeviceUtils.getMainIntent((Context)this, l));
                ((MvpApplication)this.getApplicationContext()).getSyncHelper().syncAll(this.getApplicationContext(), l);
            }
        }
        this.setResult(-1);
        this.finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onConnected(Bundle bundle) {
        this.showGoogleFitOnboarding = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.showGoogleFitOnboarding = true;
    }

    @Override
    public void onConnectionSuspended(int n) {
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968729);
        this.googleApiClient = GoogleFitUtils.buildOnBoardingClient(this, this);
        this.googleApiClient.registerConnectionCallbacks(this);
    }

    protected Dialog onCreateDialog(int n) {
        switch (n) {
            default: {
                return super.onCreateDialog(n);
            }
            case 1: 
        }
        return CustomAlertDialog.newInstance((Context)this, this.getText(2131362243).toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }
}

