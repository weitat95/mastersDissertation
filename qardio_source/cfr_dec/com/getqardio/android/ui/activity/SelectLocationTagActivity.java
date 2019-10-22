/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;

public class SelectLocationTagActivity
extends BaseActivity {
    private SelectLocationTagFragment fragment;

    public static Intent getStartIntent(Context context, long l, long l2) {
        context = new Intent(context, SelectLocationTagActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.MEASUREMENT_DATE", l2);
        return context;
    }

    @Override
    public void onBackPressed() {
        if (this.fragment != null) {
            this.fragment.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968813);
        Intent intent = this.getIntent();
        if (bundle == null && intent != null) {
            long l = intent.getLongExtra("com.getqardio.android.extras.USER_ID", -1L);
            long l2 = intent.getLongExtra("com.getqardio.android.extras.MEASUREMENT_DATE", -1L);
            bundle = this.getFragmentManager().beginTransaction();
            this.fragment = SelectLocationTagFragment.newInstance(l, l2);
            bundle.add(2131820778, (Fragment)this.fragment);
        }
        return;
        finally {
            bundle.commit();
        }
    }
}

