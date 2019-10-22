/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.ImageSlideshowFragment;

public class ImageSlideshowActivity
extends BaseActivity {
    private CloseBroadcastReceiver closeBroadcastReceiver = new CloseBroadcastReceiver();

    public static void closeImageSlideshowActivity(Context context) {
        Intent intent = new Intent("com.getqardio.android.Action.CLOSE");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static Intent getStartIntent(Context context, boolean bl, boolean bl2) {
        context = new Intent(context, ImageSlideshowActivity.class);
        context.putExtra("com.getqardio.android.extras.USE_FLICKR", bl);
        context.putExtra("com.getqardio.android.extras.USE_PHOTO_FROM_DEVICE", bl2);
        return context;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968727);
        this.toolbar.setVisibility(8);
        boolean bl = false;
        boolean bl2 = false;
        Intent intent = this.getIntent();
        if (intent != null) {
            bl = intent.getBooleanExtra("com.getqardio.android.extras.USE_FLICKR", false);
            bl2 = intent.getBooleanExtra("com.getqardio.android.extras.USE_PHOTO_FROM_DEVICE", false);
        }
        if (bundle == null) {
            bundle = this.getFragmentManager().beginTransaction();
            bundle.add(2131820778, (Fragment)ImageSlideshowFragment.newInstance(bl, bl2));
        }
        return;
        finally {
            bundle.commit();
        }
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

    @Override
    public void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.closeBroadcastReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.closeBroadcastReceiver, new IntentFilter("com.getqardio.android.Action.CLOSE"));
    }

    private class CloseBroadcastReceiver
    extends BroadcastReceiver {
        private CloseBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            ImageSlideshowActivity.this.finish();
        }
    }

}

