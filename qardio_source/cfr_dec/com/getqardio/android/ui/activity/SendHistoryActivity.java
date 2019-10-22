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
 *  android.view.MenuItem
 *  android.view.View
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.fragment.SendHistoryFragment;
import com.getqardio.android.ui.fragment.SendingHistoryResultFragment;
import com.getqardio.android.ui.widget.PickContactView;
import com.getqardio.android.utils.ContactsHelper;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.KeyboardHelper;

public class SendHistoryActivity
extends BaseActivity
implements SendHistoryFragment.Callback,
PickContactView.Callback {
    private ProgressDialogFragment progressDialogFragment;

    private void changeFragment(Fragment fragment, String string2) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(2131820778, fragment, string2);
        fragmentTransaction.commit();
    }

    public static Intent getStartIntent(Context context, long l) {
        context = new Intent(context, SendHistoryActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        return context;
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        ((SendHistoryFragment)this.getFragmentManager().findFragmentByTag("SEND_HISTORY_FRAGMENT")).onActivityResult(n, n2, intent);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968817);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        this.progressDialogFragment = ProgressDialogFragment.newInstance(false);
        long l = this.getIntent().getLongExtra("com.getqardio.android.extras.USER_ID", CustomApplication.getApplication().getCurrentUserId().longValue());
        if (bundle == null) {
            this.changeFragment(SendHistoryFragment.newInstance(l), "SEND_HISTORY_FRAGMENT");
        }
    }

    @Override
    public void onDisplayProgressDialog() {
        this.progressDialogFragment.show(this.getFragmentManager(), null);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        KeyboardHelper.hideKeyboard((Context)this, this.getCurrentFocus());
        this.onBackPressed();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        super.onRequestPermissionsResult(n, arrstring, arrn);
        switch (n) {
            default: {
                return;
            }
            case 3: {
                if (arrn.length <= 0 || arrn[0] != 0) return;
                if (PermissionUtil.Contact.hasReadContactsPermission((Context)this)) {
                    ContactsHelper.requestPickContact(this);
                    return;
                } else {
                    break;
                }
            }
        }
        PermissionUtil.Contact.checkReadContactsPermission(this);
    }

    @Override
    public void onSendingFinished(Intent intent) {
        if (this.progressDialogFragment != null) {
            this.progressDialogFragment.dismiss();
        }
        this.changeFragment(SendingHistoryResultFragment.newInstance(intent.getBooleanExtra("com.getqardio.android.extras.SENT_TO_MY_DOCTOR", false)), "SEND_HISTORY_RESULT_FRAGMENT_TAG");
    }

    @Override
    public void requestContactPermissions() {
        PermissionUtil.Contact.checkReadContactsPermission(this);
    }

    @Override
    public void requestPickContact() {
        ContactsHelper.requestPickContact(this);
    }
}

