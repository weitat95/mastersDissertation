/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.CustomApplication;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.util.Map;
import timber.log.Timber;

public class BaseActivity
extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();
    protected Toolbar toolbar;

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        super.setContentView(2130968639);
        this.toolbar = (Toolbar)((Object)this.findViewById(2131820755));
        this.setSupportActionBar(this.toolbar);
        object = CustomApplication.getApplication().getTracker();
        ((Tracker)object).enableAdvertisingIdCollection(true);
        ((Tracker)object).setScreenName(TAG);
        ((Tracker)object).send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance((Context)this).reportActivityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            GoogleAnalytics.getInstance((Context)this).reportActivityStop(this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Timber.e(illegalArgumentException, "Exception occurred during calling activityStop", new Object[0]);
            return;
        }
    }

    @Override
    public void setContentView(int n) {
        LayoutInflater.from((Context)this).inflate(n, (ViewGroup)this.findViewById(2131820891), true);
    }
}

