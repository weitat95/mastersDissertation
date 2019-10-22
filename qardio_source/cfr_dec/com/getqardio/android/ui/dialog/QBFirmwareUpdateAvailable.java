/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.getqardio.android.ui.activity.QBStepOnActivity;
import com.getqardio.android.ui.dialog.QBFirmwareUpdateAvailable$$Lambda$1;

public class QBFirmwareUpdateAvailable
extends DialogFragment {
    private String description;
    private String ipAddress;
    private String version;

    public static QBFirmwareUpdateAvailable newInstance(String object, String string2, String string3) {
        Bundle bundle = new Bundle(3);
        bundle.putString("DESCRIPTION", object);
        bundle.putString("IP_ADDRESS", string2);
        bundle.putString("VERSION", string3);
        object = new QBFirmwareUpdateAvailable();
        object.setArguments(bundle);
        return object;
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int n) {
        this.startActivity(QBStepOnActivity.createStartIntentForFirmwareUpdate((Context)this.getActivity(), this.ipAddress, this.version, this.description));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.description = this.getArguments().getString("DESCRIPTION");
        this.ipAddress = this.getArguments().getString("IP_ADDRESS");
        this.version = this.getArguments().getString("VERSION");
    }

    public Dialog onCreateDialog(Bundle object) {
        object = new AlertDialog.Builder((Context)this.getActivity(), 2131493366);
        ((AlertDialog.Builder)object).setTitle(2131362241);
        ((AlertDialog.Builder)object).setMessage(this.description);
        ((AlertDialog.Builder)object).setPositiveButton(2131362395, QBFirmwareUpdateAvailable$$Lambda$1.lambdaFactory$(this));
        ((AlertDialog.Builder)object).setNegativeButton(17039360, null);
        object = ((AlertDialog.Builder)object).create();
        object.setCanceledOnTouchOutside(false);
        return object;
    }
}

