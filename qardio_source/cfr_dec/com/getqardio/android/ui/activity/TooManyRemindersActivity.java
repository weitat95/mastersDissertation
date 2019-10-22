/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.utils.ui.ActivityUtils;

public class TooManyRemindersActivity
extends BaseActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, TooManyRemindersActivity.class);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968842);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return true;
    }
}

