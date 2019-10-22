/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.service.WearableCommunicationService;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.ui.activity.SignActivity;
import com.getqardio.android.ui.activity.SplashActivity$$Lambda$1;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;

public class SplashActivity
extends BaseActivity {
    private boolean isCanceled = false;
    private boolean needFinish = false;

    private Intent createMainActivityIntent(Context context) {
        Intent intent = this.getIntent();
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (l == null) {
            return MainActivity.getStartIntent((Context)this);
        }
        return this.createMainActivityIntent(context, intent, l);
    }

    private Intent createMainActivityIntent(Context context, Intent intent, Long l) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            intent.getAction();
            intent.getData();
            if (bundle.getBoolean("com.getqardio.android.extras.FROM_NOTIFICATION")) {
                return MainActivity.getStartIntent(context, true, bundle.getInt("com.getqardio.android.extras.NOTIFICATION_ID", -1));
            }
            return ChooseDeviceUtils.getMainIntent(context, l);
        }
        return ChooseDeviceUtils.getMainIntent(context, l);
    }

    public static Intent createStartIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void startNextActivity() {
        if (CustomApplication.getApplication().isUserLoggedIn()) {
            this.startActivity(this.createMainActivityIntent((Context)this));
            long l = CustomApplication.getApplication().getCurrentUserId();
            ((MvpApplication)this.getApplicationContext()).getSyncHelper().syncAll(this.getApplicationContext(), l);
        } else {
            this.startActivity(SignActivity.getStartIntent((Context)this, this.getIntent().getBooleanExtra("com.getqardio.android.extras.FROM_NOTIFICATION", false)));
        }
        this.needFinish = true;
    }

    /* synthetic */ void lambda$onCreate$0() {
        if (!this.isCanceled) {
            this.startNextActivity();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.isCanceled = true;
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968834);
        this.toolbar.setVisibility(8);
        if (object != null && object.containsKey("com.getqardio.android.state.NEED_FINISH")) {
            this.needFinish = object.getBoolean("com.getqardio.android.state.NEED_FINISH", this.needFinish);
        }
        if (!this.needFinish) {
            new Handler().postDelayed(SplashActivity$$Lambda$1.lambdaFactory$(this), 2000L);
        }
        if (CustomApplication.getApplication().isUserLoggedIn()) {
            WearableCommunicationService.start((Context)this);
            object = AuthHelper.getUserById((Context)CustomApplication.getApplication(), CustomApplication.getApplication().getCurrentUserId());
            if (object.trackingId == null) {
                AuthHelper.login((Context)CustomApplication.getApplication(), object.email, object.password);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.needFinish) {
            WearableCommunicationService.stop((Context)this);
            MobileDeviceFactory.stopMeasurementService((Context)this);
            CustomApplication.getApplication().disconnectSHealth();
            this.finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("com.getqardio.android.state.NEED_FINISH", this.needFinish);
    }
}

