/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.ui.dialog.CustomProgressDialog;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class SignForgotPasswordActivity
extends BaseActivity
implements SignForgotPasswordFragment.SignForgotPasswordFragmentCallback {
    private void doDismissDialog(int n) {
        try {
            this.dismissDialog(n);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignForgotPasswordActivity.class);
    }

    @Override
    public void dismissProgressDialog() {
        this.doDismissDialog(1);
    }

    @Override
    public void displayNetworkErrorDialog() {
        this.showDialog(2);
    }

    @Override
    public void displayProgressDialog() {
        this.showDialog(1);
    }

    @Override
    public void displayResetPasswordPassed() {
        this.showDialog(3);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968829);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
    }

    protected Dialog onCreateDialog(int n) {
        switch (n) {
            default: {
                return super.onCreateDialog(n);
            }
            case 1: {
                return CustomProgressDialog.newInstance((Context)this);
            }
            case 2: {
                return CustomAlertDialog.newInstance((Context)this, this.getText(2131361956).toString());
            }
            case 3: 
        }
        CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance((Context)this, this.getString(2131362047), this.getString(2131361888));
        customAlertDialog.setOnClickListener(new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                SignForgotPasswordActivity.this.finish();
            }
        });
        return customAlertDialog;
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

