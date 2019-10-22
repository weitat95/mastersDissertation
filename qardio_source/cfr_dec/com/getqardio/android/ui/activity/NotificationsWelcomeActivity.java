/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.NotificationsWelcomeFragment;

public class NotificationsWelcomeActivity
extends BaseActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968771);
        if (bundle == null) {
            this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)NotificationsWelcomeFragment.newInstance()).commit();
        }
    }
}

