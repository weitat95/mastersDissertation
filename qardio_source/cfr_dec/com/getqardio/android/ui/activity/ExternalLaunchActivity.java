/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.ui.activity.ReminderListActivity;
import com.getqardio.android.ui.activity.SendHistoryActivity;
import com.getqardio.android.ui.activity.SplashActivity;

public class ExternalLaunchActivity
extends BaseActivity {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Uri uri = this.getIntent().getData();
        bundle = null;
        if (TextUtils.isEmpty((CharSequence)CustomApplication.getApplication().getCurrentUserToken())) {
            bundle = SplashActivity.createStartIntent((Context)this);
        } else {
            long l = CustomApplication.getApplication().getCurrentUserId();
            if (uri != null && uri.getQueryParameter("action") != null) {
                switch (Integer.parseInt(uri.getQueryParameter("action"))) {
                    default: {
                        break;
                    }
                    case 1: {
                        bundle = SplashActivity.createStartIntent((Context)this);
                        break;
                    }
                    case 2: {
                        bundle = ReminderListActivity.getStartIntent((Context)this, true);
                        break;
                    }
                    case 3: {
                        bundle = MainActivity.getStartIntent((Context)this, 2131821479);
                        break;
                    }
                    case 4: {
                        bundle = MainActivity.getStartIntent((Context)this, 2131821476);
                        break;
                    }
                    case 5: {
                        bundle = SendHistoryActivity.getStartIntent((Context)this, l);
                        break;
                    }
                }
            } else if (uri.toString().contains("qardio://")) {
                bundle = MainActivity.getStartIntent((Context)this, uri.toString());
            }
        }
        this.startActivity((Intent)bundle);
        this.finish();
    }
}

