/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.ReminderEditActivity;

public class ReminderListActivity
extends BaseActivity {
    private void displayCreateReminderFragment() {
        this.startActivity(ReminderEditActivity.getStartIntent((Context)this, "bp"));
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ReminderListActivity.class);
    }

    public static Intent getStartIntent(Context context, boolean bl) {
        context = ReminderListActivity.getStartIntent(context);
        context.putExtra("com.getqardio.android.extras.EXTRA_OPENED_EXTERNAL", bl);
        return context;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968807);
        bundle = this.getIntent();
        if (bundle != null && bundle.getExtras() != null && bundle.getBooleanExtra("com.getqardio.android.extras.EXTRA_OPENED_EXTERNAL", false)) {
            this.displayCreateReminderFragment();
        }
    }
}

